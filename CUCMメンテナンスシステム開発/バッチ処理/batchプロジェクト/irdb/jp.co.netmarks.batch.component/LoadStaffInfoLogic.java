package jp.co.netmarks.batch.component;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ksc.batch.exception.BatRuntimeException;
import jp.co.ksc.batch.exception.CSVException;
import jp.co.ksc.batch.util.BatchSettings;
import jp.co.ksc.batch.util.CSVUtil;
import jp.co.ksc.batch.util.ErrorFileManager;
import jp.co.ksc.batch.util.LoadStaffInfoWriter;
import jp.co.netmarks.batch.dao.StaffInfoDAO;

/**
 * <p>Title: LoadStaffInfoLogic.java</p>
  * <pre>
  * 人事情報取込みロジック
  * </pre>
* @Time:2023/06/28
* @author Yao KaiKyou
 */
@Component
public class LoadStaffInfoLogic {

    /** ログ出力クラス*/
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private StaffInfoDAO dao;


    // ************************************************
    // 連携ファイルの移動
    // ************************************************
    /**
     * 連携ファイルの移動
     * @param props BatchSettings
     */
    public void movFiles(BatchSettings props) {
            String importDir = props.getReceiveDir();
            String exportDir = props.getCsvFtpDir();

            try {
                LoadStaffInfoWriter writer = new LoadStaffInfoWriter(importDir, exportDir);
                List<Object> plist = new ArrayList<>();
                writer.write(plist);
            } catch (Exception e) {
                System.out.println("ファイル移動に失敗しました");
                e.printStackTrace();
            }
    }

    // ************************************************
    // 必須CSVファイル存在チェック
    // ************************************************
    /**
     * 必須CSVファイル存在チェック
     * @param fileNames
     * @return ALL有り True 以外 False
     */
    public boolean existsIndispensableCsvFile(String[] fileNames) {
        boolean ret = true;

        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(fileNames[i]);
            ret = file.isFile();

            if (!ret) {
            	log.debug("CSVファイル[" + fileNames[i] + "]は存在しません。");
            	return ret;
            }
        }
        return ret;
    }

    // ************************************************
    // ＣＳＶ処理
    // ************************************************
    /**
     * CSV (ORGANIZATION)取込み
     *
     * @param setting BatchSettings
     * @throws IOException
     * @throws CSVException
     * @throws SQLException
     */
    public void doBizOrganization(BatchSettings setting) throws IOException, CSVException, SQLException {
        // CSVファイル読込み
        String[] header = setting.getTmpBizOrganizationCsvHeader().split(", ", 0);
        String tableName = setting.getTmpBizOrganizationTableName();
        Map<String, Object>[] records = readBizCsv(setting.getCsvFtpDir() + setting.getTmpIntOrganizationCsvFileName(), header);

        // CSVデータをBIZテーブルに登録
        if (records != null && records.length > 0) {
            log.debug(tableName + "登録対象件数:" + records.length);
            dao.insertBizTableAll(records, tableName, header);
        }
        // BIZテーブルからダミー等を削除
        dao.deleteBizOrganization();
    }

    // -----------------
    // CSV(BizDepartment)取込
    // -----------------
     /**
      * CSV(BizDepartment)取込
      * @param setting BatchSettings
      * @throws IOException
      * @throws CSVException
      * @throws SQLException
      */
     public void doBizDepartment(BatchSettings setting) throws IOException, CSVException, SQLException {

         // CSVファイル読込み
         String[] header = setting.getTmpBizDepartmentCsvHeader().split(",",0);
         String tableName = setting.getTmpBizDepartmentTableName();
         Map<String,Object>[] records = readBizCsv(setting.getCsvFtpDir() + setting.getTmpIntDepartmentCsvFileName(), header);

         // CSVデータをBIZテーブルに登録
         if (records != null && records.length > 0) {
             log.debug(tableName + "登録対象件数：" + records.length);
             dao.insertBizTableAll(records, tableName, header);
         }

         // BIZテーブルからダミー等を削除
         dao.deleteBizDepartment();
     }



     // -----------------
     // CSV(BizAd)取込
     // -----------------
     /**
      * CSV(BizAd)取込
      * @param setting BatchSettings
      * @throws IOException
      * @throws CSVException
      * @throws SQLException
      */
     @SuppressWarnings("unchecked")
     public void doBizAd(BatchSettings setting) throws IOException, CSVException, SQLException {

         // CSVファイル読込み
         String[] header = setting.getTmpBizAdCsvHeader().split(",",0);
         String tableName = setting.getTmpBizAdTableName();
         Map<String,Object>[] records = readBizCsv(setting.getCsvFtpDir() + setting.getBizAdCsvFileName(), header);

         // CSVデータをBIZテーブルに登録
         if (records != null && records.length > 0) {

             List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
             List<String> addedCodeList = new ArrayList<String>();

             // 社員コード10桁の場合、先頭3桁を取り除く
             for (int i = 0; i < records.length; i++) {

                 String employeeCode = (String) records[i].get("LOGIN_NM");
                 try {
                     Long.parseLong(employeeCode);

                     // 10桁＆会社コード「001」
                     if (employeeCode.length() != 7 && employeeCode.length() == 10) {
                         records[i].put("LOGIN_NM", employeeCode.substring(employeeCode.length() - 7));
                         log.debug("社員コード変換 元社員コード：[" + employeeCode + "] 新社員コード：[" + records[i].get("LOGIN_NM") + "]");
                     }

                     boolean added = false;
                     for (int j = 0; j < addedCodeList.size(); j++) {
                         String tmpCode = (String) addedCodeList.get(j);
                         if (tmpCode.equals(records[i].get("LOGIN_NM").toString())) {
                             added = true;
                             break;
                         }
                     }
                     if (!added) {
                         log.debug("ADD EmployeeCode:" + records[i].get("LOGIN_NM"));
                         list.add(records[i]);
                         addedCodeList.add(records[i].get("LOGIN_NM").toString());
                     } else {
                         log.debug("ADDED EmployeeCode:" + records[i].get("LOGIN_NM"));
                     }

                 } catch (NumberFormatException nfe) {
                     log.debug("NG EmployeeCode:" + employeeCode);
                 }
             }

             Map<String,Object>[] okRecords = (Map[]) list.toArray(new Map[list.size()]);
             log.debug(tableName + "登録対象件数：" + okRecords.length);
             dao.insertBizTableAll(okRecords, tableName, header);
         }
     }

  // -----------------
     // CSV(BizEmployee)取込
     // -----------------
     /**
      * CSV(BizEmployee)取込
      * @param setting BatchSettings
      * @throws IOException
      * @throws CSVException
      * @throws SQLException
      */
     public void doBizEmployee(BatchSettings setting) throws IOException, CSVException, SQLException {

         // CSVファイル読込み
         String[] header = setting.getTmpBizEmployeeCsvHeader().split(",",0);
         String tableName = setting.getTmpBizEmployeeTableName();
         Map<String,Object>[] records = readBizCsv(setting.getCsvFtpDir() + setting.getBizEmployeeCsvFileName(), header);

         // CSVデータをBIZテーブルに登録
         if (records != null && records.length > 0) {
             log.debug(tableName + "登録対象件数：" + records.length);
             dao.insertBizTableAll(records, tableName, header);
         }
         // BIZテーブルからダミー等を削除
        dao.deleteBizEmployee();
     }

     // ************************************************
     // 退職者リスト出力
     // ************************************************
     /**
      * 退職者リスト出力
      * @param props BatchSettings
      * @throws Exception
      */
     public void retiredUserFileOut(BatchSettings props) throws Exception {

  		String outputPath    =  props.getOutPutRetireDir();
  		String retireListNm  =  props.getRetiredUserFileName();

          File backupDir = new File(outputPath);
          if (!backupDir.exists()) {
              backupDir.mkdirs();
          }

          try{
          	Map<String,Object>[] retire = dao.selRetireUser();

  	        // 出力
  	        CSVUtil csv = new CSVUtil(retire);
              csv.setDataMaps(retire);
              csv.setHasHeader(true);
              csv.addHeader("last_name");
              csv.addHeader("first_name");
              csv.addHeader("telephone_no ");
              csv.addHeader("full_name");
  	        csv.setFileName( outputPath + csv.getTimestampAddFileNM(retireListNm));
  	        csv.write();

          }catch(Exception e){
          	throw e;
          }
      }


     // ************************************************
     // 例外処理 エラーファイル作成
     // ************************************************
     /**
      * エラーファイル作成
      * @param props BatchSettings
      */
     public void creaErrFile(BatchSettings props) {
         String errorFilePath = props.getOutPutErrFileDir();
         String errorMessage = props.getErrMessage();
 		String outputErrFile = props.getOutPutErrFileNm();
         ErrorFileManager errorFileManager = new ErrorFileManager(errorFilePath, errorMessage, outputErrFile);

         try {
             errorFileManager.checkErrorFileExists();
         } catch (BatRuntimeException e) {
             e.printStackTrace();
         }
     }




  //*********************************
 // 内部処理メソッド
 //*********************************
 /**
 * 内部処理メイン
 *
 *@param todayCsvFileName CSV)71K
 *@param csvHeader A
 *@return 読み込んだ csvの内容
 *@throws IOException
 *@throws CSVException
 */
 private Map<String, Object>[] readBizCsv(String todayCsvFileName, String[] csvHeader)
             throws IOException, CSVException {
         CSVUtil csvUtil = new CSVUtil(todayCsvFileName);

         if (csvHeader != null) {
             for (int i = 0; i < csvHeader.length; i++) {
                 csvUtil.addHeader(csvHeader[i]);
             }
         } else {
             log.warn("CSVファイルのヘッダーが定義されていません。");
             throw new CSVException();
         }

         try {
             csvUtil.read();
             log.debug("CSVファイル[" + todayCsvFileName + "]読込み成功");
         } catch (IOException ioe) {
             log.warn("CSVファイル[" + todayCsvFileName + "]読込みエラー(IOException)");
             throw ioe;
         } catch (CSVException csve) {
             log.warn("CSVファイル[" + todayCsvFileName + "]読込みエラー(CSVException)");
             throw csve;
         }

         return csvUtil.getDataMaps();
     }

 // ************************************************
 // CSVファイルリネーム
 // ************************************************
 /**
  * CSVファイルリネーム
  * @param props BatchSettings
  * @throws IOException
  */
 public void csvFileRename(BatchSettings props) throws IOException {

		String impcsvPath    =  props.getCsvFtpDir();
		String impcmpcsvPath =  props.getInputCompDir();
		String[] impcsv      = {props.getEofAd(),
								props.getEofAm(),
								props.getTmpAdCsvFileName(),
								props.getTmpIntDepartmentCsvFileName(),
								props.getTmpIntEmployeeCsvFileName(),
								props.getTmpIntOrganizationCsvFileName()
								};

     File backupDir = new File(impcmpcsvPath);
     if (!backupDir.exists()) {
         backupDir.mkdirs();
     }

     Calendar cal = Calendar.getInstance();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
     String ymdhms = sdf.format(cal.getTime());

     for (int i = 0; i < impcsv.length; i++) {
         String todayCsvFileName = impcsv[i];
         String[] csvSplit = todayCsvFileName.split("\\.", 0);

         File importCsvFile = new File(impcsvPath + todayCsvFileName);
         if (importCsvFile.exists()) {
         	String newFilenm = "";
         	if(i<2){
         		newFilenm =  backupDir + "/" + todayCsvFileName + "_IMPORTED_" + ymdhms;
         	}else{
         		newFilenm =  backupDir + "/" + csvSplit[0] + "_IMPORTED_" + ymdhms + "." + csvSplit[1];
         	}
         	File renameCsvFile = new File(newFilenm);
             importCsvFile.renameTo(renameCsvFile);
         }
     }
 }


}
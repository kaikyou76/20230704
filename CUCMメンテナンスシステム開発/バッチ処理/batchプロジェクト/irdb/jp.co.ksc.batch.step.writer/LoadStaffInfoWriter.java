package jp.co.ksc.batch.step.writer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ksc.batch.exception.BatRuntimeException;
import jp.co.ksc.batch.util.BatchSettings;
import jp.co.ksc.batch.util.LockFileManager;
import jp.co.netmarks.batch.component.LoadStaffInfoLogic;
import jp.co.netmarks.batch.dao.StaffInfoDAO;
import jp.co.netmarks.batch.dao.VacuumDAO;
import jp.co.netmarks.batch.persistence.LoadStaffMapper;
import jp.co.netmarks.util.ErrorMail;

/**
 * <p>Title: LoadStaffInfoWriter.java</p>
 * <pre>
 * 人事情報取込 Job Writer
 * </pre>
 * 2023/06/21
 * author Yao Kaikyou
 */
public class LoadStaffInfoWriter implements ItemWriter<Object> {
    private static final Log log = LogFactory.getLog(LoadStaffInfoWriter.class);

    @Autowired
    private Properties properties;

    @Autowired
    private LoadStaffInfoLogic lsl;

    @Autowired
    private StaffInfoDAO lsdao;

    @Autowired
    private LoadStaffMapper lsMapper;

    @Autowired
    private VacuumDAO vd;

    @Autowired
    private ErrorMail errMail;

    /**
     * 人事情報取込処理メイン
     *
     * @param paramList パラメーター
     * @throws Exception
     */
    public void write(List<?> paramList) throws Exception {
        BatchSettings bs = new BatchSettings(properties);

        // ロックファイルを確認
        try {
            LockFileManager.lock(bs);
            log.info(bs.getProperty("BT_000_1001"));
            // 1s1.movFiles(bs); // ファイル移動は確認済みなので、一旦コメントアウト
        } catch (IOException ex) {
            String errorMessage = bs.getProperty("BT_000_E001");
            throw new BatRuntimeException(errorMessage, ex);
        } finally {
            // ロック解除
            LockFileManager.unlock(bs);
        }

        try {
            String impcsvPath = bs.getCsvFtpDir(); // inputDir 変数A
            String InpCompPath = bs.getInputCompDir(); // 変数B
            // String OupRetirePath = bs.getOutputRetireDir(); // 退職・加入者データ格納先 変数C
            String fileEoFAd = bs.getEofAd(); // EofAd 変数D
            System.out.println(fileEoFAd);
            String fileEoFAM = bs.getEofAm(); // EofAm 変数E
            System.out.println(fileEoFAM);
            String bizAdFileNM = bs.getTmpAdCsvFileName(); // BizAdのCSVファイル名 変数F
            System.out.println(bizAdFileNM);
            String bizDeptFileNM = bs.getTmpIntDepartmentCsvFileName(); // BizDepartmentのCSVファイル名 変数G
            System.out.println(bizDeptFileNM);
            String bizEmpFileNM = bs.getTmpIntEmployeeCsvFileName(); // BizEmployeeのcsvファイル名 変数H
            System.out.println(bizEmpFileNM);
            String bizorgFileNM = bs.getTmpIntOrganizationCsvFileName(); // BizOrganizationのCSVファイル名 変数I
            System.out.println(bizorgFileNM);
            // String rUserFileNM = bs.getRetiredUserFileName(); // 退職者情報データファイル名 変数J
            // String jUserFileNM = bs.getJoinedUserFileName(); // 加入者情報データファイル名 変数K
            // String receivePath = bs.getReceiveDir(); // ReceiveDir 変数L
            System.out.println(impcsvPath);
            String[] fileNames = {
                    // impcsvPath + fileEoFAD,
                    // impcsvPath + fileEoFAM,
                    impcsvPath + bizAdFileNM,
                    impcsvPath + bizDeptFileNM,
                    impcsvPath + bizEmpFileNM,
                    impcsvPath + bizorgFileNM
            };
            if (!lsl.existsIndispensableCsvFile(fileNames)) {
                log.warn("人事情報取込み対象無し");
                throw new BatRuntimeException();
            } else {
                // 取得前にテーブルをクリア
                // BIZ_AD取込
                lsMapper.deleteBizAD();
                lsl.doBizAd(bs);

                // BIZ_DEPARTMENT取込
                lsMapper.deleteBizDepartment();
                lsl.doBizDepartment(bs); // 後ろの処理にデータが必要なので、仮に「dao.deleteBizDepartment();」をコメントアウト

                // BIZ_EMPLOYEE取込
                lsMapper.deleteBizEmployee();
                lsl.doBizEmployee(bs);

                // BIZ_ORGANIZATION取込
                lsMapper.deleteBizOrganization();
                // lsl.doBizorganization(bs);
                lsl.doBizOrganization(bs);
            }

            int msectioncnt = 0;
            int cntEmployee = 0;
            // 店部課マスタ追加
            msectioncnt = lsdao.insertMSection();
            log.info("店部課追加件数 : " + msectioncnt);

            msectioncnt = lsdao.updateMSection();
            log.info("店部課情報更新件数 : " + msectioncnt);

            // 退社処理
            cntEmployee = lsdao.retireAppUser();
            log.info("退社人数 : " + cntEmployee);

            String oPPath = bs.getOutPutNewUsersDir();
            System.out.println("OPPath---------" + oPPath);
            String JoinedPath = bs.getJoinedUserFileName();
            System.out.println("JoinedPath---" + JoinedPath);

            // 入社処理
            cntEmployee = lsdao.additionAppUser();
            log.info("入社人数: " + cntEmployee);

            // 既存社員のプロパティ更新
            cntEmployee = lsdao.updateAppUser();
            log.info("社員情報更新件数 : " + cntEmployee);

            // 社員の所属追加
            cntEmployee = lsdao.additionRUserSection();
            log.info("社員所属追加件数 : " + cntEmployee);

            // 社員の所属変更
            cntEmployee = lsdao.updateRUserSetion();
            log.info("社員所属更新件数 : " + cntEmployee);

            // 社員の転出元所属削除
            cntEmployee = lsdao.changePersonnel();
            log.info("社員所属削除件数 : " + cntEmployee);

            // FileRename
            lsl.csvFileRename(bs);

            // 退社者リスト出力
            lsl.retiredUserFileOut(bs);

            // 例外処理 エラーファイル作成
            lsl.creaErrFile(bs);

            log.info("Vacuum Analyze開始");
            vd.dayVacuum();
            log.info("Vacuum Analyze終了");
        } catch (Exception e) {
            throw e;
        } finally {
            // ロック解除
            LockFileManager.unlock(bs);
        }
    }
}
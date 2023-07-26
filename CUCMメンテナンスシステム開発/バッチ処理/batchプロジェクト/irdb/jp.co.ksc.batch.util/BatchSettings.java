/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BatchSettings.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.util;

import java.io.Serializable;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ksc.batch.exception.BatRuntimeException;

/**
 * <pre>
 * バッチの挙動に関する設定を保持するクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class BatchSettings implements Serializable {

	@Autowired
    private Properties props;
	private String osType = "Win32";

    /**
     * コンストラクタ
     * @param p プロパティファイルの内容
     */
    public BatchSettings(Properties p) {
        super();
    	this.props = p;
    	String osname = System.getProperty("os.name");
    	if(osname.indexOf("Windows")>=0){
    	   // Windowsであったときの処理
    		this.osType = "Win32";
    	} else if(osname.indexOf("Linux")>=0){
    	   // Linuxであったときの処理
    		this.osType = "Linux";
    	}
    }

    /**
     * コンストラクタ
     */
    public BatchSettings() {
        super();
    }

    /**
     * プロパティファイルの特定KEY情報を取得
     * @param key
     * @return 特定KEYのプロパティ情報
     */
    public String getProperty(String key){
        return props.getProperty(key);
    }

    /**
     * ロックファイルのパスを取得します。
     * @return ロックファイルのパス
     */
    public String getLockFile() {
        String value = props.getProperty("LockFile."+ osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに LockFile の定義がありません。");
        }
        return value;
    }

    /**
     * CSVInputディレクトリの取得
     * @return FTPディレクトリ
     */
    public String getInputDir() {
        String value = props.getProperty("InputDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにInputDirの定義がありません。");
        }
        return value;
    }

    /**
     * CSVFTP先ディレクトリの取得
     * @return FTPディレクトリ
     */
    public String getCsvFtpDir() {
        return getInputDir();
    }

    /**
     * CSVImportCompleteディレクトリの取得
     * @return FTPディレクトリ
     */
    public String getInputCompDir() {
        String value = props.getProperty("InputCompDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにInputDirの定義がありません。");
        }
        return value;
    }

    /**
     * ReceiveDirディレクトリの取得
     * @return ReceiveDirディレクトリ
     */
    public String getReceiveDir() {
        String value = props.getProperty("ReceiveDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにReceiveDirの定義がありません。");
        }
        return value;
    }


    /**
     * Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getOutputDir() {
        String value = props.getProperty("OutputDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutputDirの定義がありません。");
        }
        return value;
    }

    /**
     * DBCSV Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getOutputDirDB() {
        String value = props.getProperty("OutputDir."+osType);
        value += props.getProperty("OutputDir2."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutputDir2の定義がありません。");
        }
        return value;
    }
    /**
     * AssociateCSV Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getOutputDirAssociate() {
        String value = props.getProperty("OutputDir."+osType);
        value += props.getProperty("OutputDir3."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutputDir3の定義がありません。");
        }
        return value;
    }
    /**
     * CircuitlistCSV Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getOutputDirCircuitlist() {
        String value = props.getProperty("OutputDir."+osType);
        value += props.getProperty("OutputDir4."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutputDir4の定義がありません。");
        }
        return value;
    }

    /**
     * RetireCSV Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getReceiveStDir() {
        String value = props.getProperty("ReceiveDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにReceiveStDirの定義がありません。");
        }
        return value;
    }

   /**
     * RetireCSV Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getOutPutRetireDir() {
        String value = props.getProperty("OutputRetireDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutputRetireDirの定義がありません。");
        }
        return value;
    }

    /**
     * NewUsersCSV Outputディレクトリの取得
     * @return Outputディレクトリ
     */
    public String getOutPutNewUsersDir() {
        String value = props.getProperty("OutputNewUsersDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutputNewUsersDirの定義がありません。");
        }
        return value;
    }

    /**
     * バッチエラーファイルのディレクトリの取得
     * @return バッチエラーファイルのディレクトリ
     */
    public String getOutPutErrFileDir() {
        String value = props.getProperty("ErrorFile."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutPutErrFileDirの定義がありません。");
        }
        return value;
    }

    /**
     * バッチエラーファイル名の取得
     * @return バッチエラーファイル名
     */
    public String getOutPutErrFileNm() {
        String value = props.getProperty("ErrorFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにOutPutErrFileNmの定義がありません。");
        }
        return value;
    }

    /**
     * バッチエラーファイル存在しないメッセージの取得
     * @return バッチエラーファイル存在しないメッセージ
     */
    public String getErrMessage() {
        String value = props.getProperty("BT_000_E007");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにErrMessageの定義がありません。");
        }
        return value;
    }

    /**
     * ユーザーと電話機の一覧 エクスポート/インポートディレクトリの取得
     * @return FTPディレクトリ
     */
    public String getManageSearchCsvDir() {
        String value = props.getProperty("ManageSearchCsvDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにManageSearchCsvDirの定義がありません。");
        }
        return value;
    }

    /**
     * ファイルディレクトリのセパレータを取得します。
     * @return ファイルディレクトリのセパレータ
     */
    public String getFileSeparetor(){
        return System.getProperty("file.separator");
    }

    /**
     * BizOrganizaionのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getBizOrganizationCsvFileName() {
        String value = props.getProperty("BizOrganizationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizOrganizationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BizDepartmentのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getBizDepartmentCsvFileName() {
        String value = props.getProperty("BizDepartmentCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizDepartmentCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BizEmployeeのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getBizEmployeeCsvFileName() {
        String value = props.getProperty("BizEmployeeCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizEmployeeCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BizAdのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getBizAdCsvFileName() {
        String value = props.getProperty("BizAdCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizAdCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * TmpIntEmployeeのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getTmpIntEmployeeCsvFileName() {
        String value = props.getProperty("TmpIntEmployeeCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに TmpIntEmployeeCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * TmpIntDepartmentのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getTmpIntDepartmentCsvFileName() {
        String value = props.getProperty("TmpIntDepartmentCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに TmpIntDepartmentCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * TmpAdのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getTmpAdCsvFileName() {
        String value = props.getProperty("TmpAdCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに TmpAdCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * BizShiftのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getBizShiftCsvFileName() {
        String value = props.getProperty("BizShiftCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizShiftCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * DumOrganizationのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getDumOrganizationCsvFileName() {
        String value = props.getProperty("DumOrganizationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに DumOrganizationCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * TmpOrganizationのcsvファイル名を取得します。
     *
     * @return csvファイル名
     */
    public String getTmpIntOrganizationCsvFileName() {
        String value = props.getProperty("TmpIntOrganizationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにTmpOrganizationCsvFileNameの定義がありません。");
        }
        return value;
    }

    /**
     * DumDepartmentのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getDumDepartmentCsvFileName() {
        String value = props.getProperty("DumDepartmentCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに DumDepartmentCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * DumEmployeeのCSVファイル名を取得します。
     * @return CSVファイル名
     */
    public String getDumEmployeeCsvFileName() {
        String value = props.getProperty("DumEmployeeCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに DumEmployeeCsvFileName の定義がありません。");
        }
        return value;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
//  一括入出力用CSVファイル -----------------------------------------------------
    /**
     * APP_USER テーブル情報CSVファイル名(一括入出力用)
     * @return APP_USER テーブル情報CSVファイル名
     */
    public String getDbAppUserCsvFileName() {
        String value = props.getProperty("DbAppUserCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbAppUserCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BIZ_AD テーブル情報CSVファイル名(一括入出力用)
     * @return BIZ_AD テーブル情報CSVファイル名
     */
    public String getDbBizAdFileName() {
        String value = props.getProperty("DbBizAdFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbBizAdFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BIZ_DEPARTMENT テーブル情報CSVファイル名(一括入出力用)
     * @return BIZ_DEPARTMENT テーブル情報CSVファイル名
     */
    public String getDbBizDepartmentCsvFileName() {
        String value = props.getProperty("DbBizDepartmentCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbBizDepartmentCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BIZ_EMPLOYEE テーブル情報CSVファイル名(一括入出力用)
     * @return BIZ_EMPLOYEE テーブル情報CSVファイル名
     */
    public String getDbBizEmployeeCsvFileName() {
        String value = props.getProperty("DbBizEmployeeCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbBizEmployeeCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BIZ_ORGANIZATION テーブル情報CSVファイル名(一括入出力用)
     * @return BIZ_ORGANIZATION テーブル情報CSVファイル名
     */
    public String getDbBizOrganizationCsvFileName() {
        String value = props.getProperty("DbBizOrganizationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbBizOrganizationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * BIZ_SHIFT テーブル情報CSVファイル名(一括入出力用)
     * @return BIZ_SHIFT テーブル情報CSVファイル名
     */
    public String getDbBizShiftCsvFileName() {
        String value = props.getProperty("DbBizShiftCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbBizShiftCsvFileName  の定義がありません。");
        }
        return value;
    }
    /**
     * CCM_LINE テーブル情報CSVファイル名(一括入出力用)
     * @return CCM_LINE テーブル情報CSVファイル名
     */
    public String getDbCcmLineCsvFileName() {
        String value = props.getProperty("DbCcmLineCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbCcmLineCsvFileName の定義がありません。");
        }

        return value;
    }
    /**
     * CHARGE_ASSOCIATION テーブル情報CSVファイル名(一括取り込み用)
     * @return CHARGE_ASSOCIATION テーブル情報CSVファイル名
     */
    public String getDbChargeAssociationCsvFileName() {
        String value = props.getProperty("DbChargeAssociationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbChargeAssociationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * DUM_DEPARTMENT テーブル情報CSVファイル名(一括入出力用)
     * @return DUM_DEPARTMENT テーブル情報CSVファイル名
     */
    public String getDbDumDepartmentCsvFileName() {
        String value = props.getProperty("DbDumDepartmentCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbDumDepartmentCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * DUM_EMPLOYEE テーブル情報CSVファイル名(一括入出力用)
     * @return DUM_EMPLOYEE テーブル情報CSVファイル名
     */
    public String getDbDumEmployeeCsvFileName() {
        String value = props.getProperty("DbDumEmployeeCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbDumEmployeeCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * DUM_ORGANIZATION テーブル情報CSVファイル名(一括入出力用)
     * @return DUM_ORGANIZATION テーブル情報CSVファイル名
     */
    public String getDbDumOrganizationCsvFileName() {
        String value = props.getProperty("DbDumOrganizationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbDumOrganizationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * M_BRANCH テーブル情報CSVファイル名(一括入出力用)
     * @return M_BRANCH テーブル情報CSVファイル名
     */
    public String getDbMBranchCsvFileName() {
        String value = props.getProperty("DbMBranchCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbMBranchCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * M_SECTION テーブル情報CSVファイル名(一括入出力用)
     * @return M_SECTION テーブル情報CSVファイル名
     */
    public String getDbMSectionCsvFileName() {
        String value = props.getProperty("DbMSectionCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbMSectionCsvFileName の定義がありません。");
        }
        return value;
    }

 /**
     * R_CCM_PHONE_LINE テーブル情報CSVファイル名(一括入出力用)
     * @return R_CCM_PHONE_LINE テーブル情報CSVファイル名
     */
    public String getDbRCcmPhoneLineCsvFileName() {
        String value = props.getProperty("DbRCcmPhoneLineCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbRCcmPhoneLineCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * CCM_PHONE テーブル情報CSVファイル名(一括入出力用)
     * @return CCM_PHONE テーブル情報CSVファイル名
     */
    public String getDbCcmPhoneCsvFileName() {
        String value = props.getProperty("DbCcmPhoneCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbCcmPhoneCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * R_CCM_USER_PHONE テーブル情報CSVファイル名(一括入出力用)
     * @return R_CCM_USER_PHONE テーブル情報CSVファイル名
     */
    public String getDbRCcmUserPhoneCsvFileName() {
        String value = props.getProperty("DbRCcmUserPhoneCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbRCcmUserPhoneCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * R_SECTION_BRANCH テーブル情報CSVファイル名(一括入出力用)
     * @return R_SECTION_BRANCH テーブル情報CSVファイル名
     */
    public String getDbRSectionBranchCsvFileName() {
        String value = props.getProperty("DbRSectionBranchCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbRSectionBranchCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * R_USER_ROLE テーブル情報CSVファイル名(一括入出力用)
     * @return R_USER_ROLE テーブル情報CSVファイル名
     */
    public String getDbRUserRoleCsvFileName() {
        String value = props.getProperty("DbRUserRoleCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbRUserRoleCsvFileName の定義がありません。");
        }
        return value;
    }

    /**
     * R_USER_SECTION テーブル情報CSVファイル名(一括入出力用)
     * @return R_USER_SECTION テーブル情報CSVファイル名
     */
    public String getDbRUserSectionCsvFileName() {
        String value = props.getProperty("DbRUserSectionCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbRUserSectionCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * SYS_SCHEDULE テーブル情報CSVファイル名(一括入出力用)
     * @return SYS_SCHEDULE テーブル情報CSVファイル名
     */
    public String getDbSysScheduleCsvFileName() {
        String value = props.getProperty("DbSysScheduleCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbSysScheduleCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * TEL_DIR_ASSOCIATION テーブル情報CSVファイル名(一括入出力用)
     * @return TEL_DIR_ASSOCIATION テーブル情報CSVファイル名
     */
    public String getDbTelDirAssociationCsvFileName() {
        String value = props.getProperty("DbTelDirAssociationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbTelDirAssociationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * UNITY_ASSOCIATION テーブル情報CSVファイル名(一括入出力用)
     * @return UNITY_ASSOCIATION テーブル情報CSVファイル名
     */
    public String getDbCUCAssociationCsvFileName() {
        String value = props.getProperty("DbCUCAssociationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                "environment.propertiesに" +
                " DbCUCAssociationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * VOICE_LOGGER_ASSOCIATION テーブル情報CSVファイル名(一括入出力用)
     * @return VOICE_LOGGER_ASSOCIATION テーブル情報CSVファイル名
     */
    public String getDbVoiceLoggerAssociationCsvFileName() {
        String value = props.getProperty("DbVoiceLoggerAssociationCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbVoiceLoggerAssociationCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * CallingSearchSpace テーブル情報CSVファイル名(一括入出力用)
     * @return テーブル情報CSVファイル名
     */
    public String getDbCcmCallingSearchSpaceCsvFileName() {
        String value = props.getProperty("DbCcmCallingSearchSpaceCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbCcmCallingSearchSpaceCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * PickupGroupテーブル情報CSVファイル名(一括入出力用)
     * @return テーブル情報CSVファイル名
     */
    public String getDbCcmPickupGroupCsvFileName() {
        String value = props.getProperty("DbCcmPickupGroupCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbCcmPickupGroupCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * ExcludeExecutiveテーブル情報CSVファイル名(一括入出力用)
     * @return テーブル情報CSVファイル名
     */
    public String getDbExcludeExecutiveCsvFileName() {
        String value = props.getProperty("DbExcludeExecutiveCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbExcludeExecutiveCsvFileName の定義がありません。");
        }
        return value;
    }
    /**
     * Thresholdテーブル情報CSVファイル名(一括入出力用)
     * @return テーブル情報CSVファイル名
     */
    public String getDbThresholdCsvFileName() {
        String value = props.getProperty("DbThresholdCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbThresholdCsvFileName の定義がありません。");
        }
        return value;
    }
 /**
     * ProductTypeテーブル情報CSVファイル名(一括入出力用)
     * @return テーブル情報CSVファイル名
     */
    public String getDbCcmTypeProductCsvFileName() {
        String value = props.getProperty("DbCcmTypeProductCsvFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " DbCcmTypeProductCsvFileName の定義がありません。");
        }
        return value;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
// Export CSV 系
    /**
     * TelDir 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportTelDir() {
        String value = props.getProperty("CsvExport.TelDir");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.TelDir の定義がありません。");
        }
        return value;
    }
    /**
     * Unity 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportCUC() {
        String value = props.getProperty("CsvExport.CUC");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.CUC の定義がありません。");
        }
        return value;
    }
    /**
     * VoiceLogger 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportVoice() {
        String value = props.getProperty("CsvExport.Voice");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.Voice の定義がありません。");
        }
        return value;
    }
    /**
     * VoiceLogger 入力CSVファイル名
     * @return 入力CSVファイル名
     */
    public String getImportVoice() {
        String value = props.getProperty("CsvImport.Voice");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvImport.Voice の定義がありません。");
        }
        return value;
    }
    /**
     * Charge 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportCharge() {
        String value = props.getProperty("CsvExport.Charge");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.Charge の定義がありません。");
        }
        return value;
    }
    /**
     * AD 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportAD() {
        String value = props.getProperty("CsvExport.AD");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.AD の定義がありません。");
        }
        return value;
    }
    /**
     * LineList 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportLineList() {
        String value = props.getProperty("CsvExport.LineList");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.LineList の定義がありません。");
        }
        return value;
    }
    /**
     * Manage 出力CSVファイル名
     * @return 出力CSVファイル名
     */
    public String getExportManage() {
        String value = props.getProperty("CsvExport.Manage");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " CsvExport.Manage の定義がありません。");
        }
        return value;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * バッチ成否判定ディレクトリの取得
     * @return バッチ成否判定ディレクトリ
     */
    public String getBatchJudgeDir() {
        String value = props.getProperty("BatchJudgeDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにBatchJudgeDirの定義がありません。");
        }
        return value;
    }
 /**
     * バッチ成否判定ファイルの取得
     * @return バッチ成否判定ファイル名
     */
    public String getBatchJudgeFileName(String propName) {
        String value = props.getProperty(propName);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにBatchJudgeFile.*の定義がありません。");
        }
        return value;
    }

    /**
     * ログインJSPファイルの取得
     * @return ログインJSPファイル名
     */
    public String getMaintenanceFileName(String propName) {
        String value = props.getProperty(propName);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに[" + propName + "]の定義がありません。");
        }
        return value;
    }

    /**
     * ログインJSPディレクトリの取得
     * @return ログインJSPディレクトリ
     */
    public String getLoginDir() {
        String value = props.getProperty("LoginDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにLoginDirの定義がありません。");
        }
        return value;
    }

    /**
     * メンテナンス用ログインJSPディレクトリの取得
     * @return メンテナンス用ログインJSPディレクトリ
     */
    public String getMaintenanceLoginDir() {
        String value = props.getProperty("MaintenanceLoginDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにMaintenanceLoginDirの定義がありません。");
        }
        return value;
    }

    /**
     * web.xml格納ディレクトリの取得
     * @return web.xml格納ディレクトリ
     */
    public String getWebXmlDir() {
        String value = props.getProperty("WebXmlDir."+osType);
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesにWebXmlDirの定義がありません。");
        }
        return value;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
// 人事情報取込バッチ用 /////
    /**
     * EOFAD ファイル名
     * @return ファイル名
     */
    public String getEofAd() {
        String value = props.getProperty("Eof.Ad");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " Eof.Ad の定義がありません。");
        }
        return value;
    }
    /**
     * EOFAM ファイル名
     * @return ファイル名
     */
    public String getEofAm() {
        String value = props.getProperty("Eof.Am");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに" +
                    " Eof.Am の定義がありません。");
        }
        return value;
    }
    /**
     * BizOrganizationのCsvのHeaderを取得
     * @return BizOrganizationCsvHeader
     */
    public String getBizOrganizationCsvHeader() {
        String value = props.getProperty("BizOrganizationCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizOrganizationCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * BizDepartmentのCsvのHeaderを取得
     * @return BizDepartmentCsvHeader
     */
    public String getBizDepartmentCsvHeader() {
        String value = props.getProperty("BizDepartmentCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizDepartmentCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * BizEmployeeのCsvのHeaderを取得
     * @return BizEmployeeCsvHeader
     */
    public String getBizEmployeeCsvHeader() {
        String value = props.getProperty("BizEmployeeCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizEmployeeCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * BizADのCsvのHeaderを取得
     * @return BizADCsvHeader
     */
    public String getBizAdCsvHeader() {
        String value = props.getProperty("BizAdCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizAdCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * BizShiftのCsvのHeaderを取得
     * @return BizShiftCsvHeader
     */
    public String getBizShiftCsvHeader() {
        String value = props.getProperty("BizShiftCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizShiftCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * BizOrganizationのテーブル名を取得
     * @return BizOrganizationテーブル名
     */
    public String getBizOrganizationTableName() {
        String value = props.getProperty("BizOrganizationTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizOrganizationTableName の定義がありません。");
        }
        return value;
    }
    /**
     * BizDepartmentのテーブル名を取得
     * @return BizDepartmentテーブル名
     */
    public String getBizDepartmentTableName() {
        String value = props.getProperty("BizDepartmentTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizDepartmentTableName の定義がありません。");
        }
        return value;
    }
  /**
     * BizEmployeeのテーブル名を取得
     * @return BizEmployeeテーブル名
     */
    public String getBizEmployeeTableName() {
        String value = props.getProperty("BizEmployeeTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizEmployeeTableName の定義がありません。");
        }
        return value;
    }
    /**
     * BizAdのテーブル名を取得
     * @return BizAdテーブル名
     */
    public String getBizAdTableName() {
        String value = props.getProperty("BizAdTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizAdTableName の定義がありません。");
        }
        return value;
    }
    /**
     * BizShiftのテーブル名を取得
     * @return BizShiftテーブル名
     */
    public String getBizShiftTableName() {
        String value = props.getProperty("BizShiftTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに BizShiftTableName の定義がありません。");
        }
        return value;
    }
    /**
     * RetiredUserの出力ファイル名を取得
     * @return RetiredUser出力ファイル名
     */
    public String getRetiredUserFileName() {
        String value = props.getProperty("RetiredUserFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに RetiredUserFileName の定義がありません。");
        }
        return value;
    }
    /**
     * JoinedUserの出力ファイル名を取得
     * @return JoinedUser出力ファイル名
     */
    public String getJoinedUserFileName() {
        String value = props.getProperty("JoinedUserFileName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに JoinedUserFileName の定義がありません。");
        }
        return value;
    }

    /**
     * BizOrganizationのテーブル名を取得
     * @return BizOrganizationのテーブル名
     */
    public String getTmpBizOrganizationTableName() {
        String value = props.getProperty("TmpBizOrganizationTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizOrganizationTableNameの定義がありません。");
        }
        return value;
    }

    /**
     * BizDepartmentのテーブル名を取得
     * @return BizDepartmentのテーブル名
     */
    public String getTmpBizDepartmentTableName() {
        String value = props.getProperty("TmpBizDepartmentTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizDepartmentTableNameの定義がありません。");
        }
        return value;
    }

    /**
     * BizEmployeeのテーブル名を取得
     * @return BizEmployeeのテーブル名
     */
    public String getTmpBizEmployeeTableName() {
        String value = props.getProperty("TmpBizEmployeeTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizEmployeeTableNameの定義がありません。");
        }
        return value;
    }

    /**
     * BizAdのテーブル名を取得
     * @return BizAdのテーブル名
     */
    public String getTmpBizAdTableName() {
        String value = props.getProperty("TmpBizAdTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizAdTableNameの定義がありません。");
        }
        return value;
    }

    /**
     * BizOrganization@Csv Headerを取得
     *
     * @return BizOrganizationCsvHeader
     */
    public String getTmpBizOrganizationCsvHeader() {
        String value = props.getProperty("TmpBizOrganizationCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizOrganizationCsvHeadersの定義がありません。");
        }
        return value;
    }

    /**
     * BizEmployee@Csv Headerを取得
     *
     * @return TmpBizEmployeeCsvHeader
     */
    public String getTmpBizEmployeeCsvHeader() {
        String value = props.getProperty("TmpBizEmployeeCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizEmployeeCsvHeadersの定義がありません。");
        }
        return value;
    }
  /**
     * BizDepartment@Csv Headerを取得
     *
     * @return BizDepartmentCsvHeader
     */
    public String getTmpBizDepartmentCsvHeader() {
        String value = props.getProperty("TmpBizDepartmentCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizDepartmentCsvHeadersの定義がありません。");
        }
        return value;
    }

    /**
     * BizAdCsvHeader@Csv Headerを取得
     *
     * @return BizAdCsvHeaderCsvHeader
     */
    public String getTmpBizAdCsvHeader() {
        String value = props.getProperty("TmpBizAdCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException("environment.propertiesにBizAdCsvHeadersの定義がありません。");
        }
        return value;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * ChargeのCsvのHeaderを取得
     * @return ChargeCsvHeader
     */
    public String getChargeCsvHeader() {
        String value = props.getProperty("ChargeCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに ChargeCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * UnityのCsvのHeaderを取得
     * @return UnityCsvのHeader
     */
    public String getCUCCsvHeader() {
        String value = props.getProperty("CUCCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに CUCCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * ADのCsvのHeaderを取得
     * @return ADCSVのHeader
     */
    public String getADCsvHeader() {
        String value = props.getProperty("ADCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに ADCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * TelDirのCsvのHeaderを取得
     * @return TelDirCsvのHeader
     */
    public String getTelDirCsvHeader() {
        String value = props.getProperty("TelDirCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに TelDirCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * LineListのCsvのHeaderを取得
     * @return LineListCsvのHeader
     */
    public String getLineListCsvHeader() {
        String value = props.getProperty("LineListCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに LineListCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * ManageのCsvのHeaderを取得
     * @return ManageのHeader
     */
    public String getManageHeader() {
        String value = props.getProperty("ManageCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに ManageCsvHeader の定義がありません。");
        }
        return value;
    }
    /**
     * VoiceLoggerのCsvのHeaderを取得
     * @return VoiceLoggerのHeader
     */
    public String getVoiceCsvHeader() {
        String value = props.getProperty("VoiceCsvHeader");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに VoiceCsvHeader の定義がありません。");
        }
        return value;
    }

    /**
     * VoiceLoggerのテーブル名を取得
     * @return VoiceLoggerのテーブル名
     */
    public String getVoiceTableName() {
        String value = props.getProperty("VoiceTableName");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに VoiceTableName の定義がありません。");
        }
        return value;
    }

    // ALL TABLE EXP IMP
    /**
     * 一括出力用対象テーブル名一覧取得
     * @return 一括出力対象テーブル
     */
    public String getAllTableName() {
        String value = props.getProperty("AllTable");
        if (value == null || value.equals("")) {
            throw new BatRuntimeException(
                    "environment.propertiesに AllTable の定義がありません。");
        }
        return value;
    }

}

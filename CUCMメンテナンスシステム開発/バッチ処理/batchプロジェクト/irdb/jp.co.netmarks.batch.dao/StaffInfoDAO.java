package jp.co.netmarks.batch.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ksc.batch.util.NewUserFileOutput;
import jp.co.netmarks.batch.persistence.LoadStaffMapper;
import jp.co.netmarks.util.ErrorMail;

/**
 * <p>Title: StaffInfoDAO.java</p>
  * <pre>
  * 人事情報取込み関連テーブル操作DAOクラス
  * </pre>
* @Time:2023/06/27
* @author Yao KaiKyou
 */
@Component
public class StaffInfoDAO {

	/* ログ出力クラス */
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private LoadStaffMapper lsm;

    @Autowired
    private ErrorMail errMail;

    private List<Map<String,Object>> newEmployeeRecords;

	@Autowired
	private Properties properties;

	// *****************
	// CSV処理
	// *****************

	/**
	 * Biz系テーブルのCSV取込を行います
	 *
	 * @param records   データ
	 * @param tableName テーブル名
	 * @param header    配列
	 * @throws SQLException
	 */
	public void insertBizTableAll(Map<String, Object>[] records, String tableName, String[] header)
			throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		if (records != null) {
			try {
				for (int i = 0; i < records.length; i++) {
					StringBuffer recordLog = new StringBuffer();
					for (int j = 0; j < header.length; j++) {
						if (j == 0) {
							recordLog.append(header[j] + ":" + records[i].get(header[j]));
						} else {
							recordLog.append(", " + header[j] + ":" + records[i].get(header[j]));
							System.out.println(header[j] + ":" + records[i].get(header[j]));
						}

						param.put(header[j], records[i].get(header[j]));
					}

					if (tableName.equals("TMP_AD")) {
						if (param.get("LAST_NM") == null)
							param.put("LAST_NM", param.get("LOGIN_NM"));
						if (param.get("FIRST_NM") == null)
							param.put("FIRST_NM", param.get("LOGIN_NM"));
						lsm.insertBizAD(param);
					} else if (tableName.equals("TMP_INTEGRATEDID_DEPARTMENT")) {
						lsm.insertBizDepartment(param);
					} else if (tableName.equals("TMP_INTEGRATEDID_EMPLOYEE")) {
						lsm.insertBizEmployee(param);
					} else if (tableName.equals("TMP_INTEGRATEDID_ORGANIZATION")) {
						lsm.insertBizOrganization(param);
					} else if (tableName.equals("BIZ_SHIFT")) {
						lsm.insertBizShift(param);
					}
				}
			} catch (Exception e) {
				log.warn("BIZテーブル[" + tableName + "]登録エラー");
				throw e;
			}
		}
	}

	/**
	*BizOrganizationの削除
	*@throws SQLException
	*/
	public void deleteBizOrganization() throws SQLException {
		try {
			lsm.deleteDumOrganization();
		} catch (Exception e) {
			log.warn("BIZ_ORGANIZATION 削除エラー:DUM_ORGANIZATION");
			throw e;
		}
	}

	/**
	*BizDepartmentの削除
	*@throws SQLException
	*/
	public void deleteBizDepartment() throws SQLException {
		try {
			lsm.deleteDumDepartment();
		} catch (Exception e) {
			log.warn("BIZ_DEPARTMENT 削除エラー:DUM_DEPARTMENT");
			throw e;
		}
	}

    /**
     * BizEmployeeの削除
     * @throws SQLException
     */
    public void deleteBizEmployee() throws SQLException {
        try {
            lsm.deleteDumEmployee();
        } catch (Exception e) {
            log.warn("BIZ_EMPLOYEE 削除 エラー:DUM_EMPLOYEE");
            throw e;
        }
    }

    // ************************************************
    // 店部課 追加
    // ************************************************
    /**
     * 店部課追加処理
     * @return 追加件数
     * @throws SQLException
     */
    public int insertMSection() throws SQLException {
        int ret = 0;
        try {
            List<Map<String,Object>> records = lsm.selNotExistMSection();
            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {

                    Map<String,Object> param = new HashMap<String, Object>();
                    String companyCode = records.get(i).get("company_cd").toString();
                    System.out.println(companyCode);
                    String departmentCode = records.get(i).get("department_cd").toString();
                    System.out.println(departmentCode);
                    System.out.println("123456mst");
                    param.put("companyCode",companyCode);
                    param.put("departmentCode",departmentCode);

                    int insCnt = lsm.selinsMSection(param);

                    if (insCnt == 0) {
                    	// biz_organizationに、対応する組織コードが存在しなかったとき
                        log.error("店部課マスタ[M_SECTION]BIZ_ORGANIZATION エラー:insertMSection()");
                    	log.error("company_cd = " + companyCode + ", company_cd = " + departmentCode);
                    	throw new SQLException();
                    } else {
	                    ret = ret + 1;
                    }
                }
            }
        } catch (SQLException se) {
            log.warn("店部課マスタ[M_SECTION]登録 エラー＠insertMSection()");
            throw se;
        }
        return ret;
    }

// ************************************************
    // 店部課 更新
    // ************************************************
    /**
     * 店部課更新処理
     * @return 件数
     * @throws SQLException
     */
    public int updateMSection() throws SQLException {
        int ret = 0;
        try {
        	List<Map<String,Object>> records = lsm.selExistMSection();

            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {

                    Map<String,Object> param = new HashMap<String, Object>();
                    String companyCode = records.get(i).get("company_cd").toString();
                    String departmentCode = records.get(i).get("department_cd").toString();
                    param.put("companyCode",companyCode);
                    param.put("departmentCode",departmentCode);

                    Map<String,Object> resultMap = lsm.selDiffMSection(param);

                    if(resultMap != null){
                        if (resultMap.get("depprord") == null) {
                        	// biz_organizationに、対応する組織コードが存在しなかったとき
                            log.error("店部課マスタ[M_SECTION]BIZ_ORGANIZATION エラー:updateMSection()");
                        	log.error("company_cd = " + companyCode + ", department_cd = " + departmentCode);
                        	throw new SQLException();
                        } else {
                        	param.put("department_nm", resultMap.get("deptnm"));
                        	param.put("parent_department_cd", resultMap.get("pdeptid"));
                        	param.put("organization_cd", resultMap.get("orgcd"));
                        	param.put("print_order", resultMap.get("depprord"));

    	                    int updateCnt = lsm.updateMSection(param);
    	                    if(updateCnt==0){
    	                    	log.error("店部課マスタ[M_SECTION]UPDATE 0件 エラー:updateMSection()");
                            	throw new SQLException();
    	                    }
                            ret = ret + updateCnt;
                        }
                    }
                }
            }
        } catch (SQLException se) {
            log.warn("店部課マスタ[M_SECTION]更新 エラー＠updateMSection()");
            throw se;
        }
        return ret;
    }

    // ************************************************
    // 退社 ※ 正社員以外も消す（FULLTIME_EMP：1,2以外)
    // ************************************************
    /**
     * 退社処理
     * @return 退社人数
     * @throws SQLException
     */
    public int retireAppUser() throws SQLException {
        int ret = 0;

        try {
        	List<Map<String,Object>> records = lsm.selRetireUsers();

            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {

                    Map<String,Object> param = new HashMap<String, Object>();
                    String appUserId = records.get(i).get("user_id").toString();
                    param.put("user_id",Integer.parseInt(appUserId));

                    // APP_USER
                    int updCntDelEmp = lsm.updDelEmpAppUser(param);
                    if(updCntDelEmp==0){
                    	log.error("ユーザマスタ[APP_USER]UPDATE 0件 エラー:retireEmployee");
                    	throw new SQLException();
                    }

                    // R_CUCM_USER_PHONE
                    updCntDelEmp = lsm.updDelEmpRUserPhone(param);


                    // PhoneOwner対応
                    List<Map<String,Object>> phoneList = lsm.delUserToPhone(param);
                    for (int j=0;j<phoneList.size();j++){
		                param.put("phoneId", phoneList.get(j).get("phoneid"));
		                Map<String,Object> newOwner = lsm.delUserNewOwnerPhone(param);
		                if(newOwner != null){
		                	String newOner = newOwner.get("cucm_login_id").toString();
		                	param.put("newOwner",Integer.parseInt(newOner));

			                updCntDelEmp = lsm.updDelEmpPhoneOwner(param);
		                }else{
			                param.put("newOwner", null);
			                updCntDelEmp = lsm.updDelEmpPhoneOwner(param);
		                }
                    }
                    ret = ret + 1;
                }
            }
        } catch (SQLException se) {
            log.warn("退社 エラー＠retireEmployee()");
            throw se;
        }
        return ret;
    }

 // ************************************************
 // 社員　追加
 // ************************************************
 /**
  * 社員追加
  * @return 件数
  * @throws SQLException
  */
    public int additionAppUser() throws SQLException {
        int ret = 0;
        boolean error = false; // 取り込みエラーが存在するかどうか
        String errorlog = null;

        try {
            newEmployeeRecords = lsm.selAdditionUsers();
            System.out.println("cdcd----newEmployeeRecords-------"+newEmployeeRecords);
            System.out.println("新入社員データの処理直前のデータ状況:");
            for (Map<String, Object> record : newEmployeeRecords) {
                String employeeCode = (String) record.get("employee_cd");
                System.out.println("社員コード: " + employeeCode);
                // 必要なデータフィールドを追加して表示します
            }

            if (newEmployeeRecords != null && newEmployeeRecords.size() > 0) {
                List<String> employeeIds = new ArrayList<>();

                for (int i = 0; i < newEmployeeRecords.size(); i++) {
                    Map<String,Object> param = new HashMap<String, Object>();
                    String employee_cd = newEmployeeRecords.get(i).get("employee_cd").toString();
                    param.put("employee_cd", employee_cd);

                    Map<String,Object> record = lsm.selAdditionUserDetail(param);

                    // ADに対応するユーザーが存在しなかった場合を考慮
                    if ((record != null) && (record.get("fulltime_employee") != null)) {
                        // 例: データベースへの挿入直前に変数の値を表示
                        System.out.println("挿入前の変数の値: " + employee_cd);
                        log.debug("挿入前の変数の値: " + employee_cd);

                        System.out.println("param: " + param);
                        System.out.println("挿入前の変数の値: " + employee_cd);
                        log.debug("挿入前の変数の値: " + employee_cd);

                        System.out.println("param: " + param);
                        int addUserCnt = lsm.insAdditionUserDetail(param);
                        if(addUserCnt == 0) {
                            log.error("ユーザマスタ[APP_USER]Insert 0件 エラー:additionEmployee");
                            throw new SQLException();
                        }
                        log.info("新入社員 employeeCode : " + employee_cd);
                        ret = ret + 1;

                        // ユーザファイル出力のための社員コードをリストに追加
                        employeeIds.add(employee_cd);
                    } else {
                        errorlog = "ユーザマスタ[APP_USER]AD存在無 エラー:additionEmployee ";
                        errorlog += "employee_code=" + employee_cd;
                        log.error(errorlog);
                        error = true;
                    }
                }

                try {
                    NewUserFileOutput newUserFileOutput = new NewUserFileOutput(properties);
                    newUserFileOutput.newUserFileOut(this, employeeIds);
                } catch (Exception e) {
                    log.error("新規ユーザファイル出力エラー: " + e.getMessage());
                    throw new SQLException();
                }
            }

            // 取り込みエラーがあった場合には、メール送信
            if (error) {
                errMail.sendErrorMail(errorlog);
            }
        } catch (SQLException se) {
            log.warn("社員[APP_USER]登録 エラー＠insertAppUser()");
            throw se;
        }

        return ret;
    }

 // ************************************************
    // 社員　更新
    // ************************************************
    /**
     * 社員更新処理
     * @return 件数
     * @throws SQLException
     */
    public int updateAppUser() throws SQLException {
        int ret = 0;
        boolean error = false; // 取り込みエラーが存在するかどうか
        String errorlog = null;

        try {
        	List<Map<String,Object>> records = lsm.selExistAppUser();

            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {

                    Map<String,Object> param = new HashMap<String, Object>();
                    String employeeCode = records.get(i).get("employee_cd").toString();
                    param.put("employeeCode",employeeCode);

                    Map<String,Object> resultMap = lsm.selDiffAppUser(param);

                    if(resultMap != null){
                        if (resultMap.get("bzfullemp") == null) {
                        	// ADに対応するユーザーが存在しなかった場合を考慮
                        	error = true;
                        	errorlog = "ユーザマスタ[APP_USER]AD存在無 エラー:updateAppUser ";
                        	errorlog += "employee_code=" + employeeCode;
                        	log.error(errorlog);
                        } else {
                        	param.put("fulltime_employee", resultMap.get("bzfullemp"));
                        	param.put("user_nm_kanji", resultMap.get("bzkanji"));
                        	param.put("user_nm_kana", resultMap.get("bzkana"));
                        	param.put("birthday", resultMap.get("bzbirth"));
                        	param.put("first_nm", resultMap.get("bzfirstnm"));
                        	param.put("last_nm", resultMap.get("bzlastnm"));
                        	param.put("department", resultMap.get("bzdepartment"));

    	                    int updateCnt = lsm.updateAppUser(param);
    	                    if(updateCnt==0){
    	                    	log.error("ユーザマスタ[APP_USER]UPDATE0件 エラー:updateAppUser");
                            	throw new SQLException();
    	                    }
    	                    int updateTrnCnt = lsm.updateTrnPhone(param);
    	                    if(updateTrnCnt==0){
    	                    	log.error("trn_phone[APP_USER]UPDATE0件 エラー:updateTrnPhone");
                            	throw new SQLException();
    	                    }
                            ret = ret + updateCnt;
                        }
                    }
                }
            }
            // 取り込みエラーがあった場合には、メール送信
            if (error) {
            	errMail.sendErrorMail(errorlog);
            }
        } catch (SQLException se) {
            log.warn("社員[APP_USER]更新 エラー＠updateAppUser()");
            throw se;
        }
        return ret;
    }

    // ************************************************
    // 所属追加 ※入社、着任
    // ************************************************
    /**
     * 所属追加 ※入社、着任
     * @return 件数
     * @throws SQLException
     */
    public int additionRUserSection() throws SQLException {
        int ret = 0;
        boolean error = false; // 取り込みエラーが存在するかどうか
        StringBuffer errorlogbuf = new StringBuffer();
        String errorlog = null;
        try {

        	List<Map<String,Object>> records = lsm.selAdditionUserSection();

            if (records != null && records.size() > 0) {

                for (int i = 0; i < records.size(); i++) {
                    Map<String,Object> param = new HashMap<String, Object>();
                    String employeeCode = records.get(i).get("employee_cd").toString();
                    String companyCode = records.get(i).get("company_cd").toString();
                    String departmentCode = records.get(i).get("department_cd").toString();
                    param.put("employeeCode",employeeCode);
                    param.put("companyCode",companyCode);
                    param.put("departmentCode",departmentCode);
                    System.out.println("employeeCode-----existSec---#"+employeeCode);
                    System.out.println("companyCode-----existSec---#"+companyCode);
                    System.out.println("departmentCode-----existSec---#"+departmentCode);



                    // 存在する店部課かどうかチェック
                    Map<String,Object> existSec = lsm.selExistDepartment(param);
                    System.out.println("okok-----existSec---#"+existSec);
                    if (existSec != null) {
	                    // 新しい所属なら追加
                    	// 追加対象情報の取得
	                    Map<String,Object>[] record = lsm.selAdditionUserSectionDetail(param);
	                    System.out.println("okok--------#"+record);
	                    if (record != null) {
	                    	int existRSec = lsm.selExistUserSection(param);
	                    	if(existRSec == 0){
			                    int addUserCnt = lsm.insAdditionUserSection(param);
			                    if(addUserCnt==0){
			                    	log.error("ユーザマスタ[APP_USER]Insert 0件 エラー:additionEmployee");
			                    	throw new SQLException();
			                    }
			                    ret = ret + 1;
	                    	}
		                } else {
	                    	errorlog = "[R_User_Section]追加 エラー : BIZ_EMPLOYEEとBIZ_ORGANIZATIONの不整合のため、社員の所属が追加できませんでした。";
	                    	errorlog += "employee_code=" + employeeCode + ", company_code=" + companyCode + ", department_code=" + departmentCode;
	                    	log.error(errorlog);
	                       	errorlogbuf.append("    ");
	                    	errorlogbuf.append(errorlog);
	                    	errorlogbuf.append("\\n");
	                     	error = true;
		                }
                    } else {
                    	errorlog = "[R_User_Section]追加 エラー : BIZ_EMPLOYEEの所属が店部課マスタに存在しないため、社員の所属が追加できませんでした。";
                    	errorlog += "employee_code=" + employeeCode + ", company_code=" + ", department_code=" + departmentCode;
                    	log.error(errorlog);
                       	errorlogbuf.append("    ");
                    	errorlogbuf.append(errorlog);
                    	errorlogbuf.append("\\n");
                     	error = true;
                    }
                }
            }

            // 取り込みエラーがあった場合には、メール送信
            if (error) {
            	//errMail.sendErrorMail(new String(errorlogbuf));
            }

        } catch (SQLException se) {
            log.warn("所属[R_USER_SECTION]登録 エラー＠insertRUserSetion()");
            throw se;
        }
        return ret;
    }

  // ************************************************
    // 所属　更新
    // ************************************************
    /**
     * 所属　更新
     * @return 件数
     * @throws SQLException
     */
    public int updateRUserSetion() throws SQLException {
        int ret = 0;
        try {

        	List<Map<String,Object>> records = lsm.selExistDeptChk();

            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {

                    Map<String,Object> param = new HashMap<String, Object>();
                    String employeeCode = records.get(i).get("employee_cd").toString();
                    String companyCode = records.get(i).get("company_cd").toString();
                    String departmentCode = records.get(i).get("department_cd").toString();

                    param.put("employeeCode",employeeCode);
                    param.put("companyCode",companyCode);
                    param.put("departmentCode",departmentCode);

                    Map<String,Object> resultMap = lsm.selDiffUserSection(param);

                    if(resultMap != null){
                    	param.put("section_name", resultMap.get("bizdeptnm"));
                    	param.put("print_order", resultMap.get("bizprintord"));
                    	Map<String,Object> uIdResultMap = lsm.selTrnUser(param);
                    	 if(uIdResultMap != null){
                    		 param.put("user_id", resultMap.get("user_id"));
                    		 Map<String,Object> sectionIdResultMap = lsm.selMstSection(param);
                    		 if(sectionIdResultMap != null) {
                    			 param.put("section_id", sectionIdResultMap.get("section_id"));
                    		 }
                    	 }

	                    int updateCnt = lsm.updateUserSection(param);
	                    if(updateCnt==0){
	                    	log.error("ユーザマスタ[R_USER_SECTION]UPDATE0件 エラー:updateRUserSetion");
                        	throw new SQLException();
	                    }
                        ret = ret + updateCnt;
                    }
                }
            }
        } catch (SQLException se) {
            log.warn("所属[R_USER_SECTION]更新 エラー＠updateRUserSetion()");
            throw se;
        }
        return ret;
    }

    // ************************************************
    // 異動　※転出元
    // ************************************************
    /**
     * 異動　※転出元
     * @return 件数
     * @throws SQLException
     */
    public int changePersonnel() throws SQLException {
        int ret = 0;
        try {
        	List<Map<String,Object>> records = lsm.selChangePersonnel();

            if (records != null && records.size() > 0) {
                for (int i = 0; i < records.size(); i++) {

                    Map<String,Object> param = new HashMap<String, Object>();
                    String appUserId = records.get(i).get("user_id").toString();
                    String bizEmployeeId = records.get(i).get("biz_employee_id").toString();
                    String sectionId = records.get(i).get("section_id").toString();

                    param.put("appUserId",Integer.parseInt(appUserId));
                    param.put("sectionId",sectionId);
                    param.put("bizEmployeeId",bizEmployeeId);

                    // 転出元で電話機が紐付いていたかどうかチェック
                    int phoneCnt = lsm.selPersonnelUsedPhone(param);

                    // R_USER_SECTION
                    if ( phoneCnt == 0) {
                    	// 転出元に紐付いた電話機がない場合
                        int deleteCnt = lsm.deleteUserSection(param);
	                    if(deleteCnt==0){
	                    	log.error("ユーザマスタ[R_USER_SECTION]Delete 0件 エラー:personnelChanges");
                        	throw new SQLException();
	                    }
                    } else {
                        // 既定では削除予約フラグのみ立て、転出元でも画面表示がされるようにする
                        int updateCnt = lsm.updDelUserSection(param);
	                    if(updateCnt==0){
	                    	log.error("ユーザマスタ[R_USER_SECTION]UPDATE 0件 エラー:personnelChanges");
                        	throw new SQLException();
	                    }
                    }
                    ret = ret + 1;
                }
            }
        } catch (SQLException se) {
            log.warn("異動 エラー＠personnelChanges()");
            throw se;
        }
        return ret;
    }

    // ************************************************
    // 退社リスト出力
    // ************************************************
    /**
     * 退社リスト出力
     * @return 退社リスト
     * @throws SQLException
     */
    public Map<String,Object>[] selRetireUser() throws SQLException {
    	Map<String,Object>[] records;
    	records = lsm.selAllRetireUserList();
        return records;
    }

    // ************************************************
    // 入社リスト出力
    // ************************************************
    /**
     * 入社リスト出力
     * @param employeeIds 従業員IDのリスト
     * @return 入社リスト
     * @throws SQLException
     */
    public Map<String, Object> selAllNewUserList(String employeeId) throws SQLException {
        try {
        	Map<String, Object>   newUser = lsm.selAllAddUserList(employeeId);
            return newUser;
        } catch (Exception e) {
            log.error("新入社員データの取得に失敗しました。", e);
            throw new SQLException("新入社員データの取得に失敗しました。", e);
        }
    }



}
package jp.co.netmarks.batch.persistence;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: LoadStaffMapper.java</p>
  * <pre>
  * 人事情報取込関連 マッパークラス
  * </pre>
* @Time:2023/06/28
* @author Yao KaiKyou
 */
public interface LoadStaffMapper {

	//vacuum
	/**
	 * 人事情報関連テーブルのVacuumを実行
	 * @return int
	 */
	int vacuum();

	// 情報取込
	/**
	 * BizShift削除
	 * @return 件数
	 */
	int deleteBizShift();
	/**
	 * BizAD削除
	 * @return 件数
	 */
	int deleteBizAD();
	/**
	 *  BizOrganization削除
	 * @return 件数
	 */
	int deleteBizOrganization();
	/**
	 *  BizDepartment削除
	 * @return 件数
	 */
	int deleteBizDepartment();
	/**
	 *  BizEmployee削除
	 * @return 件数
	 */
	int deleteBizEmployee();

	/**
	 * DumOrganization削除
	 * @return 件数
	 */
	int deleteDumOrganization();
	/**
	 * DumOrganization削除
	 * @return 件数
	 */
	int deleteDumDepartment();
	/**
	 * DumOrganization削除
	 * @return 件数
	 */
	int deleteDumEmployee();

	/**
	 * BizShift登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insertBizShift(Map<String, Object> parameterValues);
	/**
	 * BizAD登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insertBizAD(Map<String, Object> parameterValues);
	/**
	 * BizOrganization登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insertBizOrganization(Map<String, Object> parameterValues);
	/**
	 * BizDepartment登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insertBizDepartment(Map<String, Object> parameterValues);
	/**
	 * BizEmployee登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insertBizEmployee(Map<String, Object> parameterValues);

	// 閾値
	/**
	 * 閾値取得
	 * @return 取得した閾値
	 */
	Map<String, Object> selectThreshold();
	/**
	 * 入社人数取得
	 * @return 取得した入社人数
	 */
	int selectEnterCount();
	/**
	 * 登録社員情報取得
	 * @return 取得した登録社員情報
	 */
	List<Map<String, Object>> selectEnterEmployee();
	/**
	 * 退社人数取得
	 * @return 取得した退社人数
	 */
	int selectRetireCount();
	/**
	 * 退社社員情報取得
	 * @return 取得した退社社員情報
	 */
	List<Map<String, Object>> selectRetireEmployee();
	/**
	 * 変更社員人数取得
	 * @return 取得した変更社員人数
	 */
	int selectChangeCount();
	/**
	 * 変更社員人数情報取得
	 * @return 取得した変更社員情報
	 */
	List<Map<String, Object>> selectChangeEmployee();

	// M_SECTION
	/**
	 * 未登録MSection取得
	 * @return 取得したMSection
	 */
	List<Map<String, Object>> selNotExistMSection();
	/**
	 * 登録済MSection取得
	 * @return 取得したMSection
	 */
	List<Map<String, Object>> selExistMSection();
	/**
	 * 登録情報と相違あるMSectionの取得
	 * @param parameterValues
	 * @return 取得したMSection情報
	 */
	Map<String,Object> selDiffMSection(Map<String,Object>parameterValues);
	/**
	 * 新規登録MSection
	 * @param parameterValues
	 * @return 件数
	 */
	int selinsMSection(Map<String,Object> parameterValues);
	/**
	 * MSectionの更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updateMSection(Map<String,Object> parameterValues);

// APP_USER
	/**
	 * 退社社員取得
	 * @return 取得した退社社員情報
	 */
	List<Map<String,Object>> selRetireUsers();
	/**
	 * 退社予約社員更新（AppUser)
	 * @param parameterValues
	 * @return 件数
	 */
	int updDelEmpAppUser(Map<String,Object> parameterValues);
	/**
	 * 退社予約社員更新（UserSection)
	 * @param parameterValues
	 * @return 件数
	 */
	int updDelEmpRUserSection(Map<String,Object> parameterValues);
	/**
	 * 退社予約社員更新（UserPhone)
	 * @param parameterValues
	 * @return 件数
	 */
	int updDelEmpRUserPhone(Map<String,Object> parameterValues);
	/**
	 * 退社予約社員更新（TelDir)
	 * @param parameterValues
	 * @return 件数
	 */
	int updDelEmpTelDir(Map<String,Object> parameterValues);

	/**
	 * 入社社員の取得
	 * @return 入社社員
	 */
	List<Map<String,Object>> selAdditionUsers();
	/**
	 * 入社社員情報の取得
	 * @param parameterValues
	 * @return 入社社員情報
	 */
	Map<String,Object> selAdditionUserDetail(Map<String,Object> parameterValues);
	/**
	 * 入社社員の登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insAdditionUserDetail(Map<String,Object> parameterValues);

	/**
	 * 登録済みユーザーの取得
	 * @return 取得したユーザー
	 */
	List<Map<String,Object>> selExistAppUser();
	/**
	 * 登録済みユーザーと相違あるユーザーの取得
	 * @param parameterValues
	 * @return 相違ユーザー情報
	 */
	Map<String,Object> selDiffAppUser(Map<String,Object>parameterValues);
	/**
	 * trn_phoneのupdate_status更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updateTrnPhone(Map<String,Object> parameterValues);

	/**
	 * ユーザー更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updateAppUser(Map<String,Object> parameterValues);

	//R_USER_SECTION
	/**
	 * 登録ユーザー情報取得
	 * @return 登録ユーザー情報
	 */
	List<Map<String,Object>> selAdditionUserSection();
	/**
	 * 登録済部署取得
	 * @param parameterValues
	 * @return 登録済部署
	 */
	Map<String,Object> selExistDepartment(Map<String,Object> parameterValues);
	/**
	 * 登録済ユーザーの部署件数を取得
	 * @param parameterValues
	 * @return ユーザー部署件数
	 */
	int selExistUserSection(Map<String,Object> parameterValues);
	/**
	 * 登録ユーザーの部署情報取得
	 * @param parameterValues
	 * @return 登録ユーザーの部署情報
	 */
	Map<String,Object>[] selAdditionUserSectionDetail(Map<String,Object> parameterValues);
	/**
	 * 登録ユーザーの部署情報を登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insAdditionUserSection(Map<String,Object> parameterValues);

	/**
	 * 登録済課情報取得
	 * @return 課情報
	 */
	List<Map<String,Object>> selExistDeptChk();
	/**
	 * 登録情報と相違あるSection情報取得
	 * @param parameterValues
	 * @return 相違あるSection情報
	 */
	Map<String,Object> selDiffUserSection(Map<String,Object>parameterValues);

	/**
	 * MST_店部課「mst_section」テーブルを更新する前に店部課IDを取得する
	 * @param parameterValues
	 * @return section_id
	 */
	Map<String,Object> selMstSection(Map<String,Object>parameterValues);

	/**
	 * MST_店部課「mst_section」テーブルを更新する前にuser_idを取得する
	 * @param parameterValues
	 * @return user_id
	 */
	Map<String,Object> selTrnUser(Map<String,Object>parameterValues);


	/**
	 * Section更新処理
	 * @param parameterValues
	 * @return 件数
	 */
	int updateUserSection(Map<String,Object> parameterValues);

	/**
	 * 人事情報の更新対象取得
	 * @return 更新対象
	 */
	List<Map<String,Object>> selChangePersonnel();
	/**
	 * ユーザーが使用している電話機の数を取得
	 * @param parameterValues
	 * @return 電話機の数
	 */
	int selPersonnelUsedPhone(Map<String,Object> parameterValues);
	/**
	 * ユーザーの所属を削除
	 * @param parameterValues
	 * @return 件数
	 */
	int deleteUserSection(Map<String,Object> parameterValues);
	/**
	 * ユーザーの所属を更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updDelUserSection(Map<String,Object> parameterValues);

	//拠点統廃合
	/**
	 * 統廃合情報の取得
	 * @return 統廃合情報
	 */
	List<Map<String,Object>> selShiftOrganization();
	/**
	 * 異動前部署からのユーザー削除対象取得
	 * @param parameterValues
	 * @return 削除対象
	 */
	List<Map<String,Object>> selDelAffiliationUser(Map<String,Object> parameterValues);
	/**
	 * ユーザー所属部署件数
	 * @param parameterValues
	 * @return 件数
	 */
	int selUserSectionCnt(Map<String,Object> parameterValues);
	/**
	 * ユーザー所属情報の更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updChgPersonnelSection(Map<String,Object> parameterValues);
	/**
	 * ユーザー所持電話機の情報更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updChgPersonnelPhone(Map<String,Object> parameterValues);

/**
	 * 異動前電話機情報の削除対象取得
	 * @param parameterValues
	 * @return 削除対象
	 */
	List<Map<String,Object>> selDelAffiliationPhone(Map<String,Object> parameterValues);
	/**
	 * 異動後部署CSS名取得
	 * @param parameterValues
	 * @return 新CSS名
	 */
	List<Map<String,Object>> selNewCssNm(Map<String,Object> parameterValues);
	/**
	 * 電話機のCSSを更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updCssOrgPhone(Map<String,Object> parameterValues);
	/**
	 * 異動後部署のPickUp名を取得
	 * @param parameterValues
	 * @return 新PickUp名
	 */
	List<Map<String,Object>> selNewPickUpNm(Map<String,Object> parameterValues);
	/**
	 * PickUpNameの更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updPickUpLine(Map<String,Object> parameterValues);
	/**
	 * 親店部課情報取得
	 * @param parameterValues
	 * @return 親店部課情報
	 */
	List<Map<String,Object>> selParentSectionId(Map<String,Object> parameterValues);
	/**
	 * 組織情報更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updChgAssociation(Map<String,Object> parameterValues);

	//退社者リスト
	/**
	 * 退社社員一覧取得
	 * @return 退社社員一覧
	 */
	Map<String,Object>[] selAllRetireUserList();

	//入社者リスト
	/**
	 * 入社社員一覧取得
	 * @return 入社社員一覧
	 */
	Map<String, Object> selAllAddUserList(String employeeId);

	//退社者リスト
	/**
	 * 退社社員一覧取得
	 * @return 退社社員一覧
	 */
	Map<String,Object>[] selAllNewUserList(List<String> employeeIds);


	//削除ユーザ使用電話機オーナー更新
	/**
	 * 削除ユーザ使用電話機数
	 * @param parameterValues
	 * @return 件数
	 */
	int updDelEmpPhoneOwner(Map<String,Object> parameterValues);
	/**
	 * 削除ユーザ使用電話機取得
	 * @param parameterValues
	 * @return 電話機一覧
	 */
	List<Map<String,Object>> delUserToPhone(Map<String,Object> parameterValues);
	/**
	 * 削除ユーザ使用電話機オーナー更新
	 * @param parameterValues
	 * @return 件数
	 */
	Map<String,Object> delUserNewOwnerPhone(Map<String,Object> parameterValues);

}


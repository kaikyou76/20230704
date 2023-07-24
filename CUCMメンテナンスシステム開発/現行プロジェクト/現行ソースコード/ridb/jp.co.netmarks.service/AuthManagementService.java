/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementService.java
 *
 * @date 2013/09/10
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.netmarks.model.AuthManagementModel;
import jp.co.netmarks.model.AuthManagementResultModel;
import jp.co.netmarks.model.AuthManagementUpdateModel;
import jp.co.netmarks.model.entity.UserEntity;
import jp.co.netmarks.persistence.AuthManagementMapper;
import jp.co.netmarks.persistence.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 権限管理用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/10 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/10
 */
@Service
public class AuthManagementService extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AuthManagementService.class);

	@Autowired
    private Properties properties;

	@Autowired
	private AuthManagementMapper authManagementMapper;

	/** ##### エンティティ関連 ##### */
	@Autowired
	private UserMapper userMapper;

	/** ##### トランザクション ##### */
	@Autowired
	private PlatformTransactionManager txManager;


	/** 検索：ソート対象カラム */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("employeeId","APP_USER.BIZ_EMPLOYEE_ID");
		sortMap.put("kanjiUserName","APP_USER.NAME_KANJI");
		sortMap.put("authName","APP_USER.USER_ROLE");
		sortMap.put("branchUserName","V_ORGANIZATION_LEVEL.BRANCH_NAME");
		sortMap.put("sectionUserName","R_USER_SECTION.SECTION_NAME");
	}

	/** 検索：デフォルトソート */
	private final String DEFAULT_SORT =
			"APP_USER.APP_USER_ID ,"+
			"V_ORGANIZATION_LEVEL.CHILD_SECTION_ID";

	/**
	 * 管理権限付与者一覧の検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getAuthManagementTotal(AuthManagementModel params){

		return authManagementMapper.getAuthManagementListTotal(params);
	}

	/**
	 *
	 * 管理権限付与者一覧の検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<AuthManagementResultModel> getAuthManagement(AuthManagementModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}
		return authManagementMapper.getAuthManagementList(params);
	}

	/**
	 * 管理権限付与対象者一覧の検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getAuthManagementTargetTotal(AuthManagementModel params){


		return authManagementMapper.getAuthManagementTargetListTotal(params);
	}

	/**
	 *
	 * 管理権限付与対象者一覧の検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<AuthManagementResultModel> getAuthManagementTarget(AuthManagementModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + "||" + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		return authManagementMapper.getAuthManagementTargetList(params);
	}

	/**
	 *
	 * 権限削除、権限付与処理
	 *
	 * @param list 更新パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	public Map<String, String> updataUserRole(List<AuthManagementUpdateModel> list) throws Exception{

		Map<String, String> map = new HashMap<String, String>();

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 更新フラグ */
		boolean sucFlg = false;
		boolean eroFlg = false;

		/* エラーメッセージセット */
		StringBuffer errorUser  = new StringBuffer();

		/* チェックボックスにチェックがついていた項目分処理を行います。 */
		for(AuthManagementUpdateModel params : list){
			if(updateRole(params,timestamp)){
				sucFlg = true;
			} else {
				eroFlg = true;
				errorUser.append(params.getUserId());
				errorUser.append(",");
			}
		}

		/* 成功が存在した場合 */
		if(sucFlg || !eroFlg){
			map.put("sucsessMessage", properties.getProperty("update.success"));
		}

		String errMsg = "";
		/* エラーが存在した場合 */
		if(eroFlg){
			String errUserStr = errorUser.toString();
			errMsg = "【ユーザー】" + errUserStr.substring(0, errUserStr.length()-1);
			map.put("errorMessage", MessageFormat.format(properties.getProperty("auth.error.key"),errMsg));
		}

		return map;
	}

	/**
	 *
	 * 権限更新処理
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @return True:エラーなし False:エラーあり
	 * @throws Exception 例外処理
	 */
	@Transactional
	private boolean updateRole(AuthManagementUpdateModel params,Timestamp timestamp) throws Exception{

		int updateCnt = 0;

		/* トランザクションの取得 */
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;

		try{
			/* トランザクションスタート */
			ts = txManager.getTransaction(dtd);

			/* ロック処理 */
			lockedUser(params);

			updateCnt = userMapper.updateUserRole(setUserEntityRoleInfo(params.getUserId(),params.getAuthId(),timestamp));

			/* コミット */
			txManager.commit(ts);

		}catch(Exception e){
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			throw e;
		}

		return(updateCnt > 0) ? true:false;
	}

	/**
	 * ユーザロック処理
	 * @param params ロックパラメータ
	 * @throws Exception 例外処理
	 */
	private void lockedUser(AuthManagementUpdateModel params) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			/* ユーザー（新規） */
			map.put("APP_USER_ID", params.getUserId());
			locked("APP_USER", null , map);

		} catch (ExcludeException e) {}
	}

	/**
	 * Userテーブルモデルに値をセットします
	 *
	 * @param userId ユーザID
	 * @param userRole 権限
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserEntity setUserEntityRoleInfo(BigDecimal userId, String userRole, Timestamp timestamp){
		/* インスタン化 */
		UserEntity entity = new UserEntity();

		entity.setAppUserId(userId);							// ユーザーID
		entity.setUserRole(userRole);					        // 権限
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}

}
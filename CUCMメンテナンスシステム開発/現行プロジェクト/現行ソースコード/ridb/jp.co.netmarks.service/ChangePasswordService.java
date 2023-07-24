/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ChangePasswordService.java
 *
 * @date 2013/08/28
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.netmarks.model.ChangePasswordModel;
import jp.co.netmarks.model.entity.UserEntity;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.UserMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * パスワード変更用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/28 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/28
 */
@Service
public class ChangePasswordService extends BaseService {
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ChangePasswordService.class);

	@Autowired
	private AppCommonMapper appCommonMapper;

	/** ##### エンティティ関連 ##### */
	@Autowired
	private UserMapper userMapper;

	/**
	 * パスワード変更処理
	 *
	 * @param userId ユーザID
	 * @param loginId ユーザログインID
	 * @param params 更新パラメータ
	 * @return True：パスワード更新成功  False：パスワード更新失敗
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Boolean updateUserPassword(BigDecimal userId, String loginId,
			ChangePasswordModel params) throws Exception{

		int updateCnt;

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* ユーザロック処理 */
		lockedUser(userId);

		/* パスワード更新処理 */
		updateCnt = userMapper.updateloginPassword(setUserEntityLoginPasswordInfo(userId,params.getNewPassword(),timestamp));

		return ( updateCnt != 0) ? true : false;
	}

	/**
	 * ユーザロック処理
	 * @param userId ユーザID
	 * @throws Exception 例外処理
	 */
	private void lockedUser(BigDecimal userId) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();

		try{
			/* ユーザー（新規） */
			map.put("APP_USER_ID", userId);
			locked("APP_USER", null , map);

		} catch (ExcludeException e) {}
	}

	/**
	 * パスワードの整合性チェック
	 *
	 * @param loginId ログインID
	 * @param inputPassword 入力パスワード
	 * @return True：整合  False：不整合
	 */
	public Boolean checkPassword(String loginId, String inputPassword) {

		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("loginId", loginId);
		searchMap.put("password", inputPassword);
		/* ユーザー情報の取得 */
		Map<String, Object> resultMap = appCommonMapper.getUserInfo(searchMap);

		return (resultMap != null) ?  true : false;
	}

	/**
	 * Userテーブルモデルに値をセットします
	 *
	 * @param userId ユーザID
	 * @param newPassword 新規パスワード
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserEntity setUserEntityLoginPasswordInfo(BigDecimal userId, String newPassword, Timestamp timestamp){
		/* インスタンス化 */
		UserEntity entity = new UserEntity();

		entity.setAppUserId(userId);							// ユーザーID
		entity.setLoginPassword(newPassword);					// 新しいログインパスワード
		entity.setLstupdtpwd(timestamp);						// パスワード更新日
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}
}
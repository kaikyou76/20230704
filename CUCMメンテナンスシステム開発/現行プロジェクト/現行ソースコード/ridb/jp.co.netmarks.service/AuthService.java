/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthService.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import jp.co.ksc.service.BaseService;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.LoginUserModel;
import jp.co.netmarks.persistence.AppCommonMapper;

/**
 * <pre>
 * 認証用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/07 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/07
 */
@Service
public class AuthService extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AuthService.class);

	@Autowired
	private AppCommonMapper appCommonMapper;
	
	/**
	 * <pre>
	 * 認証を行う
	 *
	 * ・認証エラー時はNameNotFoundExceptionを返却する
	 * ・認証成功時は、LoginUserModelを返却する
	 * ・ログインユーザーに応じたRole（複数）をセットする
	 * </pre>
	 *
	 * @param loginId ログインID
	 * @return ユーザー情報
	 * @throws Exception 例外処理
	 */
	public LoginUserModel authenticate(String loginId) {
		return authenticate(loginId, null);
	}

	/**
	 * <pre>
	 * 認証を行う
	 *
	 * ・認証エラー時はNameNotFoundExceptionを返却する
	 * ・認証成功時は、LoginUserModelを返却する
	 * ・ログインユーザーに応じたRole（複数）をセットする
	 * </pre>
	 *
	 * @param loginId ログインID
	 * @param password パスワード
	 * @return ユーザー情報
	 * @throws Exception 例外処理
	 */
	public LoginUserModel authenticate(String loginId, String password) {

		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("loginId"  , loginId);
		searchMap.put("password" , password);
		searchMap.put("deleted"  , Constants.COM_FLG_OFF);

		/* ユーザー情報の取得 */
		Map<String, Object> resultMap = appCommonMapper.getUserInfo(searchMap);

		/* 認証 */
		if(resultMap == null || resultMap.get("userRole").equals(Constants.ROLE_DIV_NOTHING)){
			return null;
		}

		/* ROLEをセット */
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		if (resultMap.get("userRole").equals(Constants.ROLE_DIV_ADMIN)) {
			/* 管理者権限 */
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_ADMIN));
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_CHANGE));
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_USER));
		} else if (resultMap.get("userRole").equals(Constants.ROLE_DIV_CHANGE)) {
			/* 変更権限 */
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_CHANGE));
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_USER));
		} else if (resultMap.get("userRole").equals(Constants.ROLE_DIV_USER)) {
			/* 参照権限 */
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_USER));
		}

		/* ユーザー情報をセット */
		LoginUserModel user = new LoginUserModel(loginId, (String)resultMap.get("password"), roles);
		user.setKanjiUserName((String)resultMap.get("kanjiUserName"));		// 漢字名
		user.setKanaUserName((String)resultMap.get("kanaUserName"));		// カナ名
		user.setUserId(new BigDecimal((Integer)resultMap.get("userId")));	// ユーザーID
		user.setLoginId(loginId);											// ログインID
		user.setUserRole((String)resultMap.get("userRole"));				// 権限情報
		user.setLastPasswordUpdatetime((Timestamp)resultMap.get("lastPasswordUpdatetime"));	// パスワード更新日

		return user;
	}

}
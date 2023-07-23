/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LoginController.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;

import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jp.co.ksc.controller.BaseController;
import jp.co.netmarks.model.LoginUserModel;
import jp.co.netmarks.model.form.LoginForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.AuthService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <pre>
 * ログイン画面用コントローラクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/18
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AppCommonService appCommonService;
	
	@Autowired
	private Properties properties;

	/**
	 * 初期表示
	 * ※認証あり
	 *
	 * @param form LoginForm
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(LoginForm form, HttpServletRequest request) {
		
		form.setFocus("username");
		return "login";			
	}
	
	/**
	 * SSOログイン（Cookie）
	 *
	 * @param form LoginForm
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/ssoLogin", method=RequestMethod.GET)
	protected String loginView(LoginForm form, HttpServletRequest request) {
		/* Cookieの値を取得 */
		String loginId = getCookieLoginId(request);
		
		/* ログインIDを取得出来なかった場合はログイン画面に遷移する */
		if(StringUtils.isEmpty(loginId)){
			form.setFocus("username");
			return "login";			
		}
		
		/* 認証 */
		LoginUserModel model = authService.authenticate(loginId);
		if (model == null) {
			form.setFocus("username");
			return "login";			
		}
		
		/* ユーザ名をフォームにセット */
		form.setUsername(loginId);

		return authAfterDisposal(form, model);
	}


	/**
	 * ログイン
	 *
	 * @param form LoginForm
	 * @param result BindingResult
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String login(@Valid LoginForm form, BindingResult result) throws Exception {

		/* validation error */
		if (result.hasErrors()) return "login";

		/* 認証 */
		LoginUserModel model = authService.authenticate(form.getUsername(), form.getPassword());
		if (model == null) {
			addError(result, properties.getProperty("authenticate.error"));
            return "login";
		}

        return authAfterDisposal(form, model);
	}
	
	/**
	 * 承認後の処理
	 * @param form ログインフォーム
	 * @param model ログインユーザーフォーム
	 * @return 遷移先
	 */
	private String authAfterDisposal(LoginForm form, LoginUserModel model){
		
		/* 認証状態をセットし、リダイレクト */
		setAuthentication(form.getUsername(), model);
		
		/* リダイレクト先 */
		String redirect = "redirect:/userAndTel";
		
		/* パスワード更新日と今日日付を比較 */
		if(!appCommonService.checkPassword(model.getUserId(), properties.getProperty("password.expiration.date"))){
			/* 今日日付以前だった場合 */
			redirect = "redirect:/changePassword";
		}
		
		return redirect;
		
	}
	
	/**
	 * Cookieを取得
	 * 
	 * @param request HttpServletRequest
	 * @return ログインID
	 */
	private String getCookieLoginId(HttpServletRequest request){
		/* ログインID */
		String loginId = "";
		/* CookieValue */
		String cookieValue ="";
		/* Cookie名 */
		String cookieNm = properties.getProperty("cookie.name");
		
		/* Cookieを取得 */
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return "";
			
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieNm)) {
    			// Cookieの値を取得
				// Cookieは、会社コード(先頭3桁),社員コード(末尾7桁)で構成
				cookieValue = cookie.getValue();
				
				if(StringUtils.isEmpty(cookieValue)) break;
				loginId = cookieValue.substring(3);
			}
		}
		
		return loginId;
	}
}
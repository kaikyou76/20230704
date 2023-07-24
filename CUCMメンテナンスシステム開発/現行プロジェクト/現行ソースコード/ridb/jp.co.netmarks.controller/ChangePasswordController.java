/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ChangePasswordController.java
 *
 * @date 2013/08/28
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ksc.controller.BaseController;
import jp.co.netmarks.model.ChangePasswordModel;
import jp.co.netmarks.model.LoginUserModel;
import jp.co.netmarks.model.form.ChangePasswordForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.ChangePasswordService;


/**
 * <pre>
 * パスワード変更画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/28 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/28
 */
@Controller
@RequestMapping("/changePassword")
public class ChangePasswordController extends BaseController {

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
	private ChangePasswordService changePasswordService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form ChangePasswordForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(ChangePasswordForm form) throws Exception {

		/* フォーカスを設定 */
		form.setFocus("currentPassword");

		/* ログインユーザID取得 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserModel usermodel = (LoginUserModel)authentication.getDetails();
		BigDecimal userId = usermodel.getUserId();

		/* パスワード有効期限規定値を取得 */
		String expirationDate = properties.getProperty("password.expiration.date");

		/* パスワードの有効期限をチェック */
		if(!appCommonService.checkPassword(userId,expirationDate)){
			/* メッセージセット */
			form.setMessage(properties.getProperty("password.expiration.error"));
		}

		return "view/changePassword/changePassword";
	}

	/**
	 * パスワードを変更ボタン押下時
	 *
	 * @param form ChangePasswordForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	public ModelAndView updatePassword(@Valid ChangePasswordForm form, BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String message = null;

		/* バリデーションエラー */
		if(result.hasErrors()) {
			/* フォーカスを設定 */
			setFocus(form,result);
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 権限情報を取得 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUserModel usermodel = (LoginUserModel)authentication.getDetails();
		BigDecimal userId = usermodel.getUserId();
		String loginId = usermodel.getLoginId();

		/* 入力値検証 */
		inputCheck(loginId,form,result);
		if(result.hasErrors()) {
			/* フォーカスを設定 */
			setFocus(form,result);
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 入力パスワードパラメータをセット */
		ChangePasswordModel params = new ChangePasswordModel();
		BeanUtils.copyProperties(form, params);

		/* パスワード変更 */
		if(changePasswordService.updateUserPassword(userId, loginId, params)){
			message = properties.getProperty("updatepassword.success");
		} else {
			message = properties.getProperty("updatepassword.error");
		}

		/* メッセージをセット */
		form.setMessage(message);

		/* フォーカスを設定 */
		setFocus(form,result);

		return getJsonView(map);
	}

	/**
	 * フォーカス設定
	 *
	 * @param form ChangePasswordForm
	 * @param result BindingResult
	 */
	private void setFocus(ChangePasswordForm form, BindingResult result){
		String focus = "currentPassword";

		/* エラー箇所にフォーカスをあてる */
		if(result.getFieldError("currentPassword") != null){
			focus = "currentPassword";
		}else if(result.getFieldError("newPassword") != null){
			focus = "newPassword";
		}else if(result.getFieldError("newPasswordConf") != null) {
			focus = "newPasswordConf";
		}

		form.setFocus(focus);
	}

	/**
	 * 入力値検証
	 *
	 * @param loginId ログインID
	 * @param form ChangePasswordForm
	 * @param result BindingResult
	 * @return True : エラーなし  False : エラーあり
	 */
	private boolean inputCheck(String loginId, ChangePasswordForm form, BindingResult result) {

		boolean check = true;

		/* 現在のパスワードの整合性チェック */
		if(!changePasswordService.checkPassword(loginId, form.getCurrentPassword())){
			addError(result, "changePasswordForm","currentPassword",properties.getProperty("correct.error"), "現在のパスワード");
			check = false;
		}

		/* 新しいパスワードの設定可否チェック(現在のパスワード != 新しいパスワードであること) */
		if(changePasswordService.checkPassword(loginId, form.getNewPassword())){
			addError(result, "changePasswordForm","newPassword",properties.getProperty("newpassword.error"));
			check = false;
		}

		/* 新しいパスワード(確認用)の入力値チェック(新しいパスワード == 新しいパスワード(確認用)であること) */
		if(!form.getNewPassword().equals(form.getNewPasswordConf())){
			addError(result, "changePasswordForm","newPasswordConf",properties.getProperty("correct.error"), "新しいパスワード(確認用)");
			check = false;
		}

		return check;
	}

}
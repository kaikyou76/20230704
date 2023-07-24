/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelController.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.ksc.util.OptionsUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.AddTelLineUserUpdateModel;
import jp.co.netmarks.model.AddTelModel;
import jp.co.netmarks.model.form.AddTelForm;
import jp.co.netmarks.model.form.AddTelUpdateForm;
import jp.co.netmarks.service.AddTelLineUserService;
import jp.co.netmarks.service.AddTelService;
import jp.co.netmarks.service.AppCommonService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 電話機の検索と選択画面用コントローラークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
@Controller
@RequestMapping("/addTel")
public class AddTelController extends BaseController {

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
	private AddTelService addTelService;

	@Autowired
	private AddTelLineUserService addTelLineUserService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form AddTelForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	protected String index(AddTelForm form) throws Exception {

		/** プルダウンのリスト取得 **/

		/* 拠点リストをセット */
		form.setBranchList(
				OptionsUtil.addBlankHeadLVBList(
						appCommonService.getBranchList(),Constants.BRANCH_ALL_LABEL));

		/* 店部課リストをセット */
		form.setSectionList(
				OptionsUtil.addBlankHeadLVBList(null, Constants.SECTION_ALL_LABEL));

		/* 電話ステータスを取得 */
		form.setTelStatusList(
				OptionsUtil.addBlankHeadLVBList(
						OptionsUtil.convertToOptionsCollection(
								Constants.TEL_STATUS_LABEL, Constants.TEL_STATUS_VALUE), Constants.ALL_LABEL));

		return "dialog/addTel";
	}

	/**
	 * 検索処理
	 *
	 * @param form UserAndTelSearchForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView search(
			AddTelForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		AddTelModel params = new AddTelModel();
		BeanUtils.copyProperties(params, form);

		/* 検索条件をセット */
		setSearchConditions(params);

		/* 検索件数を取得 */
		map.put("total", addTelService.getTelAddTotal(params));

		/* 検索結果を取得 */
		map.put("rows", addTelService.getTelAddList(params));

		return getJsonView(map);
	}

	/**
	 * 電話機変更処理
	 *
	 * @param form AddTelUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/updateTel", method=RequestMethod.POST)
	public ModelAndView updateTel(
			AddTelUpdateForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* Form→Modelコピー処理 */
		AddTelLineUserUpdateModel params = setParams(form);

		/* 存在チェック */
		if(inputCheck(params , result, map, true)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 属性更新処理 */
		Map<String, String> messageMap =
			addTelLineUserService.updateTel(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

	/**
	 * 電話機追加処理
	 *
	 * @param form AddTelUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/telLinkingUser", method=RequestMethod.POST)
	public ModelAndView telLinkingUser(
			AddTelUpdateForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* Form→Modelコピー処理 */
		AddTelLineUserUpdateModel params = setParams(form);

		/* 存在チェック */
		if(inputCheck(params , result, map, false)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 属性更新処理 */
		Map<String, String> messageMap =
			addTelLineUserService.addTel(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

	/**
	 * 検索条件をセットします。
	 * ※店部課プルダウンの値から会社IDと店部課IDを取得します。
	 * 
	 * @param params 検索条件
	 * @return 検索条件
	 */
	private AddTelModel setSearchConditions(AddTelModel params){

		/* 店部課（ユーザー）を選択していた場合 */
		if(StringUtils.isNotEmpty(params.getAddTelCompanySectionId())){
			String[] sectionCompnayArray = params.getAddTelCompanySectionId().split(",");
			/* 会社ID */
			params.setAddTelCompanyId(sectionCompnayArray[0]);
			/* 店部課ID */
			params.setAddTelSectionId(sectionCompnayArray[1]);
		}
		return params;
	}

	/**
	 * 更新用のFormの値をModelにセットします。
	 * 
	 * @param form 電話機追加フォーム
	 * @return 電話機追加モデル
	 */
	private AddTelLineUserUpdateModel setParams(AddTelUpdateForm form){
		/* インスタンス化 */
		AddTelLineUserUpdateModel params = new AddTelLineUserUpdateModel();

		params.setUserId(new BigDecimal(form.getUserId()));			// ユーザーID
		params.setTelId(new BigDecimal(form.getTelId()));			// 電話ID
		params.setLineId(new BigDecimal(form.getLineId()));			// ラインID
		params.setSectionUserId(form.getSectionUserId());			// 店部課ID
		params.setCompanyUserId(form.getCompanyUserId());			// 会社ID
		params.setOrgUserId(new BigDecimal(form.getOrgUserId()));	// ユーザーID（変更元）
		params.setOrgTelId(new BigDecimal(form.getOrgTelId()));		// 電話ID（変更元）
		params.setOrgLineId(new BigDecimal(form.getOrgLineId()));	// ラインID（変更元）
		params.setOrgSectionUserId(form.getOrgSectionUserId());		// 店部課（変更元）
		params.setOrgCompanyUserId(form.getOrgCompanyUserId());		// 会社（変更元）
		
		return params;
	}

	/**
	 * 入力チェック
	 *
	 * @param params チェック値
	 * @param result BindingResult
	 * @param map MAP
	 * @param updateFlg true:ユーザー変更 false:ユーザー追加
	 * @return True:エラー有り False:エラー無し
	 * @throws InvocationTargetException 例外処理
	 * @throws IllegalAccessException 例外処理
	 */
	private boolean inputCheck(
				AddTelLineUserUpdateModel params, BindingResult result,
				Map<String, Object> map,boolean updateFlg) throws IllegalAccessException, InvocationTargetException{

		boolean error = false;

		/* ユーザー変更且つ変更元の電話機が設定されていなかった場合 */
		if(updateFlg && params.getOrgTelId().compareTo(new BigDecimal(0)) == 0){
			addError(result,properties.getProperty("tel.linking.user.error"));
			error = true;
		}

		/* ユーザーと電話機の紐付き存在チェック */
		if(!error && appCommonService.userTelExistCheck(params.getUserId(),params.getTelId())){
			addError(result,MessageFormat.format(properties.getProperty("not.tied.error"), "ユーザー" , "電話機"));
			error = true;
		}

		return error;
	}

}


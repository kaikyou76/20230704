/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineController.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.AddLineModel;
import jp.co.netmarks.model.AddTelLineUserUpdateModel;
import jp.co.netmarks.model.form.AddLineForm;
import jp.co.netmarks.model.form.AddLineUpdateForm;
import jp.co.netmarks.service.AddLineService;
import jp.co.netmarks.service.AddTelLineUserService;
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
 * ラインの検索と選択画面用コントローラークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
@Controller
@RequestMapping("/addLine")
public class AddLineController extends BaseController {

	@Autowired
	Properties properties;

	@Autowired
	AppCommonService appCommonService;

	@Autowired
	AddLineService addLineService;

	@Autowired
	AddTelLineUserService addTelLineUserService;

	/**
	 * 初期表示
	 *
	 * @param form AddLineForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	protected String index(AddLineForm form) throws Exception {
		return "dialog/addLine";
	}

	/**
	 * 検索処理
	 *
	 * @param form AddLineForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView search(
			AddLineForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		AddLineModel params = new AddLineModel();
		BeanUtils.copyProperties(params, form);

		/* 検索件数を取得 */
		map.put("total", addLineService.getLineAddTotal(params));

		/* 検索結果を取得 */
		map.put("rows", addLineService.getLineAddList(params));

		return getJsonView(map);
	}

	/**
	 * ライン変更処理
	 *
	 * @param form AddLineUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/updateLine", method=RequestMethod.POST)
	public ModelAndView updateLine(
			@Valid AddLineUpdateForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* 入力チェック */
		if(result.hasErrors()) {
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		AddTelLineUserUpdateModel params = setParams(form);

		/* 存在チェック */
		if(inputCheck(params , result, true)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 属性更新処理 */
		Map<String, String> messageMap =
				addTelLineUserService.updateLine(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

	/**
	 * ライン追加処理
	 *
	 * @param form AddLineUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/lineLinkingTel", method=RequestMethod.POST)
	public ModelAndView lineLinkingTel(
			@Valid AddLineUpdateForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* 入力チェック */
		if(result.hasErrors()) {
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		AddTelLineUserUpdateModel params = setParams(form);

		/* 存在チェック */
		if(inputCheck(params , result, false)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 属性更新処理 */
		Map<String, String> messageMap =
				addTelLineUserService.addLine(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

	/**
	 * ラインの新規追加処理
	 *
	 * @param form AddLineUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/addNewLine", method=RequestMethod.POST)
	public ModelAndView addNewLine(
			@Valid AddLineUpdateForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* 入力チェック */
		if(result.hasErrors()) {
			map.put("errors", result.getAllErrors());
			return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		AddTelLineUserUpdateModel params = setNewLineParams(form);

		/* 存在チェック */
		if(newLineInputCheck(params , result, map)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 属性更新処理 */
		Map<String, String> messageMap =
				addTelLineUserService.addNewLine(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

	/**
	 * 更新用のFormの値をModelにセットします。
	 * 
	 * @param form ライン追加用フォーム
	 * @return ライン追加モデル
	 */
	private AddTelLineUserUpdateModel setParams(AddLineUpdateForm form){
		/* インスタンス化 */
		AddTelLineUserUpdateModel params = new AddTelLineUserUpdateModel();

		params.setUserId(new BigDecimal(form.getUserId()));			// ユーザーID
		params.setTelId(new BigDecimal(form.getTelId()));			// 電話ID
		params.setLineId(new BigDecimal(form.getLineId()));			// ラインID
		params.setLineIndex(form.getLineIndex());					// ライン連番
		params.setOrgUserId(new BigDecimal(form.getOrgUserId()));	// ユーザーID（変更元）
		params.setOrgTelId(new BigDecimal(form.getOrgTelId()));		// 電話ID（変更元）
		params.setOrgLineId(new BigDecimal(form.getOrgLineId()));	// ラインID（変更元）
		params.setOrgLineIndex(form.getOrgLineIndex());				// ライン連番（変更元）
		params.setBranchTelId(form.getBranchTelId());				// 拠点ID（変更元）

		return params;
	}

	/**
	 * 新規用のFormの値をModelにセットします。
	 * 
	 * @param form ライン追加用フォーム
	 * @return ライン追加モデル
	 */
	private AddTelLineUserUpdateModel setNewLineParams(AddLineUpdateForm form){
		/* インスタンス化 */
		AddTelLineUserUpdateModel params = new AddTelLineUserUpdateModel();

		params.setUserId(new BigDecimal(form.getUserId()));				// ユーザーID
		params.setTelId(new BigDecimal(form.getTelId()));				// 電話ID
		params.setLineId(new BigDecimal(form.getLineId()));				// ラインID
		params.setOrgUserId(new BigDecimal(form.getOrgUserId()));		// ユーザーID（変更元）
		params.setOrgTelId(new BigDecimal(form.getOrgTelId()));			// 電話ID（変更元）
		params.setOrgLineId(new BigDecimal(form.getOrgLineId()));		// ラインID（変更元）
		params.setOrgLineIndex(form.getOrgLineIndex());					// ライン連番（変更元）
		params.setDirectoryNumber(form.getNewLineDirectoryNumber());	// 内線番号
		params.setDialinNumber(form.getNewLineDialinNumber());			// ダイアルイン
		params.setLineIndex(form.getNewLineIndex());					// ライン連番
		params.setChargeAssociationBranchId(form.getNewLineChargeAssociationBranchId());				// 拠点ID
		params.setChargeAssociationParentSectionId(form.getNewLineChargeAssociationParentSectionId());	// 親店部課
		params.setChargeAssociationSectionId(form.getNewLineChargeAssociationSectionId());				// 店部課
		params.setVoiceMailFlg(form.getNewLineVoiceMailFlg());			// VM使用
		params.setBranchTelId(form.getBranchTelId());					// 拠点ID（変更元）

		return params;
	}

	/**
	 * 入力チェック
	 *
	 * @param params ライン追加モデル
	 * @param result BindingResult
	 * @param updateFlg False:ライン変更 True:ライン追加
	 * @return True:エラーあり False:エラーなし
	 */
	private boolean inputCheck(AddTelLineUserUpdateModel params, BindingResult result, boolean updateFlg) {

		/* ライン変更且つ変更元のライン連番が１の場合 */
		if(updateFlg && params.getOrgLineIndex().equals(Constants.LINE_INDEX_MAIN) ){
			addError(result,MessageFormat.format(properties.getProperty("line.index.update.error"),"1","ライン変更"));
			return true;
		}

		/* 連番に1又は0を設定した場合 */
		if(params.getLineIndex().equals(Constants.LINE_INDEX_MAIN) || 
		   params.getLineIndex().equals(Constants.LINE_INDEX_ERROR)){
			addError(result,"AddLineUpdateForm", "lineIndex",
					MessageFormat.format(properties.getProperty("line.index.error"),params.getLineIndex()));
			return true;
		}

		/* ライン存在チェック */
		String orgLineIndex = null;
		if(updateFlg) orgLineIndex = params.getOrgLineIndex();
		if(appCommonService.lineIndexExistCheck(params.getTelId(), params.getLineIndex(), orgLineIndex)){
			addError(result,"AddLineUpdateForm", "lineIndex",
					MessageFormat.format(properties.getProperty("exist.error.entry.existing"), "ライン"));
			return true;
		}

		/* 電話機とラインの紐付き存在チェック */
		if(appCommonService.telLineExistCheck(params.getTelId(),params.getLineId())){
			addError(result,MessageFormat.format(properties.getProperty("tel.not.linking.line.error"), "ユーザー" , "電話機"));
			return true;
		}
		
		/* ラインに紐づく電話の拠点チェック */
		String[] branchId = appCommonService.getSharedLineBranch(params.getLineId());
		if(branchId != null && branchId.length != 0 && 
		  (branchId.length > 1 || !branchId[0].equals(params.getBranchTelId()))){
			addError(result,properties.getProperty("tel.linking.line.shared.error"));
			return true;
		}

		return false;
	}

	/**
	 * 入力チェック(新規ライン追加用)
	 *
	 * @param params ライン追加モデル
	 * @param result BindingResult
	 * @param map MAP
	 * @return True:エラーあり False:エラー無し
	 */
	private boolean newLineInputCheck(AddTelLineUserUpdateModel params, BindingResult result,Map<String, Object> map){

		/* 連番に1又は0を設定した場合 */
		if(params.getLineIndex().equals(Constants.LINE_INDEX_MAIN) || 
		   params.getLineIndex().equals(Constants.LINE_INDEX_ERROR)){
			addError(result,"AddLineUpdateForm", "newLineIndex", 
					MessageFormat.format(properties.getProperty("line.index.error"),params.getLineIndex()));
			return true;
		}

		/* ライン存在チェック */
		if(appCommonService.lineIndexExistCheck(params.getTelId(), params.getLineIndex())){
			addError(result,"AddLineUpdateForm", "newLineIndex",
					MessageFormat.format(properties.getProperty("exist.error.entry.existing"), "ライン"));
			return true;
		}

		/* 内線番号存在チェック */
		if(appCommonService.directoryNumberExistCheck(params.getDirectoryNumber())){
			/* 存在した場合 */
			addError(result,"AddLineUpdateForm", "newLineDirectoryNumber",
					MessageFormat.format(properties.getProperty("exist.error.entry.existing"),"内線番号"));
			return true;
		}

		return false;
	}

}
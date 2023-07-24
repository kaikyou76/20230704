/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddAssociateController.java
 *
 * @date 2013/11/01
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ksc.controller.BaseController;
import jp.co.netmarks.model.MaintenanceUpdateModel;
import jp.co.netmarks.model.form.AddAssociateForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.MaintenanceService;

/**
 * <pre>
 * 拠点-店部課紐付き作成用コントローラークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/11/01 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/11/01
 */
@Controller
@RequestMapping("/addAssociate")
public class AddAssociateController extends BaseController{

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
	private MaintenanceService maintenanceService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form AddAssociateForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	protected String index(AddAssociateForm form) throws Exception {
		return "dialog/addAssociate";
	}

	/**
	 * 拠点-店部課の紐付き作成処理
	 *
	 * @param form AddAssociateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/addNewAssociate", method=RequestMethod.POST)
	public ModelAndView addAssociate(
			@Valid AddAssociateForm form, BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		/* 入力チェック */
		if(result.hasErrors()) {
			map.put("errors", result.getAllErrors());
			return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		params = setParams(form);

		/* 拠点の存在チェック */
		if(existCheck(params,result,"associateBranchId")){
			map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 店部課存在チェック */
		if(!appCommonService.sectionExistCheck(params.getSectionId(), params.getCompanyId())){
			addError(result,"AddAssociateForm", "sectionId",
					MessageFormat.format(properties.getProperty("exist.error"), "入力した店部課コード"));
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 拠点-店部課紐付き作成処理 */
		Map<String, String> messageMap =maintenanceService.addAssociate(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);

	}

	/**
	 * 存在チェック
	 *
	 * @param params 拠点-店部課紐付き追加モデル
	 * @param result BindingResult
	 * @param fieldName エラー表示フィールド名
	 * @return True:エラーあり False:エラーなし
	 */
	private boolean existCheck(MaintenanceUpdateModel params, BindingResult result, String fieldName){

		/* 拠点存在チェック */
		if(!appCommonService.branchExistCheck(params.getBranchId())){

			if(StringUtils.isEmpty(fieldName)){
				addError(result,MessageFormat.format(properties.getProperty("exist.error"), "選択した拠点コード"));
			} else {
				addError(result,"AddAssociateForm", fieldName,
						MessageFormat.format(properties.getProperty("exist.error"), "選択した拠点コード"));
			}
			return true;
	   	}

		/* 拠点-店部課紐付き存在チェック(V_ORGANIZATION_LEVEL) */
		if(maintenanceService.organizationLevelExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			if(StringUtils.isEmpty(fieldName)){
				addError(result,properties.getProperty("branch.linking.section.error"));
			} else {
				addError(result,"AddAssociateForm", fieldName,
						MessageFormat.format(properties.getProperty("exist.error.entry.existing"), "拠点-店部課紐付き"));
			}
			return true;
	   	}

		return false;
	}

	/**
	 * 更新用のFormの値をModelにセットします。
	 * @param form 拠点-店部課紐付き追加用フォーム
	 * @return 拠点-店部課紐付き追加モデル
	 */
	private MaintenanceUpdateModel setParams(AddAssociateForm form){
		/* インスタンス化 */
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		params.setBranchId(form.getBranchId());						// 拠点コード
		params.setBranchName(form.getBranchName());					// 拠点名
		params.setClusterId(new BigDecimal(form.getClusterId()));	// クラスタID
		params.setCompanyId(form.getCompanyId());					// 会社コード
		params.setSectionId(form.getSectionId());					// 店部課コード

		return params;
	}

}
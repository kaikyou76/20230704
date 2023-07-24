/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddBranchController.java
 *
 * @date 2013/11/01
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

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
import jp.co.netmarks.model.form.AddBranchForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.MaintenanceService;

/**
 * <pre>
 * 拠点追加用コントローラークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/11/01 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/11/01
 */
@Controller
@RequestMapping("/addBranch")
public class AddBranchController extends BaseController{

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
	private MaintenanceService maintenanceService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form AddBranchForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	protected String index(AddBranchForm form) throws Exception {

		/** クラスタリスト取得 **/
		form.setClusterList(appCommonService.getCluster());

		return "dialog/addBranch";
	}

	/**
	 * 拠点の新規追加処理
	 *
	 * @param form AddBranchForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/addNewBranch", method=RequestMethod.POST)
	public ModelAndView addBranch(
			@Valid AddBranchForm form,	BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		/* 入力チェック */
		if(result.hasErrors()) {
			//setErrorMessage("addBranchId","addBranchName","addClusterId",result);
			map.put("errors", result.getAllErrors());
			return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		params = setParams(form);

		/* 存在チェック */
		if(appCommonService.branchExistCheck(params.getBranchId())){
			addError(result,"addBranchForm", "branchId",
					MessageFormat.format(properties.getProperty("exist.error.entry.existing"), "拠点コード"));
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 拠点追加処理 */
		Map<String, String> messageMap =maintenanceService.addNewBranch(params);

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
	 * @param form 拠点追加用フォーム
	 * @return 拠点追加モデル
	 */
	private MaintenanceUpdateModel setParams(AddBranchForm form){
		/* インスタンス化 */
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		params.setBranchId(form.getBranchId());						// 拠点コード
		params.setBranchName(form.getBranchName());					// 拠点名
		//params.setClusterId(new BigDecimal(form.getClusterId()));	// クラスタID
		params.setCompanyId(form.getCompanyId());					// 会社コード
		params.setSectionId(form.getSectionId());					// 店部課コード

		return params;
	}

	/**
	 * エラーメッセージの表示フィールドを再設定します。
	 *
	 * @param branchId 拠点ID
	 * @param branchName 拠点名
	 * @param clusterId クラスタID
	 * @param result BindingResult
	 */
	private void setErrorMessage(String branchId, String branchName, String clusterId, BindingResult result) {

		String errorMessage = null;

		if(result.hasFieldErrors("branchId")){
			errorMessage = result.getFieldErrors("branchId").get(0).getDefaultMessage();
			addError(result,"AddBranchForm", branchId, errorMessage);
		}
		if(result.hasFieldErrors("branchName")){
			errorMessage = result.getFieldErrors("branchName").get(0).getDefaultMessage();
			addError(result,"AddBranchForm", branchName, errorMessage);
		}
		if(result.hasFieldErrors("clusterId")){
			errorMessage = result.getFieldErrors("clusterId").get(0).getDefaultMessage();
			addError(result,"AddBranchForm", clusterId, errorMessage);
		}
	}
}


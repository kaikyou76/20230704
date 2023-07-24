/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementController.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.ksc.util.OptionsUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.AuthManagementModel;
import jp.co.netmarks.model.AuthManagementUpdateModel;
import jp.co.netmarks.model.form.AuthManagementForm;
import jp.co.netmarks.model.form.AuthManagementListUpdateForm;
import jp.co.netmarks.model.form.list.AuthManagementUpdateForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.AuthManagementService;
import jp.co.netmarks.util.AppHtmlHelper;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 権限管理画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/09
 */
@Controller
@RequestMapping("/authManagement")
public class AuthManagementController extends BaseController {

	@Autowired
	private AuthManagementService authManagementService;

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form AuthManagementForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(AuthManagementForm form) throws Exception {

		/* フォーカスを設定 */
		form.setFocus("branchUserId");

		/** プルダウンのリスト取得 **/
		/* 拠点リストを取得 */
		List<LabelValueModel> branchList = appCommonService.getBranchList();

		/* 拠点(ユーザー)リストをセット */
		form.setBranchUserList(OptionsUtil.addBlankHeadLVBList(branchList, Constants.ALL_LABEL));

		/* 店部課（ユーザ）リストをセット */
		form.setSectionUserList(OptionsUtil.addBlankHeadLVBList(null, Constants.ALL_LABEL));

		/* 処理リストをセット（grid用） */
		form.setManagementListJson(AppHtmlHelper.convertToJson(OptionsUtil.convertToOptionsCollection(
							Constants.AUTH_MANAGEMENT_DATA_LABEL, Constants.AUTH_MANAGEMENT_DATA_VALUE)));

		return "view/authManagement/authManagement";
	}

	/**
	 * 検索処理（管理権限付与者）
	 *
	 * @param form AuthManagementForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/authManagementSearch", method=RequestMethod.POST)
	public ModelAndView authManagementSearch(
			@Valid AuthManagementForm form,
			BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* バリデーションエラー */
		if (result.hasErrors()) {
			map.put("total", 0);
			map.put("rows", 0);
			return getJsonView(map);
		}

		/* 検索条件パラーメータをセット */
		AuthManagementModel params = new AuthManagementModel();
		BeanUtils.copyProperties(params,form);

		/* 検索条件をセット */
		setSearchConditions(params);

		/* 検索件数を取得 */
		int cnt = authManagementService.getAuthManagementTotal(params);
		map.put("total", cnt);

		/* 検索結果を取得 */
		map.put("rows", authManagementService.getAuthManagement(params));

		return getJsonView(map);
	}

	/**
	 * 検索処理（管理権限付与対象者）
	 *
	 * @param form AuthManagementForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/authManagementTargetSearch", method=RequestMethod.POST)
	public ModelAndView authManagementTargetSearch(
			@Valid AuthManagementForm form,
			BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* バリデーションエラー */
		if (result.hasErrors()) {
			map.put("total", 0);
			map.put("rows", 0);
			return getJsonView(map);
		}

		/* 検索条件パラーメータをセット */
		AuthManagementModel params = new AuthManagementModel();
		BeanUtils.copyProperties(params,form);

		/* 検索条件をセット */
		setSearchConditions(params);

		/* 検索件数を取得 */
		int cnt = authManagementService.getAuthManagementTargetTotal(params);
		map.put("total", cnt);

		/* 検索結果を取得 */
		map.put("rows", authManagementService.getAuthManagementTarget(params));

		return getJsonView(map);
	}

	/**
	 * 管理権限を更新ボタン押下時
	 *
	 * @param form AuthManagementListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/authManagementUpdate", method=RequestMethod.POST)
	public ModelAndView authManagementUpdate(
			AuthManagementListUpdateForm form,BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<AuthManagementUpdateModel> updateList = new ArrayList<AuthManagementUpdateModel>();

		/* 処理対象のデータをセット */
		setFormToModel(form.getGridForm(), updateList);

		if(updateList.size() == 0){
			setSuccesMes(form,properties.getProperty("auth.error.no.update.user"));
			return getJsonView(map);
		}

		/* 権限更新処理 */
		Map<String, String> messageMap =
				authManagementService.updataUserRole(updateList);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		return getJsonView(map);
	}

	/**
	 * 管理権限を削除ボタン押下時
	 *
	 * @param form AuthManagementListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/authManagementDelete", method=RequestMethod.POST)
	public ModelAndView authManagementDelete(
			AuthManagementListUpdateForm form,BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<AuthManagementUpdateModel> updateList = new ArrayList<AuthManagementUpdateModel>();

		/* 処理対象のデータをセット */
		setFormToModel(form.getGridForm(), updateList);

		/* 権限更新処理 */
		Map<String, String> messageMap =
				authManagementService.updataUserRole(updateList);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		return getJsonView(map);
	}


	/**
	 * 店部課と会社の検索条件をセットします
	 * ※店部課プルダウンの値から会社IDと店部課IDを取得します。
	 *
	 * @param params 検索条件モデル
	 * @return 検索条件モデル
	 */
	private AuthManagementModel setSearchConditions(AuthManagementModel params){

		String[] sectionCompnayUserArray = params.getSectionUserId().split(",");
		/* 会社ID */
		params.setCompanyUserId(sectionCompnayUserArray[0]);
		/* 店部課ID */
		params.setSectionUserId(sectionCompnayUserArray[1]);

		return params;
	}

	/**
	 * FormデータをModelにセットします
	 *
	 * @param list フォームデータ
	 * @param updateList モデル
	 * @throws InvocationTargetException 例外処理
	 * @throws IllegalAccessException  例外処理
	 */
	private void setFormToModel(List<AuthManagementUpdateForm> list, List<AuthManagementUpdateModel> updateList)
			throws IllegalAccessException, InvocationTargetException{

		for(AuthManagementUpdateForm form : list){

			/* チェックがついていない、かつ変更なしの場合 */
			if(!form.isChecked() && !form.isChanged()){
				continue;
			}

			/* 権限なしの場合（付与対象者が"権限なし"を選択していた場合） */
			if(form.getAuthId().equals(Constants.ROLE_DIV_NOTHING)){
				continue;
			}

			/* 変更がない場合（削除対象者の場合） */
			if(!form.isChanged()){
				/* 権限なしに設定 */
				form.setAuthId(Constants.ROLE_DIV_NOTHING);
			}

			/* インスタンス化 */
			AuthManagementUpdateModel params = new AuthManagementUpdateModel();

			/* Form→Modelコピー処理 */
			BeanUtils.copyProperties(params,form);

			/* リストにセット */
			updateList.add(params);
		}
	}

	/**
	 * 店部課リスト取得
	 * @param id 拠点Id
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getAuthSectionList", method=RequestMethod.POST)
	public ModelAndView getAuthSectionList(
			@RequestParam("id") String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<LabelValueModel> list = new ArrayList<LabelValueModel>();

		if(StringUtils.isNotEmpty(id)){
			/* リスト取得 */
			list = appCommonService.getSectionList(id);
		}

		map.put("data", list);

		return getJsonView(map);
	}

	/**
	 * 成功メッセージをセットする
	 *
	 * @param form BaseForm
	 * @param message メッセージ
	 */
	private void setSuccesMes(BaseForm form, String ... message){

		StringBuffer sb = new StringBuffer();
		for(String mes : message){
			if(StringUtils.isNotEmpty(mes)){
				sb.append(mes);
				sb.append("<br>");
			}
		}

		sb.delete(sb.length() - 4, sb.length());
		form.setMessage(sb.toString());
	}


}


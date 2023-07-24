/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CheckConsistencyController.java
 *
 * @date 2013/09/12
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.CheckConsistencySearchModel;
import jp.co.netmarks.model.CheckConsistencyUpdateModel;
import jp.co.netmarks.model.form.CheckConsistencyListUpdateForm;
import jp.co.netmarks.model.form.CheckConsistencySearchForm;
import jp.co.netmarks.model.form.list.CheckConsistencyUpdateForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.CheckConsistencyService;

/**
 * <pre>
 * CUCM整合性チェック画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/12
 */
@Controller
@RequestMapping("/checkConsistency")
public class CheckConsistencyController extends BaseController{

	@Autowired
	private CheckConsistencyService checkConsistencyService;

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form CheckConsistencySearchForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(CheckConsistencySearchForm form) throws Exception {

		/* フォーカスを設定 */
		form.setFocus("clusterId");

		/** クラスタリスト取得 **/
		form.setClusterList(appCommonService.getCluster());

		/** CUCM情報取得日時取得 **/
		form.setCucmUpdateTime(checkConsistencyService.getCucmUpdateTime());

		return "view/checkConsistency/checkConsistency";
	}

	/**
	 * 検索処理(CUCM)
	 *
	 * @param form CheckConsistencySearchForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/searchInconsistancyCucm", method=RequestMethod.POST)
	public ModelAndView searchInconsistancyCucm(
			CheckConsistencySearchForm form,
			BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* CUCM情報取得日時取得 */
		form.setCucmUpdateTime(checkConsistencyService.getCucmUpdateTime());

		/* 検索条件パラーメータをセット */
		CheckConsistencySearchModel params = new CheckConsistencySearchModel();
		BeanUtils.copyProperties(params,form);

		/* 検索件数を取得 */
		int cnt = checkConsistencyService.getInconsistencyCucmTotal(params);

		map.put("total", cnt);
		/* 検索結果を取得 */
		map.put("rows", checkConsistencyService.getInconsistencyCucm(params));

		return getJsonView(map);
	}

	/**
	 * 検索処理(オフィスリンク)
	 *
	 * @param form CheckConsistencySearchForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/searchInconsistancyOfficeLink", method=RequestMethod.POST)
	public ModelAndView searchInconsistancyOfficeLink(
			CheckConsistencySearchForm form,
			BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		CheckConsistencySearchModel params = new CheckConsistencySearchModel();
		BeanUtils.copyProperties(params,form);

		/* 検索件数を取得 */
		int cnt = checkConsistencyService.getInconsistencyOfficeLinkTotal(params);

		map.put("total", cnt);
		/* 検索結果を取得 */
		map.put("rows", checkConsistencyService.getInconsistencyOfficeLink(params));

		return getJsonView(map);
	}

	/**
	 * CUCM情報取込ボタン押下時
	 *
	 * @param form CheckConsistencyListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/fetchCucmData", method=RequestMethod.POST)
	public ModelAndView fetchCucmData(
			CheckConsistencyListUpdateForm form,BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CheckConsistencyUpdateModel> updateList = new ArrayList<CheckConsistencyUpdateModel>();

		/* 取込可否チェック */
		if(!enableCheck(form.getGridForm(), result, updateList)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* CUCM情報取込処理 */
		Map<String, String> messageMap =checkConsistencyService.fetchCucm(updateList);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		return getJsonView(map);
	}

	/**
	 * 取込可否チェック
	 *
	 * @param list フォームデータ
	 * @param result BindingResult
	 * @param updateList モデル
	 * @throws InvocationTargetException 例外処理
	 * @throws IllegalAccessException 例外処理
	 */
	private boolean enableCheck(List<CheckConsistencyUpdateForm> list, BindingResult result,
			List<CheckConsistencyUpdateModel> updateList) throws IllegalAccessException, InvocationTargetException{

		boolean enable = true;
		int i = 0;

		for(CheckConsistencyUpdateForm form : list){

			/* チェックがついていなかった場合 */
			if(!form.isChecked()){
				i++;
				continue;
			}
			/* インスタンス化 */
			CheckConsistencyUpdateModel params = new CheckConsistencyUpdateModel();


			/* 対象データ存在チェック（連携アプリ、CUCM双方） */
			if(form.getInconsistencyDiv().equals(Constants.INCONSISTENCY_APP_NO_EXIST.toString())
					|| form.getInconsistencyDiv().equals(Constants.INCONSISTENCY_CUCM_NO_EXIST.toString())){
				addError(result,"checkConsistencyUpdateForm", "gridForm[" + i + "].appMacAddress",
						properties.getProperty("consistency.error.fetch"));
				enable = false;
				i++;
				continue;
			}

			/* 取込項目の差異チェック */
			if(!differenceCheck(form)){
				addError(result,"checkConsistencyUpdateForm", "gridForm[" + i + "].appMacAddress",
						properties.getProperty("consistency.error.fetch"));
				enable = false;
			}

			/* Form→Modelコピー処理 */
			BeanUtils.copyProperties(params,form);
			/* リストにセット */
			updateList.add(params);
			i++;
		}
		return enable;
	}

	/**
	 * 差異チェック
	 *
	 * @param params CUCMデータ取込用フォーム
	 * @return True:差異あり False:差異なし
	 */
	private boolean differenceCheck(CheckConsistencyUpdateForm params) {

		boolean difference = false;

		if(params.getCucmUseVmFlg().isEmpty()) {
			params.setCucmUseVmFlg(Constants.VOICE_MAIL_FLG_OFF);
		}

		/* 取込対象の差異の有無を確認する */
		if(!params.getCucmDescription().equals(params.getAppDescription())
				|| !params.getCucmPhoneButtonName().equals(params.getAppPhoneButtonName())
				|| !params.getCucmAddonModule1().equals(params.getAppAddonModule1())
				|| !params.getCucmAddonModule2().equals(params.getAppAddonModule2())
				|| !params.getCucmExternalPhoneNumber().equals(params.getAppExternalPhoneNumber())
				|| !params.getCucmLineTextLabel().equals(params.getAppLineTextLabel())
				|| !params.getCucmRingSetting().equals(params.getAppRingSetting())
				|| !params.getCucmFwdBusyDestination().equals(params.getAppFwdBusyDestination())
				|| !params.getCucmFwdNoansDestination().equals(params.getAppFwdNoansDestination())
				|| !params.getCucmUseVmFlg().equals(params.getAppUseVmFlg())){
			difference = true;
		}

		return difference;

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


/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelSearchController.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.ksc.util.OptionsUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.UserAndTelSearchModel;
import jp.co.netmarks.model.UserAndTelUpdateModel;
import jp.co.netmarks.model.form.UserAndTelListUpdateForm;
import jp.co.netmarks.model.form.UserAndTelSearchForm;
import jp.co.netmarks.model.form.list.UserAndTelUpdateForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.UserAndTelSearchService;
import jp.co.netmarks.util.AppHtmlHelper;

/**
 * <pre>
 * ユーザーと電話機の一覧画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/07 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/07
 */
@Controller
@RequestMapping("/userAndTel")
public class UserAndTelSearchController extends BaseController {

	private static Log log = LogFactory.getLog(UserAndTelSearchController.class);


	@Autowired
	private UserAndTelSearchService userAndTelSearchService ;

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form UserAndTelSearchForm
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(UserAndTelSearchForm form) throws Exception {

		/** プルダウンのリスト取得 **/

		/* 拠点リストを取得 */
		List<LabelValueModel> branchList = appCommonService.getBranchList();

		/* 拠点リスト（クラスIDありい）を取得 */
		List<LabelValueModel> branchClusterList =
				appCommonService.getBranchList(Constants.COM_FLG_ON);

		/* 拠点(ユーザー)リストをセット */
		form.setBranchUserList(
				OptionsUtil.addBlankHeadLVBList(branchList,Constants.BRANCH_ALL_LABEL));

		/* 拠点（電話機）リストをセット */
		form.setBranchTelList(
				OptionsUtil.addBlankHeadLVBList(branchClusterList,Constants.BRANCH_ALL_LABEL));

		/* 店部課（ユーザ）リストをセット */
		form.setSectionUaerList(
				OptionsUtil.addBlankHeadLVBList(null, Constants.SECTION_ALL_LABEL));

		/* 店部課（電話機）リストをセット */
		form.setSectionTelList(
				OptionsUtil.addBlankHeadLVBList(null, Constants.SECTION_ALL_LABEL));

		/* 反映状況リスト取得 */
		form.setCucmReflectionList(
				OptionsUtil.convertToOptionsCollection(
						Constants.REFLECTION_SELECT_LABEL, Constants.REFLECTION_SELECT_VALUE));

		/* 電話機リスト取得 */
		form.setTelTypeModelList(
				OptionsUtil.addBlankHeadLVBList(appCommonService.getTypeModelList(), Constants.TEL_TYPE_MODEL_ALL_LABEL));

		/* PhoneButton Templeteリスト取得 */
		form.setPhoneButtonTempleteList(
				OptionsUtil.addBlankHeadLVBList(null, Constants.ALL_LABEL ));


		/* Pickup Grouprリスト取得  */
		form.setPickupGropuList(
				OptionsUtil.addBlankHeadLVBList(null, Constants.ALL_LABEL));

		/* 通話録音リスト取得 */
		form.setLoggerDataList(
						OptionsUtil.convertToOptionsCollection(
								Constants.LOGGER_DATA_LABEL, Constants.LOGGER_DATA_VALUE));

		/* クラスタリスト取得 */
		form.setClusterList(
				OptionsUtil.addBlankHeadLVBList(
						appCommonService.getCluster(), Constants.CLUSTER_ALL_LABEL, Constants.CLUSTER_INIT_VALUE));

		/* ### グリッド用リスト項目取得 ### */

		/* 拠点（電話機）リストをセット（grid用） */
		form.setBranchListJson(AppHtmlHelper.convertToJson(branchClusterList));

		/* 所属店部課（電話機）リストをセット（grid用） */
		form.setSectionListJson(AppHtmlHelper.convertToJson(appCommonService.getDynamicSectionMap()));

		/* 通話録音リストをセット（grid用） */
		form.setLoggerDataListJson(
				AppHtmlHelper.convertToJson(OptionsUtil.convertToOptionsCollection(
						Constants.LOGGER_DATA_SELECT_LABEL, Constants.LOGGER_DATA_SELECT_VALUE)));

		/* コーリングサーチスペース(grid用 CSS) */
		form.setCallingSearchSpaceListJson(
				AppHtmlHelper.convertToJson(appCommonService.getDynamicCssMap()));

		/* PickupGroupリストをセット(grid用) */
		form.setPickupGroupListJson(
				AppHtmlHelper.convertToJson(appCommonService.getDynamicPickupGroupMap()));

		/* 拡張モジュール (grid用)*/
		form.setAddonModuleListJson(
				AppHtmlHelper.convertToJson(appCommonService.getAddonModuleMap()));

		/* 鳴動設定 (grid用)*/
		form.setRingSettingListJson(
				AppHtmlHelper.convertToJson(OptionsUtil.convertToOptionsCollection(
						Constants.RING_SETTING_LABEL, Constants.RING_SETTING_VALUE)));

		/* 電話テンプレート (grid用)*/
		form.setPhoneButtonTempleteListJson(
				AppHtmlHelper.convertToJson(appCommonService.getDynamicPhoneTempleteMap()));

		/* 親店部課リスト */
		form.setParentSectionListJson(
				AppHtmlHelper.convertToJson(appCommonService.getParentSectionMap()));

		return "view/userAndTelSearch/userAndTelSearch";
	}

	/**
	 * 検索処理
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView search(
			UserAndTelSearchForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		UserAndTelSearchModel params = new UserAndTelSearchModel();
		BeanUtils.copyProperties(params,form);

		/* 検索条件をセット */
		setSearchConditions(params);
		
		params.setChargeAssociationBranchId(null);
		params.setChargeAssociationParentSectionId(null);
		params.setChargeAssociationSectionId(null);

		/* 検索件数を取得 */
		map.put("total", userAndTelSearchService.getUserAndTelTotal(params));
		/* 検索結果を取得 */
		map.put("rows", userAndTelSearchService.getUserAndTelList(params));

		return getJsonView(map);
	}

	/**
	 * 一覧をエクスポート
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/searchResultExportCsv", method=RequestMethod.POST)
	public ModelAndView searchResultExportCsv(
			UserAndTelSearchForm form,BindingResult result) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		UserAndTelSearchModel params = new UserAndTelSearchModel();
		BeanUtils.copyProperties(params, form);

		/* CSVファイルを作成 */
		userAndTelSearchService.searchResultExportCsv(params);

		return getJsonView(map);
	}
	
/**
	 * 検索結果をエクスポート
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/tableDataExportCsv", method=RequestMethod.POST)
	public ModelAndView tableDataExportCsv(
			UserAndTelSearchForm form,BindingResult result) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		UserAndTelSearchModel params = new UserAndTelSearchModel();
		BeanUtils.copyProperties(params, form);

		/* CSVファイルを作成 */
		userAndTelSearchService.tableDataExportCsv(params);

		return getJsonView(map);
	}

	/**
	 * 個別反映ボタン押下時
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/separatelyReflection", method=RequestMethod.POST)
	public ModelAndView separatelyReflection(
			UserAndTelListUpdateForm form,BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserAndTelUpdateModel> updateList = new ArrayList<UserAndTelUpdateModel>();

        /* バリデーションエラー */
		validateGridListForm(form, result);
		if(result.hasErrors()) {
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 固有チェック */
		if(!entryCheck(form.getGridForm(), result, map , updateList, true)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 個別反映処理（コールマネージャへの通信有り） */
		Map<String, String> messageMap =
				userAndTelSearchService.updataProperty(updateList, true);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		return getJsonView(map);
	}

	/**
	 * 更新（翌日反映）ボタン押下時処理
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/nextDayReflection", method=RequestMethod.POST)
	public ModelAndView nextDayReflection(
			UserAndTelListUpdateForm form, BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserAndTelUpdateModel> updateList = new ArrayList<UserAndTelUpdateModel>();

        /* バリデーションエラー */
		 validateGridListForm(form, result);
		if(result.hasErrors()) {
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 固有チェック */
		if(!entryCheck(form.getGridForm(), result, map , updateList, false)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 更新処理 */
		Map<String, String> messageMap =
				userAndTelSearchService.updataProperty(updateList,false);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		return getJsonView(map);
	}

	/**
	 * 話中転送先確認
	 * @param form UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/busyDestinationCheck", method=RequestMethod.POST)
	public ModelAndView busyDestinationCheck(
			UserAndTelListUpdateForm form, BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		int i = -1;

		for(UserAndTelUpdateForm params :  form.gridForm){

			/* カウンター */
			i++;

			/* 話中転送先 */
			String busyDest = params.getBusyDestination();

			/* 空白の場合はチェックしない */
			if(StringUtils.isEmpty(busyDest)){
				continue;
			}

			/* 共通話中転送先チェック */
			checkBusyDestination(params, form.gridForm, result, i);
		}

		/* メッセージをセット */
		setSuccesMes(form,MessageFormat.format(properties.getProperty("list.check"), "話中転送先"));

		/* エラーがあった場合 */
		if(result.hasErrors()) {
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		return getJsonView(map);
	}

	/**
	 * 返却
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/returnTelBtn", method=RequestMethod.POST)
	public ModelAndView returnTelBtn(
		UserAndTelListUpdateForm form, BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserAndTelUpdateModel> updateList = new ArrayList<UserAndTelUpdateModel>();

		/* パラメータのセット */
		setParam(form.getGridForm(), updateList);
		UserAndTelUpdateModel params = updateList.get(0);

		/* 返却処理 */
		Map<String, String> messageMap =
				userAndTelSearchService.returnTelBtn(params);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		/* ライン削除処理 */
		return getJsonView(map);
	}

	/**
	 * 電話機削除
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/deleteTel", method=RequestMethod.POST)
	public ModelAndView deleteTel(
		UserAndTelListUpdateForm form, BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserAndTelUpdateModel> updateList = new ArrayList<UserAndTelUpdateModel>();

		/* パラメータのセット */
		setParam(form.getGridForm(), updateList);
		UserAndTelUpdateModel params = updateList.get(0);

		/* 電話機削除処理 */
		Map<String, String> messageMap =
				userAndTelSearchService.deleteTel(params);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		/* ライン削除処理 */
		return getJsonView(map);
	}

/**
	 * ライン削除
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/deleteLine", method=RequestMethod.POST)
	public ModelAndView deleteLine(
			UserAndTelListUpdateForm form, BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserAndTelUpdateModel> updateList = new ArrayList<UserAndTelUpdateModel>();

		/* パラメータのセット */
		setParam(form.getGridForm(), updateList);
		UserAndTelUpdateModel params = updateList.get(0);

		/* 連番チェック */
		if(params.getLineIndex().equals(Constants.LINE_INDEX_MAIN)){
			addError(result, MessageFormat.format(properties.getProperty("line.index.update.error"),"[1]","ライン削除"));
			map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* ライン削除処理 */
		Map<String, String> messageMap =
				userAndTelSearchService.deleteLine(params);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		/* ライン削除処理 */
		return getJsonView(map);
	}

	/**
	 * 部内在庫
	 *
	 * @param form  UserAndTelListUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/insideStock", method=RequestMethod.POST)
	public ModelAndView insideStock(
		UserAndTelListUpdateForm form, BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserAndTelUpdateModel> updateList = new ArrayList<UserAndTelUpdateModel>();

		/* パラメータのセット */
		setParam(form.getGridForm(), updateList);
		UserAndTelUpdateModel params = updateList.get(0);

		/* 部内在庫処理 */
		Map<String, String> messageMap =
				userAndTelSearchService.insideStock(params);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage"));

		/* ライン削除処理 */
		return getJsonView(map);
	}

	/**
	 * 即時反映
	 *
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/cucmReflection", method=RequestMethod.POST)
	public ModelAndView cucmReflection() throws Exception {

		/* バッチ反映処理 */
		Map<String, Object> messageMap =
				userAndTelSearchService.cucmReflection();

		return getJsonView(messageMap);
	}

	/**
	 * 店部課と会社の検索条件をセットします
	 * ※店部課プルダウンの値から会社IDと店部課IDを取得します。
	 * 
	 * @param params 検索条件モデル
	 * @return 検索条件モデル
	 */
	private UserAndTelSearchModel setSearchConditions(UserAndTelSearchModel params){

		/* 店部課（ユーザー）を選択していた場合 */
		if(StringUtils.isNotEmpty(params.getSectionUserId())){
			String[] sectionCompnayUserArray = params.getSectionUserId().split(",");
			/* 会社ID */
			params.setCompanyUserId(sectionCompnayUserArray[0]);
			/* 店部課ID */
			params.setSectionUserId(sectionCompnayUserArray[1]);
		}

		/* 店部課（電話機）を選択していた場合 */
		if(StringUtils.isNotEmpty(params.getSectionTelId())){
			String[] sectionCompnayTelArray = params.getSectionTelId().split(",");
			/* 会社ID */
			params.setCompanyTelId(sectionCompnayTelArray[0]);
			/* 店部課ID */
			params.setSectionTelId(sectionCompnayTelArray[1]);
		}

		return params;
	}

	/**
	 * 存在チェック
	 *
	 * @param list 検索条件リスト（フォーム）
	 * @param result BindingResult
	 * @param map MAP
	 * @param updateList 検索条件リスト（モデル）
	 * @param soapFlg ソープ通信フラグ True:通信する False:通信しない
	 * @return チェックフラグ True:エラー無し False:エラー有り
	 * @throws InvocationTargetException 例外処理
	 * @throws IllegalAccessException 例外処理
	 */
	private boolean entryCheck(List<UserAndTelUpdateForm> list, BindingResult result,
			Map<String, Object> map, List<UserAndTelUpdateModel> updateList, Boolean soapFlg) throws IllegalAccessException, InvocationTargetException{

		boolean exist = true;
		int i = 0;

		for(UserAndTelUpdateForm form : list){

			/* チェックがついていなかった場合 */
			if(!form.isChecked()){
				i++;
				continue;
			}

			/* インスタンス化 */
			UserAndTelUpdateModel params = new UserAndTelUpdateModel();

			/* Form→Modelコピー処理 */
			BeanUtils.copyProperties(params,form);
			/* リストにセット */
			updateList.add(params);

			/* 電話機が設定されていなかった場合 */
			if((StringUtils.isEmpty(form.getTelId()) || form.getTelId().equals("0"))){
				if(soapFlg){
					addError(result,properties.getProperty("tel.not.linking.reflect.error"));
					exist = false;
				}
				i++;
				continue;
			}

			/** ダイアルインチェック **/
			if(!form.getDialinNumber().matches("^([*#X0-9]+)?$")){
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].dialinNumber",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;
			}

			/** VM使用   **/
			if(!form.getLineIndex().equals(Constants.LINE_INDEX_MAIN) &&
				form.getVoiceMailFlg().equals(Constants.VOICE_MAIL_FLG_ON) &&
			    form.getOrgVoiceMailFlg().equals(Constants.VOICE_MAIL_FLG_OFF)){
				/* 連番が「１」以外の場合にチェックを付けたらエラー */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].voiceMailFlg",
						properties.getProperty("voice.mail.line.index.error"));
				exist = false;
			}

			/** 話中転送先チェック **/
//			if(!form.getBusyDestination().matches("^([*#0-9]+)?$")){
//				/* 文字形式チェック */
//				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].busyDestination",
//						properties.getProperty("javax.validation.constraints.Pattern.message"));
//				exist = false;
//
//			} else
			if(StringUtils.isNotEmpty(form.getBusyDestination())){
				/* 入力チェック */
				if(!checkBusyDestination(form, list, result, i)){
					exist = false;
				}
			}

			/** 課金先－拠点 **/
			if(form.getChargeAssociationBranchId().length() != 3){
				/* 3桁チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].chargeAssociationBranchId",
						MessageFormat.format(properties.getProperty("size.message"),"3"));
				exist = false;

			} else if(!form.getChargeAssociationBranchId().matches("^([0-9]+)?$")){
				/* 文字形式チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].chargeAssociationBranchId",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;
			}

			/** 課金先－親店部課 **/
			if(form.getChargeAssociationParentSectionId().length() != 5){
				/* 3桁チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].chargeAssociationParentSectionId",
						MessageFormat.format(properties.getProperty("size.message"),"5"));
				exist = false;

			} else if(!form.getChargeAssociationParentSectionId().matches("^([0-9]+)?$")){
				/* 文字形式チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].chargeAssociationParentSectionId",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;
			}
/** 課金先－店部課 **/
			if(form.getChargeAssociationSectionId().length() != 5){
				/* 3桁チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].chargeAssociationSectionId",
						MessageFormat.format(properties.getProperty("size.message"),"5"));
				exist = false;

			} else if(!form.getChargeAssociationSectionId().matches("^([0-9]+)?$")){
				/* 文字形式チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].chargeAssociationSectionId",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;
			}

			/** PhoneButtonTemplate  **/
			if(StringUtils.isEmpty(form.getPhoneButtonTemplete())){
				/* Emptyチェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].phoneButtonTemplete",
						properties.getProperty("org.hibernate.validator.constraints.NotEmpty.message"));
				exist = false;

			} else if(!appCommonService.phoneTemplateExistCheck(form.getTelTypeModel(),form.getPhoneButtonTemplete(),form.getBranchTelId())){
				addError(result, "userAndTelListUpdateForm", "gridForm[" + i + "].phoneButtonTemplete",
						properties.getProperty("exist.error"),"PhoneButtonTemplate");
				exist = false;
			}

			/** 拠点（電話機） **/
			if(StringUtils.isEmpty(form.getBranchTelId())){
				/* Emptyチェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].branchTelId",
						properties.getProperty("org.hibernate.validator.constraints.NotEmpty.message"));
				exist = false;
			}

			/** 店部課（電話機） **/
			if(StringUtils.isEmpty(form.getCompanySectionTelId())){
				/* Emptyチェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].companySectionTelId",
						properties.getProperty("org.hibernate.validator.constraints.NotEmpty.message"));
				exist = false;

			}else if(!form.getBranchTelId().equals(form.getOrgBranchTelId())){
				/** 拠点（電話機）シェアードラインチェック **/

				/* ラインに紐づく電話の拠点チェック */
				if(appCommonService.telSharedLineCheck(new BigDecimal(form.getTelId()))){
					addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].branchTelId",
							properties.getProperty("branch.update.shared.line.tel.error") );
					exist = false;
				}
			}

			/** Css **/
			if(StringUtils.isEmpty(form.getCallingSearchSpaceName())){
				/* Emptyチェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].callingSearchSpaceName",
						properties.getProperty("org.hibernate.validator.constraints.NotEmpty.message"));
				exist = false;
			}

			/** 拡張モジュール **/
			if(StringUtils.isEmpty(form.getAddonModuleName1()) &&
			   StringUtils.isNotEmpty(form.getAddonModuleName2())){
				/* 拡張モジュール１＝NULL AND 拡張モジュール２<>NULL */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].addonModuleName1",
						properties.getProperty("select.notEmpty.error"));
				exist = false;

			} else if (StringUtils.isNotEmpty(form.getAddonModuleName1()) && StringUtils.isNotEmpty(form.getAddonModuleName2()) &&
					   !form.getAddonModuleName1().equals(form.getAddonModuleName2())){
				/* 拡張モジュール１＜＞拡張モジュール２ */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].addonModuleName2",
						properties.getProperty("addon.module.name.same.error"));
				exist = false;
			}


			/** LineTextLabel **/
			if(!form.getLineTextLabel().matches("^([ -~｡-ﾟ&&[^%\\[\\]\"\\\\&]]*+)?$")){
				/* 文字形式チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].lineTextLabel",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;
			}

			/** 不応答転送 **/
			if(!form.getNoansDestination().matches("^([*#0-9]+)?$")){
				/* 文字形式チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].noansDestination",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;

			}

			/** ExternalPhoneNumber **/
			if(!form.getExternalPhoneNumberMask().matches("^([*#X0-9]+)?$")){
				/* 文字形式チェック */
				addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].externalPhoneNumberMask",
						properties.getProperty("javax.validation.constraints.Pattern.message"));
				exist = false;

			}

			i++;
		}
		return exist;
	}

	/**
	 * 話中転送先入力チェック
	 *
	 * @param form ユーザーと電話機一覧フォーム
	 * @param list 検索条件リスト（フォーム）
	 * @param result BindingResult
	 * @param i 行数
	 * @return チェックフラグ True:エラー無し False:エラー有り
	 */
	private boolean checkBusyDestination(UserAndTelUpdateForm form, List<UserAndTelUpdateForm> list, BindingResult result, int i){

		String busyDest = form.getBusyDestination();

		/* 半角英数字チェック */
		if(!busyDest.matches("^([*#0-9]+)?$")){
			addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].busyDestination",
					properties.getProperty("pattern.error"));
			return false;
		}

		/* 話中転送先存在チェック */
		Map<String, Object> lineInfo = appCommonService.getBusyDestinationInfo(busyDest);
		if(lineInfo == null){
			addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].busyDestination",
					properties.getProperty("exist.error.entry") , "話中転送先"  );
			return false;
		}

		/* 内線番号と同じかチェックします */
		if(busyDest.equals(form.getDirectoryNumber())){
			addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].busyDestination",
					properties.getProperty("directory.number.each.error"));
			return false;
		}

		/* 転送先が画面の更新先に存在するか確認する */
		String vm = null;
		for(UserAndTelUpdateForm dirPrm :  list){
			if(dirPrm.isChecked() && dirPrm.getDirectoryNumber().equals(busyDest)){
				vm = dirPrm.getVoiceMailFlg();
			}
		}

		/* 今回の更新対象に転送先が設定されていた場合は画面のVMフラグがチェック付いているか確認する  */
		if((StringUtils.isNotEmpty(vm) && vm.equals(Constants.VOICE_MAIL_FLG_ON)) ||
		   (StringUtils.isEmpty(vm) && lineInfo.get("vmFlg").toString().equals(Constants.VOICE_MAIL_FLG_ON))) {
			/* 画面に存在した且つ、チェックがされていた場合 OR 画面に存在しない且つ、チェックがされていた場合 */
			addError(result,"userAndTelListUpdateForm", "gridForm[" + i + "].busyDestination",
					properties.getProperty("voice.mail.fwd.busy.destination.error"));
			return false;
		}

		return true;
	}

	/**
	 * FormデータをModelにセットします
	 *
	 * @param list フォームデータ
	 * @param updateList モデル
	 * @throws InvocationTargetException 例外処理
	 * @throws IllegalAccessException  例外処理
	 */
	private void setParam(List<UserAndTelUpdateForm> list,List<UserAndTelUpdateModel> updateList)
			throws IllegalAccessException, InvocationTargetException{
		for(UserAndTelUpdateForm form : list){
			/* チェックがついていなかった場合 */
			if(!form.isChecked())continue;
			/* インスタンス化 */
			UserAndTelUpdateModel params = new UserAndTelUpdateModel();
			/* Form→Modelコピー処理 */
			BeanUtils.copyProperties(params,form);
			/* リストにセット */
			updateList.add(params);
		}
	}

	/**
	 * 成功メッセージをセットする
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
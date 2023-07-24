/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AjaxController.java
 *
 * @date 2013/08/28
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.util.OptionsUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.service.AppCommonService;

/**
 * <pre>
 * AJAX用コントローラークラス
 * プルダウンの値を取得する。
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/28 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/28
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController extends BaseController {

	@Autowired
	private AppCommonService appCommonService;

	/**
	 * 店部課リスト取得
	 * 
	 * @param id 拠点Id
	 * @param frontFlg 先頭行デフォルトラベルフラグ True:セットする False:セットしない
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getSectionList", method=RequestMethod.POST)
	public ModelAndView getSectionList(
			@RequestParam("id") String id,
			@RequestParam("frontFlg") boolean frontFlg )throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<LabelValueModel> list = new ArrayList<LabelValueModel>();

		if(StringUtils.isNotEmpty(id)){
			/* リスト取得 */
			list = appCommonService.getSectionList(id);
		}

		if(frontFlg){
			list = OptionsUtil.addBlankHeadLVBList(list,Constants.SECTION_ALL_LABEL);
		}
		map.put("data", list);

		return getJsonView(map);
	}

	/**
	 * ピックアップグループリスト取得
	 * 
	 * @param branchId 拠点ID
	 * @param sectionCompanyId 店部課-会社ID
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getPickupGroupList", method=RequestMethod.POST)
	public ModelAndView getPickupGroupList(
			@RequestParam("branchId") String branchId,
			@RequestParam("sectionCompanyId") String sectionCompanyId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String[] sectionCompnayArray = sectionCompanyId.split(",");

		List<LabelValueModel> list = new ArrayList<LabelValueModel>();

		if(StringUtils.isNotEmpty(sectionCompnayArray[0])){
			/* ピックアップグループを取得 */
			list = appCommonService.getPickupGroupList(branchId ,sectionCompnayArray[1]);
		}

		/* リスト取得 */
		map.put("data",OptionsUtil.addBlankHeadLVBList(list,Constants.ALL_LABEL));

		return getJsonView(map);
	}

	/**
	 * PhoneButtonTempleteリスト取得
	 * 
	 * @param name 電話機名
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getPhoneButtonTempleteList", method=RequestMethod.POST)
	public ModelAndView getPhoneButtonTempleteList(
			@RequestParam("name") String name) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<LabelValueModel> list = new ArrayList<LabelValueModel>();

		if(StringUtils.isNotEmpty(name)){
			/* PhoneButtonTempleteListを取得 */
			list = appCommonService.getPhoneTempleteList(name);
		}

		/* リスト取得 */
		map.put("data",OptionsUtil.addBlankHeadLVBList(list,Constants.ALL_LABEL));

		return getJsonView(map);
	}

}


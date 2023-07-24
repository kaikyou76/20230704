/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditController.java
 *
 * @date 2013/08/29
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.ksc.controller.BaseController;
import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.ksc.util.OptionsUtil;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.TelLumpEditService;
import jp.co.netmarks.util.AppHtmlHelper;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.TelLumpEditModel;

import jp.co.netmarks.model.form.TelLumpEditListForm;
import jp.co.netmarks.model.form.TelLumpEditForm;
import jp.co.netmarks.model.form.list.TelLumpEditUpdateForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * <pre>
 * 電話機一括登録画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/29 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/29
 */
@Controller
@RequestMapping("/telLumpEdit")
public class TelLumpEditController extends BaseController {

	@Autowired
	private TelLumpEditService telLumpEditService;

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form TelLumpEditForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(TelLumpEditForm form) throws Exception {

		/* フォーカスを設定 */
		form.setFocus("csvFile");

		/** プルダウンのリスト取得 **/

		/* 拠点リスト（クラスIDあり）を取得 */
		List<LabelValueModel> branchClusterList =
				appCommonService.getBranchList(Constants.COM_FLG_ON);

		/* 電話機リストを取得 */
		List<LabelValueModel> typeModelList = appCommonService.getTypeModelList();

		/* 拠点（電話機）リストをセット（grid用） */
		form.setBranchListJson(AppHtmlHelper.convertToJson(OptionsUtil.addBlankHeadLVBList(branchClusterList,Constants.ALL_LABEL)));

		/* 所属店部課（電話機）リストをセット（grid用） */
		form.setSectionListJson(AppHtmlHelper.convertToJson(appCommonService.getDynamicSectionMap()));
		/* 通話録音リストをセット（grid用） */
		form.setLoggerDataListJson(
				AppHtmlHelper.convertToJson(OptionsUtil.convertToOptionsCollection(
						Constants.LOGGER_DATA_SELECT_LABEL, Constants.LOGGER_DATA_SELECT_VALUE)));
		/* 電話機リストをセット（grid用） */
		form.setTelTypeModelListJson(
				AppHtmlHelper.convertToJson(OptionsUtil.addBlankHeadLVBList(typeModelList, Constants.ALL_LABEL)));

		/* PhoneButtonTempleteリストをセット（grid用） */
		form .setPhoneButtonTempleteListJson(AppHtmlHelper.convertToJson(appCommonService.getDynamicPhoneTempleteMap()));

		return "view/telLumpEdiit/telLumpEdiit";
	}

	/**
	 * Grid行表示
	 * ※初期表示時は空データを表示
	 * ※CSVファイル読み込み時はCSVデータを表示
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/setDefaultRows", method=RequestMethod.POST)
	public ModelAndView setDefaultRows(HttpServletRequest req, HttpServletResponse res) throws Exception{

		@SuppressWarnings("unchecked")
		Map<String, Object> csvmap = (Map<String, Object>) req.getSession().getAttribute("csvData");

		if (csvmap != null){
			req.getSession().removeAttribute("csvData");
			return getJsonView(csvmap);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		List<TelLumpEditModel> telList = new ArrayList<TelLumpEditModel>();

		/* 一括登録可能行数取得 */
		int cnt = Integer.parseInt(properties.getProperty("tellumpedit.rowcnt"));

		for(int i =0;i<cnt;i++){
			telList.add(new TelLumpEditModel());
		}

		map.put("total", cnt);
		map.put("rows", telList);

		return getJsonView(map);

	}

	/**
	 * 選択したCSVファイルをグリッドに表示ボタン押下時
	 *
	 * @param file MultipartFile
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/setCsvValue", method=RequestMethod.POST)
	public ModelAndView setCsvValue(@RequestParam("csvFile") MultipartFile file,
			HttpServletRequest req, HttpServletResponse res) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		List<TelLumpEditModel> telList = new ArrayList<TelLumpEditModel>();
		Map<String, String> messageMap = new HashMap<String, String>();
		String errMsg = "";
		/* ファイル取得クラス */
		BufferedReader br = null;

		try{
			/* ファイルデータの取得 */
			br = new BufferedReader(new InputStreamReader(file.getInputStream(), "Shift_JIS"));
			String phoneData = "";
			String[] phoneCols = null;

			boolean ExistData = false;

			/* 一括登録可能行数取得 */
			int cnt = Integer.parseInt(properties.getProperty("tellumpedit.rowcnt"));
			/* CSV既定カラム数取得 */
			int colCnt = Integer.parseInt(properties.getProperty("tellumpedit.csvcolcnt"));


			for(int i =0;i<cnt;i++){

				TelLumpEditModel model = new TelLumpEditModel();

				/* 取込データが存在する場合 */
				if((phoneData = br.readLine()) != null) {
					/* 取込データ存在あり */
					ExistData = true;

					/* 取込データを分割 */
					phoneCols = phoneData.split(",");

					/* 取込データのカラム数が正しくない場合 */
					if(phoneCols.length != colCnt){
						/* エラーメッセージセット */
						errMsg = properties.getProperty("csv.error.columns.format");
						break;
					}
					/* CSVデータをモデルへセット */
					setCsvToModel(phoneCols,model);
				}

				/* 取込データが1件も存在しなかった場合 */
				if(!ExistData) {
					/* エラーメッセージセット */
					errMsg = properties.getProperty("csv.error.nodata");
					break;
				}
				telList.add(model);
			}

			/* 未取込データが存在する(51行目が存在する)場合 */
			if(br.readLine() != null && StringUtils.isEmpty(errMsg)){
				/* エラーメッセージセット */
				errMsg = properties.getProperty("csv.error.rows.over");
			}

			/* エラーメッセージが存在する場合 */
			if(StringUtils.isNotEmpty(errMsg)) {
				/* メッセージをセット */
				messageMap.put("errorMessage", errMsg);
			} else {
				/* CSV取込データをマッピング */
				map.put("total", cnt);
				map.put("rows", telList);

				/* CSV取込データをセッションにセット */
				req.getSession().setAttribute("csvData", map);
			}
		} catch(Exception e) {
			throw e;
		} finally {
			/* クローズ */
			if(br != null) br.close();
		}

		MappingJacksonJsonView mjsv = new MappingJacksonJsonView();
		mjsv.setAttributesMap(messageMap);
		mjsv.setContentType("text/html");
		return new ModelAndView(mjsv);

	}
	
/**
	 * 一括登録ボタン押下時
	 *
	 * @param form TelLumpEditListForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/lumpEdit", method=RequestMethod.POST)
	public ModelAndView lumpEdit(
			TelLumpEditListForm form ,BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<TelLumpEditModel> editTelList = new ArrayList<TelLumpEditModel>();

		/* 値が設定された行を処理対象にする */
		for (TelLumpEditUpdateForm updateForm : form.getGridForm()) {
			updateForm.setChecked(editCheck(updateForm));
		}

		/* バリデーションエラー */
		validateGridListForm(form,result);
		if(result.hasErrors()) {
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 存在チェック */
		if(!existCheck(form.getGridForm(), result, editTelList)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 電話機登録処理 */
		Map<String, String> messageMap =telLumpEditService.editTel(editTelList);

		/* メッセージをセット */
		setSuccesMes(form,messageMap.get("sucsessMessage"),messageMap.get("errorMessage")
				,messageMap.get("cucmErrorMessage"),messageMap.get("lineErrorMessage"));

		return getJsonView(map);
	}

	/**
	 * 電話機登録要否判定
	 *
	 * @param form 電話機追加用フォーム
	 * @return boolean 判定結果
	 */
	private boolean editCheck(TelLumpEditUpdateForm form) {

		boolean editflg = false;

		/* 入力（設定）値が存在した場合、処理対象とする */
		if(StringUtils.isNotEmpty(form.getBranchTelId())
				|| StringUtils.isNotEmpty(form.getCompanySectionTelId())
				|| StringUtils.isNotEmpty(form.getTelTypeModel())
				|| StringUtils.isNotEmpty(form.getPhoneButtonTemplete())
				|| StringUtils.isNotEmpty(form.getMacAddress())
				|| StringUtils.isNotEmpty(form.getDirectoryNumber())
				|| StringUtils.isNotEmpty(form.getDialinNumber())
				|| StringUtils.isNotEmpty(form.getChargeAssociationBranchId())
				|| StringUtils.isNotEmpty(form.getChargeAssociationParentSectionId())
				|| StringUtils.isNotEmpty(form.getChargeAssociationSectionId())){
			editflg = true;
		}
		return editflg;
	}


	/**
	 * 読み込んだCSVデータをModelにセットします。
	 *
	 * @param csvData CSVデータ
	 * @param model CSVデータ格納用モデル
	 */
	private void setCsvToModel(String[] csvData, TelLumpEditModel model) {

		model.setChanged(true);
		model.setBranchTelId(csvData[0]);
		model.setCompanySectionTelId(csvData[1] + "," + csvData[2]);
		model.setTelTypeModel(csvData[3]);
		model.setPhoneButtonTemplete(csvData[4]);
		model.setMacAddress(csvData[5]);
		model.setDirectoryNumber(csvData[6]);
		model.setLoggerData(csvData[7]);
		model.setDialinNumber(csvData[8]);
		model.setChargeAssociationBranchId(csvData[9]);
		model.setChargeAssociationParentSectionId(csvData[10]);
		model.setChargeAssociationSectionId(csvData[11]);
	}


	/**
	 * 存在チェック
	 *
	 * @param list フォームデータ
	 * @param result BindingResult
	 * @param updateList モデル
	 * @return True:エラーなし False:エラーあり
	 * @throws Exception 例外処理
	 */
	private boolean existCheck(List<TelLumpEditUpdateForm> list, BindingResult result,
			List<TelLumpEditModel> updateList) throws Exception{

		boolean check = true;
		int i = 0;

		for(TelLumpEditUpdateForm form : list){

			/* チェックがついていなかった場合 */
			if(!form.isChecked()){
				i++;
				continue;
			}

			TelLumpEditModel params = new TelLumpEditModel();

			/* MACアドレス存在チェック */
			if(appCommonService.macAddressExistCheck(form.getMacAddress())){
				addError(result, "gridForm", "gridForm[" + i + "].macAddress",
						properties.getProperty("exist.error.entry.existing"),"MACアドレス");
				check = false;
			}

			/* CallingSearchSpace存在チェック */
			if(!telLumpEditService.cssExistCheck(form)){
				addError(result, "gridForm", "gridForm[" + i + "].companySectionTelId",
						properties.getProperty("exist.error"),"CallingSearchSpace");
				check = false;
			}

			/* DevicePool存在チェック */
			if(!telLumpEditService.devicePoolExistCheck(form)){
				addError(result, "gridForm", "gridForm[" + i + "].branchTelId",
						properties.getProperty("exist.error"),"DevicePool");
				check = false;
			}

			/* PhoneButtonTemplate存在チェック */
			if(!appCommonService.phoneTemplateExistCheck(form.getTelTypeModel(),form.getPhoneButtonTemplete(),form.getBranchTelId())){
				addError(result, "gridForm", "gridForm[" + i + "].phoneButtonTemplete",
						properties.getProperty("exist.error"),"PhoneButtonTemplate");
				check = false;
			}

			/* ラインIDの取得 */
			BigDecimal lineId = appCommonService.getLineId(form.getDirectoryNumber());

			if(lineId != null){

				/* シェアードラインの拠点整合チェック */
				if(!telLumpEditService.branchCheck(form.getBranchTelId(), lineId.toString())){
					addError(result, "gridForm", "gridForm[" + i + "].directoryNumber",
							properties.getProperty("tel.linking.line.shared.error"));
					check = false;
				}

				form.setLineId(lineId.toString());
			} else {
				form.setLineId(Constants.INPUT_DATA_STRING_NUMBER);
			}

			if(!check){
				i++;
				continue;
			}

			/* BigDecimal変換エラーを回避するダミー値をセット */
			form.convertToAvoidConversionExBean();

			/* Form→Modelコピー処理 */
			BeanUtils.copyProperties(params,form);

			/* リストにセット */
			updateList.add(params);

			i++;
		}

		return check;
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

	
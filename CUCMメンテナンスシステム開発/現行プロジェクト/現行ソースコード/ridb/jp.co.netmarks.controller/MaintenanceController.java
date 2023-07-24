/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceController.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.util.OptionsUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.MaintenanceSearchModel;
import jp.co.netmarks.model.MaintenanceUpdateModel;
import jp.co.netmarks.model.form.MaintenanceSearchForm;
import jp.co.netmarks.model.form.MaintenanceUpdateForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.DataShiftService;
import jp.co.netmarks.service.MaintenanceService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * メンテナンス画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
@Controller
@RequestMapping("/maintenance")
public class MaintenanceController extends BaseController{

	private static Log log = LogFactory.getLog(MaintenanceController.class);

	@Autowired
	private MaintenanceService maintenanceService;

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
    private Properties properties;

	@Autowired
	private DataShiftService dataShiftService;

	/**
	 * 初期表示
	 *
	 * @param form  MaintenanceSearchForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(MaintenanceSearchForm form) throws Exception {

		/* フォーカスを設定 */
		form.setFocus("searchBranchId");

		/* 拠点リストを取得 */
		List<LabelValueModel> branchList = appCommonService.getBranchList();

		/* 拠点(リストをセット */
		form.setBranchList(
				OptionsUtil.addBlankHeadLVBList(branchList,Constants.BRANCH_ALL_LABEL));

		/** クラスタリスト取得 **/
		form.setClusterList(appCommonService.getCluster());

		/** 取込テーブルリスト取得 **/
		form.setTableList(OptionsUtil.convertToOptionsCollection(
				Constants.FETCH_TABLE_LABEL, Constants.FETCH_TABLE_VALUE));

		return "view/maintenance/maintenance";
	}

	/**
	 * 検索処理
	 *
	 * @param form  MaintenanceSearchForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView search(
			MaintenanceSearchForm form,
			BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* 検索条件パラーメータをセット */
		MaintenanceSearchModel params = new MaintenanceSearchModel();
		BeanUtils.copyProperties(params,form);

		int cnt = maintenanceService.getBranchTotal(params);

		map.put("total", cnt);
		/* 検索結果を取得 */
		map.put("rows", maintenanceService.getBranchList(params));

		return getJsonView(map);
	}

	/**
	 * 拠点を削除ボタン押下時
	 *
	 * @param form MaintenanceUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/deleteBranch", method=RequestMethod.POST)
	public ModelAndView deleteBranch(
			@Valid MaintenanceUpdateForm form,  BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		/* 入力チェック */
		if(result.hasErrors()) {
			map.put("errors", result.getAllErrors());
			return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		params = setParams(form);

		/* 会社コード、店部課コードのダミーデータ削除 */
		params.setCompanyId(null);
		params.setSectionId(null);

		/* 拠点の存在チェック */
		if(existCheck(params,result,null)){
			map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 拠点削除処理 */
		Map<String, String> messageMap =maintenanceService.deleteBranch(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);

	}

	/**
	 * 拠点-店部課紐付き削除ボタン押下時
	 *
	 * @param form MaintenanceUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/deleteAssociate", method=RequestMethod.POST)
	public ModelAndView deleteAssociate(
			@Valid MaintenanceUpdateForm form, BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		/* 入力チェック */
		if(result.hasErrors()) {
			map.put("errors", result.getAllErrors());
			return getJsonView(map);
		}

		/* Form→Modelコピー処理 */
		params = setParams(form);

		/* 存在チェック */
		if(existCheckForDeleteAssociate(params,result)){
			map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 拠点-店部課紐付き削除処理 */
		Map<String, String> messageMap =maintenanceService.deleteAssociate(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

/**
	 * 取込ボタン押下時
	 *
	 * @param file 取込テーブルデータファイル
	 * @param tableName 取込対象テーブル名
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/fetchTableData", method=RequestMethod.POST)
	public ModelAndView fetchTableData(@RequestParam("csvFile") MultipartFile file,
			@RequestParam("tableName") String tableName,HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String, String> messageMap = new HashMap<String, String>();
		MappingJacksonJsonView mjsv = new MappingJacksonJsonView();

		/* 取込対象テーブル名をファイル名に指定 */
		String fileName = tableName + ".csv";
		/* ファイル保存パス取得 */
		String filePath = properties.getProperty("tmporary.directory.path") + "/" + fileName;

		try{
			/* 取込対象ファイルを保存 */
			File saveFile = new File(filePath);
			FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);

			/* 対象ファイルを取込む */
			messageMap = maintenanceService.copyCsvData(filePath,tableName);

		} catch (Exception e){
			messageMap.put("errorMessage", properties.getProperty("fetch.error"));
		}

		mjsv.setAttributesMap(messageMap);
		mjsv.setContentType("text/html");
		return new ModelAndView(mjsv);
	}

	/**
	 * CUCMマスタパラメータの取得ボタン押下時
	 *
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getCucmParam", method=RequestMethod.POST)
	public ModelAndView getCucmParam() throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* ロックファイル取得 */
		File lockFile = new File(properties.getProperty("lock.file.path"));

		/* ロックファイルの存在確認 */
		if(!lockFile.exists()){
			/* コマンドの設定 */
			String command = properties.getProperty("batch.command.cucm.shell.dir")
					+ properties.getProperty("batch.command.get.masterparameter.shell.file");

			/* CUCMマスタパラメータの取得 */
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.start();
			map.put("message", properties.getProperty("batch.execute"));

		} else {
			map.put("message", properties.getProperty("batch.execute.error"));
		}
		return getJsonView(map);
	}

	/**
	 * 双方向チェック用データの取得ボタン押下時
	 *
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getCheckData", method=RequestMethod.POST)
	public ModelAndView getCheckData() throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		/* ロックファイル取得 */
		File lockFile = new File(properties.getProperty("lock.file.path"));

		/* ロックファイルの存在確認 */
		if(!lockFile.exists()){
			/* コマンドの設定 */
			String command = properties.getProperty("batch.command.cucm.shell.dir")
					+ properties.getProperty("batch.command.get.consistencyInfo.shell.file");

			/* 双方向チェック用データの取得 */
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.start();
			map.put("message", properties.getProperty("batch.execute"));

		} else {
			map.put("message", properties.getProperty("batch.execute.error"));
		}
		return getJsonView(map);
	}

	/**
	 * データ移行ボタン押下時
	 *
	 * @param file BATファイル
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/dataShift", method=RequestMethod.POST)
	public ModelAndView dataShift(
			@RequestParam("csvDataShiftFile") MultipartFile file,
			HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		/* メッセージ */
		Map<String, String> messageMap = new HashMap<String, String>();
		/* JsonView */
		MappingJacksonJsonView mjsv = new MappingJacksonJsonView();

		/* データ移行処理 */
		messageMap = dataShiftService.dataShift(file);

		/* メッセージをセット */
		mjsv.setAttributesMap(messageMap);

		/* 返り値の型をセット */
		mjsv.setContentType("text/html");

		return new ModelAndView(mjsv);
	}

	/**
	 * 更新用のFormの値をModelにセットします。
	 *
	 * @param form 更新用フォーム
	 * @return 更新用モデル
	 */
	private MaintenanceUpdateModel setParams(MaintenanceUpdateForm form){
		/* インスタンス化 */
		MaintenanceUpdateModel params = new MaintenanceUpdateModel();

		params.setBranchId(form.getBranchId());						// 拠点コード
		params.setBranchName(form.getBranchName());					// 拠点名
		params.setClusterId(new BigDecimal(form.getClusterId()));	// クラスタID
		params.setCompanyId(form.getCompanyId());					// 会社コード
		params.setSectionId(form.getSectionId());					// 店部課コード

		return params;
	}

	/**
	 * 存在チェック(拠点削除用)
	 *
	 * @param params 拠点削除モデル
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
				addError(result,"MaintenanceUpdateForm", fieldName,
						MessageFormat.format(properties.getProperty("exist.error"), "選択した拠点コード"));
			}
			return true;
	   	}

		/* 拠点-店部課紐付き存在チェック */
		if(appCommonService.branchSectionExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			if(StringUtils.isEmpty(fieldName)){
				addError(result,properties.getProperty("branch.linking.section.error"));
			} else {
				addError(result,"MaintenanceUpdateForm", fieldName,
						MessageFormat.format(properties.getProperty("exist.error.entry.existing"), "拠点-店部課紐付き"));
			}
			return true;
	   	}
		return false;
	}

	/**
	 * 存在チェック(拠点-店部課紐付き削除用)
	 *
	 * @param params 拠点-店部課紐付き削除モデル
	 * @param result BindingResult
	 * @return True:エラーあり False:エラーなし
	 */
	private boolean existCheckForDeleteAssociate(MaintenanceUpdateModel params, BindingResult result){

		/* 拠点存在チェック */
		if(!appCommonService.branchExistCheck(params.getBranchId())){
			addError(result,MessageFormat.format(properties.getProperty("exist.error"), "選択した拠点"));
			return true;
	   	}

		/* 拠点-店部課紐付き存在チェック */
		if(!appCommonService.branchSectionExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			addError(result,properties.getProperty("delete.associate.error"));
			return true;
	   	}

		/* 電話機紐付きチェック */
		if(maintenanceService.associatePhoneExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			addError(result,properties.getProperty("branch.section.linking.phone.error"));
			return true;
	   	}

		return false;
	}

	/**
	 * 拠点リスト取得
	 *
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getBranchList", method=RequestMethod.POST)
	public ModelAndView getBranchList()throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		List<LabelValueModel> list = new ArrayList<LabelValueModel>();

		/* 拠点リストを取得 */
		List<LabelValueModel> branchList = appCommonService.getBranchList();
		/* 拠点(リストをセット */
		list = OptionsUtil.addBlankHeadLVBList(branchList,Constants.BRANCH_ALL_LABEL);

		map.put("data", list);

		return getJsonView(map);
	}

}
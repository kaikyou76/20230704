/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LogManagementController.java
 *
 * @date 2013/09/17
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.util.FileUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.LogManagementResultModel;
import jp.co.netmarks.model.form.LogManagementSearchForm;
import jp.co.netmarks.sort.LastModifiedComparator;
import jp.co.netmarks.sort.LengthComparator;
import jp.co.netmarks.sort.NameComparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ログ管理画面用コントロールクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/17 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/17
 */
@Controller
@RequestMapping("/logManagement")
public class LogManagementController extends BaseController{

	@Autowired
    private Properties properties;

	/**
	 * 初期表示
	 *
	 * @param form LogManagementSearchForm
	 * @return String 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(LogManagementSearchForm form) throws Exception {

		/* フォーカスを設定 */
		form.setFocus("txtLogWeb");

		return "view/logManagement/logManagement";
	}

	/**
	 * ログファイル取得処理
	 *
	 * @param form LogManagementSearchForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/getFileList", method=RequestMethod.POST)
	public ModelAndView getFileList(
			LogManagementSearchForm form,
			BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		int order = 1;

		List<LogManagementResultModel> resultList = new ArrayList<LogManagementResultModel>();
		List<File> searchedList = new ArrayList<File>();
		String dirDiv = form.getDirDiv();

		/* ディレクトリパスを取得 */
		String dirPath = getDirPath(dirDiv);

		if(dirPath.isEmpty()){
			return getJsonView(map);
		}

		/* ログファイルを取得 */
		List<File> fileList = FileUtil.fileListup(dirPath);

		/* ソート順を取得 */
		if(form.getOrder() != null){
			if(form.getOrder().equals("desc")){
				order = -1;
			}
		}

		/* 対象ログファイルを検索 */
		searchedList = searchFile(fileList,form);

		/* ソート */
		if(form.getSort() != null){
			switch (form.getSort()){
			case "fileName":{
				Collections.sort(searchedList, new NameComparator(order));
				break;
			}
			case "fileSize":{
				Collections.sort(searchedList, new LengthComparator(order));
				break;
			}
			case "updateDate":{
				Collections.sort(searchedList, new LastModifiedComparator(order));
				break;
			}
			default:Collections.sort(searchedList, new NameComparator(order));
			}
		} else {
			Collections.sort(searchedList, new NameComparator(order));
		}

		/* ページング */
		int i = 0;
		int startCnt = (form.getPage() - 1) * form.getRows();
		List<File> editFileList = new ArrayList<File>();
		for(i=startCnt;i<(form.getPage() * form.getRows());i++){
			if (searchedList.size() <= i){
				break;
			}
			editFileList.add(searchedList.get(i));
		}

		/* ログファイル数を取得 */
		int cnt = searchedList.size();

		/* ログファイル情報をModelにセット */
		setModelList(editFileList,resultList,form);

		/* ログファイル数をセット */
		map.put("total", cnt);
		/* ログファイル一覧情報をセット */
		map.put("rows", resultList);

		return getJsonView(map);
	}

	/**
	 * 選択ファイルダウンロード
	 *
	 *@param request  HttpServletRequest
	 *@param response HttpServletResponse
	 *@throws Exception 例外処理
	 */
	@RequestMapping(value="/downloadFile", method=RequestMethod.POST)
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{

		/* ファイル名を取得 */
		String fileName = request.getParameter("hdnFileName");

		/* ディレクトリパスを取得 */
		String dirPath = getDirPath(request.getParameter("hdnDirDiv"));

		File downloadFile = new File(dirPath + "/" +fileName);

		/* ファイルをダウンロード */
		FileUtil.downloadFile(request, response, downloadFile);
	}

	/**
	 * ファイル格納ディレクトリパス取得
	 *
	 * @param dirDiv 選択ディレクトリ識別区分
	 * @return dirPath ディレクトリパス
	 */
	private String getDirPath(String dirDiv) {
		String dirPath;

		switch(dirDiv){
			case Constants.DIR_LOG_WEB              :dirPath = properties.getProperty("dir.log.web");              break;
			case Constants.DIR_LOG_BATCH            :dirPath = properties.getProperty("dir.log.batch");            break;
			case Constants.DIR_EXPORT_WEB           :dirPath = properties.getProperty("dir.export.web");           break;
			case Constants.DIR_EXPORT_BATCH_BK      :dirPath = properties.getProperty("dir.export.batch.bk");      break;
			case Constants.DIR_EXPORT_BATCH_EXCOOP  :dirPath = properties.getProperty("dir.export.batch.excoop");  break;
			case Constants.DIR_EXPORT_BATCH_LINELIST:dirPath = properties.getProperty("dir.export.batch.linelist");break;
			default:dirPath=null;break;
		}
		return dirPath;
	}

	/**
	 * Grid表示ファイルデータをModelにセットします。
	 *
	 * @param fileList ファイルリスト
	 * @param resultList 検索結果格納モデルリスト
	 * @param form LogManagementSearchForm
	 * @throws Exception 例外処理
	 */
	private void setModelList(List<File> fileList ,List<LogManagementResultModel> resultList,
			LogManagementSearchForm form) throws Exception {

		DateFormat gridDf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		Date updateTime = new Date();

		try{

			for(File file:fileList){
				updateTime = new Date(file.lastModified());

				/* ファイル情報をModelにセット */
				LogManagementResultModel resultModel = new LogManagementResultModel();
				resultModel.setDirDiv(form.getDirDiv());
				resultModel.setFileName(file.getName());
				resultModel.setFileSize(Long.toString(file.length()));

				resultModel.setUpdateDate(gridDf.format(updateTime).toString());

				/* ファイル情報格納ModelをListにセット */
				resultList.add(resultModel);

			}
		} catch (Exception e) {

		}
	}

	/***
	 *ファイル検索処理
	 *
	 * @param fileList ファイルリスト
	 * @param form LogManagementSearchForm
	 * @return searchedList 検索結果ファイルリスト
	 * @throws Exception
	 */
	private List<File> searchFile(List<File> fileList ,	LogManagementSearchForm form) throws Exception{

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date searchUpdateTime = null;
		Date updateTime = new Date();
		String strSearchTime = null;
		String strUpdateTime = null;
		boolean searchFlg = false;
		List<File> searchedList = new ArrayList<File>();

		try{

			/* 検索条件（更新日）が入力されていた場合 */
			if(!form.getUpdateDate().isEmpty()){
				searchUpdateTime = df.parse(form.getUpdateDate());
				strSearchTime = df.format(searchUpdateTime);

				/* 検索フラグ：true */
				searchFlg = true;
			}

			for(File file:fileList){
				updateTime = new Date(file.lastModified());
				strUpdateTime = df.format(updateTime);

				/* 検索不要の場合*/
				if(!searchFlg){
					/* 検索条件（更新日）にファイルの更新日を設定*/
					strSearchTime = strUpdateTime;
				}

				/* 検索条件（更新日）とファイルの更新日が一致しない場合*/
				if(!strSearchTime.equals(strUpdateTime)){
					continue;
				}

				/* ファイルを検索結果Listにセット */
				searchedList.add(file);
			}
		} catch (Exception e) {

		}

		return searchedList;
	}

}


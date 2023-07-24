/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LogManagementDownloadForm.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form.list;

import java.io.File;
import java.io.Serializable;

import jp.co.ksc.model.form.list.BaseGridRowForm;

/**
 * <pre>
 * ログ管理画面リストダウンロード用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/18
 */
public class LogManagementDownloadForm extends BaseGridRowForm implements Serializable{

	private static final long serialVersionUID = 1L;

	/* ファイル */
	private File logFile = null;
	/* ファイル名 */
	private String fileName = null;
	/* ファイルサイズ */
	private String fileSize = null;
	/* ファイル更新日 */
	private String updateDate = null;

	/**
	 * Validateを回避するダミー値をセットする
	 */
	@Override
	public void convetToNoValidateBean() {

	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return logFile
	 */
	public File getLogFile() {
		return logFile;
	}


	/**
	 * @param logFile セットする logFile
	 */
	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}


	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * @param fileName セットする fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * @return fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}


	/**
	 * @param fileSize セットする fileSize
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}


	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}


	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LogManagementDbbkResultModel.java
 *
 * @date 2013/09/17
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import jp.co.ksc.model.AbstractSearchModel;

/**
 * <pre>
 * ログ管理検索結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/17 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/17
 */
public class LogManagementResultModel extends AbstractSearchModel implements Serializable{


	private static final long serialVersionUID = 7667457696668887815L;

	/* 選択ディレクトリ識別Div */
	private String dirDiv = StringUtils.EMPTY;
	/* ファイル名 */
	private String fileName = StringUtils.EMPTY;
	/* ファイルサイズ */
	private String fileSize = StringUtils.EMPTY;
	/* ファイル更新日 */
	private String updateDate = StringUtils.EMPTY;


/*********************************************************************
 * generated getter/setter
**********************************************************************/


	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return dirDiv
	 */
	public String getDirDiv() {
		return dirDiv;
	}

	/**
	 * @param dirDiv セットする dirDiv
	 */
	public void setDirDiv(String dirDiv) {
		this.dirDiv = dirDiv;
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
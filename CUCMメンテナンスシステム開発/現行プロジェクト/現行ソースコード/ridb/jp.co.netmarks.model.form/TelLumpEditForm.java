/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditForm.java
 *
 * @date 2013/08/29
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 電話機一括登録画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/29 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/29
 */
public class TelLumpEditForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 4173944529813876593L;

	/* CSVファイル名 */
	private FileItem csvFileName       = null;

	/** ### List ### **/
	/* 拠点（電話機）リスト */
	private List<LabelValueModel> branchTelList           = null;
	/* 店部課（電話機）リスト */
	private List<LabelValueModel> sectionTelList          = null;
	/* 電話機 */
	private List<LabelValueModel> telTypeModelList        = null;
	/* PhoneButtonTemplete */
	private List<LabelValueModel> phoneButtonTempleteList  = null;
	/* 通話録音 */
	private List<LabelValueModel> loggerDataList  = null;

	/** ### JsonList ### **/
	/* 拠点セレクトタグ */
	private String branchListJson             = null;
	/* 店部課セレクトタグ */
	private String sectionListJson            = null;
	/* 電話機セレクトタグ */
	private String telTypeModelListJson        = null;
	/* PhoneButtonTempleteセレクトタグ */
	private String phoneButtonTempleteListJson = null;
	/* 通話録音セレクトタグ */
	private String loggerDataListJson         = null;


	/*********************************************************************
	 * generated getter/setter
	**********************************************************************/

	/**
	 * @return csvFileName
	 */
	public FileItem getCsvFileName() {
		return csvFileName;
	}
	/**
	 * @param csvFileName セットする csvFileName
	 */
	public void setCsvFileName(FileItem csvFileName) {
		this.csvFileName = csvFileName;
	}

	/**
	 * @return branchTelList
	 */
	public List<LabelValueModel> getBranchTelList() {
		return branchTelList;
	}

	/**
	 * @param branchTelList セットする branchTelList
	 */
	public void setBranchTelList(List<LabelValueModel> branchTelList) {
		this.branchTelList = branchTelList;
	}
	/**
	 * @return sectionTelList
	 */
	public List<LabelValueModel> getSectionTelList() {
		return sectionTelList;
	}
	/**
	 * @param sectionTelList セットする sectionTelList
	 */
	public void setSectionTelList(List<LabelValueModel> sectionTelList) {
		this.sectionTelList = sectionTelList;
	}
	/**
	 * @return telTypeModelList
	 */
	public List<LabelValueModel> getTelTypeModelList() {
		return telTypeModelList;
	}
	/**
	 * @param telTypeModelList セットする telTypeModelList
	 */
	public void setTelTypeModelList(List<LabelValueModel> telTypeModelList) {
		this.telTypeModelList = telTypeModelList;
	}
	/**
	 * @return phoneButtonTempleteList
	 */
	public List<LabelValueModel> getPhoneButtonTempleteList() {
		return phoneButtonTempleteList;
	}
	/**
	 * @param phoneButtonTempleteList セットする phoneButtonTempleteList
	 */
	public void setPhoneButtonTempleteList(
			List<LabelValueModel> phoneButtonTempleteList) {
		this.phoneButtonTempleteList = phoneButtonTempleteList;
	}
	/**
	 * @return branchListJson
	 */
	public String getBranchListJson() {
		return branchListJson;
	}
	/**
	 * @param branchListJson セットする branchListJson
	 */
	public void setBranchListJson(String branchListJson) {
		this.branchListJson = branchListJson;
	}
	/**
	 * @return sectionListJson
	 */
	public String getSectionListJson() {
		return sectionListJson;
	}
	/**
	 * @param sectionListJson セットする sectionListJson
	 */
	public void setSectionListJson(String sectionListJson) {
		this.sectionListJson = sectionListJson;
	}
	/**
	 * @return telTypeModelListJson
	 */
	public String getTelTypeModelListJson() {
		return telTypeModelListJson;
	}
	/**
	 * @param telTypeModelListJson セットする telTypeModelListJson
	 */
	public void setTelTypeModelListJson(String telTypeModelListJson) {
		this.telTypeModelListJson = telTypeModelListJson;
	}
	/**
	 * @return phoneButtonTempleteListJson
	 */
	public String getPhoneButtonTempleteListJson() {
		return phoneButtonTempleteListJson;
	}
	/**
	 * @param phoneButtonTempleteListJson セットする phoneButtonTempleteListJson
	 */
	public void setPhoneButtonTempleteListJson(String phoneButtonTempleteListJson) {
		this.phoneButtonTempleteListJson = phoneButtonTempleteListJson;
	}
	/**
	 * @return loggerDataList
	 */
	public List<LabelValueModel> getLoggerDataList() {
		return loggerDataList;
	}
	/**
	 * @param loggerDataList セットする loggerDataList
	 */
	public void setLoggerDataList(List<LabelValueModel> loggerDataList) {
		this.loggerDataList = loggerDataList;
	}
	/**
	 * @return loggerDataListJson
	 */
	public String getLoggerDataListJson() {
		return loggerDataListJson;
	}
	/**
	 * @param loggerDataListJson セットする loggerDataListJson
	 */
	public void setLoggerDataListJson(String loggerDataListJson) {
		this.loggerDataListJson = loggerDataListJson;
	}






}
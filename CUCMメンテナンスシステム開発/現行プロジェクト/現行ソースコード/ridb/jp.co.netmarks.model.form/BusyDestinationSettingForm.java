/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingForm.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 話中転送先設定用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/18
 */
public class BusyDestinationSettingForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = -40455073372213664L;

	/* ユーザーID */
	private String userId = null;
	/* 電話ID */
	private String telId = null;
	/* ラインID */
	private String lineId = null;
	/* 話中転送  */
	private String busyDestination = null;
	/* 1番目の内線番号 */
	private String firstDirectoryNumber = null;

	/** ##### 配列 ##### **/
	/* 話中転送先階層 */
	private String[] busyDestinationArray = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return telId
	 */
	public String getTelId() {
		return telId;
	}
	/**
	 * @param telId セットする telId
	 */
	public void setTelId(String telId) {
		this.telId = telId;
	}
	/**
	 * @return lineId
	 */
	public String getLineId() {
		return lineId;
	}
	/**
	 * @param lineId セットする lineId
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	/**
	 * @return busyDestination
	 */
	public String getBusyDestination() {
		return busyDestination;
	}
	/**
	 * @param busyDestination セットする busyDestination
	 */
	public void setBusyDestination(String busyDestination) {
		this.busyDestination = busyDestination;
	}
	/**
	 * @return firstDirectoryNumber
	 */
	public String getFirstDirectoryNumber() {
		return firstDirectoryNumber;
	}
	/**
	 * @param firstDirectoryNumber セットする firstDirectoryNumber
	 */
	public void setFirstDirectoryNumber(String firstDirectoryNumber) {
		this.firstDirectoryNumber = firstDirectoryNumber;
	}
	/**
	 * @return busyDestinationArray
	 */
	public String[] getBusyDestinationArray() {
		return busyDestinationArray;
	}
	/**
	 * @param busyDestinationArray セットする busyDestinationArray
	 */
	public void setBusyDestinationArray(String[] busyDestinationArray) {
		this.busyDestinationArray = busyDestinationArray;
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingUpdateForm.java
 *
 * @date 2013/09/20
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 話中転送先設定画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/20 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/20
 */
public class BusyDestinationSettingUpdateForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 4645458865176494945L;
	
	/* 1番目の内線番号 */
	private String firstDirectoryNumber = null;

	/** ##### 配列 ##### **/
	/* 話中転送先階層 */
	private String[] busyDestinationArray = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/
	
	/**
	 * @return busyDestinationArray
	 */
	public String[] getBusyDestinationArray() {
		return busyDestinationArray;
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
	 * @param busyDestinationArray セットする busyDestinationArray
	 */
	public void setBusyDestinationArray(String[] busyDestinationArray) {
		this.busyDestinationArray = busyDestinationArray;
	}

}
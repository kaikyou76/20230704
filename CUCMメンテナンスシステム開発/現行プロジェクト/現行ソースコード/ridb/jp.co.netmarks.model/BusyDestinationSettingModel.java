/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingModel.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.util.Map;

import jp.co.ksc.model.AbstractSearchModel;
import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * 話中転送先設定用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/18
 */
public class BusyDestinationSettingModel  extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = 1047693302588666123L;

	/* 内線番号 */
	private String directoryNumber = null;

	/* 登録元の内線番号 */
	private String firstDirectoryNumber = null;
	
	/* 登録元の内線番号上4桁 */
	private String frontFour = null;

	/** ##### 配列 ##### **/
	/* 話中転送先階層 */
	private String[] busyDestinationArray = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}


	/**
	 * @return directoryNumber
	 */
	public String getDirectoryNumber() {
		return directoryNumber;
	}
	/**
	 * @param directoryNumber セットする directoryNumber
	 */
	public void setDirectoryNumber(String directoryNumber) {
		this.directoryNumber = directoryNumber;
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
	 * @return frontFour
	 */
	public String getFrontFour() {
		return frontFour;
	}
	/**
	 * @param frontFour セットする frontFour
	 */
	public void setFrontFour(String frontFour) {
		this.frontFour = frontFour;
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
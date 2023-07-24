/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingResultModel.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;

/**
 * <pre>
 * 話中転送先設定検索結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/19
 */
public class BusyDestinationSettingResultModel implements Serializable{

	private static final long serialVersionUID = 7157906458497557548L;

	/* 転送順 */
	private String forwardIndex = null;
	/* 内線番号 */
	private String directoryNumber = null;
	/* 利用者名 */
	private String kanjiUserName = null;
	/* 拠点 */
	private String branchTelName = null;
	/* 所属店部課 */
	private String sectionTelName = null;
	/* 話中転送先 */
	private String busyDestination = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return forwardIndex
	 */
	public String getForwardIndex() {
		return forwardIndex;
	}
	/**
	 * @param forwardIndex セットする forwardIndex
	 */
	public void setForwardIndex(String forwardIndex) {
		this.forwardIndex = forwardIndex;
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
	 * @return kanjiUserName
	 */
	public String getKanjiUserName() {
		return kanjiUserName;
	}
	/**
	 * @param kanjiUserName セットする kanjiUserName
	 */
	public void setKanjiUserName(String kanjiUserName) {
		this.kanjiUserName = kanjiUserName;
	}
	/**
	 * @return branchTelName
	 */
	public String getBranchTelName() {
		return branchTelName;
	}
	/**
	 * @param branchTelName セットする branchTelName
	 */
	public void setBranchTelName(String branchTelName) {
		this.branchTelName = branchTelName;
	}
	/**
	 * @return sectionTelName
	 */
	public String getSectionTelName() {
		return sectionTelName;
	}
	/**
	 * @param sectionTelName セットする sectionTelName
	 */
	public void setSectionTelName(String sectionTelName) {
		this.sectionTelName = sectionTelName;
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
}
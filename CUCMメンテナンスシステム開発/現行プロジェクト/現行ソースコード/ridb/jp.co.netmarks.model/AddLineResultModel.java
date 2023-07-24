/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineResultModel.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * ラインの検索と選択検索結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
public class AddLineResultModel implements Serializable{

	private static final long serialVersionUID = -3874269464199228792L;

	/* ラインID */
	private BigDecimal lineId = null;
	/* 内線番号 */
	private String directoryNumber = null;
	/* 話中転送先 */
	private String busyDestination = null;
	/* 不応答転送先 */
	private String noansDestination = null;
	/* ピックアップグループ名 */
	private String pickupGroupName = null;
	/* VMフラグ */
	private String voiceMailFlg = null;
	/* VMフラグ（画面表示用） */
	private String voiceMailFlgName = null;
	/* LoggerData */
	private String loggerData = null;
	/* 拠点ID（課金） */
	private String chargeAssociationBranchId = null;
	/* 親店部課（課金） */
	private String chargeAssociationParentSectionId = null;
	/* 店部課（課金） */
	private String chargeAssociationSectionId = null;
	/* 拠点－親店部課－店部課（課金） */
	private String chargeAssociationPlace = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return lineId
	 */
	public BigDecimal getLineId() {
		return lineId;
	}
	/**
	 * @param lineId セットする lineId
	 */
	public void setLineId(BigDecimal lineId) {
		this.lineId = lineId;
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
	 * @return noansDestination
	 */
	public String getNoansDestination() {
		return noansDestination;
	}
	/**
	 * @param noansDestination セットする noansDestination
	 */
	public void setNoansDestination(String noansDestination) {
		this.noansDestination = noansDestination;
	}
	/**
	 * @return pickupGroupName
	 */
	public String getPickupGroupName() {
		return pickupGroupName;
	}
	/**
	 * @param pickupGroupName セットする pickupGroupName
	 */
	public void setPickupGroupName(String pickupGroupName) {
		this.pickupGroupName = pickupGroupName;
	}
	/**
	 * @return voiceMailFlg
	 */
	public String getVoiceMailFlg() {
		return voiceMailFlg;
	}
	/**
	 * @param voiceMailFlg セットする voiceMailFlg
	 */
	public void setVoiceMailFlg(String voiceMailFlg) {
		this.voiceMailFlg = voiceMailFlg;
	}
	/**
	 * @return voiceMailFlgName
	 */
	public String getVoiceMailFlgName() {
		return voiceMailFlgName;
	}
	/**
	 * @param voiceMailFlgName セットする voiceMailFlgName
	 */
	public void setVoiceMailFlgName(String voiceMailFlgName) {
		this.voiceMailFlgName = voiceMailFlgName;
	}
	/**
	 * @return loggerData
	 */
	public String getLoggerData() {
		return loggerData;
	}
	/**
	 * @param loggerData セットする loggerData
	 */
	public void setLoggerData(String loggerData) {
		this.loggerData = loggerData;
	}
	/**
	 * @return chargeAssociationBranchId
	 */
	public String getChargeAssociationBranchId() {
		return chargeAssociationBranchId;
	}
	/**
	 * @param chargeAssociationBranchId セットする chargeAssociationBranchId
	 */
	public void setChargeAssociationBranchId(String chargeAssociationBranchId) {
		this.chargeAssociationBranchId = chargeAssociationBranchId;
	}
	/**
	 * @return chargeAssociationParentSectionId
	 */
	public String getChargeAssociationParentSectionId() {
		return chargeAssociationParentSectionId;
	}
	/**
	 * @param chargeAssociationParentSectionId セットする chargeAssociationParentSectionId
	 */
	public void setChargeAssociationParentSectionId(
			String chargeAssociationParentSectionId) {
		this.chargeAssociationParentSectionId = chargeAssociationParentSectionId;
	}
	/**
	 * @return chargeAssociationSectionId
	 */
	public String getChargeAssociationSectionId() {
		return chargeAssociationSectionId;
	}
	/**
	 * @param chargeAssociationSectionId セットする chargeAssociationSectionId
	 */
	public void setChargeAssociationSectionId(String chargeAssociationSectionId) {
		this.chargeAssociationSectionId = chargeAssociationSectionId;
	}
	/**
	 * @return chargeAssociationPlace
	 */
	public String getChargeAssociationPlace() {
		return chargeAssociationPlace;
	}
	/**
	 * @param chargeAssociationPlace セットする chargeAssociationPlace
	 */
	public void setChargeAssociationPlace(String chargeAssociationPlace) {
		this.chargeAssociationPlace = chargeAssociationPlace;
	}
}
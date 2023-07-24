/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelResultModel.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 電話機の検索と選択検索結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
public class AddTelResultModel implements Serializable{

	private static final long serialVersionUID = 3601238098980251259L;

	/* ステータス名 */
	private String telStatusName = null;
	/* ステータス */
	private String telStatus = null;
	/* 内線番号 */
	private String directoryNumber = null;
	/* ダイアルイン */
	private String dialin = null;
	/* 電話機 */
	private String telTypeModel = null;
	/* マックアドレス */
	private String macAddress = null;
	/* ユーザー名 */
	private String kanjiUserName = null;
	/* 拠点 */
	private String branchTelName = null;
	/* セクション */
	private String sectionTelName = null;
	/* 拠点（課金） */
	private String chargeAssociationBranchId = null;
	/* 親店部課（課金） */
	private String chargeAssociationParentSectionId = null;
	/* 店部課（課金） */
	private String chargeAssociationSectionId = null;
	/* 拠点－親店部課－店部課 */
	private String chargeAssociationPlace = null;
	/* ユーザーID */
	private BigDecimal userId = null;
	/* 電話機ID */
	private BigDecimal telId = null;
	/* ラインID */
	private BigDecimal lineId = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return telStatusName
	 */
	public String getTelStatusName() {
		return telStatusName;
	}
	/**
	 * @param telStatusName セットする telStatusName
	 */
	public void setTelStatusName(String telStatusName) {
		this.telStatusName = telStatusName;
	}
	/**
	 * @return telStatus
	 */
	public String getTelStatus() {
		return telStatus;
	}
	/**
	 * @param telStatus セットする telStatus
	 */
	public void setTelStatus(String telStatus) {
		this.telStatus = telStatus;
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
	 * @return dialin
	 */
	public String getDialin() {
		return dialin;
	}
	/**
	 * @param dialin セットする dialin
	 */
	public void setDialin(String dialin) {
		this.dialin = dialin;
	}
	/**
	 * @return telTypeModel
	 */
	public String getTelTypeModel() {
		return telTypeModel;
	}
	/**
	 * @param telTypeModel セットする telTypeModel
	 */
	public void setTelTypeModel(String telTypeModel) {
		this.telTypeModel = telTypeModel;
	}
	/**
	 * @return macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	/**
	 * @param macAddress セットする macAddress
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
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
	/**
	 * @return userId
	 */
	public BigDecimal getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	/**
	 * @return telId
	 */
	public BigDecimal getTelId() {
		return telId;
	}
	/**
	 * @param telId セットする telId
	 */
	public void setTelId(BigDecimal telId) {
		this.telId = telId;
	}
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
}
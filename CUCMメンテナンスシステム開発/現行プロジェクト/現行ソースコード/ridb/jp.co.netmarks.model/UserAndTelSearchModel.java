/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelSearchModel.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import jp.co.ksc.model.AbstractSearchModel;
import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * ユーザーと電話機の一覧用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/07 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/07
 */
public class UserAndTelSearchModel extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = 3952353708657868353L;

	/* 拠点（ユーザ） */
	private String branchUserId      = null;
	/* 店部課（ユーザー）  */
	private String sectionUserId     = null;
	/* 会社ID（ユーザー） */
	private String companyUserId     = null;
	/* 所属課（営業店のみ） */
	private String attachSectionName = null;
	/* 拠点（電話機） */
	private String branchTelId       = null;
	/* 店部課（電話機） */
	private String sectionTelId      = null;
	/* 会社ID（電話機） */
	private String companyTelId      = null;
	/* MACアドレス */
	private String macAddress        = null;
	/* 反映状況 */
	private String cucmReflectionDiv = null;
	/* ユーザー名 */
	private String kanaUserName      = null;
	/* 内線番号 */
	private String directoryNumber   = null;
	/* ダイアルイン */
	private String dialinNumber      = null;
	/* PhoneButtonTemplete */
	private String phoneButtonTemplete = null;
	/* 電話機 */
	private String telTypeModel      = null;
	/* ピックアップグループ */
	private String pickupGroupName   = null;
	/* ボイスメール */
	private String voiceMailFlg      = null;
	/* 話中転送先 */
	private String busyDestination   = null;
	/* 不応答転送 */
	private String noansDestination  = null;
	/* コーリングサーチスペース */
	private String callingSearchSpaceName   = null;
	/* 課金先 拠点 */
	private String chargeAssociationBranchId  = null;
	/* 課金先 親店部課 */
	private String chargeAssociationParentSectionId = null;
	/* 課金先 店部課 */
	private String chargeAssociationSectionId = null;
	/* 通話録音 */
	private String loggerData        = null;
	/* Line Text Label */
	private String lineTextLabel     = null;
	/* クラスタID */
	private BigDecimal clusterId     = null;
	/* 拡張モジュール */
	private String addonModuleFlg    = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	/**
	 * @return branchUserId
	 */
	public String getBranchUserId() {
		return branchUserId;
	}
	/**
	 * @param branchUserId セットする branchUserId
	 */
	public void setBranchUserId(String branchUserId) {
		this.branchUserId = branchUserId;
	}
	/**
	 * @return sectionUserId
	 */
	public String getSectionUserId() {
		return sectionUserId;
	}
	/**
	 * @param sectionUserId セットする sectionUserId
	 */
	public void setSectionUserId(String sectionUserId) {
		this.sectionUserId = sectionUserId;
	}
	/**
	 * @return companyUserId
	 */
	public String getCompanyUserId() {
		return companyUserId;
	}
	/**
	 * @param companyUserId セットする companyUserId
	 */
	public void setCompanyUserId(String companyUserId) {
		this.companyUserId = companyUserId;
	}
	/**
	 * @return attachSectionName
	 */
	public String getAttachSectionName() {
		return attachSectionName;
	}
	/**
	 * @param attachSectionName セットする attachSectionName
	 */
	public void setAttachSectionName(String attachSectionName) {
		this.attachSectionName = attachSectionName;
	}
	/**
	 * @return branchTelId
	 */
	public String getBranchTelId() {
		return branchTelId;
	}
	/**
	 * @param branchTelId セットする branchTelId
	 */
	public void setBranchTelId(String branchTelId) {
		this.branchTelId = branchTelId;
	}
	/**
	 * @return sectionTelId
	 */
	public String getSectionTelId() {
		return sectionTelId;
	}
	/**
	 * @param sectionTelId セットする sectionTelId
	 */
	public void setSectionTelId(String sectionTelId) {
		this.sectionTelId = sectionTelId;
	}
	/**
	 * @return companyTelId
	 */
	public String getCompanyTelId() {
		return companyTelId;
	}
	/**
	 * @param companyTelId セットする companyTelId
	 */
	public void setCompanyTelId(String companyTelId) {
		this.companyTelId = companyTelId;
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
	 * @return cucmReflectionDiv
	 */
	public String getCucmReflectionDiv() {
		return cucmReflectionDiv;
	}
	/**
	 * @param cucmReflectionDiv セットする cucmReflectionDiv
	 */
	public void setCucmReflectionDiv(String cucmReflectionDiv) {
		this.cucmReflectionDiv = cucmReflectionDiv;
	}
	/**
	 * @return kanaUserName
	 */
	public String getKanaUserName() {
		return kanaUserName;
	}
	/**
	 * @param kanaUserName セットする kanaUserName
	 */
	public void setKanaUserName(String kanaUserName) {
		this.kanaUserName = kanaUserName;
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
	 * @return dialinNumber
	 */
	public String getDialinNumber() {
		return dialinNumber;
	}
	/**
	 * @param dialinNumber セットする dialinNumber
	 */
	public void setDialinNumber(String dialinNumber) {
		this.dialinNumber = dialinNumber;
	}

/**
	 * @return phoneButtonTemplete
	 */
	public String getPhoneButtonTemplete() {
		return phoneButtonTemplete;
	}

	/**
	 * @param phoneButtonTemplete セットする phoneButtonTemplete
	 */
	public void setPhoneButtonTemplete(String phoneButtonTemplete) {
		this.phoneButtonTemplete = phoneButtonTemplete;
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
	 * @return callingSearchSpaceName
	 */
	public String getCallingSearchSpaceName() {
		return callingSearchSpaceName;
	}
	/**
	 * @param callingSearchSpaceName セットする callingSearchSpaceName
	 */
	public void setCallingSearchSpaceName(String callingSearchSpaceName) {
		this.callingSearchSpaceName = callingSearchSpaceName;
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
	 * @return lineTextLabel
	 */
	public String getLineTextLabel() {
		return lineTextLabel;
	}
	/**
	 * @param lineTextLabel セットする lineTextLabel
	 */
	public void setLineTextLabel(String lineTextLabel) {
		this.lineTextLabel = lineTextLabel;
	}
	/**
	 * @return clusterId
	 */
	public BigDecimal getClusterId() {
		return clusterId;
	}
	/**
	 * @param clusterId セットする clusterId
	 */
	public void setClusterId(BigDecimal clusterId) {
		this.clusterId = clusterId;
	}
	/**
	 * @return addonModuleFlg
	 */
	public String getAddonModuleFlg() {
		return addonModuleFlg;
	}
	/**
	 * @param addonModuleFlg セットする addonModuleFlg
	 */
	public void setAddonModuleFlg(String addonModuleFlg) {
		this.addonModuleFlg = addonModuleFlg;
	}
}
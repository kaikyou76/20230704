/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelSearchResultModel.java
 *
 * @date 2013/08/15
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * ユーザーと電話機の一覧結果格納用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/15 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/15
 */
public class UserAndTelSearchResultModel implements Serializable{

	private static final long serialVersionUID = 7176617373113191388L;

	private boolean changed;

	/* ステータス */
	private String statusCode           = StringUtils.EMPTY;
	/* ステータス名 */
	private String statusName           = StringUtils.EMPTY;
	/* ラインID */
	private BigDecimal lineId           = new BigDecimal(0);
	/* 内線番号 */
	private String directoryNumber      = StringUtils.EMPTY;
	/* ダイアルイン */
	private String dialinNumber         = StringUtils.EMPTY;
	/* ユーザーID */
	private BigDecimal userId           = new BigDecimal(0);
	/* ユーザー名_漢字 */
	private String kanjiUserName        = StringUtils.EMPTY;
	/* ユーザー名_カナ */
	private String kanaUserName         = StringUtils.EMPTY;
	/* 拠点ID（電話機） */
	private String branchTelId          = StringUtils.EMPTY;
	/* 店部課ID（電話機） */
	private String sectionTelId         = StringUtils.EMPTY;
	/* 会社ID（電話機） */
	private String companyTelId         = StringUtils.EMPTY;
	/* 会社ID＋店部課ID */
	private String companySectionTelId  = StringUtils.EMPTY;
	/* 拠点名（電話機） */
	private String branchTelName        = StringUtils.EMPTY;
	/* 店部課名（電話機） */
	private String sectionTelName       = StringUtils.EMPTY;
	/* 店部課ID（ユーザー）  */
	private String sectionUserId        = StringUtils.EMPTY;
	/* 会社ID（ユーザー） */
	private String companyUserId        = StringUtils.EMPTY;
	/* 所属課名（ユーザ） */
	private String sectionUserName      = StringUtils.EMPTY;
	/* ピックアップグループ */
	private String pickupGroupName      = StringUtils.EMPTY;
	/* ピックアップグループNO */
	private String pickupGroupNo        = StringUtils.EMPTY;
	/* ボイスメール */
	private String voiceMailFlg         = StringUtils.EMPTY;
	/* 話中転送先 */
	private String busyDestination      = StringUtils.EMPTY;
	/* 不応答転送 */
	private String noansDestination     = StringUtils.EMPTY;
	/* コーリングサーチスペース */
	private String callingSearchSpaceName     = StringUtils.EMPTY;
	/* 課金先 拠点 */
	private String chargeAssociationBranchId  = StringUtils.EMPTY;
	/* 課金先 親店部課 */
	private String chargeAssociationParentSectionId = StringUtils.EMPTY;
	/* 課金先 店部課 */
	private String chargeAssociationSectionId = StringUtils.EMPTY;
	/* 課金先 備考 */
	private String chargeRemarks        = StringUtils.EMPTY;
	/* 通話録音 */
	private String loggerData           = StringUtils.EMPTY;
	/* 通話録音名 */
	private String loggerDataName       = StringUtils.EMPTY;
	/* 電話帳 */
	private String telDirData           = StringUtils.EMPTY;
	/* 電話ID */
	private BigDecimal telId            = new BigDecimal(0);
	/* 電話機 */
	private String telTypeModel         = StringUtils.EMPTY;
	/* MACアドレス */
	private String macAddress           = StringUtils.EMPTY;
	/* PhoneButtonTemplete */
	private String phoneButtonTemplete  = StringUtils.EMPTY;
	/* 電話備考 */
	private String telLineRemarks       = StringUtils.EMPTY;
	/* Line Text Label */
	private String lineTextLabel        = StringUtils.EMPTY;
	/* External Phone Number */
	private String externalPhoneNumberMask = StringUtils.EMPTY;
	/* 設定モジュール１ */
	private String addonModuleName1     = StringUtils.EMPTY;
	/* 設定モジュール２ */
	private String addonModuleName2     = StringUtils.EMPTY;
	/* 鳴動設定 */
	private String ringSettingName      = StringUtils.EMPTY;
	/* クラスターID */
	private String clusterId            = StringUtils.EMPTY;
	/* 使用状況 */
	private String telUsagesStatus      = StringUtils.EMPTY;
	/* 共有ユーザー区分 */
	private String sharedUse            = StringUtils.EMPTY;
	/* 連番（Index） */
	private String lineIndex            = StringUtils.EMPTY;

	/* ### オリジナル項目 ### */
	/* ユーザーID */
	private BigDecimal orgUserId        = new BigDecimal(0);
	/* 電話ID */
	private BigDecimal orgTelId         = new BigDecimal(0);
	/* ラインID */
	private BigDecimal orgLineId        = new BigDecimal(0);
	/* ライン連番 */
	private String orgLineIndex         = StringUtils.EMPTY;
	/* 会社ID（ユーザー） */
	private String orgCompanyUserId     = StringUtils.EMPTY;
	/* 店部課ID（ユーザー） */
	private String orgSectionUserId     = StringUtils.EMPTY;
	/* ダイアルイン */
	private String orgDialinNumber      = StringUtils.EMPTY;
	/* ピックアップグループ名 */
	private String orgPickupGroupName   = StringUtils.EMPTY;
	/* VM使用 */
	private String orgVoiceMailFlg      = StringUtils.EMPTY;
	/* 話中転送先 */
	private String orgBusyDestination   = StringUtils.EMPTY;
	/* callingSearchSpaceName */
	private String orgCallingSearchSpaceName           = StringUtils.EMPTY;
	/* 課金先（拠点） */
	private String orgChargeAssociationBranchId        = StringUtils.EMPTY;
	/* 課金先（親店部課） */
	private String orgChargeAssociationParentSectionId = StringUtils.EMPTY;
	/* 課金先（子店部課） */
	private String orgChargeAssociationSectionId       = StringUtils.EMPTY;
	/* 備考（課金） */
	private String orgChargeRemarks     = StringUtils.EMPTY;
	/* 通話録音 */
	private String orgLoggerData        = StringUtils.EMPTY;
	/* 電話帳 */
	private String orgTelDirData        = StringUtils.EMPTY;
	/* 拠点（電話機） */
	private String orgBranchTelId       = StringUtils.EMPTY;
	/* 会社（電話機） */
	private String orgCompanyTelId      = StringUtils.EMPTY;
	/* 店部課（電話機） */
	private String orgSectionTelId      = StringUtils.EMPTY;
	/* 店部課（電話機） */
	private String orgCompanySectionTelId = StringUtils.EMPTY;
	/* 備考（電話機/内線番号） */
	private String orgTelLineRemarks    = StringUtils.EMPTY;
	/* lineTextLabel */
	private String orgLineTextLabe      = StringUtils.EMPTY;
	/* 不応答転送 */
	private String orgNoansDestination  = StringUtils.EMPTY;
	/* ExternalPhoneNumber */
	private String orgExternalPhoneNumberMask = StringUtils.EMPTY;
	/* 電話機種 */
	private String orgPhoneButtonTemplete     = StringUtils.EMPTY;
	/* 拡張モジュール１設定 */
	private String orgAddonModuleName1  = StringUtils.EMPTY;
	/* 拡張モジュール２設定 */
	private String orgAddonModuleName2  = StringUtils.EMPTY;
	/* 鳴動設定 */
	private String orgRingSettingName   = StringUtils.EMPTY;


/*********************************************************************
 * generated getter/setter
**********************************************************************/
	/**
	 * @return changed
	 */
	public boolean isChanged() {
		return changed;
	}
	/**
	 * @param changed セットする changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	/**
	 * @return statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode セットする statusCode
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return statusName
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * @param statusName セットする statusName
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
	 * @return companySectionTelId
	 */
	public String getCompanySectionTelId() {
		return companySectionTelId;
	}
	/**
	 * @param companySectionTelId セットする companySectionTelId
	 */
	public void setCompanySectionTelId(String companySectionTelId) {
		this.companySectionTelId = companySectionTelId;
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
	 * @return sectionUserName
	 */
	public String getSectionUserName() {
		return sectionUserName;
	}
	/**
	 * @param sectionUserName セットする sectionUserName
	 */
	public void setSectionUserName(String sectionUserName) {
		this.sectionUserName = sectionUserName;
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
	 * @return pickupGroupNo
	 */
	public String getPickupGroupNo() {
		return pickupGroupNo;
	}
	/**
	 * @param pickupGroupNo セットする pickupGroupNo
	 */
	public void setPickupGroupNo(String pickupGroupNo) {
		this.pickupGroupNo = pickupGroupNo;
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
	 * @return chargeRemarks
	 */
	public String getChargeRemarks() {
		return chargeRemarks;
	}
	/**
	 * @param chargeRemarks セットする chargeRemarks
	 */
	public void setChargeRemarks(String chargeRemarks) {
		this.chargeRemarks = chargeRemarks;
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
	 * @return loggerDataName
	 */
	public String getLoggerDataName() {
		return loggerDataName;
	}
	/**
	 * @param loggerDataName セットする loggerDataName
	 */
	public void setLoggerDataName(String loggerDataName) {
		this.loggerDataName = loggerDataName;
	}
	/**
	 * @return telDirData
	 */
	public String getTelDirData() {
		return telDirData;
	}
	/**
	 * @param telDirData セットする telDirData
	 */
	public void setTelDirData(String telDirData) {
		this.telDirData = telDirData;
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
	 * @return telLineRemarks
	 */
	public String getTelLineRemarks() {
		return telLineRemarks;
	}
	/**
	 * @param telLineRemarks セットする telLineRemarks
	 */
	public void setTelLineRemarks(String telLineRemarks) {
		this.telLineRemarks = telLineRemarks;
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
	 * @return externalPhoneNumberMask
	 */
	public String getExternalPhoneNumberMask() {
		return externalPhoneNumberMask;
	}
	/**
	 * @param externalPhoneNumberMask セットする externalPhoneNumberMask
	 */
	public void setExternalPhoneNumberMask(String externalPhoneNumberMask) {
		this.externalPhoneNumberMask = externalPhoneNumberMask;
	}
	/**
	 * @return addonModuleName1
	 */
	public String getAddonModuleName1() {
		return addonModuleName1;
	}
	/**
	 * @param addonModuleName1 セットする addonModuleName1
	 */
	public void setAddonModuleName1(String addonModuleName1) {
		this.addonModuleName1 = addonModuleName1;
	}
	/**
	 * @return addonModuleName2
	 */
	public String getAddonModuleName2() {
		return addonModuleName2;
	}
	/**
	 * @param addonModuleName2 セットする addonModuleName2
	 */
	public void setAddonModuleName2(String addonModuleName2) {
		this.addonModuleName2 = addonModuleName2;
	}
	/**
	 * @return ringSettingName
	 */
	public String getRingSettingName() {
		return ringSettingName;
	}
	/**
	 * @param ringSettingName セットする ringSettingName
	 */
	public void setRingSettingName(String ringSettingName) {
		this.ringSettingName = ringSettingName;
	}
	/**
	 * @return clusterId
	 */
	public String getClusterId() {
		return clusterId;
	}
/**
	 * @param clusterId セットする clusterId
	 */
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	/**
	 * @return telUsagesStatus
	 */
	public String getTelUsagesStatus() {
		return telUsagesStatus;
	}
	/**
	 * @param telUsagesStatus セットする telUsagesStatus
	 */
	public void setTelUsagesStatus(String telUsagesStatus) {
		this.telUsagesStatus = telUsagesStatus;
	}
	/**
	 * @return sharedUse
	 */
	public String getSharedUse() {
		return sharedUse;
	}
	/**
	 * @param sharedUse セットする sharedUse
	 */
	public void setSharedUse(String sharedUse) {
		this.sharedUse = sharedUse;
	}
	/**
	 * @return lineIndex
	 */
	public String getLineIndex() {
		return lineIndex;
	}
	/**
	 * @param lineIndex セットする lineIndex
	 */
	public void setLineIndex(String lineIndex) {
		this.lineIndex = lineIndex;
	}
	/**
	 * @return orgUserId
	 */
	public BigDecimal getOrgUserId() {
		return orgUserId;
	}
	/**
	 * @param orgUserId セットする orgUserId
	 */
	public void setOrgUserId(BigDecimal orgUserId) {
		this.orgUserId = orgUserId;
	}
	/**
	 * @return orgTelId
	 */
	public BigDecimal getOrgTelId() {
		return orgTelId;
	}
	/**
	 * @param orgTelId セットする orgTelId
	 */
	public void setOrgTelId(BigDecimal orgTelId) {
		this.orgTelId = orgTelId;
	}
	/**
	 * @return orgLineId
	 */
	public BigDecimal getOrgLineId() {
		return orgLineId;
	}
	/**
	 * @param orgLineId セットする orgLineId
	 */
	public void setOrgLineId(BigDecimal orgLineId) {
		this.orgLineId = orgLineId;
	}
	/**
	 * @return orgLineIndex
	 */
	public String getOrgLineIndex() {
		return orgLineIndex;
	}
	/**
	 * @param orgLineIndex セットする orgLineIndex
	 */
	public void setOrgLineIndex(String orgLineIndex) {
		this.orgLineIndex = orgLineIndex;
	}
	/**
	 * @return orgCompanyUserId
	 */
	public String getOrgCompanyUserId() {
		return orgCompanyUserId;
	}
	/**
	 * @param orgCompanyUserId セットする orgCompanyUserId
	 */
	public void setOrgCompanyUserId(String orgCompanyUserId) {
		this.orgCompanyUserId = orgCompanyUserId;
	}
	/**
	 * @return orgSectionUserId
	 */
	public String getOrgSectionUserId() {
		return orgSectionUserId;
	}
	/**
	 * @param orgSectionUserId セットする orgSectionUserId
	 */
	public void setOrgSectionUserId(String orgSectionUserId) {
		this.orgSectionUserId = orgSectionUserId;
	}
	/**
	 * @return orgDialinNumber
	 */
	public String getOrgDialinNumber() {
		return orgDialinNumber;
	}
	/**
	 * @param orgDialinNumber セットする orgDialinNumber
	 */
	public void setOrgDialinNumber(String orgDialinNumber) {
		this.orgDialinNumber = orgDialinNumber;
	}
	/**
	 * @return orgPickupGroupName
	 */
	public String getOrgPickupGroupName() {
		return orgPickupGroupName;
	}
	/**
	 * @param orgPickupGroupName セットする orgPickupGroupName
	 */
	public void setOrgPickupGroupName(String orgPickupGroupName) {
		this.orgPickupGroupName = orgPickupGroupName;
	}
	/**
	 * @return orgVoiceMailFlg
	 */
	public String getOrgVoiceMailFlg() {
		return orgVoiceMailFlg;
	}
	/**
	 * @param orgVoiceMailFlg セットする orgVoiceMailFlg
	 */
	public void setOrgVoiceMailFlg(String orgVoiceMailFlg) {
		this.orgVoiceMailFlg = orgVoiceMailFlg;
	}
	/**
	 * @return orgBusyDestination
	 */
	public String getOrgBusyDestination() {
		return orgBusyDestination;
	}
	/**
	 * @param orgBusyDestination セットする orgBusyDestination
	 */
	public void setOrgBusyDestination(String orgBusyDestination) {
		this.orgBusyDestination = orgBusyDestination;
	}
	/**
	 * @return orgCallingSearchSpaceName
	 */
	public String getOrgCallingSearchSpaceName() {
		return orgCallingSearchSpaceName;
	}
	/**
	 * @param orgCallingSearchSpaceName セットする orgCallingSearchSpaceName
	 */
	public void setOrgCallingSearchSpaceName(String orgCallingSearchSpaceName) {
		this.orgCallingSearchSpaceName = orgCallingSearchSpaceName;
	}
	/**
	 * @return orgChargeAssociationBranchId
	 */
	public String getOrgChargeAssociationBranchId() {
		return orgChargeAssociationBranchId;
	}
	/**
	 * @param orgChargeAssociationBranchId セットする orgChargeAssociationBranchId
	 */
	public void setOrgChargeAssociationBranchId(String orgChargeAssociationBranchId) {
		this.orgChargeAssociationBranchId = orgChargeAssociationBranchId;
	}
	/**
	 * @return orgChargeAssociationParentSectionId
	 */
	public String getOrgChargeAssociationParentSectionId() {
		return orgChargeAssociationParentSectionId;
	}
	/**
	 * @param orgChargeAssociationParentSectionId セットする orgChargeAssociationParentSectionId
	 */
	public void setOrgChargeAssociationParentSectionId(
			String orgChargeAssociationParentSectionId) {
		this.orgChargeAssociationParentSectionId = orgChargeAssociationParentSectionId;
	}
/**
	 * @return orgChargeAssociationSectionId
	 */
	public String getOrgChargeAssociationSectionId() {
		return orgChargeAssociationSectionId;
	}
	/**
	 * @param orgChargeAssociationSectionId セットする orgChargeAssociationSectionId
	 */
	public void setOrgChargeAssociationSectionId(
			String orgChargeAssociationSectionId) {
		this.orgChargeAssociationSectionId = orgChargeAssociationSectionId;
	}
	/**
	 * @return orgChargeRemarks
	 */
	public String getOrgChargeRemarks() {
		return orgChargeRemarks;
	}
	/**
	 * @param orgChargeRemarks セットする orgChargeRemarks
	 */
	public void setOrgChargeRemarks(String orgChargeRemarks) {
		this.orgChargeRemarks = orgChargeRemarks;
	}
	/**
	 * @return orgLoggerData
	 */
	public String getOrgLoggerData() {
		return orgLoggerData;
	}
	/**
	 * @param orgLoggerData セットする orgLoggerData
	 */
	public void setOrgLoggerData(String orgLoggerData) {
		this.orgLoggerData = orgLoggerData;
	}
	/**
	 * @return orgTelDirData
	 */
	public String getOrgTelDirData() {
		return orgTelDirData;
	}
	/**
	 * @param orgTelDirData セットする orgTelDirData
	 */
	public void setOrgTelDirData(String orgTelDirData) {
		this.orgTelDirData = orgTelDirData;
	}
	/**
	 * @return orgBranchTelId
	 */
	public String getOrgBranchTelId() {
		return orgBranchTelId;
	}
	/**
	 * @param orgBranchTelId セットする orgBranchTelId
	 */
	public void setOrgBranchTelId(String orgBranchTelId) {
		this.orgBranchTelId = orgBranchTelId;
	}
	/**
	 * @return orgCompanyTelId
	 */
	public String getOrgCompanyTelId() {
		return orgCompanyTelId;
	}
	/**
	 * @param orgCompanyTelId セットする orgCompanyTelId
	 */
	public void setOrgCompanyTelId(String orgCompanyTelId) {
		this.orgCompanyTelId = orgCompanyTelId;
	}
	/**
	 * @return orgSectionTelId
	 */
	public String getOrgSectionTelId() {
		return orgSectionTelId;
	}
	/**
	 * @param orgSectionTelId セットする orgSectionTelId
	 */
	public void setOrgSectionTelId(String orgSectionTelId) {
		this.orgSectionTelId = orgSectionTelId;
	}
	/**
	 * @return orgCompanySectionTelId
	 */
	public String getOrgCompanySectionTelId() {
		return orgCompanySectionTelId;
	}
	/**
	 * @param orgCompanySectionTelId セットする orgCompanySectionTelId
	 */
	public void setOrgCompanySectionTelId(String orgCompanySectionTelId) {
		this.orgCompanySectionTelId = orgCompanySectionTelId;
	}
	/**
	 * @return orgTelLineRemarks
	 */
	public String getOrgTelLineRemarks() {
		return orgTelLineRemarks;
	}
	/**
	 * @param orgTelLineRemarks セットする orgTelLineRemarks
	 */
	public void setOrgTelLineRemarks(String orgTelLineRemarks) {
		this.orgTelLineRemarks = orgTelLineRemarks;
	}
	/**
	 * @return orgLineTextLabe
	 */
	public String getOrgLineTextLabe() {
		return orgLineTextLabe;
	}
	/**
	 * @param orgLineTextLabe セットする orgLineTextLabe
	 */
	public void setOrgLineTextLabe(String orgLineTextLabe) {
		this.orgLineTextLabe = orgLineTextLabe;
	}
	/**
	 * @return orgNoansDestination
	 */
	public String getOrgNoansDestination() {
		return orgNoansDestination;
	}
	/**
	 * @param orgNoansDestination セットする orgNoansDestination
	 */
	public void setOrgNoansDestination(String orgNoansDestination) {
		this.orgNoansDestination = orgNoansDestination;
	}
	/**
	 * @return orgExternalPhoneNumberMask
	 */
	public String getOrgExternalPhoneNumberMask() {
		return orgExternalPhoneNumberMask;
	}
	/**
	 * @param orgExternalPhoneNumberMask セットする orgExternalPhoneNumberMask
	 */
	public void setOrgExternalPhoneNumberMask(String orgExternalPhoneNumberMask) {
		this.orgExternalPhoneNumberMask = orgExternalPhoneNumberMask;
	}
	/**
	 * @return orgPhoneButtonTemplete
	 */
	public String getOrgPhoneButtonTemplete() {
		return orgPhoneButtonTemplete;
	}
	/**
	 * @param orgPhoneButtonTemplete セットする orgPhoneButtonTemplete
	 */
	public void setOrgPhoneButtonTemplete(String orgPhoneButtonTemplete) {
		this.orgPhoneButtonTemplete = orgPhoneButtonTemplete;
	}
	/**
	 * @return orgAddonModuleName1
	 */
	public String getOrgAddonModuleName1() {
		return orgAddonModuleName1;
	}
	/**
	 * @param orgAddonModuleName1 セットする orgAddonModuleName1
	 */
	public void setOrgAddonModuleName1(String orgAddonModuleName1) {
		this.orgAddonModuleName1 = orgAddonModuleName1;
	}
	/**
	 * @return orgAddonModuleName2
	 */
	public String getOrgAddonModuleName2() {
		return orgAddonModuleName2;
	}
	/**
	 * @param orgAddonModuleName2 セットする orgAddonModuleName2
	 */
	public void setOrgAddonModuleName2(String orgAddonModuleName2) {
		this.orgAddonModuleName2 = orgAddonModuleName2;
	}
	/**
	 * @return orgRingSettingName
	 */
	public String getOrgRingSettingName() {
		return orgRingSettingName;
	}
	/**
	 * @param orgRingSettingName セットする orgRingSettingName
	 */
	public void setOrgRingSettingName(String orgRingSettingName) {
		this.orgRingSettingName = orgRingSettingName;
	}
}	
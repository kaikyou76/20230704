/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelUpdateModel.java
 *
 * @date 2013/08/22
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * ユーザーと電話機の一覧更新用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/22
 */
public class UserAndTelUpdateModel implements Serializable{

	private static final long serialVersionUID = -5292540943321328104L;

	/* チェック有無 */
	private boolean checked;

	/* 値の変更有無 */
	private boolean changed;

	/* ### 入力項目 ### */
	/* ダイアルイン */
	public String dialinNumber               = null;
	/* ピックアップグループ名 */
	public String pickupGroupName            = null;
	/* voiceMail */
	public String voiceMailFlg;
	/* 話中転送先 */
	public String busyDestination            = null;
	/* CSS名 */
	public String callingSearchSpaceName     = null;
	/* 課金先-拠点 */
	public String chargeAssociationBranchId  = null;
	/* 課金先-親店部課 */
	public String chargeAssociationParentSectionId = null;
	/* 課金先-店部課 */
	public String chargeAssociationSectionId = null;
	/* 備考（課金先） */
	public String chargeRemarks              = null;
	/* 通話録音 */
	public String loggerData                 = null;
	/* 電話帳 */
	public String telDirData                 = null;
	/* 拠点（電話機） */
	public String branchTelId                = null;
	/* 店部課（電話機） */
	public String sectionTelId               = null;
	/* 会社（電話機） */
	public String companyTelId               = null;
	/* 店部課（電話機） カンマ区切りで会社ID、店部課IDがセットされている */
	public String companySectionTelId        = null;
	/* 拡張モジュール1設定 */
	public String addonModuleName1           = null;
	/* 拡張モジュール2設定 */
	public String addonModuleName2           = null;
	/* 鳴動設定 */
	public String ringSettingName            = null;
	/* 備考（電話機/内線番号） */
	public String telLineRemarks             = null;
	/* LineTextLabel */
	public String lineTextLabel              = null;
	/* 不応答転送 */
	public String noansDestination           = null;
	/* ExternalPhoneNumber */
	public String externalPhoneNumberMask    = null;
	/* 電話機種 */
	public String phoneButtonTemplete        = null;

	/* ### 非入力項目 ### */
	/* ユーザーID */
	private BigDecimal userId           = null;
	/* 電話ID */
	private BigDecimal telId            = null;
	/* ラインID */
	private BigDecimal lineId           = null;
	/* 内線番号 */
	private String directoryNumber      = null;
	/* ラインIndex */
	private String lineIndex            = null;
	/* MACアドレス */
	private String macAddress           = null;
	/* 店部課ID（ユーザー）  */
	private String sectionUserId        = null;
	/* 会社ID（ユーザー） */
	private String companyUserId        = null;
	/* 共有ユーザー区分 */
	private String sharedUse            = null;
	/* 電話機種 */
	private String telTypeModel         = null;
	/* ユーザー名 */
	private String kanjiUserName        = null;

	/* ### オリジナル項目 ### */
	/* ユーザーID */
	private BigDecimal orgUserId        = null;
	/* 電話ID */
	private BigDecimal orgTelId         = null;
	/* ラインID */
	private BigDecimal orgLineId        = null;
	/* ライン連番 */
	private String orgLineIndex         = null;
	/* 会社ID（ユーザー） */
	private String orgCompanyUserId     = null;
	/* 店部課ID（ユーザー） */
	private String orgSectionUserId     = null;
	/* ダイアルイン */
	private String orgDialinNumber      = null;
	/* ピックアップグループ */
	private String orgPickupGroupName   = null;
	/* VM使用 */
	private String orgVoiceMailFlg      = null;
	/* 話中転送先 */
	private String orgBusyDestination   = null;
	/* callingSearchSpaceName */
	private String orgCallingSearchSpaceName           = null;
	/* 課金先（拠点） */
	private String orgChargeAssociationBranchId        = null;
	/* 課金先（親店部課） */
	private String orgChargeAssociationParentSectionId = null;
	/* 課金先（子店部課） */
	private String orgChargeAssociationSectionId       = null;
	/* 備考（電話機） */
	private String orgChargeRemarks     = null;
	/* 通話録音 */
	private String orgLoggerData        = null;
	/* 電話帳 */
	private String orgTelDirData        = null;
	/* 拠点（電話機） */
	private String orgBranchTelId       = null;
	/* 会社（電話機） */
	private String orgCompanyTelId      = null;
	/* 店部課（電話機） */
	private String orgSectionTelId      = null;
	/* 会社＋店部課（電話機） */
	private String orgCompanySectionTelId = null;
	/* 備考（電話機/内線番号） */
	private String orgTelLineRemarks    = null;
	/* lineTextLabel */
	private String orgLineTextLabe      = null;
	/* 不応答転送 */
	private String orgNoansDestination  = null;
	/* ExternalPhoneNumber */
	private String orgExternalPhoneNumberMask = null;
	/* 電話機種 */
	private String orgPhoneButtonTemplete     = null;
	/* 拡張モジュール１設定 */
	private String orgAddonModuleName1  = null;
	/* 拡張モジュール２設定 */
	private String orgAddonModuleName2  = null;
	/* 鳴動設定 */
	private String orgRingSettingName   = null;
	/* プリント順 */
	private String printOrder           = null;
	/* セクション名（ユーザー） */
	private String sectionUserName      = null;

	/* ### 共有電話項目 ### */
	private String sharedUserName       = null;

/*********************************************************************
 * generated getter/setter
 **********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	/**
	 * @return checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked セットする checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
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
	/**
	 * @return printOrder
	 */
	public String getPrintOrder() {
		return printOrder;
	}
	/**
	 * @param printOrder セットする printOrder
	 */
	public void setPrintOrder(String printOrder) {
		this.printOrder = printOrder;
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
	 * @return sharedUserName
	 */
	public String getSharedUserName() {
		return sharedUserName;
	}
	/**
	 * @param sharedUserName セットする sharedUserName
	 */
	public void setSharedUserName(String sharedUserName) {
		this.sharedUserName = sharedUserName;
	}
}

	
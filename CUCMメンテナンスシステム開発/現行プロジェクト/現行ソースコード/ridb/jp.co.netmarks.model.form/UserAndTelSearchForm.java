/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelSearchForm.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.List;

import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * ユーザーと電話機の一覧画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/07 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/07
 */
public class UserAndTelSearchForm  extends BaseForm implements Serializable {

	private static final long serialVersionUID = 4911921279759415981L;

	/* 拠点（ユーザ） */
	private String branchUserId      = null;
	/* 店部課（ユーザー）  */
	private String sectionUserId     = null;
	/* 所属課（営業店のみ） */
	private String attachSectionName = null;
	/* 拠点（電話機） */
	private String branchTelId       = null;
	/* 店部課（電話機） */
	private String sectionTelId      = null;
	/* MACアドレス */
	private String macAddress        = null;
	/* 反映状況 */
	private String cucmReflectionDiv = null;
	/* ユーザー名 */
	private String kanaUserName      = null;
	/* 内線番号 */
	private String directoryNumber   = null;
	/* ダイアルイン */
	private String dialinNumber     = null;
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
	private String callingSearchSpaceName           = null;
	/* 課金先 拠点 */
	private String chargeAssociationBranchId        = null;
	/* 課金先 親店部課 */
	private String chargeAssociationParentSectionId = null;
	/* 課金先 店部課 */
	private String chargeAssociationSectionId       = null;
	/* 通話録音 */
	private String loggerData        = null;
	/* Line Text Label */
	private String lineTextLabel     = null;
	/* 鳴動設定 */
	private String ringSettingName   = null;
	/* クラスタID */
	//private String clusterId         = null;
	/* 拡張モジュール */
	private String addonModuleFlg    = null;

	/** ### List ### **/
	/* 拠点（ユーザ）リスト */
	private List<LabelValueModel> branchUserList          = null;
	/* 店部課（ユーザ）リスト */
	private List<LabelValueModel> sectionUaerList         = null;
	/* 拠点（電話機）リスト */
	private List<LabelValueModel> branchTelList           = null;
	/* 店部課（電話機）リスト */
	private List<LabelValueModel> sectionTelList          = null;
	/* 反映状況 */
	private List<LabelValueModel> cucmReflectionList      = null;
	/* PhoneButton Templete  */
	private List<LabelValueModel> phoneButtonTempleteList = null;
	/* 電話機 */
	private List<LabelValueModel> telTypeModelList        = null;
	/* Pickup Gropu */
	private List<LabelValueModel> pickupGropuList         = null;
	/* 通話録音 */
	private List<LabelValueModel> loggerDataList          = null;
	/* クラスタリスト */
	private List<LabelValueModel> clusterList             = null;
	
	/** ### JsonList ### **/
	/* 拠点セレクトタグ */
	private String branchListJson             = null;
	/* PicupGroupセレクトタグ */
	private String pickupGroupListJson        = null;
	/* コーリングサーチスペースセレクトタグ */
	private String callingSearchSpaceListJson = null;
	/* 通話録音セレクトタグ */
	private String loggerDataListJson         = null;
	/* 店部課セレクトタグ */
	private String sectionListJson            = null;
	/* 拡張モジュール設定セレクトタグ */
	private String addonModuleListJson        = null;
	/* 鳴動設定セレクトタグ */
	private String ringSettingListJson        = null;
	/* 電話テンプレートセレクトタグ */
	private String phoneButtonTempleteListJson = null;
	/* 親店部課 */
	private String parentSectionListJson      = null;


/*********************************************************************
 * generated getter/setter
**********************************************************************/

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
	
	
//	/**
//	 * @return clusterId
//	 */
//	public String getClusterId() {
//		return clusterId;
//	}
//	/**
//	 * @param clusterId セットする clusterId
//	 */
//	public void setClusterId(String clusterId) {
//		this.clusterId = clusterId;
//	}
	
	
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
	/**
	 * @return branchUserList
	 */
	public List<LabelValueModel> getBranchUserList() {
		return branchUserList;
	}
	/**
	 * @param branchUserList セットする branchUserList
	 */
	public void setBranchUserList(List<LabelValueModel> branchUserList) {
		this.branchUserList = branchUserList;
	}
	/**
	 * @return sectionUaerList
	 */
	public List<LabelValueModel> getSectionUaerList() {
		return sectionUaerList;
	}
	/**
	 * @param sectionUaerList セットする sectionUaerList
	 */
	public void setSectionUaerList(List<LabelValueModel> sectionUaerList) {
		this.sectionUaerList = sectionUaerList;
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
	 * @return cucmReflectionList
	 */
	public List<LabelValueModel> getCucmReflectionList() {
		return cucmReflectionList;
	}
	/**
	 * @param cucmReflectionList セットする cucmReflectionList
	 */
	public void setCucmReflectionList(List<LabelValueModel> cucmReflectionList) {
		this.cucmReflectionList = cucmReflectionList;
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
	 * @return pickupGropuList
	 */
	public List<LabelValueModel> getPickupGropuList() {
		return pickupGropuList;
	}
	/**
	 * @param pickupGropuList セットする pickupGropuList
	 */
	public void setPickupGropuList(List<LabelValueModel> pickupGropuList) {
		this.pickupGropuList = pickupGropuList;
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
	 * @return clusterList
	 */
	public List<LabelValueModel> getClusterList() {
		return clusterList;
	}
	/**
	 * @param clusterList セットする clusterList
	 */
	public void setClusterList(List<LabelValueModel> clusterList) {
		this.clusterList = clusterList;
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
	 * @return pickupGroupListJson
	 */
	public String getPickupGroupListJson() {
		return pickupGroupListJson;
	}
	/**
	 * @param pickupGroupListJson セットする pickupGroupListJson
	 */
	public void setPickupGroupListJson(String pickupGroupListJson) {
		this.pickupGroupListJson = pickupGroupListJson;
	}
	/**
	 * @return callingSearchSpaceListJson
	 */
	public String getCallingSearchSpaceListJson() {
		return callingSearchSpaceListJson;
	}
	/**
	 * @param callingSearchSpaceListJson セットする callingSearchSpaceListJson
	 */
	public void setCallingSearchSpaceListJson(String callingSearchSpaceListJson) {
		this.callingSearchSpaceListJson = callingSearchSpaceListJson;
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
	 * @return addonModuleListJson
	 */
	public String getAddonModuleListJson() {
		return addonModuleListJson;
	}
	/**
	 * @param addonModuleListJson セットする addonModuleListJson
	 */
	public void setAddonModuleListJson(String addonModuleListJson) {
		this.addonModuleListJson = addonModuleListJson;
	}
	/**
	 * @return ringSettingListJson
	 */
	public String getRingSettingListJson() {
		return ringSettingListJson;
	}
	/**
	 * @param ringSettingListJson セットする ringSettingListJson
	 */
	public void setRingSettingListJson(String ringSettingListJson) {
		this.ringSettingListJson = ringSettingListJson;
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
	 * @return parentSectionListJson
	 */
	public String getParentSectionListJson() {
		return parentSectionListJson;
	}
	/**
	 * @param parentSectionListJson セットする parentSectionListJson
	 */
	public void setParentSectionListJson(String parentSectionListJson) {
		this.parentSectionListJson = parentSectionListJson;
	}
}	
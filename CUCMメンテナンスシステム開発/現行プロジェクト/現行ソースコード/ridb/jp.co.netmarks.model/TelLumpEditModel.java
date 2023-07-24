/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditModel.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.netmarks.common.Constants;

import org.apache.commons.lang3.StringUtils;


/**
 * <pre>
 * 電話機一括登録用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/30
 */
public class TelLumpEditModel implements Serializable {


	private static final long serialVersionUID = -7021651759015476257L;

	/* チェック有無 */
	private boolean checked;

	/* 値の変更有無 */
	private boolean changed;

	/* 拠点（電話機） */
	private String branchTelId       = StringUtils.EMPTY;
	/* 店部課（電話機） */
	public String sectionTelId       = StringUtils.EMPTY;
	/* 会社（電話機） */
	public String companyTelId       = StringUtils.EMPTY;
	/* 店部課（電話機） カンマ区切りで会社ID、店部課IDがセットされている */
	public String companySectionTelId        = StringUtils.EMPTY;
	/* 電話機 */
	private String telTypeModel      = StringUtils.EMPTY;
	/* PhoneButtonTemplete */
	private String phoneButtonTemplete = StringUtils.EMPTY;
	/* MACアドレス */
	private String macAddress        = StringUtils.EMPTY;
	/* 内線番号 */
	private String directoryNumber   = StringUtils.EMPTY;
	/* 通話録音 */
	private String loggerData        = Constants.LOGGER_DATA_OFF;
	/* ダイアルイン */
	private String dialinNumber     = StringUtils.EMPTY;
	/* 課金先 拠点 */
	private String chargeAssociationBranchId        = StringUtils.EMPTY;
	/* 課金先 親店部課 */
	private String chargeAssociationParentSectionId = StringUtils.EMPTY;
	/* 課金先 店部課 */
	private String chargeAssociationSectionId       = StringUtils.EMPTY;

	/* ### 非入力項目 ### */
	/* 電話機ID*/
	private BigDecimal telId = null;
	/* Line ID*/
	private BigDecimal lineId = null;
	/* Calling Search Space Name*/
	private String cssName = StringUtils.EMPTY;
	/* Location Name*/
	private String locationName = StringUtils.EMPTY;
	/* Device Pool Name*/
	private String devicePoolName = StringUtils.EMPTY;
	/* Line Text Label*/
	private String lineTextLabel = StringUtils.EMPTY;
	/* External Phone Number Mask*/
	private String ExternalPhoneNumber = StringUtils.EMPTY;

	/*********************************************************************
	 * generated getter/setter
	 **********************************************************************/

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
	 * @return cssName
	 */
	public String getCssName() {
		return cssName;
	}
	/**
	 * @param cssName セットする cssName
	 */
	public void setCssName(String cssName) {
		this.cssName = cssName;
	}
	/**
	 * @return locationName
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @param locationName セットする locationName
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/**
	 * @return devicePoolName
	 */
	public String getDevicePoolName() {
		return devicePoolName;
	}
	/**
	 * @param devicePoolName セットする devicePoolName
	 */
	public void setDevicePoolName(String devicePoolName) {
		this.devicePoolName = devicePoolName;
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
	 * @return externalPhoneNumber
	 */
	public String getExternalPhoneNumber() {
		return ExternalPhoneNumber;
	}
	/**
	 * @param externalPhoneNumber セットする externalPhoneNumber
	 */
	public void setExternalPhoneNumber(String externalPhoneNumber) {
		ExternalPhoneNumber = externalPhoneNumber;
	}




}


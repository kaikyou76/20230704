/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditForm.java
 *
 * @date 2013/08/29
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form.list;

import java.io.Serializable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import jp.co.netmarks.common.Constants;

import jp.co.ksc.model.form.list.BaseGridRowForm;


/**
 * <pre>
 * 電話機一括登録画面リスト更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/29 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/29
 */
public class TelLumpEditUpdateForm extends BaseGridRowForm implements Serializable {

	private static final long serialVersionUID = 4173944529813876593L;

	/* ### 入力項目 ### */
	/* 拠点（電話機） */
	@NotEmpty
	private String branchTelId       = null;
	/* 店部課（電話機） */
	public String sectionTelId       = null;
	/* 会社（電話機） */
	public String companyTelId       = null;
	/* 店部課（電話機） カンマ区切りで会社ID、店部課IDがセットされている */
	@NotEmpty
	public String companySectionTelId        = null;
	/* 電話機 */
	@NotEmpty
	private String telTypeModel      = null;
	/* PhoneButtonTemplete */
	@NotEmpty
	private String phoneButtonTemplete = null;
	/* MACアドレス */
	@Size(min = 12, max = 12, message="{min}桁で入力してください。")
	@Pattern(regexp="^([a-fA-F0-9]+$)?$")
	private String macAddress        = null;
	/* 内線番号 */
	@Size(min = 8, max = 8, message="{min}桁で入力してください。")
	@Pattern(regexp="^([*#0-9]+$)?$")
	private String directoryNumber   = null;
	/* 通話録音 */
	private String loggerData        = null;
	/* ダイアルイン */
	@Size(min = 0, max = 24,message="{max}桁までで入力してください。")
	@Pattern(regexp="^([*#X0-9]+$)?$")
	private String dialinNumber     = null;
	/* 課金先 拠点 */
	@Pattern(regexp="^([0-9]+$)?$")
	@Size(min = 3, max = 3, message="{min}桁で入力してください。")
	private String chargeAssociationBranchId        = null;
	/* 課金先 親店部課 */
	@Pattern(regexp="^([0-9]+$)?$")
	@Size(min = 5, max = 5, message="{min}桁で入力してください。")
	private String chargeAssociationParentSectionId = null;
	/* 課金先 店部課 */
	@Pattern(regexp="^([0-9]+$)?$")
	@Size(min = 5, max = 5, message="{min}桁で入力してください。")
	private String chargeAssociationSectionId       = null;

	/* ### 非入力項目 ### */
	/* 電話機ID*/
	private String telId = null;
	/* Line ID*/
	private String lineId = null;
	/* Calling Search Space Name*/
	private String cssName = null;
	/* Location Name*/
	private String locationName = null;
	/* Device Pool Name*/
	private String devicePoolName = null;
	/* Line Text Label*/
	private String lineTextLabel = null;

	/**
	 * Validateを回避するダミー値をセットする
	 */
	@Override
	public void convetToNoValidateBean() {
		this.branchTelId                      = Constants.INPUT_DATA_STRING_NUMBER;
		this.companySectionTelId              = Constants.INPUT_DATA_STRING_NUMBER;
		this.telTypeModel                     = Constants.INPUT_DATA_STRING_NUMBER;
		this.phoneButtonTemplete              = Constants.INPUT_DATA_STRING_NUMBER;
		this.macAddress                       = Constants.INPUT_DATA_MAC_ADDRESS;
		this.directoryNumber                  = Constants.INPUT_DATA_DIRECTORY_NUMBER;
		this.loggerData                       = Constants.INPUT_DATA_STRING_NUMBER;
		this.dialinNumber                     = Constants.INPUT_DATA_STRING_NUMBER;
		this.chargeAssociationBranchId        = Constants.INPUT_DATA_CHARGE_BRANCH_ID;
		this.chargeAssociationParentSectionId = Constants.INPUT_DATA_CHARGE_SECTION_ID;
		this.chargeAssociationSectionId       = Constants.INPUT_DATA_CHARGE_SECTION_ID;
	}

	/**
	 * BigDecimalへの変換エラーを回避するダミー値をセットする
	 */
	public void convertToAvoidConversionExBean() {
		this.telId  = Constants.INPUT_DATA_STRING_NUMBER;
	}


	/*********************************************************************
	 * generated getter/setter
	**********************************************************************/

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
	public String getTelId() {
		return telId;
	}


	/**
	 * @param telId セットする telId
	 */
	public void setTelId(String telId) {
		this.telId = telId;
	}


	/**
	 * @return lineId
	 */
	public String getLineId() {
		return lineId;
	}


	/**
	 * @param lineId セットする lineId
	 */
	public void setLineId(String lineId) {
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
}
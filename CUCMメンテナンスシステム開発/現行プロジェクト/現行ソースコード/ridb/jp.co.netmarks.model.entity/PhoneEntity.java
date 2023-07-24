/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * PhoneEntity.java
 *
 * @date 2013/08/22
 * @version 1.0
 * @author KSC Yuchiro Yoshida
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * CUCM_PHONEテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class PhoneEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -8495365335785087795L;

	private BigDecimal cucmPhoneId           = null;
	private String phoneProductName          = null;
	private String macAddress                = null;
	private String description               = null;
	private String callingSearchSpaceName    = null;
	private String locationName              = null;
	private String devicePoolName            = null;
	private String phoneButtonTemplateName   = null;
	private String addonModuleName1          = null;
	private String addonModuleName2          = null;
	private String companyId                 = null;
	private String sectionId                 = null;
	private String branchId                  = null;
	private String cucmUpdateRequestFlag     = null;
	private String deleted                   = null;
	private String errorFlg                  = null;
	private String ownerUserId               = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/


	/**
	 * @return cucmPhoneId
	 */
	public BigDecimal getCucmPhoneId() {
		return cucmPhoneId;
	}
	/**
	 * @param cucmPhoneId セットする cucmPhoneId
	 */
	public void setCucmPhoneId(BigDecimal cucmPhoneId) {
		this.cucmPhoneId = cucmPhoneId;
	}
	/**
	 * @return phoneProductName
	 */
	public String getPhoneProductName() {
		return phoneProductName;
	}
	/**
	 * @param phoneProductName セットする phoneProductName
	 */
	public void setPhoneProductName(String phoneProductName) {
		this.phoneProductName = phoneProductName;
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
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description セットする description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return phoneButtonTemplateName
	 */
	public String getPhoneButtonTemplateName() {
		return phoneButtonTemplateName;
	}
	/**
	 * @param phoneButtonTemplateName セットする phoneButtonTemplateName
	 */
	public void setPhoneButtonTemplateName(String phoneButtonTemplateName) {
		this.phoneButtonTemplateName = phoneButtonTemplateName;
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
	 * @return companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId セットする companyId
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return sectionId
	 */
	public String getSectionId() {
		return sectionId;
	}
	/**
	 * @param sectionId セットする sectionId
	 */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	/**
	 * @return branchId
	 */
	public String getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId セットする branchId
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return cucmUpdateRequestFlag
	 */
	public String getCucmUpdateRequestFlag() {
		return cucmUpdateRequestFlag;
	}
	/**
	 * @param cucmUpdateRequestFlag セットする cucmUpdateRequestFlag
	 */
	public void setCucmUpdateRequestFlag(String cucmUpdateRequestFlag) {
		this.cucmUpdateRequestFlag = cucmUpdateRequestFlag;
	}
	/**
	 * @return deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted セットする deleted
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return errorFlg
	 */
	public String getErrorFlg() {
		return errorFlg;
	}
	/**
	 * @param errorFlg セットする errorFlg
	 */
	public void setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
	}
	/**
	 * @return ownerUserId
	 */
	public String getOwnerUserId() {
		return ownerUserId;
	}
	/**
	 * @param ownerUserId セットする ownerUserId
	 */
	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
}
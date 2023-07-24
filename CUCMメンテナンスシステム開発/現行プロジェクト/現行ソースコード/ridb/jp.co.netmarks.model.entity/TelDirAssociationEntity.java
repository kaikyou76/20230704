/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelDirAssociationEntity.java
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
 * TEL_DIR_ASSOCIATIONテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class TelDirAssociationEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3854852397870204004L;

	private BigDecimal telDirAssociationId = null;
	private BigDecimal cucmPhoneId         = null;
	private BigDecimal cucmLineId          = null;
	private BigDecimal appUserId           = null;
	private String statusCode              = null;
	private String associateCode           = null;
	private String telDirData              = null;
	private String cucmUpdateRequestFlag   = null;
	private String deleted                 = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/


	/**
	 * @return telDirAssociationId
	 */
	public BigDecimal getTelDirAssociationId() {
		return telDirAssociationId;
	}
	/**
	 * @param telDirAssociationId セットする telDirAssociationId
	 */
	public void setTelDirAssociationId(BigDecimal telDirAssociationId) {
		this.telDirAssociationId = telDirAssociationId;
	}
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
	 * @return cucmLineId
	 */
	public BigDecimal getCucmLineId() {
		return cucmLineId;
	}
	/**
	 * @param cucmLineId セットする cucmLineId
	 */
	public void setCucmLineId(BigDecimal cucmLineId) {
		this.cucmLineId = cucmLineId;
	}
	/**
	 * @return appUserId
	 */
	public BigDecimal getAppUserId() {
		return appUserId;
	}
	/**
	 * @param appUserId セットする appUserId
	 */
	public void setAppUserId(BigDecimal appUserId) {
		this.appUserId = appUserId;
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
	 * @return associateCode
	 */
	public String getAssociateCode() {
		return associateCode;
	}
	/**
	 * @param associateCode セットする associateCode
	 */
	public void setAssociateCode(String associateCode) {
		this.associateCode = associateCode;
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
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ChargeAssociationEntity.java
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
 * CHARGE_ASSOCIATIONテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class ChargeAssociationEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -5945687480791866252L;

	private BigDecimal cucmLineId         = null;
	private String header                 = null;
	private String statusCode             = null;
	private String branchId               = null;
	private String companyId              = null;
	private String parentSectionId        = null;
	private String sectionId              = null;
	private String remarks                = null;
	private String cucmUpdateRequestFlag  = null;
	private String deleted                = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

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
	 * @return header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header セットする header
	 */
	public void setHeader(String header) {
		this.header = header;
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
	 * @return parentSectionId
	 */
	public String getParentSectionId() {
		return parentSectionId;
	}
	/**
	 * @param parentSectionId セットする parentSectionId
	 */
	public void setParentSectionId(String parentSectionId) {
		this.parentSectionId = parentSectionId;
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
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks セットする remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
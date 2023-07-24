/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserSectionEntity.java
 *
 * @date 2013/08/22
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * R_USER_SECTIONテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/22
 */
public class UserSectionEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3655382925494939284L;

	private BigDecimal appUserId = null;
	private String companyId     = null;
	private String sectionId     = null;
	private String sectionName   = null;
	private String printOrder    = null;
	private String deleteReserve = null;
	private String deleted       = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

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
	 * @return sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * @param sectionName セットする sectionName
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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
	 * @return deleteReserve
	 */
	public String getDeleteReserve() {
		return deleteReserve;
	}
	/**
	 * @param deleteReserve セットする deleteReserve
	 */
	public void setDeleteReserve(String deleteReserve) {
		this.deleteReserve = deleteReserve;
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
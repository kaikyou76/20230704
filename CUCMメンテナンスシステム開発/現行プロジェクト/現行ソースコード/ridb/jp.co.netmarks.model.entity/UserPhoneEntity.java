/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserPhoneEntity.java
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
 * R_CCM_USER_PHONEテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class UserPhoneEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -1880638484227387104L;

	private BigDecimal appUserId     = null;
	private BigDecimal cucmPhoneId   = null;
	private String companyId         = null;
	private String sectionId         = null;
	private String cucmUpdateRequestFlag  = null;
	private String deleted           = null;
	private String errorFlg          = null;

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
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelUpdateForm.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 電話機の検索と選択画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
public class AddTelUpdateForm extends BaseForm implements  Serializable {

	private static final long serialVersionUID = -6069851581403315077L;

	/* ユーザーID */
	private String userId        = null;
	/* 電話ID */
	private String telId         = null;
	/* LineID */
	private String lineId        = null;
	/* 店部課ID（ユーザー）  */
	private String sectionUserId = null;
	/* 会社ID（ユーザー） */
	private String companyUserId = null;

	/* ### オリジナル項目 ### */
	/* ユーザーID */
	private String orgUserId     = null;
	/* 電話ID */
	private String orgTelId      = null;
	/* ラインID */
	private String orgLineId     = null;
	/* 店部課ID（ユーザー）  */
	private String orgSectionUserId = null;
	/* 会社ID（ユーザー） */
	private String orgCompanyUserId = null;
	
	
/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return orgUserId
	 */
	public String getOrgUserId() {
		return orgUserId;
	}
	/**
	 * @param orgUserId セットする orgUserId
	 */
	public void setOrgUserId(String orgUserId) {
		this.orgUserId = orgUserId;
	}
	/**
	 * @return orgTelId
	 */
	public String getOrgTelId() {
		return orgTelId;
	}
	/**
	 * @param orgTelId セットする orgTelId
	 */
	public void setOrgTelId(String orgTelId) {
		this.orgTelId = orgTelId;
	}
	/**
	 * @return orgLineId
	 */
	public String getOrgLineId() {
		return orgLineId;
	}
	/**
	 * @param orgLineId セットする orgLineId
	 */
	public void setOrgLineId(String orgLineId) {
		this.orgLineId = orgLineId;
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
}
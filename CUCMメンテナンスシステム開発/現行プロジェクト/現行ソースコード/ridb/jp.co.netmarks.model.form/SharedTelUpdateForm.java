/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * SharedTelUpdateForm.java
 *
 * @date 2013/09/14
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 電話機の共用化画面用フォーム
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/14 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/14
 */
public class SharedTelUpdateForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = -2022302619987408394L;

	/* #### 入力項目 #### */
	/* 共用電話名 */
	@NotEmpty
	private String sharedUserName = null;

	/* #### 非入力項目 #### */
	/* ユーザーID */
	private String userId               = null;
	/* 電話ID */
	private String telId                = null;
	/* LineID */
	private String lineId               = null;
	/* ラインIndex */
	private String lineIndex            = null;
	/* 店部課ID（ユーザー）  */
	private String sectionUserId        = null;
	/* 会社ID（ユーザー） */
	private String companyUserId        = null;
	/* 共有区分 */
	private String sharedUse            = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

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
}
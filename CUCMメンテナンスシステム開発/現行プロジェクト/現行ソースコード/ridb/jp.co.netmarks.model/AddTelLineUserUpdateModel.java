/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelLineUserUpdateModel.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.AbstractSearchModel;

/**
 * <pre>
 * ユーザ×電話×ライン紐付き登録用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
public class AddTelLineUserUpdateModel extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = -3875873143879361098L;

	/* ユーザーID */
	private BigDecimal userId        = null;
	/* 電話ID */
	private BigDecimal telId         = null;
	/* LineID */
	private BigDecimal lineId        = null;
	/* 店部課ID（ユーザー）  */
	private String sectionUserId     = null;
	/* 会社ID（ユーザー） */
	private String companyUserId     = null;
	/* ライン連番 */
	private String lineIndex         = null;
	/* 内線番号 */
	private String directoryNumber   = null;
	/* ダイアルイン */
	public String dialinNumber       = null;
	/* 課金先-拠点 */
	public String chargeAssociationBranchId        = null;
	/* 課金先-親店部課 */
	public String chargeAssociationParentSectionId = null;
	/* 課金先-店部課 */
	public String chargeAssociationSectionId       = null;
	/* voiceMail */
	public String voiceMailFlg       = null;
	/* 拠点ID（電話） */
	private String branchTelId     = null;

	/* ### オリジナル項目 ### */
	/* ユーザーID */
	private BigDecimal orgUserId     = null;
	/* 電話ID */
	private BigDecimal orgTelId      = null;
	/* ラインID */
	private BigDecimal orgLineId     = null;
	/* ライン連番 */
	private String orgLineIndex      = null;
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
	public BigDecimal getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
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
	 * @return orgUserId
	 */
	public BigDecimal getOrgUserId() {
		return orgUserId;
	}
	/**
	 * @param orgUserId セットする orgUserId
	 */
	public void setOrgUserId(BigDecimal orgUserId) {
		this.orgUserId = orgUserId;
	}
	/**
	 * @return orgTelId
	 */
	public BigDecimal getOrgTelId() {
		return orgTelId;
	}
	/**
	 * @param orgTelId セットする orgTelId
	 */
	public void setOrgTelId(BigDecimal orgTelId) {
		this.orgTelId = orgTelId;
	}
	/**
	 * @return orgLineId
	 */
	public BigDecimal getOrgLineId() {
		return orgLineId;
	}
	/**
	 * @param orgLineId セットする orgLineId
	 */
	public void setOrgLineId(BigDecimal orgLineId) {
		this.orgLineId = orgLineId;
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
	 * @return orgLineIndex
	 */
	public String getOrgLineIndex() {
		return orgLineIndex;
	}
	/**
	 * @param orgLineIndex セットする orgLineIndex
	 */
	public void setOrgLineIndex(String orgLineIndex) {
		this.orgLineIndex = orgLineIndex;
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
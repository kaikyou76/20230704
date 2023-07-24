/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineUpdateForm.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.ksc.model.form.BaseForm;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <pre>
 * ラインの検索と選択画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
public class AddLineUpdateForm  extends BaseForm implements  Serializable {

	private static final long serialVersionUID = 2243717478597908939L;

	/* ユーザーID */
	private String userId          = null;
	/* 電話ID */
	private String telId           = null;
	/* LineID */
	private String lineId          = null;
	/* Line連番 */
	@NotEmpty
	@Pattern(regexp="^([0-9]+)?$")
	private String lineIndex       = null;
	/* 拠点ID（電話） */
	private String branchTelId     = null;

	/* ### オリジナル項目 ### */
	/* ユーザーID */
	private String orgUserId       = null;
	/* 電話ID */
	private String orgTelId        = null;
	/* ラインID */
	private String orgLineId       = null;
	/* Line連番 */
	private String orgLineIndex    = null;

	/* ### 新規ライン登録用 ### */
	/* Line連番 */
	@NotEmpty
	@Pattern(regexp="^([0-9]+)?$")
	private String newLineIndex    = null;
	/* 内線番号 */
	@NotEmpty
	@Pattern(regexp="^$|[*#0-9]+$")
	@Size(min = 8, max = 8, message="{min}桁で入力してください。")
	private String newLineDirectoryNumber = null;
	/* ダイアルイン */
	@Pattern(regexp="^$|[*#X0-9]+$")
	public String newLineDialinNumber     = null;
	/* 課金先-拠点 */
	@Pattern(regexp="^([0-9]+)?$")
	@Length(min=3, max=3, message="{min}桁で入力してください。")
	public String newLineChargeAssociationBranchId        = null;
	/* 課金先-親店部課 */
	@Pattern(regexp="^([0-9]+)?$")
	@Length(min=5, max=5, message="{min}桁で入力してください。")
	public String newLineChargeAssociationParentSectionId = null;
	/* 課金先-店部課 */
	@Pattern(regexp="^([0-9]+)?$")
	@Length(min=5, max=5, message="{min}桁で入力してください。")
	public String newLineChargeAssociationSectionId       = null;
	/* voiceMail */
	public String newLineVoiceMailFlg     = null;

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
	 * @return newLineIndex
	 */
	public String getNewLineIndex() {
		return newLineIndex;
	}
	/**
	 * @param newLineIndex セットする newLineIndex
	 */
	public void setNewLineIndex(String newLineIndex) {
		this.newLineIndex = newLineIndex;
	}
	/**
	 * @return newLineDirectoryNumber
	 */
	public String getNewLineDirectoryNumber() {
		return newLineDirectoryNumber;
	}
	/**
	 * @param newLineDirectoryNumber セットする newLineDirectoryNumber
	 */
	public void setNewLineDirectoryNumber(String newLineDirectoryNumber) {
		this.newLineDirectoryNumber = newLineDirectoryNumber;
	}
	/**
	 * @return newLineDialinNumber
	 */
	public String getNewLineDialinNumber() {
		return newLineDialinNumber;
	}
	/**
	 * @param newLineDialinNumber セットする newLineDialinNumber
	 */
	public void setNewLineDialinNumber(String newLineDialinNumber) {
		this.newLineDialinNumber = newLineDialinNumber;
	}
	/**
	 * @return newLineChargeAssociationBranchId
	 */
	public String getNewLineChargeAssociationBranchId() {
		return newLineChargeAssociationBranchId;
	}
	/**
	 * @param newLineChargeAssociationBranchId セットする newLineChargeAssociationBranchId
	 */
	public void setNewLineChargeAssociationBranchId(
			String newLineChargeAssociationBranchId) {
		this.newLineChargeAssociationBranchId = newLineChargeAssociationBranchId;
	}
	/**
	 * @return newLineChargeAssociationParentSectionId
	 */
	public String getNewLineChargeAssociationParentSectionId() {
		return newLineChargeAssociationParentSectionId;
	}
	/**
	 * @param newLineChargeAssociationParentSectionId セットする newLineChargeAssociationParentSectionId
	 */
	public void setNewLineChargeAssociationParentSectionId(
			String newLineChargeAssociationParentSectionId) {
		this.newLineChargeAssociationParentSectionId = newLineChargeAssociationParentSectionId;
	}
	/**
	 * @return newLineChargeAssociationSectionId
	 */
	public String getNewLineChargeAssociationSectionId() {
		return newLineChargeAssociationSectionId;
	}
	/**
	 * @param newLineChargeAssociationSectionId セットする newLineChargeAssociationSectionId
	 */
	public void setNewLineChargeAssociationSectionId(
			String newLineChargeAssociationSectionId) {
		this.newLineChargeAssociationSectionId = newLineChargeAssociationSectionId;
	}
	/**
	 * @return newLineVoiceMailFlg
	 */
	public String getNewLineVoiceMailFlg() {
		return newLineVoiceMailFlg;
	}
	/**
	 * @param newLineVoiceMailFlg セットする newLineVoiceMailFlg
	 */
	public void setNewLineVoiceMailFlg(String newLineVoiceMailFlg) {
		this.newLineVoiceMailFlg = newLineVoiceMailFlg;
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelForm.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.List;

import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 電話機の検索と選択画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
public class AddTelForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = -2382454483793540390L;

	/* ユーザーカナ名 */
	private String addTelDirectoryNumber   = null;
	/* ステータス */
	private String telStatus               = null;
	/* 拠点ID */
	private String addTelBranchId          = null;
	/* 会社－店部課 */
	private String addTelCompanySectionId  = null;
	/* 課名 */
	private String addTelAttachSectionName = null;
	/* 拠点（課金先） */
	private String addTelChargeAssociationBranchId        = null;
	/* 親店部課（課金先） */
	private String addTelChargeAssociationParentSectionId = null;
	/* 店部課（課金先） */
	private String addTelChargeAssociationSectionId       = null;
	/* 選択元の電話機ID */
	private String orgTelId                = null;

	/** ### List ### **/
	/* 拠点（ユーザ）リスト */
	private List<LabelValueModel> branchList    = null;
	/* 店部課（ユーザ）リスト */
	private List<LabelValueModel> sectionList   = null;
	/* 電話保持ステータス */
	private List<LabelValueModel> telStatusList = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return branchList
	 */
	public List<LabelValueModel> getBranchList() {
		return branchList;
	}
	/**
	 * @return addTelDirectoryNumber
	 */
	public String getAddTelDirectoryNumber() {
		return addTelDirectoryNumber;
	}
	/**
	 * @param addTelDirectoryNumber セットする addTelDirectoryNumber
	 */
	public void setAddTelDirectoryNumber(String addTelDirectoryNumber) {
		this.addTelDirectoryNumber = addTelDirectoryNumber;
	}
	/**
	 * @return telStatus
	 */
	public String getTelStatus() {
		return telStatus;
	}
	/**
	 * @param telStatus セットする telStatus
	 */
	public void setTelStatus(String telStatus) {
		this.telStatus = telStatus;
	}
	/**
	 * @return addTelBranchId
	 */
	public String getAddTelBranchId() {
		return addTelBranchId;
	}
	/**
	 * @param addTelBranchId セットする addTelBranchId
	 */
	public void setAddTelBranchId(String addTelBranchId) {
		this.addTelBranchId = addTelBranchId;
	}
	/**
	 * @return addTelCompanySectionId
	 */
	public String getAddTelCompanySectionId() {
		return addTelCompanySectionId;
	}
	/**
	 * @param addTelCompanySectionId セットする addTelCompanySectionId
	 */
	public void setAddTelCompanySectionId(String addTelCompanySectionId) {
		this.addTelCompanySectionId = addTelCompanySectionId;
	}
	/**
	 * @return addTelAttachSectionName
	 */
	public String getAddTelAttachSectionName() {
		return addTelAttachSectionName;
	}
	/**
	 * @param addTelAttachSectionName セットする addTelAttachSectionName
	 */
	public void setAddTelAttachSectionName(String addTelAttachSectionName) {
		this.addTelAttachSectionName = addTelAttachSectionName;
	}
	/**
	 * @return addTelChargeAssociationBranchId
	 */
	public String getAddTelChargeAssociationBranchId() {
		return addTelChargeAssociationBranchId;
	}
	/**
	 * @param addTelChargeAssociationBranchId セットする addTelChargeAssociationBranchId
	 */
	public void setAddTelChargeAssociationBranchId(
			String addTelChargeAssociationBranchId) {
		this.addTelChargeAssociationBranchId = addTelChargeAssociationBranchId;
	}
	/**
	 * @return addTelChargeAssociationParentSectionId
	 */
	public String getAddTelChargeAssociationParentSectionId() {
		return addTelChargeAssociationParentSectionId;
	}
	/**
	 * @param addTelChargeAssociationParentSectionId セットする addTelChargeAssociationParentSectionId
	 */
	public void setAddTelChargeAssociationParentSectionId(
			String addTelChargeAssociationParentSectionId) {
		this.addTelChargeAssociationParentSectionId = addTelChargeAssociationParentSectionId;
	}
	/**
	 * @return addTelChargeAssociationSectionId
	 */
	public String getAddTelChargeAssociationSectionId() {
		return addTelChargeAssociationSectionId;
	}
	/**
	 * @param addTelChargeAssociationSectionId セットする addTelChargeAssociationSectionId
	 */
	public void setAddTelChargeAssociationSectionId(
			String addTelChargeAssociationSectionId) {
		this.addTelChargeAssociationSectionId = addTelChargeAssociationSectionId;
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
	 * @param branchList セットする branchList
	 */
	public void setBranchList(List<LabelValueModel> branchList) {
		this.branchList = branchList;
	}
	/**
	 * @return sectionList
	 */
	public List<LabelValueModel> getSectionList() {
		return sectionList;
	}
	/**
	 * @param sectionList セットする sectionList
	 */
	public void setSectionList(List<LabelValueModel> sectionList) {
		this.sectionList = sectionList;
	}
	/**
	 * @return telStatusList
	 */
	public List<LabelValueModel> getTelStatusList() {
		return telStatusList;
	}
	/**
	 * @param telStatusList セットする telStatusList
	 */
	public void setTelStatusList(List<LabelValueModel> telStatusList) {
		this.telStatusList = telStatusList;
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddUserForm.java
 *
 * @date 2013/08/30
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
 * ユーザーの検索と選択画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/30
 */
public class AddUserForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 4814903906870647371L;

	/* ユーザーカナ名 */
	private String addUserKanaUserName      = null;
	/* ステータス */
	private String retainTelStatus          = null;
	/* 拠点ID */
	private String addUserBranchId          = null;
	/* 会社－店部課 */
	private String addUserCompanySectionId  = null;
	/* 課名 */
	private String addUserAttachSectionName = null;
	/* 選択元のユーザーID */
	private String orgUserId                = null;
	/* 選択元のユーザーID */
	private String orgCompanyUserId         = null;
	/* 選択元のユーザーID */
	private String orgSectionUserId         = null;
	

	/** ### List ### **/
	/* 拠点（ユーザ）リスト */
	private List<LabelValueModel> branchList          = null;
	/* 店部課（ユーザ）リスト */
	private List<LabelValueModel> sectionList         = null;
	/* 電話保持ステータス */
	private List<LabelValueModel> retainTelStatusList     = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return addUserKanaUserName
	 */
	public String getAddUserKanaUserName() {
		return addUserKanaUserName;
	}
	/**
	 * @param addUserKanaUserName セットする addUserKanaUserName
	 */
	public void setAddUserKanaUserName(String addUserKanaUserName) {
		this.addUserKanaUserName = addUserKanaUserName;
	}
	/**
	 * @return retainTelStatus
	 */
	public String getRetainTelStatus() {
		return retainTelStatus;
	}
	/**
	 * @param retainTelStatus セットする retainTelStatus
	 */
	public void setRetainTelStatus(String retainTelStatus) {
		this.retainTelStatus = retainTelStatus;
	}
	/**
	 * @return addUserBranchId
	 */
	public String getAddUserBranchId() {
		return addUserBranchId;
	}
	/**
	 * @param addUserBranchId セットする addUserBranchId
	 */
	public void setAddUserBranchId(String addUserBranchId) {
		this.addUserBranchId = addUserBranchId;
	}
	/**
	 * @return addUserCompanySectionId
	 */
	public String getAddUserCompanySectionId() {
		return addUserCompanySectionId;
	}
	/**
	 * @param addUserCompanySectionId セットする addUserCompanySectionId
	 */
	public void setAddUserCompanySectionId(String addUserCompanySectionId) {
		this.addUserCompanySectionId = addUserCompanySectionId;
	}
	/**
	 * @return addUserAttachSectionName
	 */
	public String getAddUserAttachSectionName() {
		return addUserAttachSectionName;
	}
	/**
	 * @param addUserAttachSectionName セットする addUserAttachSectionName
	 */
	public void setAddUserAttachSectionName(String addUserAttachSectionName) {
		this.addUserAttachSectionName = addUserAttachSectionName;
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
	 * @return branchList
	 */
	public List<LabelValueModel> getBranchList() {
		return branchList;
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
	 * @return retainTelStatusList
	 */
	public List<LabelValueModel> getRetainTelStatusList() {
		return retainTelStatusList;
	}
	/**
	 * @param retainTelStatusList セットする retainTelStatusList
	 */
	public void setRetainTelStatusList(List<LabelValueModel> retainTelStatusList) {
		this.retainTelStatusList = retainTelStatusList;
	}
}
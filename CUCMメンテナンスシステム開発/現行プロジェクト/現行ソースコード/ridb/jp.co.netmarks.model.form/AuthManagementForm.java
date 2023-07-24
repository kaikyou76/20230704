/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementForm.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 権限管理画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/09
 */
public class AuthManagementForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 拠点（ユーザー） */
	@NotEmpty
	private String branchUserId      = null;
	/* 店部課（ユーザー）  */
	@NotEmpty
	private String sectionUserId     = null;

	/** ### List ### **/
	/* 拠点（ユーザ）リスト */
	private List<LabelValueModel> branchUserList          = null;
	/* 店部課（ユーザ）リスト */
	private List<LabelValueModel> sectionUserList         = null;

	/** ### JsonList ### **/
	/* 処理セレクトタグ */
	private String managementListJson             = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return branchUserId
	 */
	public String getBranchUserId() {
		return branchUserId;
	}
	/**
	 * @param branchUserId セットする branchUserId
	 */
	public void setBranchUserId(String branchUserId) {
		this.branchUserId = branchUserId;
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
	 * @return branchUserList
	 */
	public List<LabelValueModel> getBranchUserList() {
		return branchUserList;
	}
	/**
	 * @param branchUserList セットする branchUserList
	 */
	public void setBranchUserList(List<LabelValueModel> branchUserList) {
		this.branchUserList = branchUserList;
	}
	/**
	 * @return sectionUserList
	 */
	public List<LabelValueModel> getSectionUserList() {
		return sectionUserList;
	}
	/**
	 * @param sectionUserList セットする sectionUaerList
	 */
	public void setSectionUserList(List<LabelValueModel> sectionUserList) {
		this.sectionUserList = sectionUserList;
	}
	/**
	 * @return managementListJson
	 */
	public String getManagementListJson() {
		return managementListJson;
	}
	/**
	 * @param managementListJson セットする managementListJson
	 */
	public void setManagementListJson(String managementListJson) {
		this.managementListJson = managementListJson;
	}


}
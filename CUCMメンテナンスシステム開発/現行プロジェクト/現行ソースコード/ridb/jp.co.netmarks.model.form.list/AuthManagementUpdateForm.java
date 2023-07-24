/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementUpdateForm.java
 *
 * @date 2013/09/11
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form.list;

import java.io.Serializable;
import jp.co.ksc.model.form.list.BaseGridRowForm;

/**
 * <pre>
 * 権限管理画面用リスト更新フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/11 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/11
 */
public class AuthManagementUpdateForm extends BaseGridRowForm implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 社員コード */
	private String employeeId           = null;
	/* ユーザID */
	private String userId               = null;
	/* ユーザ名_漢字 */
	private String kanjiUserName        = null;
	/* 権限区分 */
	private String authId               = null;
	/* 権限名 */
	private String authName             = null;
	/* 拠点名 */
	private String branchUserName       = null;
	/* 店部課名 */
	private String sectionUserName      = null;

	/**
	 * Validateを回避するダミー値をセットする
	 */
	@Override
	public void convetToNoValidateBean() {

	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId セットする employeeId
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	 * @return kanjiUserName
	 */
	public String getKanjiUserName() {
		return kanjiUserName;
	}
	/**
	 * @param kanjiUserName セットする kanjiUserName
	 */
	public void setKanjiUserName(String kanjiUserName) {
		this.kanjiUserName = kanjiUserName;
	}
	/**
	 * @return authId
	 */
	public String getAuthId() {
		return authId;
	}
	/**
	 * @param authId セットする authId
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	/**
	 * @return authName
	 */
	public String getAuthName() {
		return authName;
	}
	/**
	 * @param authName セットする authName
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	/**
	 * @return branchUserName
	 */
	public String getBranchUserName() {
		return branchUserName;
	}
	/**
	 * @param branchUserName セットする branchUserName
	 */
	public void setBranchUserName(String branchUserName) {
		this.branchUserName = branchUserName;
	}
	/**
	 * @return sectionUserName
	 */
	public String getSectionUserName() {
		return sectionUserName;
	}
	/**
	 * @param sectionUserName セットする sectionUserName
	 */
	public void setSectionUserName(String sectionUserName) {
		this.sectionUserName = sectionUserName;
	}
}
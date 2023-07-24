/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementResultModel.java
 *
 * @date 2013/09/11
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 管理権限付与者の一覧結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/11 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/11
 */
public class AuthManagementResultModel implements Serializable{

	private static final long serialVersionUID = 5886075124883592393L;

	/* 社員コード */
	private String employeeId           = StringUtils.EMPTY;
	/* ユーザID */
	private BigDecimal userId           = new BigDecimal(0);
	/* ユーザ名_漢字 */
	private String kanjiUserName        = StringUtils.EMPTY;
	/* 権限区分 */
	private String authId               = StringUtils.EMPTY;
	/* 権限名 */
	private String authName             = StringUtils.EMPTY;
	/* ロック状態名 */
	private String lockStateName        = StringUtils.EMPTY;
	/* 拠点名 */
	private String branchUserName       = StringUtils.EMPTY;
	/* 店部課名 */
	private String sectionUserName      = StringUtils.EMPTY;

	/* 拠点ID */
	private String branchUserId      = StringUtils.EMPTY;
	/* 会社-店部課ID  */
	private String sectionUserId     = StringUtils.EMPTY;

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
	 * @return lockStateName
	 */
	public String getLockStateName() {
		return lockStateName;
	}
	/**
	 * @param lockStateName セットする lockStateName
	 */
	public void setLockStateName(String lockStateName) {
		this.lockStateName = lockStateName;
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




}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementModel.java
 *
 * @date 2013/09/10
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.util.Map;

import jp.co.ksc.model.AbstractSearchModel;
import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * 権限管理用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/10 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/10
 */
public class AuthManagementModel extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 拠点（ユーザ） */
	private String branchUserId      = null;
	/* 店部課（ユーザー）  */
	private String sectionUserId     = null;
	/* 会社ID（ユーザー） */
	private String companyUserId     = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
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


}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceUpdateModel.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * メンテナンス更新用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public class MaintenanceUpdateModel implements Serializable{

	private static final long serialVersionUID = 5975213901401705856L;

	/* 拠点コード */
	private String branchId        = null;
	/* 拠点名 */
	private String branchName      = null;
	/* クラスタID */
	private BigDecimal clusterId   = null;
	/* 会社コード */
	private String companyId       = null;
	/* 店部課コード */
	private String sectionId       = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return branchId
	 */
	public String getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId セットする branchId
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName セットする branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return clusterId
	 */
	public BigDecimal getClusterId() {
		return clusterId;
	}
	/**
	 * @param clusterId セットする clusterId
	 */
	public void setClusterId(BigDecimal clusterId) {
		this.clusterId = clusterId;
	}
	/**
	 * @return companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId セットする companyId
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return sectionId
	 */
	public String getSectionId() {
		return sectionId;
	}
	/**
	 * @param sectionId セットする sectionId
	 */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}



}
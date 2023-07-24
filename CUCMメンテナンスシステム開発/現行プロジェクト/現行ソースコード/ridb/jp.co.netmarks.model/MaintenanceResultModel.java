/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceResultModel.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * メンテナンス検索結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public class MaintenanceResultModel implements Serializable {


	private static final long serialVersionUID = 1799475124610286646L;

	/* 拠点ID */
	private String branchId         = StringUtils.EMPTY;
	/* 拠点名 */
	private String branchName       = StringUtils.EMPTY;
	/* クラスタID */
	private String clusterId        = StringUtils.EMPTY;
	/* クラスタ名 */
	private String clusterName      = StringUtils.EMPTY;
	/* 会社ID */
	private String companyId        = StringUtils.EMPTY;
	/* 店部課ID */
	private String sectionId        = StringUtils.EMPTY;
	/* 店部課名 */
	private String sectionName      = StringUtils.EMPTY;

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
	public String getClusterId() {
		return clusterId;
	}
	/**
	 * @param clusterId セットする clusterId
	 */
	public void setClusterId(String clusterId) {
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
	 * @return clusterName
	 */
	public String getClusterName() {
		return clusterName;
	}
	/**
	 * @param clusterName セットする clusterName
	 */
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
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
	/**
	 * @return sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * @param sectionName セットする sectionName
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}


}
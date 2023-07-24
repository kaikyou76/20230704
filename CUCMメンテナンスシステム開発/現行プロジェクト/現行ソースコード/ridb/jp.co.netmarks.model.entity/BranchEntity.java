/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BranchEntity.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * M_BRANCHテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public class BranchEntity extends BaseEntity implements Serializable{


	private static final long serialVersionUID = 3439621236295067472L;

	private String branchId          = null;
	private String branchName        = null;
	private BigDecimal clusterId     = null;

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



}
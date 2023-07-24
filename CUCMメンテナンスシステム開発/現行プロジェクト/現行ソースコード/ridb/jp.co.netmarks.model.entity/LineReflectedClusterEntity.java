/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LineReflectedClusterEntity.java
 *
 * @date 2013/10/25
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * LINE_REFLECTED_CLUSTERテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/25 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/25
 */
public class LineReflectedClusterEntity  extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5685606510994287774L;
	
	private BigDecimal cucmLineId;
	private BigDecimal clusterId;
	private String linePkid;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return cucmLineId
	 */
	public BigDecimal getCucmLineId() {
		return cucmLineId;
	}
	/**
	 * @param cucmLineId セットする cucmLineId
	 */
	public void setCucmLineId(BigDecimal cucmLineId) {
		this.cucmLineId = cucmLineId;
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
	 * @return linePkid
	 */
	public String getLinePkid() {
		return linePkid;
	}
	/**
	 * @param linePkid セットする linePkid
	 */
	public void setLinePkid(String linePkid) {
		this.linePkid = linePkid;
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * PhoneReflectedClusterEntity.java
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
 * PHONE_REFLECTED_CLUSTERテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/25 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/25
 */
public class PhoneReflectedClusterEntity  extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2597020724589864883L;

	private BigDecimal cucmPhoneId;
	private BigDecimal clusterId;
	private String phonePkid;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return cucmPhoneId
	 */
	public BigDecimal getCucmPhoneId() {
		return cucmPhoneId;
	}
	/**
	 * @param cucmPhoneId セットする cucmPhoneId
	 */
	public void setCucmPhoneId(BigDecimal cucmPhoneId) {
		this.cucmPhoneId = cucmPhoneId;
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
	 * @return phonePkid
	 */
	public String getPhonePkid() {
		return phonePkid;
	}
	/**
	 * @param phonePkid セットする phonePkid
	 */
	public void setPhonePkid(String phonePkid) {
		this.phonePkid = phonePkid;
	}
}
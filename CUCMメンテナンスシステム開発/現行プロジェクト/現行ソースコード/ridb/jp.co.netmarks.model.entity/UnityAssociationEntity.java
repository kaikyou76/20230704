/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UnityAssociationEntity.java
 *
 * @date 2013/08/22
 * @version 1.0
 * @author KSC Yuchiro Yoshida
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * UNITY_ASSOCIATIONテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class UnityAssociationEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -4789319378494660407L;

	private BigDecimal cucmPhoneId         = null;
	private String statusCode              = null;
	private String associateCode           = null;
	private String unityData               = null;
	private String voiceMailProfileClass   = null;
	private String cucmUpdateRequestFlag   = null;
	private String deleted                 = null;

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
	 * @return statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode セットする statusCode
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return associateCode
	 */
	public String getAssociateCode() {
		return associateCode;
	}
	/**
	 * @param associateCode セットする associateCode
	 */
	public void setAssociateCode(String associateCode) {
		this.associateCode = associateCode;
	}
	/**
	 * @return unityData
	 */
	public String getUnityData() {
		return unityData;
	}
	/**
	 * @param unityData セットする unityData
	 */
	public void setUnityData(String unityData) {
		this.unityData = unityData;
	}
	/**
	 * @return voiceMailProfileClass
	 */
	public String getVoiceMailProfileClass() {
		return voiceMailProfileClass;
	}
	/**
	 * @param voiceMailProfileClass セットする voiceMailProfileClass
	 */
	public void setVoiceMailProfileClass(String voiceMailProfileClass) {
		this.voiceMailProfileClass = voiceMailProfileClass;
	}
	/**
	 * @return cucmUpdateRequestFlag
	 */
	public String getCucmUpdateRequestFlag() {
		return cucmUpdateRequestFlag;
	}
	/**
	 * @param cucmUpdateRequestFlag セットする cucmUpdateRequestFlag
	 */
	public void setCucmUpdateRequestFlag(String cucmUpdateRequestFlag) {
		this.cucmUpdateRequestFlag = cucmUpdateRequestFlag;
	}
	/**
	 * @return deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted セットする deleted
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}
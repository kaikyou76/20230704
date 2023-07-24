/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * VoiceLoggerAssociationEntity.java
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
 * VOICE_LOGGER_ASSOCIATIONテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class VoiceLoggerAssociationEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1420329070094099459L;

	private BigDecimal cucmLineId          = null;
	private String statusCode              = null;
	private String associateCode           = null;
	private String loggerData              = null;
	private String cucmUpdateRequestFlag   = null;
	private String deleted                 = null;

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
	 * @return loggerData
	 */
	public String getLoggerData() {
		return loggerData;
	}
	/**
	 * @param loggerData セットする loggerData
	 */
	public void setLoggerData(String loggerData) {
		this.loggerData = loggerData;
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


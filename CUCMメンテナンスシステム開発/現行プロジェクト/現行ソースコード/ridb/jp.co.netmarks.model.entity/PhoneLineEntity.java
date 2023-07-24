/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * PhoneLineEntity.java
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
 * R_CUCM_PHONE_LINEテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class PhoneLineEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 6728937238315351237L;

	private BigDecimal cucmPhoneId         = null;
	private BigDecimal cucmLineId          = null;
	private String index                   = null;
	private String externalPhoneNumberMask = null;
	private String dialin                  = null;
	private String lineTextLabel           = null;
	private String ringsettingName         = null;
	private String remarks                 = null;
	private String cucmUpdateRequestFlag   = null;
	private String deleted                 = null;
	private String errorFlg                = null;


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
	 * @return index
	 */
	public String getIndex() {
		return index;
	}
	/**
	 * @param index セットする index
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	/**
	 * @return externalPhoneNumberMask
	 */
	public String getExternalPhoneNumberMask() {
		return externalPhoneNumberMask;
	}
	/**
	 * @param externalPhoneNumberMask セットする externalPhoneNumberMask
	 */
	public void setExternalPhoneNumberMask(String externalPhoneNumberMask) {
		this.externalPhoneNumberMask = externalPhoneNumberMask;
	}
	/**
	 * @return dialin
	 */
	public String getDialin() {
		return dialin;
	}
	/**
	 * @param dialin セットする dialin
	 */
	public void setDialin(String dialin) {
		this.dialin = dialin;
	}
	/**
	 * @return lineTextLabel
	 */
	public String getLineTextLabel() {
		return lineTextLabel;
	}
	/**
	 * @param lineTextLabel セットする lineTextLabel
	 */
	public void setLineTextLabel(String lineTextLabel) {
		this.lineTextLabel = lineTextLabel;
	}
	/**
	 * @return ringsettingName
	 */
	public String getRingsettingName() {
		return ringsettingName;
	}
	/**
	 * @param ringsettingName セットする ringsettingName
	 */
	public void setRingsettingName(String ringsettingName) {
		this.ringsettingName = ringsettingName;
	}
	/**
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks セットする remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	/**
	 * @return errorFlg
	 */
	public String getErrorFlg() {
		return errorFlg;
	}
	/**
	 * @param errorFlg セットする errorFlg
	 */
	public void setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
	}

}
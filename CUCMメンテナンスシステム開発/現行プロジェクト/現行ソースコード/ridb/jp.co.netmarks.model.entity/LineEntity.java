/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LineEntity.java
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
 * CUCM_LINEテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class LineEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -8297276048545670251L;

	private BigDecimal cucmLineId        = null;
	private String directoryNumber       = null;
	private String fwdBusyDestination    = null;
	private String fwdNoansDestination   = null;
	private String fwdAllCss             = null;
	private String fwdBusyCss            = null;
	private String fwdNoansCss           = null;
	private String callPickupGroupName   = null;
	private String useVmFlg              = null;
	private String cucmUpdateRequestFlag = null;
	private String deleted               = null;
	private String errorFlg              = null;

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
	 * @return directoryNumber
	 */
	public String getDirectoryNumber() {
		return directoryNumber;
	}
	/**
	 * @param directoryNumber セットする directoryNumber
	 */
	public void setDirectoryNumber(String directoryNumber) {
		this.directoryNumber = directoryNumber;
	}
	/**
	 * @return fwdBusyDestination
	 */
	public String getFwdBusyDestination() {
		return fwdBusyDestination;
	}
	/**
	 * @param fwdBusyDestination セットする fwdBusyDestination
	 */
	public void setFwdBusyDestination(String fwdBusyDestination) {
		this.fwdBusyDestination = fwdBusyDestination;
	}
	/**
	 * @return fwdNoansDestination
	 */
	public String getFwdNoansDestination() {
		return fwdNoansDestination;
	}
	/**
	 * @param fwdNoansDestination セットする fwdNoansDestination
	 */
	public void setFwdNoansDestination(String fwdNoansDestination) {
		this.fwdNoansDestination = fwdNoansDestination;
	}
	/**
	 * @return fwdAllCss
	 */
	public String getFwdAllCss() {
		return fwdAllCss;
	}
	/**
	 * @param fwdAllCss セットする fwdAllCss
	 */
	public void setFwdAllCss(String fwdAllCss) {
		this.fwdAllCss = fwdAllCss;
	}
	/**
	 * @return fwdBusyCss
	 */
	public String getFwdBusyCss() {
		return fwdBusyCss;
	}
	/**
	 * @param fwdBusyCss セットする fwdBusyCss
	 */
	public void setFwdBusyCss(String fwdBusyCss) {
		this.fwdBusyCss = fwdBusyCss;
	}
	/**
	 * @return fwdNoansCss
	 */
	public String getFwdNoansCss() {
		return fwdNoansCss;
	}
	/**
	 * @param fwdNoansCss セットする fwdNoansCss
	 */
	public void setFwdNoansCss(String fwdNoansCss) {
		this.fwdNoansCss = fwdNoansCss;
	}
	/**
	 * @return callPickupGroupName
	 */
	public String getCallPickupGroupName() {
		return callPickupGroupName;
	}
	/**
	 * @param callPickupGroupName セットする callPickupGroupName
	 */
	public void setCallPickupGroupName(String callPickupGroupName) {
		this.callPickupGroupName = callPickupGroupName;
	}
	/**
	 * @return useVmFlg
	 */
	public String getUseVmFlg() {
		return useVmFlg;
	}
	/**
	 * @param useVmFlg セットする useVmFlg
	 */
	public void setUseVmFlg(String useVmFlg) {
		this.useVmFlg = useVmFlg;
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
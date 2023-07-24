/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddUserResultModel.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * ユーザーの検索と選択検索結果用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/30
 */
public class AddUserResultModel  implements Serializable{

	private static final long serialVersionUID = -5911784269439797380L;

	/* ユーザー名 */
	private BigDecimal userId          = null;
	/* ユーザー漢字名 */
	private String userKanjiName       = null;
	/* ユーザーカナ名 */
	private String userKanaName        = null;
	/* 会社ID */
	private String companyId           = null;
	/* 店部課ID */
	private String sectionId           = null;
	/* 店部課名 */
	private String sectionName         = null;
	/* ステータス */
	private String retainTelStatus     = null;
	/* ステータス名 */
	private String retainTelStatusName = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return userId
	 */
	public BigDecimal getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	/**
	 * @return userKanjiName
	 */
	public String getUserKanjiName() {
		return userKanjiName;
	}
	/**
	 * @param userKanjiName セットする userKanjiName
	 */
	public void setUserKanjiName(String userKanjiName) {
		this.userKanjiName = userKanjiName;
	}
	/**
	 * @return userKanaName
	 */
	public String getUserKanaName() {
		return userKanaName;
	}
	/**
	 * @param userKanaName セットする userKanaName
	 */
	public void setUserKanaName(String userKanaName) {
		this.userKanaName = userKanaName;
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
	/**
	 * @return retainTelStatus
	 */
	public String getRetainTelStatus() {
		return retainTelStatus;
	}
	/**
	 * @param retainTelStatus セットする retainTelStatus
	 */
	public void setRetainTelStatus(String retainTelStatus) {
		this.retainTelStatus = retainTelStatus;
	}
	/**
	 * @return retainTelStatusName
	 */
	public String getRetainTelStatusName() {
		return retainTelStatusName;
	}
	/**
	 * @param retainTelStatusName セットする retainTelStatusName
	 */
	public void setRetainTelStatusName(String retainTelStatusName) {
		this.retainTelStatusName = retainTelStatusName;
	}
}
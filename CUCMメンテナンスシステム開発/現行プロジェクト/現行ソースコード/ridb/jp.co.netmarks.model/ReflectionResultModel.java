/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionResultModel.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;


/**
 * <pre>
 * 個別反映－処理結果格納用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public class ReflectionResultModel implements Serializable{

	private static final long serialVersionUID = -7612081100494435296L;

	/* Phone */
	private String phoneResult         = null;

	/* Line */
	private String lineResult          = null;

	/* User */
	private String userResult          = null;

	/* PhoneLine */
	private String phoneLineResult     = null;
	/* UserPhone */
	private String userPhoneResult     = null;


	/**
	 * phoneResultを取得します。
	 * @return phoneResult
	 */
	public String getPhoneResult() {
		return phoneResult;
	}
	/**
	 * phoneResultを設定します。
	 * @param phoneResult phoneResult
	 */
	public void setPhoneResult(String phoneResult) {
		this.phoneResult = phoneResult;
	}
	/**
	 * userResultを取得します。
	 * @return userResult
	 */
	public String getUserResult() {
		return userResult;
	}
	/**
	 * userResultを設定します。
	 * @param userResult userResult
	 */
	public void setUserResult(String userResult) {
		this.userResult = userResult;
	}
	/**
	 * lineResultを取得します。
	 * @return lineResult
	 */
	public String getLineResult() {
		return lineResult;
	}
	/**
	 * lineResultを設定します。
	 * @param lineResult lineResult
	 */
	public void setLineResult(String lineResult) {
		this.lineResult = lineResult;
	}
	/**
	 * phoneLineResultを取得します。
	 * @return phoneLineResult
	 */
	public String getPhoneLineResult() {
		return phoneLineResult;
	}
	/**
	 * phoneLineResultを設定します。
	 * @param phoneLineResult phoneLineResult
	 */
	public void setPhoneLineResult(String phoneLineResult) {
		this.phoneLineResult = phoneLineResult;
	}
	/**
	 * userPhoneResultを取得します。
	 * @return userPhoneResult
	 */
	public String getUserPhoneResult() {
		return userPhoneResult;
	}
	/**
	 * userPhoneResultを設定します。
	 * @param userPhoneResult userPhoneResult
	 */
	public void setUserPhoneResult(String userPhoneResult) {
		this.userPhoneResult = userPhoneResult;
	}


}


/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ChangePasswordModel.java
 *
 * @date 2013/08/28
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;

/**
 * <pre>
 * パスワード変更用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/28 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/28
 */
public class ChangePasswordModel implements Serializable {

	private static final long serialVersionUID = 7176617373113191388L;

	/* 現在のパスワード */
	private String currentPassword = null;
	/* 新しいパスワード */
	private String newPassword     = null;
	/* 新しいパスワード(確認用) */
	private String newPasswordConf = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/


	/**
	 * @return currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * @param currentPassword セットする currentPassword
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	/**
	 * @return newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword セットする newPassword
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return newPasswordConf
	 */
	public String getNewPasswordConf() {
		return newPasswordConf;
	}
	/**
	 * @param newPasswordConf セットする newPasswordConf
	 */
	public void setNewPasswordConf(String newPasswordConf) {
		this.newPasswordConf = newPasswordConf;
	}



}
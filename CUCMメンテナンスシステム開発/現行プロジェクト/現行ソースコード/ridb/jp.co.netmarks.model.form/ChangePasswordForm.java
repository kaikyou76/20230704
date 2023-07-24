*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ChangePasswordForm.java
 *
 * @date 2013/08/28
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * パスワード変更画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/28 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/08/28
 */
public class ChangePasswordForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 4911921279759415981L;

	/* 現在のパスワード */
	@NotEmpty
	@Pattern(regexp="^([a-zA-Z0-9]+$)?$")
	@Size(min = 0, max = 50, message="最大桁数は{max}桁までです。")
	private String currentPassword = null;
	/* 新しいパスワード */
	@NotEmpty
	@Pattern(regexp="^([a-zA-Z0-9]+$)?$")
	@Size(min = 0, max = 50, message="最大桁数は{max}桁までです。")
	private String newPassword = null;
	/* 新しいパスワード(確認用) */
	@NotEmpty
	@Pattern(regexp="^([a-zA-Z0-9]+$)?$")
	@Size(min = 0, max = 50, message="最大桁数は{max}桁までです。")
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
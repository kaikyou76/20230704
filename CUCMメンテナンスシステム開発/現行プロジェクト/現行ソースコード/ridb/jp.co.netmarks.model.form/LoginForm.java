/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LoginForm.java
 *
 * @date 2013/08/12
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import jp.co.ksc.model.form.BaseForm;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <pre>
 * ログイン画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/12 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/12
 */
public class LoginForm extends BaseForm implements Serializable {
	private static final long serialVersionUID = 7503261073519961492L;

	/** username */
	@NotEmpty
	private String username;

	/** password */
	@NotEmpty
	private String password;

	/** 永続化 */
	private boolean rememberMe;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
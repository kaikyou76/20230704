/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthenticationToken.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <pre>
 * AuthenticationTokenクラス
 * ・spring security AuthenticationTokenクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public class AuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = -9103882523515743276L;

	/* principal */
	private final Object principal;

	/* credentials */
	private Object credentials;

	/* userの属性情報 */
	private UserDetails details;

	/**
	 * コンストラクタ
	 * @param principal principal
	 * @param details userの属性情報
	 */
	public AuthenticationToken(String principal, UserDetails details) {
		super(details.getAuthorities());
		this.principal = principal;
		this.credentials = null;
		this.details = details;
		super.setAuthenticated(true);
	}

/*********************************************************************
 * generated getter
**********************************************************************/

	public Object getCredentials() {
		return credentials;
	}
	public Object getPrincipal() {
		return principal;
	}
	public Object getDetails() {
		return details;
	}
}
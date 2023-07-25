/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TokenManager.java
 *
 * @date 2013/08/15
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring.security;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * <pre>
 * ワンタイムトークン管理クラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/15 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/09/26
 */
public class TokenManager {
	private static Log log = LogFactory.getLog(TokenManager.class);

	/**
	 * ワンタイムトークン名
	 */
	public final static String DEFAULT_TOKEN_NAME =  "_RequestVerificationToken";

	/**
	 * ワンタイムトークンValidate結果名
	 */
	private final static String TOKEN_VALIDATE_RESULT =  "_TokenValidateResult";

	/**
	 * ワンタイムトークンを保持する
	 *
	 * @param request HttpServletRequest
	 */
	public static void save(HttpServletRequest request) {
		save(request, DEFAULT_TOKEN_NAME);
	}

	/**
	 * ワンタイムトークンを保持する
	 *
	 * @param request HttpServletRequest
	 * @param tokenName トークン名
	 */
	public static void save(HttpServletRequest request, String tokenName) {
		String token = UUID.randomUUID().toString();
		request.getSession().setAttribute(tokenName, token);
	}

	/**
	 * ワンタイムトークンを保持する
	 *
	 * @param request HttpServletRequest
	 * @param mav ModelAndView 
	 * @param tokenName トークン名
	 */
	public static void save(HttpServletRequest request, ModelAndView mav, String tokenName) {
		String token = UUID.randomUUID().toString();

		request.getSession().setAttribute(tokenName, token);
		MappingJacksonJsonView view = (MappingJacksonJsonView) mav.getView();
		view.getAttributesMap().put(tokenName, token);
	}

	/**
	 * ワンタイムトークンが一致するかどうか
	 *
	 * @param request HttpServletRequest
	 * @param tokenName トークン名
	 * @param removeToken True:トークン削除する False:トークン削除しない
	 * @return True:一致する False:一致しない
	 */
	public static boolean validate(HttpServletRequest request, String tokenName, boolean removeToken) {
		String token1 = (String) request.getSession().getAttribute(tokenName);
		String token2 = request.getParameter(tokenName);

		log.debug("token1 : " + token1 + " token2 : " + token2);
		boolean result = (token1 != null) ? StringUtils.equals(token1, token2) : false;

		/* 結果を保持する */
		request.setAttribute(TOKEN_VALIDATE_RESULT, result);

		/* トークンを削除 */
		if (removeToken) {
			request.getSession().removeAttribute(tokenName);
		}
		return result;
	}

	/**
	 * トークンチェック
	 * 
	 * @param request HttpServletRequest
	 * @return True:エラー無し False:エラー有り
	 */
	public static boolean isValidToken(HttpServletRequest request) {
		Object result = request.getAttribute(TokenManager.TOKEN_VALIDATE_RESULT);
		return (result == null) ? true :Boolean.parseBoolean(result.toString()) ;
	}

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * SecurityHandlerInterceptor.java
 *
 * @date 2013/08/15
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * <pre>
 * セキュリティのハンドラークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/15 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/09/26
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

	/* (非 Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) return true;
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		/* クリックジャッキング対応 */
		response.setHeader("X-FRAME-OPTION", "SAMEORIGN");

		/* ワンタイムトークンをValidateする */
		TokenValidateType type = doValidateToken(handlerMethod);
		if ( !type.equals(TokenValidateType.NONE)) {

			/* ワンタイムトークンのValidate */
			boolean removeToken = type.equals(TokenValidateType.REMOVE) ? true : false;
			if ( !TokenManager.validate(request, getTokenName(handlerMethod), removeToken)) {

				/* エラーを自動でハンドリングする */
				if (!handleError(handlerMethod)) {
					/* 400 */
				    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad or Missing onetime token");
				    return false;
				}
			}
		}
		return true;
	}

	/* (非 Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mav) throws Exception {
		if (!(handler instanceof HandlerMethod)) return;
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		/* トークン名を取得 */
		String tokenName = getTokenName(handlerMethod);

		/* ワンタイムトークンを保存する */
		if (doSaveToken(handlerMethod)) {

			if (mav.getView() instanceof MappingJacksonJsonView) {
				/* jsonの場合 */
				TokenManager.save(request, mav, tokenName);
			} else {
				/* 通常レスポンスの場合 */
				TokenManager.save(request, tokenName);
			}
			return;
		}

		/**
		 *  トークンのValidateを行い、トークンを削除したが、
		 *  フォームデータのValidateエラーがある為、トークンを保存する
		 */
		if (doValidateToken(handlerMethod).equals(TokenValidateType.REMOVE)) {

			/* フォームデータのValidateにエラーがある */
			if (hasFormErrors(mav)) {
				if (mav.getView() instanceof MappingJacksonJsonView) {
					/* jsonの場合 */
					TokenManager.save(request, mav, tokenName);
				} else {
					/* 通常レスポンスの場合 */
					TokenManager.save(request, tokenName);
				}
			}
		}
	}

	/**
	 * ワンタイムトークン名を取得する
	 *
	 * @param handlerMethod handlerMethod
	 * @return トークン名
	 */
	private String getTokenName(HandlerMethod handlerMethod) {
		TokenHandler annotation = handlerMethod.getMethodAnnotation(TokenHandler.class);
	    if (annotation == null || StringUtils.isEmpty(annotation.name())) {
		    return TokenManager.DEFAULT_TOKEN_NAME;
	    }
	    return  annotation.name();
	}

	/**
	 * ワンタイムトークンを保存するかどうか
	 *
	 * @param handlerMethod handlerMethod
	 * @return True:保存する False:保存しない
	 */
	private boolean doSaveToken(HandlerMethod handlerMethod) {
		TokenHandler annotation = handlerMethod.getMethodAnnotation(TokenHandler.class);
	    if (annotation == null)  return false;
        return annotation.save();
	}

	/**
	 * ワンタイムトークンをValidateするかどうか
	 *
	 * @param handlerMethod handlerMethod
	 * @return True:Validateする False:Validateしない
	 */
	private TokenValidateType doValidateToken(HandlerMethod handlerMethod) {
		TokenHandler annotation = handlerMethod.getMethodAnnotation(TokenHandler.class);
	    if (annotation == null)  return TokenValidateType.NONE;
        return annotation.validate();
	}

	/**
	 * ワンタイムトークンのValidateエラーをハンドリングするかどうか
	 *
	 * @param handlerMethod handlerMethod
	 * @return True:ハンドリングする False:ハンドリングしない
	 */
	private boolean handleError(HandlerMethod handlerMethod) {
		TokenHandler annotation = handlerMethod.getMethodAnnotation(TokenHandler.class);
	    if (annotation == null)  return false;
        return annotation.handleError();
	}

	/**
	 * フォームデータのValidationにエラーがあるかどうか
	 *
	 * @param mav ModelAndView
	 * @return True:エラーあり  False:エラーなし
	 */
	private boolean hasFormErrors(ModelAndView mav) {
		String key = BindingResult.MODEL_KEY_PREFIX;

		for (Map.Entry<String, Object> e: mav.getModel().entrySet()) {
			if (e.getKey().startsWith(key)){
				return ((BindingResult) e.getValue()).hasErrors();
			}
		}
		return false;
	}
}
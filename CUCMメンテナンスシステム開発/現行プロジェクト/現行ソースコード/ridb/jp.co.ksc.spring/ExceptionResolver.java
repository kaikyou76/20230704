/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ExceptionResolver.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * ControllerからthrowされたExceptionをハンドリングするクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public class ExceptionResolver implements HandlerExceptionResolver {
	private static Log log = LogFactory.getLog(ExceptionResolver.class);

	/* (非 Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception ex) {

		/* ログ出力 */
		log.error(ex.getMessage(), ex);

		/* http status code 500をセット */
		response.setStatus(500);

		/* エラー画面へ遷移 */
		return new ModelAndView("error/500");
    }
}
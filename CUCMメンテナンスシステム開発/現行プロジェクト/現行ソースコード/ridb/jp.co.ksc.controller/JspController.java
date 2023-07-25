/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * JspController.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <pre>
 * JSP表示コントローラ
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
@Controller
@RequestMapping("/jsp")
public class JspController {

	@RequestMapping(value="/error", method=RequestMethod.GET)
	protected String error() throws Exception {
		return "/error/500";
	}

	@RequestMapping(value="/badRequest", method=RequestMethod.GET)
	protected String badRequest() throws Exception {
		return "/error/400";
	}
}
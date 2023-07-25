/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LoggingInterceptor.java
 *
 * @date 2013/10/08
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.ksc.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * <pre>
 * 処理前後ログ出力処理クラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/08 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/08
 */
@Aspect
public class LoggingInterceptor {
	
	private final Log log = LogFactory.getLog(LoggingInterceptor.class);
	
	 //メソッドの実行前に出力するログ
	@Before("execution(* jp.co.netmarks.controller.*.*(..))")
	public void doLog(JoinPoint jp) {
		log.info("【処理開始】 [Class] ： " + jp.getTarget().getClass().getName() + 
				             " [Method] ： " + jp.getSignature().getName());
	}
	 
	 //メソッドの実行後に出力するログ
	 @After("execution(* jp.co.netmarks.controller.*.*(..))")
	 public void doSysout(JoinPoint jp) {
		log.info("【処理終了】 [Class] ： " + jp.getTarget().getClass().getName() + 
				             " [Method] ： " + jp.getSignature().getName());
	 }
}
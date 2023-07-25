/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TokenHandler.java
 *
 * @date 2013/08/15
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * ワンタイムトークン制御アノテーション
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/15 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/09/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenHandler {

	/* トークンを生成する */
    boolean save() default false;

	/* トークンをValidateする */
    TokenValidateType validate() default TokenValidateType.NONE;

	/* トークンの名前を指定する */
    String name() default StringUtils.EMPTY;

	/* エラーのハンドリングを実装する */
    boolean handleError() default false;

}
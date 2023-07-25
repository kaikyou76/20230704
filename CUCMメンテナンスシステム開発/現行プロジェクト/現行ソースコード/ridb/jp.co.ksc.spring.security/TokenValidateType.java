/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TokenValidateType.java
 *
 * @date 2013/08/15
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring.security;


/**
 * <pre>
 * ワンタイムトークンのValidateタイプ
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/15 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/09/26
 */
public enum TokenValidateType {
	NONE,		// validateしない
	REMOVE,		// validate実施 & トークンを削除する
	KEEP		// validate実施 & トークンを削除しない
}
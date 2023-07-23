/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ExcludeException.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.exception;

import java.util.Map;

/**
 * <pre>
 * 排他Excptionクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public class ExcludeException extends Exception {
	private static final long serialVersionUID = 9214760928847113434L;

	private Map<String, Object> criteria;

	public ExcludeException(Map<String, Object> criteria) {
		this.criteria = criteria;
	}

	public Object getCriteria(String keyName) {
		return criteria.get(keyName);
	}
}
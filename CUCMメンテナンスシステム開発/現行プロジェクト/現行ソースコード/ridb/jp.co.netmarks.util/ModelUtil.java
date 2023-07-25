/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ModelUtil.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Yomomichi Iwasawa
 */
package jp.co.netmarks.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jp.co.netmarks.common.Constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * モデルユーティリティクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2009/09/18 KSC Yomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Yomomichi Iwasawa
 * @version 1.0 2013/08/30
 */
public class ModelUtil {
	private static Log log = LogFactory.getLog(ModelUtil.class);

	private static Map<String, Object> constants = new HashMap<String, Object>();
	static {
		try {
			/* ConstantsのフィールドをMapにセットする */
			Object instance = Class.forName("jp.co.netmarks.common.Constants").newInstance();
			for (Field field : Constants.class.getFields()) {
				constants.put(field.getName(), field.get(instance));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Constansの定数がセットされたMapを返却する
	 *
	 * @return Constants
	 */
	public static Map<String, Object> getConstants() {
		return constants;
	}
}
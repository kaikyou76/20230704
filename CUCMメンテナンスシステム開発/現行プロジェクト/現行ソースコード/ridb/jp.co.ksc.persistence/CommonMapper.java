/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CommonMapper.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.persistence;

import java.util.Map;

/**
 * <pre>
 * 共通データマッパークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public interface CommonMapper {

	/**
	 * Lock取得
	 * @param params Map
	 * @return int
	 */
	Boolean locked(Map<String, Object> params);

	/**
	 * シーケンスを取得
	 * @param sequenceName シーケンス名
	 * @return シーケンス値
	 */
	String sequence(String sequenceName);
}
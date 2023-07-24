/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditMapper.java
 *
 * @date 2013/09/02
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.persistence;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <pre>
 * 電話機一括登録画面用データマッパーインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/02 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/02
 */
public interface TelLumpEditMapper {

	/**
	 * 電話機IDを取得する
	 *
	 * @param macAddress MACアドレス
	 * @return 電話機ID
	 */
	BigDecimal getTelId(String macAddress);

	/**
	 * 親店部課コードを取得する
	 *
	 * @param map 検索条件
	 * @return 親店部課コード
	 */
	String getParentSectionId(Map<String,Object> map);

	/**
	 * ラインIDと削除フラグを取得する
	 *
	 * @param directoryNumber 内線番号
	 * @return ラインIDと削除フラグ
	 */
	Map<String, Object> getLineIdDelete(String directoryNumber);

	/**
	 * MACアドレス存在チェック
	 *
	 * @param macAddress MACアドレス
	 * @return 件数
	 */
	int macAddressExistCheck(String macAddress);

}

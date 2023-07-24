/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingMapper.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.util.List;
import java.util.Map;

import jp.co.netmarks.model.BusyDestinationSettingModel;
import jp.co.netmarks.model.BusyDestinationSettingResultModel;

/**
 * <pre>
 * 話中転送的設定画面用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/18
 */
public interface BusyDestinationSettingMapper {

	/**
	 * VM仕様フラグを取得
	 * ※検索条件は話中転送先と内線番号
	 *
	 * @param params 話中転送先と削除フラグ
	 * @return VM仕様
	 */
	Map<String, Object> getLineInfo(Map<String, String> params);

	/**
	 * 話中転送先の設定情報取得
	 *
	 * @param params 条件(話中転送)
	 * @return 設定情報取得
	 */
	List<BusyDestinationSettingResultModel> getBusyDestinationInfo(BusyDestinationSettingModel params);
}
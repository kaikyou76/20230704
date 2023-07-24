/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelMapper.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.util.List;

import jp.co.netmarks.model.AddTelModel;
import jp.co.netmarks.model.AddTelResultModel;

/**
 * <pre>
 * 電話機の検索と選択画面用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
public interface AddTelMapper {

	/**
	 * 電話機追加一覧件数取得
	 *
	 * @param params 検索条件
	 * @return 件数
	 */
	int getTelAddTotal(AddTelModel params);


	/**
	 * 電話機追加一覧取得
	 *
	 * @param params 検索条件
	 * @return 件数
	 */
	List<AddTelResultModel> getTelAddList(AddTelModel params);
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineMapper.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.util.List;

import jp.co.netmarks.model.AddLineModel;
import jp.co.netmarks.model.AddLineResultModel;

/**
 * <pre>
 * ラインの検索と選択画面用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
public interface AddLineMapper {

	/**
	 * ライン追加件数取得
	 * @param params 検索条件
	 * @return 件数
	 */
	int getLineAddTotal(AddLineModel params);

	/**
	 * ライン追加検索結果取得
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<AddLineResultModel> getLineAddList(AddLineModel params);


}
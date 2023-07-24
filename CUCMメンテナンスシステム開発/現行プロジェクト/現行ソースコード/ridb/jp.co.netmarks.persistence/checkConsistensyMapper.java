/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * checkConsistensyMapper.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.persistence;

import java.util.List;

import jp.co.netmarks.model.CheckConsistencyResultModel;
import jp.co.netmarks.model.CheckConsistencySearchModel;

/**
 * <pre>
 * CUCM整合性チェック画面用データマッパーインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/12
 */
public interface CheckConsistencyMapper {

	/**
	 * CUCM情報取得日時を取得
	 *
	 * @return CUCM情報取得日時
	 */
	String getCucmMasterUpdateTime();


	/**
	 * CUCM差分の検索結果件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果件数
	 */
	int getInconsistencyCucmListTotal(CheckConsistencySearchModel params);

	/**
	 * CUCM差分の検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<CheckConsistencyResultModel> getInconsistencyCucmList(CheckConsistencySearchModel params);
	
	/**
	 * オフィスリンク差分の検索結果件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果件数
	 */
	int getInconsistencyOfficeLinkListTotal(CheckConsistencySearchModel params);

	/**
	 * オフィスリンク差分の検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<CheckConsistencyResultModel> getInconsistencyOfficeLinkList(CheckConsistencySearchModel params);

}
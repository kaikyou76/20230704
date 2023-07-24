/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LineMapper.java
 *
 * @date 2013/08/27
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.util.Map;

import jp.co.netmarks.model.entity.LineEntity;

/**
 * <pre>
 * Lineテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/27 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/27
 */
public interface LineMapper {

	/**
	 * Line作成処理
	 *
	 * @param params 登録情報
	 * @return 登録件数
	 */
	int insertLine(LineEntity params);

	/**
	 * Line更新処理
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineReflect(LineEntity params);

	/**
	 * Line更新処理（VmFlgの更新）
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineVmFlg(LineEntity params);

	/**
	 * Line更新処理（話中転送先）
	 * 
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateBusyDestination(LineEntity params);

	/**
	 * Lineの削除更新処理（Updateフラグ更新あり）
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineDelete(LineEntity params);
	
	/**
	 * Lineの削除更新処理（Updateフラグ更新あり）
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineDeleteOnly(LineEntity params);

	
	/**
	 * 変更した電話機に該当するCSS項目の更新(条件：電話ID)
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineCssPhone(Map<String, Object> params);

	/**
	 * 変更した電話機に該当するCSS項目の更新(条件：ラインID)
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineCssLine(Map<String, Object> params);

	/**
	 * CUCM取込処理
	 *
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateLineFetchCucm(LineEntity params);

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceMapper.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.persistence;

import java.util.List;
import java.util.Map;

import jp.co.netmarks.model.MaintenanceResultModel;
import jp.co.netmarks.model.MaintenanceSearchModel;

/**
 * <pre>
 * メンテナンス画面用データマッパーインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public interface MaintenanceMapper {

	/**
	 * 検索件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	int getBranchTotal(MaintenanceSearchModel params);

	/**
	 * 検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<MaintenanceResultModel> getBranchList(MaintenanceSearchModel params);

	/**
	 * CSVデータ取込
	 *
	 * @param params テーブル名とCSVファイルパス
	 * @return 取込件数
	 */
	int copyCsvData(Map<String, String> params);

	/**
	 * テーブルデータ削除
	 *
	 * @param params テーブル名
	 * @return 削除件数
	 */
	int deleteTableData(Map<String, String> params);

	/**
	 * 拠点と店部課の紐付き存在チェック(V_ORGANIZATION_LEVEL)
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int organizationLevelExistCheck(Map<String, Object> params);

	/**
	 * 電話機紐付き存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int associatePhoneExistCheck(Map<String, Object> params);

}
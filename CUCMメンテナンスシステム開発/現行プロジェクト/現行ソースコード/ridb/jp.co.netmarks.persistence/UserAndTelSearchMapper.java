/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelSearchMapper.java
 *
 * @date 2013/08/13
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.netmarks.model.UserAndTelSearchModel;
import jp.co.netmarks.model.UserAndTelUpdateModel;

/**
 * <pre>
 * ユーザーと電話機の一覧画面用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/13 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/13
 */
public interface UserAndTelSearchMapper {

	/**
	 * 検索件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	int getUserAndTelTotal(UserAndTelSearchModel params);

	/**
	 * 検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<UserAndTelSearchModel> getUserAndTelList(UserAndTelSearchModel params);

	/**
	 * 検索結果を取得（CSV）
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<LinkedHashMap<String, String>> getUserAndTelCsvList(UserAndTelSearchModel params);

	/**
	 * 電話機とLINEの紐付き情報Eの削除フラグを取得する
	 *
	 * @param params 取得条件
	 * @return 削除フラグ
	 */
	String getTelLineDeleteFlg(UserAndTelUpdateModel params);

	/**
	 * LINEの削除フラグを取得する
	 *
	 * @param params 取得条件
	 * @return ラインの削除フラグ
	 */
	Map<String, Object> getLineDeleteFlg(UserAndTelUpdateModel params);

	/**
	 * 店部課情報を取得する
	 * ※電話の店部課から取得する
	 *
	 * @param telId 電話ID
	 * @return 店部課情報
	 */
	Map<String, Object> getSharedUserSectionInfo(BigDecimal telId);

	/**
	 * ユーザーテーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return ユーザーテーブル結果
	 */
	List<LinkedHashMap<String, String>> getUserInfoCsv(UserAndTelSearchModel params);

	/**
	 * ユーザー電話テーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return ユーザー電話テーブル結果
	 */
	List<LinkedHashMap<String, String>> getUserPhoneInfoCsv(UserAndTelSearchModel params);

	/**
	 * 電話テーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return 電話テーブル結果
	 */
	List<LinkedHashMap<String, String>> getPhoneInfoCsv(UserAndTelSearchModel params);

	/**
	 * ラインテーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return ラインテーブル結果
	 */
	List<LinkedHashMap<String, String>> getLineInfoCsv(UserAndTelSearchModel params);

	/**
	 * 電話ラインテーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return 電話ラインテーブル結果
	 */
	List<LinkedHashMap<String, String>> getPhoneLineInfoCsv(UserAndTelSearchModel params);

	/**
	 * 課金関連テーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return 課金関連テーブル結果
	 */
	List<LinkedHashMap<String, String>> getChargeAssociationInfoCsv(UserAndTelSearchModel params);

	/**
	 * 通録関連テーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return 通録関連テーブル結果
	 */
	List<LinkedHashMap<String, String>> getVoiceLoggerAssociationInfoCsv(UserAndTelSearchModel params);

	/**
	 * Unity関連テーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return Unity関連テーブル結果
	 */
	List<LinkedHashMap<String, String>> getUnityAssociationInfoCsv(UserAndTelSearchModel params);

	/**
	 * 電子電話帳関連テーブル結果取得（CSV）
	 *
	 * @param params 検索条件
	 * @return 電子電話帳関連テーブル結果
	 */
	List<LinkedHashMap<String, String>> getTelDirAssociationInfoCsv(UserAndTelSearchModel params);
}
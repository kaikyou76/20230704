/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * IndividualPhoneMapper.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.persistence;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 個別反映－電話用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public interface IndividualPhoneMapper {

	// 電話機関係　---------------------------------------------------------------------------
	/**
	 * 電話機詳細取得
	 * @param parameterValues 対象条件
	 * @return 対象情報リスト
	 */
	List<Map<String, Object>> selectPhoneInfo(Map<String, Object> parameterValues);

	/**
	 * 対象(電話機)に紐付くラインの取得
	 * @param parameterValues 対象条件
	 * @return ライン情報リスト
	 */
	List<Map<String, Object>> selectPhoneToLine(Map<String,Object> parameterValues);

	/**
	 * 対象(電話機)に紐付くUserの取得
	 * @param parameterValues 対象条件
	 * @return ユーザ情報リスト
	 */
	List<Map<String, Object>> selectPhoneToUser(Map<String,Object> parameterValues);

	/**
	 * 対象(User)に紐付く電話機の取得
	 * @param parameterValues 対象条件
	 * @return 電話情報リスト
	 */
	List<Map<String, Object>> selectUserToPhone(Map<String,Object> parameterValues);

	/**
	 * 対象が更新時、クラスタ跨ぎの判定
	 * @param parameterValues 対象条件
	 * @return 新旧クラスタリスト
	 */
	List<Map<String, Object>> selectClusterPhonejudge(Map<String, Object> parameterValues);

	/**
	 * 電話機クラスタ追加
	 * @param parameterValues 登録パラメータ
	 * @return 登録件数
	 */
	int insertPhoneCluster(Map<String, Object> parameterValues);

	/**
	 * 電話機クラスタ削除
	 * @param parameterValues 削除条件
	 * @return 削除件数
	 */
	int deletePhoneCluster(Map<String, Object> parameterValues);

	/**
	 * 電話機情報関連削除
	 * CUCM_PHONE,UNITY_ASSOCIATION,TEL_DIR_ASSOCIATION,R_CUCM_USER_PHONE,R_CUCM_PHONE_LINE
	 * @param parameterValues 削除条件
	 * @return 削除件数
	 */
	int deletePhoneInfo(Map<String, Object> parameterValues);

	/**
	 * 追加処理成功時更新
	 * @param parameterValues 更新パラメータ
	 * @return 更新件数
	 */
	int addSuccessUpdPhone(Map<String, Object> parameterValues);

	/**
	 * ERROR FLG更新
	 * @param parameterValues 更新パラメータ
	 * @return 更新件数
	 */
	int updateErrorPhone(Map<String, Object> parameterValues);

}
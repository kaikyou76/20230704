/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * IndividualUserMapper.java
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
 * 個別反映－ユーザー用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public interface IndividualUserMapper {

	// User関係　---------------------------------------------------------------------------
	/**
	 * ユーザー詳細取得
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selectUserInfo(Map<String, Object> parameterValues);

	/**
	 * 全クラスタのIDを取得
	 * @param parameterValues パラメータ
	 * @return List<String> 取得結果リスト
	 */
	List<String> selectAllClusterId(Map<String, Object> parameterValues);

	/**
	 * ユーザーが持っている電話機のうち一番IDの小さい電話機の１ライン目を取得（クラスタ毎）
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selUserTelNoByCluster(Map<String, Object> parameterValues);

	/**
	 * 削除ユーザーに紐付いている電話
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selectDelUserUsePhone(Map<String, Object> parameterValues);

	/**
	 * ユーザ情報関連削除
	 * TEL_DIR_ASSOCIATION,R_CUCM_USER_PHONE,R_USER_SECTION
	 *
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int deleteUserInfo(Map<String, Object> parameterValues);

	/**
	 * 追加処理成功時更新
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int addSuccessUpdUser(Map<String, Object> parameterValues);


	// Userと電話機紐付　関係　---------------------------------------------------------------------------
	/**
	 * ユーザーと電話機の紐付詳細取得（ユーザー指定）
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selectUserPhoneInfo(Map<String, Object> parameterValues);

	/**
	 * ユーザーと電話機の紐付詳細取得（ユーザーと電話機を指定）
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selectUserAndPhoneInfo(Map<String, Object> parameterValues);

	/**
	 * 紐付切れユーザ取得
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Integer> selDelPhoneUserList(Map<String, Object> parameterValues);

	/**
	 * ユーザーと電話機の紐付テーブル削除
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int delRCucmUserPhone(Map<String, Object> parameterValues);

	/**
	 * ユーザーと電話機の紐付テーブル更新
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int updRCucmUserPhone(Map<String, Object> parameterValues);

	/**
	 * 電子電話帳テーブル削除
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int deleteTelDir(Map<String, Object> parameterValues);

	// ERROR FLG
	/**
	 * ユーザーテーブルへエラーフラグを立てる
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int updateErrorUser(Map<String, Object> parameterValues);

	/**
	 * ユーザーと電話機の紐付テーブルへエラーフラグを立てる
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int updateErrorUserPhone(Map<String, Object> parameterValues);

}
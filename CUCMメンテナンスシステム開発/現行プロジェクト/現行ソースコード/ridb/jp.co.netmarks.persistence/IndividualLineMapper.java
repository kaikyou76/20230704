/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * IndividualLineMapper.java
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
 * 個別反映－ライン用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public interface IndividualLineMapper {

	// Line関係　---------------------------------------------------------------------------
	/**
	 * ライン詳細取得
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selectLineInfo(Map<String, Object> parameterValues);

	/**
	 * ライン登録
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int insertLineInfo(Map<String, Object> parameterValues);

	/**
	 * Line情報関連削除
	 * TEL_DIR_ASSOCIATION
	 * CHARGE_ASSOCIATION
	 * VOICE_LOGGER_ASSOCIATION
	 * CUCM_LINE
	 *
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int deleteLineInfo(Map<String, Object> parameterValues);

	/**
	 * 追加処理成功時更新
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int addSuccessUpdLine(Map<String, Object> parameterValues);

	/**
	 * Lineクラスタ追加
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int insertLineCluster(Map<String, Object> parameterValues);

	/**
	 * Lineクラスタ削除
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int deleteLineCluster(Map<String, Object> parameterValues);

	// Lineと電話機紐付　関係　---------------------------------------------------------------------------
	/**
	 * 電話機とライン紐付詳細取得
	 * @param parameterValues パラメータ
	 * @return List<Map> 取得結果リスト
	 */
	List<Map<String, Object>> selectPhoneLineInfo(Map<String, Object> parameterValues);

	/**
	 * 電子電話帳テーブル削除
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int deleteTelDir(Map<String, Object> parameterValues);
	/**
	 * 電話機とラインの紐付テーブル削除
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int delRCucmPhoneLine(Map<String, Object> parameterValues);
	/**
	 * 電話機とラインの紐付テーブル更新
	 * @param parameterValues パラメータ
	 * @return int 件数
	 */
	int updRCucmPhoneLine(Map<String, Object> parameterValues);


	/**
	 * ライン　テーブルのエラーフラグを立てる
	 * @param parameterValues パラメータ
	 * @return int Update件数
	 */
	int updateErrorLine(Map<String, Object> parameterValues);
	/**
	 * 電話機とライン　テーブルのエラーフラグを立てる
	 * @param parameterValues パラメータ
	 * @return int Update件数
	 */
	int updateErrorPhoneLine(Map<String, Object> parameterValues);

}
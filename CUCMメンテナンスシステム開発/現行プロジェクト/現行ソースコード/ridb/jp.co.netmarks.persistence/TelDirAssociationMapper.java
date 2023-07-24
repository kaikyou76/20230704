/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelDirAssociationMapper.java
 *
 * @date 2013/08/28
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.TelDirAssociationEntity;

/**
 * <pre>
 * TEL_DIR_ASSOCIATIONテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/28 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/28
 */
public interface TelDirAssociationMapper {

	/**
	 * 登録処理
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertTelDirData(TelDirAssociationEntity params);

	/**
	 * 登録処理
	 * ※電話機に該当するLine分作成します。
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertTelDirDataTelLine(TelDirAssociationEntity params);

	/**
	 * 登録処理
	 * ※電話機に該当するUser分作成します。
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertTelDirDataTelUser(TelDirAssociationEntity params);
	
	/**
	 * 登録処理
	 * ※電話機に該当する全ての情報を登録します
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertTelDirDataTelAll(TelDirAssociationEntity params);

	/**
	 * 更新処理
	 * ※TEL_DIR_DATAの更新を行います。
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateTelDirData(TelDirAssociationEntity params);

	/**
	 * 更新処理
	 * ※削除フラグの更新のみ行います
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateTelDirDataDelete(TelDirAssociationEntity params);

	/**
	 * 更新処理
	 * ※削除フラグが立ってるデータのみ更新を行います。（条件は電話とユーザーのみ）
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateTelDirDataUserTelDelete(TelDirAssociationEntity params);

	/**
	 * 更新処理
	 * ※削除フラグが立ってるデータのみ更新を行います。（条件は電話とラインのみ）
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateTelDirDataTelLineDelete(TelDirAssociationEntity params);
	
	/**
	 * 更新処理
	 * ※削除予約フラグが立ってるユーザーに対して削除フラグを設定します
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateTelDirDataReserveDelete(TelDirAssociationEntity params);

	/**
	 * 削除処理
	 *
	 * @param params 削除条件
	 * @return 削除件数
	 */
	int deleteTelDirData(TelDirAssociationEntity params);
}
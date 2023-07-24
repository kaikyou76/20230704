/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserMapper.java
 *
 * @date 2013/08/22
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.UserEntity;

/**
 * <pre>
 * Userテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/22
 */
public interface UserMapper {

	/**
	 * ユーザーテーブル登録
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertAppUser(UserEntity params);

	/**
	 * ユーザーテーブルの電話番号を更新する
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateTelephoneNumber(UserEntity params);

	/**
	 * ユーザーテーブルのログインパスワードを更新する
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateloginPassword(UserEntity params);

	/**
	 * ユーザーテーブルの権限を更新する
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateUserRole(UserEntity params);

	/**
	 * ユーザー名を更新する
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updateUserKanjiName(UserEntity params);

	/**
	 * ユーザーテーブル削除
	 * @param params 削除条件
	 * @return 更新件数
	 */
	int deleteAppUser(UserEntity params);

}


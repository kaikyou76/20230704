/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserPhoneMapper.java
 *
 * @date 2013/08/23
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.UserPhoneEntity;

/**
 * <pre>
 * R_CUCM_USER_PHONEテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/23 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/23
 */
public interface UserPhoneMapper {


	/**
	 * ユーザー電話機情報を登録します。
	 *
	 * @param params 登録データ
	 * @return 件数
	 */
	int insertUserPhone(UserPhoneEntity params);

	/**
	 * ユーザー電話機情報を更新します。
	 *
	 * @param params 更新データ
	 * @return 件数
	 */
	int updateUserPhone(UserPhoneEntity params);

	/**
	 * 論理削除フラグをセットします。
	 *
	 * @param params 更新データ
	 * @return 件数
	 */
	int updateUserPhoneDelete(UserPhoneEntity params);

	/**
	 * 会社、店部課を更新します。
	 *
	 * @param params 更新データ
	 * @return 件数
	 */
	int updateUserPhoneSection(UserPhoneEntity params);

	/**
	 * 削除予約の項目を削除フラグを立てます。
	 *
	 * @param params 更新データ
	 * @return 件数
	 */
	int updateUserPhoneDeleteReserve(UserPhoneEntity params);

	/**
	 * ユーザー電話機情報を削除します。
	 *
	 * @param params 削除条件
	 * @return 件数
	 */
	int deleteUserPhone(UserPhoneEntity params);

}
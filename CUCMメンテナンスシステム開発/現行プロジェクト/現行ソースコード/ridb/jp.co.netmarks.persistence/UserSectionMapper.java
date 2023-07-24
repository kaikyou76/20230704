/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserSectionMapper.java
 *
 * @date 2013/08/23
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.UserSectionEntity;

/**
 * <pre>
 * R_USER_SECTIONテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/23 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/23
 */
public interface UserSectionMapper {

	/**
	 * 登録処理
	 *
	 * @param entity 登録パラメータ
	 * @return 登録件数
	 */
	int insertUserSection(UserSectionEntity entity);

	/**
	 * 更新処理
	 * ※ユーザーの所属部署の予約削除フラグが立っている項目に削除フラグを立てる
	 *
	 * @param entity 更新パラメータ
	 * @return 更新件数
	 */
	int updateUserSectionDeleteReserve(UserSectionEntity entity);

	/**
	 * 削除処理
	 *
	 * @param entity 削除条件
	 * @return 削除件数
	 */
	int deleteUserSection(UserSectionEntity entity);

}


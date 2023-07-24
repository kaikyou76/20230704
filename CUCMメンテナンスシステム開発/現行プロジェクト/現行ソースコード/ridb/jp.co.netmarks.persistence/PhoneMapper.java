/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * PhoneMapper.java
 *
 * @date 2013/08/27
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.util.Map;

import jp.co.netmarks.model.entity.PhoneEntity;

/**
 * <pre>
 * CUCM_PHONEテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/27 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/27
 */
public interface PhoneMapper {

	/**
	 * 登録処理
	 *
	 * @param params 登録パラメータ
	 * @return 件数
	 */
	int insertPhone(PhoneEntity params);

	/**
	 * 更新処理（個別反映、翌日反映）
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updatePhoneReflect(PhoneEntity params);

	/**
	 * 削除フラグ更新処理
	 *
	 * @param params 更新パラメータ
	 * @return 削除件数
	 */
	int updatePhoneDelete(PhoneEntity params);

	/**
	 * 更新フラグ更新処理
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updatePhoneUpdateFlag(PhoneEntity params);

	/**
	 * CUCM取込処理
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updatePhoneFetchCucm(PhoneEntity params);
	
	/**
	 * 電話機に該当するオーナーユーザーIDの更新処理
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updatePhoneOwnerUser(PhoneEntity params);
	
	/**
	 * 削除予約フラグに該当する、電話機のオーナーユーザーIDの更新処理
	 *
	 * @param params 更新パラメータ
	 * @return 更新件数
	 */
	int updatePhoneOwnerUserReserveDelete(Map<String, Object> params);

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * PhoneLineMapper.java
 *
 * @date 2013/08/23
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.PhoneLineEntity;

/**
 * <pre>
 * R_CUCM_PHONE_LINEテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/23 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/23
 */
public interface PhoneLineMapper {

	/**
	 * 登録処理
	 *
	 * @param params 更新値
	 * @return 更新件数
	 */
	int insertPhoneLine(PhoneLineEntity params);


	/**
	 * 更新処理
	 *
	 * @param params 更新値
	 * @return 更新件数
	 */
	int updatePhoneLine(PhoneLineEntity params);

	/**
	 * 削除フラグを立てる
	 *
	 * @param params 更新値
	 * @return 更新件数
	 */
	int updatePhoneLineDelete(PhoneLineEntity params);
	
	/**
	 * 削除フラグを立てる
	 * ※ダイアルインも更新する
	 *
	 * @param params 更新値
	 * @return 更新件数
	 */
	int updatePhoneLineDeleteDialin(PhoneLineEntity params);

	/**
	 * CUCM取込処理
	 *
	 * @param params 更新値
	 * @return 更新件数
	 */
	int updatePhoneLineFetchCucm(PhoneLineEntity params);
	
	/**
	 * 備考更新処理
	 * 
	 * @param params 更新値
	 * @return 更新件数
	 */
	int updatePhoneLineRemarks(PhoneLineEntity params);


}
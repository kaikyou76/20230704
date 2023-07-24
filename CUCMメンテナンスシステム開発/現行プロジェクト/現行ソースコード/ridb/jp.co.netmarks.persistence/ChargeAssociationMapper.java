/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ChargeAssociationMapper.java
 *
 * @date 2013/08/27
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.ChargeAssociationEntity;

/**
 * <pre>
 * 課金関連テーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/27 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/27
 */
public interface ChargeAssociationMapper {

	/**
	 * 登録処理
	 *
	 * @param params 登録値
	 * @return 件数
	 */
	int inertChargeAssociation(ChargeAssociationEntity params);

	/**
	 * 更新処理
	 *
	 * @param params 更新値
	 * @return 件数
	 */
	int updateChargeAssociation(ChargeAssociationEntity params);

	/**
	 * 更新処理(拠点、店部課、親店部課)
	 *
	 * @param params 更新値
	 * @return 件数
	 */
	int updateChargeAssociationPlace(ChargeAssociationEntity params);

	/**
	 * 更新処理（削除）
	 *
	 * @param params 更新値
	 * @return 件数
	 */
	int updateChargeAssociationDelete(ChargeAssociationEntity params);

}
*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UnityAssociationMapper.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.UnityAssociationEntity;

/**
 * <pre>
 * UNITY_ASSOCIATIONテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/30
 */
public interface UnityAssociationMapper {

	/**
	 * 登録処理
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertUnityAssociation(UnityAssociationEntity params);

	/**
	 * 削除処理
	 *
	 * @param params 削除パラメータ
	 * @return 削除件数
	 */
	int deleteUnityAssociation(UnityAssociationEntity params);

}
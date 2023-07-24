/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * PhoneReflectedClusterMapper.java
 *
 * @date 2013/10/25
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.PhoneReflectedClusterEntity;

/**
 * <pre>
 * PHONE_REFLECTED_CLUSTERテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/25 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/25
 */
public interface PhoneReflectedClusterMapper {
	
	/**
	 * 登録処理
	 *
	 * @param params 登録パラメータ
	 * @return 登録件数
	 */
	int insertPhoneReflectedCluster(PhoneReflectedClusterEntity params);

}


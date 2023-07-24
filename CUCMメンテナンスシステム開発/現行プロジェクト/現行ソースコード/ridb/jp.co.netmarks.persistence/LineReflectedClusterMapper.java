/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LineReflectedClusterMapper.java
 *
 * @date 2013/10/25
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.LineReflectedClusterEntity;

/**
 * <pre>
 * LINE_REFLECTED_CLUSTERテーブル用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/25 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/25
 */
public interface LineReflectedClusterMapper {

	/**
	 * 登録処理
	 * 
	 * @param lineReflectedClusterEntity 登録パラメータ
	 * @return 登録件数
	 */
	int insertLineReflectedCluster(LineReflectedClusterEntity lineReflectedClusterEntity);
	
}
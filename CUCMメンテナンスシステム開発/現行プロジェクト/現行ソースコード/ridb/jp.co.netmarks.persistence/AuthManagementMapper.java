/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementMapper.java
 *
 * @date 2013/09/10
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.persistence;

import java.util.List;

import jp.co.netmarks.model.AuthManagementModel;
import jp.co.netmarks.model.AuthManagementResultModel;

/**
 * <pre>
 * 権限管理画面用データマッパーインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/10 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/10
 */
public interface AuthManagementMapper {

	/**
	 * 管理権限付与者一覧の検索件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果件数
	 */
	int getAuthManagementListTotal(AuthManagementModel params);

	/**
	 * 管理権限付与者一覧の検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<AuthManagementResultModel> getAuthManagementList(AuthManagementModel params);

	/**
	 * 管理権限付与対象者一覧の検索件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果件数
	 */
	int getAuthManagementTargetListTotal(AuthManagementModel params);

	/**
	 * 管理権限付与対象者一覧の検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<AuthManagementResultModel> getAuthManagementTargetList(AuthManagementModel params);
}
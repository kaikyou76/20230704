/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BranchMapper.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.BranchEntity;

/**
 * <pre>
 * M_BRANCHテーブル用データマッパーインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public interface BranchMapper {


	/**
	 * 拠点テーブル登録
	 * @param params 登録情報
	 * @return 登録件数
	 */
	int insertBranch(BranchEntity params);

	/**
	 * 拠点テーブル更新
	 * @param params 更新情報
	 * @return 更新件数
	 */
	int updateBranch(BranchEntity params);

	/**
	 * 拠点テーブル削除
	 * @param branchId 拠点ID
	 * @return 削除件数
	 */
	int deleteBranch(String branchId);

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * SectionBranchMapper.java
 *
 * @date 2013/09/20
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.persistence;

import jp.co.netmarks.model.entity.SectionBranchEntity;

/**
 * <pre>
 * R_SECTION_BRANCHテーブル用データマッパーインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/20 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/20
 */
public interface SectionBranchMapper {

	/**
	 * 拠点_店部課テーブル登録
	 *
	 * @param params 登録情報
	 * @return 登録件数
	 */
	int insertSectionBranch(SectionBranchEntity params);

	/**
	 * 拠点_店部課テーブル削除
	 *
	 * @param params 削除条件
	 * @return 削除件数
	 */
	int deleteSectionBranch(SectionBranchEntity params);

}


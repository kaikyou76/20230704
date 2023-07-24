/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddUserMapper.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.util.List;

import jp.co.netmarks.model.AddTelLineUserUpdateModel;
import jp.co.netmarks.model.AddUserModel;
import jp.co.netmarks.model.AddUserResultModel;

/**
 * <pre>
 * ユーザーの検索と選択画面用データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/30
 */
public interface AddUserMapper {

	/**
	 * 検索件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	int getUserAddTotal(AddUserModel params);

	/**
	 * 検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	List<AddUserResultModel> getUserAddList(AddUserModel params);

	/**
	 * ユーザーと電話機の紐付き情報Eの削除フラグを取得する
	 *
	 * @param params 条件
	 * @return カウント
	 */
	String getUserTelDeleteFlg(AddTelLineUserUpdateModel params);

}
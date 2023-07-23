/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * GridListForm.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.model.form;

import java.util.List;

/**
 * <pre>
 * リスト用基底フォームインタフェース
 * Gridのリストデータを受け取るフォームクラスのインターフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public interface GridListForm {

	/* Gridデータを取得するメソッド */
	public List<?> getGridForm();

}
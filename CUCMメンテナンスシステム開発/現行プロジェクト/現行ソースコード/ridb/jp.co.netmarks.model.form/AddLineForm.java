/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineForm.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * ラインの検索と選択画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
public class AddLineForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = -7389794212151714226L;
	
	/* 内線番号 */
	private String addLineDirectoryNumber  = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return addLineDirectoryNumber
	 */
	public String getAddLineDirectoryNumber() {
		return addLineDirectoryNumber;
	}
	/**
	 * @param addLineDirectoryNumber セットする addLineDirectoryNumber
	 */
	public void setAddLineDirectoryNumber(String addLineDirectoryNumber) {
		this.addLineDirectoryNumber = addLineDirectoryNumber;
	}
}
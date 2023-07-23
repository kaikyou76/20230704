/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LogManagementSearchForm.java
 *
 * @date 2013/09/17
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * ログ管理画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/17 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/17
 */
public class LogManagementSearchForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = -3575946885327753521L;

	/* 更新日 */
	private String updateDate = null;
	/* ディレクトリ区分 */
	private String dirDiv = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return dirDiv
	 */
	public String getDirDiv() {
		return dirDiv;
	}

	/**
	 * @param dirDiv セットする dirDiv
	 */
	public void setDirDiv(String dirDiv) {
		this.dirDiv = dirDiv;
	}






}


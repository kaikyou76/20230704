/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LogManagementDownloadListForm.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;

import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.model.form.GridListForm;
import jp.co.netmarks.model.form.list.LogManagementDownloadForm;

/**
 * <pre>
 * ログ管理画面ダウンロード用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/18
 */
public class LogManagementListDownloadForm extends BaseForm implements GridListForm, Serializable{


	private static final long serialVersionUID = -7015631758376694709L;

	public List<LogManagementDownloadForm> gridForm;

	@SuppressWarnings("unchecked")
	public LogManagementListDownloadForm() {
		super();
		gridForm = ListUtils.lazyList(new ArrayList<LogManagementDownloadForm>(), new Factory() {
			public Object create() {
				return new LogManagementDownloadForm();
			}
		});
	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/


	@Override
	public List<LogManagementDownloadForm> getGridForm() {
		return gridForm;
	}

	/**
	 * @param gridForm セットする gridForm
	 */
	public void setGridForm(List<LogManagementDownloadForm> gridForm) {
		this.gridForm = gridForm;
	}



}


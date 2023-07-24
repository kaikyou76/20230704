/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditListForm.java
 *
 * @date 2013/09/02
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.model.form.GridListForm;
import jp.co.netmarks.model.form.list.TelLumpEditUpdateForm;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;


/**
 * <pre>
 * 電話機一括登録画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/02 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/02
 */
public class TelLumpEditListForm extends BaseForm implements GridListForm, Serializable {


	private static final long serialVersionUID = 2257237593493996032L;

	@Valid
	public List<TelLumpEditUpdateForm> gridForm;

	@SuppressWarnings("unchecked")
	public TelLumpEditListForm() {
		super();
		gridForm = ListUtils.lazyList(new ArrayList<TelLumpEditUpdateForm>(), new Factory() {
			public Object create() {
				return new TelLumpEditUpdateForm();
			}
		});
	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return gridForm
	 */
	@Override
	public List<TelLumpEditUpdateForm> getGridForm() {
		return gridForm;
	}

	/**
	 * @param gridForm セットする gridForm
	 */
	public void setGridForm(List<TelLumpEditUpdateForm> gridForm) {
		this.gridForm = gridForm;
	}


}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CheckConsistencyListUpdateForm.java
 *
 * @date 2013/09/13
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;

import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.model.form.GridListForm;
import jp.co.netmarks.model.form.list.CheckConsistencyUpdateForm;

/**
 * <pre>
 * CUCM整合性チェック画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/13 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/13
 */
public class CheckConsistencyListUpdateForm extends BaseForm implements GridListForm, Serializable{

	private static final long serialVersionUID = -7774575783076722040L;

	@Valid
	public List<CheckConsistencyUpdateForm> gridForm;

	@SuppressWarnings("unchecked")
	public CheckConsistencyListUpdateForm() {
		super();
		gridForm = ListUtils.lazyList(new ArrayList<CheckConsistencyUpdateForm>(), new Factory() {
			public Object create() {
				return new CheckConsistencyUpdateForm();
			}
		});
	}



/*********************************************************************
 * generated getter/setter
**********************************************************************/

	@Override
	public List<CheckConsistencyUpdateForm> getGridForm() {
		return gridForm;
	}

	public void setGridForm(List<CheckConsistencyUpdateForm> gridForm) {
		this.gridForm = gridForm;
	}
}
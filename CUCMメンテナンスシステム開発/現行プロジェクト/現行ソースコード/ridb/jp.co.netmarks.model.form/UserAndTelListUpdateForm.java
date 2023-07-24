/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelListUpdateForm.java
 *
 * @date 2013/08/21
 * @version 1.0
 * @author KSC Yoshida Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import jp.co.ksc.model.form.BaseForm;
import jp.co.ksc.model.form.GridListForm;
import jp.co.netmarks.model.form.list.UserAndTelUpdateForm;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;

/**
 * <pre>
 * ユーザーと電話機の一覧画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/21 KSC Yoshida Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yoshida Yoshida
 * @version 1.0 2013/08/21
 */
public class UserAndTelListUpdateForm  extends BaseForm implements GridListForm, Serializable {

	private static final long serialVersionUID = -2222787136038181731L;

	@Valid
	public List<UserAndTelUpdateForm> gridForm;

	@SuppressWarnings("unchecked")
	public UserAndTelListUpdateForm() {
		super();
		gridForm = ListUtils.lazyList(new ArrayList<UserAndTelUpdateForm>(), new Factory() {
			public Object create() {
				return new UserAndTelUpdateForm();
			}
		});
	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	@Override
	public List<UserAndTelUpdateForm> getGridForm() {
		return gridForm;
	}

	public void setGridForm(List<UserAndTelUpdateForm> gridForm) {
		this.gridForm = gridForm;
	}

}
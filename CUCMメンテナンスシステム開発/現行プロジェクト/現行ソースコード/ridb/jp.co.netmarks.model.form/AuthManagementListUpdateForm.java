/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementListUpdateForm.java
 *
 * @date 2013/09/11
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
import jp.co.netmarks.model.form.list.AuthManagementUpdateForm;

/**
 * <pre>
 * 権限管理画面更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/11 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/11
 */
public class AuthManagementListUpdateForm extends BaseForm implements GridListForm, Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	public List<AuthManagementUpdateForm> authManagementGridForm;

	public List<AuthManagementUpdateForm> authManagementTargetGridForm;

	@Valid
	public List<AuthManagementUpdateForm> gridForm;

	@SuppressWarnings("unchecked")
	public AuthManagementListUpdateForm() {
		super();
		gridForm = ListUtils.lazyList(new ArrayList<AuthManagementUpdateForm>(), new Factory() {
			public Object create() {
				return new AuthManagementUpdateForm();
			}
		});
	}

/*********************************************************************
 * generated getter/setter
**********************************************************************/

		@Override
		public List<AuthManagementUpdateForm> getGridForm() {
			return gridForm;
		}

		public void setGridForm(List<AuthManagementUpdateForm> gridForm) {
			this.gridForm = gridForm;
		}


}
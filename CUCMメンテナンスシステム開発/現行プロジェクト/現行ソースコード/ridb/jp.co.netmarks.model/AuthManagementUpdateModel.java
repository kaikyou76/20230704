/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AuthManagementUpdateModel.java
 *
 * @date 2013/09/11
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.ksc.model.AbstractSearchModel;

/**
 * <pre>
 * 権限管理更新用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/11 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/11
 */
public class AuthManagementUpdateModel extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = -1977421828148272966L;

	/* チェック有無 */
	private boolean checked;

	/* 値の変更有無 */
	private boolean changed;

	/* ユーザID */
	private BigDecimal userId               = null;
	/* 権限区分 */
	private String authId               = null;

/*********************************************************************
 * generated getter/setter
 **********************************************************************/

	/**
	 * @return checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked セットする checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return changed
	 */
	public boolean isChanged() {
		return changed;
	}
	/**
	 * @param changed セットする changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	/**
	 * @return userId
	 */
	public BigDecimal getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	/**
	 * @return authId
	 */
	public String getAuthId() {
		return authId;
	}
	/**
	 * @param authId セットする authId
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

}
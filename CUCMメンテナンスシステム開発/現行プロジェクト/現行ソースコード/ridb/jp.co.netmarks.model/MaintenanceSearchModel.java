/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceSearchModel.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.util.Map;

import jp.co.ksc.model.AbstractSearchModel;
import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * メンテナンス用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public class MaintenanceSearchModel extends AbstractSearchModel implements Serializable{


	private static final long serialVersionUID = 1L;

	/* 拠点 */
	private String branchId      = null;


/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	/**
	 * @return branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId セットする branchId
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


}
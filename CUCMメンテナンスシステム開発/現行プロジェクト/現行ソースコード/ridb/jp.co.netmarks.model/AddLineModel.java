/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineModel.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.util.Map;

import jp.co.ksc.model.AbstractSearchModel;
import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * ラインの検索と選択用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
public class AddLineModel  extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = 1925915154845259147L;

	/** 検索条件 **/
	/* 内線番号 */
	private String addLineDirectoryNumber = null;

	/* ラインID */
	private String orgLineId = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

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
	/**
	 * @return orgLineId
	 */
	public String getOrgLineId() {
		return orgLineId;
	}
	/**
	 * @param orgLineId セットする orgLineId
	 */
	public void setOrgLineId(String orgLineId) {
		this.orgLineId = orgLineId;
	}
}
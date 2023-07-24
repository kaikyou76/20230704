/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceSearchForm.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.List;


import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * メンテナンス画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
public class MaintenanceSearchForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = -147497775913608240L;

	/* 拠点 */
	private String branchId      = null;

	/** ### List ### **/
	/* 拠点リスト */
	private List<LabelValueModel> branchList    = null;
	/* クラスタリスト */
	private List<LabelValueModel> clusterList   = null;
	/* 取込テーブルリスト */
	private List<LabelValueModel> tableList     = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

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

	/**
	 * @return branchList
	 */
	public List<LabelValueModel> getBranchList() {
		return branchList;
	}

	/**
	 * @param branchList セットする branchList
	 */
	public void setBranchList(List<LabelValueModel> branchList) {
		this.branchList = branchList;
	}

	/**
	 * @return clusterList
	 */
	public List<LabelValueModel> getClusterList() {
		return clusterList;
	}

	/**
	 * @param clusterList セットする clusterList
	 */
	public void setClusterList(List<LabelValueModel> clusterList) {
		this.clusterList = clusterList;
	}

	/**
	 * @return tableList
	 */
	public List<LabelValueModel> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList セットする tableList
	 */
	public void setTableList(List<LabelValueModel> tableList) {
		this.tableList = tableList;
	}




}
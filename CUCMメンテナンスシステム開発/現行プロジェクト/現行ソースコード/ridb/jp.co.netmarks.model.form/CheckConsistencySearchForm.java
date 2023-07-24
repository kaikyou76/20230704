/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CheckConsistencySearchForm.java
 *
 * @date 2013/09/12
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
 * CUCM整合性チェック画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/12
 */
public class CheckConsistencySearchForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;

	/* クラスタID */
	private String clusterId  = null;
	/* CUCM情報取得日 */
	private String cucmUpdateTime = null;

	/** ### List ### **/
	/* クラスタリスト */
	private List<LabelValueModel> clusterList     = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/**
	 * @return clusterId
	 */
	public String getClusterId() {
		return clusterId;
	}

	/**
	 * @param clusterId セットする clusterId
	 */
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}


	/**
	 * @return cucmUpdateTime
	 */
	public String getCucmUpdateTime() {
		return cucmUpdateTime;
	}

	/**
	 * @param cucmUpdateTime セットする cucmUpdateTime
	 */
	public void setCucmUpdateTime(String cucmUpdateTime) {
		this.cucmUpdateTime = cucmUpdateTime;
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



}
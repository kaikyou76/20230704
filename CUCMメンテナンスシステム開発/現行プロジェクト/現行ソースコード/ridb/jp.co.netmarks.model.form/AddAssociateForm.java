/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddAssociateForm.java
 *
 * @date 2013/11/01
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 拠点-店部課紐付き作成画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/11/01 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/11/01
 */
public class AddAssociateForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = -3960282754466076447L;

	/* 拠点コード */
	@Pattern(regexp="^([0-9_]+)?$")
	@Size(min=3, max=5, message="{max}桁までで入力してください。")
	private String branchId        = null;
	/* 拠点名 */
	@NotEmpty
	@Size(max=40, message="{max}桁までで入力してください。")
	private String branchName      = null;
	/* クラスタID */
	private String clusterId       = null;
	/* 会社コード */
	@Pattern(regexp="^([0-9]+)?$")
	@Size(min=3, max=3, message="{min}桁で入力してください。")
	private String companyId       = null;
	/* 店部課コード */
	@Pattern(regexp="^([0-9]+)?$")
	@Size(min=5, max=5, message="{min}桁で入力してください。")
	private String sectionId       = null;

	/** ### List ### **/
	/* クラスタリスト */
	private List<LabelValueModel> clusterList   = null;

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
	 * @return branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName セットする branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
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
	 * @return companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId セットする companyId
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return sectionId
	 */
	public String getSectionId() {
		return sectionId;
	}
	/**
	 * @param sectionId セットする sectionId
	 */
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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
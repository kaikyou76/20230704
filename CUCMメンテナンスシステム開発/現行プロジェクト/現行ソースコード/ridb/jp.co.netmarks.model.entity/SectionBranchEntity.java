/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * SectionBranchEntity.java
 *
 * @date 2013/09/20
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * R_SECTION_BRANCHテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/20 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/20
 */
public class SectionBranchEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -8231101572830263088L;

	private String branchId         = null;
	private String companyId        = null;
	private String sectionId        = null;

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


}
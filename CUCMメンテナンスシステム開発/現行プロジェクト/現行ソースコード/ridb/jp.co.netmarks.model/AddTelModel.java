/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelModel.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import jp.co.ksc.model.AbstractSearchModel;
import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * 電話機の検索と選択用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
public class AddTelModel  extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = -292020492602181744L;

	/* 内線番号 */
	private String addTelDirectoryNumber   = null;
	/* ステータス */
	private String telStatus               = null;
	/* 拠点ID */
	private String addTelBranchId          = null;
	/* 会社－店部課 */
	private String addTelCompanySectionId  = null;
	/* 店部課ID */
	private String addTelSectionId         = null;
	/* 会社ID */
	private String addTelCompanyId         = null;
	/* 拠点（課金先） */
	private String addTelChargeAssociationBranchId        = null;
	/* 親店部課（課金先） */
	private String addTelChargeAssociationParentSectionId = null;
	/* 店部課（課金先） */
	private String addTelChargeAssociationSectionId       = null;
	/* 選択元の電話機ID */
	private BigDecimal orgTelId            = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	/**
	 * @return addTelDirectoryNumber
	 */
	public String getAddTelDirectoryNumber() {
		return addTelDirectoryNumber;
	}
	/**
	 * @param addTelDirectoryNumber セットする addTelDirectoryNumber
	 */
	public void setAddTelDirectoryNumber(String addTelDirectoryNumber) {
		this.addTelDirectoryNumber = addTelDirectoryNumber;
	}
	/**
	 * @return telStatus
	 */
	public String getTelStatus() {
		return telStatus;
	}
	/**
	 * @param telStatus セットする telStatus
	 */
	public void setTelStatus(String telStatus) {
		this.telStatus = telStatus;
	}
	/**
	 * @return addTelBranchId
	 */
	public String getAddTelBranchId() {
		return addTelBranchId;
	}
	/**
	 * @param addTelBranchId セットする addTelBranchId
	 */
	public void setAddTelBranchId(String addTelBranchId) {
		this.addTelBranchId = addTelBranchId;
	}
	/**
	 * @return addTelCompanySectionId
	 */
	public String getAddTelCompanySectionId() {
		return addTelCompanySectionId;
	}
	/**
	 * @param addTelCompanySectionId セットする addTelCompanySectionId
	 */
	public void setAddTelCompanySectionId(String addTelCompanySectionId) {
		this.addTelCompanySectionId = addTelCompanySectionId;
	}
	/**
	 * @return addTelSectionId
	 */
	public String getAddTelSectionId() {
		return addTelSectionId;
	}
	/**
	 * @param addTelSectionId セットする addTelSectionId
	 */
	public void setAddTelSectionId(String addTelSectionId) {
		this.addTelSectionId = addTelSectionId;
	}
	/**
	 * @return addTelCompanyId
	 */
	public String getAddTelCompanyId() {
		return addTelCompanyId;
	}
	/**
	 * @param addTelCompanyId セットする addTelCompanyId
	 */
	public void setAddTelCompanyId(String addTelCompanyId) {
		this.addTelCompanyId = addTelCompanyId;
	}
	/**
	 * @return addTelChargeAssociationBranchId
	 */
	public String getAddTelChargeAssociationBranchId() {
		return addTelChargeAssociationBranchId;
	}
	/**
	 * @param addTelChargeAssociationBranchId セットする addTelChargeAssociationBranchId
	 */
	public void setAddTelChargeAssociationBranchId(
			String addTelChargeAssociationBranchId) {
		this.addTelChargeAssociationBranchId = addTelChargeAssociationBranchId;
	}
	/**
	 * @return addTelChargeAssociationParentSectionId
	 */
	public String getAddTelChargeAssociationParentSectionId() {
		return addTelChargeAssociationParentSectionId;
	}
	/**
	 * @param addTelChargeAssociationParentSectionId セットする addTelChargeAssociationParentSectionId
	 */
	public void setAddTelChargeAssociationParentSectionId(
			String addTelChargeAssociationParentSectionId) {
		this.addTelChargeAssociationParentSectionId = addTelChargeAssociationParentSectionId;
	}
	/**
	 * @return addTelChargeAssociationSectionId
	 */
	public String getAddTelChargeAssociationSectionId() {
		return addTelChargeAssociationSectionId;
	}
	/**
	 * @param addTelChargeAssociationSectionId セットする addTelChargeAssociationSectionId
	 */
	public void setAddTelChargeAssociationSectionId(
			String addTelChargeAssociationSectionId) {
		this.addTelChargeAssociationSectionId = addTelChargeAssociationSectionId;
	}
	/**
	 * @return orgTelId
	 */
	public BigDecimal getOrgTelId() {
		return orgTelId;
	}
	/**
	 * @param orgTelId セットする orgTelId
	 */
	public void setOrgTelId(BigDecimal orgTelId) {
		this.orgTelId = orgTelId;
	}
}
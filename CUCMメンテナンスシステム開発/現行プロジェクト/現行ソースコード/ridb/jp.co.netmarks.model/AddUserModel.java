/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddUserModel.java
 *
 * @date 2013/08/30
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
 * ユーザーの検索と選択用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/30
 */
public class AddUserModel extends AbstractSearchModel implements Serializable {

	private static final long serialVersionUID = 7338010546219030034L;

	/* ユーザーカナ名 */
	private String addUserKanaUserName    = null;
	/* ステータス */
	private String retainTelStatus          = null;
	/* 拠点ID */
	private String addUserBranchId          = null;
	/* 会社－店部課 */
	private String addUserCompanySectionId  = null;
	/* 店部課ID */
	private String addUserSectionId         = null;
	/* 会社ID */
	private String addUserCompanyId         = null;
	/* 課名 */
	private String addUserAttachSectionName = null;
	/* 選択元のユーザーID */
	private BigDecimal orgUserId            = null;
	/* 選択元のユーザーID */
	private String orgCompanyUserId         = null;
	/* 選択元のユーザーID */
	private String orgSectionUserId         = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	/**
	 * @return addUserKanaUserName
	 */
	public String getAddUserKanaUserName() {
		return addUserKanaUserName;
	}
	/**
	 * @param addUserKanaUserName セットする addUserKanaUserName
	 */
	public void setAddUserKanaUserName(String addUserKanaUserName) {
		this.addUserKanaUserName = addUserKanaUserName;
	}
	/**
	 * @return retainTelStatus
	 */
	public String getRetainTelStatus() {
		return retainTelStatus;
	}
	/**
	 * @param retainTelStatus セットする retainTelStatus
	 */
	public void setRetainTelStatus(String retainTelStatus) {
		this.retainTelStatus = retainTelStatus;
	}
	/**
	 * @return addUserBranchId
	 */
	public String getAddUserBranchId() {
		return addUserBranchId;
	}
	/**
	 * @param addUserBranchId セットする addUserBranchId
	 */
	public void setAddUserBranchId(String addUserBranchId) {
		this.addUserBranchId = addUserBranchId;
	}
	/**
	 * @return addUserCompanySectionId
	 */
	public String getAddUserCompanySectionId() {
		return addUserCompanySectionId;
	}
	/**
	 * @param addUserCompanySectionId セットする addUserCompanySectionId
	 */
	public void setAddUserCompanySectionId(String addUserCompanySectionId) {
		this.addUserCompanySectionId = addUserCompanySectionId;
	}
	/**
	 * @return addUserSectionId
	 */
	public String getAddUserSectionId() {
		return addUserSectionId;
	}
	/**
	 * @param addUserSectionId セットする addUserSectionId
	 */
	public void setAddUserSectionId(String addUserSectionId) {
		this.addUserSectionId = addUserSectionId;
	}
	/**
	 * @return addUserCompanyId
	 */
	public String getAddUserCompanyId() {
		return addUserCompanyId;
	}
	/**
	 * @param addUserCompanyId セットする addUserCompanyId
	 */
	public void setAddUserCompanyId(String addUserCompanyId) {
		this.addUserCompanyId = addUserCompanyId;
	}
	/**
	 * @return addUserAttachSectionName
	 */
	public String getAddUserAttachSectionName() {
		return addUserAttachSectionName;
	}
	/**
	 * @param addUserAttachSectionName セットする addUserAttachSectionName
	 */
	public void setAddUserAttachSectionName(String addUserAttachSectionName) {
		this.addUserAttachSectionName = addUserAttachSectionName;
	}
	/**
	 * @return orgUserId
	 */
	public BigDecimal getOrgUserId() {
		return orgUserId;
	}
	/**
	 * @param orgUserId セットする orgUserId
	 */
	public void setOrgUserId(BigDecimal orgUserId) {
		this.orgUserId = orgUserId;
	}
	/**
	 * @return orgCompanyUserId
	 */
	public String getOrgCompanyUserId() {
		return orgCompanyUserId;
	}
	/**
	 * @param orgCompanyUserId セットする orgCompanyUserId
	 */
	public void setOrgCompanyUserId(String orgCompanyUserId) {
		this.orgCompanyUserId = orgCompanyUserId;
	}
	/**
	 * @return orgSectionUserId
	 */
	public String getOrgSectionUserId() {
		return orgSectionUserId;
	}
	/**
	 * @param orgSectionUserId セットする orgSectionUserId
	 */
	public void setOrgSectionUserId(String orgSectionUserId) {
		this.orgSectionUserId = orgSectionUserId;
	}
}
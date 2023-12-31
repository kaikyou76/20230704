/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BaseEntity.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import jp.co.netmarks.util.ModelUtil;

/**
 * <pre>
 * Entitiyの基底モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 764317129858719542L;

	/** tableName */
	private String tableName;
	/** 作成者ID */
	private String crtUsrId;
	/** 作成日時 */
	private Timestamp crtTmstmp;
	/** 最終更新者ID */
	private String lstupdtUsrId;
	/** 最終更新日時 */
	private Timestamp lstupdtTmstmp;

/*********************************************************************
 * generated getter/setter
**********************************************************************/

	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCrtUsrId() {
		return crtUsrId;
	}
	public void setCrtUsrId(String crtUsrId) {
		this.crtUsrId = crtUsrId;
	}
	public Timestamp getCrtTmstmp() {
		return crtTmstmp;
	}
	public void setCrtTmstmp(Timestamp crtTmstmp) {
		this.crtTmstmp = crtTmstmp;
	}
	public String getLstupdtUsrId() {
		return lstupdtUsrId;
	}
	public void setLstupdtUsrId(String lstupdtUsrId) {
		this.lstupdtUsrId = lstupdtUsrId;
	}
	public Timestamp getLstupdtTmstmp() {
		return lstupdtTmstmp;
	}
	public void setLstupdtTmstmp(Timestamp lstupdtTmstmp) {
		this.lstupdtTmstmp = lstupdtTmstmp;
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CheckConsistensySearchModel.java
 *
 * @date 2013/09/12
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
 * CUCM整合性チェック用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/12
 */
public class CheckConsistencySearchModel extends AbstractSearchModel implements Serializable {


	private static final long serialVersionUID = 4065199418112526036L;

	/* クラスタID */
	//private BigDecimal clusterId  = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/
	/** Constansの定数を取得する */
	public Map<String, Object> getConstants() {
		return ModelUtil.getConstants();
	}

	/**
	 * @return clusterId
	 */
//	public BigDecimal getClusterId() {
//		return clusterId;
//	}
//
//	/**
//	 * @param clusterId セットする clusterId
//	 */
//	public void setClusterId(BigDecimal clusterId) {
//		this.clusterId = clusterId;
//	}



}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * SeparatelyReferenceForm.java
 *
 * @date 2013/09/25
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.model.form;

import java.io.Serializable;

import jp.co.ksc.model.form.BaseForm;

/**
 * <pre>
 * 個別参照画面用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/25 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/25
 */
public class SeparatelyReferenceForm  extends BaseForm implements Serializable{

	private static final long serialVersionUID = 8857063430285411353L;
	
	/* 全転送 */
	private String fwdAllDestination = null;

/*********************************************************************
 * generated getter/setter
**********************************************************************/
	
	/**
	 * @return fwdAllDestination
	 */
	public String getFwdAllDestination() {
		return fwdAllDestination;
	}
	/**
	 * @param fwdAllDestination セットする fwdAllDestination
	 */
	public void setFwdAllDestination(String fwdAllDestination) {
		this.fwdAllDestination = fwdAllDestination;
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LabelValueModel.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.model;

import java.io.Serializable;

/**
 * <pre>
 * LABEL/VALUEモデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public class LabelValueModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 */
	public LabelValueModel(){
		super();
	}

	/**
	 * コンストラクタ
	 *
	 * @param label
	 * @param value
	 */
	public LabelValueModel(String label, String value){
		this.label = label;
		this.value = value;
	}

	/** label */
	private String label;

	/** value */
	private String value;

	/**
	 * labelを取得します。
	 * @return label
	 */
	public String getLabel() {
	    return label;
	}

	/**
	 * labelを設定します。
	 * @param label label
	 */
	public void setLabel(String label) {
	    this.label = label;
	}

	/**
	 * valueを取得します。
	 * @return value
	 */
	public String getValue() {
	    return value;
	}

	/**
	 * valueを設定します。
	 * @param value value
	 */
	public void setValue(String value) {
	    this.value = value;
	}
}
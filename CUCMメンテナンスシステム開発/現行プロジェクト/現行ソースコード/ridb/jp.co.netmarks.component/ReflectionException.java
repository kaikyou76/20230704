/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionException.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.component;

/**
 * <pre>
 * 個別反映処理失敗時用　Exception
 * result 0:電話機処理で失敗
 *        1:ライン処理で失敗
 *        2:ユーザ処理で失敗、
 *        3:電話機とラインの紐付で失敗
 *        4:ユーザと電話機の紐付で失敗
 *        5:ユーザと電話機の紐付(削除済削除)で失敗
 *        6:ALL成功
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class ReflectionException extends Exception {

    private static final long serialVersionUID = 1L;
	private int result;

    /**
     * 個別反映処理時　Exception
     * @param cause
     * @param result
     */
    public ReflectionException(Throwable cause,int result) {
        super(cause);
		this.result= result;
    }

	/**
	 * resultを設定します。
	 * @param result result
	 */
	public void setResult(int result) {
	    this.result = result;
	}

	/**
	 * resultを取得します。
	 * @return result
	 */
	public int getResult() {
		return result;
	}
}
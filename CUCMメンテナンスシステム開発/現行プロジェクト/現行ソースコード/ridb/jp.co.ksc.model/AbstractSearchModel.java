/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AbstractSearchModel.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.model;

/**
 * <pre>
 * 検索時の基底モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public abstract class AbstractSearchModel {

	/** ページング：開始位置 */
	private int start;
	/** ページング：終了位置 */
	private int end;
	/** ページング：ページ数 */
	private int page;
	/** ページング：取得件数 */
	private int rows;
	/** ソート項目 */
	private String sort;
	/** ソート方向 */
	private String order;

	/**
	 * ページング：開始位置を取得します。
	 * @return ページング：開始位置
	 */
	public int getStart() {
		this.start = (page - 1)  * rows + 1;
	    return start;
	}
	/**
	 * ページング：終了位置を取得します。
	 * @return end
	 */
	public int getEnd() {
		this.end = page * rows;
		return end;
	}
	/**
	 * ページング：ページ数を取得します。
	 * @return ページング：ページ数
	 */
	public int getPage() {
	    return page;
	}
	/**
	 * ページング：ページ数を設定します。
	 * @param page ページング：ページ数
	 */
	public void setPage(int page) {
	    this.page = page;
	}
	/**
	 * ページング：取得件数を取得します。
	 * @return ページング：取得件数
	 */
	public int getRows() {
	    return rows;
	}
	/**
	 * ページング：取得件数を設定します。
	 * @param rows ページング：取得件数
	 */
	public void setRows(int rows) {
	    this.rows = rows;
	}
	/**
	 * ソート項目を取得します。
	 * @return ソート項目
	 */
	public String getSort() {
	    return sort;
	}
	/**
	 * ソート項目を設定します。
	 * @param sort ソート項目
	 */
	public void setSort(String sort) {
	    this.sort = sort;
	}
	/**
	 * ソート方向を取得します。
	 * @return ソート方向
	 */
	public String getOrder() {
	    return order;
	}
	/**
	 * ソート方向を設定します。
	 * @param order ソート方向
	 */
	public void setOrder(String order) {
	    this.order = order;
	}
}
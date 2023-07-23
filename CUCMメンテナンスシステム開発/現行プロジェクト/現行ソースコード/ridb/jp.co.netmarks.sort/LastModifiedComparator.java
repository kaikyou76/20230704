/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * LastModifiedComparator.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuki Onishi
 */
package jp.co.netmarks.sort;

import java.io.File;
import java.util.Comparator;

/**
 * <pre>
 *  ファイルソートクラス
 *  ※最終更新日時でソートします。
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuki Onishi 新規作成
 * </pre>
 *
 * @author KSC Yuki Onishi
 * @version 1.0 2013/09/09
 */
public class LastModifiedComparator implements Comparator{

	public static final int ASC = 1;    //昇順
	public static final int DESC = -1;  //降順
	private int sort = ASC; //デフォルトは昇順

	/**
	 * コンストラクタ
	 * @param sort ソート順 1:昇順 -1:降順
	 */
	public LastModifiedComparator(int sort){
		this.sort = sort;
	}

	/**
	 * ソート処理
	 * ※最終更新日単位でソートします。
	 * 
	 * @param o1 ソート対象オブジェクト
	 * @param o2 ソート対象オブジェクト
	 */
	@Override
	public int compare(Object o1, Object o2) {

	    if (o1 == null && o2 == null) {
	        return 0;
	    } else if (o1 == null) {
	        return 1 * sort;
	    } else if (o2 == null) {
	        return -1 * sort;
	    }

	    Long value1 = ((File)o1).lastModified();
	    Long value2 = ((File)o2).lastModified();

	    return value1.compareTo(value2) * sort;
	}

}
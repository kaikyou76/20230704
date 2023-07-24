/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * DataShift.java
 *
 * @date 2013/10/29
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.io.IOException;

import jp.co.ksc.util.LogHelpUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 非同期処理用クラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/29 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/29
 */
public class DataShift implements Runnable {

	private static Log log = LogFactory.getLog(DataShift.class);

	/** データ移行サービス **/
	private DataShiftService service;
	private String fileName;

	/**
	 * コンストラクタ
	 * @param service
	 */
	public DataShift(DataShiftService service,String fileName){
		this.service = service;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		try {
			// データ移行処理
			service.entry(fileName);
		} catch (IOException e) {
			log.error(LogHelpUtil.getStackTrace(e));
		}
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BatchJobErrorFileAppender.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.log4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.FileAppender;


/**
 * <pre>
 * BatchJobAppender
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class BatchJobErrorFileAppender extends FileAppender {

	/* (非 Javadoc)
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String)
	 */
	public void setFile(String fileName) {

		/* コマンドライン引数 */
		String[] commands = System.getProperty("sun.java.command").split(" ");
		String jobName = commands[2];

		/* 起動日時 */
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Timestamp(System.currentTimeMillis()));

		super.setFile(fileName + jobName + "_ERROR_" + timestamp + ".log");
	}
}
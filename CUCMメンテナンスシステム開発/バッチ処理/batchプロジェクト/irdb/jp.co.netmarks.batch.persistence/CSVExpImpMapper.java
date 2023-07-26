/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CSVExpImpMapper.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.batch.persistence;

import java.util.Map;

import jp.co.ksc.batch.util.BaseConstants;

/**
 * <pre>
 * CSV Export、Import用 マッパークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public interface CSVExpImpMapper {

	/**
	 * ALL TABLE EXPORT
	 * @param parameterValues
	 * @return 件数
	 */
	int allTableExport(Map<String,Object>parameterValues);
	/**
	 * ALL TABLE IMPORT
	 * @param parameterValues
	 * @return 件数
	 */
	int allTableImport(Map<String,Object>parameterValues);

	/**
	 * VoiceLoggerID取得
	 * @param parameterValues
	 * @return ID
	 */
	int selVoiceID(Map<String,Object>parameterValues);
	/**
	 * VoiceLogger DirectNumber取得
	 * @param parameterValues
	 * @return 取得したDirectNumber
	 */
	Map<String,Object> selVoiceDN(Map<String,Object> parameterValues);
	/**
	 * VoiceLogger 登録
	 * @param parameterValues
	 * @return 件数
	 */
	int insertVoiceLogger(Map<String, Object> parameterValues);
	/**
	 * VoiceLogger 更新
	 * @param parameterValues
	 * @return 件数
	 */
	int updateVoiceLogger(Map<String, Object> parameterValues);
	/**
	 * VoiceLogger Export
	 * @return VoiceLogger
	 */
	Map<String,Object>[] getVoiceLoggerExp();

	/**
	 * Charge Export
	 * @return Charge
	 */
	Map<String,Object>[] getChargeExp();

	/**
	 * TelDir Export
	 * @return TelDir
	 */
	Map<String,Object>[] getTelDirExp();

	/**
	 * Unity Export
	 * @return Unity
	 */
	Map<String,Object>[] getCUCExp();

	/**
	 * AD Export
	 * @return AD
	 */
	Map<String,Object>[] getADExp();

	/**
	 * LineList(回線一覧 管理会計) Export
	 * @param constants
	 * @return LineList
	 */
	Map<String,Object>[] getLineListExp(BaseConstants constants);

	/**
	 * LineList(回線一覧 管理会計_改修) Export
	 * @param constants
	 * @return LineList
	 */
	Map<String,Object>[] getUserAndTelCsvList(BaseConstants constants);

}


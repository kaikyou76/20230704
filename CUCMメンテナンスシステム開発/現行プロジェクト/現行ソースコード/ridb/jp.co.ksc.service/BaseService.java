/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BaseService.java
 *
 * @date 2013/08/27
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.persistence.CommonMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * 基底サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
public class BaseService {
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(BaseService.class);

	@Autowired
	private CommonMapper commonMapper;

	/**
	 * 排他ロックを取得する
	 * ※ロックが取得出来ない場合は、ExcludeExceptionを返す
	 *
	 * @param tableName テーブル名
	 * @param lstupdtTmstmp 最終更新日時
	 * @param primaryKeys [0]=columnName1, [1]=value1, [2]=columnName2, [3]=value2
	 * @return true：成功
	 */
	protected boolean locked(String tableName, Timestamp lstupdtTmstmp, Map<String , Object> primaryKeys) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("tableName", tableName);
		map.put("lstupdtTmstmp", lstupdtTmstmp);
		map.put("primaryKeys", primaryKeys.entrySet());

		/* 排他エラー */
		if (commonMapper.locked(map) == null) {
			throw new ExcludeException(map);
		}
		return true;
	}

	/**
	 * シーケンスを取得する
	 * 
	 * @param sequenceName シーケンス名
	 * @return シーケンス値
	 */
	protected String sequence(String sequenceName){
		return commonMapper.sequence(sequenceName);
	}

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CheckConsistencyService.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.CheckConsistencyResultModel;
import jp.co.netmarks.model.CheckConsistencySearchModel;
import jp.co.netmarks.model.CheckConsistencyUpdateModel;
import jp.co.netmarks.model.entity.LineEntity;
import jp.co.netmarks.model.entity.PhoneEntity;
import jp.co.netmarks.model.entity.PhoneLineEntity;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.CheckConsistencyMapper;
import jp.co.netmarks.persistence.LineMapper;
import jp.co.netmarks.persistence.PhoneLineMapper;
import jp.co.netmarks.persistence.PhoneMapper;

/**
 * <pre>
 * CUCM整合性チェック用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/12
 */
@Service
public class CheckConsistencyService extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(CheckConsistencyService.class);

	@Autowired
	private AppCommonMapper appCommonMapper;

	@Autowired
	private CheckConsistencyMapper checkConsistencyMapper;

	@Autowired
    private Properties properties;

	/** ##### エンティティ関連 ##### */
	@Autowired
	private PhoneMapper phoneMapper;

	@Autowired
	private PhoneLineMapper phoneLineMapper;

	@Autowired
	private LineMapper lineMapper;

	/** ##### トランザクション ##### */
	@Autowired
	private PlatformTransactionManager txManager;

	/** 検索：ソート対象カラム */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("appMacAddress","APP.MAC_ADDRESS");
		sortMap.put("appDirectoryNumber","APP.DIRECTORY_NUMBER");
		sortMap.put("cucmMacAddress","CUCM.MAC_ADDRESS");
		sortMap.put("cucmDirectoryNumber","CUCM.DIALIN");
	}

	/** 検索：デフォルトソート */
	private final String DEFAULT_SORT =
				"APP.MAC_ADDRESS ," +		// MACアドレス（連携アプリ）
				"APP.DIRECTORY_NUMBER ," +	// 内線番号（連携アプリ）
				"CUCM.MAC_ADDRESS ," +		// MACアドレス（CUCM）
				"CUCM.DIALIN";				// 内線番号（CUCM）

	/**
	 * CUCM情報取得日時を取得します。
	 *
	 * @return String CUCM情報取得日時
	 */
	public String getCucmUpdateTime() {
		return checkConsistencyMapper.getCucmMasterUpdateTime();
	}

	/**
	 * CUCM差分の検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getInconsistencyCucmTotal(CheckConsistencySearchModel params){

		return checkConsistencyMapper.getInconsistencyCucmListTotal(params);
	}

	/**
	 *
	 * CUCM差分の検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public List<CheckConsistencyResultModel> getInconsistencyCucm(CheckConsistencySearchModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}
		
		return checkConsistencyMapper.getInconsistencyCucmList(params);
	}

	/**
	 * オフィスリンク差分の検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getInconsistencyOfficeLinkTotal(CheckConsistencySearchModel params){

		return checkConsistencyMapper.getInconsistencyOfficeLinkListTotal(params);
	}

	/**
	 *
	 * オフィスリンク差分の検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public List<CheckConsistencyResultModel> getInconsistencyOfficeLink(CheckConsistencySearchModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}
		
		return checkConsistencyMapper.getInconsistencyOfficeLinkList(params);
	}

	/**
	 *
	 * CUCM情報取込処理
	 *
	 * @param list 更新パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	public Map<String, String> fetchCucm(List<CheckConsistencyUpdateModel> list) throws Exception{

		Map<String, String> map = new HashMap<String, String>();

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 更新フラグ */
		boolean sucFlg = false;
		boolean eroFlg = false;
		/* エラーメッセージセット */
		StringBuffer errorTel      = new StringBuffer();
		StringBuffer errorLine     = new StringBuffer();
		/* エラーのエンティティを設定する */
		String erroEntity = "";

		/* チェックボックスにチェックがついていた項目分処理を行います。 */
		for(CheckConsistencyUpdateModel params : list){

			/* 取込処理 */
			erroEntity = entryTelLine(params, timestamp);

			/* メッセージ対応 */
			if(StringUtils.isEmpty(erroEntity)){
				/* 成功 */
				sucFlg = true;
			} else if(erroEntity.equals(Constants.ERROR_TYPE_TEL)){
				/* 電話エラー */
				errorTel.append(params.getAppPhoneId());
				errorTel.append(",");
				eroFlg=true;
			} else if(erroEntity.equals(Constants.ERROR_TYPE_LINE)){
				/* ラインエラー */
				errorLine.append(params.getAppLineId());
				errorLine.append(",");
				eroFlg=true;
			}

		}
		/* 成功が存在した場合 */
		if(sucFlg || !eroFlg){
			map.put("sucsessMessage", properties.getProperty("update.success"));
		}

		String errMsg = "";
		/* エラーが存在した場合 */
		if(eroFlg){
			String errTelStr = errorTel.toString();
			String errLineStr = errorLine.toString();
			/* 電話 */
			if(StringUtils.isNotEmpty(errTelStr)){
				errMsg += "【電話機】" + errTelStr.substring(0, errTelStr.length()-1);
			}
			/* ライン */
			if(StringUtils.isNotEmpty(errLineStr)){
				errMsg += "【内線番号】" + errLineStr.substring(0, errLineStr.length()-1);
			}
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exclusion.error.key"),errMsg));
		}
		return map;
	}

	/**
	 * CUCM情報取込処理
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @throws Exception 例外処理
	 */
	private String entryTelLine(CheckConsistencyUpdateModel params, Timestamp timestamp) throws Exception{

		String errorType = "";

		/* トランザクションの取得 */
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;

		try{
			/* トランザクションスタート */
			ts = txManager.getTransaction(dtd);

			/* ロック処理 */
			lockedConduct(params);

			/* 存在チェック */
			errorType = existCheck(params);
			if(StringUtils.isEmpty(errorType)){
				/* 存在チェックエラーが存在しなかった場合 */

				/* 取込処理 */
				fetchCucmData(params, timestamp);
			}

			/* コミット */
			txManager.commit(ts);
		}catch(Exception e){
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			throw e;
		}
		return  errorType;
	}

	/**
	 * 取込処理
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 */
	private void fetchCucmData(CheckConsistencyUpdateModel params, Timestamp timestamp){

		/* 電話情報の整合確認 */
		boolean updflg = inconsistent(new String[] {params.getCucmDescription(),params.getAppDescription()},
				  new String[] {params.getCucmPhoneButtonName(),params.getAppPhoneButtonName()},
				  new String[] {params.getCucmAddonModule1(),params.getAppAddonModule1()},
				  new String[] {params.getCucmAddonModule2(),params.getAppAddonModule2()});

		/* 電話機情報の更新 */
		if(!updflg){
			phoneMapper.updatePhoneFetchCucm(setPhoneEntity(params,timestamp));
		}

		/* Line情報の整合確認 */
		updflg = inconsistent(new String[] {params.getCucmFwdBusyDestination(),params.getAppFwdBusyDestination()},
							  new String[] {params.getCucmFwdNoansDestination(),params.getAppFwdNoansDestination()},
							  new String[] {params.getCucmUseVmFlg(),params.getAppUseVmFlg()});

		/* Line情報の更新 */
		if(!updflg){
			lineMapper.updateLineFetchCucm(setLineEntity(params, timestamp));
		}

		/* 電話-Line情報の整合確認 */
		updflg = inconsistent(new String[] {params.getCucmExternalPhoneNumber(),params.getAppExternalPhoneNumber()},
							  new String[] {params.getCucmLineTextLabel(),params.getAppLineTextLabel()},
							  new String[] {params.getCucmRingSetting(),params.getAppRingSetting()});

		/* 電話-Line情報の更新 */
		if(!updflg){
			phoneLineMapper.updatePhoneLineFetchCucm(setPhoneLineEntity(params, timestamp));
		}

	}

	/**
	 * ロック処理
	 *
	 * @param params ロックパラメータ
	 * @throws Exception 例外処理
	 */
	private void lockedConduct(CheckConsistencyUpdateModel params) throws Exception{

		Map<String, Object> map = null;

		try{
			/* 電話テーブル */
			if(params.getAppPhoneId().compareTo(new BigDecimal(0)) != 0){
				map = new HashMap<String, Object>();

				/* 電話 */
				map.put("CUCM_PHONE_ID", params.getAppPhoneId());
				locked("CUCM_PHONE", null , map);
			}

			/* Line */
			if(params.getAppLineId().compareTo(new BigDecimal(0)) != 0){
				map = new HashMap<String, Object>();

				/* ライン */
				map.put("CUCM_LINE_ID", params.getAppLineId());
				locked("CUCM_LINE", null , map);
			}

		} catch (ExcludeException e) {}
	}

	/**
	 * 存在チェック
	 *
	 * @param params 更新パラメータ
	 * @return エラータイプ
	 */
	private String existCheck(CheckConsistencyUpdateModel params){

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", params.getAppPhoneId());				// 電話ID
		map.put("lineId", params.getAppLineId());				// ラインID
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);		    // 削除フラグ(削除無し)

		/* 電話機の存在チェック */
		if(appCommonMapper.telExistCheck(map) == 0){
			/* エラーメッセージをセット */

			return Constants.ERROR_TYPE_TEL;
		}

		/* Lineの存在チェック */
		if(appCommonMapper.lineExistCheck(map) == 0){
			/* エラーメッセージをセット */
			return Constants.ERROR_TYPE_LINE;
		}

		/* 電話とLineの存在チェック */
		if(appCommonMapper.telLineExistCheck(map) == 0){
			return Constants.ERROR_TYPE_LINE;
		}

		return "";
	}

	/**
	 * 電話機テーブルモデルに値をセットします
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneEntity setPhoneEntity(CheckConsistencyUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		PhoneEntity entity = new PhoneEntity();

		entity.setCucmPhoneId(params.getAppPhoneId());								// 電話ID
		entity.setDescription(params.getCucmDescription());
		entity.setPhoneButtonTemplateName(params.getCucmPhoneButtonName());		// 電話ボタン
		entity.setAddonModuleName1(params.getCucmAddonModule1());				// 拡張モジュール1設定
		entity.setAddonModuleName2(params.getCucmAddonModule2());				// 拡張モジュール2設定
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

	/**
	 * ラインテーブルモデルに値をセットします。
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntity(CheckConsistencyUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(params.getAppLineId());							// ラインID
		entity.setFwdBusyDestination(params.getCucmFwdBusyDestination());		// 話中転送
		entity.setFwdNoansDestination(params.getCucmFwdNoansDestination());		// 不応答転送
		entity.setUseVmFlg(params.getCucmUseVmFlg());							// VMフラグ
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

	/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntity(CheckConsistencyUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(params.getAppPhoneId());							// 電話ID
		entity.setCucmLineId(params.getAppLineId());							// ラインID
		entity.setIndex(params.getCucmIndex());									// 連番
		entity.setExternalPhoneNumberMask(params.getCucmExternalPhoneNumber());	// ExternalPhoneNumberMask
		entity.setLineTextLabel(params.getCucmLineTextLabel());					// LineTextLabel
		entity.setRingsettingName(params.getCucmRingSetting());					// 鳴動設定
		entity.setLstupdtTmstmp(timestamp);										// 最終更新日

		return entity;
	}

	/**
	 * 不整合性チェック
	 *
	 * @param condition 条件
	 * @return True:整合 False:不整合
	 */
	private boolean inconsistent(String[]... condition){

		boolean flg = true;
		for(String[] cond : condition){
			if(!StringUtils.defaultString(cond[0]).equals(StringUtils.defaultString(cond[1]))){
				flg = false;
				break;
			}
		}
		return flg;
	}
}


/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * TelLumpEditService.java
 *
 * @date 2013/09/02
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.service;

import java.io.File;
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
import jp.co.ksc.util.LogHelpUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.component.ReflectionException;
import jp.co.netmarks.component.ReflectionManager;
import jp.co.netmarks.model.TelLumpEditModel;
import jp.co.netmarks.model.entity.ChargeAssociationEntity;
import jp.co.netmarks.model.entity.LineEntity;
import jp.co.netmarks.model.entity.PhoneEntity;
import jp.co.netmarks.model.entity.PhoneLineEntity;
import jp.co.netmarks.model.entity.VoiceLoggerAssociationEntity;
import jp.co.netmarks.model.form.list.TelLumpEditUpdateForm;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.ChargeAssociationMapper;
import jp.co.netmarks.persistence.LineMapper;
import jp.co.netmarks.persistence.PhoneLineMapper;
import jp.co.netmarks.persistence.PhoneMapper;
import jp.co.netmarks.persistence.TelLumpEditMapper;
import jp.co.netmarks.persistence.VoiceLoggerAssociationMapper;

/**
 * <pre>
 * 電話機一括登録用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/02 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/02
 */
@Service
public class TelLumpEditService extends BaseService {

	private static Log log = LogFactory.getLog(TelLumpEditService.class);

	@Autowired
	private AppCommonMapper appCommonMapper;

	@Autowired
	private TelLumpEditMapper telLumpEditMapper;

	@Autowired
    private Properties properties;

	/** ##### エンティティ関連 ##### */
	@Autowired
	private PhoneMapper phoneMapper;

	@Autowired
	private LineMapper lineMapper;

	@Autowired
	private PhoneLineMapper phoneLineMapper;

	@Autowired
	private VoiceLoggerAssociationMapper voiceLoggerAssociationMapper;

	@Autowired
	private ChargeAssociationMapper chargeAssociationMapper;

	/** ##### コールマネージャプロジェクト ##### */
	@Autowired
	private ReflectionManager reflectionManager;

	/** ##### トランザクション ##### */
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 *
	 * 電話機登録処理
	 *
	 * @param list 登録パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	public Map<String, String> editTel(List<TelLumpEditModel> list) throws Exception {

		Map<String, String> map = new HashMap<String, String>();

		/* ロックファイル取得 */
		File lockFile = new File(properties.getProperty("lock.file.path"));
		/* ロックファイルの存在確認 */
		if(lockFile.exists()){
			/* 「バッチ起動中です。しばらくお待ちください。」メッセージをセット */
			map.put("errorMessage",properties.getProperty("batch.execute.error"));
			return map;
		}

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 更新フラグ */
		boolean sucFlg     = false;
		boolean eroFlg     = false;
		boolean cucmEroFlg = false;

		boolean cucmError  = false;

		/* エラーメッセージセット */
		StringBuffer errorTel   = new StringBuffer();
		StringBuffer cucmErrorTel   = new StringBuffer();
		StringBuffer errorLine  = new StringBuffer();

		/* エラーのエンティティを設定する */
		String errorEntity = "";

		/* 登録対象が0件の場合 */
		if(list.size() < 1) {
			map.put("errorMessage", properties.getProperty("exclusion.error.no.edit.tel"));
			return map;
		}

		/* チェックボックスにチェックがついていた項目分処理を行う。 */
		for(TelLumpEditModel params : list){

			errorEntity = "";
			cucmError  = false;

			/* 反映対象外の場合 */
			if(!params.isChecked()) continue;

			/* 非入力項目値設定 */
			setOtherValue(params);

			/* 更新処理 */
			errorEntity = editTelInfo(params, timestamp);

			if(StringUtils.isEmpty(errorEntity)){
				/* 成功 */
				sucFlg = true;

				try{
					/* CUCM反映 */
					reflectionManager.reflection(params.getTelId().toString(), null, params.getLineId().toString());
				}catch (ReflectionException e){
					/* CUCMエラーフラグ */
					cucmError = true;

					/* スタックトレースを出力 */
					log.error(LogHelpUtil.getStackTrace(e));
				}

			} else {
				/* 登録エラー（DB更新時） */
				if(errorEntity.equals(Constants.ERROR_TYPE_TEL)){
					/* 電話エラー */
					errorTel.append(params.getMacAddress());
					errorTel.append(Constants.MESSAGE_DELIMITER);
				} else if(errorEntity.equals(Constants.ERROR_TYPE_LINE)){
					/* ラインエラー */
					errorLine.append(params.getDirectoryNumber());
					errorLine.append(Constants.MESSAGE_DELIMITER);
				}
				eroFlg = true;
			}

			if(cucmError){
				/* 登録エラー（CUCM反映時） */
				cucmErrorTel.append(params.getMacAddress());
				cucmErrorTel.append(Constants.MESSAGE_DELIMITER);
				cucmEroFlg = true;
			}
		}

		/* 成功が存在した場合 */
		if(sucFlg){
			map.put("sucsessMessage", properties.getProperty("insert.tel.success"));
		}

		String telErrMsg = "";
		String lineErrMsg = "";
		/* エラーが存在した場合（DB更新時） */
		if(eroFlg){
			/* エラーメッセージをセット */
			String errTelStr = errorTel.toString();
			String errLineStr = errorLine.toString();
			/* 電話 */
			if(StringUtils.isNotEmpty(errTelStr)){
				telErrMsg += "【MACアドレス】" + errTelStr.substring(0, errTelStr.length()-1);
				map.put("errorMessage", MessageFormat.format(properties.getProperty("exclusion.error.key.macaddress"),telErrMsg));
			}
			/* ライン */
			if(StringUtils.isNotEmpty(errLineStr)){
				lineErrMsg += "【内線番号】" + errLineStr.substring(0, errLineStr.length()-1);
				map.put("lineErrorMessage", MessageFormat.format(properties.getProperty("exclusion.error.key.directorynumber"),lineErrMsg));
			}
		}

		String cucnErrMsg = "";
		/* エラーが存在した場合（CUCM反映時） */
		if(cucmEroFlg){
			/* エラーメッセージをセット */
			String cucmErrTelStr = cucmErrorTel.toString();
			cucnErrMsg += "MACアドレス[" + cucmErrTelStr.substring(0, cucmErrTelStr.length()-1) + "]";
			map.put("cucmErrorMessage", MessageFormat.format(properties.getProperty("exclusion.error.cucm"),cucnErrMsg));
		}
		return map;
	}

	/**
	 * 電話機とラインの関連情報登録処理
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エラータイプ
	 * @throws Exception 例外処理
	 */
	private String editTelInfo(TelLumpEditModel params, Timestamp timestamp) throws Exception {

		/* エラータイプ */
		String errorType = "";

		/* トランザクションの取得 */
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;

		try{
			/* トランザクションスタート */
			ts = txManager.getTransaction(dtd);

			/* MACアドレス存在チェック */
//			if(macAddressExistCheck(params.getMacAddress())){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("macAddress", params.getMacAddress());
			if(appCommonMapper.macAddressExistCheck(map) == 0){
				/* MACアドレス存在チェックエラーが存在しなかった場合 */

				/* 更新処理 */
				errorType = editTelLineInfo(params,timestamp);
			}else{
				errorType = Constants.ERROR_TYPE_TEL;
			}

			/* コミット */
			txManager.commit(ts);

		}catch(Exception e){
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			throw e;
		}
		return errorType;
	}
	
/**
	 * DB更新処理
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エラータイプ
	 * @throws Exception 例外処理
	 */
	private String editTelLineInfo (TelLumpEditModel params, Timestamp timestamp) throws Exception {
		BigDecimal telId;
		BigDecimal lineId;

		/* 処理分岐判断 */
		String executeDiv = setExecuteDiv(params);

		/* 入力した内線番号が既に存在し、かつ拠点が異なる場合 */
		if(executeDiv.equals(Constants.LINE_EXIST_DEFFER_BRANCH)){
			return Constants.ERROR_TYPE_LINE;
		}

		/** ##### 電話機 ##### */
		/* 電話機情報の登録 */
		phoneMapper.insertPhone(setPhoneEntityInfo(params, timestamp));
		/* 電話機IDの取得 */
		telId = telLumpEditMapper.getTelId(params.getMacAddress());
		/* 電話機IDをモデルにセット */
		params.setTelId(telId);

		/** ##### Line ##### */
		if(executeDiv.equals(Constants.LINE_NO_EXIST)){
			/* 入力した内線番号が存在しない場合 */

			/* 新規ラインIDを設定 */
			lineId = new BigDecimal(sequence(Constants.CUCM_LINE_ID_SEQ));
			/* ラインIDをモデルにセット */
			params.setLineId(lineId);

			/* ライン情報の新規登録 */
			lineMapper.insertLine(setLineEntity(params, timestamp));

			/* 課金情報の新規登録 */
			chargeAssociationMapper.inertChargeAssociation(setChargeAssociationEntity(params, timestamp));

			/* VoiceLoggerAssociationの新規登録 */
			voiceLoggerAssociationMapper.inertVoiceLoggerAssociation(setVoiceLoggerAssociationEntity(params, timestamp));
		}else {
			/* 入力した内線番号が存在する場合 */

			/* ラインロック処理 */
			lockedLine(params);

			/* 削除済みの場合 */
			if(executeDiv.equals(Constants.LINE_EXIST_DELETED)){
				/* ライン情報の復元 */
				lineMapper.updateLineReflect(setLineEntity(params, timestamp));
			}

			/* 課金情報の更新 */
			chargeAssociationMapper.updateChargeAssociation(setChargeAssociationEntity(params, timestamp));

			/* VoiceLoggerAssociationの更新 */
			voiceLoggerAssociationMapper.updateVoiceLoggerAssociation(setVoiceLoggerAssociationEntity(params, timestamp));
		}

		/** ##### 電話機_Line ##### */
		/* 電話-Line情報の登録 */
		phoneLineMapper.insertPhoneLine(setPhoneLineEntityInfo(params, timestamp));

		return "";
	}


	/**
	 * 処理分岐判断処理
	 *
	 * @param params 登録パラメータ
	 * @return 処理区分
	 * @throws Exception 例外処理
	 */
	private String setExecuteDiv(TelLumpEditModel params) throws Exception{
		/* 処理分岐判断 */
		String executeDiv = "";

		/* ライン存在チェック */
		BigDecimal lineId = appCommonMapper.getLineId(params.getDirectoryNumber());;
		String deleted = null;

		/* 入力した内線番号が存在しない場合 */
		if(lineId == null){
			executeDiv = Constants.LINE_NO_EXIST;
			return executeDiv;
		}else{
			params.setLineId(lineId);
		}

		/* LineIDと削除フラグを取得 */
		Map<String, Object> map = telLumpEditMapper.getLineIdDelete(params.getDirectoryNumber());
		deleted = map.get("deleted").toString();

		if(deleted.equals(Constants.COM_FLG_ON)){
			/* 削除フラグが立っている場合 */
			executeDiv = Constants.LINE_EXIST_DELETED;
		} else if(branchCheck(params.getBranchTelId(),lineId.toString())) {
			/* シェアードするラインが同一拠点の場合 */
			executeDiv = Constants.LINE_EXIST_SAME_BRANCH;
		}else {
			/* シェアードするラインが同一拠点でない場合 */
			executeDiv = Constants.LINE_EXIST_DEFFER_BRANCH;
		}
		return executeDiv;
	}

	/**
	 * 非入力項目値設定処理
	 *
	 * @param params 登録パラメータ
	 * @throws Exception 例外処理
	 */
	private void setOtherValue(TelLumpEditModel params) throws Exception{

		String lineText = null;
		String external = null;

		/* 拠点コードを取得 */
		String branchId = params.getBranchTelId();
		/* 内線番号を取得 */
		String directoryNumber = params.getDirectoryNumber();
		/* ダイアルインを取得 */
		String dialinNumber = params.getDialinNumber();

		/** ##### LocationName ##### */
		/* LocationNameをセット */
		params.setLocationName(branchId);

		/** ##### LineTextLabel ##### */
		/* LineTextLabelの設定 */
		lineText = Constants.LINE_TEXT_LABEL_FIRST_NUMBER
				+Constants.LINE_TEXT_LABEL_HYPHEN + branchId.substring(0, 3)
				+Constants.LINE_TEXT_LABEL_HYPHEN + directoryNumber.substring(directoryNumber.length() - 4);
		/* LineTextLabelをセット */
		params.setLineTextLabel(lineText);

		/** ##### ExternalPhoneNumber ##### */
		/* ExternalPhoneNumberの設定 */
		if(StringUtils.isEmpty(dialinNumber)){
			/* ExternalPhoneNumberの設定（アスタリスク10桁） */
			external = Constants.EXTERNAL_PHONE_NUMBER_NO_DIALIN;
		} else {
			/* ExternalPhoneNumberの設定（ダイアルイン設定値） */
			external = dialinNumber;
		}
		/* ExternalPhoneNumberをセット */
		params.setExternalPhoneNumber(external);
	}

	/**
	 * 電話機テーブルモデルに値をセットします
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneEntity setPhoneEntityInfo(TelLumpEditModel params, Timestamp timestamp){
		/* インスタンス化 */
		PhoneEntity entity = new PhoneEntity();

		/* 電話機ID取得 */
		BigDecimal phoneId = new BigDecimal(sequence(Constants.CUCM_PHONE_ID_SEQ));

		entity.setCucmPhoneId(phoneId);											// 電話ID
		entity.setPhoneProductName(params.getTelTypeModel());					// 電話機種
		entity.setMacAddress(params.getMacAddress());							// MacAddress
		entity.setDescription(params.getDirectoryNumber());						// 説明
		entity.setCallingSearchSpaceName(params.getCssName());					// CallingSearchSpaceName
		entity.setLocationName(params.getLocationName());						// ロケーション名
		entity.setDevicePoolName(params.getDevicePoolName());					// デバイスプール名
		entity.setPhoneButtonTemplateName(params.getPhoneButtonTemplete());		// 電話ボタン
		entity.setCompanyId(params.getCompanyTelId());							// 会社ID
		entity.setSectionId(params.getSectionTelId());							// 店部課ID
		entity.setBranchId(params.getBranchTelId());							// 拠点ID
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);		// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);								// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);								// エラーフラグ
		entity.setCrtTmstmp(timestamp);											// 作成日時
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

	/**
	 * ラインテーブルモデルに値をセットします。
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntity(TelLumpEditModel params, Timestamp timestamp){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(params.getLineId());								// ラインID
		entity.setDirectoryNumber(params.getDirectoryNumber());					// 内線番号
		entity.setFwdAllCss(params.getCssName());								// FwdAllCss
		entity.setFwdBusyCss(params.getCssName());								// FwdBusyCss
		entity.setFwdNoansCss(params.getCssName());								// FwdNoansCss
		entity.setUseVmFlg(Constants.LOGGER_DATA_OFF);							// VMフラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);		// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);								// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);								// エラーフラグ
		entity.setCrtTmstmp(timestamp);											// 作成日時
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntityInfo(TelLumpEditModel params , Timestamp timestamp){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(params.getTelId());							// 電話機ID
		entity.setCucmLineId(params.getLineId());							// ラインID
		entity.setIndex(Constants.TEL_LINE_INDEX_1);						// 連番
		entity.setExternalPhoneNumberMask(params.getExternalPhoneNumber());	// ExternalPhoneNumberMask
		entity.setDialin(params.getDialinNumber());							// ダイアルイン
		entity.setLineTextLabel(params.getLineTextLabel());					// LineTextLabel
		entity.setRingsettingName(Constants.RING_SETTING_DEFAULT);			// 鳴動設定
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);							// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);							// エラーフラグ
		entity.setCrtTmstmp(timestamp);										// 作成日時
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}

	/**
	 * 課金関連モデルに値をセットします。
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private ChargeAssociationEntity setChargeAssociationEntity(TelLumpEditModel params, Timestamp timestamp){

		/* インスタンス化 */
		ChargeAssociationEntity entity = new ChargeAssociationEntity();

		entity.setCucmLineId(params.getLineId());									// ラインID
		//entity.setStatusCode(Constants.COM_FLG_OFF);								// ステータス
		entity.setBranchId(params.getChargeAssociationBranchId());					// 拠点ID
		entity.setCompanyId(params.getCompanyTelId());								// 会社ID
		entity.setParentSectionId(params.getChargeAssociationParentSectionId());	// 親店部課ID
		entity.setSectionId(params.getChargeAssociationSectionId());				// 店部課ID
		entity.setDeleted(Constants.COM_FLG_OFF);									// 削除フラグ
		entity.setCrtTmstmp(timestamp);												// 作成日時
		entity.setLstupdtTmstmp(timestamp);											// 更新日時

		return entity;
	}

	/**
	 * VoiceLoggerAssociationモデルに値をセットします。
	 *
	 * @param params 登録パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private VoiceLoggerAssociationEntity setVoiceLoggerAssociationEntity(
			TelLumpEditModel params, Timestamp timestamp){

		/* インスタンス化 */
		VoiceLoggerAssociationEntity entity = new VoiceLoggerAssociationEntity();

		entity.setCucmLineId(params.getLineId());									// ラインID
		entity.setLoggerData(params.getLoggerData());								// LoggerData
		entity.setDeleted(Constants.COM_FLG_OFF);									// 削除フラグ
		entity.setCrtTmstmp(timestamp);												// 作成日時
		entity.setLstupdtTmstmp(timestamp);											// 更新日時

		return entity;
	}

	/**
	 * CallingSearchSpace存在チェック
	 *
	 * @param form フォームデータ
	 * @return 存在チェック結果
	 */
	public boolean cssExistCheck(TelLumpEditUpdateForm form) {
		Map<String, Object> map = new HashMap<String, Object>();
		String css = null;

		/* 電話機の拠点IDとダイアルインを取得する */
		String branchId = form.getBranchTelId();
		String dialIn = form.getDialinNumber();

		/* 電話機の店部課IDと会社IDを取得する */
		String[] comSec = form.getCompanySectionTelId().split(",");
		String companyId = comSec[0];
		String sectionId = comSec[1];

		map.put("companyTelId", companyId);
		map.put("sectionTelId", sectionId);
		/* 親店部課コードを取得 */
		String parentSectionId = telLumpEditMapper.getParentSectionId(map);

		/* CallingSearchSpaceNameの設定 */
		if(StringUtils.isEmpty(dialIn)){
			css = branchId + "_" + parentSectionId + "_" + Constants.CALLING_SEARCH_SPACE_NAME_NO_DIALIN_DOMESTIC;
		} else {
			css = branchId + "_" + parentSectionId + "_" + Constants.CALLING_SEARCH_SPACE_NAME_DIALIN_DOMESTIC;
		}

		/* CallingSearchSpaceNameの存在チェック */
		map.put("cssName", css);
		map.put("branchId", branchId);

		if(appCommonMapper.callingSearchSpaceExistCheck(map) < 1){
			return false;
		}

		/* 店部課ID、会社ID、CallingSearchSpaceNameをセット */
		form.setCompanyTelId(companyId);
		form.setSectionTelId(sectionId);
		form.setCssName(css);
		return true;

	}

	/**
	 * DevicePool存在チェック
	 *
	 * @param form フォームデータ
	 * @return 存在チェック結果
	 */
	public boolean devicePoolExistCheck(TelLumpEditUpdateForm form) {
		String devicepool = null;

		Map<String, Object> map = new HashMap<String, Object>();

		/* 電話機種名を取得 */
		String telTypeModel = form.getTelTypeModel();

		/* DevicePoolNameの設定 */
		devicepool = form.getBranchTelId() + "_" + telTypeModel.substring(telTypeModel.length() - 4);

		/* DevicePoolNameの存在チェック */
		map.put("devicePoolName", devicepool);
		map.put("branchId", form.getBranchTelId());
		if(appCommonMapper.devicePoolExistCheck(map) < 1){
			return false;
		}

		/* DevicePoolNameのセット */
		form.setDevicePoolName(devicepool);
		return true;

	}

	/**
	 * シェアードラインの拠点整合チェック
	 *
	 * @param branchId 拠点ID
	 * @param lineId ラインID
	 * @return 整合チェック結果
	 * @throws Exception 例外処理
	 */
	public boolean branchCheck(String branchId,String lineId) throws Exception{
		/* 紐付いている電話機の拠点コード取得 */
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("lineId", new BigDecimal(lineId));
		paramMap.put("deleted", Constants.COM_FLG_OFF);
		String[] sharedBranch = appCommonMapper.getSharedLineBranch(paramMap);

		if(sharedBranch.length == 0){
			return true;
		}

		return (branchId.equals(sharedBranch[0]))? true:false;
	}

	/**
	 * ラインロック処理
	 * @param params ロックパラメータ
	 * @throws Exception 例外処理
	 */
	private void lockedLine(TelLumpEditModel params) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			/* ロック */
			map.put("CUCM_LINE_ID", params.getLineId());
			locked("CUCM_LINE", null , map);

		}catch(ExcludeException e){

		}
	}
}	
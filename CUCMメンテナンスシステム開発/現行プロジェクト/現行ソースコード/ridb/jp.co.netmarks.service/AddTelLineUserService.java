/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelLineUserService.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.AddTelLineUserUpdateModel;
import jp.co.netmarks.model.entity.ChargeAssociationEntity;
import jp.co.netmarks.model.entity.LineEntity;
import jp.co.netmarks.model.entity.PhoneEntity;
import jp.co.netmarks.model.entity.PhoneLineEntity;
import jp.co.netmarks.model.entity.TelDirAssociationEntity;
import jp.co.netmarks.model.entity.UserEntity;
import jp.co.netmarks.model.entity.UserPhoneEntity;
import jp.co.netmarks.model.entity.UserSectionEntity;
import jp.co.netmarks.model.entity.VoiceLoggerAssociationEntity;
import jp.co.netmarks.persistence.AddUserMapper;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.ChargeAssociationMapper;
import jp.co.netmarks.persistence.LineMapper;
import jp.co.netmarks.persistence.PhoneLineMapper;
import jp.co.netmarks.persistence.PhoneMapper;
import jp.co.netmarks.persistence.TelDirAssociationMapper;
import jp.co.netmarks.persistence.UnityAssociationMapper;
import jp.co.netmarks.persistence.UserMapper;
import jp.co.netmarks.persistence.UserPhoneMapper;
import jp.co.netmarks.persistence.UserSectionMapper;
import jp.co.netmarks.persistence.VoiceLoggerAssociationMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 電話機、ライン、ユーザー紐付き更新用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
@Service
public class AddTelLineUserService extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AddTelLineUserService.class);

	@Autowired
    private Properties properties;

	@Autowired
	private AppCommonMapper appCommonMapper;

	@Autowired
	private AddUserMapper addUserMapper;

	/** ##### エンティティ関連 ##### */

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserPhoneMapper userPhoneMapper;

	@Autowired
	private UserSectionMapper userSectionMapper;

	@Autowired
	private TelDirAssociationMapper telDirAssociationMapper;

	@Autowired
	private PhoneLineMapper phoneLineMapper;

	@Autowired
	private LineMapper lineMapper;

	@Autowired
	private VoiceLoggerAssociationMapper voiceLoggerAssociationMapper;

	@Autowired
	private ChargeAssociationMapper chargeAssociationMapper;

	@Autowired
	private UnityAssociationMapper unityAssociationMapper;
	
	@Autowired
	private PhoneMapper phoneMapper;


	/**
	 * 電話機変更処理
	 *
	 * @param params 電話機変更パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> updateTel(AddTelLineUserUpdateModel params) throws Exception{

		return userTelEdit(params,true);
	}

	/**
	 * 電話機追加
	 *
	 * @param params 電話機追加パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public  Map<String, String> addTel(AddTelLineUserUpdateModel params) throws Exception{

		return userTelEdit(params,false);
	}

	/**
	 * ユーザー変更処理
	 *
	 * @param params ユーザー変更パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> updateUser(AddTelLineUserUpdateModel params) throws Exception{

		return userTelEdit(params,true);
	}

	/**
	 * ユーザー追加
	 *
	 * @param params ユーザ追加パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public  Map<String, String> addUser(AddTelLineUserUpdateModel params) throws Exception{

		return userTelEdit(params,false);
	}

	/**
	 * ライン変更処理
	 *
	 * @param params ライン変更パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> updateLine(AddTelLineUserUpdateModel params) throws Exception{

		return telLineEdit(params,true);
	}

	/**
	 * ライン追加処理
	 *
	 * @param params ライン追加パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> addLine(AddTelLineUserUpdateModel params) throws Exception{

		return telLineEdit(params,false);
	}

	/**
	 * ラインの新規追加処理
	 *
	 * @param params ライン新規パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> addNewLine(AddTelLineUserUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedConduct(params);

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 存在チェック（排他チェック） */
		if(!telLineExisisCheck(params , false)) {
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/* 内線番号に該当するラインIDを取得する */
		BigDecimal lineId = appCommonMapper.getLineId(params.getDirectoryNumber());

		if(lineId != null && lineId.compareTo(new BigDecimal(0)) != 0){
			/* ラインが存在した場合 */
			/* 取得した値をセット */
			params.setLineId(lineId);

			/* ライン情報の更新を行う */
			lineMapper.updateLineVmFlg(setLineEntity(params, timestamp));

			/* 課金関連の更新を行う */
			chargeAssociationMapper.updateChargeAssociationPlace(
					setChargeAssociationEntity(params, timestamp));

			/* 通録関連の更新 */
			voiceLoggerAssociationMapper.updateVoiceLoggerAssociationDelete(
					setVoiceLoggerAssociationEntityDeleteInfo(
							params.getLineId(), timestamp, Constants.COM_FLG_OFF));

		} else {
			/* ラインが存在しなかった場合 */
			/* lineIdの取得 */
			lineId = new BigDecimal(sequence(Constants.CUCM_LINE_ID_SEQ));
			/* 取得した値をセット */
			params.setLineId(lineId);

			/* ライン情報の登録を行う */
			lineMapper.insertLine(setLineEntity(params, timestamp));

			/* 課金情報の登録を行う */
			chargeAssociationMapper.inertChargeAssociation(
					setChargeAssociationEntity(params, timestamp));

			/* VOICE_LOGGER_ASSOCIATIONの登録を行う */
			voiceLoggerAssociationMapper.inertVoiceLoggerAssociation(
					setVoiceLoggerAssociationEntity(params, lineId, timestamp));
		}

		/* 電話とラインの紐付け関連の更新 */
		lineTelUpdateEdit(params, timestamp, true);

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("update.success"));

		return map;
	}
/**
	 * ユーザーと電話機の紐付け処理
	 *
	 * @param params 紐付け情報
	 * @param updFlg 更新状況フラグ True：変更 False:追加
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	private Map<String, String> userTelEdit(AddTelLineUserUpdateModel params, Boolean updFlg) throws Exception{

		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedConduct(params);

		/* 存在チェック（排他チェック） */
		if(!userTelExisisCheck(params , updFlg)) {
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/** ##### ユーザーと電話機の紐付け情報の更新 ##### **/

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* ########## 変更前データの更新処理 ########## */
		if(updFlg && params.getOrgUserId().compareTo(new BigDecimal(0)) !=  0 &&
					 params.getOrgTelId().compareTo(new BigDecimal(0)) !=  0){
			/* 電話とユーザーの紐付きを論理削除にする【変更前情報】 */
			userPhoneMapper.updateUserPhoneDelete(
					setUserTelEntityDeleteInfo(
					params.getOrgUserId(), params.getOrgTelId(), timestamp, Constants.COM_FLG_ON));

			/* TelDirDataの削除フラグを立てる */
			telDirAssociationMapper.updateTelDirDataDelete(
					setTeldirAssociationEntityDeleteInfo(
							params.getOrgTelId(), null, params.getOrgUserId(), timestamp, Constants.COM_FLG_ON));

			/* 削除予約フラグ更新処理  */
			updateDeleteReserve(params.getOrgUserId(), timestamp);

			/* 内線番号更新処理 */
			updateTelNumber(params.getOrgUserId(), timestamp);

			/* 電話機に紐づくユーザー情報の更新 */
			updateTelOwnerUser(params.getOrgTelId(), timestamp);

		}

		/* ########## 変更後データの更新処理 ########## */
		/* ユーザーと電話機の紐付きデータの削除フラグを取得 */
		String userTelDelFlg = addUserMapper.getUserTelDeleteFlg(params);

		if(StringUtils.isEmpty(userTelDelFlg)){
			/* データが存在しなかった場合 */

			/* ユーザーと電話機の紐付きを作成します。 */
			userPhoneMapper.insertUserPhone(
					setUserPhoneEntity(params, timestamp));
		} else{
			/* 存在した場合 */

			/* ユーザーと電話機の紐付き情報を更新する */
			userPhoneMapper.updateUserPhone(
					setUserPhoneEntity(params, timestamp));
		}

		/* TelDirDataの存在チェックを行います */
		Map<String, Object> telDirMap = new HashMap<String, Object>();
		telDirMap.put("telId", params.getTelId());		// 電話ID
		telDirMap.put("userId", params.getUserId());	// ユーザーID
		telDirMap.put("deleted", Constants.COM_FLG_OFF);
		if(appCommonMapper.telDirAssociationExistEntryTelUserCheck(telDirMap) > 0){
			/* 存在しない場合は、新規登録を行う */

			/* 登録処理 */
			telDirAssociationMapper.insertTelDirDataTelLine(
					setTeldirAssociationEntity(params, timestamp));
		}

		/* TelDirDataの削除データ存在チェックを行います */
		telDirMap.put("deleted", Constants.COM_FLG_ON);
		if(appCommonMapper.telDirAssociationExistCheck(telDirMap) > 0){
			/* 存在する場合は、削除フラグの更新を行う */
			telDirAssociationMapper.updateTelDirDataUserTelDelete(
					setTeldirAssociationEntityDeleteInfo(
							params.getTelId(), null, params.getUserId(), timestamp, Constants.COM_FLG_OFF));
		}

		/* 変更後の削除予約フラグ更新処理  */
		updateDeleteReserve(params.getUserId(), timestamp);

		/* 内線番号更新処理【変更後情報】 */
		updateTelNumber(params.getUserId(), timestamp);

		/* 電話機に紐づくユーザー情報の更新 */
		updateTelOwnerUser(params.getTelId(), timestamp);

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("update.success"));

		return map;
	}

	/**
	 * 電話-ライン紐付け更新処理
	 *
	 * @param params 紐付け情報
	 * @param updFlg true：ライン変更  false：ライン追加
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	private Map<String, String> telLineEdit(AddTelLineUserUpdateModel params, Boolean updFlg) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();
		/* SQL用マップ */
		Map<String, Object> sqlMap = new HashMap<String, Object>();

		/* ロック処理 */
		lockedConduct(params);

		/* 存在チェック（排他チェック） */
		if(!telLineExisisCheck(params , updFlg)) {
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/** ##### 電話機とラインの紐付け情報の更新 ##### **/

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* ########## 変更前データの更新処理 ########## */
		if(updFlg){
			/* 紐付けを論理削除 */
			phoneLineMapper.updatePhoneLineDelete(
					setPhoneLineEntity(
							params.getOrgTelId(),params.getOrgLineId(),
							params.getOrgLineIndex(), null,timestamp, Constants.COM_FLG_ON));

			/* 電子電話帳関連の削除 */
			telDirAssociationMapper.updateTelDirDataDelete(
					setTeldirAssociationEntityDeleteInfo(
							params.getOrgTelId(), params.getOrgLineId(),
							null, timestamp, Constants.COM_FLG_ON));

			/* 削除したラインの紐付きチェック */
			sqlMap.put("lineId", params.getOrgLineId());
			sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
			/* 紐付きが存在しない場合 */
			if(appCommonMapper.telLineExistCheck(sqlMap) == 0){
				/* ラインとライン関連の削除を行う */

				/* ラインの削除 */
				lineMapper.updateLineDelete(
						setLineEntityDeleteInfo(params.getOrgLineId(), timestamp, Constants.COM_FLG_ON));

				/* 通録関連の削除 */
				voiceLoggerAssociationMapper.updateVoiceLoggerAssociationDelete(
						setVoiceLoggerAssociationEntityDeleteInfo(
								params.getOrgLineId(), timestamp, Constants.COM_FLG_ON));

				/* 課金関連の削除 */
				chargeAssociationMapper.updateChargeAssociationDelete(
						setChargeAssociationEntityDeleteInfo(
								params.getOrgLineId(), timestamp, Constants.COM_FLG_ON));
			} else {
				
				/* CSSの更新 */
				lineCssUpdate(params.getOrgLineId(),timestamp);
			}
		}

		/* ########## 変更後データの更新処理 ########## */
		lineTelUpdateEdit(params, timestamp, false);

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("update.success"));

		return map;
	}

	/**
	 * ライン追加、変更、新規登録時の変更後データの更新
	 *
	 * @param params 更新値
	 * @param timestamp タイムスタンプ
	 * @param newLineFlg true:新規ライン追加 false:ライン変更、追加
	 */
	private void lineTelUpdateEdit(AddTelLineUserUpdateModel params, Timestamp timestamp, boolean newLineFlg){

		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap = new HashMap<String, Object>();
		sqlMap.put("telId", params.getTelId());
		sqlMap.put("lineId", params.getLineId());
		sqlMap.put("lineIndex", params.getLineIndex());
		sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_ON);
		/* 削除データの有無を確認する */
		if(appCommonMapper.telLineExistCheck(sqlMap) == 0){
			/* 削除データが存在しない場合 */
			/* ラインと電話機のヒモ付を作成する */
			phoneLineMapper.insertPhoneLine(
					setPhoneLineEntity(
							params.getTelId(), params.getLineId(),
							params.getLineIndex(), params.getDialinNumber(), timestamp, Constants.COM_FLG_OFF));
		} else if(!newLineFlg){
			/* 削除データが存在する場合[ライン変更・追加] */
			/* ラインと電話機のヒモ付を復活させる */
			phoneLineMapper.updatePhoneLineDelete(
					setPhoneLineEntity(
							params.getTelId(),params.getLineId(),
							params.getLineIndex(), null, timestamp, Constants.COM_FLG_OFF));
		} else if(newLineFlg){
			/* 削除データが存在する場合[新規ライン追加] */
			/* ラインと電話機のヒモ付を復活させる */
			phoneLineMapper.updatePhoneLineDeleteDialin(
					setPhoneLineEntity(
							params.getTelId(),params.getLineId(),
							params.getLineIndex(), params.getDialinNumber(), timestamp, Constants.COM_FLG_OFF));
		}

		/* ユーザーと紐付いていた場合 */
		if(params.getOrgUserId().compareTo(new BigDecimal(0)) != 0){
			/* TelDirDataの存在チェックを行います */
			sqlMap.put("deleted", Constants.COM_FLG_OFF);
			if(appCommonMapper.telDirAssociationExistEntryTelLineCheck(sqlMap) > 0){
				/* 存在しない場合は、新規登録を行う */

				/* 登録処理 */
				telDirAssociationMapper.insertTelDirDataTelUser(
						setTeldirAssociationEntity(params, timestamp));
			}

			/* TelDirDataの削除データ存在チェックを行います */
			sqlMap.put("deleted", Constants.COM_FLG_ON);
			if(appCommonMapper.telDirAssociationExistCheck(sqlMap) > 0){
				/* 存在する場合は、削除フラグの更新を行う */
				telDirAssociationMapper.updateTelDirDataTelLineDelete(
						setTeldirAssociationEntityDeleteInfo(
								params.getTelId(), params.getLineId(), null, timestamp, Constants.COM_FLG_OFF));
			}
		}
		
		/* CSSの更新 */
		lineCssUpdate(params.getLineId(),timestamp);
	}

/**
	 * 削除予約フラグ更新処理
	 * 
	 * @param userId ユーザーID
	 * @param timestamp タイムスタンプ
	 */
	private void updateDeleteReserve(BigDecimal userId,Timestamp timestamp){

		/* 変数の定義 */
		UserPhoneEntity userPhoneEntity = new UserPhoneEntity();
		Map<String, Object> editMap = new HashMap<String, Object>();

		/* ユーザー削除予約フラグが立っているか確認する */
		editMap.put("userId", userId);
		editMap.put("COM_FLG_ON", Constants.COM_FLG_ON);
		if(appCommonMapper.userSectionDeleteReserveExistCheck(editMap) > 0){
			
			/* オーナーユーザーIDの更新処理 */
			editMap = new HashMap<String, Object>();
			editMap.put("appUserId", userId);
			editMap.put("cucmUpdateRequestFlag", Constants.CUCM_UPDATE_FLG_WAIT_2);
			editMap.put("lstupdtTmstmp", timestamp);
			editMap.put("ENABLED_SHARED_USE_PRIVATE", Constants.ENABLED_SHARED_USE_PRIVATE);
			editMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
			editMap.put("COM_FLG_ON", Constants.COM_FLG_ON);
			phoneMapper.updatePhoneOwnerUserReserveDelete(editMap);
			
			/* TelDirの削除対応 */
			telDirAssociationMapper.updateTelDirDataReserveDelete(
					setTeldirAssociationEntityDeleteInfo(null, null, userId, timestamp, Constants.COM_FLG_ON));

			/* ユーザーと電話機の紐付き項目を削除します */
			userPhoneEntity.setAppUserId(userId);
			userPhoneEntity.setLstupdtTmstmp(timestamp);
			userPhoneMapper.updateUserPhoneDeleteReserve(userPhoneEntity);
			
			/* ユーザー削除予約フラグが立っている項目に削除フラグを立てる */
			userSectionMapper.updateUserSectionDeleteReserve(
					setUserSectionDeleteReserveInfo(userId, timestamp));
		}
	}

	/**
	 * 内線番号更新処理
	 *
	 * @param userId ユーザーID
	 * @param timestamp タイムスタンプ
	 */
	private void updateTelNumber(BigDecimal userId, Timestamp timestamp){

		Map<String, Object> map = new HashMap<String, Object>();

		/* ユーザーに紐づく電話機の内線番号を取得 */
		map.put("userId", userId);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		String directoryNumber = appCommonMapper.getTelephoneNumber(map);

		/* ユーザーテーブルのTELEPHONENUMBERと更新フラグを更新 */
		userMapper.updateTelephoneNumber(setUserEntityTelephoneNumberInfo(userId, directoryNumber, timestamp));
	}
	
	/**
	 * 電話機に該当するユーザーの更新（オーナーユーザー更新）
	 * @param telId 電話ID
	 * @param timestamp タイムスタンプ
	 */
	private void updateTelOwnerUser(BigDecimal telId,Timestamp timestamp){
		
		/* インスタンス化 */
		PhoneEntity entity = new PhoneEntity();
		entity.setCucmPhoneId(telId);
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);
		entity.setLstupdtTmstmp(timestamp);
		
		phoneMapper.updatePhoneOwnerUser(entity);
	}

	/**
	 * ラインのCSS項目の更新を行います。
	 *
	 * @param lineId ラインID
	 * @param timestamp タイムスタンプ
	 */
	private void lineCssUpdate(BigDecimal lineId, Timestamp timestamp){
		Map<String, Object> lineMap = new HashMap<String, Object>();
		lineMap.put("cucmLineId", lineId);
		lineMap.put("cucmUpdateRequestFlag",Constants.CUCM_UPDATE_FLG_WAIT_2);
		lineMap.put("lstupdtTmstmp",timestamp);
		lineMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		lineMapper.updateLineCssLine(lineMap);
	}

	/**
	 * ロック処理
	 * ※ロック対象は「電話テーブル」「ユーザーテーブル」「Lineテーブル」の３つになります。
	 * ※変更前のデータと変更後のデータに該当する行のロックを行います。
	 *
	 * @param params ロック対象パラメータ
	 * @throws Exception エクセプション
	 */
	private void lockedConduct(AddTelLineUserUpdateModel params) throws Exception{

		Map<String, Object> map = null;

		try{

			/* 電話機テーブル */
			if(params.getTelId().compareTo(new BigDecimal(0)) != 0){
				/* 電話（新規） */
				map = new HashMap<String, Object>();
				map.put("CUCM_PHONE_ID", params.getTelId());
				locked("CUCM_PHONE", null , map);

				if(params.getOrgTelId().compareTo(new BigDecimal(0)) != 0  &&
				   params.getTelId().compareTo(params.getOrgTelId()) != 0){

					/* 電話（変更前） */
					map.put("CUCM_PHONE_ID", params.getOrgTelId());
					locked("CUCM_PHONE", null , map);
				}
			}

			/* ラインテーブル */
			if(params.getLineId().compareTo(new BigDecimal(0)) != 0){
				/* ライン（新規） */
				map = new HashMap<String, Object>();
				map.put("CUCM_LINE_ID", params.getLineId());
				locked("CUCM_LINE", null , map);

				if(params.getOrgLineId().compareTo(new BigDecimal(0)) != 0  &&
				   params.getLineId().compareTo(params.getOrgLineId()) != 0){

					/* ユーザー（変更前） */
					map.put("CUCM_LINE_ID", params.getOrgUserId());
					locked("CUCM_LINE", null , map);
				}
			} else if(params.getOrgLineId().compareTo(new BigDecimal(0)) != 0){
				/* ユーザー（変更前） */
				map = new HashMap<String, Object>();
				map.put("CUCM_LINE_ID", params.getOrgUserId());
				locked("CUCM_LINE", null , map);
			}

			/* ユーザーテーブル */
			if(params.getUserId().compareTo(new BigDecimal(0)) != 0){
				/* ユーザー（新規） */
				map = new HashMap<String, Object>();
				map.put("APP_USER_ID", params.getUserId());
				locked("APP_USER", null , map);

				if(params.getOrgUserId().compareTo(new BigDecimal(0)) != 0  &&
				   params.getUserId().compareTo(params.getOrgUserId()) != 0){

					/* ユーザー（変更前） */
					map.put("APP_USER_ID", params.getOrgUserId());
					locked("APP_USER", null , map);
				}
			}
		} catch (ExcludeException e) {}
	}

	/**
	 * ユーザー×電話存在チェック
	 *
	 * @param params チェックパラメータ
	 * @return True:エラー無し  False:エラー有り
	 */
	private Boolean userTelExisisCheck(AddTelLineUserUpdateModel params, boolean updFlg){

		/* パラメーターのセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", params.getTelId());
		map.put("userId", params.getUserId());
		map.put("sectionId", params.getSectionUserId());
		map.put("companyId", params.getCompanyUserId());
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);

		/* 電話機 */
		if(params.getTelId().compareTo(new BigDecimal(0)) != 0 &&
		   params.getTelId() != null){
			/* 電話機の存在チェック */
			if(appCommonMapper.telExistCheck(map) == 0) return false;
		}

		/* ユーザー */
		if(params.getUserId().compareTo(new BigDecimal(0)) != 0 &&
		   params.getUserId() != null){
			/* ユーザーの存在チェック */
			if(appCommonMapper.userExistCheck(map) == 0 ) return false;

			/* ユーザーと店部課の紐付き情報の存在チェック */
			if(appCommonMapper.userSectionExistCheck(map) == 0) return false;
		}

		/* ユーザー追加、電話機追加の場合 */
		if(!updFlg){
			/* ユーザーと電話機の紐付き存在チェック 
			 * ユーザー追加：選択したユーザーと選択元の電話機
			 * 電話機追加　：選択元のユーザーと選択した電話機 */
			if(appCommonMapper.userTelExistCheck(map) > 0) return false;
		}
		
		/* 変更元のユーザーと電話機が設定されていた場合 */
		if((params.getOrgUserId().compareTo(new BigDecimal(0)) != 0 &&
			params.getOrgUserId() != null) && 
		   (params.getOrgTelId().compareTo(new BigDecimal(0)) != 0 &&
			params.getOrgTelId()  != null)){
			
			/* 変更元のユーザーと電話機の紐付き情報の存在チェック */
			map.put("telId", params.getOrgTelId());
			map.put("userId", params.getOrgUserId());
			map.put("sectionId", params.getOrgSectionUserId());
			map.put("companyId", params.getOrgCompanyUserId());
			if(appCommonMapper.userTelSectionExistCheck(map) == 0) return false;
		} else if ((params.getOrgTelId().compareTo(new BigDecimal(0)) != 0 &&
				    params.getOrgTelId()  != null)){
			/* 変更元の共用電話チェック */
			map.put("telId", params.getOrgTelId());
			map.put("sharedUse", Constants.ENABLED_SHARED_USE_SHARE);
			if(appCommonMapper.telSharedUseCheck(map) > 0) return false;
		}
		
		/* 電話機変更・追加の場合、変更・追加する電話機が共用電話に設定されていないかチェック */
		if(params.getTelId().compareTo(params.getOrgTelId()) != 0){
			/* 共用電話機に設定されていた場合 */
			map.put("telId", params.getTelId());
			map.put("sharedUse", Constants.ENABLED_SHARED_USE_SHARE);
			if(appCommonMapper.telSharedUseCheck(map) > 0) return false;
		}

		return true;
	}

/**
	 * 電話×ライン存在チェック
	 *
	 * @param params 画面データ
	 * @param updFlg True:ライン変更 False:ライン追加、新規
	 * @return True:エラー無し  False:エラー有り
	 */
	private Boolean telLineExisisCheck(AddTelLineUserUpdateModel params, boolean updFlg){

		/* パラメーターのセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", params.getTelId());
		map.put("lineId", params.getLineId());
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);

		/* 電話機の存在チェック */
		if(appCommonMapper.telExistCheck(map) == 0) return false;

		/* ラインの存在チェック */
		if(params.getLineId().compareTo(new BigDecimal(0)) != 0){
			/* ライン変更、ライン追加の場合 */
			if(appCommonMapper.lineExistCheck(map) == 0) return false;

			/* 電話ラインの存在チェック */
			if(appCommonMapper.telLineExistCheck(map) > 0) return false;

		} else {
			/* 新規ライン作成の場合 */
			
			/* 内線番号のセット */
			map.put("directoryNumber", params.getDialinNumber());
			/* 内線番号の不在チェック */
			if(appCommonMapper.directoryNumberExistCheck(map) >  0)return false;
		}

		/* ラインの連番存在チェック */
		String orgLineIndex = null;
		/* ライン変更の場合は変更元の連番以外の存在チェックを行う */
		if(updFlg) orgLineIndex = params.getOrgLineIndex();
		map.put("orgLineIndex", orgLineIndex);
		map.put("lineIndex", params.getLineIndex());
		if(appCommonMapper.lineIndexExistCheck(map) > 0) return false;

		return true;
	}

	/**
	 * Userテーブルモデルに値をセットします
	 * ※電話番号の値をセット
	 *
	 * @param userId ユーザーID
	 * @param directoryNumber 内線番号
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserEntity setUserEntityTelephoneNumberInfo(BigDecimal userId, String directoryNumber, Timestamp timestamp){
		/* インスタン化 */
		UserEntity entity = new UserEntity();

		entity.setAppUserId(userId);					// ユーザーID
		entity.setTelephoneNumber(directoryNumber);		// 電話番号
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);				// 最終更新日

		return entity;
	}

	/**
	 * User_Phoneテーブルモデルに値をセットします。
	 * ※変更後の紐付け情報を登録するための値をセットします。
	 * 
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserPhoneEntity setUserPhoneEntity(AddTelLineUserUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		UserPhoneEntity entity = new UserPhoneEntity();

		entity.setAppUserId(params.getUserId());				// ユーザーID
		entity.setCucmPhoneId(params.getTelId());				// 電話ID
		entity.setCompanyId(params.getCompanyUserId());			// 会社ID（ユーザー）
		entity.setSectionId(params.getSectionUserId());			// 店部課ID（ユーザー）
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);				// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);				// エラーフラグ
		entity.setCrtTmstmp(timestamp);							// 登録日
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}

	/**
	 * User_Phoneテーブルモデルに値をセットします。
	 * ※変更前のUserPhoneデータを論理削除する場合
	 *
	 * @param userId ユーザーID
	 * @param telId 電話ID
	 * @param timestamp タイムスタンプ
	 * @param deleteFlag 削除フラグ
	 * @return エンティティパラメータ
	 */
	private UserPhoneEntity setUserTelEntityDeleteInfo(BigDecimal userId, BigDecimal telId, Timestamp timestamp, String deleteFlag){
		/* インスタンス化 */
		UserPhoneEntity entity = new UserPhoneEntity();

		entity.setAppUserId(userId);										// ユーザーID
		entity.setCucmPhoneId(telId);										// 電話ID
		entity.setDeleted(deleteFlag);										// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}

	/**
	 * UserSectionテーブルモデルに値をセットします
	 * ※ユーザーの所属部署の予約削除と削除フラグを立てる
	 *
	 * @param userId ユーザーId
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserSectionEntity setUserSectionDeleteReserveInfo(BigDecimal userId, Timestamp timestamp){
		/* インスタンス化 */
		UserSectionEntity entity = new UserSectionEntity();

		entity.setAppUserId(userId);							// ユーザーID
		entity.setDeleteReserve(Constants.COM_FLG_OFF);			// 予約削除フラグ
		entity.setDeleted(Constants.COM_FLG_ON);				// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}

	/**
	 * 電子電話帳関連モデルに値をセットします。
	 * 
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private TelDirAssociationEntity setTeldirAssociationEntity(AddTelLineUserUpdateModel params, Timestamp timestamp){

		/* インスタンス化 */
		TelDirAssociationEntity entity = new TelDirAssociationEntity();

		entity.setCucmPhoneId(params.getTelId());				// 電話ID
		entity.setCucmLineId(params.getLineId());				// ラインID
		entity.setAppUserId(params.getUserId());				// ユーザーID
//		entity.setStatusCode("");								// ステータスコード
//		entity.setAssociateCode("");							// AssociateCode
		entity.setTelDirData(Constants.COM_FLG_OFF);			// TelDirData
		entity.setDeleted(Constants.COM_FLG_OFF);				// 削除フラグ
		entity.setCrtTmstmp(timestamp);							// 作成日時
		entity.setLstupdtTmstmp(timestamp);						// 更新日時

		return entity;
	}

	/**
	 * 電子電話帳関連モデルに値をセットします。
	 * 
	 * @param telId 電話ID
	 * @param lineId ラインID
	 * @param userId ユーザーID
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private TelDirAssociationEntity setTeldirAssociationEntityDeleteInfo(
			BigDecimal telId, BigDecimal lineId, BigDecimal userId, Timestamp timestamp, String deleteFlg){

		/* インスタンス化 */
		TelDirAssociationEntity entity = new TelDirAssociationEntity();

		entity.setCucmPhoneId(telId);				// 電話ID
		entity.setCucmLineId(lineId);				// ラインID
		entity.setAppUserId(userId);				// ユーザーID
		entity.setDeleted(deleteFlg);					// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);			// 更新日時

		return entity;
	}

	/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param telId 電話ID
	 * @param lineId ラインID
	 * @param lineIndex ライン番号
	 * @param dialin ダイルイン
	 * @param timestamp ライムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntity(
			BigDecimal telId, BigDecimal lineId,String lineIndex, String dialin, Timestamp timestamp, String deleteFlg){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(telId);										// 電話ID
		entity.setCucmLineId(lineId);										// ラインId
		entity.setIndex(lineIndex);											// 連番
		entity.setDialin(dialin);											// ダイアルイン
		entity.setRingsettingName(Constants.RING_SETTING_DEFAULT);			// RingSetting名
		entity.setDeleted(deleteFlg);										// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setErrorFlg(deleteFlg);										// エラーフラグ
		entity.setCrtTmstmp(timestamp);										// 作成日時
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}

/**
	 * ラインテーブルモデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp ライムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntity(AddTelLineUserUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(params.getLineId());								// ラインID
		entity.setDirectoryNumber(params.getDirectoryNumber());					// 内線番号
		entity.setUseVmFlg(params.getVoiceMailFlg());							// VMフラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);		// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);								// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);								// エラーフラグ
		entity.setCrtTmstmp(timestamp);											// 作成日時
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

	/**
	 * ラインテーブルモデルに値をセットします。
	 * ※削除フラグを立てます
	 *
	 * @param lineId 登録・更新データ
	 * @param timestamp タイムスタンプ 
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntityDeleteInfo(BigDecimal lineId, Timestamp timestamp, String deleteFlg){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(lineId);										// ラインId
		entity.setDeleted(deleteFlg);										// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}

	/**
	 * VoiceLoggerAssociationモデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param lineId ラインId
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private VoiceLoggerAssociationEntity setVoiceLoggerAssociationEntity(
			AddTelLineUserUpdateModel params, BigDecimal lineId, Timestamp timestamp){

		/* インスタンス化 */
		VoiceLoggerAssociationEntity entity = new VoiceLoggerAssociationEntity();

		entity.setCucmLineId(lineId);						// ラインID
		entity.setLoggerData(Constants.LOGGER_DATA_OFF);	// LoggerData
		entity.setDeleted(Constants.COM_FLG_OFF);			// 削除フラグ
		entity.setCrtTmstmp(timestamp);						// 作成日時
		entity.setLstupdtTmstmp(timestamp);					// 更新日時

		return entity;
	}

	/**
	 * VoiceLoggerAssociationモデルに値をセットします。
	 * 
	 * @param lineId ラインID
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private VoiceLoggerAssociationEntity setVoiceLoggerAssociationEntityDeleteInfo(
				BigDecimal lineId, Timestamp timestamp, String deleteFlg){

		/* インスタンス化 */
		VoiceLoggerAssociationEntity entity = new VoiceLoggerAssociationEntity();

		entity.setCucmLineId(lineId);			// ラインID
		entity.setDeleted(deleteFlg);			// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);		// 更新日時

		return entity;
	}

	/**
	 * 課金関連モデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private ChargeAssociationEntity setChargeAssociationEntity(AddTelLineUserUpdateModel params, Timestamp timestamp){

		/* インスタンス化 */
		ChargeAssociationEntity entity = new ChargeAssociationEntity();

		entity.setCucmLineId(params.getLineId());									// ラインID
		entity.setStatusCode(Constants.COM_FLG_OFF);								// ステータス
		entity.setBranchId(params.getChargeAssociationBranchId());					// 拠点ID
		entity.setParentSectionId(params.getChargeAssociationParentSectionId());	// 親店部課ID
		entity.setSectionId(params.getChargeAssociationSectionId());				// 店部課ID
		entity.setDeleted(Constants.COM_FLG_OFF);									// 削除フラグ
		entity.setCrtTmstmp(timestamp);												// 作成日時
		entity.setLstupdtTmstmp(timestamp);											// 更新日時

		return entity;
	}

	/**
	 * 課金関連モデルに値をセットします。
	 * 
	 * @param lineId ラインID
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private ChargeAssociationEntity setChargeAssociationEntityDeleteInfo(
				BigDecimal lineId, Timestamp timestamp, String deleteFlg){

		/* インスタンス化 */
		ChargeAssociationEntity entity = new ChargeAssociationEntity();

		entity.setCucmLineId(lineId);			// ラインID
		entity.setDeleted(deleteFlg);			// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);		// 更新日時

		return entity;
	}
}	
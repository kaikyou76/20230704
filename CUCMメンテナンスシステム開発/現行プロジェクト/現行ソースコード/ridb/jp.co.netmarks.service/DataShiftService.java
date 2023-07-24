/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * DataShiftService.java
 *
 * @date 2013/10/22
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.co.ksc.service.BaseService;
import jp.co.ksc.util.LogHelpUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.entity.ChargeAssociationEntity;
import jp.co.netmarks.model.entity.LineEntity;
import jp.co.netmarks.model.entity.LineReflectedClusterEntity;
import jp.co.netmarks.model.entity.PhoneEntity;
import jp.co.netmarks.model.entity.PhoneLineEntity;
import jp.co.netmarks.model.entity.PhoneReflectedClusterEntity;
import jp.co.netmarks.model.entity.TelDirAssociationEntity;
import jp.co.netmarks.model.entity.UnityAssociationEntity;
import jp.co.netmarks.model.entity.UserEntity;
import jp.co.netmarks.model.entity.UserPhoneEntity;
import jp.co.netmarks.model.entity.VoiceLoggerAssociationEntity;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.ChargeAssociationMapper;
import jp.co.netmarks.persistence.LineMapper;
import jp.co.netmarks.persistence.LineReflectedClusterMapper;
import jp.co.netmarks.persistence.PhoneLineMapper;
import jp.co.netmarks.persistence.PhoneMapper;
import jp.co.netmarks.persistence.PhoneReflectedClusterMapper;
import jp.co.netmarks.persistence.TelDirAssociationMapper;
import jp.co.netmarks.persistence.UnityAssociationMapper;
import jp.co.netmarks.persistence.UserMapper;
import jp.co.netmarks.persistence.UserPhoneMapper;
import jp.co.netmarks.persistence.UserSectionMapper;
import jp.co.netmarks.persistence.VoiceLoggerAssociationMapper;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * データ移行用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/10/22 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/10/22
 */
@Service
public class DataShiftService extends BaseService {

	private static Log log = LogFactory.getLog(DataShiftService.class);

	/** ##### 共通 ##### */
	@Autowired
	private AppCommonMapper appCommonMapper;

	@Autowired
	private Properties properties;

	/** ##### エンティティ関連 ##### */
	@Autowired
	private PhoneMapper phoneMapper;

	@Autowired
	private UserPhoneMapper userPhoneMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserSectionMapper userSectionMapper;

	@Autowired
	private PhoneLineMapper phoneLineMapper;

	@Autowired
	private LineMapper lineMapper;

	@Autowired
	private VoiceLoggerAssociationMapper voiceLoggerAssociationMapper;

	@Autowired
	private ChargeAssociationMapper chargeAssociationMapper;

	@Autowired
	private TelDirAssociationMapper telDirAssociationMapper;

	@Autowired
	private UnityAssociationMapper unityAssociationMapper;

	@Autowired
	private PhoneReflectedClusterMapper phoneReflectedClusterMapper;

	@Autowired
	private LineReflectedClusterMapper lineReflectedClusterMapper;


	/** ##### トランザクション ##### */
	@Autowired
	private PlatformTransactionManager txManager;

	/** 検索：カラム名 */
	private static Map<String, String> keyMap = new HashMap<String, String>();
	static {
		keyMap.put("macAddress", "Device Name");									// MAC ADDRESS
		keyMap.put("description", "Description");									// 電話－説明
		keyMap.put("devicePoolName", "Device Pool");								// デバイスプール名
		keyMap.put("phoneButtonTemplateName", "Phone Button Template");				// ボタンテンプレート名
		keyMap.put("callingSearchSpaceName", "CSS");								// コーリングサーチスペース名
		keyMap.put("locationName", "Location");										// ロケーション名
		keyMap.put("addonModuleName1", "Module 1");									// 拡張モジュール１
		keyMap.put("addonModuleName2", "Module 2");									// 拡張モジュール２
		keyMap.put("phoneProductName", "Device Type");								// 電話機種名
		keyMap.put("ownerUserId", "Owner User ID");									// オーナユーザID
		/** ##### ユーザー ##### **/
		keyMap.put("cucmLoginId", "User ID");										// cucmLoginId
		/** ##### ライン ##### **/
		keyMap.put("directoryNumber", "Directory Number");							// 内線番号
		keyMap.put("vmProfile", "Voice Mail Profile");								// ボイスメールプロファイルクラス
		keyMap.put("fwdAllCss", "Forward All CSS");									// 全転送CSS
		keyMap.put("fwdBusyDestination", "Forward Busy Internal Destination");		// 話中転送先
		keyMap.put("fwdBusyCss", "Forward Busy Internal CSS");						// 話中転送CSS
		keyMap.put("fwdNoansDestination", "Forward No Answer Internal Destination");// 不応答転送先
		keyMap.put("fwdNoansCss", "Forward No Answer Internal CSS");				// 不応答転送CSVV
		keyMap.put("callPickupGroupName", "Call Pickup Group");						// ピックアップグループ
		keyMap.put("lineTextLabel", "Line Text Label");								// LineTextLabel
		keyMap.put("externalPhoneNumberMask", "External Phone Number Mask");		// ExternalPhoneNumberMask
		keyMap.put("ringSettingName", "Ring setting (Phone Idle)");					// RingSetting名
		keyMap.put("chargeAssociationBranchId", "kakin branch");					// 課金先拠点ID
		keyMap.put("chargeAssociationParentSectionId", "kakin parent section");		// 課金先親店部課ID
		keyMap.put("chargeAssociationSectionId", "kakin section");					// 課金先店部課ID
	}

	/**
	 * データ移行処理
	 *
	 * @param file BATファイル
	 * @return map メッセージ
	 * @throws Exception 例外処理
	 */
	public Map<String, String> dataShift(MultipartFile file) throws Exception {

		Map<String, String> messageMap = new HashMap<String, String>();

		/* ファイル形式チェック */
		if(!fileCheck(file,messageMap)) return messageMap;

		/* ファイルを一時ディレクトリに保存 */
		/* ファイル名の定義 */
		String fileName = properties.getProperty("tmporary.directory.path") +
				"/" + RandomStringUtils.randomAlphanumeric(25);
		/* ファイルの保存 */
		file.transferTo(new File(fileName));

		/* 非同期処理 */
		DataShift dataShift = new DataShift(this,fileName);
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(dataShift);
		executor.shutdown();

		/* メッセージをセット */
		messageMap.put("successMessage", properties.getProperty("data.shift.execute"));

		return messageMap;
	}

	/**
	 * データ移行処理
	 *
	 * @param fileName BATファイル名
	 * @throws IOException 例外処理
	 */
	public void entry(String fileName) throws IOException {

		log.info("【データ移行】開始");

		/* ファイルデータ変数 */
		String header                 = null;								// ヘッダ
		String detail                 = null;								// データ
		List<String> headerList       = new ArrayList<String>();			// ヘッダリスト
		List<String> detailList       = new ArrayList<String>();			// データ詳細リスト
		int headerCnt                 = 0;									// ヘッダ件数
		Map<String, String> dataMap   = new HashMap<String, String>();		// データマップ（KEY：HEDER VALUE:DATA）
		Map<String, Integer> countMap = new HashMap<String, Integer>();		// カウントマップ

		/* ファイル取得クラス */
		BufferedReader br = null;


		try{
			/* ファイルデータの取得 */

			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "Shift_JIS"));

			/** ##### ヘッダーデータの取得 ##### **/
			header = br.readLine();
			headerList = Arrays.asList(header.split(properties.getProperty("data.shift.delimiter")));

			/* ヘッダー整合性チェック */
			if(!headerExistCheck(headerList, countMap)) {
				// 不整合エラー
				log.error(properties.getProperty("data.shift.inconsistency.error"));
			} else {
				/* ヘッダー件数をセット */
				headerCnt = headerList.size();

				/* タイムスタンプを取得 */
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				/** ##### 詳細データの取得  ##### **/
				int i = -1;
				int updCnt = 0;
				int errCnt = 0;
				while((detail = br.readLine()) != null){
					/* カウンター */
					i++;

					/* 空行の場合は処理を行わない */
					if(StringUtils.isEmpty(detail)) continue;

					/* リストにセット */
					detailList = Arrays.asList(detail.split(
							properties.getProperty("data.shift.delimiter"), headerCnt + 1));

					/* ヘッダー数とデータの件数を比較する */
					if(detailList.size() != headerCnt){
						/* データ数が違った場合 */
						log.error(MessageFormat.format(properties.getProperty("file.data.size.error"), i+1 ));
						log.error(MessageFormat.format(properties.getProperty("data.shift.inconsistency.error.list"), i+1 ));
						errCnt++;
						continue;
					}

					/* ハッシュマップに値をセットする */
					for( int j = 0; j < headerCnt ; j++){
						dataMap.put(headerList.get(j), detailList.get(j));
					}
					
					/** ##### 入力チェック ##### **/
					if(!dataCheck(dataMap, countMap, i + 1)){
						log.error(MessageFormat.format(properties.getProperty("data.shift.inconsistency.error.list"), i+1 ));
						errCnt++;
						continue;
					}

					/** ##### 更新処理 ##### **/
					dataEntry(dataMap, countMap, i + 1, timestamp);


					/* 更新件数をカウント */
					updCnt++;
				}

				/* 更新数,エラー数の出力 */
				log.info(MessageFormat.format(properties.getProperty("data.shift.entry.count.success"), updCnt));

				/* エラーがあった場合 */
				if(errCnt > 0){
					/* エラーメッセージ */
					log.error(MessageFormat.format(properties.getProperty("data.shift.entry.count.error"), errCnt));
					log.info(properties.getProperty("data.shift.error"));
				}
			}

		} catch (Exception e){
			log.error(properties.getProperty("data.shift.exception"));
			log.error(LogHelpUtil.getStackTrace(e));
		} finally{
			/* クローズ */
			if(br != null){
				br.close();
			}

			/* 一時ファイルの削除 */
			File file = new File(fileName);
			file.delete();
		}

		log.info("【データ移行】終了");
	}

	/**
	 * 登録処理
	 *
	 * @param dataMap 登録データ
	 * @param countMap ライン、ユーザーのヘッダー件数
	 * @param number 行数
	 * @param timestamp タイムスタンプ
	 */
	private void dataEntry(Map<String, String> dataMap, Map<String, Integer> countMap ,int number, Timestamp timestamp){

		/* SQLマップ */
		Map<String, Object> sqlMap = null;

		/* トランザクションの取得 */
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;

		try {

			/* トランザクションスタート */
			ts = txManager.getTransaction(dtd);

			/** ##### 電話機登録 ##### **/
			/* 電話機ID取得 */
			dataMap.put("telId", sequence(Constants.CUCM_PHONE_ID_SEQ));
			/* クラスターID取得 */
			dataMap.put("clusterId", appCommonMapper.getClusterId(dataMap.get("branchId")).toString());

			/* 電話機登録処理 */
			phoneMapper.insertPhone(setPhoneEntity(dataMap, timestamp));

			/* 電話反映先クラスタ登録処理 */
			phoneReflectedClusterMapper.insertPhoneReflectedCluster(setPhoneReflectedClusterEntity(dataMap, timestamp));

			/** ##### ライン登録 ##### **/
			/* ファイルに定義されたライン分処理を行う */
			for(int i = 0; i < countMap.get("lineCount"); i++){

				/* ヘッダーKEYを取得 */
				Map<String, String> lineKeyMap = setLineKey(i+1);

				/* ライン２以降、内線番号が存在しない場合は登録しない */
				if(i > 0 && StringUtils.isEmpty(dataMap.get(lineKeyMap.get("directoryNumber")))) continue;

				/* ラインIDを取得 */
				Map<String, Object> lineMap = appCommonMapper.getLineDeleteFlg(dataMap.get(lineKeyMap.get("directoryNumber")));

				/* ラインIDの存在、非存在によって処理を分ける */
				if(MapUtils.isNotEmpty(lineMap)){
					/* ラインが存在している場合 */
					dataMap.put("lineId", String.valueOf(lineMap.get("lineId")));
					/* 削除されていた場合 */
					if(lineMap.get("deleted").equals(Constants.COM_FLG_ON)){
						/* ラインの削除フラグを削除なしに更新する */
						lineMapper.updateLineDeleteOnly(
								setLineEntityDeleteInfo(new BigDecimal(dataMap.get("lineId")), timestamp));

						/* 課金先の削除フラグを削除なしに更新する */
						chargeAssociationMapper.updateChargeAssociationDelete(
								setChargeAssociationEntity(dataMap, lineKeyMap, timestamp));

						/* 通録関連の削除フラグを削除なしに更新する */
						voiceLoggerAssociationMapper.updateVoiceLoggerAssociationDelete(
								setVoiceLoggerAssociationEntity(dataMap, timestamp));
					}
				} else {
					/* ラインが存在しなかった場合 */
					dataMap.put("lineId", sequence(Constants.CUCM_LINE_ID_SEQ));

					/* ラインの登録を行う */
					lineMapper.insertLine(setLineEntity(dataMap, lineKeyMap, timestamp));

					/* 課金情報の登録を行う */
					chargeAssociationMapper.inertChargeAssociation(
						setChargeAssociationEntity(dataMap, lineKeyMap, timestamp));

					/* 通録関連の登録を行う */
					voiceLoggerAssociationMapper.inertVoiceLoggerAssociation(
						setVoiceLoggerAssociationEntity(dataMap, timestamp));

					/* クラスタ反映先クラスタ */
					lineReflectedClusterMapper.insertLineReflectedCluster(
							setLineReflectedClusterEntity(dataMap, timestamp));

				}

				/* ラインIndexをセット */
				dataMap.put("lineIndex", String.valueOf(i+1));
				
				/* 電話機－ラインの紐付き作成 */
				phoneLineMapper.insertPhoneLine(setPhoneLineEntity(dataMap, lineKeyMap, timestamp));

				/* １ライン目且つ、ラインが存在していない且つ、ボイスメールプロファイルクラスに値がセットされていた場合 */
				if(i==0 && ((MapUtils.isEmpty(lineMap)) ? StringUtils.isNotEmpty(dataMap.get(lineKeyMap.get("vmProfile"))) :
														 lineMap.get("vmFlg").equals(Constants.VOICE_MAIL_FLG_ON))){
					/* Unity関連の登録を行う ※ 電話機ラインが登録された後登録しなくてはいけない（） */
					unityAssociationMapper.insertUnityAssociation(
							setAssociationEntity(dataMap, timestamp));
				}

			}

			/** ##### ユーザー登録 ##### **/
			String conUserName = "";		// 兼務ユーザー名
			boolean userEntryFlg = false;
			/* ファイルに定義されたユーザ分処理を行う */
			for(int i = 0; i < countMap.get("userCount"); i++){

				String userKey = keyMap.get("cucmLoginId") + " " + String.valueOf(i+1);

				/* 空の場合チェックを行わない */
				if(StringUtils.isEmpty(dataMap.get(userKey))){
					break;
				}

				/* 店部課情報取得 */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("cucmLoginId", dataMap.get(userKey));
				sqlMap.put("deleted",Constants.COM_FLG_OFF);
				sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
				sqlMap.put("COM_FLG_ON", Constants.COM_FLG_ON);
				Map<String, Object> userMap = appCommonMapper.getMaxUserSection(sqlMap);
				/* User存在チェック */
				if(MapUtils.isEmpty(userMap)){
					log.warn(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), userKey));
					continue;
				}

				/* ユーザ－電話機の紐付き作成 */
				userPhoneMapper.insertUserPhone(
						setUserPhoneEntity(dataMap, userMap, timestamp));

				/* ユーザーテーブル.内線番号の更新 */
				userMapper.updateTelephoneNumber(
						setUserEntityTelephoneNumberInfo(
								new BigDecimal((Integer)userMap.get("userId")), timestamp));

				/* 兼務だった場合 */
				if(userMap.get("concurrentlyFlg").toString().equals(Constants.COM_FLG_ON)){
					conUserName += (StringUtils.isNotEmpty(conUserName) ? "," : "") +
							dataMap.get(userKey) + "（" + (String)userMap.get("userKanjiName") + "）";
				}
				/* ユーザ登録フラグ */
				userEntryFlg = true;
			}

			/* 兼務ユーザーが存在した場合 */
			if(StringUtils.isNotEmpty(conUserName)){
				/* 備考を更新する */
				phoneLineMapper.updatePhoneLineRemarks(
						setPhoneLineEntityRemarksInfo(dataMap, conUserName, timestamp));
			}

			/* ユーザーを登録していた場合 */
			if(userEntryFlg){
				/* 電子電話帳関連の登録 */
				telDirAssociationMapper.insertTelDirDataTelAll(
						setTeldirAssociationEntity(dataMap, timestamp));
			}

			/* コミット */
			txManager.commit(ts);

		} catch(Exception e) {
			/* ロールバック */
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			throw e;
		}
	}

	/**
	 * データチェック
	 *
	 * @param dataMap データ件数
	 * @param countMap ライン、ユーザーのヘッダー件数
	 * @param number 行数
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean dataCheck(Map<String, String> dataMap, Map<String, Integer> countMap, int number){

		Boolean check = true;
		String branchId = null;
		String sectionId = null;
		Map<String, Object> sqlMap = null;
	/* ###### MAC ADDRESS ##### */
		String macAddress = dataMap.get(keyMap.get("macAddress"));
		if(!notEmptyCheck(macAddress, keyMap.get("macAddress"), number)){
			/* 必須チェック */
			check = false;
		} else if(!lengthSameCheck(macAddress, keyMap.get("macAddress"), number, 15)){
			/* 桁チェック */
			check = false;
		} else {
			/* MacAddressの前３文字を取り除く */
			macAddress = macAddress.substring(3, macAddress.length());
			/* 存在チェック */
			sqlMap = new HashMap<String, Object>();
			sqlMap.put("macAddress", macAddress);
			if(appCommonMapper.macAddressExistCheck(sqlMap) > 0){
				/* 存在していた場合は、その時点でチェックも行わず次の処理を行う */
				log.error(MessageFormat.format(properties.getProperty("not.exist.error.list"),
						String.valueOf(number), keyMap.get("macAddress")));
				return false;
			}
			if(!characterMatchesCheck(macAddress, keyMap.get("macAddress"), number, "^([a-fA-F0-9]+$)?$")){
					/* 文字列パターンチェック */
					check = false;
			} else if(!lengthSameCheck(macAddress, keyMap.get("macAddress"), number, 12)){
				/* 桁チェック */
				check = false;
			} else {
				/* MacAddressをデータマップにセット */
				dataMap.put("macAddress", macAddress);
			}
		}

		/* ##### CSS ##### */
		String css = dataMap.get(keyMap.get("callingSearchSpaceName"));
		if(!notEmptyCheck(css, keyMap.get("callingSearchSpaceName"), number)){
			/* 必須チェック */
			check = false;
		} else {
			/* 拠点、店部課の取得 */
			String[] cssArray = css.split("_");
			if(cssArray.length == 3 || (cssArray.length == 4 && !cssArray[2].matches("^([0-9]+)?$"))){
				/* Ex) 112_00112_Dialin or 112_00112_Dialin_Domestic */
				branchId = cssArray[0];
				sectionId = cssArray[1];
			} else if(cssArray.length == 4 || (cssArray.length == 5 && !cssArray[3].matches("^([0-9]+)?$"))){
				/* Ex) 112_1_00112_Dialin or 112_1_00112_Dialin_Domestic*/
				branchId = cssArray[0] + "_" + cssArray[1];
				sectionId = cssArray[2];
			}

			/* "_"形式でCSSが設定されていない 又は "_"が多い場合 */
			if(StringUtils.isEmpty(branchId)){
				/* 形式チェック */
				log.error(MessageFormat.format(properties.getProperty("pattern.error.list"),
						String.valueOf(number), keyMap.get("callingSearchSpaceName")));
				check = false;
			} else {
				/* CallingSearchSpaceNameの存在チェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("cssName", css);
				sqlMap.put("branchId", branchId);
				sqlMap.put("sectionId", sectionId);
				sqlMap.put("companyId", Constants.DATA_SHIFT_ENTRY_COMPANY);
				if(appCommonMapper.callingSearchSpaceExistCheck(sqlMap) == 0){
					/* 存在しなかった場合 */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), keyMap.get("callingSearchSpaceName")));
					branchId = null;
					check = false;
				} else if(appCommonMapper.branchExistCheck(branchId) == 0){
					/* 拠点存在チェック */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), keyMap.get("callingSearchSpaceName") + "[拠点]"));
					branchId = null;
					check = false;

				} else if(appCommonMapper.sectionExistCheck(sqlMap) == 0){
					/* 店部課存在チェック */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), keyMap.get("callingSearchSpaceName") + "[店部課]"));
					branchId = null;
					check = false;
				} else {
					/* dataMapに拠点と親店部課をセットする ※更新時に使う */
					dataMap.put("branchId", branchId);
					dataMap.put("sectionId", sectionId);
				}
			}
		}

		/* ##### デバイスプール ##### */
		String devicePoolName = dataMap.get(keyMap.get("devicePoolName"));
		if(!notEmptyCheck(devicePoolName, keyMap.get("devicePoolName"), number)){
			/* 必須チェック */
			check = false;
		}else if(StringUtils.isNotEmpty(branchId)){
			/* 存在チェック */
			sqlMap = new HashMap<String, Object>();
			sqlMap.put("devicePoolName", devicePoolName);
			sqlMap.put("branchId", branchId);
			if(appCommonMapper.devicePoolExistCheck(sqlMap) == 0){
				log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
						String.valueOf(number),keyMap.get("devicePoolName")));
				check = false;
			}
		}

		/* ##### 電話機種名 ##### */
		String phoneType = dataMap.get(keyMap.get("phoneProductName"));
		if(!notEmptyCheck(phoneType, keyMap.get("phoneProductName"), number)){
			/* 必須チェック */
			check = false;
		} else if(StringUtils.isNotEmpty(branchId)){
			/* 存在チェック */
			sqlMap = new HashMap<String, Object>();
			sqlMap.put("typeModelName", phoneType);
			sqlMap.put("branchId", branchId);
			if(appCommonMapper.typeModelExistCheck(sqlMap) == 0){
				log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
						String.valueOf(number),keyMap.get("phoneProductName")));
				check = false;
			}
		}

		/* ##### ボタンテンプレート ##### */
		String phoneButtonTemplate = dataMap.get(keyMap.get("phoneButtonTemplateName"));
		if(!notEmptyCheck(phoneButtonTemplate, keyMap.get("phoneButtonTemplateName"), number)){
			/* 必須チェック */
			check = false;
		} else if(StringUtils.isNotEmpty(branchId)){
			/* 存在チェック */
			sqlMap = new HashMap<String, Object>();
			sqlMap.put("typeModelName", phoneType);
			sqlMap.put("phoneTemplateName", phoneButtonTemplate);
			sqlMap.put("branchId", branchId);
			if(appCommonMapper.phoneTemplateExistCheck(sqlMap) == 0){
				log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
						String.valueOf(number),keyMap.get("phoneButtonTemplateName")));
				check = false;
			}
		}

		/* ##### ロケーション ##### */
		String location = dataMap.get(keyMap.get("locationName"));
		if(!notEmptyCheck(location, keyMap.get("locationName"), number)){
			/* 必須チェック */
			check = false;
		} else if(StringUtils.isNotEmpty(branchId)){
			/* 存在チェック */
//			sqlMap = new HashMap<String, Object>();
//			sqlMap.put("locationName", location);
//			sqlMap.put("branchId", branchId);
//			if(appCommonMapper.locationExistCheck(sqlMap) == 0){
//				log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
//						String.valueOf(number),keyMap.get("locationName")));
//				check = false;
//			}
		}

		/* ##### 拡張モジュール ##### */
		String addonModuleName1 = dataMap.get(keyMap.get("addonModuleName1"));
		String addonModuleName2 = dataMap.get(keyMap.get("addonModuleName2"));
		if(StringUtils.isNotEmpty(addonModuleName1) || StringUtils.isNotEmpty(addonModuleName2)){
			/* 拡張モジュール１又は、拡張モジュール２に値が入っていた場合 */
			if(StringUtils.isEmpty(addonModuleName1) && StringUtils.isNotEmpty(addonModuleName2)){
				/* 拡張モジュール１の必須チェック */
				log.error(MessageFormat.format(properties.getProperty("notempty.error.list"),
						String.valueOf(number), keyMap.get("addonModuleName1")));
				check = false;
			} else if(StringUtils.isNotEmpty(addonModuleName2) && !addonModuleName1.equals(addonModuleName2)){
				/* 拡張モジュール１、２の整合性チェック */
				log.error(MessageFormat.format(properties.getProperty("value.same.error.list"),
						String.valueOf(number), keyMap.get("addonModuleName1"), keyMap.get("addonModuleName2")));
				check = false;
			} else if(StringUtils.isNotEmpty(phoneType) && phoneType.equals(Constants.TEL_TYPE_MODEL_CISCO_7962)){
				/* 電話機が「Cisco 7962」の場合 */
				if(!Arrays.asList(Constants.ADDON_MODULE_NAME_7962.split(",")).contains(addonModuleName1)){
					/* 該当の拡張モジュール値と違っていた場合 */
					log.error(MessageFormat.format(properties.getProperty("addon.module.conformity.error.list"),
							String.valueOf(number), phoneType, keyMap.get("addonModuleName1"), Constants.ADDON_MODULE_NAME_7962));
					check = false;
				}
			} else if (StringUtils.isNotEmpty(phoneType) && phoneType.equals(Constants.TEL_TYPE_MODEL_CISCO_7960)){
				/* 電話機が「Cisco 7960」の場合 */
				if(!Arrays.asList(Constants.ADDON_MODULE_NAME_7960.split(",")).contains(addonModuleName1)){
					/* 該当の拡張モジュール値と違っていた場合 */
					log.error(MessageFormat.format(properties.getProperty("addon.module.conformity.error.list"),
							String.valueOf(number), phoneType, keyMap.get("addonModuleName1"), Constants.ADDON_MODULE_NAME_7960));
					check = false;
				}
			} else if(StringUtils.isNotEmpty(phoneType)){
				/* 電話機が「Cisco 7962,Cisco 7960」の場合 */
				log.error(MessageFormat.format(properties.getProperty("addon.module.impossible.set.error.list"),
						String.valueOf(number), phoneType, keyMap.get("addonModuleName1"), Constants.ADDON_MODULE_NAME_7960));
				check = false;
			}
		}

		/* 内線番号のリスト(入力チェック用) */
		List<String> dirNumList = new ArrayList<String>();

		/* ラインチェック */
		for(int i = 0; i < countMap.get("lineCount"); i++){

			/* ヘッダーKEYをセット */
			Map<String, String> lineKeyMap = setLineKey(i+1);

			/* 内線番号 */
			String directoryNumber = dataMap.get(lineKeyMap.get("directoryNumber"));
			/* ライン２以降、内線番号が存在しない場合はチェックしない(登録しないため) */
			if(i > 0 && StringUtils.isEmpty(directoryNumber)){
				/* 内線番号がない場合は次の番号を確認する。 */
				continue;
			} else if(StringUtils.isNotEmpty(directoryNumber)){
				/* 同じ行に同じ内線番号が設定されていないかチェックする */
				if(i > 0 && dirNumList.contains(directoryNumber)){
					/* 重複チェック */
					log.error(MessageFormat.format(properties.getProperty("not.exist.same.row.error.list"),
							String.valueOf(number), lineKeyMap.get("directoryNumber")));
					check = false;
					continue;
				}
				/* 内線番号のセット */
				dirNumList.add(directoryNumber);
			}

			/* ##### 内線番号 ##### */
			if(!notEmptyCheck(directoryNumber, lineKeyMap.get("directoryNumber"), number)){
				/* 必須チェック */
				check = false;
			} else if(!characterMatchesCheck(directoryNumber, lineKeyMap.get("directoryNumber"), number, "^$|[*#0-9]+$")){
				/* パターンチェック */
				check = false;
			} else if(!lengthSameCheck(directoryNumber, lineKeyMap.get("directoryNumber"), number, 8)){
				/* 桁チェック */
				check = false;
			} else if(StringUtils.isNotEmpty(branchId)){
				/* ラインの同拠点チェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("directoryNumber", directoryNumber);
				sqlMap.put("deleted", Constants.COM_FLG_OFF);
				String[] lineBranchId = appCommonMapper.getSharedLineBranchDirNum(sqlMap);
				if(lineBranchId != null && lineBranchId.length > 0 && !lineBranchId[0].equals(branchId)){
					/* ラインが存在する且つ、別拠点が設定されていた場合 */
					log.error(MessageFormat.format(properties.getProperty("branch.same.error.list"),
							String.valueOf(number), lineKeyMap.get("directoryNumber"), "拠点"));
					check = false;
				}
			}

			/* ##### 課金（拠点） ##### */
			String chargeBranch = dataMap.get(lineKeyMap.get("chargeAssociationBranchId"));
			if(!notEmptyCheck(chargeBranch, lineKeyMap.get("chargeAssociationBranchId"), number)){
				/* 必須チェック */
				check = false;
			} else if(!characterMatchesCheck(chargeBranch, lineKeyMap.get("chargeAssociationBranchId"), number, "^([0-9]+)?$")){
				/* パターンチェック */
				check = false;
			} else if(!lengthSameCheck(chargeBranch, lineKeyMap.get("chargeAssociationBranchId"), number, 3)){
				/* 桁チェック */
				check = false;
			}
		/* ##### 課金（親店部課） ##### */
			String chargeParentSection = dataMap.get(lineKeyMap.get("chargeAssociationParentSectionId"));
			if(!notEmptyCheck(chargeParentSection, lineKeyMap.get("chargeAssociationParentSectionId"), number)){
				/* 必須チェック */
				check = false;
			} else if(!characterMatchesCheck(chargeParentSection, lineKeyMap.get("chargeAssociationParentSectionId"), number, "^([0-9]+)?$")){
				/* パターンチェック */
				check = false;
			} else if(!lengthSameCheck(chargeParentSection, lineKeyMap.get("chargeAssociationParentSectionId"), number, 5)){
				/* 桁チェック */
				check = false;
			}

			/* ##### 課金（店部課） ##### */
			String chargeSection = dataMap.get(lineKeyMap.get("chargeAssociationSectionId"));
			if(!notEmptyCheck(chargeSection, lineKeyMap.get("chargeAssociationSectionId"), number)){
				/* 必須チェック */
				check = false;
			} else if(!characterMatchesCheck(chargeSection, lineKeyMap.get("chargeAssociationSectionId"), number, "^([0-9]+)?$")){
				/* パターンチェック */
				check = false;
			} else if(!lengthSameCheck(chargeSection, lineKeyMap.get("chargeAssociationSectionId"), number, 5)){
				/* 桁チェック */
				check = false;
			}

			/* ##### 鳴動設定 ##### */
			String ringSettingName = dataMap.get(lineKeyMap.get("ringSettingName"));
			if(!notEmptyCheck(ringSettingName, lineKeyMap.get("ringSettingName"), number)){
				/* 必須チェック */
				check = false;
			} else if(!Arrays.asList(Constants.RING_SETTING_VALUE).contains(ringSettingName)){
				/* 存在チェック */
				log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
						String.valueOf(number),lineKeyMap.get("ringSettingName")));
				check = false;
			}

			/* ##### ボイスメールクラス ##### */
			String vmProfile = dataMap.get(lineKeyMap.get("vmProfile"));
			if(StringUtils.isNotEmpty(vmProfile) && !vmProfile.equals(Constants.UNITY_DATA)){
				/* 名称整合性チェック */
				log.error(MessageFormat.format(properties.getProperty("definition.value.error.list"),
						String.valueOf(number), lineKeyMap.get("vmProfile"), Constants.UNITY_DATA));
				check = false;
			}

			/* ##### 全転送CSS ##### */
			String fwdAllCss = dataMap.get(lineKeyMap.get("fwdAllCss"));
			if(StringUtils.isNotEmpty(fwdAllCss) && StringUtils.isNotEmpty(branchId)){
				/* CallingSearchSpaceNameの存在チェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("cssName", fwdAllCss);
				sqlMap.put("branchId", branchId);
				if(appCommonMapper.callingSearchSpaceExistCheck(sqlMap) == 0){
					/* 存在しなかった場合 */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), lineKeyMap.get("fwdAllCss")));
					check = false;
				}
			}

			/* ##### 話中転送先 ##### */
			String fwdBusyDest = dataMap.get(lineKeyMap.get("fwdBusyDestination"));
			if(StringUtils.isNotEmpty(fwdBusyDest)){
				if(!characterMatchesCheck(fwdBusyDest, lineKeyMap.get("fwdBusyDestination"), number, "^([*#0-9]+)?$")){
					/* パターンチェック */
					check = false;
				} else if(!lengthSameCheck(fwdBusyDest, lineKeyMap.get("fwdBusyDestination"), number, 8)){
					/* 桁チェック */
					check = false;
				} else if(StringUtils.isNotEmpty(directoryNumber) && fwdBusyDest.equals(directoryNumber)){
					/* 内線番号と別番号かチェック */
					log.error(MessageFormat.format(properties.getProperty("directory.number.each.error.list"),
							String.valueOf(number), lineKeyMap.get("fwdBusyDestination")));
					check = false;
				} else {
					/* 転送先情報の取得 */
					sqlMap = new HashMap<String, Object>();
					sqlMap.put("directoryNumber", fwdBusyDest);
					sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
					Map<String, Object> lineInfo = appCommonMapper.getBusyDestinationInfo(sqlMap);
					if(MapUtils.isNotEmpty(lineInfo) && lineInfo.get("vmFlg").toString().equals(Constants.VOICE_MAIL_FLG_ON)){
						/* 転送先のVM仕様フラグチェック */
						log.error(MessageFormat.format(properties.getProperty("voice.mail.fwd.busy.destination.error.list"),
								String.valueOf(number), lineKeyMap.get("fwdBusyDestination")));
						check = false;
					}
//					if(lineInfo == null){
//						/* 存在チェック */
//						log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
//								String.valueOf(number), lineKeyMap.get("fwdBusyDestination")));
//						check = false;
//					} else
				}
			}


			/* ##### 話中転送CSS ##### */
			String fwdBusyCss = dataMap.get(lineKeyMap.get("fwdBusyCss"));
			if(StringUtils.isNotEmpty(fwdBusyCss) && StringUtils.isNotEmpty(branchId)){
				/* CallingSearchSpaceNameの存在チェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("cssName", fwdBusyCss);
				sqlMap.put("branchId", branchId);
				if(appCommonMapper.callingSearchSpaceExistCheck(sqlMap) == 0){
					/* 存在しなかった場合 */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), lineKeyMap.get("fwdBusyCss")));
					check = false;
				}
			}

			/* ##### 不応答転送先 ##### */
			String fwdNoansDest = dataMap.get(lineKeyMap.get("fwdNoansDestination"));
			if(StringUtils.isNotEmpty(fwdNoansDest)){
				if(!characterMatchesCheck(fwdNoansDest, lineKeyMap.get("fwdNoansDestination"), number, "^([*#0-9]+)?$")){
					/* パターンチェック */
					check = false;
				} else if(!lengthMaxCheck(fwdNoansDest, lineKeyMap.get("fwdNoansDestination"), number, 50)){
					/* 桁チェック */
					check = false;
				}
			}

			/* ##### 不応答転送CSS ##### */
			String fwdNoansCss = dataMap.get(lineKeyMap.get("fwdNoansCss"));
			if(StringUtils.isNotEmpty(fwdNoansCss) && StringUtils.isNotEmpty(branchId)){
				/* CallingSearchSpaceNameの存在チェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("cssName", fwdNoansCss);
				sqlMap.put("branchId", branchId);
				if(appCommonMapper.callingSearchSpaceExistCheck(sqlMap) == 0){
					/* 存在しなかった場合 */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), lineKeyMap.get("fwdNoansCss")));
					check = false;
				}
			}

			/* ##### ピックアップグループ ##### */
			String callPickupGroup = dataMap.get(lineKeyMap.get("callPickupGroupName"));
			if(StringUtils.isNotEmpty(callPickupGroup) && StringUtils.isNotEmpty(branchId)){
				/* CallingSearchSpaceNameの存在チェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("pickupGroupName", callPickupGroup);
				sqlMap.put("branchId", branchId);
				if(appCommonMapper.pickupGroupExistCheck(sqlMap) == 0){
					/* 存在しなかった場合 */
					log.error(MessageFormat.format(properties.getProperty("exist.error.list"),
							String.valueOf(number), lineKeyMap.get("callPickupGroupName")));
					check = false;
				}
			}

			/* ##### ラインテキストラベル ##### */
			String lineTextLabel = dataMap.get(lineKeyMap.get("lineTextLabel"));
			if(!characterMatchesCheck(lineTextLabel, lineKeyMap.get("lineTextLabel"), number, "^([ -~｡-ﾟ&&[^%\\[\\]\"\\\\&]]*+)?$")){
				/* パターンチェック */
				check = false;
			} else if(!lengthMaxCheck(lineTextLabel, lineKeyMap.get("lineTextLabel"), number, 30)){
				/* 桁チェック */
				check = false;
			}

			/* ##### ExternalPhoneNumber ##### */
			String externalPhone = dataMap.get(lineKeyMap.get("externalPhoneNumberMask"));
			if(!characterMatchesCheck(externalPhone, lineKeyMap.get("externalPhoneNumberMask"), number, "^([*#X0-9]+)?$")){
				/* パターンチェック */
				check = false;
			} else if(!lengthMaxCheck(externalPhone, lineKeyMap.get("externalPhoneNumberMask"), number, 24)){
				/* 桁チェック */
				check = false;
			}
		}

		/* ユーザーチェック */
		List<String> userList = new ArrayList<String>();
		for(int i = 0; i < countMap.get("userCount"); i++){
			String userKey = keyMap.get("cucmLoginId") + " " + String.valueOf(i+1);
			String cucmLoginId = dataMap.get(userKey);

			/* 設定されていない場合は、ユーザのチェック処理を終了する */
			if(StringUtils.isEmpty(cucmLoginId)){
				break;
			}

			/* 同じユーザーが同行に設定された場合 */
			if(i > 0 && userList.contains(cucmLoginId)){
				log.error(MessageFormat.format(properties.getProperty("not.exist.same.row.error.list"),
						String.valueOf(number), cucmLoginId));
				check = false;
				continue;
			}
			userList.add(cucmLoginId);
		}
		return check;
	}

/**
	 * 必須チェック
	 * 
	 * @param value チェック値
	 * @param key 対象キー
	 * @param number 行数
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean notEmptyCheck(String value, String key, int number){
		if(StringUtils.isEmpty(value)){
			/* 必須チェック */
			log.error(MessageFormat.format(properties.getProperty("notempty.error.list"),
					String.valueOf(number), key));
			return false;
		}
		return true;
	}

	/**
	 * 文字列パターンチェック
	 *
	 * @param value チェック値
	 * @param key 対象キー
	 * @param number 行数
	 * @param matches パターン文字列（正規表現）
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean characterMatchesCheck(String value, String key, int number, String matches){
		if(!value.matches(matches)){
			/* 文字列チェック */
			log.error(MessageFormat.format(properties.getProperty("pattern.error.list"),
					String.valueOf(number), key));
			return false;
		}
		return true;
	}

	/**
	 * 桁設定チェック
	 * ※設定された桁と同じ文字数かチェックします
	 * 
	 * @param value チェック値
	 * @param key 対象キー
	 * @param number 行数
	 * @param length 桁数
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean lengthSameCheck(String value, String key, int number, int length){
		if(value.length() != length){
			/* 桁チェック */
			log.error(MessageFormat.format(properties.getProperty("length.same.error.list"),
					String.valueOf(number), key, String.valueOf(length)));
			return false;
		}
		return true;
	}

	/**
	 * 桁設定チェック
	 * 
	 * @param value チェック値
	 * @param key 対象キー
	 * @param number 行数
	 * @param length 桁数
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean lengthMaxCheck(String value, String key, int number, int length){
		if(value.length() > length){
			/* 桁チェック */
			log.error(MessageFormat.format(properties.getProperty("length.max.error.list"),
					String.valueOf(number), key, String.valueOf(length)));
			return false;
		}
		return true;
	}

	/**
	 * ファイルの形式チェック
	 *
	 * @param file ファイル
	 * @param messageMap メッセージMAP
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean fileCheck(MultipartFile file, Map<String, String> messageMap){

		/* ファイルのエラーチェック */
		if(!file.getContentType().equals(properties.getProperty("data.shift.mime.type"))){
			/* 取込ファイルの型（mime）チェック */
			messageMap.put("errorMessage", properties.getProperty("file.type.error"));
			return false;
		} else if(file.getSize() == 0) {
			/* ファイルのサイズ(空)チェック */
			messageMap.put("errorMessage", properties.getProperty("file.zero.error"));
			return false;
		}
		return true;
	}

	/**
	 * ヘッダーの存在チェック
	 * @param headerList ヘッダーリスト
	 * @param countMap ライン、ユーザーのヘッダー件数
	 * @return True:エラー無し False:エラー有り
	 */
	private boolean headerExistCheck(List<String> headerList, Map<String, Integer> countMap){

		/* 存在しないヘッダーをセット */
		boolean exist = true;

		/* 電話項目 */
		for(String header : properties.getProperty("data.shift.phone.header").split(",")){
			/* ヘッダーリスト */
			if(!headerList.contains(header)){
				log.error(MessageFormat.format(properties.getProperty("file.header.exist.detail.error"),header));
				exist = false;
			}else if(headerList.indexOf(header) != headerList.lastIndexOf(header)){
				log.error(MessageFormat.format(properties.getProperty("file.header.many.exist.detail.error"),header));
				exist = false;
			}
		}

		/* ライン項目 */
		int i = 0;
		boolean checkFlg = true;
		while(true){
			int j = 0;
			for(String header : properties.getProperty("data.shift.line.header").split(",")){
				String lineHed = header + " "  + String.valueOf(i+1);

				if(!headerList.contains(lineHed)){
					/* ライン2以降の場合、内線番号（dataMap.get(lineKeyMap.get(）が存在しなかった場合はエラーにせずに処理を終了する */
					if(i > 0 && j == 0){
						checkFlg = false;
						break;
					}
					/* エラー処理 */
					log.error(MessageFormat.format(properties.getProperty("file.header.exist.detail.error"),
							lineHed));
					exist = false;
				} else if(headerList.indexOf(lineHed) != headerList.lastIndexOf(lineHed)){
					/* 複数存在チェック */
					log.error(MessageFormat.format(properties.getProperty("file.header.many.exist.detail.error"),lineHed));
					exist = false;
				}
				j++;
			}
			/* ヘッダーがなくなった時点で終了する */
			if(!checkFlg)break;
			i++;
		}

		/* ユーザ項目 */
		int j = 0;
		while(true){

			String userHed = properties.getProperty("data.shift.user.header") + " "  + String.valueOf(j+1);

			if(!headerList.contains(userHed)){
					break;
			}else if(headerList.indexOf(userHed) != headerList.lastIndexOf(userHed)){
				/* 複数存在チェック */
				log.error(MessageFormat.format(properties.getProperty("file.header.many.exist.detail.error"),userHed));
				exist = false;
			}
			j++;
		}

		countMap.put("lineCount", i);	/* ラインの件数をセット */
		countMap.put("userCount", j);	/* ユーザの件数をセット */

		return exist;
	}

	/**
	 * 電話機テーブルモデルに値をセットします
	 *
	 * @param dataMap 登録データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneEntity setPhoneEntity(Map<String, String> dataMap, Timestamp timestamp){

		/* インスタンス化 */
		PhoneEntity entity = new PhoneEntity();

		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));							// 電話ID
		entity.setPhoneProductName(dataMap.get(keyMap.get("phoneProductName")));				// 電話機種
		entity.setMacAddress(dataMap.get("macAddress"));										// MacAddress
		entity.setDescription(dataMap.get(keyMap.get("description")));							// 説明
		entity.setCallingSearchSpaceName(dataMap.get(keyMap.get("callingSearchSpaceName")));	// CallingSearchSpaceName
		entity.setLocationName(dataMap.get(keyMap.get("locationName")));						// ロケーション名
		entity.setDevicePoolName(dataMap.get(keyMap.get("devicePoolName")));					// デバイスプール名
		entity.setPhoneButtonTemplateName(dataMap.get(keyMap.get("phoneButtonTemplateName")));	// 電話ボタン
		entity.setAddonModuleName1(dataMap.get(keyMap.get("addonModuleName1")));				// 拡張モジュール１
		entity.setAddonModuleName2(dataMap.get(keyMap.get("addonModuleName2")));				// 拡張モジュール２
		entity.setCompanyId(Constants.DATA_SHIFT_ENTRY_COMPANY);								// 会社ID
		entity.setSectionId(dataMap.get("sectionId"));											// 店部課ID
		entity.setBranchId(dataMap.get("branchId"));											// 拠点ID
		entity.setOwnerUserId(dataMap.get(keyMap.get("ownerUserId")));							// オーナーユーザーID
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_FINISHED);					// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);												// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);												// エラーフラグ
		entity.setCrtTmstmp(timestamp);															// 作成日時
		entity.setLstupdtTmstmp(timestamp);														// 更新日時

		return entity;
	}

/**
	 * ラインテーブルモデルに値をセットします。
	 *
	 * @param dataMap 登録データ
	 * @param lineKeyMap ラインキーMAP
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntity(Map<String, String> dataMap,Map<String, String> lineKeyMap, Timestamp timestamp){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(new BigDecimal(dataMap.get("lineId")));						// ラインID
		entity.setDirectoryNumber(dataMap.get(lineKeyMap.get("directoryNumber")));			// 内線番号
		entity.setFwdAllCss(dataMap.get(lineKeyMap.get("fwdAllCss")));						// 全転送CSS
		entity.setFwdBusyDestination(dataMap.get(lineKeyMap.get("fwdBusyDestination")));	// 話中転送先
		entity.setFwdBusyCss(dataMap.get(lineKeyMap.get("fwdBusyCss")));					// 話中転送CSS
		entity.setFwdNoansDestination(dataMap.get(lineKeyMap.get("fwdNoansDestination")));	// 不応答転送先
		entity.setFwdNoansCss(dataMap.get(lineKeyMap.get("fwdNoansCss")));					// 不応答転送CSV
		entity.setCallPickupGroupName(dataMap.get(lineKeyMap.get("callPickupGroupName")));	// ピックアップグループ
		entity.setUseVmFlg((StringUtils.isEmpty(dataMap.get(lineKeyMap.get("vmProfile"))) ?
				Constants.VOICE_MAIL_FLG_OFF : Constants.VOICE_MAIL_FLG_ON));				// VMフラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_FINISHED);				// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);											// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);											// エラーフラグ
		entity.setCrtTmstmp(timestamp);														// 作成日時
		entity.setLstupdtTmstmp(timestamp);													// 更新日時

		return entity;
	}

	/**
	 * ラインテーブルモデルに値をセットします。
	 * ※削除フラグを立てます
	 *
	 * @param lineId ラインID
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntityDeleteInfo(BigDecimal lineId, Timestamp timestamp){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(lineId);											// ラインId
		entity.setDeleted(Constants.COM_FLG_OFF);								// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);										// 最終更新日

		return entity;
	}

	/**
	 * Userテーブルモデルに値をセットします
	 * ※電話番号の値をセット
	 *
	 * @param userId ユーザーID
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserEntity setUserEntityTelephoneNumberInfo(
			BigDecimal userId, Timestamp timestamp){

		/* ユーザーに紐づく電話機の内線番号を取得 */
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("userId", userId);
		sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		String directoryNumber = appCommonMapper.getTelephoneNumber(sqlMap);

		/* インスタン化 */
		UserEntity entity = new UserEntity();

		entity.setAppUserId(userId);					// ユーザーID
		entity.setTelephoneNumber(directoryNumber);		// 電話番号
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_FINISHED);// 更新フラグ
//		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);				// 最終更新日

		return entity;
	}

	/**
	 * User_Phoneテーブルモデルに値をセットします。
	 * ※変更後の紐付け情報を登録するための値をセットします。
	 * 
	 * @param dataMap 登録データ
	 * @param userMap ユーザデータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserPhoneEntity setUserPhoneEntity(Map<String, String> dataMap, Map<String, Object> userMap,Timestamp timestamp){
		/* インスタンス化 */
		UserPhoneEntity entity = new UserPhoneEntity();

		entity.setAppUserId(new BigDecimal((Integer)userMap.get("userId")));	// ユーザーID
		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));			// 電話ID
		entity.setCompanyId((String)userMap.get("companyId"));					// 会社ID
		entity.setSectionId((String)userMap.get("sectionId"));					// 店部課ID
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_FINISHED);	// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);								// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);								// エラーフラグ
		entity.setCrtTmstmp(timestamp);											// 登録日
		entity.setLstupdtTmstmp(timestamp);										// 最終更新日

		return entity;
	}

	/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param dataMap 登録データ
	 * @param lineKeyMap ラインKEYMAP
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntity(Map<String, String> dataMap, Map<String, String> lineKeyMap, Timestamp timestamp){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));				// 電話ID
		entity.setCucmLineId(new BigDecimal(dataMap.get("lineId")));				// ラインID
		entity.setIndex(dataMap.get("lineIndex"));									// 連番
		entity.setExternalPhoneNumberMask(
				dataMap.get(lineKeyMap.get("externalPhoneNumberMask")));			// ExternalPhoneNumberMask
		entity.setRingsettingName(dataMap.get(lineKeyMap.get("ringSettingName")));	// RingSetting名
		entity.setLineTextLabel(dataMap.get(lineKeyMap.get("lineTextLabel")));		// LineTextLabel
		entity.setDeleted(Constants.COM_FLG_OFF);									// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_FINISHED);		// 更新フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);									// エラーフラグ
		entity.setCrtTmstmp(timestamp);												// 作成日時
		entity.setLstupdtTmstmp(timestamp);											// 最終更新日

		return entity;
	}

	/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param dataMap 登録データ
	 * @param conUserName 兼務ユーザー
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntityRemarksInfo(Map<String, String> dataMap,String conUserName, Timestamp timestamp){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));	// 電話ID
		entity.setRemarks("兼務ユーザ：" + conUserName);				// 備考
		entity.setLstupdtTmstmp(timestamp);								// 最終更新日

		return entity;
	}


	/**
	 * 電話機反映クラスタモデルに値をセットします
	 *
	 * @param dataMap 登録データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneReflectedClusterEntity setPhoneReflectedClusterEntity(Map<String, String> dataMap, Timestamp timestamp){

		/* インスタンス化 */
		PhoneReflectedClusterEntity entity = new PhoneReflectedClusterEntity();
		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));					// 電話ID
		entity.setClusterId(new BigDecimal(dataMap.get("clusterId")));					// クラスタID
		entity.setPhonePkid(
				new SimpleDateFormat(Constants.DATE_FORMAT_NOTHING).format(timestamp));	// UUID
		entity.setCrtTmstmp(timestamp);													// 作成日時
		entity.setLstupdtTmstmp(timestamp);												// 更新日時

		return entity;
	}

	/**
	 * ライン反映クラスタモデルに値をセットします
	 *
	 * @param dataMap 登録データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineReflectedClusterEntity setLineReflectedClusterEntity(Map<String, String> dataMap, Timestamp timestamp){

		/* インスタンス化 */
		LineReflectedClusterEntity entity = new LineReflectedClusterEntity();

		entity.setCucmLineId(new BigDecimal(dataMap.get("lineId")));					// ラインID
		entity.setClusterId(new BigDecimal(dataMap.get("clusterId")));					// クラスタID
		entity.setLinePkid(
				new SimpleDateFormat(Constants.DATE_FORMAT_NOTHING).format(timestamp));	// UUID
		entity.setCrtTmstmp(timestamp);													// 作成日時
		entity.setLstupdtTmstmp(timestamp);												// 更新日時

		return entity;
	}

	/**
	 * 課金関連モデルに値をセットします。
	 *
	 * @param dataMap 登録データ
	 * @param lineKeyMap ラインKEYMAP
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private ChargeAssociationEntity setChargeAssociationEntity(
			Map<String, String> dataMap,Map<String, String> lineKeyMap, Timestamp timestamp){

		/* インスタンス化 */
		ChargeAssociationEntity entity = new ChargeAssociationEntity();

		entity.setCucmLineId(new BigDecimal(dataMap.get("lineId")));								// ラインID
		entity.setStatusCode(Constants.COM_FLG_OFF);												// ステータス
		entity.setBranchId(	dataMap.get(lineKeyMap.get("chargeAssociationBranchId")));				// 拠点ID
		entity.setParentSectionId(dataMap.get(lineKeyMap.get("chargeAssociationParentSectionId")));	// 親店部課ID
		entity.setSectionId(dataMap.get(lineKeyMap.get("chargeAssociationSectionId")));				// 店部課ID
		entity.setCompanyId(Constants.DATA_SHIFT_ENTRY_COMPANY);									// 会社ID
		entity.setDeleted(Constants.COM_FLG_OFF);													// 削除フラグ
		entity.setCrtTmstmp(timestamp);																// 作成日時
		entity.setLstupdtTmstmp(timestamp);															// 更新日時

		return entity;
	}

	/**
	 * VoiceLoggerAssociationモデルに値をセットします。
	 *
	 * @param dataMap 登録データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private VoiceLoggerAssociationEntity setVoiceLoggerAssociationEntity(
			Map<String, String> dataMap, Timestamp timestamp){

		/* インスタンス化 */
		VoiceLoggerAssociationEntity entity = new VoiceLoggerAssociationEntity();

		entity.setCucmLineId(new BigDecimal(dataMap.get("lineId")));	// ラインID
		entity.setLoggerData(Constants.LOGGER_DATA_OFF);				// LoggerData
		entity.setDeleted(Constants.COM_FLG_OFF);						// 削除フラグ
		entity.setCrtTmstmp(timestamp);									// 作成日時
		entity.setLstupdtTmstmp(timestamp);								// 更新日時

		return entity;
	}

	/**
	 * UnityAssociationモデルに値をセットします。
	 * 
	 * @param dataMap 登録データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UnityAssociationEntity setAssociationEntity(Map<String, String> dataMap, Timestamp timestamp){
		/* インスタンス化 */
		UnityAssociationEntity entity = new UnityAssociationEntity();

		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));	// 電話ID
		entity.setUnityData(Constants.UNITY_DATA);						// unity_data
		entity.setVoiceMailProfileClass(Constants.VOICE_MAIL_FLG_OFF);	// VMフラグ
		entity.setDeleted(Constants.COM_FLG_OFF);						// 削除フラグ
		entity.setCrtTmstmp(timestamp);									// 作成日時
		entity.setLstupdtTmstmp(timestamp);								// 更新日時

		return entity;
	}

	/**
	 * 電子電話帳関連モデルに値をセットします。
	 * 
	 * @param dataMap 登録データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private TelDirAssociationEntity setTeldirAssociationEntity(
			Map<String, String> dataMap, Timestamp timestamp){

		/* インスタンス化 */
		TelDirAssociationEntity entity = new TelDirAssociationEntity();

		entity.setCucmPhoneId(new BigDecimal(dataMap.get("telId")));// 電話ID
		entity.setTelDirData(Constants.TEL_DIR_DATA_OFF);			// TelDirData
		entity.setDeleted(Constants.COM_FLG_OFF);					// 削除フラグ
		entity.setCrtTmstmp(timestamp);								// 作成日時
		entity.setLstupdtTmstmp(timestamp);							// 更新日時

		return entity;
	}


	/**
	 * ライン用ヘッダーKEY取得
	 * 
	 * @param i ラインカウンター
	 * @return ラインKEYMAP
	 */
	private Map<String, String> setLineKey(int i){

		Map<String, String> lineKeyMap = new HashMap<String, String>();

		String lineIndex = " " + String.valueOf(i);

		/** ##### ライン ##### **/
		lineKeyMap.put("directoryNumber"        , keyMap.get("directoryNumber") + lineIndex);			// 内線番号
		lineKeyMap.put("vmProfile"              , keyMap.get("vmProfile") + lineIndex);				// ボイスメールプロファイルクラス
		lineKeyMap.put("fwdAllCss"              , keyMap.get("fwdAllCss") + lineIndex);				// 全転送CSS
		lineKeyMap.put("fwdBusyDestination"     , keyMap.get("fwdBusyDestination") + lineIndex);		// 話中転送先
		lineKeyMap.put("fwdBusyCss"             , keyMap.get("fwdBusyCss") + lineIndex);				// 話中転送CSS
		lineKeyMap.put("fwdNoansDestination"    , keyMap.get("fwdNoansDestination") + lineIndex);		// 不応答転送先
		lineKeyMap.put("fwdNoansCss"            , keyMap.get("fwdNoansCss") + lineIndex);				// 不応答転送CSVV
		lineKeyMap.put("callPickupGroupName"    , keyMap.get("callPickupGroupName") + lineIndex);		// ピックアップグループ
		lineKeyMap.put("lineTextLabel"          , keyMap.get("lineTextLabel") + lineIndex);			// LineTextLabel
		lineKeyMap.put("externalPhoneNumberMask", keyMap.get("externalPhoneNumberMask") + lineIndex);	// ExternalPhoneNumberMask
		lineKeyMap.put("ringSettingName"        , keyMap.get("ringSettingName") + lineIndex);			// RingSetting名
		lineKeyMap.put("chargeAssociationBranchId"        , keyMap.get("chargeAssociationBranchId") + lineIndex);			// 課金先拠点ID
		lineKeyMap.put("chargeAssociationParentSectionId" , keyMap.get("chargeAssociationParentSectionId") + lineIndex);	// 課金先親店部課ID
		lineKeyMap.put("chargeAssociationSectionId"       , keyMap.get("chargeAssociationSectionId") + lineIndex);			// 課金先店部課ID

		return lineKeyMap;
	}

}

	
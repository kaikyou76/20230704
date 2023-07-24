/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserAndTelSearchService.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.ksc.util.LogHelpUtil;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.component.ReflectionManager;
import jp.co.netmarks.csv.CsvWriter;
import jp.co.netmarks.model.CUCMLineModel;
import jp.co.netmarks.model.LoginUserModel;
import jp.co.netmarks.model.UserAndTelSearchModel;
import jp.co.netmarks.model.UserAndTelUpdateModel;
import jp.co.netmarks.model.entity.ChargeAssociationEntity;
import jp.co.netmarks.model.entity.LineEntity;
import jp.co.netmarks.model.entity.PhoneEntity;
import jp.co.netmarks.model.entity.PhoneLineEntity;
import jp.co.netmarks.model.entity.TelDirAssociationEntity;
import jp.co.netmarks.model.entity.UnityAssociationEntity;
import jp.co.netmarks.model.entity.UserEntity;
import jp.co.netmarks.model.entity.UserPhoneEntity;
import jp.co.netmarks.model.entity.UserSectionEntity;
import jp.co.netmarks.model.entity.VoiceLoggerAssociationEntity;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.ChargeAssociationMapper;
import jp.co.netmarks.persistence.LineMapper;
import jp.co.netmarks.persistence.PhoneLineMapper;
import jp.co.netmarks.persistence.PhoneMapper;
import jp.co.netmarks.persistence.TelDirAssociationMapper;
import jp.co.netmarks.persistence.UnityAssociationMapper;
import jp.co.netmarks.persistence.UserAndTelSearchMapper;
import jp.co.netmarks.persistence.UserMapper;
import jp.co.netmarks.persistence.UserPhoneMapper;
import jp.co.netmarks.persistence.UserSectionMapper;
import jp.co.netmarks.persistence.VoiceLoggerAssociationMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <pre>
 * ユーザーと電話機の一覧用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/07 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/07
 */
@Service
public class UserAndTelSearchService extends BaseService {

	private static Log log = LogFactory.getLog(UserAndTelSearchService.class);

	@Autowired
	private AppCommonMapper appCommonMapper;

	@Autowired
	private UserAndTelSearchMapper userAndTelSearchMapper;

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
	private CUCMLineService cucmLineService;

	/** ##### トランザクション ##### */
	@Autowired
	private PlatformTransactionManager txManager;

	/** ##### コールマネージャプロジェクト ##### */
	@Autowired
	private ReflectionManager reflectionManager;

	/** 検索：ソート対象カラム */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("statusName","v_search_info.statusName");
		sortMap.put("directoryNumber","v_search_info.directoryNumber");
		sortMap.put("dialinNumber","v_search_info.dialinNumber");
		sortMap.put("lineIndex","v_search_info.lineIndex::integer");
		sortMap.put("kanjiUserName","v_search_info.kanjiUserName");
		sortMap.put("sectionUserName","v_search_info.sectionUserName");
		sortMap.put("pickupGroupName","v_search_info.pickupGroupName");
		sortMap.put("voiceMailFlg","v_search_info.voiceMailFlg");
		sortMap.put("busyDestination","v_search_info.busyDestination");
		sortMap.put("charge","v_search_info.chargeAssociationBranchId || v_search_info.chargeAssociationParentSectionId || v_search_info.chargeAssociationSectionId ");
		sortMap.put("chargeRemarks","v_search_info.chargeRemarks");
		sortMap.put("loggerData","v_search_info.loggerData");
		sortMap.put("telDirData","v_search_info.telDirData");
		sortMap.put("telTypeModel","v_search_info.telTypeModel");
		sortMap.put("macAddress","v_search_info.macAddress");
		sortMap.put("phoneButtonTemplete","v_search_info.phoneButtonTemplete");
		sortMap.put("branchTelId","v_search_info.branchTelId");
		sortMap.put("companySectionTelId","v_search_info.companyTelId || v_search_info.sectionTelId");
		sortMap.put("callingSearchSpaceName","v_search_info.callingSearchSpaceName");
		sortMap.put("addonModuleName1","v_search_info.addonModuleName1");
		sortMap.put("addonModuleName2","v_search_info.addonModuleName2");
		sortMap.put("ringSettingName","v_search_info.ringSettingName");
		sortMap.put("telLineRemarks","v_search_info.telLineRemarks");
		sortMap.put("lineTextLabel","v_search_info.lineTextLabel");
		sortMap.put("noansDestination","v_search_info.noansDestination");
		sortMap.put("externalPhoneNumberMask","v_search_info.externalPhoneNumberMask");
	}

	/** 検索：デフォルトソート */
	private final String DEFAULT_SORT =
				"v_search_info.directoryNumber ," +									// 内線番号
				"v_search_info.branchTelId   ," +									// 拠点（電話）
				"v_search_info.companyTelId  , v_search_info.sectionTelId  ," +		// 会社、店部課ID（電話）
				"v_search_info.companyUserId , v_search_info.sectionUserId ," +		// 会社、店部課ID（ユーザー）
				"v_search_info.macAddress    , v_search_info.userId";				// 電話機種、ユーザー名

	/**
	 * 検索件数を取得
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getUserAndTelTotal(UserAndTelSearchModel params){

		return userAndTelSearchMapper.getUserAndTelTotal(params);
	}

	/**
	 * 検索結果を取得
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<UserAndTelSearchModel> getUserAndTelList(UserAndTelSearchModel params){

		/* ソート設定 */
		if (StringUtils.isNotEmpty(params.getSort())) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		return userAndTelSearchMapper.getUserAndTelList(params);
	}

	/**
	 * 一覧をエクスポート（CSV）
	 * ※所定のフォルダにCSVをアップロードする
	 *
	 * @param params 検索条件
	 * @throws Exception 例外処理
	 */
	public void searchResultExportCsv(UserAndTelSearchModel params) throws Exception{

		/* ソート設定 */
		if (StringUtils.isNotEmpty(params.getSort())) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		/* CSVデータを取得 */
		List<LinkedHashMap<String, String>> list =
				userAndTelSearchMapper.getUserAndTelCsvList(params);

		/* ユーザー情報の取得 */
		LoginUserModel user = (LoginUserModel)SecurityContextHolder.getContext().
				getAuthentication().getDetails();

		/* 現在時分秒を取得 */
		String time = new SimpleDateFormat("yyyyMMddHHmmss").
				format(new Timestamp(System.currentTimeMillis())).toString();

		/* CSVWriterのインスタンス化 */
		CsvWriter csvWriter = new CsvWriter(
				properties.getProperty("csv.directory.path.userandtel"),
				properties.getProperty("csv.filename.userandtel"),
				properties.getProperty("csv.header.userandtel").split(","));

		/* CSVの作成 */
		csvWriter.createCSV(list, time, user.getLoginId());
	}
	
/**
	 * 検索結果をエクスポート
	 * ※所定のフォルダにCSVをアップロードする
	 *
	 * @param params 検索条件
	 * @throws Exception 例外処理
	 */
	public void tableDataExportCsv(UserAndTelSearchModel params) throws Exception{

		/* 現在時分秒を取得 */
		String date = new SimpleDateFormat(Constants.DATE_FORMAT_NOTHING).
				format(new Timestamp(System.currentTimeMillis())).toString();

		/* ログイン情報の取得 */
		LoginUserModel user = (LoginUserModel)SecurityContextHolder.getContext().
				getAuthentication().getDetails();

		/* ログインID */
		String loginId = user.getLoginId();

		/*** ユーザーテーブル ***/
		createTableCsv("appuser", userAndTelSearchMapper.getUserInfoCsv(params), date, loginId);

		/*** 電話テーブル  ***/
		createTableCsv("cucmphone", userAndTelSearchMapper.getPhoneInfoCsv(params), date, loginId);

		/*** ユーザー電話テーブル  ***/
		createTableCsv("rcucmuserphone", userAndTelSearchMapper.getUserPhoneInfoCsv(params), date, loginId);

		/*** ラインテーブル  ***/
		createTableCsv("cucmline", userAndTelSearchMapper.getLineInfoCsv(params), date, loginId);

		/*** 電話ラインテーブル  ***/
		createTableCsv("rcucmphoneline", userAndTelSearchMapper.getPhoneLineInfoCsv(params), date, loginId);

		/*** 課金関連テーブル  ***/
		createTableCsv("chargeassociation", userAndTelSearchMapper.getChargeAssociationInfoCsv(params), date, loginId);

		/*** 通録関連テーブル  ***/
		createTableCsv("voiceloggerassociation", userAndTelSearchMapper.getVoiceLoggerAssociationInfoCsv(params), date, loginId);

		/*** Unity関連テーブル  ***/
		createTableCsv("unityassociation", userAndTelSearchMapper.getUnityAssociationInfoCsv(params), date, loginId);

		/*** 電子電話帳テーブル  ***/
		createTableCsv("teldirassociation", userAndTelSearchMapper.getTelDirAssociationInfoCsv(params), date, loginId);
	}

	/**
	 *
	 * 個別反映、更新（翌日反映処理）
	 *
	 * @param list 更新パラメータ
	 * @param soapFlg True:ソープ通信をする False:ソープ通信しない
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	public Map<String, String> updataProperty(List<UserAndTelUpdateModel> list, boolean soapFlg) throws Exception{

		Map<String, String> map = new HashMap<String, String>();

		/* ロックファイルの存在確認 */
		if(soapFlg){
			/* ロックファイル取得 */
			File lockFile = new File(properties.getProperty("lock.file.path"));
			if(lockFile.exists()){
				/* 「バッチ起動中です。しばらくお待ちください。」メッセージをセット */
				map.put("errorMessage",properties.getProperty("batch.execute.error"));
				return map;
			}
		}

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 更新フラグ */
		boolean sucFlg = false;
		boolean eroFlg = false;
		boolean eroSoapFlg  = false;
		/* エラーメッセージセット */
		StringBuffer errorTel   = new StringBuffer();
		StringBuffer errorUser  = new StringBuffer();
		StringBuffer errorLine  = new StringBuffer();
		/* エラーのエンティティを設定する */
		String erroeEntity = "";

		/* チェックボックスにチェックがついていた項目分処理を行います。 */
		for(UserAndTelUpdateModel params : list){

			erroeEntity = "";

			/* 反映対象外の場合 */
			if(params.isChanged()){

				/* 更新処理 */
				erroeEntity = entryUserTel(params, timestamp);

				/* メッセージ対応 */
				if(StringUtils.isEmpty(erroeEntity)){
					/* 成功 */
					sucFlg = true;
				} else {
					if(erroeEntity.equals(Constants.ERROR_TYPE_TEL)){
						/* 電話エラー */
						errorTel.append(params.getMacAddress());
						errorTel.append(Constants.MESSAGE_DELIMITER);
					} else if(erroeEntity.equals(Constants.ERROR_TYPE_LINE)){
						/* ラインエラー */
						errorLine.append(params.getDirectoryNumber());
						errorLine.append(Constants.MESSAGE_DELIMITER);
					} else if(erroeEntity.equals(Constants.ERROR_TYPE_USER)){
						/* ユーザーエラー */
						errorUser.append(params.getKanjiUserName());
						errorUser.append(Constants.MESSAGE_DELIMITER);
					}
					eroFlg=true;
				}
			}

			/* ソープ通信 */
			if(soapFlg && StringUtils.isEmpty(erroeEntity) && StringUtils.isEmpty(erroeEntity)){
				/* エラーがない場合 */
				String telId = params.getTelId().toString();
				String userId = params.getUserId().toString();
				String lineId = params.getLineId().toString();

				try{
					reflectionManager.reflection((telId.equals("0")) ? null : telId,
												  (userId.equals("0")) ? null : userId,
											      (lineId.equals("0")) ? null : lineId);
				}catch(Exception e){
					/* メッセージをセット */
					log.error(LogHelpUtil.getStackTrace(e));

					eroFlg=true;
					eroSoapFlg = true;
				}
			}
		}
		/* 成功が存在した場合 */
		if(sucFlg || !eroFlg){
			map.put("sucsessMessage", properties.getProperty("update.success"));
		}

		String errMsg = "";
		/* エラーが存在した場合 */
		if(eroFlg){
			/* ユーザー */
			String errUserStr = errorUser.toString();
			String errTelStr = errorTel.toString();
			String errLineStr = errorLine.toString();
			if(StringUtils.isNotEmpty(errUserStr)){
				errMsg = "【ユーザー】" + errUserStr.substring(0, errUserStr.length()-1);
			}
			/* 電話 */
			if(StringUtils.isNotEmpty(errTelStr)){
				errMsg += "【電話機】" + errTelStr.substring(0, errTelStr.length()-1);
			}
			/* ライン */
			if(StringUtils.isNotEmpty(errLineStr)){
				errMsg += "【内線番号】" + errLineStr.substring(0, errLineStr.length()-1);
			}
			/* エラーメッセージのセット */
			String errorMessage =  (StringUtils.isNotEmpty(errMsg) ? MessageFormat.format(properties.getProperty("exclusion.error.key"),errMsg) + "<br/>" : "")
								+ (eroSoapFlg ? properties.getProperty("soap.any.reflect.error") : "");

			map.put("errorMessage",errorMessage);
		}
		return map;
	}

	/**
	 * 個別参照処理
	 *
	 * @param directoryNumber 内線番号
	 * @param clusterId クラスターID
	 * @return 参照先または、errorメッセージ
	 * @throws Exception 例外処理
	 */
	public Map<String, String> separatelyReference(String directoryNumber,String clusterId) throws Exception{

		Map<String, String> map = new HashMap<String, String>();

		/* 条件をセット */
		CUCMLineModel model = new CUCMLineModel();
		model.setDirectoryNumber(directoryNumber);
		model.setClusterId(clusterId);

		try{
			/* コールマネージャーに通信 */
			model = cucmLineService.GetLine(model);
			map.put("fwdAllDestination", model.getFwdAllDestination());
		} catch(Exception e){
			/* メッセージをセット */
			log.error(LogHelpUtil.getStackTrace(e));
			map.put("errorMessage", properties.getProperty("soap.transmission.error"));

		}
		return map;
	}

	/**
	 * 返却処理
	 *
	 * @param params 返却パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> returnTelBtn(UserAndTelUpdateModel params)throws Exception{
		/* メッセージ用マップ */
		Map<String, String> map = new HashMap<String, String>();
		/* SQL用マップ */
		Map<String, Object> sqlMap = new HashMap<String, Object>();

		try{
			/* 電話機のロック処理 */
			sqlMap.put("CUCM_PHONE_ID", params.getTelId());
			sqlMap.put("DELETED", Constants.COM_FLG_OFF);
			locked("CUCM_PHONE", null , sqlMap);

			/* タイムスタンプを取得 */
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			/** ##### 電話－ユーザー情報の削除 ##### **/
			/* ユーザーが存在した場合 */
			if(params.getUserId().compareTo(new BigDecimal(0)) != 0){

				/* ロック処理 */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("APP_USER_ID", params.getUserId());
				sqlMap.put("DELETED", Constants.COM_FLG_OFF);
				locked("APP_USER", null , sqlMap);

				/* ユーザーと電話機紐付きチェック */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("userId", params.getUserId());
				sqlMap.put("telId", params.getTelId());
				sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
				if(appCommonMapper.userTelExistCheck(sqlMap) == 0){
					throw new ExcludeException(new HashMap<String,Object>());
				}

				/* ユーザーと電話機の紐付き削除 */
				userTelDelete(params, timestamp);

				/* 電話機に紐づくユーザー情報の更新 */
				updateTelOwnerUser(params.getTelId(), timestamp);
			}

			/* 電話機に紐づくユーザー情報を取得 */
			sqlMap.put("telId", params.getTelId());
			sqlMap.put("deleted", Constants.COM_FLG_OFF);
			List<Map<String, Object>> userList = appCommonMapper.getUserLinkedTel(sqlMap);

			if(userList.size() == 0){
				/** ##### 電話－ライン情報の削除 ##### **/
				/* 電話機に該当するラインの削除処理 */
				telLineAllDelete(params, timestamp);

				/** ##### 電話機の削除 ##### **/
				/* 電話機の削除処理 */
				telDelete(params, timestamp);
			}

			/* 成功メッセージをセット */
			map.put("sucsessMessage", properties.getProperty("update.success"));

		} catch (ExcludeException e) {
			/* 排他エラーメッセージをセット */
			map.put("errorMessage", properties.getProperty("exclusion.error"));
		}

		return map;
	}

/**
	 * 電話機削除処理
	 *
	 * @param params 電話機削除パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> deleteTel(UserAndTelUpdateModel params)throws Exception{
		/* メッセージ用マップ */
		Map<String, String> map = new HashMap<String, String>();
		/* SQL用マップ */
		Map<String, Object> sqlMap = new HashMap<String, Object>();

		/* トランザクションの取得 */
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;

		try {

			/* トランザクションスタート */
			ts = txManager.getTransaction(dtd);

			/* 電話機のロック処理 */
			sqlMap.put("CUCM_PHONE_ID", params.getTelId());
			sqlMap.put("DELETED", Constants.COM_FLG_OFF);
			locked("CUCM_PHONE", null , sqlMap);

			/* ユーザーと電話機存在チェック(排他) */
			sqlMap.put("telId", params.getTelId());
			sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
			if(appCommonMapper.userTelExistCheck(sqlMap) == 0){
				/* タイムスタンプを取得 */
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				/*  電話機に該当するラインの削除処理 */
				telLineAllDelete(params, timestamp);

				/* 電話機の削除処理 */
				telDelete(params, timestamp);

				/* 成功メッセージをセット */
				map.put("sucsessMessage", properties.getProperty("update.success"));

			} else {
				/* 排他エラーメッセージをセット */
				map.put("errorMessage", properties.getProperty("exclusion.error"));
			}

			/* コミット */
			txManager.commit(ts);

		} catch (ExcludeException e) {
			/* 排他エラーメッセージをセット */
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			/* ロールバック */
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			return map;
		}

		/** コールマネージャ更新処理 */
		try{
			/* ソープ通信 */
			reflectionManager.reflection(params.getTelId().toString(), null, params.getLineId().toString());
		}catch (Exception e) {
			map.put("errorMessage", properties.getProperty("soap.reflect.error"));

			/* スタックトレースを出力 */
			log.error(LogHelpUtil.getStackTrace(e));
		}

		return map;
	}

	/**
	 * ライン削除処理
	 *
	 * @param params ライン削除パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> deleteLine(UserAndTelUpdateModel params) throws Exception{
		/* メッセージ用マップ */
		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedConduct(params);

		/* 存在チェック */
		if(StringUtils.isNotEmpty(exisisCheck(params))){
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 電話－ライン紐付き削除処理 */
		telLineDelete(params, timestamp);

		/* 電話の更新フラグをセットします */
		phoneMapper.updatePhoneUpdateFlag(setPhoneEntityDeleteInfo(params, timestamp, null));

		/* メッセージセット */
		map.put("sucsessMessage", properties.getProperty("update.success"));

		return map;
	}

	/**
	 * 部内在庫
	 *
	 * @param params 部内在庫パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> insideStock(UserAndTelUpdateModel params) throws Exception{
		/* メッセージ用マップ */
		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedConduct(params);

		/* 存在チェック */
		if(StringUtils.isNotEmpty(exisisCheck(params))){
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* ユーザーと電話機の紐付き削除 */
		userTelDelete(params, timestamp);

		/* 電話機に紐づくユーザー情報の更新 */
		updateTelOwnerUser(params.getTelId(), timestamp);

		/* 電話の更新フラグをセットします */
		phoneMapper.updatePhoneUpdateFlag(setPhoneEntityDeleteInfo(params, timestamp, null));

		/* メッセージセット */
		map.put("sucsessMessage", properties.getProperty("update.success"));

		return map;
	}

	/**
	 * 新規共用電話化処理
	 *
	 * @param params 共用電話化パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> sharedTelEdit(UserAndTelUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedConduct(params);

		/* 存在チェック（排他チェック） */
		if(StringUtils.isNotEmpty(exisisCheck(params))) {
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try{

			/* 電話機に該当するユーザーの削除処理 */
			userTelAllDelete(params, timestamp);

			/* ユーザーIDの取得（シーケンス） */
			BigDecimal userId = new BigDecimal(sequence(Constants.APP_USER_ID_SEQ));

			/* ユーザーIDをセット */
			params.setUserId(userId);

			/* 共用電話作成処理 */
			userMapper.insertAppUser(setUserEntitySharedUse(params, timestamp));

			/* 店部課情報の取得 */
			Map<String, Object> sectionMap = userAndTelSearchMapper.getSharedUserSectionInfo(params.getTelId());
			/* 取得したパラメータをモデルにセット */
			params.setSectionUserName(sectionMap.get("sectionName").toString());
			params.setPrintOrder(sectionMap.get("printOrder").toString());
			params.setSectionUserId(sectionMap.get("sectionId").toString());
			params.setCompanyUserId(sectionMap.get("companyId").toString());
			params.setSectionTelId(sectionMap.get("sectionId").toString());
			params.setCompanyTelId(sectionMap.get("companyId").toString());

			/* ユーザー店部課情報の登録 */
			userSectionMapper.insertUserSection(setUserSectionEntity(params, timestamp));

			/* ユーザーと電話機の紐付きを作成します。 */
			userPhoneMapper.insertUserPhone(
					setUserPhoneEntity(params, timestamp,Constants.CUCM_UPDATE_FLG_FINISHED));

			/* 電子電話帳関連を作成します */
			params.setTelDirData(Constants.TEL_DIR_DATA_OFF);
			telDirAssociationMapper.insertTelDirDataTelLine(setTeldirAssociationEntity(params, timestamp));

			/* 成功メッセージをセット */
			map.put("sucsessMessage", properties.getProperty("update.success"));

		} catch (ExcludeException e) {
			/* 排他エラーメッセージをセット */
			map.put("errorMessage", properties.getProperty("exclusion.error"));
		}

		return map;
	}

/**
	 * 共用電話名変更処理
	 *
	 * @param params 共用電話パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> sharedTelUpdate(UserAndTelUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedConduct(params);

		/* 存在チェック（排他チェック） */
		if(StringUtils.isNotEmpty(exisisCheck(params))) {
			map.put("errorMessage", properties.getProperty("exclusion.error"));
			return map;
		}

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* パラメータをセット */
		UserEntity user = new UserEntity();
		user.setAppUserId(params.getUserId());
		user.setNameKanji(params.getSharedUserName());
		user.setLstupdtTmstmp(timestamp);

		/* 共用名変更 */
		userMapper.updateUserKanjiName(user);

		/* 成功メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("update.success"));

		return map;

	}

	/**
	 * 即時反映処理
	 * 
	 * @return メッセージMAP
	 * @throws IOException 例外処理
	 */
	public Map<String, Object> cucmReflection() throws IOException{

		/* メッセージセット用 */
		Map<String, Object> map = new HashMap<String, Object>();

		/* ロックファイル取得 */
		File lockFile = new File(properties.getProperty("lock.file.path"));

		/* ロックファイルの存在確認 */
		if(!lockFile.exists()){
			/* コマンドの設定 */
			String command = properties.getProperty("batch.command.cucm.shell.dir")
					+ properties.getProperty("batch.command.cucm.reflection.shell.file");

			/* 双方向チェック用データの取得 */
			ProcessBuilder pb = new ProcessBuilder(command);

			pb.start();

			/* 「バッチ起動しました」メッセージをセット */
			map.put("message", properties.getProperty("batch.execute"));
		} else {
			/* 「バッチ起動中です。しばらくお待ちください。」メッセージをセット */
			map.put("message", properties.getProperty("batch.execute.error"));
		}
		return map;
	}

	/**
	 * テーブルCSV作成処理
	 *
	 * @param tableName テーブル名 (_をトリムした小文字)
	 * @param list CSVリスト
	 * @param date DATEデータ
	 * @param loginId ログインID
	 * @throws Exception 例外処理
	 */
	private void createTableCsv(String tableName,List<LinkedHashMap<String, String>> list,
								String date, String loginId)throws Exception{

		/*** ユーザーテーブル ***/
		/* CSVWriterのインスタンス化 */
		CsvWriter csvWriter = new CsvWriter(
				properties.getProperty("csv.directory.path.table"),
				properties.getProperty("csv.filename." + tableName),
				properties.getProperty("csv.header." + tableName).split(","));

		/* CSVの作成 */
		csvWriter.createCSV(list, date, loginId);
	}


	/**
	 * ロック処理
	 * ※ロック対象は「電話テーブル」「ユーザーテーブル」「Lineテーブル」の３つになります。
	 * ※変更前のデータと変更後のデータに該当する行のロックを行います。
	 *
	 * @param params ロックパラメータ
	 * @throws Exception 例外処理
	 */
	private void lockedConduct(UserAndTelUpdateModel params) throws Exception{

		Map<String, Object> map = null;

		try{
			/* 電話テーブル */
			if(params.getTelId().compareTo(new BigDecimal(0)) != 0){
				map = new HashMap<String, Object>();

				/* 電話（新規） */
				map.put("CUCM_PHONE_ID", params.getTelId());
				locked("CUCM_PHONE", null , map);
			}

			/* Line */
			if(params.getLineId().compareTo(new BigDecimal(0)) != 0){
				map = new HashMap<String, Object>();

				/* ユーザー（新規） */
				map.put("CUCM_LINE_ID", params.getLineId());
				locked("CUCM_LINE", null , map);
			}

			/* ユーザーテーブル */
			if(params.getUserId().compareTo(new BigDecimal(0)) != 0){
				map = new HashMap<String, Object>();

				/* ユーザー（新規） */
				map.put("APP_USER_ID", params.getUserId());
				locked("APP_USER", null , map);
			}
		} catch (ExcludeException e) {}
	}

	/**
	 * 存在チェック
	 *
	 * @param params 登録・更新・削除パラメータ
	 * @return True:エラー無し  False:エラー有り
	 */
	private String exisisCheck(UserAndTelUpdateModel params){

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", params.getTelId());				// 電話ID
		map.put("lineId", params.getLineId());				// ラインID
		map.put("userId", params.getUserId());				//  ユーザーID
		map.put("lineIndex", params.getLineIndex());		// ライン番号
		map.put("sectionId", params.getSectionUserId());	// 店部課
		map.put("companyId", params.getCompanyUserId());	// 会社
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);		// 削除フラグ(削除無し)

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


		/* ユーザーを紐付けていた場合は */
		if(params.getUserId().compareTo(new BigDecimal(0)) != 0){

			/* ユーザーの存在チェック */
			if(appCommonMapper.userExistCheck(map) == 0 ){
				/* エラーメッセージをセット */

				return Constants.ERROR_TYPE_USER;
			}

			/* ユーザーと店部課の紐付き情報の存在チェック */
			if(appCommonMapper.userSectionExistCheck(map) == 0){
				/* エラーメッセージをセット */

				return Constants.ERROR_TYPE_USER;
			}

			/*ユーザーと電話機存在チェック*/
			if(params.getTelId().compareTo(new BigDecimal(0)) != 0 &&
			   appCommonMapper.userTelExistCheck(map) == 0){
				return Constants.ERROR_TYPE_USER;
			}

		}
		return "";
	}

	/**
	 * 個別反映、更新の処理
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @throws Exception 例外処理
	 */
	private String entryUserTel(UserAndTelUpdateModel params, Timestamp timestamp) throws Exception{

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

			/* 電話機の店部課IDと会社IDをモデルにセットする */
			String[] comSec = params.getCompanySectionTelId().split(",");
			params.setCompanyTelId(comSec[0]);
			params.setSectionTelId(comSec[1]);

			/* ロック処理 */
			lockedConduct(params);

			/* 存在チェック */
			errorType = exisisCheck(params);
			if(StringUtils.isEmpty(errorType)){
				/* 存在チェックエラーが存在しなかった場合 */

				/* 更新処理 */
				updateUserTelLineInfo(params, timestamp);
			}

			/* コミット */
			txManager.commit(ts);

		} catch(Exception e){
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			throw e;
		}
		return  errorType;
	}

/**
	 * 個別反映、翌日反映共通処理
	 *
	 * @param params 更新パラメータ
	 * @param timestamp タイムスタンプ
	 */
	private void updateUserTelLineInfo(UserAndTelUpdateModel params, Timestamp timestamp){

		boolean updateRequest = false;

		/* 課金関連情報の変更確認 */
		boolean updflg = inconsistent(new String[] {params.getChargeAssociationBranchId(),params.getOrgChargeAssociationBranchId()},
							  new String[] {params.getChargeAssociationParentSectionId(),params.getOrgChargeAssociationParentSectionId()},
							  new String[] {params.getChargeAssociationSectionId(),params.getOrgChargeAssociationSectionId()},
							  new String[] {params.getChargeRemarks(),params.getOrgChargeRemarks()});

		/* 課金情報の更新 */
		if(!updflg){
			chargeAssociationMapper.updateChargeAssociation(
					setChargeAssociationEntity(params, timestamp));
			updateRequest = true;
		}

		/* VoiceLoggerAssociationの変更確認 */
		updflg = inconsistent(new String[] {params.getLoggerData(),params.getOrgLoggerData()});

		/* VoiceLoggerAssociationの更新 */
		if(!updflg){
			voiceLoggerAssociationMapper.updateVoiceLoggerAssociation(
					setVoiceLoggerAssociationEntity(params, timestamp));
			updateRequest = true;
		}

		/* ユーザーと紐付いていた場合 */
		if(params.getUserId() != null && params.getUserId().compareTo(new BigDecimal(0)) != 0 ){
			/* 電子電話帳関連情報の変更確認 */
			updflg = inconsistent(new String[] {params.getTelDirData(),params.getOrgTelDirData()});

			/* 電子電話帳関連の更新 */
			if(!updflg){
				telDirAssociationMapper.updateTelDirData(
						setTeldirAssociationEntity(params, timestamp));
				updateRequest = true;
			}
		}

		/* UnityAssociation情報の変更確認 */
		updflg = inconsistent(new String[] {params.getVoiceMailFlg(),params.getOrgVoiceMailFlg()});

		/* UnityAssociationの削除 OR 登録 */
		if(!updflg){
			/* VM使用のチェックボックスにて処理を分ける */
			if(params.getVoiceMailFlg().equals(Constants.VOICE_MAIL_FLG_ON)){
				/* Unity関連存在チェック */
				if(appCommonMapper.unityAssociationExistCheck(params.getTelId()) == 0)
					/* チェックがついていた場合データを作成します */
					unityAssociationMapper.insertUnityAssociation(setAssociationEntity(params, timestamp));

			} else {
				/* チェックがついていなかった場合データを削除します */
				UnityAssociationEntity unity = new UnityAssociationEntity();
				unity.setCucmPhoneId(params.getTelId());
				unityAssociationMapper.deleteUnityAssociation(unity);
			}
			updateRequest = true;
		}

		/* Line情報の変更確認 */
		boolean lineUpdflg = inconsistent(new String[] {params.getBusyDestination(),params.getOrgBusyDestination()},
							  new String[] {params.getNoansDestination(),params.getOrgNoansDestination()},
							  new String[] {params.getPickupGroupName(),params.getOrgPickupGroupName()},
							  new String[] {params.getVoiceMailFlg(),params.getOrgVoiceMailFlg()});

		/* 変更があった場合ライン情報の更新を行う */
		if(!lineUpdflg){
			lineMapper.updateLineReflect(setLineEntity(params, timestamp));
			updateRequest = false;
		}

		/* 電話-Line情報の変更確認 */
		updflg = inconsistent(new String[] {params.getExternalPhoneNumberMask(),params.getOrgExternalPhoneNumberMask()},
							  new String[] {params.getDialinNumber(),params.getOrgDialinNumber()},
							  new String[] {params.getLineTextLabel(),params.getOrgLineTextLabe()},
							  new String[] {params.getRingSettingName(),params.getOrgRingSettingName()},
							  new String[] {params.getTelLineRemarks(),params.getOrgTelLineRemarks()});

		/* 電話-Line情報の更新 */
		if(!updflg){
			phoneLineMapper.updatePhoneLine(setPhoneLineEntity(params, timestamp));
			updateRequest = false;
		}

		/* 電話情報の変更確認 */
		updflg = inconsistent(new String[] {params.getPhoneButtonTemplete(),params.getOrgPhoneButtonTemplete()},
									  new String[] {params.getCallingSearchSpaceName(),params.getOrgCallingSearchSpaceName()},
									  new String[] {params.getAddonModuleName1(),params.getOrgAddonModuleName1()},
									  new String[] {params.getAddonModuleName2(),params.getOrgAddonModuleName2()},
									  new String[] {params.getCompanyTelId(),params.getOrgCompanyTelId()},
									  new String[] {params.getSectionTelId(),params.getOrgSectionTelId()},
									  new String[] {params.getBranchTelId(),params.getOrgBranchTelId()});

		/* 電話機情報の更新 */
		if(updateRequest || !updflg){
			phoneMapper.updatePhoneReflect(setPhoneEntity(params,timestamp));
		}

		/* ラインの属性変更又は、CSSの変更を行った場合はfwd系のCSSを変更する */
		if(!lineUpdflg || !inconsistent(new String[] {params.getCallingSearchSpaceName(),params.getOrgCallingSearchSpaceName()})){
			/* CSSの変更を行う */
			lineCssUpdate(params.getTelId(), null, timestamp);
			updateRequest = false;
		}

		/* ### 共用電話の場合 ### */
		if(params.getSharedUse().equals(Constants.ENABLED_SHARED_USE_SHARE)){

			/* 電話機の拠点、店部課の変更を確認する */
			updflg = inconsistent(
					  new String[] {params.getCompanyTelId(),params.getOrgCompanyTelId()},
					  new String[] {params.getSectionTelId(),params.getOrgSectionTelId()},
					  new String[] {params.getBranchTelId(),params.getOrgBranchTelId()});

			/* 変更があった場合はユーザーと電話の紐付け、ユーザーと店部課のヒモ付の拠点、店部課を変更する */
			if(!updflg){
				/* ユーザーとセクションの紐付きを削除します。 */
				userSectionMapper.deleteUserSection(
						setUserSectionEntityPlaceInfo(params, timestamp));

				/* 店部課情報の取得 */
				Map<String, Object> sectionMap = userAndTelSearchMapper.getSharedUserSectionInfo(params.getTelId());
				/* 取得したパラメータをモデルにセット */
				params.setSectionUserName(sectionMap.get("sectionName").toString());
				params.setPrintOrder(sectionMap.get("printOrder").toString());
				params.setCompanyUserId(params.getCompanyTelId());
				params.setSectionUserId(params.getSectionTelId());

				/* ユーザーとセクションの紐付きを登録します。 */
				userSectionMapper.insertUserSection(setUserSectionEntity(params, timestamp));

				/* ユーザーと電話の紐付き情報の会社、店部課を更新します。 */
				userPhoneMapper.updateUserPhoneSection(
						setUserPhoneEntitySharedInfo(params, timestamp));
			}
		}
	}

	/**
	 * ユーザーと電話の紐付き削除処理
	 *
	 * @param params 削除パラメータ
	 * @param timestamp タイムスタンプ
	 */
	private void userTelDelete(UserAndTelUpdateModel params, Timestamp timestamp){

		/* 一般ユーザー、共有ユーザー別削除処理 */
		if(params.getSharedUse().equals(Constants.ENABLED_SHARED_USE_PRIVATE)){
			/* 一般ユーザーの場合 */

			/* 電話とユーザーの紐付きを論理削除 */
			userPhoneMapper.updateUserPhoneDelete(
					setUserTelEntityDeleteInfo(params, timestamp, Constants.COM_FLG_ON));

			/* TelDirDataの削除フラグを立てる */
			telDirAssociationMapper.updateTelDirDataDelete(
					setTeldirAssociationEntityDeleteInfo(
							params.getTelId(), null, params.getUserId(), timestamp, Constants.COM_FLG_ON));

			/* 変更後の削除予約フラグ更新処理  */
			updateDeleteReserve(params.getUserId(), timestamp);

			/* 内線番号更新処理 */
			updateTelNumber(params.getUserId(), timestamp);

		} else if(params.getSharedUse().equals(Constants.ENABLED_SHARED_USE_SHARE)) {
			/* 共有電話の場合 */

			/* 電話とユーザーの紐付きを物理削除 */
			UserPhoneEntity userPhone = new UserPhoneEntity();
			userPhone.setAppUserId(params.getUserId());		// ユーザーID
			userPhone.setCucmPhoneId(params.getTelId());	// 電話ID
			userPhoneMapper.deleteUserPhone(userPhone);

			/* 電子電話帳関連の物理削除 */
			TelDirAssociationEntity telDir = new TelDirAssociationEntity();
			telDir.setAppUserId(params.getUserId());	// ユーザーID
			telDir.setCucmPhoneId(params.getTelId());	// 電話ID
			telDirAssociationMapper.deleteTelDirData(telDir);

			/* ユーザー店部課の紐付きを物理削除 */
			UserSectionEntity userSection = new UserSectionEntity();
			userSection.setAppUserId(params.getUserId());			// ユーザーID
			userSection.setSectionId(params.getSectionUserId());	// 店部課Id
			userSection.setCompanyId(params.getCompanyUserId());	// 会社ID
			userSectionMapper.deleteUserSection(userSection);

			/* ユーザー情報の物理削除 */
			UserEntity user = new UserEntity();
			user.setAppUserId(params.getUserId());		// ユーザーID
			userMapper.deleteAppUser(user);
		}
	}

	/**
	 * ユーザーと電話の紐付き削除処理
	 * ※電話に該当する全てのユーザーを削除します。
	 *
	 * @param params 紐付き削除パラメータ
	 * @param timestamp タイムスタンプ
	 * @throws Exception 例外処理
	 */
	private void userTelAllDelete(UserAndTelUpdateModel params, Timestamp timestamp) throws Exception{
		/* SQL用マップ */
		Map<String, Object> sqlMap = new HashMap<String, Object>();

		/* 電話機に紐づくユーザー情報を取得 */
		sqlMap.put("telId", params.getTelId());
		sqlMap.put("deleted", Constants.COM_FLG_OFF);
		List<Map<String, Object>> userList = appCommonMapper.getUserLinkedTel(sqlMap);

		/* 取得したユーザーを全て論理削除する */
		for(Map<String, Object> userMap : userList ){

			try{
				/* ユーザー情報をモデルにセットする */
				params.setUserId(new BigDecimal(String.valueOf(userMap.get("userId"))));
				params.setSharedUse((String)userMap.get("sharedUse"));

				/* ユーザーのロック処理 */
				sqlMap = new HashMap<String, Object>();
				sqlMap.put("APP_USER_ID", params.getUserId());
				locked("APP_USER", null , sqlMap);

				/* 電話機－ユーザー更新処理 */
				userTelDelete(params, timestamp);

			}catch (ExcludeException e){}
		}

		/* 電話機に紐づくユーザー情報の更新 */
		updateTelOwnerUser(params.getTelId(), timestamp);
	}

/**
	 * 電話とラインの紐付き削除処理
	 *
	 * @param params 紐付き削除パラメータ
	 * @param timestamp タイムスタンプ
	 */
	private void telLineDelete(UserAndTelUpdateModel params, Timestamp timestamp ){
		/* SQL用マップ */
		Map<String, Object> sqlMap = new HashMap<String, Object>();


		/* 電話－ラインの紐付けを論理削除 */
		phoneLineMapper.updatePhoneLineDelete(
				setPhoneLineEntityDeleteInfo(params, timestamp, Constants.COM_FLG_ON));

		/* 電子電話帳関連の削除 ※電話、ラインに該当する全てのデータを削除 */
		telDirAssociationMapper.updateTelDirDataDelete(
				setTeldirAssociationEntityDeleteInfo(
						params.getTelId(), params.getLineId(), null, timestamp, Constants.COM_FLG_ON));

		/* 削除対象のラインが対象の電話機以外に紐付いているかチェックする */
		sqlMap.put("lineId", params.getLineId());
		sqlMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		if(appCommonMapper.telLineExistCheck(sqlMap) == 0){
			/* 紐付きが存在しない場合 */

			/* ラインの削除 */
			lineMapper.updateLineDelete(
					setLineEntityDeleteInfo(params, timestamp, Constants.COM_FLG_ON));

			/* 通録関連の削除 */
			voiceLoggerAssociationMapper.updateVoiceLoggerAssociationDelete(
					setVoiceLoggerAssociationEntityDeleteInfo(
								params, timestamp, Constants.COM_FLG_ON));

			/* 課金関連の削除 */
			chargeAssociationMapper.updateChargeAssociationDelete(
					setChargeAssociationEntityDeleteInfo(
							params, timestamp, Constants.COM_FLG_ON));

		} else {
			/* 紐付きが存在する場合 */

			/* ラインのCSSを更新 */
			lineCssUpdate(null, params.getLineId(), timestamp);
		}
	}

	/**
	 * 電話とラインの紐付き削除処理
	 * ※電話機に該当するラインを全て削除します
	 *
	 * @param params 紐付き削除パラメータ
	 * @param timestamp タイムスタンプ
	 * @throws Exception 例外処理
	 */
	private void telLineAllDelete(UserAndTelUpdateModel params, Timestamp timestamp ) throws Exception{
		/* SQL用マップ */
		Map<String, Object> sqlMap = new HashMap<String, Object>();

		/* 電話機に紐づくライン情報を取得 */
		sqlMap.put("telId", params.getTelId());
		sqlMap.put("deleted", Constants.COM_FLG_OFF);
		List<Map<String, Object>> lineList = appCommonMapper.getLineLinkedTel(sqlMap);

		/* 取得したラインを全て論理削除する */
		for(Map<String, Object> lineMap : lineList ){

			/* ライン情報をモデルにセットする */
			params.setLineId(new BigDecimal(String.valueOf(lineMap.get("lineId"))));
			params.setLineIndex(String.valueOf(lineMap.get("lineIndex")));

			/* ラインのロック処理 */
			sqlMap = new HashMap<String, Object>();
			sqlMap.put("CUCM_LINE_ID", params.getLineId());
			locked("CUCM_LINE", null , sqlMap);

			/* 電話－ライン紐付き削除処理 */
			telLineDelete(params, timestamp);
		}
	}

	/**
	 * 電話機の削除処理
	 *
	 * @param params 削除パラメータ
	 * @param timestamp タイムスタンプ
	 */
	private void telDelete(UserAndTelUpdateModel params, Timestamp timestamp ){

		/* 電話機の論理削除処理 */
		phoneMapper.updatePhoneDelete(setPhoneEntityDeleteInfo(params, timestamp, Constants.COM_FLG_ON));

		/* Unity関連の物理削除 */
		UnityAssociationEntity unity = new UnityAssociationEntity();
		unity.setCucmPhoneId(params.getTelId());
		unityAssociationMapper.deleteUnityAssociation(unity);
	}

	/**
	 * ラインのCSS項目の更新を行います。
	 *
	 * @param telId 電話機ID
	 * @param lineId ラインID
	 * @param timestamp タイムスタンプ
	 */
	private void lineCssUpdate(BigDecimal telId, BigDecimal lineId, Timestamp timestamp){
		Map<String, Object> lineMap = new HashMap<String, Object>();
		lineMap.put("cucmUpdateRequestFlag",Constants.CUCM_UPDATE_FLG_WAIT_2);
		lineMap.put("lstupdtTmstmp",timestamp);
		lineMap.put("COM_FLG_OFF", Constants.COM_FLG_OFF);

		/* CSSを取得する条件が電話IDかLineIDかで処理を分ける */
		if(telId != null && telId.compareTo(new BigDecimal(0)) != 0){
			/* 条件が電話IDの場合 */
			lineMap.put("cucmPhoneId", telId);
			lineMapper.updateLineCssPhone(lineMap);
		} else {
			/* 条件がLineIDの場合 */
			lineMap.put("cucmLineId", lineId);
			lineMapper.updateLineCssLine(lineMap);

		}
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
	 * 
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
	 * 電話機テーブルモデルに値をセットします
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneEntity setPhoneEntity(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		PhoneEntity entity = new PhoneEntity();

		entity.setCucmPhoneId(params.getTelId());								// 電話ID
		entity.setPhoneProductName(params.getTelTypeModel());					// 電話機種
		entity.setMacAddress(params.getMacAddress());							// MacAddress
		entity.setDescription(params.getDirectoryNumber());						// 説明
		entity.setCallingSearchSpaceName(params.getCallingSearchSpaceName());	// CallingSearchSpaceName
		entity.setLocationName(params.getBranchTelId());						// ロケーション名
		entity.setDevicePoolName(params.getBranchTelId() + "_" +
				params.getTelTypeModel().substring(
						params.getTelTypeModel().length() - 4,
						params.getTelTypeModel().length()));					// デバイスプール名 拠点＋"_"+電話機種名の後ろから４文字
		entity.setPhoneButtonTemplateName(params.getPhoneButtonTemplete());		// 電話ボタン
		entity.setAddonModuleName1(params.getAddonModuleName1());				// 拡張モジュール1設定
		entity.setAddonModuleName2(params.getAddonModuleName2());				// 拡張モジュール2設定
		entity.setCompanyId(params.getCompanyTelId());							// 会社ID
		entity.setSectionId(params.getSectionTelId());							// 店部課ID
		entity.setBranchId(params.getBranchTelId());							// 拠点ID
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);		// 更新フラグ
		entity.setCrtTmstmp(timestamp);											// 作成日時
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

/**
	 * 電話機テーブルモデルに値をセットします
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneEntity setPhoneEntityDeleteInfo(UserAndTelUpdateModel params, Timestamp timestamp, String deleteFlg){
		/* インスタンス化 */
		PhoneEntity entity = new PhoneEntity();

		entity.setCucmPhoneId(params.getTelId());								// 電話ID
		entity.setDeleted(deleteFlg);											// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);		// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);										// 更新日時

		return entity;
	}

	/**
	 * ラインテーブルモデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntity(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(params.getLineId());								// ラインID
		entity.setDirectoryNumber(params.getDirectoryNumber());					// 内線番号
		entity.setFwdBusyDestination(params.getBusyDestination());				// 話中転送
		entity.setFwdNoansDestination(params.getNoansDestination());			// 不応答転送
		entity.setCallPickupGroupName(params.getPickupGroupName());				// ピックアップグループ
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
	 * ※論理削除、物理削除用
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private LineEntity setLineEntityDeleteInfo(UserAndTelUpdateModel params, Timestamp timestamp, String deleteFlg){
		/* インスタンス化 */
		LineEntity entity = new LineEntity();

		entity.setCucmLineId(params.getLineId());							// ラインId
		entity.setDeleted(deleteFlg);										// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}

	/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntity(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(params.getTelId());								// ユーザーID
		entity.setCucmLineId(params.getLineId());								// 電話ID
		entity.setCucmLineId(params.getLineId());								// ラインID
		entity.setIndex(params.getLineIndex());									// 連番
		entity.setExternalPhoneNumberMask(params.getExternalPhoneNumberMask());	// ExternalPhoneNumberMask
		entity.setDialin(params.getDialinNumber());								// ダイアルイン
		entity.setLineTextLabel(params.getLineTextLabel());						// LineTextLabel
		entity.setRingsettingName(params.getRingSettingName());					// 鳴動設定
		entity.setRemarks(params.getTelLineRemarks());							// 備考
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);		// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);								// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);								// エラーフラグ
		entity.setLstupdtTmstmp(timestamp);										// 最終更新日

		return entity;
	}

	/**
	 * 電話ラインテーブルモデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private PhoneLineEntity setPhoneLineEntityDeleteInfo(
			UserAndTelUpdateModel params, Timestamp timestamp, String deleteFlg){
		/* インスタンス化 */
		PhoneLineEntity entity = new PhoneLineEntity();

		entity.setCucmPhoneId(params.getTelId());							// 電話ID
		entity.setCucmLineId(params.getLineId());							// ラインID
		entity.setIndex(params.getLineIndex());								// 連番
		entity.setDeleted(deleteFlg);										// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}

	/**
	 * Userテーブルモデルに値をセットします
	 * ※共用電話用
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserEntity setUserEntitySharedUse(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタン化 */
		UserEntity entity = new UserEntity();

		/* 共有ユーザー名 */
		String sharedName = Constants.SHARED_USE_NAME + params.getUserId().toString();

		entity.setAppUserId(params.getUserId());							// ユーザーID
		entity.setEnabledSharedUse(Constants.ENABLED_SHARED_USE_SHARE);		// 共有ユーザー区分
		entity.setFulltimeEmployee(Constants.FULLTIME_EMPLOYEE_SHARED_USE);	// 正社員区分
		entity.setBizEmployeeId(sharedName);								// 人事情報側ユーザー区分
		entity.setNameKanji(params.getSharedUserName());					// ユーザー名・漢字
		entity.setFirstName(sharedName);									// ユーザー名・性
		entity.setLastName(sharedName);										// ユーザー名・名
		entity.setPin(Constants.PIN_SHARED_USE);							// PIN
		entity.setDeleted(Constants.COM_FLG_OFF);							// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_FINISHED);// 更新フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);							// エラーフラグ
		entity.setLstupdtpwd(timestamp);									// ラストパスワード設定日
		entity.setCrtTmstmp(timestamp);										// 作成日
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
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
	 * @param updReqFlg 更新フラグ
	 * @return エンティティパラメータ
	 */
	private UserPhoneEntity setUserPhoneEntity(UserAndTelUpdateModel params, Timestamp timestamp, String updReqFlg){
		/* インスタンス化 */
		UserPhoneEntity entity = new UserPhoneEntity();

		entity.setAppUserId(params.getUserId());				// ユーザーID
		entity.setCucmPhoneId(params.getTelId());				// 電話ID
		entity.setCompanyId(params.getCompanyUserId());			// 会社ID（ユーザー）
		entity.setSectionId(params.getSectionUserId());			// 店部課ID（ユーザー）
		entity.setCucmUpdateRequestFlag(updReqFlg);				// 更新フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);				// 削除フラグ
		entity.setErrorFlg(Constants.COM_FLG_OFF);				// エラーフラグ
		entity.setCrtTmstmp(timestamp);							// 登録日
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}

	/**
	 * User_Phoneテーブルモデルに値をセットします。
	 * ※共用ユーザーの場合
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserPhoneEntity setUserPhoneEntitySharedInfo(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		UserPhoneEntity entity = new UserPhoneEntity();

		entity.setAppUserId(params.getUserId());				// ユーザーID
		entity.setCucmPhoneId(params.getTelId());				// 電話ID
		entity.setCompanyId(params.getCompanyUserId());			// 会社ID（ユーザー）
		entity.setSectionId(params.getSectionUserId());			// 店部課ID（ユーザー）
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}

/**
	 * User_Phoneテーブルモデルに値をセットします。
	 * ※変更前のUserPhoneデータを論理削除する場合
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @param deleteFlag 削除フラグ
	 * @return エンティティパラメータ
	 */
	private UserPhoneEntity setUserTelEntityDeleteInfo(UserAndTelUpdateModel params, Timestamp timestamp, String deleteFlag){
		/* インスタンス化 */
		UserPhoneEntity entity = new UserPhoneEntity();

		entity.setAppUserId(params.getUserId());							// ユーザーID
		entity.setCucmPhoneId(params.getTelId());							// 電話ID
		entity.setDeleted(deleteFlag);										// 削除フラグ
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);									// 最終更新日

		return entity;
	}



	/**
	 * UserSectionテーブルモデルに値をセットします。
	 * ※店部課名はSQLにて取得
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserSectionEntity setUserSectionEntity(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		UserSectionEntity entity = new UserSectionEntity();

		entity.setAppUserId(params.getUserId());				// ユーザーID
		entity.setCompanyId(params.getCompanyUserId());			// 会社ID
		entity.setSectionId(params.getSectionUserId());			// 店部課
		entity.setSectionName(params.getSectionUserName());		// 店部課名
		entity.setPrintOrder(params.getPrintOrder());			// プリント順
		entity.setDeleteReserve(Constants.COM_FLG_OFF);			// 予約削除フラグ
		entity.setDeleted(Constants.COM_FLG_OFF);				// 削除フラグ
		entity.setCrtTmstmp(timestamp);							// 作成日時
		entity.setLstupdtTmstmp(timestamp);						// 最終更新日

		return entity;
	}

	/**
	 * UserSectionテーブルモデルに値をセットします。
	 * ※削除時の値をセット
	 * 
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UserSectionEntity setUserSectionEntityPlaceInfo(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		UserSectionEntity entity = new UserSectionEntity();

		entity.setAppUserId(params.getUserId());					// ユーザーID
		entity.setCompanyId(params.getOrgCompanyTelId());			// 会社ID
		entity.setSectionId(params.getOrgSectionTelId());			// 店部課

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

		entity.setAppUserId(userId);						// ユーザーID
		entity.setDeleteReserve(Constants.COM_FLG_OFF);		// 予約削除フラグ
		entity.setDeleted(Constants.COM_FLG_ON);			// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);					// 最終更新日

		return entity;
	}

	/**
	 * 課金関連モデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private ChargeAssociationEntity setChargeAssociationEntity(UserAndTelUpdateModel params, Timestamp timestamp){

		/* インスタンス化 */
		ChargeAssociationEntity entity = new ChargeAssociationEntity();

		entity.setCucmLineId(params.getLineId());									// ラインID
		entity.setStatusCode(Constants.COM_FLG_OFF);								// ステータス
		entity.setBranchId(params.getChargeAssociationBranchId());					// 拠点ID
		entity.setCompanyId(params.getCompanyTelId());								// 会社ID
		entity.setParentSectionId(params.getChargeAssociationParentSectionId());	// 親店部課ID
		entity.setSectionId(params.getChargeAssociationSectionId());				// 店部課ID
		entity.setRemarks(params.getChargeRemarks());								// 備考
		entity.setDeleted(Constants.COM_FLG_OFF);									// 削除フラグ
		entity.setCrtTmstmp(timestamp);												// 作成日時
		entity.setLstupdtTmstmp(timestamp);											// 更新日時

		return entity;
	}

	/**
	 * 課金関連モデルに値をセットします。
	 * ※論理削除、物理削除用
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private ChargeAssociationEntity setChargeAssociationEntityDeleteInfo(
			UserAndTelUpdateModel params, Timestamp timestamp, String deleteFlg){

		/* インスタンス化 */
		ChargeAssociationEntity entity = new ChargeAssociationEntity();

		entity.setCucmLineId(params.getLineId());	// ラインID
		entity.setDeleted(deleteFlg);				// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);			// 更新日時

		return entity;
	}

	/**
	 * VoiceLoggerAssociationモデルに値をセットします。
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private VoiceLoggerAssociationEntity setVoiceLoggerAssociationEntity(
			UserAndTelUpdateModel params, Timestamp timestamp){

		/* インスタンス化 */
		VoiceLoggerAssociationEntity entity = new VoiceLoggerAssociationEntity();

		entity.setCucmLineId(params.getLineId());			// ラインID
		entity.setLoggerData(params.getLoggerData());		// LoggerData
		entity.setDeleted(Constants.COM_FLG_OFF);			// 削除フラグ
		entity.setCrtTmstmp(timestamp);						// 作成日時
		entity.setLstupdtTmstmp(timestamp);					// 更新日時

		return entity;
	}

	/**
	 * VoiceLoggerAssociationモデルに値をセットします。
	 * ※論理削除、物理削除用
	 *
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @param deleteFlg 削除フラグ
	 * @return エンティティパラメータ
	 */
	private VoiceLoggerAssociationEntity setVoiceLoggerAssociationEntityDeleteInfo(
					UserAndTelUpdateModel params, Timestamp timestamp, String deleteFlg){

		/* インスタンス化 */
		VoiceLoggerAssociationEntity entity = new VoiceLoggerAssociationEntity();

		entity.setCucmLineId(params.getLineId());	// ラインID
		entity.setDeleted(deleteFlg);				// 削除フラグ
		entity.setLstupdtTmstmp(timestamp);			// 更新日時

		return entity;
	}


	/**
	 * 電子電話帳関連モデルに値をセットします。
	 * 
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private TelDirAssociationEntity setTeldirAssociationEntity(
					UserAndTelUpdateModel params, Timestamp timestamp){

		/* インスタンス化 */
		TelDirAssociationEntity entity = new TelDirAssociationEntity();

		entity.setCucmPhoneId(params.getTelId());		// 電話ID
		entity.setCucmLineId(params.getLineId());		// ラインID
		entity.setAppUserId(params.getUserId());		// ユーザーID
//		entity.setStatusCode("");						// ステータスコード
//		entity.setAssociateCode("");					// AssociateCode
		entity.setTelDirData(params.getTelDirData());	// TelDirData
		entity.setDeleted(Constants.COM_FLG_OFF);		// 削除フラグ
		entity.setCrtTmstmp(timestamp);					// 作成日時
		entity.setLstupdtTmstmp(timestamp);				// 更新日時

		return entity;
	}

/**
	 * 電子電話帳関連モデルに値をセットします。
	 * ※論理削除、物理削除用
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
	 * UnityAssociationモデルに値をセットします。
	 * 
	 * @param params 登録・更新データ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private UnityAssociationEntity setAssociationEntity(UserAndTelUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		UnityAssociationEntity entity = new UnityAssociationEntity();

		entity.setCucmPhoneId(params.getTelId());						// 電話ID
		entity.setUnityData(Constants.UNITY_DATA);						// unity_data
		entity.setVoiceMailProfileClass(params.getVoiceMailFlg());		// VMフラグ
		entity.setDeleted(Constants.COM_FLG_OFF);						// 削除フラグ
		entity.setCrtTmstmp(timestamp);									// 作成日時
		entity.setLstupdtTmstmp(timestamp);								// 更新日時

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

	
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BaseConstants.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.ksc.batch.util;


/**
 * <pre>
 * 定数定義クラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
public class BaseConstants {

	/**
	 * 共通フラグ：OFF
	 */
	public static final String COM_FLG_OFF = "0";

	/**
	 * 共通フラグ：ON
	 */
	public static final String COM_FLG_ON = "1";

	/**
	 * 共通フラグ：OFF記号
	 */
	public static final String COM_FLG_OFF_MARK = "×";
	/**
	 * 共通フラグ：ON記号
	 */
	public static final String COM_FLG_ON_MARK  = "○";

	/**
	 * エスケープ文字列
	 */
	public static final String ESCAPE = "ESCAPE '\\'";

	/** 使用状況 */
	public static final String TEL_USAGES_DEFAULT       = "1"; // 一般
	public static final String TEL_USAGES_COMMON_USER   = "2"; // 共有ユーザー
	public static final String TEL_USAGES_SHARED_LINE   = "3"; // シェアードライン
	public static final String TEL_USAGES_MULTI_DEVICE  = "4"; // マルチデバイス
	public static final String TEL_USAGES_MULTI_LINE    = "5"; // マルチライン
	public static final String TEL_USAGES_SHARED_TEL    = "6"; // 共用電話機
	public static final String TEL_USAGES_NOT_LINK_TEL  = "7"; // 電話機と紐付いていないユーザ
	public static final String TEL_USAGES_NOT_LINK_USER = "8"; // 部内在庫電話機
	public static final String TEL_USAGES_MANY          = "9"; // 複数対複数

	/** 使用状況 コード */
	public static final String TEL_USAGES_STATUS_SINGLE  = "0";	// 単一つながり
	public static final String TEL_USAGES_STATUS_MANY    = "1";	// 複数つながり
	public static final String TEL_USAGES_STATUS_NOTHING = "9";	// つながり無し

	/** 共有ユーザー区分 */
	public static final String ENABLED_SHARED_USE_PRIVATE = "0";	// 個別ユーザー
	public static final String ENABLED_SHARED_USE_SHARE   = "1";	// 共有ユーザー

	/** 正社員区分 */
	public static final String FULLTIME_EMPLOYEE_SHARED_USE = "8";	// 共有ユーザー

	/**
	 * 通話録音(LOGGER_DATA)
	 */
	public static final String LOGGER_DATA_ALL        = "";
	public static final String LOGGER_DATA_OFF        = "0";
	public static final String LOGGER_DATA_ON         = "1";

	public static final String LOGGER_DATA_ALL_NAME   = "全件";
	public static final String LOGGER_DATA_ON_NAME    = "全録";
	public static final String LOGGER_DATA_OFF_NAME   = "なし";

	/**
	 * CUCM更新依頼フラグ
	 */
	public static final String CUCM_UPDATE_FLG_FINISHED    = "0";		// 更新済み
	public static final String CUCM_UPDATE_FLG_WAIT        = "1";		// 更新待ち
	public static final String CUCM_UPDATE_FLG_WAIT_2      = "2";		// 更新待ち
	public static final String CUCM_UPDATE_FLG_WAIT_3      = "3";		// バッチで更新時：空

	/**
	 * 反映状況
	 */
	public static final String REFLECTION_ALL            = "";
	public static final String REFLECTION_ERROR          = "1";
	public static final String REFLECTION_WAIT           = "2";
	public static final String REFLECTION_FINISHED       = "3";
	public static final String REFLECTION_BATCH          = "4";

	public static final String REFLECTION_ALL_NAME       = "全件";
	public static final String REFLECTION_ERROR_NAME     = "エラー";
	public static final String REFLECTION_WAIT_NAME      = "反映待";
	public static final String REFLECTION_FINISHED_NAME  = "反映済";
	public static final String REFLECTION_BATCH_NAME     = "";

	/* 電話機 */
	public static final String TEL_TYPE_MODEL_CTI_PORT = "CTI port";

	/* 電話機区分 */
	public static final String PHONE_TYPE_IP             = "1";
	public static final String PHONE_TYPE_FMC            = "2";
	public static final String PHONE_TYPE_COMBINED_USE = "3";

	/* 電話機区分ラベル */
	public static final String PHONE_TYPE_IP_NAME                 = "IP電話";
	public static final String PHONE_TYPE_FMC_NAME                = "FMC端末";
	public static final String PHONE_TYPE_COMBINED_USE_NAME      = "固定併用";

	/* 代表ピックアップ */
	public static final int PICKUP_OFF = 0;
	public static final int PICKUP_ON  = 1;

	/* 代表ピックアップラベル */
	public static final String PICKUP_OFF_NAME = "設定なし";
	public static final String PICKUP_ON_NAME  = "設定あり";

	/**
	 * Sort条件
	 */
//	public static final String DEFAULT_SORT =
//			"v_search_info.directoryNumber," +								// 内線番号
//			"v_search_info.branchTelId," +									// 拠点（電話）
//			"v_search_info.companyTelId,v_search_info.sectionTelId," +		// 会社、店部課ID（電話）
//			"v_search_info.companyUserId,v_search_info.sectionUserId," +	// 会社、店部課ID（ユーザー）
//			"v_search_info.telTypeModel,v_search_info.kanjiUserName";		// 電話機種、ユーザー名
	public static final String DEFAULT_SORT =
			"v_search_info.directoryNo," +
			"v_search_info.phoneBranchId," +
			"v_search_info.phoneSectionId," +
			"v_search_info.userSectionId," +
			"v_search_info.addonModuleId1,v_search_info.userNmKanji";

}
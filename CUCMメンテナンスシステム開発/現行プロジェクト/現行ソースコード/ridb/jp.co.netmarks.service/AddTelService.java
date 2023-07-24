/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddTelService.java
 *
 * @date 2013/09/06
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ksc.service.BaseService;
import jp.co.netmarks.model.AddTelModel;
import jp.co.netmarks.model.AddTelResultModel;
import jp.co.netmarks.persistence.AddTelMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 電話機の検索と選択画面用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/06 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/06
 */
@Service
public class AddTelService extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AddTelService.class);

	@Autowired
	private AddTelMapper addTelMapper;

	/** ソート対象カラムをセット */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("telStatusName","V_SEARCH_INFO.telStatusName");						// ステータス
		sortMap.put("directoryNumber","V_SEARCH_INFO.directoryNumber");					// 内線番号
		sortMap.put("dialin","V_SEARCH_INFO.dialin");									// ダイアルイン
		sortMap.put("telTypeModel","V_SEARCH_INFO.telTypeModel");						// 電話機種
		sortMap.put("macAddress","V_SEARCH_INFO.macAddress");							// MACアドレス
		sortMap.put("kanjiUserName","V_SEARCH_INFO.kanjiUserName");						// 利用者名
		sortMap.put("branchTelName","V_SEARCH_INFO.branchTelName");						// 拠点
		sortMap.put("sectionTelName","V_SEARCH_INFO.sectionTelName");					// 所属店部課
		sortMap.put("chargeAssociationPlace","V_SEARCH_INFO.chargeAssociationBranchId " +
				                             ", V_SEARCH_INFO.chargeAssociationParentSectionId " +
				                             ", V_SEARCH_INFO.chargeAssociationSectionId");	// 課金先
	}

	/** デフォルトソート */
	private final String DEFAULT_SORT =
			"V_SEARCH_INFO.directoryNumber "  +				// 内線番号
			", V_SEARCH_INFO.branchTelId "    +				// 拠点（電話）
			", V_SEARCH_INFO.companyTelId  " +
			", V_SEARCH_INFO.sectionTelId  "  +				// 会社、店部課ID（電話）
			", V_SEARCH_INFO.companyUserId " +
			", V_SEARCH_INFO.sectionUserId "  +				// 会社、店部課ID（ユーザー）
			", V_SEARCH_INFO.macAddress " 	  +				// MACアドレス
			", V_SEARCH_INFO.userId";						// ユーザーId


	/**
	 * 検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getTelAddTotal(AddTelModel params){

		return addTelMapper.getTelAddTotal(params);
	}

	/**
	 * 検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<AddTelResultModel> getTelAddList(AddTelModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		return addTelMapper.getTelAddList(params);

	}
}


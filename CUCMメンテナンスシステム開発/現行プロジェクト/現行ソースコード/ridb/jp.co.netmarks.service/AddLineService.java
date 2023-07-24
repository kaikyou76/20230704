/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddLineService.java
 *
 * @date 2013/09/09
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ksc.service.BaseService;
import jp.co.netmarks.model.AddLineModel;
import jp.co.netmarks.model.AddLineResultModel;
import jp.co.netmarks.persistence.AddLineMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * ラインの検索と選択画面用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/09 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/09
 */
@Service
public class AddLineService  extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AddLineService.class);

	@Autowired
	private AddLineMapper addLineMapper;

	/** ソート対象カラムをセット */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("directoryNumber","V_SEARCH_INFO.directoryNumber");		// ステータス
		sortMap.put("busyDestination","V_SEARCH_INFO.busyDestination");		// 内線番号
		sortMap.put("noansDestination","V_SEARCH_INFO.noansDestination");	// ダイアルイン
		sortMap.put("pickupGroupName","V_SEARCH_INFO.pickupGroupName");		// 電話機種
		sortMap.put("chargeAssociationPlace","V_SEARCH_INFO.chargeAssociationBranchId" +
                ", V_SEARCH_INFO.chargeAssociationParentSectionId" +
                ", V_SEARCH_INFO.chargeAssociationSectionId");				// 課金先
		sortMap.put("voiceMailFlgName","V_SEARCH_INFO.voiceMailFlg");			// 利用者名
	}

	private final String DEFAULT_SORT = "V_SEARCH_INFO.directoryNumber";	// 内線番号

	/**
	 * 検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getLineAddTotal(AddLineModel params){

		return addLineMapper.getLineAddTotal(params);
	}

	/**
	 * 検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<AddLineResultModel> getLineAddList(AddLineModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + "," + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		return addLineMapper.getLineAddList(params);

	}
}
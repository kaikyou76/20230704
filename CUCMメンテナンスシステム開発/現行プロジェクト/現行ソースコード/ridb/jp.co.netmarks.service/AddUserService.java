/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AddUserService.java
 *
 * @date 2013/08/30
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ksc.service.BaseService;
import jp.co.netmarks.model.AddUserModel;
import jp.co.netmarks.model.AddUserResultModel;
import jp.co.netmarks.persistence.AddUserMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * ユーザーの検索と選択用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/30 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/30
 */
@Service
public class AddUserService extends BaseService {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AddUserService.class);

	@Autowired
	private AddUserMapper addUserMapper;

	/** ソート対象カラムをセット */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("userKanjiName","V_SEARCH_INFO.userKanjiName");
		sortMap.put("sectionName","V_SEARCH_INFO.sectionName");
		sortMap.put("retainTelStatusName","V_SEARCH_INFO.retainTelStatusName");
	}

	/** デフォルトソート順 */
	private final String DEFAULT_SORT = "V_SEARCH_INFO.sectionId , V_SEARCH_INFO.companyId "
			                          + ", V_SEARCH_INFO.userKanjiName , V_SEARCH_INFO.userId";

	/**
	 * 検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getUserAddTotal(AddUserModel params){

		return addUserMapper.getUserAddTotal(params);
	}

	/**
	 * 検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<AddUserResultModel> getUserAddList(AddUserModel params){

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		return addUserMapper.getUserAddList(params);

	}

}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MaintenanceService.java
 *
 * @date 2013/09/19
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.ksc.util.LogHelpUtil;
import jp.co.netmarks.model.MaintenanceResultModel;
import jp.co.netmarks.model.MaintenanceSearchModel;
import jp.co.netmarks.model.MaintenanceUpdateModel;
import jp.co.netmarks.model.entity.BranchEntity;
import jp.co.netmarks.model.entity.SectionBranchEntity;
import jp.co.netmarks.persistence.BranchMapper;
import jp.co.netmarks.persistence.MaintenanceMapper;
import jp.co.netmarks.persistence.SectionBranchMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * メンテナンス用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/19 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/19
 */
@Service
public class MaintenanceService extends BaseService {

	private static Log log = LogFactory.getLog(MaintenanceService.class);

	@Autowired
	private AppCommonService appCommonService;

	@Autowired
    private Properties properties;

	@Autowired
	private MaintenanceMapper maintenanceMapper;

	/** ##### エンティティ関連 ##### */
	@Autowired
	private BranchMapper branchMapper;

	@Autowired
	private SectionBranchMapper sectionBranchMapper;

	/** 検索：ソート対象カラム */
	private static Map<String, String> sortMap = new HashMap<String, String>();
	static {
		sortMap.put("branchId","M_BRANCH.BRANCH_ID");
		sortMap.put("branchName","M_BRANCH.BRANCH_NAME");
		sortMap.put("clusterName","M_BRANCH.CLUSTER_ID");
		sortMap.put("companyId","V_ORGANIZATION_LEVEL.CHILD_COMPANY_ID");
		sortMap.put("sectionId","V_ORGANIZATION_LEVEL.CHILD_SECTION_ID");
		sortMap.put("sectionName","V_ORGANIZATION_LEVEL.CHILD_SECTION_NAME");
	}

	/** 検索：デフォルトソート */
	private final String DEFAULT_SORT =
				"M_BRANCH.BRANCH_ID ," +							// 拠点コード
				"V_ORGANIZATION_LEVEL.CHILD_SECTION_ID";			// 店部課コード

	/**
	 * 検索件数を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索件数
	 */
	public int getBranchTotal(MaintenanceSearchModel params){
		return maintenanceMapper.getBranchTotal(params);
	}


	/**
	 * 検索結果を取得します。
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<MaintenanceResultModel> getBranchList(MaintenanceSearchModel params) {

		/* ソート設定 */
		if (params.getSort() != null) {
			params.setSort(sortMap.get(params.getSort()) + " , " + DEFAULT_SORT);
		} else {
			params.setSort(DEFAULT_SORT);		// デフォルトのソース条件
		}

		return maintenanceMapper.getBranchList(params);
	}


	/**
	 * 拠点新規追加処理
	 *
	 * @param params 登録パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> addNewBranch(MaintenanceUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* 拠点の新規追加を行う */
		try {
			branchMapper.insertBranch(setBranchEntity(params,timestamp));
		} catch (Exception e) {
			map.put("errorMessage", properties.getProperty("add.branch.exception"));
			/* スタックトレースを出力 */
			log.error(LogHelpUtil.getStackTrace(e));
			return map;
		}

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("insert.branch.success"));

		return map;
	}

	/**
	 * 拠点情報更新処理
	 *
	 * @param params 登録パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> updateBranch(MaintenanceUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* ロック処理 */
		lockedBranch(params);

		/* 存在チェック */
		if(!appCommonService.branchExistCheck(params.getBranchId())){
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exist.error.entry"), "拠点コード"));
			return map;
		}

		/* 拠点情報の更新を行う */
		branchMapper.updateBranch(setBranchEntity(params,timestamp));

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("update.branch.success"));

		return map;
	}

	/**
	 * 拠点削除処理
	 *
	 * @param params 削除パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> deleteBranch(MaintenanceUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		boolean errFlg = false;

		/* ロック処理 */
		lockedBranch(params);

		/* 拠点存在チェック */
		if(!appCommonService.branchExistCheck(params.getBranchId())){
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exist.error"), "選択した拠点"));
			errFlg = true;
		}

		/* 拠点-店部課紐付き存在チェック */
		if(appCommonService.branchSectionExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			map.put("errorMessage", properties.getProperty("branch.linking.section.error"));
			errFlg = true;
	   	}

		/* エラーの場合 */
		if(errFlg){
			return map;
		}

		/* 拠点の削除を行う */
		branchMapper.deleteBranch(params.getBranchId());

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("delete.branch.success"));

		return map;
	}

	/**
	 * 拠点_店部課紐付き作成処理
	 *
	 * @param params 登録パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> addAssociate(MaintenanceUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		boolean errFlg = false;

		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		/* ロック処理 */
		lockedBranch(params);

		/* 拠点存在チェック */
		if(!appCommonService.branchExistCheck(params.getBranchId())){
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exist.error"), "選択した拠点"));
			errFlg = true;
		}

		/* 店部課存在チェック */
		if(!appCommonService.sectionExistCheck(params.getSectionId(), params.getCompanyId())){
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exist.error"), "入力した店部課"));
			errFlg = true;
		}

		/* 拠点-店部課紐付き存在チェック(V_ORGANIZATION_LEVEL) */
		if(organizationLevelExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exist.error.entry.existing"), "拠点-店部課紐付き"));
			errFlg = true;
	   	}

		/* エラーの場合 */
		if(errFlg){
			return map;
		}

		/* 拠点_店部課紐付きの作成を行う */
		sectionBranchMapper.insertSectionBranch(setSectionBranchEntity(params,timestamp));

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("insert.associate.success"));

		return map;
	}

	/**
	 * 拠点_店部課紐付き削除処理
	 *
	 * @param params 削除パラメータ
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> deleteAssociate(MaintenanceUpdateModel params) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();

		/* ロック処理 */
		lockedBranch(params);

		/* 拠点-店部課紐付き存在チェック */
		if(!appCommonService.branchSectionExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			map.put("errorMessage", MessageFormat.format(properties.getProperty("exist.error"), "削除対象"));
			return map;
	   	}

		/* 電話機紐付きチェック */
		if(associatePhoneExistCheck(params.getBranchId(),params.getSectionId(),params.getCompanyId())){
			map.put("errorMessage", properties.getProperty("branch.section.linking.phone.error"));
			return map;
	   	}

		/* 拠点_店部課紐付きの削除を行う */
		sectionBranchMapper.deleteSectionBranch(setSectionBranchEntity(params,null));

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("delete.associate.success"));

		return map;
	}

	/**
	 * CSVデータ取込処理
	 *
	 * @param filePath CSVファイルパス
	 * @param tableName テーブル名
	 * @return メッセージMAP
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> copyCsvData(String filePath, String tableName) throws Exception{

		/* メッセージセット用 */
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", tableName);
		params.put("filePath", "'" + filePath + "'");

		/* 既存データの削除 */
		maintenanceMapper.deleteTableData(params);
		/* CSVデータの取込 */
		maintenanceMapper.copyCsvData(params);

		/* 更新メッセージをセット */
		map.put("sucsessMessage", properties.getProperty("fetch.success"));

		return map;

	}

	/**
	 * 拠点ロック処理
	 * @param params ロックパラメータ
	 * @throws Exception 例外処理
	 */
	private void lockedBranch(MaintenanceUpdateModel params) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();

		try{
			/* ユーザー（新規） */
			map.put("BRANCH_ID", params.getBranchId());
			locked("M_BRANCH", null , map);

		} catch (ExcludeException e) {}
	}

	/**
	 * 拠点マスタテーブルモデルに値をセットします。
	 *
	 * @param params 登録・削除・更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private BranchEntity setBranchEntity(MaintenanceUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		BranchEntity entity = new BranchEntity();

		entity.setBranchId(params.getBranchId());					// 拠点コード
		entity.setBranchName(params.getBranchName());				// 拠点名
		entity.setClusterId(params.getClusterId());					// クラスタID
		entity.setCrtTmstmp(timestamp);								// 作成日時
		entity.setLstupdtTmstmp(timestamp);							// 更新日時

		return entity;
	}

	/**
	 * 拠点_店部課テーブルモデルに値をセットします。
	 *
	 * @param params 登録・削除・更新パラメータ
	 * @param timestamp タイムスタンプ
	 * @return エンティティパラメータ
	 */
	private SectionBranchEntity setSectionBranchEntity(MaintenanceUpdateModel params, Timestamp timestamp){
		/* インスタンス化 */
		SectionBranchEntity entity = new SectionBranchEntity();

		entity.setBranchId(params.getBranchId());					// 拠点コード
		entity.setCompanyId(params.getCompanyId());					// 会社コード
		entity.setSectionId(params.getSectionId());					// 拠点コード
		entity.setCrtTmstmp(timestamp);								// 作成日時
		entity.setLstupdtTmstmp(timestamp);							// 更新日時

		return entity;
	}

	/**
	 * 拠点と店部課の紐付き存在チェック(V_ORGANIZATION_LEVEL)
	 *
	 * @param branchId 拠点ID
	 * @param sectionId 店部課ID
	 * @param companyId 会社ID
	 * @return 存在チェック結果
	 */
	public boolean organizationLevelExistCheck(String branchId, String sectionId, String companyId) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("branchId", branchId);
		map.put("sectionId", sectionId);
		map.put("companyId", companyId);
		return (maintenanceMapper.organizationLevelExistCheck(map) >  0) ? true : false;
	}

	/**
	 * 電話機紐付きチェック
	 *
	 * @param branchId 拠点ID
	 * @param sectionId 店部課ID
	 * @param companyId 会社ID
	 * @return 紐付きチェック結果
	 */
	public boolean associatePhoneExistCheck(String branchId, String sectionId, String companyId) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("branchId", branchId);
		map.put("sectionId", sectionId);
		map.put("companyId", companyId);
		return (maintenanceMapper.associatePhoneExistCheck(map) >  0) ? true : false;
	}
}
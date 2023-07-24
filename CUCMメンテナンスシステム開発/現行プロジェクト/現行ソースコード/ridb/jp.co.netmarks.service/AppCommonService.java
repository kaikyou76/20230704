/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * AppCommonService.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.ksc.model.LabelValueModel;
import jp.co.ksc.service.BaseService;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.persistence.AppCommonMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 共通ロジック用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/07 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/07
 */
@Service
public class AppCommonService  extends BaseService{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AppCommonService.class);

	@Autowired
	private AppCommonMapper appCommonMapper;

/*********************************************************************
 * リスト取得
**********************************************************************/

	/**
	 * 拠点リスト取得
	 *
	 * @return List 店部課リスト
	 */
	public List<LabelValueModel> getBranchList() {
		return getBranchList(null);
	}

	/**
	 * 拠点リスト取得
	 *
	 * @param clusterFlg クラスターフラグ
	 * @return List 店部課リスト
	 */
	public List<LabelValueModel> getBranchList(String clusterFlg) {
		return appCommonMapper.getBranchList(clusterFlg);
	}

	/**
	 * 店部課リスト取得
	 *
	 * @return List 店部課リスト
	 */
	public List<LabelValueModel> getSectionList() {
		return getSectionList(null);
	}

	/**
	 * 店部課リスト取得
	 *
	 * @param branchId 拠点ID
	 * @return List 店部課リスト
	 */
	public List<LabelValueModel> getSectionList(String branchId) {
		return appCommonMapper.getSectionList(branchId);
	}

	/**
	 * 拠点＿店部課リスト取得
	 *
	 * @return List 店部課リスト
	 */
	public List<Map<String, String>> getBranchSectionList() {
		return appCommonMapper.getBranchSectionList();
	}

	/**
	 * PickupGroupリスト取得
	 *
	 * @param branchId  拠点Id
	 * @param sectionId 店部課Id
	 * @return PickupGroupピックアップリスト
	 */
	public List<LabelValueModel> getPickupGroupList(String branchId, String sectionId){

		/* 条件をセット */
		Map<String, String > map = new HashMap<String, String>();
		map.put("branchId", branchId);
		map.put("sectionId", sectionId);

		return appCommonMapper.getPickupGroupList(map);
	}

	/**
	 * ピックアップリストマスタを全件取得
	 *
	 * @return List ピックアップリスト
	 */
	public List<Map<String, String>> getPickupGroupMstarList() {
		return appCommonMapper.getPickupGroupMstarList();
	}

	/**
	 * 電話機リスト取得
	 *
	 * @return List 電話機リスト
	 */
	public List<LabelValueModel> getTypeModelList() {
		return appCommonMapper.getTypeModelList();
	}

	/**
	 * 電話機Templateリスト取得
	 *
	 * @param typeModelName 電話機ID
	 * @return List 店部課リスト
	 */
	public List<LabelValueModel> getPhoneTempleteList(String typeModelName) {
		return appCommonMapper.getPhoneTempleteList(typeModelName);
	}

	/**
	 * 電話機Templateリスト取得
	 *
	 * @return List 店部課リスト
	 */
	public List<Map<String, String>> getPhoneTempleteMstarList() {
		return appCommonMapper.getPhoneTempleteMstarList();
	}

	/**
	 * コーリングサーチスペースをパターン分全て取得します。
	 *
	 * @return コーリングサーチスペースリスト
	 */
	public List<Map<String, String>> getCallingSearchSpaceDynamicList() {
		return appCommonMapper.getCallingSearchSpaceDynamicList();
	}

	/**
	 * 店部課動的用セレクトMAP取得
	 *
	 * @return 動的コンボ用－店部課MAP
	 */
	public Map<String, List<LabelValueModel>> getDynamicSectionMap() {
		Map<String, List<LabelValueModel>> map = new LinkedHashMap<String, List<LabelValueModel>>();

		/* リストの取得 */
		List<Map<String, String>> list = appCommonMapper.getBranchSectionList();

		for(Map<String, String> selectMap : list){
			/* 拠点Id */
			String branchId = selectMap.get("branch_id");
			/* 作成したMAPのKEYと */
			if(!map.keySet().contains(branchId)){
				/* 新たにKeyを定義する */
				map.put(branchId, new ArrayList<LabelValueModel>());
				/* 先頭行をセット */
				map.get(branchId).add(new LabelValueModel(Constants.ALL_LABEL, ""));
			}
			/* Listにモデルをセット */
			map.get(branchId).add(new LabelValueModel(selectMap.get("section_name"), selectMap.get("section_id")));
		}
		return map;
	}

	/**
	 * ピックアップリスト用セレクトMAP取得
	 *
	 * @return 動的コンボ用－ピックアップグループMAP
	 */
	public Map<String, List<LabelValueModel>> getDynamicPickupGroupMap() {
		Map<String, List<LabelValueModel>> map = new HashMap<String, List<LabelValueModel>>();

		/* リストの取得 */
		List<Map<String, String>> list = appCommonMapper.getPickupGroupMstarList();

		for(Map<String, String> selectMap : list){
			String branchId = selectMap.get("branch_id");		/* 拠点Id */
			String sectionId = selectMap.get("section_id");		/* 店部課Id */
			String branchSectionId = branchId + "_" + sectionId;

			/* 作成したMAPのKEYと */
			if(!map.keySet().contains(branchSectionId)){
				/* 新たにKeyを定義する */
				map.put(branchSectionId, new ArrayList<LabelValueModel>());
				/* 先頭行をセット */
				map.get(branchSectionId).add(new LabelValueModel(Constants.ALL_LABEL, ""));
			}
			/* Listにモデルをセット */
			map.get(branchSectionId).add(new LabelValueModel(selectMap.get("no"), selectMap.get("name")));
		}
		return map;
	}

	/**
	 * 電話機テンプレート用セレクトMAP取得
	 *
	 * @return 動的コンボ用－電話機MAP
	 */
	public Map<String, List<LabelValueModel>> getDynamicPhoneTempleteMap() {
		Map<String, List<LabelValueModel>> map = new HashMap<String, List<LabelValueModel>>();

		/* リストの取得 */
		List<Map<String, String>> list = appCommonMapper.getPhoneTempleteMstarList();

		for(Map<String, String> selectMap : list){
			String typeName = selectMap.get("type_name");		/* template_name */
			/* 作成したMAPのKEYと */
			if(!map.keySet().contains(typeName)){
				/* 新たにKeyを定義する */
				map.put(typeName, new ArrayList<LabelValueModel>());
				/* 先頭行をセット */
				map.get(typeName).add(new LabelValueModel(Constants.ALL_LABEL, ""));
			}

			/* Listにモデルをセット */
			map.get(typeName).add(new LabelValueModel(selectMap.get("template_name"), selectMap.get("template_name")));
		}

		return map;
	}
/**
	 * コーリングサーチスペース用MAP取得
	 *
	 * @return 動的コンボ用－コーリングサーチスペースMAP
	 */
	public Map<String, List<LabelValueModel>> getDynamicCssMap() {
		Map<String, List<LabelValueModel>> map = new HashMap<String, List<LabelValueModel>>();

		List<Map<String, String>> list = appCommonMapper.getCallingSearchSpaceDynamicList();

		for(Map<String, String> selectMap : list){
			String id_1 = selectMap.get("id_1");				/* 1番目のコード */
			String id_2 = selectMap.get("id_2");				/* 2番目のコード */
			String id_3 = selectMap.get("id_3");				/* 3番目のコード */
			String section_id = selectMap.get("section_id");	/* 子店部課ID（会社ID,店部課） */
			String name = selectMap.get("name");				/* CSS名 */

			String key = "";
			if(StringUtils.isNotEmpty(section_id) && NumberUtils.isNumber(id_3)){
				/* 拠点 + 店部課 */
				key = id_1 + "_" + id_2 + "_" + section_id;
			} else if(StringUtils.isNotEmpty(section_id) && NumberUtils.isNumber(id_2)) {
				/* 拠点 + 店部課 */
				key = id_1 + "_" + section_id;
			} else if(id_1.equals(id_2)){
				/* 拠点 + 拠点 */
				key = id_1 + "_" + id_2;
			} else {
				continue;
			}

			/* 作成したMAPのKEYと */
			if(!map.keySet().contains(key)){
				/* 新たにKeyを定義する */
				map.put(key, new ArrayList<LabelValueModel>());
				/* 先頭行をセット */
				map.get(key).add(new LabelValueModel(Constants.ALL_LABEL, ""));
			}
			/* Listにモデルをセット */
			map.get(key).add(new LabelValueModel(name, name));
		}
		return map;
	}

	/**
	 * 親店部課情報を取得
	 *
	 * @return 親店部課情報MAP
	 */
	public Map<String, String> getParentSectionMap(){
		Map<String, String> map = new HashMap<String,String>();

		List<Map<String, String>> list = appCommonMapper.getParentSectionList();
		/* MAPにセット */
		for(Map<String, String> selectMap : list){
			map.put(selectMap.get("child_id"),selectMap.get("parent_id"));
		}

		return map;
	}


	/**
	 * 拡張モジュール設定
	 *
	 * @return 拡張モジュールMAP
	 */
	public Map<String, List<LabelValueModel>> getAddonModuleMap(){
		Map<String, List<LabelValueModel>> map = new HashMap<String, List<LabelValueModel>>();

		int i=0;
		for(String telType : Constants.MODULE_TEL_TYPE_MODEL.split(",")){
			/* 新たにKeyを定義する */
			map.put(telType, new ArrayList<LabelValueModel>());
			/* 先頭行をセット */
			map.get(telType).add(new LabelValueModel(Constants.ALL_LABEL, ""));

			for(String adnMod : Constants.ADDON_MODULE_ARRAY[i].split(",")){
				map.get(telType).add(new LabelValueModel(adnMod, adnMod));
			}
			i++;
		}
		return map;
	}

	/**
	 * クラスタリスト取得
	 *
	 * @return List クラスタリスト
	 */
	public List<LabelValueModel> getCluster() {
		return appCommonMapper.getClusterList(Constants.CLUSTER_LABEL);
	}

/*********************************************************************
 * 存在チェック
**********************************************************************/

	/**
	 * 拠点存在チェック
	 *
	 * @param branchId 拠点ID
	 * @return 存在チェック結果
	 */
	public boolean branchExistCheck(String branchId) {
		return (appCommonMapper.branchExistCheck(branchId) > 0) ? true : false;
	}

	/**
	 * 店部課存在チェック
	 *
	 * @param sectionId 店部課ID
	 * @return 存在チェック結果
	 */
	public boolean sectionExistCheck(String sectionId) {
		return sectionExistCheck(sectionId, null);
	}

	/**
	 * 店部課存在チェック
	 *
	 * @param sectionId 店部課ID
	 * @param companyId 会社ID
	 * @return 存在チェック結果
	 */
	public boolean sectionExistCheck(String sectionId, String companyId) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sectionId", sectionId);
		map.put("companyId", companyId);
		return (appCommonMapper.sectionExistCheck(map) >  0) ? true : false;
	}

	/**
	 * 拠点と店部課の紐付き存在チェック
	 *
	 * @param branchId 拠点ID
	 * @return 存在チェック結果
	 */
	public boolean branchSectionExistCheck(String branchId) {
		return branchSectionExistCheck(branchId, null,null);
	}

	/**
	 * 拠点と店部課の紐付き存在チェック
	 *
	 * @param branchId 拠点ID
	 * @param sectionId 店部課ID
	 * @param companyId 会社ID
	 * @return 存在チェック結果
	 */
	public boolean branchSectionExistCheck(String branchId, String sectionId, String companyId) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("branchId", branchId);
		map.put("sectionId", sectionId);
		map.put("companyId", companyId);
		return (appCommonMapper.branchSectionExistCheck(map) >  0) ? true : false;
	}

	/**
	 * マックアドレス存在チェック
	 *
	 * @param macAddress 内線番号
	 * @return 存在チェック結果
	 */
	public boolean macAddressExistCheck(String macAddress) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("macAddress", macAddress);
		return (appCommonMapper.macAddressExistCheck(map) >  0) ? true : false;
	}

	/**
	 * PhoneButtonTemplate存在チェック
	 *
	 * @param typeModelName     電話機種名
	 * @param phoneTemplateName PhoneButtonTemplate名
	 * @param branchId          拠点ID
	 * @return 存在チェック結果
	 */
	public boolean phoneTemplateExistCheck(String  typeModelName, String phoneTemplateName, String branchId) {

		Map<String, Object> map = new HashMap<String, Object>();

		/* PhoneButtonTemplate存在チェック */
		map = new HashMap<String, Object>();
		map.put("typeModelName", typeModelName);
		map.put("phoneTemplateName", phoneTemplateName);
		map.put("branchId", branchId);

		return (appCommonMapper.phoneTemplateExistCheck(map) > 0)? true:false;

	}

	/**
	 * 内線番号存在チェック
	 *
	 * @param directoryNumber 内線番号
	 * @return 存在チェック結果
	 */
	public boolean directoryNumberExistCheck(String directoryNumber) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("directoryNumber", directoryNumber);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.directoryNumberExistCheck(map) >  0) ? true : false;
	}

	/**
	 * ユーザー存在チェック
	 *
	 * @param userId ユーザーID
	 * @return 存在チェック結果
	 */
	public boolean userExistCheck(String userId) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.userExistCheck(map) >  0) ? true : false;
	}
/**
	 * ユーザーと電話機の紐付き存在チェック
	 *
	 * @param userId ユーザーID
	 * @param telId 電話ID
	 * @return 存在チェック結果
	 */
	public boolean userTelExistCheck(BigDecimal userId,BigDecimal telId) {
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("telId", telId);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.userTelExistCheck(map) >  0) ? true : false;
	}

	/**
	 * ラインIndex存在チェック
	 *
	 * @param telId 電話機ID
	 * @param lineIndex ラインIndex
	 * @return 存在チェック結果
	 */
	public boolean lineIndexExistCheck(BigDecimal telId, String lineIndex) {
		return lineIndexExistCheck(telId, lineIndex, null);
	}

	/**
	 * ラインIndex存在チェック
	 *
	 * @param telId 電話機ID
	 * @param lineIndex ラインIndex
	 * @param orgLineIndex 変更前のラインIndex
	 * @return 存在チェック結果
	 */
	public boolean lineIndexExistCheck(BigDecimal telId, String lineIndex, String orgLineIndex) {

		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", telId);
		map.put("lineIndex", lineIndex);
		map.put("orgLineIndex", orgLineIndex);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.lineIndexExistCheck(map) >  0) ? true : false;
	}

	/**
	 * 電話機とラインの紐付き存在チェック
	 *
	 * @param telId 電話機ID
	 * @return 存在チェック結果
	 */
	public boolean telLineExistCheckTel(BigDecimal telId){
		return telLineExistCheck(telId, null, null);
	}

	/**
	 * 電話機とラインの紐付き存在チェック
	 *
	 * @param telId 電話機ID
	 * @param lineId ラインID
	 * @return 存在チェック結果
	 */
	public boolean telLineExistCheck(BigDecimal telId, BigDecimal lineId){
		return telLineExistCheck(telId, lineId, null);
	}

	/**
	 * 電話機とラインの紐付き存在チェック
	 *
	 * @param telId 電話機ID
	 * @param lineId ラインID
	 * @param lineIndex ライン連番
	 * @return 存在チェック結果
	 */
	public boolean telLineExistCheck(BigDecimal telId, BigDecimal lineId, String lineIndex){

		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", telId);
		map.put("lineId", lineId);
		map.put("lineIndex", lineIndex);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.telLineExistCheck(map) >  0) ? true : false;
	}

	/**
	 * 電話機とラインの紐付き存在チェック
	 *
	 * @param lineId ラインID
	 * @return 存在チェック結果
	 */
	public int telLineNum(BigDecimal lineId){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lineId", lineId);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return appCommonMapper.telLineExistCheck(map);
	}


	/**
	 * 電話機とラインの紐付き存在チェック
	 * ※条件は電話IDと内線番号
	 *
	 * @param telId 電話機ID
	 * @param directoryNumber 内線番号
	 * @return 存在チェック結果
	 */
	public boolean telDirectoryNumberExistCheck(BigDecimal telId, String directoryNumber){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", telId);
		map.put("directoryNumber", directoryNumber);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.telLineExistCheck(map) >  0) ? true : false;
	}

	/**
	 * 電話機に該当するラインのシェアードチェック
	 * ※電話機に該当する全てのラインの確認をします。
	 *
	 * @param telId 電話機ID
	 * @return true:シェアードライン false:通常ライン
	 */
	public boolean telSharedLineCheck(BigDecimal telId){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", telId);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return (appCommonMapper.telSharedLineCheck(map) >  1) ? true : false;
	}

/*********************************************************************
 * その他
**********************************************************************/

	/**
	 * 話中転送先の階層を取得（下四桁）
	 *
	 * @param directoryNumber 内線番号
	 * @return 話中転送先階層
	 */
	public String[] getBusyDestinationClass(String directoryNumber){
		return getBusyDestinationClass(directoryNumber,0);
	}

	/**
	 * 話中転送先の階層を取得（下四桁）
	 *
	 * @param directoryNumber 内線番号
	 * @param displayNumber 取得件数
	 * @return 話中転送先階層
	 */
	public String[] getBusyDestinationClass(String directoryNumber, int displayNumber){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("directoryNumber", directoryNumber);
		map.put("displayNumber", displayNumber);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return appCommonMapper.getBusyDestinationClass(map);
	}

	/**
	 * 話中転送先の逆階層を取得（下四桁）
	 *
	 * @param directoryNumber 内線番号
	 * @return 話中転送先階層
	 */
	public String[] getBusyDestinationReverseClass(String directoryNumber){
		return getBusyDestinationReverseClass(directoryNumber, 0);
	}

	/**
	 * 話中転送先の逆階層を取得（下四桁）
	 *
	 * @param directoryNumber 内線番号
	 * @param displayNumber 取得件数
	 * @return 話中転送先階層
	 */
	public String[] getBusyDestinationReverseClass(String directoryNumber, int displayNumber){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("directoryNumber", directoryNumber);
		map.put("displayNumber", displayNumber);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);
		return appCommonMapper.getBusyDestinationReverseClass(map);
	}

	/**
	 * ラインに紐づく電話機の拠点を取得
	 *
	 * @param lineId ラインID
	 * @return 拠点ID
	 */
	public String[] getSharedLineBranch(BigDecimal lineId){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lineId", lineId);
		map.put("deleted", Constants.COM_FLG_OFF);
		return appCommonMapper.getSharedLineBranch(map);
	}

	/**
	 * 内線番号に紐づく電話機の拠点を取得
	 *
	 * @param directoryNumber ラインID
	 * @return 拠点ID
	 */
	public String[] getSharedLineBranch(String directoryNumber){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("directoryNumber", directoryNumber);
		map.put("deleted", Constants.COM_FLG_OFF);
		return appCommonMapper.getSharedLineBranch(map);
	}

	/**
	 * 転送先のライン情報を取得
	 * @param directoryNumber 内線番号
	 * @return ライン情報
	 */
	public Map<String, Object> getBusyDestinationInfo(String directoryNumber){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("directoryNumber", directoryNumber);
		map.put("COM_FLG_OFF", Constants.COM_FLG_OFF);

		return appCommonMapper.getBusyDestinationInfo(map);
	}

	/**
	 * ラインIDを取得
	 * @param directoryNumber 内線番号
	 * @return ラインID
	 */
	public BigDecimal getLineId(String directoryNumber){
		return appCommonMapper.getLineId(directoryNumber);
	}

	/**
	 * パスワードの有効期限チェック
	 *
	 * @param userId ユーザID
	 * @param expirationDate パスワード変更日数
	 * @return 有効期限チェック結果
	 */
	public boolean checkPassword(BigDecimal userId, String expirationDate){
		/* 条件をセット */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("expirationDate", expirationDate);

		return appCommonMapper.checkPasswordLifetime(map);
	}
}	
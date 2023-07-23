/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CommonMapper.java
 *
 * @date 2013/08/08
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jp.co.ksc.model.LabelValueModel;

/**
 * <pre>
 * 共通データマッパーインタフェース
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/08 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/08/08
 */
public interface AppCommonMapper {

	/**
	 * ユーザー情報の取得
	 * 
	 * @param params ログインID,パスワード
	 * @return ユーザー&パスワード
	 */
	Map<String, Object> getUserInfo(Map<String, String> params);

	/**
	 * 拠点リスト
	 * 
	 * @param param クラスタ
	 * @return リスト
	 */
	List<LabelValueModel> getBranchList(String param);

	/**
	 * 店部課リスト
	 *
	 * @param id 拠点ID
	 * @return 店部課リスト
	 */
	List<LabelValueModel> getSectionList(String id);

	/**
	 * 店部課リストのマスタ情報リスト(動的用)
	 *
	 * @return 店部課動的リスト
	 */
	List<Map<String, String>> getBranchSectionList();
	
	/**
	 * 親店部課のマスタ情報リスト(
	 *
	 * @return 親店部課リスト
	 */
	List<Map<String, String>> getParentSectionList();

	/**
	 * ピックアップグループリスト
	 * 
	 * @param params 検索条件
	 * @return ピックアップグループリスト
	 */
	List<LabelValueModel> getPickupGroupList(Map<String, String> params);

	/**
	 * ピックアップグループのマスタ情報リスト(動的用)
	 *
	 * @return ピックアップグループ動的リスト
	 */
	List<Map<String, String>> getPickupGroupMstarList();

	/**
	 * 電話機リスト
	 *
	 * @return 電話機リスト
	 */
	List<LabelValueModel> getTypeModelList();

	/**
	 * 電話機Templateリスト
	 *
	 * @param typeModelName 電話機タイプ
	 * @return 電話機Templateリスト
	 */
	List<LabelValueModel> getPhoneTempleteList(String typeModelName);

	/**
	 * 電話機Templateのマスタ情報リスト(動的用)
	 *
	 * @return 電話機Template動的リスト
	 */
	List<Map<String, String>> getPhoneTempleteMstarList();

	/**
	 * コーリングサーチスペースリスト取得
	 *
	 * @return コーリングサーチスペースリスト
	 */
	List<Map<String, String>> getCallingSearchSpaceDynamicList();

	/**
	 * クラスタリストを取得
	 *
	 * @param clusterLabel クラスタラベル
	 * @return クラスタリスト
	 */
	List<LabelValueModel> getClusterList(String clusterLabel);


	/* #### 存在チェック #### */
	/**
	 * 拠点存在チェック
	 *
	 * @param branchId 拠点Id
	 * @return 件数
	 */
	int branchExistCheck(String branchId);

	/**
	 * 店部課存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int sectionExistCheck(Map<String, Object> params);

	/**
	 * 拠点と店部課の紐付き存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int branchSectionExistCheck(Map<String, Object> params);

	/**
	 * 電話機存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int telExistCheck(Map<String, Object> params);

	/**
	 * ユーザー存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int userExistCheck(Map<String, Object> params);

	/**
	 * Line存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int lineExistCheck(Map<String, Object> params);

	/**
	 * 内線番号存在チェック(LINE存在チェック)
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int directoryNumberExistCheck(Map<String, Object> params);

	/**
	 * Unity関連存在チェック
	 *
	 * @param telId 電話ID
	 * @return 件数
	 */
	int unityAssociationExistCheck(BigDecimal telId);

	/**
	 * ユーザーと店部課の紐付き存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int userSectionExistCheck(Map<String, Object> params);

	/**
	 * ユーザーと電話機の紐付き存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int userTelExistCheck(Map<String, Object> params);
	
	/**
	 * ユーザーと電話機とユーザーの店部課の紐付き存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int userTelSectionExistCheck(Map<String, Object> params);

	/**
	 * 削除予約フラグ存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int userSectionDeleteReserveExistCheck(Map<String, Object> params);

	/**
	 * DevicePool存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int devicePoolExistCheck(Map<String,Object> params);
	
	/**
	 * CallingSearchSpace存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int callingSearchSpaceExistCheck(Map<String,Object> params);
	
	/**
	 * 電話機種存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int typeModelExistCheck(Map<String,Object> params);
	
	/**
	 * マックアドレス存在チェック
	 * 
	 * @param params 条件
	 * @return 件数
	 */
	int macAddressExistCheck(Map<String,Object> params);

	/**
	 * ボタンテンプレート存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int phoneTemplateExistCheck(Map<String,Object> params);
	
	/**
	 * ロケーション存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int locationExistCheck(Map<String,Object> params);
	
	/**
	 * ピックアップグループ存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int pickupGroupExistCheck(Map<String,Object> params);
	
	/**
	 * TelDirData存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int telDirAssociationExistCheck(Map<String, Object> params);

	/**
	 * TelDirData存在チェック
	 * ※電話とユーザーに紐づくラインデータに該当する登録されていないデータ件を取得
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int telDirAssociationExistEntryTelUserCheck(Map<String, Object> params);

	/**
	 * TelDirData存在チェック
	 * ※電話とラインに紐づくユーザーデータに該当する登録されていないデータ件を取得
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int telDirAssociationExistEntryTelLineCheck(Map<String, Object> params);

	/**
	 * ラインIndex存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int lineIndexExistCheck(Map<String, Object> params);
	
	/**
	 * 共用電話チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int telSharedUseCheck(Map<String, Object> params);

	/**
	 * 電話ライン紐付き存在チェック
	 *
	 * @param params 条件
	 * @return 件数
	 */
	int telLineExistCheck(Map<String, Object> params);
	
	/**
	 * 電話機に該当するラインがシェアードラインか確認します。
	 * 2以上の値が帰ってきた場合は、シェアードラインとなります。
	 * 
	 * @param params 条件
	 * @return 件数
	 */
	int telSharedLineCheck(Map<String, Object> params);

	/* #### その他 #### */
	
	/**
	 * 電話番号を取得します。
	 * ※ユーザーに該当するラインの内線番号を取得。
	 * 　複数存在する場合はIndexをASCでソートした１行目の値を取得します。
	 *
	 * @param params 条件
	 * @return 内線番号
	 */
	String getTelephoneNumber(Map<String, Object> params);

	/**
	 * ラインIDを取得します
	 *
	 * @param directoryNumber 内線番号
	 * @return ラインID
	 */
	BigDecimal getLineId(String directoryNumber);
	
	/**
	 * ラインの削除フラグを取得します
	 *
	 * @param directoryNumber 内線番号
	 * @return ラインの削除フラグ
	 */
	Map<String, Object> getLineDeleteFlg(String directoryNumber);

	/**
	 * CSSの取得
	 * ※条件はラインID
	 *
	 * @param params 条件
	 * @return CSS
	 */
	String getCssConditionsLineString(Map<String, Object> params);

	/**
	 * 電話機に紐づくラインを取得
	 *
	 * @param params 条件
	 * @return ライン情報
	 */
	List<Map<String, Object>> getLineLinkedTel(Map<String, Object> params);

	/**
	 * 電話機に紐づくユーザーを取得
	 *
	 * @param params 条件
	 * @return ライン情報
	 */
	List<Map<String, Object>> getUserLinkedTel(Map<String, Object> params);

	/**
	 * 話中転送先階層の取得
	 *
	 * @param params 話中転送先
	 * @return 話中転送先階層
	 */
	String[] getBusyDestinationClass(Map<String, Object> params);

	/**
	 * 話中転送先階層の取得
	 * ※逆階層
	 *
	 * @param params 話中転送先
	 * @return 話中転送先階層
	 */
	String[] getBusyDestinationReverseClass(Map<String, Object> params);

	/**
	 * ラインに紐づく拠点の取得
	 *
	 * @param params ラインId
	 * @return 拠点Id
	 */
	String[] getSharedLineBranch(Map<String, Object> params);
	
	/**
	 * ラインに紐づく拠点の取得(条件内線番号)
	 *
	 * @param params ラインId
	 * @return 拠点Id
	 */
	String[] getSharedLineBranchDirNum(Map<String, Object> params);

	/**
	 * 転送先のライン情報を取得
	 *
	 * @param params 内線番号
	 * @return ライン情報
	 */
	Map<String, Object> getBusyDestinationInfo(Map<String, Object> params);

	/**
	 * パスワードの有効チェック
	 *
	 * @param params 条件
	 * @return チェック結果
	 */
	Boolean checkPasswordLifetime(Map<String, Object> params);
	
	/**
	 * クラスターIDの取得
	 * 
	 * @param branchId 拠点ID
	 * @return クラスターID
	 */
	BigDecimal getClusterId(String branchId);
	
	/**
	 * ユーザの最大値店部課値の取得
	 * 
	 * @param params 条件
	 * @return 店部課情報
	 */
	Map<String, Object> getMaxUserSection(Map<String, Object> params);
}


/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * CheckConsistencyUpdateForm.java
 *
 * @date 2013/09/13
 * @version 1.0
 * @author KSC Hiroki Fukazawa
 */
package jp.co.netmarks.model.form.list;

import java.io.Serializable;
import jp.co.ksc.model.form.list.BaseGridRowForm;

/**
 * <pre>
 * CUCM整合性チェック画面リスト更新用フォームクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/13 KSC Hiroki Fukazawa 新規作成
 * </pre>
 *
 * @author KSC Hiroki Fukazawa
 * @version 1.0 2013/09/13
 */
public class CheckConsistencyUpdateForm extends BaseGridRowForm implements Serializable{

	private static final long serialVersionUID = 1L;

	/* 不整合区分 */
	private String inconsistencyDiv           = null;

	/* ### CUCM項目 ### */
	/* MACアドレス/内線番号 */
	private String cucmJoinKey                = null;
	/* MACアドレス*/
	private String cucmMacAddress             = null;
	/* 内線番号 */
	private String cucmDirectoryNumber        = null;
	/* Description */
	private String cucmDescription            = null;
	/* CSS名 */
	private String cucmCssName                = null;
	/* Location名 */
	private String cucmLocationName           = null;
	/* デバイスプール名 */
	private String cucmDevicePoolName         = null;
	/* PhonButton名 */
	private String cucmPhoneButtonName        = null;
	/* 拡張モジュール1 */
	private String cucmAddonModule1           = null;
	/* 拡張モジュール2 */
	private String cucmAddonModule2           = null;
	/* 全転送CSS */
	private String cucmFwdAllCss              = null;
	/* 話中転送CSS */
	private String cucmFwdBusyCss             = null;
	/* 話中転送先 */
	private String cucmFwdBusyDestination     = null;
	/* 不応答CSS */
	private String cucmFwdNoansCss            = null;
	/* 不応答転送先 */
	private String cucmFwdNoansDestination    = null;
	/* ピックアップグループ */
	private String cucmPickupGroup            = null;
	/* ボイスメール使用フラグ */
	private String cucmUseVmFlg               = null;
	/* ライン連番 */
	private String cucmIndex                  = null;
	/* ExternalPhoneNumber */
	private String cucmExternalPhoneNumber    = null;
	/* LineTextLabel */
	private String cucmLineTextLabel          = null;
	/* 鳴動設定 */
	private String cucmRingSetting            = null;


	/* ### 連携アプリ項目 ### */
	/* MACアドレス/内線番号 */
	private String appJoinKey                = null;
	/* 電話機ID */
	private String appPhoneId                = null;
	/* ラインID */
	private String appLineId                 = null;
	/* MACアドレス*/
	private String appMacAddress             = null;
	/* 内線番号 */
	private String appDirectoryNumber        = null;
	/* Description */
	private String appDescription            = null;
	/* CSS名 */
	private String appCssName                = null;
	/* Location名 */
	private String appLocationName           = null;
	/* デバイスプール名 */
	private String appDevicePoolName         = null;
	/* PhonButton名 */
	private String appPhoneButtonName        = null;
	/* 拡張モジュール1 */
	private String appAddonModule1           = null;
	/* 拡張モジュール2 */
	private String appAddonModule2           = null;
	/* 全転送CSS */
	private String appFwdAllCss              = null;
	/* 話中転送CSS */
	private String appFwdBusyCss             = null;
	/* 話中転送先 */
	private String appFwdBusyDestination     = null;
	/* 不応答CSS */
	private String appFwdNoansCss            = null;
	/* 不応答転送先 */
	private String appFwdNoansDestination    = null;
	/* ピックアップグループ */
	private String appPickupGroup            = null;
	/* ボイスメール使用フラグ */
	private String appUseVmFlg               = null;
	/* ライン連番 */
	private String appIndex                  = null;
	/* ExternalPhoneNumber */
	private String appExternalPhoneNumber    = null;
	/* LineTextLabel */
	private String appLineTextLabel          = null;
	/* 鳴動設定 */
	private String appRingSetting            = null;
	/**
	 * Validateを回避するダミー値をセットする
	 */
	@Override
	public void convetToNoValidateBean() {

	}


/*********************************************************************
 * generated getter/setter
**********************************************************************/



	/**
	 * @return cucmJoinKey
	 */
	public String getCucmJoinKey() {
		return cucmJoinKey;
	}
	/**
	 * @return inconsistencyDiv
	 */
	public String getInconsistencyDiv() {
		return inconsistencyDiv;
	}
	/**
	 * @param inconsistencyDiv セットする inconsistencyDiv
	 */
	public void setInconsistencyDiv(String inconsistencyDiv) {
		this.inconsistencyDiv = inconsistencyDiv;
	}



	/**
	 * @param cucmJoinKey セットする cucmJoinKey
	 */
	public void setCucmJoinKey(String cucmJoinKey) {
		this.cucmJoinKey = cucmJoinKey;
	}
	/**
	 * @return cucmMacAddress
	 */
	public String getCucmMacAddress() {
		return cucmMacAddress;
	}
	/**
	 * @param cucmMacAddress セットする cucmMacAddress
	 */
	public void setCucmMacAddress(String cucmMacAddress) {
		this.cucmMacAddress = cucmMacAddress;
	}
	/**
	 * @return cucmDirectoryNumber
	 */
	public String getCucmDirectoryNumber() {
		return cucmDirectoryNumber;
	}
	/**
	 * @param cucmDirectoryNumber セットする cucmDirectoryNumber
	 */
	public void setCucmDirectoryNumber(String cucmDirectoryNumber) {
		this.cucmDirectoryNumber = cucmDirectoryNumber;
	}
	/**
	 * @return cucmDescription
	 */
	public String getCucmDescription() {
		return cucmDescription;
	}
	/**
	 * @param cucmDescription セットする cucmDescription
	 */
	public void setCucmDescription(String cucmDescription) {
		this.cucmDescription = cucmDescription;
	}
	/**
	 * @return cucmCssName
	 */
	public String getCucmCssName() {
		return cucmCssName;
	}
	/**
	 * @param cucmCssName セットする cucmCssName
	 */
	public void setCucmCssName(String cucmCssName) {
		this.cucmCssName = cucmCssName;
	}
	/**
	 * @return cucmLocationName
	 */
	public String getCucmLocationName() {
		return cucmLocationName;
	}
	/**
	 * @param cucmLocationName セットする cucmLocationName
	 */
	public void setCucmLocationName(String cucmLocationName) {
		this.cucmLocationName = cucmLocationName;
	}
	/**
	 * @return cucmDevicePoolName
	 */
	public String getCucmDevicePoolName() {
		return cucmDevicePoolName;
	}
	/**
	 * @param cucmDevicePoolName セットする cucmDevicePoolName
	 */
	public void setCucmDevicePoolName(String cucmDevicePoolName) {
		this.cucmDevicePoolName = cucmDevicePoolName;
	}
	/**
	 * @return cucmPhoneButtonName
	 */
	public String getCucmPhoneButtonName() {
		return cucmPhoneButtonName;
	}
	/**
	 * @param cucmPhoneButtonName セットする cucmPhoneButtonName
	 */
	public void setCucmPhoneButtonName(String cucmPhoneButtonName) {
		this.cucmPhoneButtonName = cucmPhoneButtonName;
	}
	/**
	 * @return cucmAddonModule1
	 */
	public String getCucmAddonModule1() {
		return cucmAddonModule1;
	}
	/**
	 * @param cucmAddonModule1 セットする cucmAddonModule1
	 */
	public void setCucmAddonModule1(String cucmAddonModule1) {
		this.cucmAddonModule1 = cucmAddonModule1;
	}
/**
	 * @return cucmAddonModule2
	 */
	public String getCucmAddonModule2() {
		return cucmAddonModule2;
	}
	/**
	 * @param cucmAddonModule2 セットする cucmAddonModule2
	 */
	public void setCucmAddonModule2(String cucmAddonModule2) {
		this.cucmAddonModule2 = cucmAddonModule2;
	}
	/**
	 * @return cucmFwdAllCss
	 */
	public String getCucmFwdAllCss() {
		return cucmFwdAllCss;
	}
	/**
	 * @param cucmFwdAllCss セットする cucmFwdAllCss
	 */
	public void setCucmFwdAllCss(String cucmFwdAllCss) {
		this.cucmFwdAllCss = cucmFwdAllCss;
	}
	/**
	 * @return cucmFwdBusyCss
	 */
	public String getCucmFwdBusyCss() {
		return cucmFwdBusyCss;
	}
	/**
	 * @param cucmFwdBusyCss セットする cucmFwdBusyCss
	 */
	public void setCucmFwdBusyCss(String cucmFwdBusyCss) {
		this.cucmFwdBusyCss = cucmFwdBusyCss;
	}
	/**
	 * @return cucmFwdBusyDestination
	 */
	public String getCucmFwdBusyDestination() {
		return cucmFwdBusyDestination;
	}
	/**
	 * @param cucmFwdBusyDestination セットする cucmFwdBusyDestination
	 */
	public void setCucmFwdBusyDestination(String cucmFwdBusyDestination) {
		this.cucmFwdBusyDestination = cucmFwdBusyDestination;
	}
	/**
	 * @return cucmFwdNoansCss
	 */
	public String getCucmFwdNoansCss() {
		return cucmFwdNoansCss;
	}
	/**
	 * @param cucmFwdNoansCss セットする cucmFwdNoansCss
	 */
	public void setCucmFwdNoansCss(String cucmFwdNoansCss) {
		this.cucmFwdNoansCss = cucmFwdNoansCss;
	}
	/**
	 * @return cucmFwdNoansDestination
	 */
	public String getCucmFwdNoansDestination() {
		return cucmFwdNoansDestination;
	}
	/**
	 * @param cucmFwdNoansDestination セットする cucmFwdNoansDestination
	 */
	public void setCucmFwdNoansDestination(String cucmFwdNoansDestination) {
		this.cucmFwdNoansDestination = cucmFwdNoansDestination;
	}
	/**
	 * @return cucmPickupGroup
	 */
	public String getCucmPickupGroup() {
		return cucmPickupGroup;
	}
	/**
	 * @param cucmPickupGroup セットする cucmPickupGroup
	 */
	public void setCucmPickupGroup(String cucmPickupGroup) {
		this.cucmPickupGroup = cucmPickupGroup;
	}
	/**
	 * @return cucmUseVmFlg
	 */
	public String getCucmUseVmFlg() {
		return cucmUseVmFlg;
	}
	/**
	 * @param cucmUseVmFlg セットする cucmUseVmFlg
	 */
	public void setCucmUseVmFlg(String cucmUseVmFlg) {
		this.cucmUseVmFlg = cucmUseVmFlg;
	}
	/**
	 * @return cucmIndex
	 */
	public String getCucmIndex() {
		return cucmIndex;
	}
	/**
	 * @param cucmIndex セットする cucmIndex
	 */
	public void setCucmIndex(String cucmIndex) {
		this.cucmIndex = cucmIndex;
	}
	/**
	 * @return cucmExternalPhoneNumber
	 */
	public String getCucmExternalPhoneNumber() {
		return cucmExternalPhoneNumber;
	}
	/**
	 * @param cucmExternalPhoneNumber セットする cucmExternalPhoneNumber
	 */
	public void setCucmExternalPhoneNumber(String cucmExternalPhoneNumber) {
		this.cucmExternalPhoneNumber = cucmExternalPhoneNumber;
	}
	/**
	 * @return cucmLineTextLabel
	 */
	public String getCucmLineTextLabel() {
		return cucmLineTextLabel;
	}
	/**
	 * @param cucmLineTextLabel セットする cucmLineTextLabel
	 */
	public void setCucmLineTextLabel(String cucmLineTextLabel) {
		this.cucmLineTextLabel = cucmLineTextLabel;
	}
	/**
	 * @return cucmRingSetting
	 */
	public String getCucmRingSetting() {
		return cucmRingSetting;
	}
	/**
	 * @param cucmRingSetting セットする cucmRingSetting
	 */
	public void setCucmRingSetting(String cucmRingSetting) {
		this.cucmRingSetting = cucmRingSetting;
	}
	/**
	 * @return appJoinKey
	 */
	public String getAppJoinKey() {
		return appJoinKey;
	}
	/**
	 * @param appJoinKey セットする appJoinKey
	 */
	public void setAppJoinKey(String appJoinKey) {
		this.appJoinKey = appJoinKey;
	}
	/**
	 * @return appPhoneId
	 */
	public String getAppPhoneId() {
		return appPhoneId;
	}
	/**
	 * @param appPhoneId セットする appPhoneId
	 */
	public void setAppPhoneId(String appPhoneId) {
		this.appPhoneId = appPhoneId;
	}
	/**
	 * @return appLineId
	 */
	public String getAppLineId() {
		return appLineId;
	}
	/**
	 * @param appLineId セットする appLineId
	 */
	public void setAppLineId(String appLineId) {
		this.appLineId = appLineId;
	}
	/**
	 * @return appMacAddress
	 */
	public String getAppMacAddress() {
		return appMacAddress;
	}
	/**
	 * @param appMacAddress セットする appMacAddress
	 */
	public void setAppMacAddress(String appMacAddress) {
		this.appMacAddress = appMacAddress;
	}
	/**
	 * @return appDirectoryNumber
	 */
	public String getAppDirectoryNumber() {
		return appDirectoryNumber;
	}
/**
	 * @param appDirectoryNumber セットする appDirectoryNumber
	 */
	public void setAppDirectoryNumber(String appDirectoryNumber) {
		this.appDirectoryNumber = appDirectoryNumber;
	}
	/**
	 * @return appDescription
	 */
	public String getAppDescription() {
		return appDescription;
	}
	/**
	 * @param appDescription セットする appDescription
	 */
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}
	/**
	 * @return appCssName
	 */
	public String getAppCssName() {
		return appCssName;
	}
	/**
	 * @param appCssName セットする appCssName
	 */
	public void setAppCssName(String appCssName) {
		this.appCssName = appCssName;
	}
	/**
	 * @return appLocationName
	 */
	public String getAppLocationName() {
		return appLocationName;
	}
	/**
	 * @param appLocationName セットする appLocationName
	 */
	public void setAppLocationName(String appLocationName) {
		this.appLocationName = appLocationName;
	}
	/**
	 * @return appDevicePoolName
	 */
	public String getAppDevicePoolName() {
		return appDevicePoolName;
	}
	/**
	 * @param appDevicePoolName セットする appDevicePoolName
	 */
	public void setAppDevicePoolName(String appDevicePoolName) {
		this.appDevicePoolName = appDevicePoolName;
	}
	/**
	 * @return appPhoneButtonName
	 */
	public String getAppPhoneButtonName() {
		return appPhoneButtonName;
	}
	/**
	 * @param appPhoneButtonName セットする appPhoneButtonName
	 */
	public void setAppPhoneButtonName(String appPhoneButtonName) {
		this.appPhoneButtonName = appPhoneButtonName;
	}
	/**
	 * @return appAddonModule1
	 */
	public String getAppAddonModule1() {
		return appAddonModule1;
	}
	/**
	 * @param appAddonModule1 セットする appAddonModule1
	 */
	public void setAppAddonModule1(String appAddonModule1) {
		this.appAddonModule1 = appAddonModule1;
	}
	/**
	 * @return appAddonModule2
	 */
	public String getAppAddonModule2() {
		return appAddonModule2;
	}
	/**
	 * @param appAddonModule2 セットする appAddonModule2
	 */
	public void setAppAddonModule2(String appAddonModule2) {
		this.appAddonModule2 = appAddonModule2;
	}
	/**
	 * @return appFwdAllCss
	 */
	public String getAppFwdAllCss() {
		return appFwdAllCss;
	}
	/**
	 * @param appFwdAllCss セットする appFwdAllCss
	 */
	public void setAppFwdAllCss(String appFwdAllCss) {
		this.appFwdAllCss = appFwdAllCss;
	}
	/**
	 * @return appFwdBusyCss
	 */
	public String getAppFwdBusyCss() {
		return appFwdBusyCss;
	}
	/**
	 * @param appFwdBusyCss セットする appFwdBusyCss
	 */
	public void setAppFwdBusyCss(String appFwdBusyCss) {
		this.appFwdBusyCss = appFwdBusyCss;
	}
	/**
	 * @return appFwdBusyDestination
	 */
	public String getAppFwdBusyDestination() {
		return appFwdBusyDestination;
	}
	/**
	 * @param appFwdBusyDestination セットする appFwdBusyDestination
	 */
	public void setAppFwdBusyDestination(String appFwdBusyDestination) {
		this.appFwdBusyDestination = appFwdBusyDestination;
	}
	/**
	 * @return appFwdNoansCss
	 */
	public String getAppFwdNoansCss() {
		return appFwdNoansCss;
	}
	/**
	 * @param appFwdNoansCss セットする appFwdNoansCss
	 */
	public void setAppFwdNoansCss(String appFwdNoansCss) {
		this.appFwdNoansCss = appFwdNoansCss;
	}
	/**
	 * @return appFwdNoansDestination
	 */
	public String getAppFwdNoansDestination() {
		return appFwdNoansDestination;
	}
	/**
	 * @param appFwdNoansDestination セットする appFwdNoansDestination
	 */
	public void setAppFwdNoansDestination(String appFwdNoansDestination) {
		this.appFwdNoansDestination = appFwdNoansDestination;
	}
	/**
	 * @return appPickupGroup
	 */
	public String getAppPickupGroup() {
		return appPickupGroup;
	}
	/**
	 * @param appPickupGroup セットする appPickupGroup
	 */
	public void setAppPickupGroup(String appPickupGroup) {
		this.appPickupGroup = appPickupGroup;
	}
	/**
	 * @return appUseVmFlg
	 */
	public String getAppUseVmFlg() {
		return appUseVmFlg;
	}
	/**
	 * @param appUseVmFlg セットする appUseVmFlg
	 */
	public void setAppUseVmFlg(String appUseVmFlg) {
		this.appUseVmFlg = appUseVmFlg;
	}
	/**
	 * @return appIndex
	 */
	public String getAppIndex() {
		return appIndex;
	}
	/**
	 * @param appIndex セットする appIndex
	 */
	public void setAppIndex(String appIndex) {
		this.appIndex = appIndex;
	}
	/**
	 * @return appExternalPhoneNumber
	 */
	public String getAppExternalPhoneNumber() {
		return appExternalPhoneNumber;
	}
	/**
	 * @param appExternalPhoneNumber セットする appExternalPhoneNumber
	 */
	public void setAppExternalPhoneNumber(String appExternalPhoneNumber) {
		this.appExternalPhoneNumber = appExternalPhoneNumber;
	}
	/**
	 * @return appLineTextLabel
	 */
	public String getAppLineTextLabel() {
		return appLineTextLabel;
	}
	/**
	 * @param appLineTextLabel セットする appLineTextLabel
	 */
	public void setAppLineTextLabel(String appLineTextLabel) {
		this.appLineTextLabel = appLineTextLabel;
	}
	/**
	 * @return appRingSetting
	 */
	public String getAppRingSetting() {
		return appRingSetting;
	}
	/**
	 * @param appRingSetting セットする appRingSetting
	 */
	public void setAppRingSetting(String appRingSetting) {
		this.appRingSetting = appRingSetting;
	}



}	
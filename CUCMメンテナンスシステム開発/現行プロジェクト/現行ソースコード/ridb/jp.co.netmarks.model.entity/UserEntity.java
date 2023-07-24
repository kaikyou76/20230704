/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * UserEntity.java
 *
 * @date 2013/08/22
 * @version 1.0
 * @author KSC Yuchiro Yoshida
 */
package jp.co.netmarks.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.ksc.model.entity.BaseEntity;

/**
 * <pre>
 * APP_USERテーブル用モデルクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/22 KSC Yuchiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuchiro Yoshida
 * @version 1.0 2013/08/22
 */
public class UserEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 267562951797565777L;

	private BigDecimal appUserId     = null;
	private String userRole          = null;
	private String enabledSharedUse  = null;
	private String fulltimeEmployee  = null;
	private String bizEmployeeId     = null;
	private String loginId           = null;
	private String loginPassword     = null;
	private String cucmLoginId       = null;
	private String cucmLoginPassword = null;
	private String nameKanji         = null;
	private String nameKana          = null;
	private String birthday          = null;
	private String firstName         = null;
	private String lastName          = null;
	private String pin               = null;
	private String telephoneNumber   = null;
	private String enableCtiApplicationUse  = null;
	private String managerUserId     = null;
	private String department        = null;
	private Timestamp lstupdtPwd     = null;
	private String cucmUpdateRequestFlag    = null;
	private String deleted           = null;
	private String errorFlg          = null;




	/*********************************************************************
 * generated getter/setter
**********************************************************************/
	/**
	 * @return appUserId
	 */
	public BigDecimal getAppUserId() {
		return appUserId;
	}
	/**
	 * @param appUserId セットする appUserId
	 */
	public void setAppUserId(BigDecimal appUserId) {
		this.appUserId = appUserId;
	}
	/**
	 * @return userRole
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole セットする userRole
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @return enabledSharedUse
	 */
	public String getEnabledSharedUse() {
		return enabledSharedUse;
	}
	/**
	 * @param enabledSharedUse セットする enabledSharedUse
	 */
	public void setEnabledSharedUse(String enabledSharedUse) {
		this.enabledSharedUse = enabledSharedUse;
	}
	/**
	 * @return fulltimeEmployee
	 */
	public String getFulltimeEmployee() {
		return fulltimeEmployee;
	}
	/**
	 * @param fulltimeEmployee セットする fulltimeEmployee
	 */
	public void setFulltimeEmployee(String fulltimeEmployee) {
		this.fulltimeEmployee = fulltimeEmployee;
	}
	/**
	 * @return bizEmployeeId
	 */
	public String getBizEmployeeId() {
		return bizEmployeeId;
	}
	/**
	 * @param bizEmployeeId セットする bizEmployeeId
	 */
	public void setBizEmployeeId(String bizEmployeeId) {
		this.bizEmployeeId = bizEmployeeId;
	}
	/**
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId セットする loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}
	/**
	 * @param loginPassword セットする loginPassword
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	/**
	 * @return cucmLoginId
	 */
	public String getCucmLoginId() {
		return cucmLoginId;
	}
	/**
	 * @param cucmLoginId セットする cucmLoginId
	 */
	public void setCucmLoginId(String cucmLoginId) {
		this.cucmLoginId = cucmLoginId;
	}
	/**
	 * @return cucmLoginPassword
	 */
	public String getCucmLoginPassword() {
		return cucmLoginPassword;
	}
	/**
	 * @param cucmLoginPassword セットする cucmLoginPassword
	 */
	public void setCucmLoginPassword(String cucmLoginPassword) {
		this.cucmLoginPassword = cucmLoginPassword;
	}
	/**
	 * @return nameKanji
	 */
	public String getNameKanji() {
		return nameKanji;
	}
	/**
	 * @param nameKanji セットする nameKanji
	 */
	public void setNameKanji(String nameKanji) {
		this.nameKanji = nameKanji;
	}
	/**
	 * @return nameKana
	 */
	public String getNameKana() {
		return nameKana;
	}
	/**
	 * @param nameKana セットする nameKana
	 */
	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}
	/**
	 * @return birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday セットする birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName セットする firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName セットする lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return pin
	 */
	public String getPin() {
		return pin;
	}
	/**
	 * @param pin セットする pin
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}
	/**
	 * @return telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	/**
	 * @param telephoneNumber セットする telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	/**
	 * @return enableCtiApplicationUse
	 */
	public String getEnableCtiApplicationUse() {
		return enableCtiApplicationUse;
	}
	/**
	 * @param enableCtiApplicationUse セットする enableCtiApplicationUse
	 */
	public void setEnableCtiApplicationUse(String enableCtiApplicationUse) {
		this.enableCtiApplicationUse = enableCtiApplicationUse;
	}
	/**
	 * @return managerUserId
	 */
	public String getManagerUserId() {
		return managerUserId;
	}
	/**
	 * @param managerUserId セットする managerUserId
	 */
	public void setManagerUserId(String managerUserId) {
		this.managerUserId = managerUserId;
	}
	/**
	 * @return department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department セットする department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return lstupdtpwd
	 */
	public Timestamp getLstupdtpwd() {
		return lstupdtPwd;
	}
	/**
	 * @param lstupdtpwd セットする lstupdtpwd
	 */
	public void setLstupdtpwd(Timestamp lstupdtpwd) {
		this.lstupdtPwd = lstupdtpwd;
	}
	/**
	 * @return cucmUpdateRequestFlag
	 */
	public String getCucmUpdateRequestFlag() {
		return cucmUpdateRequestFlag;
	}
	/**
	 * @param cucmUpdateRequestFlag セットする cucmUpdateRequestFlag
	 */
	public void setCucmUpdateRequestFlag(String cucmUpdateRequestFlag) {
		this.cucmUpdateRequestFlag = cucmUpdateRequestFlag;
	}
	/**
	 * @return deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted セットする deleted
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return errorFlg
	 */
	public String getErrorFlg() {
		return errorFlg;
	}
	/**
	 * @param errorFlg セットする errorFlg
	 */
	public void setErrorFlg(String errorFlg) {
		this.errorFlg = errorFlg;
	}
}
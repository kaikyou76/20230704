/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionUserPhone.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.netmarks.model.CUCMPhoneModel;
import jp.co.netmarks.model.CUCMUserModel;
import jp.co.netmarks.model.CUDSoapModel;
import jp.co.netmarks.persistence.IndividualLineMapper;
import jp.co.netmarks.persistence.IndividualPhoneMapper;
import jp.co.netmarks.persistence.IndividualUserMapper;
import jp.co.netmarks.service.CUCMUserService;
import jp.co.netmarks.service.CUDSoapService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <pre>
 * 個別反映（ユーザと電話機）処理詳細
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class ReflectionUserPhone {

	private static final Log log = LogFactory.getLog(ReflectionUserPhone.class);

	@Autowired
	private IndividualUserMapper userMapper;
	@Autowired
	private IndividualPhoneMapper userphoneMapper;
	@Autowired
	private IndividualLineMapper lineMapper;

	@Autowired
	private CUDSoapService cmService;
	@Autowired
	private CUCMUserService cmUser;
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 * 個別反映処理（ユーザと電話機）
	 * @param param パラメータ
	 * @param clusterList クラスタ（新旧）
	 * @return String 成功可否判定文字
	 * @throws Exception
	 */
	public String individualReflection(Map<String,Object> param,List<Map<String,Object>> clusterList) throws Exception {

		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;
		String successRtnUUID = "";

		// 取得データ格納用
		Map<String, Object> userPhoneParameter = new HashMap<String, Object>();

		try{

			String oldcluster = clusterList.get(0).get("o_clusterid") == null ? "" : clusterList.get(0).get("o_clusterid").toString();
			String newcluster = clusterList.get(0).get("n_clusterid") == null ? "" : clusterList.get(0).get("n_clusterid").toString();
			if(oldcluster.equals("") && newcluster.equals("")){
				log.error("新旧クラスタ情報の取得に失敗しました。");
				throw new Exception();
			}
			boolean updateFlg = false;
			Integer orgPid = (Integer)param.get("phoneid");

			List<Map<String, Object>> paramValues  = userMapper.selectUserPhoneInfo(param);
			log.info("UserLine反映 Start : oldcluster = " + oldcluster + ", newcluster = " + newcluster);

			// User情報の取得
			// 取得したテーブル値を加工し、処理をする

			//Soap登録、更新、削除時用モデル
			CUDSoapModel soapModel = new CUDSoapModel();
			//USER設定
			CUCMUserModel userModel = new CUCMUserModel();

			for(int cnt = 0; paramValues != null && cnt < paramValues.size(); cnt++){
				userPhoneParameter = paramValues.get(cnt);
				String strupdflg = (String)userPhoneParameter.get("updflg");
				if(!updateFlg)updateFlg = strupdflg.equals("1") || strupdflg.equals("2") ? true : false;
				if(!updateFlg)updateFlg = userPhoneParameter.get("delflg").equals("1") ? true : false;;
			}

			if(updateFlg){
				log.info("Userと電話機の紐付処理：START");
				userModel.setUserId((String)userPhoneParameter.get("cucmloginid"));
				userModel.setClusterId(oldcluster);
				userModel.setFirstName((String)userPhoneParameter.get("firstnm"));
				userModel.setLastName((String)userPhoneParameter.get("lastnm"));
				userModel.setPassword((String)userPhoneParameter.get("cucmpass"));
				userModel.setPin((String)userPhoneParameter.get("pin"));

				//電話機の内線
				userModel.setTelNum("");

				//電話機との紐付
				param.put("clusterid",Integer.parseInt(oldcluster));
				List<Map<String, Object>> useList = userphoneMapper.selectUserToPhone(param);
				userModel.getAssociateMacAddres().clear();

				for (int i=0; i<useList.size(); i++){
					String usrMacaddress = (String)useList.get(i).get("macaddress");
					String phoneRegistFlg = (String)useList.get(i).get("phonepkid");
					String usrDelFlg = (String)useList.get(i).get("delflg");

					if(usrDelFlg.equals("1")){
						param.put("phoneid", (int)useList.get(i).get("phoneid"));
						userMapper.deleteTelDir(param);
						userMapper.delRCucmUserPhone(param);
					}else{
						//電話機の内線
						List<Map<String,Object>> selUserTelNoByCluster = userMapper.selUserTelNoByCluster(param);
						if(selUserTelNoByCluster != null && selUserTelNoByCluster.size() > 0){
							//一番若い番号を取得
							userModel.setTelNum((String)selUserTelNoByCluster.get(0).get("telephone_number"));
						}
						userModel.getAssociateMacAddres().add(usrMacaddress);

						//電話機の追加実行
						if(phoneRegistFlg == null){
							CUCMPhoneModel phoneModel = new CUCMPhoneModel();
							//電話機設定
							phoneModel.setMacAddress(usrMacaddress);
							phoneModel.setAddonModuleName1((String)useList.get(i).get("addmod1"));
							phoneModel.setAddonModuleName2((String)useList.get(i).get("addmod2"));
							phoneModel.setBranchID((String)useList.get(i).get("branchid"));
							phoneModel.setClusterId(newcluster);
							phoneModel.setCompanyID((String)useList.get(i).get("companyid"));
							phoneModel.setCssName((String)useList.get(i).get("css"));
							phoneModel.setDescription((String)useList.get(i).get("desc"));
							phoneModel.setDevicePoolName((String)useList.get(i).get("device"));
							phoneModel.setLocationName((String)useList.get(i).get("location"));
							phoneModel.setPhoneButtonTemplate((String)useList.get(i).get("phonetmp"));
							phoneModel.setPhoneProductName((String)useList.get(i).get("productname"));
							phoneModel.setSectionID((String)useList.get(i).get("sectionid"));
							phoneModel.setOwnerUserId((String)useList.get(i).get("ownerid"));
							//電話機情報設定
							soapModel.setPhoneModel(phoneModel);

							ts = txManager.getTransaction(dtd);
							//電話機の追加
							successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDPHONE, soapModel);
							log.info("AddPhone UUID : " + successRtnUUID);
							param.put("phoneuuid", successRtnUUID.replaceAll("[{|}]", ""));
							param.put("clusterid", Integer.parseInt(oldcluster));
							userphoneMapper.addSuccessUpdPhone(param);
							userphoneMapper.insertPhoneCluster(param);
							txManager.commit(ts);
						}
						param.put("phoneid", useList.get(i).get("phoneid"));
						userMapper.updRCucmUserPhone(param);
					}
				}
				param.put("phoneid", orgPid);

				//Soap用モデルに電話機、Userモデルセット
				soapModel.setUserModel(userModel);
				//ユーザの追加(電話機との紐付も行う)
				successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATEUSER_PHONE, soapModel);

				log.info("UpdateUserPhone UUID : " + successRtnUUID);
				log.info("Userと電話機の紐付削除処理：END");
			}

			return "success :" + successRtnUUID;
		}catch(Exception e){
			log.info("Userと電話機の紐付処理：ERROR");
			ts = txManager.getTransaction(dtd);
			userphoneMapper.updateErrorPhone(param);
			lineMapper.updateErrorLine(param);
			userMapper.updateErrorUser(param);
			userMapper.updateErrorUserPhone(param);
			lineMapper.updateErrorPhoneLine(param);
			txManager.commit(ts);
			throw e;
		}
	}



	/**
	 * 関連が削除されたユーザーの個別反映
	 * @param param パラメータ
	 * @param clusterList クラスタ（新旧）
	 * @return String 成功可否判定文字
	 * @throws Exception
	 */
	public String delUserPhoneReflection(Map<String,Object> param,List<Map<String,Object>> clusterList) throws Exception {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;
		// 取得データ格納用
		Map<String, Object> userPhoneDelParameter = new HashMap<String, Object>();

		try{
			// 個別反映処理内では、関連を削除されたユーザーが
		    // CUCMに反映されないので、関連が削除されたユーザーの反映を行う。
			String oldcluster = clusterList.get(0).get("o_clusterid") == null ? "" : clusterList.get(0).get("o_clusterid").toString();
			String successRtnUUID = "";

	        // 対象のphoneIdに関連付けられたユーザーのうち、
	        // 関連付けが削除されているユーザーを取得します
	        List<Integer> delUserIdList = userMapper.selDelPhoneUserList(param);

	        log.info("個別反映用：関連削除ユーザ更新処理：START");
	        for (int i = 0; delUserIdList != null && i < delUserIdList.size(); i++) {
				//Soap登録、更新、削除時用モデル
				CUDSoapModel soapModel = new CUDSoapModel();
				//USER設定
				CUCMUserModel userModel = new CUCMUserModel();

	            int appUserId = (int)delUserIdList.get(i);

				param.put("userid", appUserId);
	        	List<Map<String, Object>> delUserList  = userMapper.selectUserAndPhoneInfo(param);
	        	userPhoneDelParameter = delUserList.get(0);

	            log.debug("============> User ID = " + appUserId);
	            if (appUserId > 0) {
					//削除処理
					userModel.setUserId((String)userPhoneDelParameter.get("cucmloginid"));
					userModel.setClusterId(oldcluster);
					userModel.setFirstName((String)userPhoneDelParameter.get("firstnm"));
					userModel.setLastName((String)userPhoneDelParameter.get("lastnm"));
					userModel.setPassword((String)userPhoneDelParameter.get("cucmpass"));
					userModel.setPin((String)userPhoneDelParameter.get("pin"));

					//電話機の内線
					userModel.setTelNum("");

					//電話機との紐付
					String delMacAddress = (String)userPhoneDelParameter.get("macaddress");
					param.put("clusterid", Integer.parseInt(oldcluster));
					List<Map<String, Object>> useList = userphoneMapper.selectUserToPhone(param);
					userModel.getAssociateMacAddres().clear();

					for (int j=0; useList != null && j<useList.size(); j++){
						String usrMacaddress = (String)useList.get(j).get("macaddress");
						String phoneRegistFlg = (String)useList.get(j).get("phonepkid");

						//電話機の内線
						List<Map<String,Object>> selUserTelNoByCluster = userMapper.selUserTelNoByCluster(param);
						if(selUserTelNoByCluster != null && selUserTelNoByCluster.size() > 0){
							//一番若い番号を取得
							userModel.setTelNum((String)selUserTelNoByCluster.get(0).get("telephone_number"));
						}

						if(!usrMacaddress.equals(delMacAddress)){
							userModel.getAssociateMacAddres().add(usrMacaddress);

							//電話機の追加実行
							if(phoneRegistFlg == null){
								CUCMPhoneModel phoneModel = new CUCMPhoneModel();
								//電話機設定
								phoneModel.setMacAddress(usrMacaddress);
								phoneModel.setAddonModuleName1((String)useList.get(i).get("addmod1"));
								phoneModel.setAddonModuleName2((String)useList.get(i).get("addmod2"));
								phoneModel.setBranchID((String)useList.get(i).get("branchid"));
								phoneModel.setClusterId(oldcluster);
								phoneModel.setCompanyID((String)useList.get(i).get("companyid"));
								phoneModel.setCssName((String)useList.get(i).get("css"));
								phoneModel.setDescription((String)useList.get(i).get("desc"));
								phoneModel.setDevicePoolName((String)useList.get(i).get("device"));
								phoneModel.setLocationName((String)useList.get(i).get("location"));
								phoneModel.setPhoneButtonTemplate((String)useList.get(i).get("phonetmp"));
								phoneModel.setPhoneProductName((String)useList.get(i).get("productname"));
								phoneModel.setSectionID((String)useList.get(i).get("sectionid"));
								phoneModel.setOwnerUserId((String)useList.get(i).get("ownerid"));
								//電話機情報設定
								soapModel.setPhoneModel(phoneModel);
								ts = txManager.getTransaction(dtd);
								//電話機の追加
								successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDPHONE, soapModel);
								log.info("AddPhone UUID : " + successRtnUUID);
								param.put("phoneuuid", successRtnUUID.replaceAll("[{|}]", ""));
								param.put("clusterid", Integer.parseInt(oldcluster));
								userphoneMapper.addSuccessUpdPhone(param);
								userphoneMapper.insertPhoneCluster(param);
								txManager.commit(ts);
							}
						}
					}

					//Soap用モデルに電話機、Userモデルセット
					soapModel.setUserModel(userModel);
					//ユーザの追加(電話機との紐付も行う)
					successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATEUSER_PHONE, soapModel);
					ts = txManager.getTransaction(dtd);
					userMapper.deleteTelDir(param);
					userMapper.delRCucmUserPhone(param);
					txManager.commit(ts);
					log.info("UpdateUserPhone UUID : " + successRtnUUID);
	            }
	        }
	        log.info("個別反映用：関連削除ユーザ更新処理：END");
			return "success : delUserPhone : " + delUserIdList.size();

		}catch(Exception e){
	        log.info("個別反映用：関連削除ユーザ更新処理：ERROR");
			ts = txManager.getTransaction(dtd);
			userphoneMapper.updateErrorPhone(param);
			lineMapper.updateErrorLine(param);
			userMapper.updateErrorUser(param);
			userMapper.updateErrorUserPhone(param);
			lineMapper.updateErrorPhoneLine(param);
			txManager.commit(ts);
			throw e;
		}
	}
}


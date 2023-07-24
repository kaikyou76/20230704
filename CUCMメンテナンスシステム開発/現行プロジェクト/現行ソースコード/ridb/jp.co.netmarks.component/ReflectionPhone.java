/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionPhone.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.netmarks.model.CUCMLineModel;
import jp.co.netmarks.model.CUCMPhoneModel;
import jp.co.netmarks.model.CUCMUserModel;
import jp.co.netmarks.model.CUDSoapModel;
import jp.co.netmarks.persistence.IndividualLineMapper;
import jp.co.netmarks.persistence.IndividualPhoneMapper;
import jp.co.netmarks.persistence.IndividualUserMapper;
import jp.co.netmarks.service.CUCMPhoneService;
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
 * 個別反映（電話機）処理詳細
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class ReflectionPhone {

	private static final Log log = LogFactory.getLog(ReflectionPhone.class);

	@Autowired
	private IndividualPhoneMapper phoneMapper;
	@Autowired
	private IndividualLineMapper lineMapper;
	@Autowired
	private IndividualUserMapper userMapper;

	@Autowired
	private CUDSoapService cmService;
	@Autowired
	private CUCMPhoneService cmPhone;
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 * 個別反映処理（電話機）
	 * @param param パラメータ（PhoneID等）
	 * @param clusterList クラスタ（新旧）
	 * @return String 成功可否判定文字
	 * @throws Exception
	 */
	public String individualReflection(Map<String,Object> param,List<Map<String,Object>> clusterList) throws Exception {
		String successRtnUUID = "";
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;

		// 取得データ格納用
		Map<String, Object> phoneParameter = new HashMap<String, Object>();

		try{
			String updateFlg,deleteFlg,macAddress,oldpkId;

			List<Map<String, Object>> paramValues  = phoneMapper.selectPhoneInfo(param);
			String oldcluster = clusterList.get(0).get("o_clusterid") == null ? "" : clusterList.get(0).get("o_clusterid").toString();
			String newcluster = clusterList.get(0).get("n_clusterid") == null ? "" : clusterList.get(0).get("n_clusterid").toString();
			if(oldcluster.equals("") && newcluster.equals("")){
				log.error("新旧クラスタ情報の取得に失敗しました。");
				throw new Exception();
			}

			log.info("電話機反映 Start : oldcluster = " + oldcluster + ", newcluster = " + newcluster);

			// phone情報の取得
			// 取得したテーブル値を加工し、処理をする
			for(int cnt = 0; cnt < paramValues.size(); cnt++){

				//Soap登録、更新、削除時用モデル
				CUDSoapModel soapModel = new CUDSoapModel();
				//Model
				CUCMPhoneModel phoneModel = new CUCMPhoneModel();
				CUCMUserModel userModel   = new CUCMUserModel();
				CUCMLineModel lineModel   = new CUCMLineModel();

				phoneParameter = paramValues.get(cnt);

				oldpkId = (String)phoneParameter.get("pkid");
				updateFlg = (String)phoneParameter.get("updateflg");
				deleteFlg = (String)phoneParameter.get("delflg");
				macAddress = (String)phoneParameter.get("macaddress");

				if(!updateFlg.equals("0") && !updateFlg.equals("3")){
					log.info("電話機に紐付いていたライン削除処理：START");
					//Lineの削除(更新対象電話機に紐付いて「いた」ライン（画面では削除済）
					//電話機に紐付くラインを取得
					List<Map<String, Object>> selPhoneLine  = lineMapper.selectPhoneLineInfo(param);
					for(int linecnt=0; selPhoneLine!=null && linecnt<selPhoneLine.size();linecnt++){
						Map<String, Object>lineParameter = selPhoneLine.get(linecnt);
						Map<String,Object> lineDelParam = new HashMap<String,Object>();

						if(lineParameter.get("linedelflg").equals("1")){
							ts = txManager.getTransaction(dtd);
							lineDelParam.put("phoneid", param.get("phoneid"));
							lineDelParam.put("lineid", (int)lineParameter.get("lineid"));

							if(lineParameter.get("pkid") != null){
								lineModel.setDirectoryNumber((String)lineParameter.get("directno"));
								lineModel.setClusterId(lineParameter.get("linecluster").toString());
								lineDelParam.put("clusterid", (int)lineParameter.get("linecluster"));
								//Line情報設定
								soapModel.setLineModel(lineModel);
								cmService.execute(CUDSoapService.SOAPTYPE.REMOVELINE, soapModel);
								lineMapper.deleteLineCluster(lineDelParam);
							}
							lineMapper.deleteLineInfo(lineDelParam);
							lineMapper.delRCucmPhoneLine(lineDelParam);
							lineMapper.deleteTelDir(lineDelParam);
							txManager.commit(ts);
						}
					}
					log.info("電話機に紐付いていたライン削除処理削除処理：END");

					if(deleteFlg.equals("1")){
						log.info("電話機削除 START：" + param.get("phoneid"));
						//削除処理
						phoneModel.setMacAddress(macAddress);
						phoneModel.setClusterId(oldcluster);
						//電話機情報設定
						soapModel.setPhoneModel(phoneModel);

						//電話機の削除
						if(!oldcluster.equals("")){
							cmService.execute(CUDSoapService.SOAPTYPE.REMOVEPHONE, soapModel);
							param.put("clusterid", Integer.parseInt(oldcluster));
							phoneMapper.deletePhoneCluster(param);
						}
						phoneMapper.deletePhoneInfo(param);
						log.info("電話機削除 END：" + param.get("phoneid"));

					}else{
						log.info("電話機追加・更新 START：" + param.get("phoneid"));
						//電話機設定
						phoneModel.setMacAddress(macAddress);

						phoneModel.setAddonModuleName1((String)phoneParameter.get("addmod1"));
						phoneModel.setAddonModuleName2((String)phoneParameter.get("addmod2"));
						phoneModel.setBranchID((String)phoneParameter.get("branchid"));
						phoneModel.setClusterId(newcluster);
						phoneModel.setCompanyID((String)phoneParameter.get("companyid"));
						phoneModel.setCssName((String)phoneParameter.get("css"));
						phoneModel.setDescription((String)phoneParameter.get("desc"));
						phoneModel.setDevicePoolName((String)phoneParameter.get("device"));
						phoneModel.setLocationName((String)phoneParameter.get("location"));
						phoneModel.setPhoneButtonTemplate((String)phoneParameter.get("phonetmp"));
						phoneModel.setPhoneProductName((String)phoneParameter.get("productname"));
						phoneModel.setSectionID((String)phoneParameter.get("sectionid"));
						phoneModel.setOwnerUserId((String)phoneParameter.get("ownerid"));

						//電話機情報設定
						soapModel.setPhoneModel(phoneModel);

						//追加・更新処理
						if(oldpkId != null){
							log.info("電話機更新 START");
							//クラスタ跨ぎ判断
							if(oldcluster.equals(newcluster)){
								//クラスタ跨ぎ無し
						    	log.info(" ---------------- クラスタ異動無 --------------------------");
								//電話機の更新
								successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATEPHONE, soapModel);
								log.info("UpdatePhone UUID : " + successRtnUUID);

						    }else{
						    	//クラスタ跨ぎ有
						    	log.info(" ---------------- クラスタ異動有 --------------------------");
						    	// [各種情報の登録(新クラスタ）]

						    	//追加電話機に紐付くラインの追加
						    	param.put("clusterid", Integer.parseInt(oldcluster));
								List<Map<String, Object>> lines = phoneMapper.selectPhoneToLine(param);
								phoneModel.getAssociateLine().clear();

								for(int lc = 0; lc < lines.size(); lc++){
									CUCMLineModel reflineModel   = new CUCMLineModel();
									//追加・更新処理
									reflineModel.setDirectoryNumber((String)lines.get(lc).get("directno"));
									reflineModel.setLineTextLabel((String)lines.get(lc).get("linelabel"));
									reflineModel.setExternalPhoneNumber((String)lines.get(lc).get("expnummask"));
									reflineModel.setCallPickUpGroupName((String)lines.get(lc).get("pickupnm"));
									reflineModel.setClusterId(newcluster);
									reflineModel.setFwdAllCSS((String)lines.get(lc).get("allcss"));
									reflineModel.setFwdBusyCSS((String)lines.get(lc).get("busycss"));
									reflineModel.setFwdNoAnsCSS((String)lines.get(lc).get("noanscss"));
									reflineModel.setFwdBusyDestination((String)lines.get(lc).get("busyno"));
									reflineModel.setFwdNoansDestination((String)lines.get(lc).get("noansno"));
									reflineModel.setIndex((String)lines.get(lc).get("index"));
									reflineModel.setPhoneProductName((String)lines.get(lc).get("product"));
									reflineModel.setRingSetting((String)lines.get(lc).get("ringnm"));
									reflineModel.setUseVMFlg((boolean)lines.get(lc).get("vmflg").equals("1") ? true : false);

									//soapModelにライン情報セット
									soapModel.setLineModel(reflineModel);
									phoneModel.getAssociateLine().add(reflineModel);

									//ラインの追加実行
									ts = txManager.getTransaction(dtd);
									successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDLINE, soapModel);
									log.info("AddLine UUID : " + successRtnUUID);

									param.put("lineid", (int)lines.get(lc).get("lineid"));
									param.put("lineuuid", successRtnUUID.replaceAll("[{|}]", ""));
									param.put("clusterid", Integer.parseInt(newcluster));
									lineMapper.insertLineCluster(param);
									lineMapper.addSuccessUpdLine(param);
									lineMapper.updRCucmPhoneLine(param);

									//Line情報設定
									reflineModel.setClusterId(oldcluster);
									param.put("clusterid", Integer.parseInt(oldcluster));
									soapModel.setLineModel(reflineModel);
									//Lineの削除(旧クラスタ)
									cmService.execute(CUDSoapService.SOAPTYPE.REMOVELINE, soapModel);
									lineMapper.deleteLineCluster(param);
									txManager.commit(ts);
								}

						    	//電話機の追加
								ts = txManager.getTransaction(dtd);
								soapModel.setPhoneModel(phoneModel);
						    	successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDPHONE_LINE, soapModel);
								log.info("AddPhone UUID : " + successRtnUUID);
								param.put("phoneuuid", successRtnUUID.replaceAll("[{|}]", ""));
								param.put("clusterid", Integer.parseInt(newcluster));
								phoneMapper.insertPhoneCluster(param);

								//電話機の削除(旧クラスタ）
						    	phoneModel.setClusterId(oldcluster);
								soapModel.setPhoneModel(phoneModel);
						    	cmService.execute(CUDSoapService.SOAPTYPE.REMOVEPHONE, soapModel);
						    	param.put("clusterid", Integer.parseInt(oldcluster));
						    	phoneMapper.deletePhoneCluster(param);

								txManager.commit(ts);

						    	//追加電話機使用ユーザ情報の更新
								param.put("clusterid", Integer.parseInt(oldcluster));
								List<Map<String, Object>> users = phoneMapper.selectPhoneToUser(param);
								List<String> clusterAllList  = userMapper.selectAllClusterId(param);

								for(int uc = 0; users != null && uc < users.size(); uc++){
									userModel.setUserId((String)users.get(uc).get("cucmloginid"));
									userModel.setFirstName((String)users.get(uc).get("firstnm"));
									userModel.setLastName((String)users.get(uc).get("lastnm"));
									userModel.setPassword((String)users.get(uc).get("cucmpass"));
									userModel.setPin((String)users.get(uc).get("pin"));

									// 全クラスタに対して対象ユーザの更新を行う。
									for(int cc=0;cc<clusterAllList.size();cc++){
										String updCluster = clusterAllList.get(cc);
										userModel.getAssociateMacAddres().clear();
										userModel.setTelNum("");
										Map<String,Object> selUserTelNoByClusterParam = new HashMap<String,Object>();
										selUserTelNoByClusterParam.put("clusterid",Integer.parseInt(updCluster));
										selUserTelNoByClusterParam.put("userid",(Integer)users.get(uc).get("userid"));

										List<Map<String,Object>> selUserTelNoByCluster = userMapper.selUserTelNoByCluster(selUserTelNoByClusterParam);
										for(int i=0;selUserTelNoByCluster != null && i < selUserTelNoByCluster.size();i++){
											//一番若い番号を取得
											if(i==0)userModel.setTelNum((String)selUserTelNoByCluster.get(0).get("telephone_number"));
											userModel.getAssociateMacAddres().add((String)selUserTelNoByCluster.get(i).get("mac_address"));
										}

										userModel.setClusterId(updCluster);

										//soapModelにUSER情報セット
										soapModel.setUserModel(userModel);
										//USERの更新実行
										successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATEUSER_PHONE, soapModel);
										log.info("UpdateUSER UUID : " + successRtnUUID);
									}
									ts = txManager.getTransaction(dtd);
									userMapper.addSuccessUpdUser(param);
									userMapper.updRCucmUserPhone(param);
									txManager.commit(ts);
								}
							}
							// 成功時更新
							phoneMapper.addSuccessUpdPhone(param);
							log.info("電話機更新 END");
						}else{
							log.info("電話機追加 START");
							//電話機の追加
							successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDPHONE, soapModel);
							log.info("AddPhone UUID : " + successRtnUUID);
							param.put("phoneuuid", successRtnUUID.replaceAll("[{|}]", ""));
							param.put("clusterid", Integer.parseInt(newcluster));
							successAddRef(param);
							log.info("電話機追加 END");
						}
						// Device リセット
						cmPhone.DoReset(macAddress, newcluster);
					}
				}
			}
			return "success :" + successRtnUUID;
		}catch(Exception e){
			log.error("電話機反映 ERROR終了");
			ts = txManager.getTransaction(dtd);
			phoneMapper.updateErrorPhone(param);
			lineMapper.updateErrorLine(param);
			userMapper.updateErrorUser(param);
			userMapper.updateErrorUserPhone(param);
			lineMapper.updateErrorPhoneLine(param);
			txManager.commit(ts);
			throw e;
		}
	}

	/**
	 * 削除処理成功時更新
	 * @param param
	 */
	public void successRemoveRef(Map<String,Object> param){
		phoneMapper.deletePhoneInfo(param);
		phoneMapper.deletePhoneCluster(param);
	}

	/**
	 * 追加処理成功時更新
	 * @param param
	 */
	public void successAddRef(Map<String,Object> param){
		phoneMapper.addSuccessUpdPhone(param);
		phoneMapper.insertPhoneCluster(param);
	}

}


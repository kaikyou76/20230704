/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionUser.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 個別反映（User）処理詳細
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class ReflectionUser {

	private static final Log log = LogFactory.getLog(ReflectionUser.class);

	@Autowired
	private IndividualPhoneMapper phoneMapper;
	@Autowired
	private IndividualLineMapper lineMapper;
	@Autowired
	private IndividualUserMapper userMapper;

	@Autowired
	private CUDSoapService cmService;
	@Autowired
	private CUCMUserService cmUser;
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 * 個別反映処理（User）
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
		Map<String, Object> userParameter = new HashMap<String, Object>();

		try{
			String updateFlg,deleteFlg;

			// 全クラスタIDと対象ユーザのラインがそのクラスタに存在すれば、紐付く電話番号を取得する。
			List<Map<String, Object>> paramValues  = userMapper.selectUserInfo(param);
			List<String> clusterAllList  = userMapper.selectAllClusterId(param);

			log.info("User反映 Start ");

			// User情報の取得
			// 取得したテーブル値を加工し、処理をする
			for(int cnt = 0; paramValues != null && cnt < paramValues.size(); cnt++){
				//Soap登録、更新、削除時用モデル
				CUDSoapModel soapModel = new CUDSoapModel();
				//USER設定
				CUCMUserModel userModel = new CUCMUserModel();

				userParameter = paramValues.get(cnt);

				updateFlg = (String)userParameter.get("updflg");
				deleteFlg = (String)userParameter.get("delflg");

				if(!updateFlg.equals("0") && !updateFlg.equals("3")){
					if(deleteFlg.equals("1")){
						log.info("User削除処理：Start");
						userModel.setUserId((String)userParameter.get("cucmloginid"));
						userModel.setFirstName((String)userParameter.get("firstnm"));
						userModel.setLastName((String)userParameter.get("lastnm"));
						userModel.setPassword((String)userParameter.get("cucmpass"));
						userModel.setPin((String)userParameter.get("pin"));

						for(int cl=0;cl<clusterAllList.size();cl++){
							userModel.setClusterId(clusterAllList.get(cl));
							//User情報設定
							soapModel.setUserModel(userModel);
							//Userの削除
							cmService.execute(CUDSoapService.SOAPTYPE.REMOVEUSER, soapModel);
						}
						// 削除ユーザの電話機情報を更新する
						userMapper.deleteUserInfo(param);
						log.info("User削除処理：END");
					}else{
						log.info("User更新処理：START");
						//更新処理
						userModel.setUserId((String)userParameter.get("cucmloginid"));
						userModel.setFirstName((String)userParameter.get("firstnm"));
						userModel.setLastName((String)userParameter.get("lastnm"));
						userModel.setPassword((String)userParameter.get("cucmpass"));
						userModel.setPin((String)userParameter.get("pin"));

						// 全クラスタに対して対象ユーザの更新を行う。
						for(int cc=0;cc<clusterAllList.size();cc++){
							userModel.setClusterId(clusterAllList.get(cc));

							//soapModelにUSER情報セット
							soapModel.setUserModel(userModel);
							//USERの更新実行
							ts = txManager.getTransaction(dtd);
							successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATEUSER, soapModel);
							log.info("UpdateUSER UUID : " + successRtnUUID);
							userMapper.addSuccessUpdUser(param);
							txManager.commit(ts);
						}

						log.info("User更新処理：END");
					}
				}
			}
			return "success :" + successRtnUUID;
		}catch(Exception e){
			log.info("User処理ERROR");
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
}


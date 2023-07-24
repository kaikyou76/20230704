/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionLine.java
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
import jp.co.netmarks.model.CUDSoapModel;
import jp.co.netmarks.persistence.IndividualLineMapper;
import jp.co.netmarks.persistence.IndividualPhoneMapper;
import jp.co.netmarks.persistence.IndividualUserMapper;
import jp.co.netmarks.service.CUCMLineService;
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
 * 個別反映（Line）処理詳細
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */

@Component
public class ReflectionLine {

	private static final Log log = LogFactory.getLog(ReflectionLine.class);

	@Autowired
	private IndividualPhoneMapper phoneMapper;
	@Autowired
	private IndividualLineMapper lineMapper;
	@Autowired
	private IndividualUserMapper userMapper;

	@Autowired
	private CUDSoapService cmService;
	@Autowired
	private CUCMLineService cmLine;
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 * 個別反映処理（Line反映）
	 *
	 * @param param
	 * @param clusterList
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
		Map<String, Object> lineParameter = new HashMap<String, Object>();

		try{
			String oldcluster = clusterList.get(0).get("o_clusterid") == null ? "" : clusterList.get(0).get("o_clusterid").toString();
			String newcluster = clusterList.get(0).get("n_clusterid") == null ? "" : clusterList.get(0).get("n_clusterid").toString();
			if(oldcluster.equals("") && newcluster.equals("")){
				log.error("新旧クラスタ情報の取得に失敗しました。");
				throw new Exception();
			}
			String updateFlg,deleteFlg,oldpkId;

			List<Map<String, Object>> paramValues  = lineMapper.selectLineInfo(param);
			log.info("LINE反映 Start : oldcluster = " + oldcluster + ", newcluster = " + newcluster);
			param.put("lineuuid",null);

			// Line情報の取得
			// 取得したテーブル値を加工し、処理をする
			for(int cnt = 0; cnt < paramValues.size(); cnt++){

				//Soap登録、更新、削除時用モデル
				CUDSoapModel soapModel = new CUDSoapModel();
				//ライン設定
				CUCMLineModel lineModel = new CUCMLineModel();

				lineParameter = paramValues.get(cnt);

				updateFlg = (String)lineParameter.get("updflg");
				deleteFlg = (String)lineParameter.get("delflg");
				oldpkId = lineParameter.get("pkid") != null ? (String)lineParameter.get("pkid") : (String)param.get("lineuuid");

				if(!updateFlg.equals("0") && !updateFlg.equals("3")){
					if(deleteFlg.equals("1")){
						log.info("Line削除処理：START");
						//削除処理
						//Lineの削除
						if(!oldcluster.equals("")){
							lineModel.setDirectoryNumber((String)lineParameter.get("directno"));
							lineModel.setClusterId(oldcluster);
							param.put("clusterid", Integer.parseInt(oldcluster));
							//Line情報設定
							soapModel.setLineModel(lineModel);
							cmService.execute(CUDSoapService.SOAPTYPE.REMOVELINE, soapModel);
							lineMapper.deleteLineCluster(param);
						}
						lineMapper.deleteLineInfo(param);
						log.info("Line削除処理：END");
					}else{
						//追加・更新処理
						lineModel.setDirectoryNumber((String)lineParameter.get("directno"));
						lineModel.setLineTextLabel((String)lineParameter.get("linelabel"));
						lineModel.setExternalPhoneNumber((String)lineParameter.get("expnummask"));
						lineModel.setCallPickUpGroupName((String)lineParameter.get("pickupnm"));
						lineModel.setClusterId(newcluster);
						lineModel.setFwdAllCSS((String)lineParameter.get("allcss"));
						lineModel.setFwdBusyCSS((String)lineParameter.get("busycss"));
						lineModel.setFwdNoAnsCSS((String)lineParameter.get("noanscss"));
						lineModel.setFwdBusyDestination((String)lineParameter.get("busyno"));
						lineModel.setFwdNoansDestination((String)lineParameter.get("noansno"));
						lineModel.setIndex((String)lineParameter.get("index"));
						lineModel.setPhoneProductName((String)lineParameter.get("product"));
						lineModel.setRingSetting((String)lineParameter.get("ringnm"));
						lineModel.setUseVMFlg((boolean)lineParameter.get("vmflg").equals("1") ? true : false);

						//soapModelにライン情報セット
						soapModel.setLineModel(lineModel);

						if(oldpkId != null){
							log.info("Line更新処理：START");
							//クラスタ跨ぎ判断
							if(oldcluster.equals(newcluster)){
								//跨ぎ無し
								//ラインの更新実行
								successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATELINE, soapModel);
								log.info("UpdateLine UUID : " + successRtnUUID);
						    }else{
								//ラインの追加実行
								ts = txManager.getTransaction(dtd);
								successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDLINE, soapModel);
								log.info("AddLine UUID : " + successRtnUUID);

								param.put("lineuuid", successRtnUUID.replaceAll("[{|}]", ""));
								param.put("clusterid", Integer.parseInt(newcluster));
								lineMapper.insertLineCluster(param);
								txManager.commit(ts);

						    	//ラインの削除
								lineModel.setClusterId(oldcluster);
								param.put("clusterid", Integer.parseInt(oldcluster));
								//ライン情報設定
								ts = txManager.getTransaction(dtd);
								soapModel.setLineModel(lineModel);
						    	cmService.execute(CUDSoapService.SOAPTYPE.REMOVELINE, soapModel);
						    	lineMapper.deleteLineCluster(param);
								txManager.commit(ts);
						    }
							log.info("Line更新処理：END");
						}else{
							log.info("Line追加処理：START");
							//ラインの追加実行
							successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDLINE, soapModel);
							log.info("AddLine UUID : " + successRtnUUID);

							param.put("lineuuid", successRtnUUID.replaceAll("[{|}]", ""));
							param.put("clusterid", Integer.parseInt(newcluster));
							lineMapper.insertLineCluster(param);
							log.info("Line更新処理：END");
						}
						lineMapper.addSuccessUpdLine(param);
					}
				}
			}
			return "success :" + successRtnUUID;
		}catch(Exception e){
			log.error("Line処理ERROR");
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
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionPhoneLine.java
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
import jp.co.netmarks.model.CUDSoapModel;
import jp.co.netmarks.persistence.IndividualLineMapper;
import jp.co.netmarks.persistence.IndividualPhoneMapper;
import jp.co.netmarks.persistence.IndividualUserMapper;
import jp.co.netmarks.service.CUCMLineService;
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
 * 個別反映（Lineと電話機）処理詳細
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class ReflectionPhoneLine {

	private static final Log log = LogFactory.getLog(ReflectionPhoneLine.class);

	@Autowired
	private IndividualLineMapper lineMapper;
	@Autowired
	private IndividualUserMapper userMapper;
	@Autowired
	private IndividualPhoneMapper phonelineMapper;

	@Autowired
	private CUDSoapService cmService;
	@Autowired
	private CUCMLineService cmLine;
	@Autowired
	private CUCMPhoneService cmPhone;
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 * 個別反映処理（Lineと電話機）
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
		Map<String, Object> phoneLineParameter = new HashMap<String, Object>();

		try{
			String oldcluster = clusterList.get(0).get("o_clusterid") == null ? "" : clusterList.get(0).get("o_clusterid").toString();
			String newcluster = clusterList.get(0).get("n_clusterid") == null ? "" : clusterList.get(0).get("n_clusterid").toString();
			if(oldcluster.equals("") && newcluster.equals("")){
				log.error("新旧クラスタ情報の取得に失敗しました。");
				throw new Exception();
			}
			boolean updateFlg = false;

			List<Map<String, Object>> paramValues  = lineMapper.selectPhoneLineInfo(param);
			log.info("電話機Line反映 Start : oldcluster = " + oldcluster + ", newcluster = " + newcluster);

			// Line情報の取得
			// 取得したテーブル値を加工し、処理をする

			//Soap登録、更新、削除時用モデル
			CUDSoapModel soapModel = new CUDSoapModel();
			CUCMPhoneModel phoneModel = new CUCMPhoneModel();

			for(int cnt=0;cnt<paramValues.size();cnt++){
				phoneLineParameter = paramValues.get(cnt);
				String strupdflg = (String)phoneLineParameter.get("updflg");
				if(!updateFlg)updateFlg = strupdflg.equals("1") || strupdflg.equals("2") ? true : false;
				if(!updateFlg)updateFlg = phoneLineParameter.get("delflg").equals("1") ? true : false;
			}

			if(updateFlg){
				log.info("電話機とLineの紐付処理：START");

				//電話機との紐付
				phoneModel.setMacAddress((String)phoneLineParameter.get("macaddress"));
				phoneModel.setAddonModuleName1((String)phoneLineParameter.get("addmod1"));
				phoneModel.setAddonModuleName2((String)phoneLineParameter.get("addmod2"));
				phoneModel.setBranchID((String)phoneLineParameter.get("branchid"));
				phoneModel.setClusterId(newcluster);
				phoneModel.setCompanyID((String)phoneLineParameter.get("companyid"));
				phoneModel.setCssName((String)phoneLineParameter.get("css"));
				phoneModel.setDescription((String)phoneLineParameter.get("desc"));
				phoneModel.setDevicePoolName((String)phoneLineParameter.get("poolnm"));
				phoneModel.setLocationName((String)phoneLineParameter.get("location"));
				phoneModel.setPhoneButtonTemplate((String)phoneLineParameter.get("phonetmp"));
				phoneModel.setPhoneProductName((String)phoneLineParameter.get("product"));
				phoneModel.setSectionID((String)phoneLineParameter.get("sectionid"));
				phoneModel.setOwnerUserId((String)phoneLineParameter.get("ownerid"));

				//電話機モデルに、削除のライン以外を設定する(紐付ける)
		    	param.put("clusterid", Integer.parseInt(newcluster));
				List<Map<String, Object>> lines = phonelineMapper.selectPhoneToLine(param);
				phoneModel.getAssociateLine().clear();

				for (int i=0; i<lines.size(); i++){
					//ライン設定
					CUCMLineModel lineModel = new CUCMLineModel();
					String lineRegistFlg = (String)lines.get(i).get("pkid");

					//追加・更新処理
					lineModel.setDirectoryNumber((String)lines.get(i).get("directno"));
					lineModel.setLineTextLabel((String)lines.get(i).get("linelabel"));
					lineModel.setExternalPhoneNumber((String)lines.get(i).get("expnummask"));
					lineModel.setCallPickUpGroupName((String)lines.get(i).get("pickupnm"));
					lineModel.setClusterId(newcluster);
					lineModel.setFwdAllCSS((String)lines.get(i).get("allcss"));
					lineModel.setFwdBusyCSS((String)lines.get(i).get("busycss"));
					lineModel.setFwdNoAnsCSS((String)lines.get(i).get("noanscss"));
					lineModel.setFwdBusyDestination((String)lines.get(i).get("busyno"));
					lineModel.setFwdNoansDestination((String)lines.get(i).get("noansno"));
					lineModel.setIndex((String)lines.get(i).get("index"));
					lineModel.setPhoneProductName((String)lines.get(i).get("product"));
					lineModel.setRingSetting((String)lines.get(i).get("ringnm"));
					lineModel.setUseVMFlg((boolean)lines.get(i).get("vmflg").equals("1") ? true : false);

					//soapModelにライン情報セット
					soapModel.setLineModel(lineModel);
					phoneModel.getAssociateLine().add(lineModel);

					//ラインの追加実行
					if(lineRegistFlg == null){
						ts = txManager.getTransaction(dtd);
						successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.ADDLINE, soapModel);
						log.info("AddLine UUID : " + successRtnUUID);

						param.put("lineid", (int)lines.get(i).get("lineid"));
						param.put("lineuuid", successRtnUUID.replaceAll("[{|}]", ""));
						param.put("clusterid", Integer.parseInt(newcluster));
						lineMapper.insertLineCluster(param);
						txManager.commit(ts);
					}
				}

				//電話機情報設定
				soapModel.setPhoneModel(phoneModel);
				//電話機の追加(ライン紐付も行う)
				successRtnUUID = cmService.execute(CUDSoapService.SOAPTYPE.UPDATEPHONE_LINE, soapModel);
				// Device リセット
				cmPhone.DoReset((String)phoneLineParameter.get("macaddress"), newcluster);
				lineMapper.deleteTelDir(param);
				lineMapper.delRCucmPhoneLine(param);
				lineMapper.updRCucmPhoneLine(param);


				log.info("UpdatePhoneLine UUID : " + successRtnUUID);
				log.info("電話機とLineの紐付処理：END");
			}

			return "success :" + successRtnUUID;
		}catch(Exception e){
			log.info("電話機とLineの紐付処理：ERROR");
			ts = txManager.getTransaction(dtd);
			phonelineMapper.updateErrorPhone(param);
			lineMapper.updateErrorLine(param);
			userMapper.updateErrorUser(param);
			userMapper.updateErrorUserPhone(param);
			lineMapper.updateErrorPhoneLine(param);
			txManager.commit(ts);
			throw e;
		}
	}
}
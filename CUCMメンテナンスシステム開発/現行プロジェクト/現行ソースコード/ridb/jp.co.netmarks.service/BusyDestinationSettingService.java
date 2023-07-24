/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingService.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.exception.ExcludeException;
import jp.co.ksc.service.BaseService;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.BusyDestinationSettingModel;
import jp.co.netmarks.model.BusyDestinationSettingResultModel;
import jp.co.netmarks.model.entity.LineEntity;
import jp.co.netmarks.persistence.AppCommonMapper;
import jp.co.netmarks.persistence.BusyDestinationSettingMapper;
import jp.co.netmarks.persistence.LineMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 話中転送先設定用サービスクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/18
 */
@Service
public class BusyDestinationSettingService extends BaseService {

	@Autowired
    private Properties properties;
	
	@Autowired
	private AppCommonMapper appCommonMapper;

	@Autowired
	private BusyDestinationSettingMapper busyDestinationSettingMapper;
	
	@Autowired
	private LineMapper lineMapper;

	/**
	 * ライン情報の取得
	 *
	 * @param busyDestination 話中転送先
	 * @return ライン情報マップ
	 */
	public Map<String, Object> getLineInfo(String busyDestination){
		Map<String, String> map = new HashMap<String, String>();
		map.put("busyDestination", busyDestination);
		map.put("deleted", Constants.COM_FLG_OFF);
		return busyDestinationSettingMapper.getLineInfo(map);
	}

	/**
	 * 検索処理
	 *
	 * @param params 検索条件
	 * @return 検索結果
	 */
	public List<BusyDestinationSettingResultModel> search(BusyDestinationSettingModel params){

		/* 画面表示用リスト */
		List<BusyDestinationSettingResultModel> list = new ArrayList<BusyDestinationSettingResultModel>();

		/* 設定情報を取得 */
		List<BusyDestinationSettingResultModel> resultList = busyDestinationSettingMapper.getBusyDestinationInfo(params);

		int i = 1;
		String dirNum = StringUtils.EMPTY;
		String buusyDest = StringUtils.EMPTY;
		
		/* 設定情報を取得 */
		for(String busy : params.getBusyDestinationArray()){
			
			buusyDest = StringUtils.EMPTY;
			
			if(StringUtils.isEmpty(busy)) break;
			/* 内線番号セット */
			dirNum = params.getFrontFour()  + busy;

			/* DB情報をループ */
			for(BusyDestinationSettingResultModel result : resultList){

				/* DBと画面の値を比較する */
				if(result.getDirectoryNumber().equals(dirNum)){
					BusyDestinationSettingResultModel model = new BusyDestinationSettingResultModel();
					/* パラメータをセット */
					model.setDirectoryNumber(result.getDirectoryNumber());
					model.setBranchTelName(result.getBranchTelName());
					model.setSectionTelName(result.getSectionTelName());
					model.setKanjiUserName(result.getKanjiUserName());
					model.setForwardIndex(Integer.toString(i));
					
					/* 話中転送に転送順が次の内線番号をセットする */
					if(i != params.getBusyDestinationArray().length &&
					   StringUtils.isNotEmpty(params.getBusyDestinationArray()[i])){
						buusyDest = params.getFrontFour()  + params.getBusyDestinationArray()[i];
					}
					model.setBusyDestination(buusyDest);
					
					/* リストにセット */
					list.add(model);
					break;
				}
			}
			i++;
		}

		return list;
	}
	
	/**
	 * 更新処理
	 * 
	 * @param params 更新パラメータ
	 * @return メッセージ
	 * @throws Exception 例外処理
	 */
	@Transactional
	public Map<String, String> updateBusyDestination(BusyDestinationSettingModel params) throws Exception{
		
		/* メッセージ用 */
		Map<String, String> map = new HashMap<String, String>();
		
		/* 初期化 */
		String dirNum = null;	// 内線番号
		String bsyDes = null;	// 話中転送先
		
		/* タイムスタンプを取得 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		try{
			for(int i=0; i < params.getBusyDestinationArray().length; i++){
				/* 初期化 */
				dirNum = null;	// 内線番号
				bsyDes = null;	// 話中転送先
								
				/* テキスト値が空白又は、40番目の値又は、最終行で内線番号をループしていた場合は処理を終了する */
				if((i+1) == Constants.BUSY_DESTINATION_CONDITION_INDEX || 
				   StringUtils.isEmpty(params.getBusyDestinationArray()[i]) ||
				   (StringUtils.isEmpty(params.getBusyDestinationArray()[i+1]) &&
				   Arrays.asList(params.getBusyDestinationArray()).
				   			indexOf(params.getBusyDestinationArray()[i]) != i)) break;
				
				/* 内線番号 */
				dirNum = params.getFrontFour() + params.getBusyDestinationArray()[i];
				
				/* 話中転送先  最終行の場合はチェックセットしない */
				if(StringUtils.isNotEmpty(params.getBusyDestinationArray()[i+1])){
					bsyDes = params.getFrontFour() + params.getBusyDestinationArray()[i+1];
				}

				/* ロック処理 */
				Map<String, Object> sqlMap = new HashMap<String, Object>();
				sqlMap.put("DIRECTORY_NUMBER", dirNum);
				locked("CUCM_LINE", null , sqlMap);
				
				/* 更新処理 */
				lineMapper.updateBusyDestination(setLineEntity(dirNum, bsyDes, timestamp));
			}
			
			/* 更新メッセージをセット */
			map.put("sucsessMessage", properties.getProperty("update.success"));
			
		} catch (ExcludeException e) {
			map.put("errorMessage", properties.getProperty("exclusion.error"));
		}
		
		return map;
	}
	
	/**
	 * Line情報のセット
	 * 
	 * @param directoryNumber 内線番号
	 * @param busyDestination 話中転送先
	 * @param timestamp タイムスタンプ
	 * @return エンティティモデル
	 */
	private LineEntity setLineEntity(String directoryNumber, String busyDestination, Timestamp timestamp){
		LineEntity entity = new LineEntity();
		
		entity.setDirectoryNumber(directoryNumber);		// 内線番号
		entity.setFwdBusyDestination(busyDestination);	// 話中転送先
		entity.setCucmUpdateRequestFlag(Constants.CUCM_UPDATE_FLG_WAIT_2);	// 更新フラグ
		entity.setLstupdtTmstmp(timestamp);				// 最終更新タイムスタンプ
		
		return entity;
	}
}
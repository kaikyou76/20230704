/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * BusyDestinationSettingController.java
 *
 * @date 2013/09/18
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jp.co.ksc.controller.BaseController;
import jp.co.ksc.spring.security.TokenHandler;
import jp.co.ksc.spring.security.TokenValidateType;
import jp.co.netmarks.common.Constants;
import jp.co.netmarks.model.BusyDestinationSettingModel;
import jp.co.netmarks.model.form.BusyDestinationSettingForm;
import jp.co.netmarks.model.form.BusyDestinationSettingUpdateForm;
import jp.co.netmarks.service.AppCommonService;
import jp.co.netmarks.service.BusyDestinationSettingService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 話中転送先設定画面用コントローラークラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/18 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/18
 */
@Controller
@RequestMapping("/busyDestination")
public class BusyDestinationSettingController extends BaseController {

	@Autowired
	private Properties properties;

	@Autowired
	private AppCommonService appCommonService;
	
	@Autowired
	private BusyDestinationSettingService busyDestinationSettingService;

	/**
	 * 初期表示
	 * 
	 * @param form BusyDestinationSettingForm
	 * @param directoryNumber 内線番号
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	protected String index(BusyDestinationSettingForm form,
			@RequestParam("directoryNumber") String directoryNumber) throws Exception {

		/* 話中転送先階層一覧取得 */
		String[] busyArray = appCommonService.getBusyDestinationClass(
				directoryNumber,Constants.BUSY_DESTINATION_CONDITION_INDEX);

		/* 話中転送先階層 */
		form.setBusyDestinationArray(conditionArrray(busyArray));
		
		return "dialog/busyDestinationSetting";
	}
	
	/**
	 * 確認処理
	 *
	 * @param form BusyDestinationSettingForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(save=true)
	@RequestMapping(value="/confirm", method=RequestMethod.POST)
	public ModelAndView confirm(
			BusyDestinationSettingForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* Form→Modelコピー処理 */
		BusyDestinationSettingModel params = new BusyDestinationSettingModel();
		BeanUtils.copyProperties(params,form);

		/* 存在チェック */
		if(!inputCheck(params , result)){
			map.put("total", 0);
			map.put("rows", new ArrayList<Object>());
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 検索処理 */
		map.put("rows", busyDestinationSettingService.search(params));

		return getJsonView(map);
	}
	
	/**
	 * 更新処理
	 *
	 * @param form BusyDestinationSettingUpdateForm
	 * @param result BindingResult
	 * @return ModelAndView
	 * @throws Exception 例外処理
	 */
	@TokenHandler(validate=TokenValidateType.REMOVE)
	@RequestMapping(value="/updateBusyDestination", method=RequestMethod.POST)
	public ModelAndView updateBusyDestination(
			BusyDestinationSettingUpdateForm form,
			BindingResult result) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		/* Form→Modelコピー処理 */
		BusyDestinationSettingModel params = new BusyDestinationSettingModel();
		BeanUtils.copyProperties(params,form);

		/* 存在チェック */
		if(!inputCheck(params , result)){
	   	    map.put("errors", result.getAllErrors());
    		return getJsonView(map);
		}

		/* 属性更新処理 */
		Map<String, String> messageMap =
				busyDestinationSettingService.updateBusyDestination(params);

		/* メッセージを取得 */
		String message = StringUtils.isNotEmpty(messageMap.get("errorMessage")) ?
				 messageMap.get("errorMessage") : messageMap.get("sucsessMessage");

		/* メッセージをセット */
		form.setMessage(message);

		return getJsonView(map);
	}

	/**
	 * 入力チェック
	 * 
	 * @param params 入力パラメータ
	 * @param result BindingResult
	 * @return チェック結果 True:エラー無し False:エラー有り
	 */
	private boolean inputCheck(BusyDestinationSettingModel params ,BindingResult result){

		/* チェック */
		boolean emptyFlg = false;
		boolean checkFlg = true;

		/* No.2必須チェック */
		if(StringUtils.isEmpty(params.getBusyDestinationArray()[1])){
			addError(result,"busyDestinationSettingForm", "busyDestinationArray1",
					properties.getProperty("notempty.error"));
			return false;
		}

		/* 画面用の配列を取得 */
		String[] viewBusyArray = new String[params.getBusyDestinationArray().length];
		
		/* 登録元の内線番号の拠点を取得 */
		String branchId = appCommonService.getSharedLineBranch(params.getFirstDirectoryNumber())[0];
		
		/* 登録元の内線番号の上4桁を取得 */
		String frontFour = params.getFirstDirectoryNumber().substring(0, 4);
		
		/* 上4桁をモデルにセットする（検索、登録で使用するため） */
		params.setFrontFour(frontFour);

		/* 項目分ループする */
		for(int i=0; i< params.getBusyDestinationArray().length; i++ ){
			
			/* 値を取得 */
			String busyDestination = params.getBusyDestinationArray()[i];

			/* Emptyチェック */
			if(StringUtils.isEmpty(busyDestination)){
				emptyFlg = true;
				continue;
			} else if(emptyFlg){
				/* 途中に空が存在した場合 */
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("halfway.blank.error"));
				return false;
			} else if(i==0) {
				/* 初回はチェックしない */
				viewBusyArray[i] = busyDestination;
				continue;
			}

			/* 半角英数字チェック */
			if(!busyDestination.matches("^([*#0-9]+)?$")){
				/* 途中に空が存在した場合 */
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("pattern.error"));
				checkFlg = false;
				continue;
			}

			/* ライン情報を取得 */
			String convertBusy = frontFour + busyDestination;
			Map<String, Object> map = busyDestinationSettingService.getLineInfo(convertBusy);

			/* 存在チェック */
			if(map == null){
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("exist.error.entry") , "話中転送先");
				checkFlg = false;
				continue;
			}
			
			/* 別拠点の内線番号が設定されていないかチェックする */
			if(!map.get("branchId").toString().equals(branchId)){
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("busy.destination.same.branch.error") );
				checkFlg = false;
				continue;
			}

			/* VM仕様が有に設定されていた場合 */
			if(map.get("vmFlg").toString().equals(Constants.VOICE_MAIL_FLG_ON)){
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("voice.mail.busy.destination.error"));
				checkFlg = false;
				continue;
			}

			/* 連続で同じ転送先を設定していた場合 */
			if(i > 0 && busyDestination.equals(params.getBusyDestinationArray()[i-1])){
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("busy.destination.loop.error"));
				checkFlg = false;
				continue;

			}

			/* 話中転送先のループチェック */
			if(!(i + 1 == Constants.BUSY_DESTINATION_CONDITION_INDEX) &&
			   !StringUtils.isEmpty(params.getBusyDestinationArray()[i+1]) &&
			   !loopCheck(busyDestination, viewBusyArray)){
				addError(result,"busyDestinationSettingForm", "busyDestinationArray" + Integer.toString(i),
						properties.getProperty("busy.destination.loop.error"));
				checkFlg = false;
			}
			
			/* 配列にセット */
			viewBusyArray[i] = busyDestination;
		}
		return checkFlg;
	}

	/**
	 * 検索条件用配列の作成
	 * ※検索条件に表示するテキスト分をセットします。
	 *
	 * @param condArray 検索条件配列
	 * @return 検索条件配列
	 */
	private String[] conditionArrray(String[] condArray){

		/* 条件用配列 */
		String[] array = new String[Constants.BUSY_DESTINATION_CONDITION_INDEX];

		for(int i=0; i < condArray.length ; i++){
			array[i] = condArray[i];
		}
		return array;
	}
	/**
	 * 話中転送先ループチェック
	 *
	 * @param busyDestination 話中転送先
	 * @param viewBusyArray 画面で選択した話中転送先
	 * @return true:存在しない false:存在する
	 */
	private boolean loopCheck(String busyDestination, String[] viewBusyArray){

		if(viewBusyArray == null || viewBusyArray.length == 0)return true;

		return (Arrays.asList(viewBusyArray).contains(busyDestination)) ? false : true ;
	}

}
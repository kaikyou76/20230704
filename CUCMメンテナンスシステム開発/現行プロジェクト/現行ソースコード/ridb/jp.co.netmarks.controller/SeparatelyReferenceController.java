/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * SeparatelyReferenceController.java
 *
 * @date 2013/09/25
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.controller;

import java.util.Map;

import jp.co.ksc.controller.BaseController;
import jp.co.netmarks.model.form.SeparatelyReferenceForm;
import jp.co.netmarks.service.UserAndTelSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <pre>
 * 個別参照画面用コントローラクラス
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/25 KSC Yuichiro Yoshida 新規作成
 * </pre>
 *
 * @author KSC Yuichiro Yoshida
 * @version 1.0 2013/09/25
 */
@Controller
@RequestMapping("/separatelyReference")
public class SeparatelyReferenceController extends BaseController {
	
	@Autowired
	private UserAndTelSearchService userAndTelSearchService;
	
	/**
	 * 初期表示
	 *
	 * @return String busyDestination
	 * @throws Exception 例外処理
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	protected String index(SeparatelyReferenceForm form,
			@RequestParam("directoryNumber") String directoryNumber,
			@RequestParam("clusterId") String clusterId) throws Exception {
		
		/* 全転送取得 */
		Map<String, String> map = userAndTelSearchService.separatelyReference(directoryNumber,clusterId);
		/* 転送先をセット */
		form.setFwdAllDestination(map.get("fwdAllDestination"));
		/* メッセージをセット */
		form.setMessage(map.get("errorMessage"));
		
		return "dialog/separatelyReference";
	}

}
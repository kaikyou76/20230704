package jp.co.netmarks.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ksc.controller.BaseController;
import jp.co.netmarks.model.form.LoginForm;
import jp.co.netmarks.service.EncryptService;

/**
 * <pre>
 * パスワードの暗号化、複合化コントローラクラス
 * </pre>
 *
 * @author 
 * @version 1.0 2023/06/01
 */
@Controller
@RequestMapping("/encrypt")
public class EncryptController extends BaseController {

	@Autowired
	private EncryptService encryptService;

	/**
	 * 初期表示
	 *
	 * @param form LoginForm
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String index(LoginForm form, HttpServletRequest request) {
		return "encrypt";			
	}


	/**
	 * パスワードの暗号化、復号化
	 *
	 * @param form LoginForm
	 * @param result BindingResult
	 * @return 遷移先
	 * @throws Exception 例外処理
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView login(@Valid LoginForm form, BindingResult result) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		return getJsonView(map);
	}
}
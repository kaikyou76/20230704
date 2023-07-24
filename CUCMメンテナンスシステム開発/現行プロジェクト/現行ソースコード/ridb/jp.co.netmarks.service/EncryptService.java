/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * EncryptService.java
 *
 * @date 2013/08/07
 * @version 1.0
 * @author KSC Yuichiro Yoshida
 */
package jp.co.netmarks.service;

import org.springframework.stereotype.Service;

import jp.co.ksc.service.BaseService;

/**
 * <pre>
 * パスワードの暗号化、複合化サービスクラス
 * </pre>
 *
 * @author 
 * @version 1.0 2023/06/01
 */
@Service
public class EncryptService extends BaseService {
	
	/**
	 * <pre>
	 * 暗号化処理
	 *
	 * ・認証エラー時はNameNotFoundExceptionを返却する
	 * ・認証成功時は、LoginUserModelを返却する
	 * ・ログインユーザーに応じたRole（複数）をセットする
	 * </pre>
	 *
	 * @param loginId ログインID
	 * @return ユーザー情報
	 * @throws Exception 例外処理
	 */
	public String encrypt(String password) {
		return null;
	}
	
	/**
	 * <pre>
	 * 複合化処理
	 *
	 * ・認証エラー時はNameNotFoundExceptionを返却する
	 * ・認証成功時は、LoginUserModelを返却する
	 * ・ログインユーザーに応じたRole（複数）をセットする
	 * </pre>
	 *
	 * @param loginId ログインID
	 * @return ユーザー情報
	 * @throws Exception 例外処理
	 */
	public String decrypt(String password) {
		return null;
	}
}
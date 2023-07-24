/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * ReflectionManager.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.netmarks.model.ReflectionResultModel;
import jp.co.netmarks.persistence.IndividualPhoneMapper;

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
 * 個別反映処理 制御Manager
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class ReflectionManager {

	private static final Log log = LogFactory.getLog(ReflectionManager.class);

	@Autowired
	private IndividualPhoneMapper phoneMapper;

	@Autowired
	private ReflectionPhone phoneReflec;
	@Autowired
	private ReflectionLine lineReflec;
	@Autowired
	private ReflectionPhoneLine phonelineReflec;
	@Autowired
	private ReflectionUser userReflec;
	@Autowired
	private ReflectionUserPhone userphoneReflec;
	@Autowired
	private PlatformTransactionManager txManager;

	/**
	 * 各個別反映処理を呼び出す
	 * 
	 * @param phoneid 電話機ID
	 * @param userid ユーザID
	 * @param lineid ラインID
	 * @throws ReflectionException 個別反映エラー
	 */
	public void reflection(String phoneid,String userid,String lineid) throws ReflectionException {

		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		dtd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		TransactionStatus ts = null;
		int successResult = 0;
		String execResult = "";

		try{
			Map<String, Object> parameterValues = new HashMap<String, Object>();
			ReflectionResultModel result = new ReflectionResultModel();

			if(phoneid != null)parameterValues.put("phoneid", Integer.parseInt(phoneid));
			if(userid != null)parameterValues.put("userid", Integer.parseInt(userid));
			if(lineid != null)parameterValues.put("lineid", Integer.parseInt(lineid));

			log.info("個別反映処理 開始 phoneid : " + phoneid + ",userid : "+ userid + ",lineid : " + lineid);
			// 処理対象クラスタを取得
			List<Map<String, Object>> clusterInfoList  = phoneMapper.selectClusterPhonejudge(parameterValues);
			if(clusterInfoList == null){
				log.error("新旧クラスタ情報の取得に失敗しました。");
				throw new Exception();
			}

			if(phoneid != null){
				// 電話情報
				log.info("①電話機　個別反映 開始");
				ts = txManager.getTransaction(dtd);
				execResult = phoneReflec.individualReflection(parameterValues,clusterInfoList);
				result.setPhoneResult(execResult);
				successResult++;
				txManager.commit(ts);
				log.info("①電話機　個別反映 終了");
			}
			if(lineid != null){
				// Line情報
				log.info("②Line　個別反映 開始");
				ts = txManager.getTransaction(dtd);
				execResult = lineReflec.individualReflection(parameterValues,clusterInfoList);
				result.setLineResult(execResult);
				successResult++;
				txManager.commit(ts);
				log.info("②Line　個別反映 終了");
			}

			if(userid != null){
				// User情報
				log.info("③User　個別反映 開始");
				ts = txManager.getTransaction(dtd);
				execResult = userReflec.individualReflection(parameterValues,clusterInfoList);
				result.setUserResult(execResult);
				successResult++;
				txManager.commit(ts);
				log.info("③User　個別反映 終了");
			}

			if(phoneid != null){
				// 紐付情報(PhoneLine)
				log.info("④電話機とライン紐付　個別反映 開始");
				ts = txManager.getTransaction(dtd);
				execResult = phonelineReflec.individualReflection(parameterValues,clusterInfoList);
				result.setPhoneLineResult(execResult);
				successResult++;
				txManager.commit(ts);
				log.info("④電話機とライン紐付　個別反映 終了");

				// 紐付情報(UserPhone)
				log.info("⑤ユーザと電話機の紐付　個別反映 開始");
				ts = txManager.getTransaction(dtd);
				execResult = userphoneReflec.individualReflection(parameterValues,clusterInfoList);
				result.setUserPhoneResult(execResult);
				successResult++;
				log.info("⑤ユーザと電話機の紐付　個別反映 終了");
				log.info("⑥紐付削除済みユーザ削除　個別反映 開始");
				execResult += userphoneReflec.delUserPhoneReflection(parameterValues,clusterInfoList);
				result.setUserPhoneResult(execResult);
				successResult++;
				txManager.commit(ts);
				log.info("⑥紐付削除済みユーザ削除　個別反映 終了");
			}
			log.info("個別反映処理 終了");
		}catch (Exception e){
			log.error("個別反映処理 ERROR終了 ---------------------------------");
			if(ts!=null && !ts.isCompleted())txManager.rollback(ts);
			throw new ReflectionException(e,successResult);
		}
	}
}
/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 * ErrorMail.java
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.AddressException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.netmarks.batch.persistence.ErrorMailMapper;


/**
 * <pre>
 * エラーメール送信クラス
 *
 * &lt; MODIFICATION HISTORY&gt;
 * 1.0 2013/09/12 KSC Hiroaki Endo 新規作成
 * </pre>
 *
 * @author KSC Hiroaki Endo
 * @version 1.0 2013/09/12
 */
@Component
public class ErrorMail {

    /** ログクラス */
    private final static Log log = LogFactory.getLog(ErrorMail.class);

    @Autowired
    private Properties properties;

    @Autowired
    private ErrorMailMapper emm;

    @Autowired
    private MailSender ms;

    /**
     * エラーメールを送信します。
     *
     * @param appUserId 操作していた人の APP_USER.APP_USER_ID (‡(不明時は–1を指定)
     * @param errorlog  エラーログ
     */
    public void sendErrorMail(int appUserId, String errorlog) {
        try {
            String[] toAddresses = null;
            // 全管理者全員のメールアドレスを取得
            List<Map<String, Object>> mailAll = emm.selectMailAll();
            int sizeOfToAddress = 0;
            // 共用アドレスをプロパティから取得(MailSender.SharedAddress)
            String sharedMailAddress = properties.getProperty("MailSender.SharedAddress");
            if (sharedMailAddress != null && sharedMailAddress.length() > 0) {
                sizeOfToAddress = 1 + mailAll.size();
            } else {
                sizeOfToAddress = mailAll.size();
            }
            // 操作していた人のメールを含める場合
            if (isHimself(appUserId)) {
                Map<String, Object> parameterValues = new HashMap<String, Object>();
                parameterValues.put("appUserId", appUserId);
                // 権限の取得
                Map<String, Object> result = emm.selectHimself(parameterValues);
                String mail = result.get("mail_address") + "";

                // 操作者本人にメールアドレスがあれば、それをtoAddress配列の末尾から1コ前(-2)にセット。
                // toAddress配列の末尾には、共用アドレスが付加される。
                if (mail != null && !mail.equals("")) {
                    toAddresses = new String[sizeOfToAddress + 1];
                    toAddresses[toAddresses.length - 2] = mail;
                } else {
                    toAddresses = new String[sizeOfToAddress];
                }
            } else {
                toAddresses = new String[sizeOfToAddress];
            }

            if (log.isDebugEnabled()) {
                if (mailAll.size() > 0) {
                    log.debug("エラーメール配信用通常アドレス有り:");
                }
            }

            for (int i = 0; i < mailAll.size(); i++) {
                Map<String, Object> mailAllMap = mailAll.get(i);
                String mailAddress = (String) mailAllMap.get("mail_address");
                if (log.isDebugEnabled()) {
                    log.debug("Error Mail ToAddress [" + (i + 1) + "/" + mailAll.size() + "] = [" + mailAddress + "]");
                }
                if ((mailAddress != null) && (mailAddress.length() > 8)) {
                    toAddresses[i] = mailAddress;
                }
            }

            // 共用アドレスがあれば、toAddress配列末尾にセット
            if (sharedMailAddress != null && sharedMailAddress.length() > 0) {
                if (log.isDebugEnabled()) {
                    log.debug("エラーメール配信用共用アドレス有り: ");
                }
                toAddresses[toAddresses.length - 1] = sharedMailAddress;
                if (log.isDebugEnabled()) {
                    for (int i = 0; i < toAddresses.length; i++) {
                        log.debug("Error Mail ToAddress [" + (i + 1) + "/" + toAddresses.length + "] = [" + toAddresses[i] + "]");
                    }
                }
            }

            ms.setErrorLog(errorlog);
            ms.main(toAddresses);
        } catch (AddressException ex) {
            log.error("[メール配信] メールのメッセージングエラーです。 (SMTPホスト/送信元/送信先不正等が考えられます)");
            log.warn("Exception", ex);
        } catch (SQLException ex) {
            log.error("[メール配信] データベースアクセス時にエラーが発生しました。");
            log.warn("Exception", ex);
        } catch (ClassNotFoundException ex) {
            log.error("[メール配信] その他のメール送信エラー。 詳細はログを確認してください。");
            log.warn("Exception", ex);
        }
    }

    /**
     * エラーメールを送信します。
     *
     * @param errorlog 送信するエラーログ
     */
    public void sendErrorMail(String errorlog) {
        sendErrorMail(-1, errorlog);
    }

    /**
     * 画面を操作していた人に送信するかを判断します。
     *
     * @param appUserId
     * @return boolean 画面を操作していた人に送信する場合、true
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    protected boolean isHimself(int appUserId) throws SQLException, ClassNotFoundException {
        if (appUserId <= 0) {
            return false;
        }
        Map<String, Object> parameterValues = new HashMap<String, Object>();
        parameterValues.put("appUserId", appUserId);
        // 権限の取得
        Map<String, Object> result = emm.selectisHimself(parameterValues);
        String role = result.get("role_class") + "";
        return "2".equals(role);
    }
}

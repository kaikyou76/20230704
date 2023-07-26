/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * MailSender.java
 *
 * @date 2013/09/12
 * @version 1.0
 * @author KSC Hiroaki Endo
 */
package jp.co.netmarks.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
/**
*
*<pre>
* メール送信クラス: <br>
* IDSP (1) 複数の宛先に対し、通常のメール(text/plain)を送信する<br>
* ※JavaMail自体の稼働情報は、セッションのデバックを有効にする事により、
* SMTP中のログを採取できます。<br>
* mailsession.setDebug (true); <br>
* (標準出力に出力されている) <br>
*
* &lt; MODIFICATION HISTORY&gt;
*1.0 2013/09/12 KSC Hiroaki Endo 新規作成
* </pre>
* @author KSC Hiroaki Endo
* @version 1.0 2013/09/12
*/
@Component
public class MailSender {
	/**ログクラス */
    private static final Log log = LogFactory.getLog(MailSender.class);

    @Autowired
    private static Properties properties;

    /**メールの件名 */
    private static String MESSAGE_SUBJECT = null;

    /**送信者アドレス */
    private String SENDER_ADDRESS = null;

    /** 送信者名 */
    private String SENDER_NAME = null;

    /** 送信に用いるSMTPサーバのアドレス */
    private String HOST_SMTP = null;

    /** 送信に用いるSMTPサーバのポート */
    private String HOST_SMTP_PORT = null;

    /** 送信に用いるSMTPサーバのSSL ONOFF */
    private String HOST_SMTP_SSL = null;

    /** POP before SMTP */
    private String HOST_POP = null;

    /** POP before SMTP Port */
    private String HOST_POP_PORT = null;

    /** POP before SMTP */
    private String LOGIN_ID_POP = null;

    /** POP before SMTP */
    private String LOGIN_PW_POP = null;

    private boolean enabledMailSender = false;
    private boolean enabledSSLSend = false;

    /** 送信対象者数指定: 1名 (1アドレス) に限定する */
    static final int SEND_MODE_SINGLE_RECIPIENT = 1;

    /** 送信対象者数指定:複数名 (複数アドレス) 対応とする */
    static final int SEND_MODE_MULTI_RECIPIENT = 2;

    private Properties props_ = null;
    private Session session_ = null;
    private MimeMessage mimeMessage_ = null;
    private InternetAddress emailAddress_ = null;
    private InternetAddress[] emailAddresses_;
    private String subject_ = "";
    private String mailBody_ = "";
    private static String errorlog_ = "";

    /**
     * メール送信の実行
     * SMTPを利用して、指定アドレスにE-mailを送信する。
     */
    public static void main(String[] args) throws AddressException {
        MailSender mailSender = new MailSender();
        mailSender.init();

        // Dateオブジェクトを生成
        Date date = new Date();
        // SimpleDateFormatインスタンス生成
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String dateValue = formatter.format(date);

        // 複数の宛先に対し、通常のメール(text/plain)を送信する
        String[] addresses1 = args;
        mailSender.setEmailAddresses(addresses1);
        // 件名設定
        mailSender.setSubject(MESSAGE_SUBJECT);
        // 本文設定
        StringBuffer sb = new StringBuffer();
        sb.append(properties.getProperty("ErrorMailBody1"));
        sb.append(dateValue);
        sb.append("\\n\\n");
        sb.append(properties.getProperty("ErrorMailBody2"));
        sb.append("\\n");
        sb.append(errorlog_);

        String mailBody = sb.toString();
        mailBody = StringUtils.replace(mailBody, "\\n", "\n");

        mailSender.setMailBody(mailBody);
        // 送信実行
        mailSender.sendMail(SEND_MODE_MULTI_RECIPIENT);
        if (log.isDebugEnabled()) {
            log.debug("エラーメールを送信しました。");
        }

    }

    /**
     * 出力するエラーログをセット
     * @param errorlog エラーログ
     */
    public void setErrorLog(String errorlog) {
        errorlog_ = errorlog;
    }

    /**
     * メール送信クライアント
     * SMTPを利用して、指定アドレスにE-mailを送信する。
     */
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("CREATE INSTANCE : MailSender");
        }
        MESSAGE_SUBJECT = properties.getProperty("MailSender.MessageSubject");
        SENDER_ADDRESS = properties.getProperty("MailSender.SenderAddress");
        SENDER_NAME = properties.getProperty("MailSender.SenderName");
        HOST_SMTP = properties.getProperty("MailSender.HostSMTP");
        HOST_SMTP_PORT = properties.getProperty("MailSender.HostSMTP.PORT");
        HOST_SMTP_SSL = properties.getProperty("MailSender.HostSMTP.SSL");
        HOST_POP = properties.getProperty("MailSender.HostPOP");
        HOST_POP_PORT = properties.getProperty("MailSender.HostPOP.PORT");
        LOGIN_ID_POP = properties.getProperty("MailSender.Auth.ID");
        LOGIN_PW_POP = properties.getProperty("MailSender.Auth.Password");

        enabledMailSender = Boolean.valueOf(properties.getProperty("MailSender.enabled"));
        enabledSSLSend = Boolean.valueOf(properties.getProperty("MailSender.sslsend"));
        props_ = System.getProperties();
        props_.put("mail.smtp.host", HOST_SMTP);
        props_.put("mail.smtp.port", HOST_SMTP_PORT);

        props_.put("mail.pop3.host", HOST_POP);
        props_.put("mail.pop3.port", HOST_POP_PORT);

        Session session;
		// SSL
        if (enabledSSLSend) {
            props_.put("mail.smtp.auth", HOST_SMTP_SSL);
            props_.put("mail.smtp.isSSL", HOST_SMTP_SSL);
            props_.put("mail.pop3.auth", HOST_SMTP_SSL);
            props_.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props_.put("mail.smtp.socketFactory.fallback", "false");
            props_.put("mail.smtp.socketFactory.port", HOST_SMTP_PORT);
            props_.put("mail.smtp.ssl.trust", "*");

            // パスワード認証つきのセッションを作成
            session = Session.getDefaultInstance(props_);
        } else {
            session = Session.getDefaultInstance(props_, null);
        }
        session_.setDebug(true);
        mimeMessage_ = new MimeMessage(session);
        try {
            // 送信元メールアドレスと送信者名を指定
            mimeMessage_.setFrom(new InternetAddress(SENDER_ADDRESS, SENDER_NAME, "iso-2022-jp"));
        } catch (UnsupportedEncodingException e) {
            // エラーログ出力
            log.error("[メール配信] メール送信でサポートされていないエンコードです。");
            log.warn("Exception", e);
        } catch (MessagingException e) {
            // エラーログ出力
            log.error("[メール配信] メールのメッセージングエラーです。 (SMTPホスト / 送信元/送信先不正等が考えられます) ");
            log.warn("Exception", e);
        }
    }

    /**
     * メール送信の実行
     * @param sendMode 送信先が1名ならSEND_MODE_SINGLE_RECIPIENTを、複数名ならSEND_MODE_MULTI_RECIPIENTをセット。
     * @return boolean 送信内容にエラーがあった場合、false
     */
    public boolean sendMail(int sendMode) {
        if (!enabledMailSender && log.isDebugEnabled()) {
            log.debug("*** メール配信機能がOFFになっています。");
            log.debug("*** 設定ファイル: cordial.propertiesの属性[MailSender.enabled]をtrueに変更してください。");
            return true;
        }
        if (log.isDebugEnabled()) {
            log.debug("Start: sendMail()");
        }
        if (subject_.length() == 0) {
            if (log.isDebugEnabled()) {
                log.debug("メールの件名がありません。");
            }
            return false;
        }
        if (mailBody_.length() == 0) {
            if (log.isDebugEnabled()) {
                log.debug("メールの本文がありません。");
            }
            return false;
        }
        try {
            // 送信先メールアドレスを指定
            if (sendMode == SEND_MODE_SINGLE_RECIPIENT) {
                if (emailAddress_ == null) {
                    if (log.isDebugEnabled()) {
                        log.debug("メールの送り先が指定されていません。");
                    }
                    return false;
                }
                mimeMessage_.setRecipient(Message.RecipientType.TO, emailAddress_);
            } else if (sendMode == SEND_MODE_MULTI_RECIPIENT) {
                if (emailAddresses_.length == 0) {
                    if (log.isDebugEnabled()) {
                        log.debug("メールの送り先が指定されていません。");
                    }
                    return false;
                }
                mimeMessage_.setRecipients(Message.RecipientType.TO, emailAddresses_);
            }

            // メールのタイトルを指定
            mimeMessage_.setSubject(subject_, "iso-2022-jp");
            // メールの内容を指定
            mimeMessage_.setText(mailBody_, "iso-2022-jp");
            // メールの形式を指定
            mimeMessage_.setHeader("Content-Type", "text/plain; charset=\"ISO-2022-JP\"");
            mimeMessage_.setHeader("Content-Transfer-Encoding", "7bit");
            mimeMessage_.setHeader("X-Mailer", "CCMAP-Email-Client");
            // 送信日付を指定
            mimeMessage_.setSentDate(new Date());
            // 送信します
            if (log.isDebugEnabled()) {
                log.debug("メール送信実行");
            }
            // メッセージをストリームに書き出すメソッド
            try {
                mimeMessage_.writeTo(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Transport.send(mimeMessage_);
        } catch (SendFailedException e) {
            // エラーログ出力
            log.error("[メール配信] その他のメール送信エラー。 詳細はログを確認してください。");
            log.warn("Exception", e);
        } catch (MessagingException e) {
            // エラーログ出力
            log.error("[メール配信] メールのメッセージングエラーです。 (SMTPホスト/送信元/送信先不正等が考えられます)");
            log.warn("Exception", e);
            return false;
        }
        return true;
    }

    /**
     * メール送信の実行
     * @param sendMode 送信先が1名ならSEND_MODE_SINGLE_RECIPIENTを、複数名ならSEND_MODE_MULTI_RECIPIENTをセット。
     * @param filePath 添付ファイルのフルパス
     * @return boolean 送信内容にエラーがあった場合、false
     */
    public boolean sendMailWithAttacheFile(int sendMode, String filePath) {
        if (!enabledMailSender && log.isDebugEnabled()) {
            log.debug("*** メール配信機能がOFFになっています。");
            log.debug("*** 設定ファイル: cordial.propertiesの属性[MailSender.enabled]をtrueに変更してください。");
            return true;
        }
        if (log.isDebugEnabled()) {
            log.debug("Start: sendMail()");
        }
        if (subject_.length() == 0) {
            return false;
        }

        if (mailBody_.length() == 0) {
            return false;
        }
        if (filePath == null || filePath.length() == 0) {
            return false;
        }

        try {
            // 送信先メールアドレスを指定
            if (sendMode == SEND_MODE_SINGLE_RECIPIENT) {
                if (emailAddress_ == null) {
                    return false;
                }
                mimeMessage_.setRecipient(Message.RecipientType.TO, emailAddress_);
            } else if (sendMode == SEND_MODE_MULTI_RECIPIENT) {
                if (emailAddresses_.length == 0) {
                    return false;
                }
                mimeMessage_.setRecipients(Message.RecipientType.TO, emailAddresses_);
            } else {
                return false;
            }

            // メールのタイトルを指定
            mimeMessage_.setSubject(subject_, "iso-2022-jp");

            /** 1つ目のボディパートを作成 */
            MimeBodyPart mbp1 = new MimeBodyPart();
            // メールの内容を指定
            mbp1.setText(mailBody_, "iso-2022-jp");

            /** 2つ目のボディパートを作成 */
            MimeBodyPart mbp2 = new MimeBodyPart();
            // 添付するファイル名を指定
            FileDataSource fds = new FileDataSource(filePath);
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(MimeUtility.encodeWord(fds.getName()));

            // 複数のボディを格納するマルチパートオブジェクトを生成
            Multipart mp = new MimeMultipart();
            // 1つ目のボディパートを追加
            mp.addBodyPart(mbp1);
            // 2つ目のボディパートを追加
            mp.addBodyPart(mbp2);
            // マルチパートオブジェクトをメッセージに設定
            mimeMessage_.setContent(mp);

            // 省略

        } catch (MessagingException e) {
            // エラーログ出力
            log.error("[メール配信] メールのメッセージングエラーです。 (SMTPホスト/送信元/送信先不正等が考えられます)");
            log.warn("Exception", e);
            return false;
        } catch (UnsupportedEncodingException e) {
            // エラーログ出力
            log.error("[メール配信] メール送信でサポートされていないエンコードです。");
            log.warn("Exception", e);
        }
        return true;
    }

    /**
     * <pre>
     * POPチェック POP Before SMTP用
     * </pre>
     */
    @SuppressWarnings("unused")
    private void checkPOP() {
        try {
            String host = HOST_POP; // ホストアドレス
            String user = LOGIN_ID_POP; // アカウント
            String password = LOGIN_PW_POP; // パスワード

            // 接続します
            Session session = Session.getDefaultInstance(System.getProperties(), null);
            Store store = session.getStore("pop3");
            store.connect(host, -1, user, password);

            // フォルダーを開きます
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // フォルダーにあるメッセージの数を取得します
            int totalMessages = folder.getMessageCount();
            if (totalMessages == 0) {
                // System.out.println("メール0件です");
                folder.close(false);
                store.close();
                return;
            }
            // フォルダーを閉じます
            folder.close(false);
            store.close();
        } catch (Exception e) {
            // エラーログ出力
            log.error("[メール配信] POP認証に失敗しました (POP before SMTP)");
            log.warn("Exception", e);
        }
    }

    /**
     * <pre>
     * emailAddressをセット
     * </pre>
     * @param emailAddress emailAddressを設定
     */
    public void setEmailAddress(InternetAddress emailAddress) {
        this.emailAddress_ = emailAddress;
    }

    /**
     * <pre>
     * mailBodyをセット
     * </pre>
     * @param mailBody mailBodyを設定
     */
    public void setMailBody(String mailBody) {
        this.mailBody_ = mailBody;
    }

    /**
     * <pre>
     * subjectをセット
     * </pre>
     * @param subject subjectを設定
     */
    public void setSubject(String subject) {
        subject_ = subject;
    }

    /**
     * <pre>
     * addressesをセット
     * </pre>
     * @param addresses addressesを設定
     * @throws AddressException
     */
    public void setEmailAddresses(String[] addresses) {
        int addressSize = addresses.length;
        emailAddresses_ = new InternetAddress[addressSize];
        for (int i = 0; i < addressSize; i++) {
            try {
                emailAddresses_[i] = new InternetAddress(addresses[i]);
            } catch (AddressException e) {
                // エラーログ出力
                log.error("[メール配信] 不正なメールアドレスが含まれています。");
                log.warn("Exception", e);
            }
        }
    }


public void sendMail() {
    // SMTPサーバーへの認証情報を設定
    String username = "your_email@example.com";
    String password = "your_email_password";
    PasswordAuthenticator authenticator = new PasswordAuthenticator(username, password);

    // セッションを作成
    Session session = Session.getInstance(properties, authenticator);
    // メール送信などの処理
}

/**
 * パスワードを保持するクラス
 * @author  KSC Hiroaki Endo
 */
//private class PasswordAuthenticatior extends Authenticator{
//      private String username;
//      private String password;
//
//    /**
//     * パスワードを保持します
//     * @param username
//     * @param password
//     */
//    PasswordAuthenticatior(String username, String password) {
//        this.username = username;
//        this.password = password;
//      }
//
//    /* (非 Javadoc)
//     * @see javax.mail.Authenticator#getPasswordAuthentication()
//     */
//    @Override
//      public PasswordAuthentication getPasswordAuthentication() {
//        return new PasswordAuthentication(username, password);
//    }
}



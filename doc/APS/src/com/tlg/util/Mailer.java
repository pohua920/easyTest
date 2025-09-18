/*
 * Created on 2005/4/28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.tlg.util;

//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400 START
//因應 sql server環境升級:jdk1.7->1.8/tomcat7->8:修改1.factory 2.mssql url add ;encrypt=false
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.*;

/**
 * @author Ryan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Mailer {

  private String smtpServer;

  private String userName;

  private String password;

  public static void main(String[] args) {
  }

  /**
   * 線上透過使用者指定的 mail server 寄出電子郵件, 也可以透過需要認證的 mail server 來寄信，此種情況，使用者必須要將 auth
   * 設為 smtp 或 pop3(視伺服器所要求的認証方式而定) 並設定「使用者帳號」、「密碼」。
   * 
   * @param smtpServer
   *          郵件伺服器主機
   * @param contentTyep
   *          郵件格式，使用 text/plain 表示寄出的信件是純文字格式。若使用 text/html 表示為 html 格式
   *          郵件編碼也是在此設定，如使用 utf-8 編碼，要指定此參數為 text/plain; charset=utf-8 html
   *          格式則為 text/html; charset=utf-8。 建議編碼使用
   *          request.getCharacterEncoding() 取得。
   * @param subject
   *          郵件主旨
   * @param from
   *          寄件者 e-mail 信箱
   * @param personalFrom
   *          收信軟體上顯示的 寄件者名稱，若不使用，請填入 "" 或是 null
   * @param to
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param personalTo
   *          收信軟體上顯示的 收件者名稱，若有多個請使用 , 或是 ; 分隔，若不使用，請填入 "" 或是 null
   * @param CC
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param personalCC
   *          收信軟體上顯示的 收件者名稱，若有多個請使用 , 或是 ; 分隔，若不使用，請填入 "" 或是 null
   * @param BCC
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param personalBCC
   *          收信軟體上顯示的 收件者名稱，若有多個請使用 , 或是 ; 分隔，若不使用，請填入 "" 或是 null
   * @param mailBody
   *          信件內容
   * @param auth
   *          所使用郵件伺服器主機(smtp server)寄信是否要認証才可以寄信, 此欄數值為 none 或 smtp 或 pop3 none
   *          表示不需要認証， smtp 表示使用 smtp 伺服器驗証， pop3 表示使用 pop3 伺服器驗証。
   * @param userName
   *          若要認証，請在此填上使用者帳號，否則請填入 "" 或是 null
   * @param password
   *          若要認証，請在此填上使用者密碼，否則請填入 "" 或是 null
   * 
   * @exception AddressException
   *              e-mail 地址不符合規格
   * @exception MessagingException
   *              郵件訊息有問題
   * @exception java.io.UnsupportedEncodingException
   *              不支援所使用的編碼
   */
  public void sendmail(
      String smtpServer, String contentType, String subject, String from,
      String personalFrom, String to, String personalTo, String CC,
      String personalCC, String BCC, String personalBCC, String mailBody,
      String auth, String userName, String password) throws AddressException, MessagingException, java.io.UnsupportedEncodingException {
    // 設定要使用的 mail server 是哪一台
    Properties props = System.getProperties();
    props.put("mail.smtp.host", smtpServer);
    if(auth.equals("pop3")){
      props.put("mail.smtp.auth", "false");
    }else
      if(auth.equals("smtp")){
        props.put("mail.smtp.auth", "true");
      }else{
        props.put("mail.smtp.auth", "false");
        userName = null;
        password = null;
      }

    // 產生 mail session
    Session mailSession = Session.getDefaultInstance(props, null);
    mailSession.setDebug(true);
    int index = contentType.indexOf("charset=");
    String encoding = (index != -1) ? contentType.substring(
        index + 8, contentType.length()) : "ISO8859_1";
    // 寄件者 From
    InternetAddress fromAddr = new InternetAddress(from, personalFrom, encoding);

    // 取得 email_address
    InternetAddress[] toAddrs = splitAddresses(to, personalTo, encoding); // 若 to
    // = ""
    // 會傳回
    // null
    InternetAddress[] CCAddrs = splitAddresses(CC, personalCC, encoding); // 若 CC
    // = ""
    // 會傳回
    // null
    InternetAddress[] BCCAddrs = splitAddresses(BCC, personalBCC, encoding); // 若
    // BCC
    // = ""
    // 會傳回
    // null

    // 產生郵件內容
    MimeMessage message = new MimeMessage(mailSession);
    message.setFrom(fromAddr);

    message.setRecipients(Message.RecipientType.TO, toAddrs);
    message.setRecipients(Message.RecipientType.CC, CCAddrs);
    message.setRecipients(Message.RecipientType.BCC, BCCAddrs);

    message.setSubject(subject, encoding);
    message.setContent(mailBody, contentType);

    Transport trans = mailSession.getTransport("smtp");
    message.saveChanges();
    if(auth.equals("pop3")){
      Store store = mailSession.getStore("pop3");
      store.connect(smtpServer, userName, password);
      trans.connect(smtpServer, userName, password);
      if(toAddrs != null){
        trans.sendMessage(message, toAddrs);
      }
      if(CCAddrs != null){
        trans.sendMessage(message, CCAddrs);
      }
      if(BCCAddrs != null){
        trans.sendMessage(message, BCCAddrs);
      }
      trans.close();
      store.close();
    }else{
      trans.connect(smtpServer, userName, password);
      if(toAddrs != null){
        trans.sendMessage(message, toAddrs);
      }
      if(CCAddrs != null){
        trans.sendMessage(message, CCAddrs);
      }
      if(BCCAddrs != null){
        trans.sendMessage(message, BCCAddrs);
      }
      trans.close();
    }
  }

  /**
   * 線上透過使用者指定的 mail server 寄出電子郵件, 也可以透過需要認證的 mail server 來寄信，此種情況，使用者必須要將 auth
   * 設為 smtp 或 pop3(視伺服器所要求的認証方式而定) 並設定「使用者帳號」、「密碼」。
   * 
   * @param smtpServer
   *          郵件伺服器主機
   * @param contentTyep
   *          郵件格式，使用 text/plain 表示寄出的信件是純文字格式。若使用 text/html 表示為 html 格式
   *          郵件編碼也是在此設定，如使用 utf-8 編碼，要指定此參數為 text/plain; charset=utf-8 html
   *          格式則為 text/html; charset=utf-8。 建議編碼使用
   *          request.getCharacterEncoding() 取得。
   * @param subject
   *          郵件主旨
   * @param from
   *          寄件者 e-mail 信箱
   * @param personalFrom
   *          收信軟體上顯示的 寄件者名稱，若不使用，請填入 "" 或是 null
   * @param to
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param personalTo
   *          收信軟體上顯示的 收件者名稱，若有多個請使用 , 或是 ; 分隔，若不使用，請填入 "" 或是 null
   * @param CC
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param personalCC
   *          收信軟體上顯示的 收件者名稱，若有多個請使用 , 或是 ; 分隔，若不使用，請填入 "" 或是 null
   * @param BCC
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param personalBCC
   *          收信軟體上顯示的 收件者名稱，若有多個請使用 , 或是 ; 分隔，若不使用，請填入 "" 或是 null
   * @param mailBody
   *          信件內容
   * @param auth
   *          所使用郵件伺服器主機(smtp server)寄信是否要認証才可以寄信, 此欄數值為 none 或 smtp 或 pop3 none
   *          表示不需要認証， smtp 表示使用 smtp 伺服器驗証， pop3 表示使用 pop3 伺服器驗証。
   * @param userName
   *          若要認証，請在此填上使用者帳號，否則請填入 "" 或是 null
   * @param password
   *          若要認証，請在此填上使用者密碼，否則請填入 "" 或是 null
   * @param filePath
   * 			欲帶附件的實體位置
   * @param fileName
   * 			欲帶附件的檔案名稱
   * @exception AddressException
   *              e-mail 地址不符合規格
   * @exception MessagingException
   *              郵件訊息有問題
   * @exception java.io.UnsupportedEncodingException
   *              不支援所使用的編碼
   */
  public void sendmail(
      String smtpServer, String contentType, String subject, String from,
      String personalFrom, String to, String personalTo, String CC,
      String personalCC, String BCC, String personalBCC, String mailBody,
      String auth, String userName, String password,String[] filePath,String[] fileName) throws AddressException, MessagingException, java.io.UnsupportedEncodingException {
    // 設定要使用的 mail server 是哪一台
    Properties props = System.getProperties();
    props.put("mail.smtp.host", smtpServer);
    if(auth.equals("pop3")){
      props.put("mail.smtp.auth", "false");
    }else
      if(auth.equals("smtp")){
        props.put("mail.smtp.auth", "true");
      }else{
        props.put("mail.smtp.auth", "false");
        userName = null;
        password = null;
      }

    // 產生 mail session
    Session mailSession = Session.getDefaultInstance(props, null);
    mailSession.setDebug(true);
    int index = contentType.indexOf("charset=");
    String encoding = (index != -1) ? contentType.substring(
        index + 8, contentType.length()) : "ISO8859_1";
    // 寄件者 From
    InternetAddress fromAddr = new InternetAddress(from, personalFrom, encoding);

    // 取得 email_address
    InternetAddress[] toAddrs = splitAddresses(to, personalTo, encoding); // 若 to
    // = ""
    // 會傳回
    // null
    InternetAddress[] CCAddrs = splitAddresses(CC, personalCC, encoding); // 若 CC
    // = ""
    // 會傳回
    // null
    InternetAddress[] BCCAddrs = splitAddresses(BCC, personalBCC, encoding); // 若
    // BCC
    // = ""
    // 會傳回
    // null

    // 產生郵件內容
    MimeMessage message = new MimeMessage(mailSession);
    message.setFrom(fromAddr);

    message.setRecipients(Message.RecipientType.TO, toAddrs);
    message.setRecipients(Message.RecipientType.CC, CCAddrs);
    message.setRecipients(Message.RecipientType.BCC, BCCAddrs);

    message.setSubject(subject, encoding);

    Transport trans = mailSession.getTransport("smtp");
    
    Multipart multipart = new MimeMultipart();
    BodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(mailBody, contentType);
    multipart.addBodyPart(messageBodyPart);

    Encoder enc = Base64.getEncoder();
  //mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400 
	//因應 sql server環境升級:jdk1.7->1.8/tomcat7->8:修改1.factory 2.mssql url add ;encrypt=false
    // sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
    if(filePath != null) {
    	for(int i = 0;i<filePath.length;i++) {
    	    messageBodyPart = new MimeBodyPart();
    	    DataSource source = new FileDataSource(filePath[i]);
    	    messageBodyPart.setDataHandler(new DataHandler(source));
    	  //mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400 
//    	    fileName[i] = "=?utf-8?B?" + enc.encode(fileName[i].getBytes("utf8")) + "?=";
//    	    messageBodyPart.setFileName(fileName[i]);
    	    messageBodyPart.setFileName(MimeUtility.encodeText(fileName[i], "UTF-8", "B"));
    	    multipart.addBodyPart(messageBodyPart);
    	}
    }
 
    // Put parts in message
    message.setContent(multipart);
    
    message.saveChanges();
    if(auth.equals("pop3")){
      Store store = mailSession.getStore("pop3");
      store.connect(smtpServer, userName, password);
      trans.connect(smtpServer, userName, password);
      if(toAddrs != null){
        trans.sendMessage(message, toAddrs);
      }
      if(CCAddrs != null){
        trans.sendMessage(message, CCAddrs);
      }
      if(BCCAddrs != null){
        trans.sendMessage(message, BCCAddrs);
      }
      trans.close();
      store.close();
    }else{
      trans.connect(smtpServer, userName, password);
      if(toAddrs != null){
        trans.sendMessage(message, toAddrs);
      }
      if(CCAddrs != null){
        trans.sendMessage(message, CCAddrs);
      }
      if(BCCAddrs != null){
        trans.sendMessage(message, BCCAddrs);
      }
      trans.close();
    }
  }
  
  /**
   * 線上透過使用者指定的 mail server 寄出電子郵件, 也可以透過需要認證的 mail server 來寄信，此種情況，使用者必須要將 auth
   * 設為 smtp 或 pop3(視伺服器所要求的認証方式而定) 並設定「使用者帳號」、「密碼」。
   * 
   * @param smtpServer
   *          郵件伺服器主機
   * @param subject
   *          郵件主旨
   * @param from
   *          寄件者 e-mail 信箱
   * @param to
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param CC
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param BCC
   *          收件者 e-mail 信箱，若有多個請使用 , 或是 ; 分隔
   * @param mailBody
   *          信件內容
   * @param auth
   *          所使用郵件伺服器主機(smtp server)寄信是否要認証才可以寄信, 此欄數值為 none 或 smtp 或 pop3 none
   *          表示不需要認証， smtp 表示使用 smtp 伺服器驗証， pop3 表示使用 pop3 伺服器驗証。
   * @param userName
   *          若要認証，請在此填上使用者帳號，否則請填入 "" 或是 null
   * @param password
   *          若要認証，請在此填上使用者密碼，否則請填入 "" 或是 null
   * 
   * @exception AddressException
   *              e-mail 地址不符合規格
   * @exception MessagingException
   *              郵件訊息有問題
   * @exception java.io.UnsupportedEncodingException
   *              不支援所使用的編碼
   */
  public void ezSendHtmlMail(
      String subject, String from, String to, String CC, String BCC,
      String mailBody, String auth) throws AddressException, MessagingException, java.io.UnsupportedEncodingException {
    // 設定要使用的 mail server 是哪一台
    Properties props = System.getProperties();
    props.put("mail.smtp.host", this.smtpServer);
    if(auth.equals("pop3")){
      props.put("mail.smtp.auth", "false");
    }else
      if(auth.equals("smtp")){
        props.put("mail.smtp.auth", "true");
      }else{
        props.put("mail.smtp.auth", "false");
        userName = null;
        password = null;
      }

    // 產生 mail session
    Session mailSession = Session.getDefaultInstance(props, null);
    mailSession.setDebug(true);

    String encoding = "utf-8";
    // 寄件者 From
    InternetAddress fromAddr = new InternetAddress(from, "Ryan");

    // 取得 email_address
    InternetAddress[] toAddrs = splitAddresses(to, "", encoding); // 若 to = ""
    // 會傳回 null
    InternetAddress[] CCAddrs = splitAddresses(CC, "", encoding); // 若 CC = ""
    // 會傳回 null
    InternetAddress[] BCCAddrs = splitAddresses(BCC, "", encoding); // 若 BCC =
    // "" 會傳回
    // null

    // 產生郵件內容
    MimeMessage message = new MimeMessage(mailSession);
    message.setFrom(fromAddr);

    message.setRecipients(Message.RecipientType.TO, toAddrs);
    message.setRecipients(Message.RecipientType.CC, CCAddrs);
    message.setRecipients(Message.RecipientType.BCC, BCCAddrs);

    message.setSubject(subject, encoding);
    message.setContent(mailBody, "text/html;charset=utf-8");

    Transport trans = mailSession.getTransport("smtp");
    message.saveChanges();
    if(auth.equals("pop3")){
      Store store = mailSession.getStore("pop3");
      store.connect(this.smtpServer, this.userName, this.password);
      trans.connect(this.smtpServer, this.userName, this.password);
      if(toAddrs != null){
        trans.sendMessage(message, toAddrs);
      }
      if(CCAddrs != null){
        trans.sendMessage(message, CCAddrs);
      }
      if(BCCAddrs != null){
        trans.sendMessage(message, BCCAddrs);
      }
      trans.close();
      store.close();
    }else{
      trans.connect(this.smtpServer, this.userName, this.password);
      if(toAddrs != null){
        trans.sendMessage(message, toAddrs);
      }
      if(CCAddrs != null){
        trans.sendMessage(message, CCAddrs);
      }
      if(BCCAddrs != null){
        trans.sendMessage(message, BCCAddrs);
      }
      trans.close();
    }
  }

  /**
   * 這是 senamail() 的工具，用來將使用者所輸入的 e-mail address 以 ,或 ; 切開， 並回傳一個
   * InternetAddress[] 來表示使用者輸入的所有 e-mail 地址
   * 
   * @param mails
   *          尚未分割的e-mail地址
   * @param personals
   *          尚未分割的personal名稱
   * @param encoding
   *          personal 名稱的編碼
   * 
   * @exception java.io.UnsupportedEncodingException
   *              不支援所使用的編碼
   * 
   * @return 代表所有使用者輸入e-mail address 的 InternetAddress 陣列
   */
  public InternetAddress[] splitAddresses(
      String mails, String personals, String encoding) throws java.io.UnsupportedEncodingException {
    if(mails == null || mails.equals("")){
      return null;
    }
    // 產生 收件者地址。有多個收件者時，使用 , ; 分開，在此將傳入的收件者切割成陣列
    String[] mailAddrList = mails.split(" {0,}[,;] {0,}");
    String[] personalList_temp = (personals != null) ? personals.split(" {0,}[,;] {0,}") : new String[mailAddrList.length];
    String[] personalList = new String[mailAddrList.length];
    // 將 toPersonalList 和 toAddrList 設成等長，防止 array index out of bound
    // 因 toPersonalList 可以為 null , 所以下面 if { } 超出 toPersonalList_temp
    // 長度部分的不設成 ""
    for(int i = 0; i < personalList.length; i++){
      if(i < personalList_temp.length){
        personalList[i] = personalList_temp[i];
      }
    }

    InternetAddress[] mailAddrs = new InternetAddress[mailAddrList.length];

    for(int i = 0; i < mailAddrList.length; i++){
      mailAddrs[i] = new InternetAddress(
          mailAddrList[i].toLowerCase(), personalList[i], encoding);
    }
    return mailAddrs;
  }

  /**
   * @return Returns the password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          The password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return Returns the smtpServer.
   */
  public String getSmtpServer() {
    return smtpServer;
  }

  /**
   * @param smtpServer
   *          The smtpServer to set.
   */
  public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
  }

  /**
   * @return Returns the userName.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName
   *          The userName to set.
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }
}

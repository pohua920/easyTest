package com.sinosoft.app.mail.util;

import java.util.Properties;

public class MailConstants {

	public static String smtpURL;// 邮件服务器地址
	public static String smtpUserName;// 邮件服务器用户
	public static String smtpPassowrd;// 邮件服务器密码
	public static String mailTimeOut;// 超时值
	public static String mailAuth;// 邮件服务器是否认证

	public static String mailUserName;// 邮箱地址
	public static String mailNickName;// 邮箱昵称

	public static String attachIP;// 附件地址IP
	public static String rootPath;// 附件根目录
	public static String exportPath;// 文件导出地址
	public static String compressPath;// 文件压缩地址
	public static String compressName;// 文件压缩地址及名称

	public static String localhostURL;// 系统地址
	public static String adminEmail;
	public static String velocityFilePath;//模板路径

	/**
	 * 初始化邮件配置
	 * @param properties
	 */
	public static void initMailConfig(Properties properties) {
		smtpURL = properties.getProperty("mail.smtpurl", "");
		smtpUserName = properties.getProperty("mail.smtpusername", "");
		smtpPassowrd = properties.getProperty("mail.smtppassword", "");
		mailTimeOut = properties.getProperty("mail.smtp.timeout", "");

		mailUserName = properties.getProperty("mail.username", "");
		mailNickName = properties.getProperty("mail.nickname", "");

		mailAuth = properties.getProperty("mail.smtp.auth", "");
		attachIP = properties.getProperty("mail.attachip", "");
		rootPath = properties.getProperty("mail.rootpath", "");
		// 导出及压缩
		exportPath = properties.getProperty("mail.exportPath", "");
		compressPath = properties.getProperty("mail.compressPath", "");
		compressName = properties.getProperty("mail.compressName", "");
		velocityFilePath = properties.getProperty("mail.velocityFilePath", "");
		
		String email1 = properties.getProperty("mail.admin1", "");
		String email2 = properties.getProperty("mail.admin2", "");
		String email3 = properties.getProperty("mail.admin3", "");
		String email4 = properties.getProperty("mail.admin4", "");
		StringBuffer aEmailBuffer = new StringBuffer();
		if (email1 != null && !"".equals(email1)) {
			aEmailBuffer.append(email1).append(",");
		}
		if (email2 != null && !"".equals(email2)) {
			aEmailBuffer.append(email2).append(",");
		}
		if (email3 != null && !"".equals(email3)) {
			aEmailBuffer.append(email3).append(",");
		}
		if (email4 != null && !"".equals(email4)) {
			aEmailBuffer.append(email4).append(",");
		}
		adminEmail = aEmailBuffer.toString().substring(0, aEmailBuffer.toString().lastIndexOf(","));
	}

}

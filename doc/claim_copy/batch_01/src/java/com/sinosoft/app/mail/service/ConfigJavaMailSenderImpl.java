package com.sinosoft.app.mail.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class ConfigJavaMailSenderImpl extends JavaMailSenderImpl{
	public ConfigJavaMailSenderImpl(){
//		super.setHost(MailConstants.smtpURL);
//		super.setPort(25);
//		super.setPassword(MailConstants.smtpPassowrd);
//		super.setUsername(MailConstants.smtpUserName);
		super.setHost("10.10.110.1");
		super.setPort(25);
		super.setPassword("eygtx123!");
		super.setUsername("eygtx@sinosig.com");
	}
}

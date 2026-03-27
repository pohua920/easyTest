package com.sinosoft.app.mail.service;

import ins.framework.common.DateTime;
import ins.framework.common.ServiceFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.app.common.util.EmailUtil;
import com.sinosoft.app.mail.model.Attachment;
import com.sinosoft.app.mail.util.CheckedMailException;
import com.sinosoft.app.mail.util.MailConstants;

public class EmailService {
	private Email email;
	public void mailSend4Intention(String start,String end) throws Exception {
		EmailUtil emailUtil = new EmailUtil();
		DateTime dateTime = new DateTime(new DateTime(),new DateTime().YEAR_TO_DAY);
		String[] text = emailUtil.sendMesFriday(start, end);
		String title = text[0];
		Email email = (Email) ServiceFactory.getService("email");
//		String to[] = { "akill83@sina.com","yang_jun@sinosoft.com.cn"};
		//modify by linsiming 修改管理员邮箱地址为可配置
		String to[] = MailConstants.adminEmail.split(",");
		String str[] = start.split("-");
		String ends[] = end.split("-");
		Map<String, Comparable> model = new HashMap<String, Comparable>();
		model.put("adc", "adc");
		model.put("dateTime", dateTime);
		model.put("strYear", str[0]);
		model.put("strMonth", str[1]);
		model.put("strDay", str[2]);
		model.put("endYear", ends[0]);
		model.put("endMonth", ends[1]);
		model.put("endDay", ends[2]);
//		File file = new File("/weblogic/webapps/ZipAutoExcel/excel.zip");
		File file = new File(MailConstants.compressName);//modify by linsiming 修改压缩地址以及文件
		Attachment inLine = new Attachment();
		List<Attachment> inList = new ArrayList<Attachment>();
		inLine.setAttachmentFileName(file.getName());
		inLine.setAttachmentFile(file);
		inList.add(inLine);
		//email.sendMail(to, inList, title, text[1]);
		email.sendMail(to, inList, null, model, title);
		System.out.println("---发了---");
	}
	/**
	 * 在臨分件或者現金攤賠 的情況下 觸發事件給再保人員通過 Email 發送通知
	 * @param model
	 * @param title
	 * @throws CheckedMailException 
	 */
	public void mailSendToReins(Map<?, ?> model,String title) throws CheckedMailException{
		String to[] = MailConstants.adminEmail.split(",");
		this.getEmail().sendMail(to, null, null, model, title);
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}
	
}

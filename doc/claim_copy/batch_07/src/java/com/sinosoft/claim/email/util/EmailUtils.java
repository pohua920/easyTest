package com.sinosoft.claim.email.util;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.utils.DataUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sinosoft.claim.email.vo.Attachment;
import com.sinosoft.claim.email.vo.InlineFile;
import com.sinosoft.claim.schema.model.PrpLemailConfig;
import com.sinosoft.claim.schema.model.PrpLemailLog;
import com.sinosoft.claim.schema.service.facade.PrpLemailConfigService;
import com.sinosoft.claim.schema.service.facade.PrpLemailLogService;

public class EmailUtils implements MimeMessagePreparator {
	// 发件人
	private String from;
	private String senderName;
	// 收件人
	private String[] to;
	// 抄送人
	private String[] cc;
	// 密件抄送人
	private String[] bcc;
	// 邮件主题
	private String subject = "你好!";
	// 邮件正文（仅在简单文本邮件发送时使用，稍微复杂格式的邮件内容，建议使用velocity模板配置）
	private String text;
	// 附件信息Map，包含附件文件名称和附件文件组成的key-value对
	private List<?> attachment;
	// 内嵌文件信息Map，包含由内嵌文件名称和内嵌文件组成的key-value对
	private List<?> inlineFile;
	// velocity模板文件路径，为velocity的class的相对路径
	private String velocityFilePath;
	// velocity模板中定义变量的集合，第一个参数为velocity模板中的变量名，第二个参数为其值
	// 内嵌到模板的内容
	private Map<?, ?> model;
	// VelocityEngine
	private VelocityEngine velocityEngine;
	// JavaMailSender
	private JavaMailSenderImpl sender;
	/**
	 * 邮件配置类Service
	 */
	private PrpLemailConfigService prpLemailConfigService;
	/**
	 * 邮件日志类Service
	 */
	private PrpLemailLogService prpLemailLogService;
	
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	// 更新邮件标志
	private boolean flag = false;

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean getFlag() {
		return flag;
	}

	public PrpLemailConfigService getPrpLemailConfigService() {
		return prpLemailConfigService;
	}

	public void setPrpLemailConfigService(
			PrpLemailConfigService prpLemailConfigService) {
		this.prpLemailConfigService = prpLemailConfigService;
	}

	public void setVelocityFilePath(String path) {
		this.velocityFilePath = path;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 简单格式邮件发送功能
	 * @param to 收件人地址，可以包含多个收件人
	 * @param text 邮件文本内容
	 * @throws Exception
	 */
//	public void sendMail(String[] to, String text) throws Exception {
//		try {
//			this.init();
//			this.sendMail(this.from, to, null, null, null, null, this.subject, text, null, null);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}

	/**
	 * 简单格式邮件发送
	 * @param to 收件人地址，可以包含多个收件人
	 * @param bcc 密送人，可以包含多个收件人
	 * @param title 标题
	 * @param text 内容
	 * @throws Exception
	 */
//	public void sendMailAndBcc(String[] to, String[] bcc, String title, String text) throws Exception {
//		try {
//			this.init();
//			this.sendMail(this.from, to, null, bcc, null, null, title, text, null, null);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}
//
//	public void sendMail(String to, List<?> inlineFile, String text) throws Exception {
//		try {
//			this.init();
//			String toS[] = { to };
//			this.sendMail(this.from, toS, null, null, inlineFile, null, this.subject, text, null, null);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}
//
//	public void sendMail(String[] to, List<?> inlineFile, String text) throws Exception {
//		try {
//			this.init();
//			String toS[] = to;
//			this.sendMail(this.from, toS, null, null, inlineFile, null, this.subject, text, null, null);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}

	/**
	 * 使用velocity模板发送邮件
	 * @param to 收件人地址，可以包含多个收件人
	 * @param model 邮件正文velocity模板中使用的变量Map，key为velocity模板中引用的变量，value为其对应的值
	 * @throws Exception
	 */
//	public void sendMail(String[] to, Map<?, ?> model) throws Exception {
//		try {
//			this.init();
//			this.sendMail(this.from, to, null, null, null, null, this.subject, null, this.velocityFilePath, model);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}

	/**使用velocity模板发送邮件
	 * @param emailInfoMap 邮件内容集，其中必须包含businessNo，用于prplEmailLog的businessNo的赋值。
	 * @param emaildetailId 邮件模板号 eg：10000：火险（有赔款）在核赔通过后给承保发送邮件的模板号；10001：车险在涉及临分案的保单立案时给承保发送邮件的模板号
	 * @param receiver 收件模块代号：01再保，02承保
	 * @throws Exception
	 */
	public PrpLemailLog sendMail(Map<String, Object> emailInfoMap,String emaildetailId,String receiver) throws Exception {
		PrpLemailLog prpLemailLog = new PrpLemailLog();
		try {
			this.init(emaildetailId);
			this.sendMail(this.from, to, null, null, null, null, this.subject, null, this.velocityFilePath, emailInfoMap,emaildetailId);
		} catch (CheckedMailException cme) {
			prpLemailLog.setFlag("0");
			prpLemailLog.setRemark("郵件：發送失敗，"+cme.getErrMsg());
		} catch (Exception me){
			prpLemailLog.setFlag("0");
			prpLemailLog.setRemark("郵件：發送失敗，請聯繫管理員！");
		}
		String key = cacheManager.generateCacheKey("prpLemailConfigSelect", emaildetailId);
		Object result = cacheManager.getCache(key);
		prpLemailLog.setBusinessNo(emailInfoMap.get("businessNo").toString());
		prpLemailLog.setSendType(emaildetailId);
		prpLemailLog.setReceiver(receiver);
		prpLemailLog.setSender(result==null ? "":((PrpLemailConfig)result).getUsername());
		prpLemailLog.setSendTime(new Date());
		prpLemailLog.setTitle(result==null ? "":((PrpLemailConfig)result).getTitle());
		prpLemailLog.setAddressee(result==null ? "":((PrpLemailConfig)result).getAddress());
		prpLemailLog.setValidstatus("1");//1代表數據有效
		this.prpLemailLogService.save(prpLemailLog);
		return prpLemailLog;
	}

	/**
	 * 不使用velocity模板发送邮件，但是包含附件文件功能
	 * @param to 收件人地址，可以包含多个收件人
	 * @param attachment 附件对象，参见Attachment类，包含附件名称及其对应的附件文件（java.io.File）
	 * @throws Exception
	 */
//	public void sendMail(String[] to, List<?> attachment, String title, String text) throws Exception {
//		try {
//			this.init();
//			this.subject = title;
//			this.sendMail(this.from, to, null, null, attachment, null, this.subject, text, null, null);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}

	/**
	 * 使用velocity模板发送邮件，同时包含附件和内嵌文件功能
	 * @param to 收件人地址，可以包含多个收件人
	 * @param attachment 附件对象，参见Attachment类，包含附件名称及其对应的附件文件（java.io.File）
	 * @param inlineFile 内嵌对象，参见InlineFile类，包含附件名称及其对应的附件文件（java.io.File）
	 * @param model 邮件正文velocity模板中使用的变量Map，key为velocity模板中引用的变量，value为其对应的值
	 * @throws Exception
	 */
//	public void sendMail(String[] to, List<?> attachment, List<?> inlineFile, Map<?, ?> model, String title) throws Exception {
//		try {
//			this.init();
//			this.subject = title;
//			this.sendMail(this.from, to, null, null, attachment, inlineFile, this.subject, null, this.velocityFilePath, model);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}

	/**
	 * 使用velocity模板发送邮件，同时包含抄送、密件抄送、附件、内嵌文件功能
	 * @param to 收件人地址，可以包含多个收件人
	 * @param cc 抄送人地址，可以包含多个抄送人
	 * @param bcc 密件抄送地址，可以包含多个密件抄送
	 * @param attachment 附件对象，参见Attachment类，包含附件名称及其对应的附件文件（java.io.File）
	 * @param inlineFile 内嵌对象，参见InlineFile类，包含附件名称及其对应的附件文件（java.io.File）
	 * @param model 邮件正文velocity模板中使用的变量Map，key为velocity模板中引用的变量，value为其对应的值
	 * @throws Exception
	 */
//	public void sendMail(String[] to, String[] cc, String[] bcc, List<?> attachment, List<?> inlineFile, Map<?, ?> model) throws Exception {
//		try {
//			this.init();
//			this.sendMail(this.from, to, cc, bcc, attachment, inlineFile, this.subject, null, this.velocityFilePath, model);
//		} catch (CheckedMailException cme) {
//			throw cme;
//		}
//	}

	/**
	 * 发送邮件主功能，主要用於其他sendMail方法重载
	 * @param from 发件人地址
	 * @param to 收件人地址，可以包含多个收件人
	 * @param cc 抄送人地址，可以包含多个抄送人
	 * @param bcc 密件抄送地址，可以包含多个密件抄送
	 * @param attachment 附件对象，参见Attachment类，包含附件名称及其对应的附件文件（java.io.File）
	 * @param inlineFile 内嵌对象，参见InlineFile类，包含附件名称及其对应的附件文件（java.io.File）
	 * @param subject 邮件主题
	 * @param text 邮件正文（文本，仅用於简单邮件发送）
	 * @param velocityFilePath 邮件正文使用的velocity模板
	 * @param emailInfoMap 邮件正文velocity模板中使用的变量Map，key为velocity模板中引用的变量，value为其对应的值
	 * @param emaildetailId 邮件模板号 eg：10000：火险（有赔款）在核赔通过后给承保发送邮件的模板号；10001：车险在涉及临分案的保单立案时给承保发送邮件的模板号
	 * @throws Exception
	 */
	private void sendMail(String from, String[] to, String[] cc, String[] bcc, List<?> attachment, List<?> inlineFile, String subject, String text, String velocityFilePath, Map<?, ?> emailInfoMap,String emaildetailId) throws Exception {
		// 收集邮件信息
		this.setProperties(from, to, cc, bcc, attachment, inlineFile, subject, text, velocityFilePath, emailInfoMap);
		// 准备邮件信息
		try {
			String key = cacheManager.generateCacheKey("prpLemailConfigSelect", emaildetailId);
			Object result = cacheManager.getCache(key);
			this.sender.setHost(((PrpLemailConfig)result).getSmtpurl());
			this.sender.setPort(DataUtils.getInteger(((PrpLemailConfig)result).getPort()));
			this.sender.setPassword(((PrpLemailConfig)result).getSmtppassword());
			this.sender.setUsername(((PrpLemailConfig)result).getSmtpusername());
			this.prepare(this.sender.createMimeMessage());
		} catch (CheckedMailException cme) {
			throw cme;
		}
		// 发送邮件
		try {
			this.sender.send(this);
			flag = true;
		} catch (Exception me) {
			flag = false;
			throw me;
		}
	}

	public void prepare(MimeMessage mimeMessage) throws CheckedMailException {
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
			boolean flag = false;

			// 设置发件人地址
			if (this.from != null && this.from.trim().length() > 0) {
				if (this.isAddressValidate(this.from)) {
					try {
						message.setFrom(from, this.senderName);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					CheckedMailException cme = new CheckedMailException("發件人地址有誤，請檢查！");
					throw cme;
				}
			} else {
				CheckedMailException cme = new CheckedMailException("發件人地址不能爲空，請輸入發件人地址！");
				throw cme;
			}
			// 设置收件人地址
			if (this.to != null && this.to.length > 0) {
				InternetAddress[] address = new InternetAddress[this.to.length];
				for (int i = 0; i < this.to.length; i++) {
					if (this.isAddressValidate(this.to[i])) {
						address[i] = new InternetAddress(this.to[i]);
					} else {
						CheckedMailException cme = new CheckedMailException("收件人地址有誤，請檢查！");
						throw cme;
					}
				}
				message.setTo(address);
			} else {
				CheckedMailException cme = new CheckedMailException("收件人地址不能爲空，請輸入收件人地址！");
				throw cme;
			}

			// 设置抄送人地址
			if (this.cc != null && this.cc.length > 0) {
				InternetAddress[] ccAddress = new InternetAddress[this.cc.length];
				for (int i = 0; i < this.cc.length; i++) {
					if (this.isAddressValidate(this.cc[i])) {
						ccAddress[i] = new InternetAddress(this.cc[i]);
					} else {
						flag = true;
					}
				}
				if (!flag) {
					message.setCc(ccAddress);
					flag = false;
				} else {
					CheckedMailException cme = new CheckedMailException("抄送人地址有誤，請檢查！");
					throw cme;
				}
			}

			// 设置密件抄送人地址
			if (this.bcc != null && this.bcc.length > 0) {
				InternetAddress[] bccAddress = new InternetAddress[this.bcc.length];
				for (int i = 0; i < this.bcc.length; i++) {
					if (this.isAddressValidate(this.bcc[i])) {
						bccAddress[i] = new InternetAddress(this.bcc[i]);
					} else {
						flag = true;
					}
				}
				if (!flag) {
					message.setBcc(bccAddress);
					flag = false;
				} else {
					CheckedMailException cme = new CheckedMailException("密件抄送人地址有誤，請檢查！");
					throw cme;
				}
			}

			// 设置邮件主题
			message.setSubject(this.subject);

			// 设置附件信息
			if (this.attachment != null && !this.attachment.isEmpty()) {
				for (int i = 0; i < this.attachment.size(); i++) {
					Attachment theAttachment = (Attachment) this.attachment.get(i);
					try {
						message.addAttachment(MimeUtility.encodeWord(theAttachment.getAttachmentFileName()), theAttachment.getAttachmentFile());
						;
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			// 设置邮件正文
			if (this.text == null || text.trim().length() < 1) {
				this.text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, this.velocityFilePath, "GBK", this.model);
			}
			message.setText(this.text, true);

			// 设置inline文件
			if (this.inlineFile != null && !this.inlineFile.isEmpty()) {
				for (int i = 0; i < inlineFile.size(); i++) {
					InlineFile theInlineFile = (InlineFile) this.inlineFile.get(i);
					message.addInline(theInlineFile.getInlineFileName(), theInlineFile.getInlineFile());
				}
			}

		} catch (MessagingException me) {
			me.printStackTrace();
			CheckedMailException cme = new CheckedMailException("組裝MimeMessage失敗！");
			throw cme;
		} catch (VelocityException ve) {
			ve.printStackTrace();
			CheckedMailException cme = new CheckedMailException("讀取Velocity郵件模板失敗！");
			throw cme;
		}
	}

	/**
	 * 设置邮件相关属性
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param attachment
	 * @param inlineFile
	 * @param subject
	 * @param text
	 * @param velocityFilePath
	 * @param model
	 */
	private void setProperties(String from, String[] to, String[] cc, String[] bcc, List<?> attachment, List<?> inlineFile, String subject, String text, String velocityFilePath, Map<?, ?> model) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.attachment = attachment;
		this.inlineFile = inlineFile;
		this.subject = subject;
		this.text = text;
		this.velocityFilePath = velocityFilePath;
		this.model = model;
	}

//	public void run() throws Exception {
//		try {
//			this.sendMail(this.to, null, this.inlineFile, this.model, this.subject);
//		} catch (CheckedMailException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 目前只用到了发送图片，没用到添加附件
	 * @author 中科软
	 * @param to
	 * @param inlineFile
	 * @param model
	 */
	public void setMailMessage(String[] to, List<?> inlineFile, Map<?, ?> model) {
		this.to = to;
		this.inlineFile = inlineFile;
		this.model = model;
	}

	/**
	 * 初始缓存实例
	 */
	private static CacheService cacheManager = CacheManager.getInstance("prplemaildetail");
	
	/**初始化邮件工具类
	 * @param emaildetailId 邮件模板号
	 * @throws Exception
	 */
	private void init(String emaildetailId) throws Exception {
		String key = cacheManager.generateCacheKey("prpLemailConfigSelect", emaildetailId);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			this.to = ((PrpLemailConfig)result).getAddress().split(",");
			this.from = ((PrpLemailConfig)result).getUsername();
			this.senderName = ((PrpLemailConfig)result).getNickname();
			this.velocityFilePath = ((PrpLemailConfig)result).getVelocityFilePath();
			this.subject = ((PrpLemailConfig)result).getTitle();
		}else{
			PrpLemailConfig prpLemailConfig = this.prpLemailConfigService.findPrpLemailConfig(emaildetailId);
			if(prpLemailConfig==null){
				throw new CheckedMailException("請聯繫管理員配置編號"+emaildetailId+"的郵件模板信息！");
			}
			this.to = prpLemailConfig.getAddress().split(",");
			this.from = prpLemailConfig.getUsername();
			this.senderName = prpLemailConfig.getNickname();
			this.velocityFilePath = prpLemailConfig.getVelocityFilePath();
			this.subject = prpLemailConfig.getTitle();
			cacheManager.putCache(key, prpLemailConfig);
		}

	}

	/**
	 * 邮箱地址可靠性判断
	 * @param addr 邮件地址
	 * @return
	 */
	public boolean isAddressValidate(String addr) {
		Pattern p = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		Matcher m = p.matcher(addr);
		return m.matches();
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setSender(JavaMailSenderImpl sender) {
		this.sender = sender;
	}

	public String getVelocityFilePath() {
		return velocityFilePath;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public JavaMailSenderImpl getSender() {
		return sender;
	}

	public String getSubject() {
		return subject;
	}

	public PrpLemailLogService getPrpLemailLogService() {
		return prpLemailLogService;
	}

	public void setPrpLemailLogService(PrpLemailLogService prpLemailLogService) {
		this.prpLemailLogService = prpLemailLogService;
	}
    
}

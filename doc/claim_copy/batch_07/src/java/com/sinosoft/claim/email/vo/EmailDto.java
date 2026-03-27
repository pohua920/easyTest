package com.sinosoft.claim.email.vo;

import ins.framework.utils.DataUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.schema.model.PrpLemailConfig;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 封装待发送邮件的主要讯息对象
 * @author 中科软
 */
public class EmailDto implements MimeMessagePreparator, Serializable {

	private static final long serialVersionUID = 1L;
	/** 邮件所属业务号码 日志记录需要 */
	private String businessNo;
	/** 发件人邮件地址 */
	private String from;
	/** 发件人名称 */
	private String senderName;
	/** 收件人 */
	private String[] to;
	/** 抄送人 */
	private String[] cc;
	/** 密件抄送人 */
	private String[] bcc;
	/** 邮件主题 */
	private String subject = "";
	/** 邮件正文（仅在简单文本邮件发送时使用，稍微复杂格式的邮件内容，建议使用velocity模板配置） */
	private String text;
	/** attachment 附件对象，参见Attachment类，包含附件名称及其对应的附件文件（java.io.File） */
	private List<Attachment> attachment = new ArrayList<Attachment>();
	/** 内嵌对象，参见InlineFile类，包含附件名称及其对应的附件文件（java.io.File） */
	private List<InlineFile> inlineFile = new ArrayList<InlineFile>();
	/** 邮件正文使用的velocity模板 */
	private String velocityFilePath;
	/** velocity模板中定义变量的集合，第一个参数为velocity模板中的变量名， 内嵌到模板的内容 */
	private Map<String, Object> model;
	/** 邮件正文使用的velocity模板 */
	private VelocityEngine velocityEngine;
	/** JavaMailSender */
	private JavaMailSenderImpl sender;

	/**
	 * 初始化邮件工具类
	 * @param emaildetailId 邮件模板号
	 * @param email 邮件讯息对象
	 * @throws Exception
	 */
	public void init(PrpLemailConfig config) throws Exception {
		this.sender.setHost(config.getSmtpurl());
		this.sender.setPort(DataUtils.getInteger(config.getPort()));
		this.sender.setPassword(config.getSmtppassword());
		this.sender.setUsername(config.getSmtpusername());
		this.to = config.getAddress().split(",");
		this.from = config.getUsername();
		this.senderName = config.getNickname();
		this.velocityFilePath = config.getVelocityFilePath();
		if (DataUtils.emptyToNull(this.subject) == null) {
			this.subject = config.getTitle();
		}
		if (this.attachment.isEmpty()) {// 没有预设附件，则默认发送配置数据里的附件
			String attachip = config.getAttachip();
			if (DataUtils.emptyToNull(attachip) != null) {
				File tempFile = null;
				for (String name : attachip.split(",")) {
					tempFile = CommonUtils.getWebRootFile(name);
					if (tempFile != null) {
						this.attachment.add(new Attachment(tempFile.getName(), tempFile));
					}
				}
			}
		}
	}

	/**
	 * 初始化邮件工具类(無附件)
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param emaildetailId 邮件模板号
	 * @param email 邮件讯息对象
	 * @throws Exception
	 */
	public void initForRtc(PrpLemailConfig config) throws Exception {
		this.sender.setHost(config.getSmtpurl());
		this.sender.setPort(DataUtils.getInteger(config.getPort()));
		this.sender.setPassword(config.getSmtppassword());
		this.sender.setUsername(config.getSmtpusername());
		this.to = config.getAddress().split(",");
		this.from = config.getUsername();
		this.senderName = config.getNickname();
		this.velocityFilePath = config.getVelocityFilePath();
		if (DataUtils.emptyToNull(this.subject) == null) {
			this.subject = config.getTitle();
		}
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Attachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}

	public List<InlineFile> getInlineFile() {
		return inlineFile;
	}

	public String getVelocityFilePath() {
		return velocityFilePath;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public void setSender(JavaMailSenderImpl sender) {
		this.sender = sender;
	}

	@Override
	public void prepare(MimeMessage paramMimeMessage) throws Exception {
		try {
			MimeMessageHelper message = new MimeMessageHelper(paramMimeMessage, true);
			message.setSubject(this.subject);
			// 设置发件人地址
			if (DataUtils.emptyToNull(this.from) == null) {
				throw new UserException(1, 3, "郵件發送", "沒有配置發件人，請管理員檢查當前郵件發送模板的配置訊息！");
			} else {
				if (!this.isAddressValidate(this.from)) {
					throw new UserException(1, 3, "郵件發送", "發件人地址" + this.from + "校驗不通過，請管理員檢查當前郵件發送模板的配置訊息！");
				}
				message.setFrom(this.from, this.senderName);
			}
			InternetAddress[] tempArray = null;
			// 设置收件人地址
			if (this.to == null || this.to.length == 0) {
				throw new UserException(1, 3, "郵件發送", "沒有配置收件人，請管理員檢查當前郵件發送模板的配置訊息！");
			} else {
				tempArray = new InternetAddress[this.to.length];
				for (int i = 0; i < this.to.length; i++) {
					if (!this.isAddressValidate(this.to[i])) {
						throw new UserException(1, 3, "郵件發送", "收件人地址" + this.to[i] + "校驗不通過，請管理員檢查當前郵件發送模板的配置訊息！");
					}
					tempArray[i] = new InternetAddress(this.to[i]);
				}
				message.setTo(tempArray);
			}
			// 设置抄送人地址
			if (this.cc != null && this.cc.length != 0) {
				tempArray = new InternetAddress[this.cc.length];
				for (int i = 0; i < this.cc.length; i++) {
					if (!this.isAddressValidate(this.cc[i])) {
						throw new UserException(1, 3, "郵件發送", "抄送人地址" + this.cc[i] + "校驗不通過，請管理員檢查當前郵件發送模板的配置訊息！");
					}
					tempArray[i] = new InternetAddress(this.cc[i]);
				}
				message.setCc(tempArray);
			}
			// 设置密件抄送人地址
			if (this.bcc != null && this.bcc.length > 0) {
				tempArray = new InternetAddress[this.bcc.length];
				for (int i = 0; i < this.bcc.length; i++) {
					if (!this.isAddressValidate(this.bcc[i])) {
						throw new UserException(1, 3, "郵件發送", "密件抄送人地址" + this.bcc[i] + "校驗不通過，請管理員檢查當前郵件發送模板的配置訊息！");
					}
					tempArray[i] = new InternetAddress(this.bcc[i]);
				}
				message.setBcc(tempArray);
			}
			// 设置附件信息
			Attachment tempAttachment = null;
			if (this.attachment != null && !this.attachment.isEmpty()) {
				for (int i = 0; i < this.attachment.size(); i++) {
					tempAttachment = this.attachment.get(i);
					message.addAttachment(MimeUtility.encodeWord(tempAttachment.getAttachmentFileName()), tempAttachment.getAttachmentFile());
				}
			}
			// 设置邮件正文
			if (this.text == null || this.text.trim().length() < 1) {
				this.text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, this.velocityFilePath, this.sender.getDefaultEncoding(), this.model);
			}
			message.setText(this.text, true);
			InlineFile tempInlineFile = null;
			if (this.inlineFile != null && !this.inlineFile.isEmpty()) {
				for (int i = 0; i < this.inlineFile.size(); i++) {
					tempInlineFile = this.inlineFile.get(i);
					message.addInline(tempInlineFile.getInlineFileName(), tempInlineFile.getInlineFile());
				}
			}
		} catch (MessagingException me) {
			throw new UserException(1, 3, "郵件發送", "組裝MimeMessage失敗！");
		} catch (VelocityException ve) {
			throw new UserException(1, 3, "郵件發送", "讀取Velocity郵件模板" + this.velocityFilePath + "失敗！");
		}
	}

	/**
	 * 邮箱地址可靠性判断
	 * @param addr 邮件地址
	 * @return
	 */
	private boolean isAddressValidate(String addr) {
		Pattern p = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		Matcher m = p.matcher(addr);
		return m.matches();
	}

	/***
	 * 发送邮件
	 * @param email 收集的邮件发送讯息
	 * @throws Exception
	 */
	public void send() throws Exception {
		try {
			this.sender.send(this);// 发送邮件
		} catch (MailPreparationException me) {
			Object obj = me.getCause();
			if(obj instanceof UserException){
				throw (UserException)me.getCause();
			}
			throw me;
		} catch (Exception me) {
			throw me;
		}
	}
}

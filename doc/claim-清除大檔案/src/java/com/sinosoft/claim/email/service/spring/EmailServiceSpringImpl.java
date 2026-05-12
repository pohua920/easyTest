package com.sinosoft.claim.email.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.utils.DataUtils;

import java.util.Date;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.email.service.facade.EmailService;
import com.sinosoft.claim.email.vo.EmailDto;
import com.sinosoft.claim.schema.model.PrpLemailConfig;
import com.sinosoft.claim.schema.model.PrpLemailLog;
import com.sinosoft.claim.schema.service.facade.PrpLemailConfigService;
import com.sinosoft.claim.schema.service.facade.PrpLemailLogService;
import com.sinosoft.sysframework.exceptionlog.UserException;

public class EmailServiceSpringImpl implements EmailService {
	// JavaMailSender
	private JavaMailSenderImpl mailSender;
	/** 邮件正文使用的velocity模板 */
	private VelocityEngine velocityEngine;
	/** 邮件配置类Service */
	private PrpLemailConfigService prpLemailConfigService;
	/** 邮件日志类Service */
	private PrpLemailLogService prpLemailLogService;
	/** 初始缓存实例 */
	private static CacheService cacheManager = CacheManager.getInstance("prplemaildetail");

	/**
	 * 邮件发送，正文为vm模板的
	 * @param businessNo 日志记录的业务号码
	 * @param emaildetailId PrpLemailConfig表配置的邮件讯息
	 * @param receiver 收件模块代号：01再保，02承保
	 * @param emailInfoMap
	 *            1，邮件内容集，其中必须包含businessNo，用于prplEmailLog的businessNo的赋值。
	 *            2，邮件正文velocity模板中使用的变量Map，key为velocity模板中引用的变量，value为其对应的值
	 * @return prpLemailLog 返回生成的邮件日志
	 * @throws Exception
	 */
	@Override
	public void mailSend(String businessNo, String emaildetailId, String receiver, Map<String, Object> emailInfoMap) throws Exception {
		EmailDto email = new EmailDto();
		email.setModel(emailInfoMap);
		this.mailSend(businessNo, emaildetailId, receiver, email);
	}

	/***
	 * 带有部分初始化信息email的邮件发送
	 */
	public void mailSend(String businessNo, String emaildetailId, String receiver, EmailDto email) throws Exception {
		this.init(email);
		String errorMessage = "";
		try {
			email.init(this.getConfig(emaildetailId));
			email.send();
		} catch (UserException ue) {
			ue.printStackTrace();
			errorMessage = ue.getErrorMessage();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = CommonUtils.getLocalizedMessage(e.getCause());
			throw e;
		} finally {
			// 记录日志
			this.logForSendEmail(businessNo, emaildetailId, receiver, email.getFrom(), email.getSubject(), email.getTo(), errorMessage);
		}
	}

	/**
	 * 记录邮件发送日志
	 * @param businessNo 业务号码
	 * @param emaildetailId 邮件发送配置模板
	 * @param receiver 接收人所属业务模块
	 * @param from 发件人地址
	 * @param subject 主题
	 * @param to 收件人地址
	 * @param errorMessage 邮件发送异常讯息
	 */
	private void logForSendEmail(String businessNo, String emaildetailId, String receiver, String from, String subject, String[] to, String errorMessage) {
		PrpLemailLog prpLemailLog = new PrpLemailLog();
		prpLemailLog.setBusinessNo(businessNo);
		prpLemailLog.setSendType(emaildetailId);
		prpLemailLog.setReceiver(receiver);
		prpLemailLog.setSender(DataUtils.dbNullToEmpty(from));
		prpLemailLog.setSendTime(new Date());
		prpLemailLog.setTitle(DataUtils.dbNullToEmpty(subject));
		prpLemailLog.setAddressee(CommonUtils.join(to, ","));
		prpLemailLog.setValidstatus("1");// 1代表數據有效
		if (DataUtils.emptyToNull(errorMessage) != null) {
			prpLemailLog.setFlag("0");
			prpLemailLog.setRemark(errorMessage);
		}
		this.prpLemailLogService.logForSendEmail(prpLemailLog);
	}

	/****
	 * 发送邮件初始化讯息
	 * @param emaildetailId
	 * @return
	 * @throws Exception
	 */
	private PrpLemailConfig getConfig(String emaildetailId) throws Exception {
		String key = cacheManager.generateCacheKey("prpLemailConfigSelect", emaildetailId);
		Object result = cacheManager.getCache(key);
		PrpLemailConfig prpLemailConfig = null;
		if (result != null) {
			prpLemailConfig = (PrpLemailConfig) result;
		} else {
			prpLemailConfig = this.prpLemailConfigService.findPrpLemailConfig(emaildetailId);
			if (prpLemailConfig == null) {
				throw new UserException(1, 3, "郵件發送", "編號" + emaildetailId + "的郵件模板訊息沒有配置！");
			}
			cacheManager.putCache(key, prpLemailConfig);
		}
		return prpLemailConfig;
	}

	
	/***
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 */
	public void mailSendForRtc(String registNo, EmailDto email) throws Exception {
		this.init(email);
		String errorMessage = "";
		try {
			if(null!=email && null!=email.getTo() && email.getTo().length>0 ){
				String addr = "";
				for(String s:email.getTo()){
					addr +=","+s+"@ctbcins.com";
				}
				PrpLemailConfig plcf = new PrpLemailConfig();//this.getConfig(emaildetailId);
				plcf.setAddress(addr.substring(1));
				plcf.setUsername(email.getFrom());
				plcf.setSmtpurl("mail.ctbcins.com");
				plcf.setPort("25");
				plcf.setSmtpusername("newims");
				plcf.setSmtppassword("2012newims");
				email.initForRtc(plcf);
				email.send();
			}
		} catch (UserException ue) {
			ue.printStackTrace();
			errorMessage = ue.getErrorMessage();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = CommonUtils.getLocalizedMessage(e.getCause());
			throw e;
		} finally {
			// 记录日志
			this.logForSendEmail(registNo, "toRtc", "01", email.getFrom(), email.getSubject(), email.getTo(), errorMessage);
		}
	}
	/***
	 * 设置邮件注入的对象
	 * @param email
	 */
	private void init(EmailDto email) {
		email.setSender(this.getMailSender());
		email.setVelocityEngine(this.getVelocityEngine());
	}

	public PrpLemailConfigService getPrpLemailConfigService() {
		return prpLemailConfigService;
	}

	public void setPrpLemailConfigService(PrpLemailConfigService prpLemailConfigService) {
		this.prpLemailConfigService = prpLemailConfigService;
	}

	public PrpLemailLogService getPrpLemailLogService() {
		return prpLemailLogService;
	}

	public void setPrpLemailLogService(PrpLemailLogService prpLemailLogService) {
		this.prpLemailLogService = prpLemailLogService;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}

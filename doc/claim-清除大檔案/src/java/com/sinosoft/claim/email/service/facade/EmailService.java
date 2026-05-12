package com.sinosoft.claim.email.service.facade;

import java.util.Map;

import com.sinosoft.claim.email.vo.EmailDto;

public interface EmailService {
	/** 
	 * 发送邮件 主信息完全从模板读取的
	 * @param businessNo 日志记录的业务号码
	 * @param emailInfoMap 邮件内容集，其中必须包含businessNo，用于prplEmailLog的businessNo的赋值。
	 * @param emaildetailId 邮件发送主信息配置的模板号 详见PrpLemailConfig
	 * @param receiver 收件模块代号：01再保，02承保
	 * @throws Exception
	 */
	public void mailSend(String businessNo,String emaildetailId,String receiver,Map<String, Object> emailInfoMap) throws Exception;

	/***
	 * 发送邮件 主信息有部分初始化
	 * @param emaildetailId PrpLemailConfig邮件讯息配置的模板号
	 * @param receiver 
	 * @param email 邮件讯息主对象
	 * @throws Exception
	 */
	public void mailSend(String businessNo,String emaildetailId, String receiver,EmailDto email) throws Exception;
	
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param email
	 * @throws Exception
	 */
	public void mailSendForRtc(String registNo, EmailDto email) throws Exception ;

}

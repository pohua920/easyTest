package com.sinosoft.claim.sms.service.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.schema.model.PrpLsmsTemplate;

public interface SmsService {
	/**
	 * 发送多个模板内容
	 * @param prpLsmsTemplateList
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String sendSms(List<PrpLsmsTemplate> prpLsmsTemplateList,Map<String,Object> data)throws Exception;
	/**
	 * 发送短信接口
	 * @param data
	 * @param modelIds
	 * @return
	 */
	public String sendSms(final PrpLsmsTemplate prpLsmsTemplate,final Map<String, Object> data) throws Exception;
	/**
	 * 获取提交日期
	 * @return
	 */
	public String getSubmitDate(Date submitDate);
	/**
	 * 判断是否是手机号码
	 * @param mobileNumber
	 * @return
	 */
	public boolean isMobileNumber(String mobileNumber);
	/**
	 * 定时任务执行
	 * @throws Exception
	 */
	public void smsJobDetail()throws Exception;
	
}

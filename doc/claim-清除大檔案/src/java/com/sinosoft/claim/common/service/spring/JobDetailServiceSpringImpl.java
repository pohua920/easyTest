package com.sinosoft.claim.common.service.spring;

import ins.framework.common.ServiceFactory;

import com.sinosoft.claim.common.service.facade.JobDetailService;
import com.sinosoft.claim.sms.service.facade.SmsService;
import com.sinosoft.sysframework.common.datatype.DateTime;

public class JobDetailServiceSpringImpl implements JobDetailService{

	public void smsTask() {
		try {
			SmsService smsService = (SmsService) ServiceFactory.getService("smsService");
			smsService.smsJobDetail();
			System.out.println("定时执行发送简讯任务执行成功。。。"+DateTime.current());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

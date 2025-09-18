package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.cibPolicyDataImportService.RunCibPolicyDataImportService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public class CibPolicyDataReturnDailyJob implements Job{

	protected Logger logger = Logger.getLogger(CibPolicyDataReturnDailyJob.class);	
	private RunCibPolicyDataImportService runCibPolicyDataImportService;
	
	public CibPolicyDataReturnDailyJob() {
		super();
		runCibPolicyDataImportService = (RunCibPolicyDataImportService)AppContext.getBean("runCibPolicyDataImportService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("CibPolicyDataReturnDailyJob begin...");
		String dateStr = DateUtils.getDateString(new Date());
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("SYS");
		
		try {
			runCibPolicyDataImportService.policyDataReturn(userInfo);
			logger.info("中信新件資料回饋檔作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("中信新件資料回饋檔作業失敗" + dateStr);
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("中信新件資料回饋檔作業失敗" + dateStr);
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("CibPolicyDataReturnDailyJob end...");
	}
}

package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firBatchSendmailService.BatchSendmailService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.UserInfo;

public class FirBatchSendmailJob implements Job{

	protected Logger logger = Logger.getLogger(FirBatchSendmailJob.class);	
	//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
	private BatchSendmailService batchSendmailService;
	
	public FirBatchSendmailJob() {
		super();
		//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
		batchSendmailService = (BatchSendmailService)AppContext.getBean("batchSendmailService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirBatchSendmailJob begin...");
		String dateStr = DateUtils.getDateString(new Date());
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("SYS");
		
		try {
			//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
			batchSendmailService.batchSendmail(userInfo);
			logger.info("APS電子保單發送結果確認作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("APS電子保單發送結果確認作業失敗" + dateStr);
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("APS電子保單發送結果確認作業失敗" + dateStr);
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("FirBatchSendmailJob end...");
	}
}

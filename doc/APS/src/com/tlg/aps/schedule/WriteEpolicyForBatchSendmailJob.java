package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firBatchSendmailService.WriteEpolicyForBatchSendmailService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
public class WriteEpolicyForBatchSendmailJob implements Job {
	protected Logger logger = Logger.getLogger(WriteEpolicyForBatchSendmailJob.class);	
	private WriteEpolicyForBatchSendmailService writeEpolicyForBatchSendmailService;
	
	public WriteEpolicyForBatchSendmailJob() {
		super();
		writeEpolicyForBatchSendmailService = (WriteEpolicyForBatchSendmailService)AppContext.getBean("writeEpolicyForBatchSendmailService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("WriteEpolicyForBatchSendmail begin...");
		
		try {
			writeEpolicyForBatchSendmailService.writeEpolicyForBatchSendmail();
			logger.info("成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("成功" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("WriteEpolicyForBatchSendmailJob end...");
	}
}

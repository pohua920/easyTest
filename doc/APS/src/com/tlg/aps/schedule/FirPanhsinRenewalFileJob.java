package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firPanhsinFeedbackFile.FirPanhsinRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

public class FirPanhsinRenewalFileJob implements Job{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	
	protected Logger logger = Logger.getLogger(FirPanhsinRenewalFileJob.class);	
	private FirPanhsinRenewalFileService firPanhsinRenewalFileService;
	
	public FirPanhsinRenewalFileJob() {
		super();
		firPanhsinRenewalFileService = (FirPanhsinRenewalFileService)AppContext.getBean("firPanhsinRenewalFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirPanhsinRenewalFileJob begin...");
		try {
			firPanhsinRenewalFileService.runToReceiveData("SYSTEM", new Date(), "FIR_AGT_BOPRN");
			logger.info("板信續保檔資料接收作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("板信續保檔資料接收作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("板信續保檔資料接收作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirPanhsinRenewalFileJob end...");
	}
}

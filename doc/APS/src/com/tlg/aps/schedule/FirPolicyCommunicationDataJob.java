package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firPolicyCommunicationDataService.RunCommunicationDataService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public class FirPolicyCommunicationDataJob implements Job {
	protected Logger logger = Logger.getLogger(FirPolicyCommunicationDataJob.class);	
	private RunCommunicationDataService runCommunicationDataService;
	
	public FirPolicyCommunicationDataJob() {
		super();
		runCommunicationDataService = (RunCommunicationDataService)AppContext.getBean("runCommunicationDataService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirPolicyCommunicationDataJob begin...");
		
		try {
			runCommunicationDataService.generatedata("FIR_APS_PINS01");
			logger.info("住火-保單通訊資料產生及批次作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("住火-保單通訊資料產生及批次作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("住火-保單通訊資料產生及批次作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("FirPolicyCommunicationDataJob end...");
	}
}

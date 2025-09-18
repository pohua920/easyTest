package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firFubonRenewalService.FirFubonRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

public class FirFubonRenewalFileJob implements Job{
	
	protected Logger logger = Logger.getLogger(FirFubonRenewalFileJob.class);	
	private FirFubonRenewalFileService firFubonRenewalFileService;
	
	public FirFubonRenewalFileJob() {
		super();
		firFubonRenewalFileService = (FirFubonRenewalFileService)AppContext.getBean("firFubonRenewalFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirFubonRenewalFileJob begin...");
		try {
			firFubonRenewalFileService.runToReceiveData("SYSTEM", new Date(), "FIR_AGT_FBRN");
			logger.info("富邦續保檔資料接收作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("富邦續保檔資料接收作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("富邦續保檔資料接收作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirFubonRenewalFileJob end...");
	}
}

package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firRnproposalRenewalFileService.FirRnproposalRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public class FirRnproposalRenewalFileJob implements Job{

	protected Logger logger = Logger.getLogger(FirRnproposalRenewalFileJob.class);	
	private FirRnproposalRenewalFileService firRnproposalRenewalFileService;
	
	public FirRnproposalRenewalFileJob() {
		super();
		firRnproposalRenewalFileService = (FirRnproposalRenewalFileService)AppContext.getBean("firRnproposalRenewalFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirRnproposalRenewalFileJob begin...");
		try {
			firRnproposalRenewalFileService.runToProcessFile("SYSTEM", new Date(), "FIR_CTBC_03");
			logger.info("中信續件要保書資料接收排程作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("中信續件要保書資料接收排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("中信續件要保書資料接收排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirRnproposalRenewalFileJob end...");
	}
}

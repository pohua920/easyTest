package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firBotRenewalFileService.FirBotRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public class FirBotCompareFdIntoCore implements Job {
	protected Logger logger = Logger.getLogger(FirBotCompareFdIntoCore.class);	
	private FirBotRenewalFileService firBotRenewalFileService;
	
	public FirBotCompareFdIntoCore() {
		super();
		firBotRenewalFileService = (FirBotRenewalFileService)AppContext.getBean("firBotRenewalFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirBotCompareFdIntoCoreJob begin...");
		
		try {
			Date excuteTime = new Date();
			firBotRenewalFileService.compareFDIntoCore("SYSTEM", excuteTime, "FIR_AGT_BOTRN_FD");
			logger.info("台銀FD檔轉檔進核心作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("台銀FD檔轉檔進核心作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("台銀FD檔轉檔進核心作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirBotCompareFdIntoCore end...");
	}
}

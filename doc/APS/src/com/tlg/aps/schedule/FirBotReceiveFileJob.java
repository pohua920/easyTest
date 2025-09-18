package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firBotRenewalFileService.FirBotReceiveFDFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public class FirBotReceiveFileJob implements Job {
	protected Logger logger = Logger.getLogger(FirBotReceiveFileJob.class);	
	private FirBotReceiveFDFileService firBotReceiveFDFileService;
	
	public FirBotReceiveFileJob() {
		super();
		firBotReceiveFDFileService = (FirBotReceiveFDFileService)AppContext.getBean("firBotReceiveFDFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirBotReceiveFDFileJob begin...");
		
		try {
			Date excuteTime = new Date();
			firBotReceiveFDFileService.runToReceiveData("SYSTEM", excuteTime, "FIR_AGT_BOT_FD");
			logger.info("接收台銀FD檔作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("接收台銀FD檔作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("接收台銀FD檔作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirBotReceiveFDFileJob end...");
	}
}

package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firPanhsinFeedbackFile.FirProcessPanhsinFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

public class FirAcceptanceFileJob implements Job{
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */

	protected Logger logger = Logger.getLogger(FirAcceptanceFileJob.class);	
	/* mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 start*/
	private FirProcessPanhsinFileService firProcessPanhsinFileService;
	
	public FirAcceptanceFileJob() {
		super();
		firProcessPanhsinFileService = (FirProcessPanhsinFileService)AppContext.getBean("firProcessPanhsinFileService");
	}		
	/* mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 end */
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirAcceptanceFileJob begin...");
		try {
			//mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業
			//mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業
			firProcessPanhsinFileService.RunToGenerateFiles("SYSTEM", new Date(), "FIR_AGT_BOP_01");
			logger.info("板信要保受理檔產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("板信要保受理檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("板信要保受理檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirAcceptanceFileJob end...");
	}
}

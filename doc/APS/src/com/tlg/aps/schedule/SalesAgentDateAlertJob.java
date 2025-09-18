package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.salesAgentDateAlertService.SalesAgentDateAlertService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;

/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 */
public class SalesAgentDateAlertJob implements Job {
	protected Logger logger = Logger.getLogger(SalesAgentDateAlertJob.class);	
	private SalesAgentDateAlertService salesAgentDateAlertService;
	
	public SalesAgentDateAlertJob() {
		super();
		salesAgentDateAlertService = (SalesAgentDateAlertService)AppContext.getBean("salesAgentDateAlertService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("SalesAgentDateAlertJob begin...");
		
		try {
			Result result = salesAgentDateAlertService.excute();
			logger.info(result.getMessage());
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("業務員登錄證到期通知排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("業務員登錄證到期通知排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("SalesAgentDateAlertJob end...");
	}
}

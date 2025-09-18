package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.salesAgentDateChangeService.SalesAgentDateChangeService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知 */
public class SalesAgentDateChangeJob implements Job {
	protected Logger logger = Logger.getLogger(SalesAgentDateChangeJob.class);	
	private SalesAgentDateChangeService salesAgentDateChangeService;
	
	public SalesAgentDateChangeJob() {
		super();
		logger.info("SalesAgentDateChangeJob super begin...");
		salesAgentDateChangeService = (SalesAgentDateChangeService)AppContext.getBean("salesAgentDateChangeService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("SalesAgentDateChangeJob(PRPDAGENTLOGINDATERESERVE) begin...");
		
		try {
			Result result = salesAgentDateChangeService.excute();
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
		
		logger.info("SalesAgentDateChangeJob end...");
	}
}

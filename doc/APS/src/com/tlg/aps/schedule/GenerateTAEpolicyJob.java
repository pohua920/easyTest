package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.generateEpolicyService.GenerateEpolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/**
 * HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單
 * @author bi086
 *
 */
public class GenerateTAEpolicyJob extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(GenerateTAEpolicyJob.class);

	public GenerateTAEpolicyJob() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("GenerateTAEpolicyJob begin...");
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			 if (jobs != null && !jobs.isEmpty()) {
				 for (JobExecutionContext job : jobs) {
					 if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						 logger.info("There's another instance running, : " + this);
						 return;
					 }
				 }
			 }
			 
			 GenerateEpolicyService generateEpolicyService = (GenerateEpolicyService) AppContext.getBean("generateEpolicyService");
			 generateEpolicyService.queryToGenerateTAEpolicyService("APS", "SCHEDULE");
			 
			logger.info("排程產生TA電子保單作業完成");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("排程產生TA電子保單作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("排程產生TA電子保單作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("GenerateTAEpolicyJob end...");
	}
}

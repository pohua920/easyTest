package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.metaAmlService.AmlAS400Service;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

public class As400ToMetaAmlJob  extends QuartzJobBean{

	protected Logger logger = Logger.getLogger(As400ToMetaAmlJob.class);	
	
	public As400ToMetaAmlJob() {
		super();
	}		
	
	 @SuppressWarnings("unchecked")
	 @Override
	 protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
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
			 logger.info("AS400 轉洗錢作業開始～");
			 AmlAS400Service amlAS400Service = (AmlAS400Service)AppContext.getBean("amlAS400Service");
			 amlAS400Service.as400toMetaAmlWebService();
			 logger.info("AS400 轉洗錢作業完成～");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("AS400 轉洗錢作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("AS400 轉洗錢作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		logger.info("As400ToMetaAmlJob end...");
	}
}

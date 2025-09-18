package com.tlg.aps.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;

/** mantis：MOB0009，處理人員：BJ016，需求單編號：MOB0009 	回傳遠傳回饋檔作業 */
public class UploadFeedbackFileToFetSchedule implements Job{

	protected Logger logger = Logger.getLogger(UploadFeedbackFileToFetSchedule.class);	
	private RunFetPolicyService runFetPolicyService;
	
	public UploadFeedbackFileToFetSchedule() {
		super();
		runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("UploadFeedbackFileToFetSchedule begin...");
		try {
			runFetPolicyService.offerDailyFeedbackFileToFet();
			logger.info("回傳遠傳回饋檔作業");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("回傳遠傳回饋檔作業失敗");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("回傳遠傳回饋檔作業失敗");
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("UploadFeedbackFileToFetSchedule end...");
	}
}

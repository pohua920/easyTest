package com.tlg.aps.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

/** mantis：MOB0003，處理人員：CC009，需求單編號：MOB0003 從遠傳抓取要保書檔作業 */
public class FetPolicyFileDownloadSchedule implements Job{

	protected Logger logger = Logger.getLogger(FetPolicyFileDownloadSchedule.class);	
	private RunFetPolicyService runFetPolicyService;
	
	public FetPolicyFileDownloadSchedule() {
		super();
		runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FetPolicyFileDownloadSchedule begin...");
		try {
			Result result = runFetPolicyService.getProposalFileFromFet();
			logger.info(result.getMessage().getMessageString());
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("從遠傳抓取要保書檔作業失敗");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("從遠傳抓取要保書檔作業失敗");
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("FetPolicyFileDownloadSchedule end...");
	}
}

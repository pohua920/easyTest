package com.tlg.aps.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

/** mantis：MOB0002，處理人員：CC009，需求單編號：MOB0002 遠傳要保及批單資料作洗錢、利關人、黑名單檢核 */
public class FetPolicyCheckSchedule implements Job{

	protected Logger logger = Logger.getLogger(FetPolicyCheckSchedule.class);	
	private RunFetPolicyService runFetPolicyService;
	
	public FetPolicyCheckSchedule() {
		super();
		runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FetPolicyCheckSchedule begin...");
		try {
			Result result = runFetPolicyService.checkFetInsuranceData();
			logger.info(result.getMessage().getMessageString());
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("遠傳要保及批單資料作洗錢、利關人、黑名單檢核作業失敗");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("遠傳要保及批單資料作洗錢、利關人、黑名單檢核作業失敗");
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("FetPolicyCheckSchedule end...");
	}
}

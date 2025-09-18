package com.tlg.aps.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

/** mantis：MOB0020，處理人員：BJ016，需求單編號：MOB0020保單資料要寄送電子保單*/
public class FetEpolicySchedule implements Job{

	protected Logger logger = Logger.getLogger(FetEpolicySchedule.class);	
	private RunFetPolicyService runFetPolicyService;
	
	public FetEpolicySchedule() {
		super();
		runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FetEpolicySchedule begin...");
		try {
			Result result = this.runFetPolicyService.sendMobileEpolicy();
			String msg = "";
			if(result != null && result.getMessage() != null) {
				msg = result.getMessage().getMessageString();
			} else {
				msg = "遠傳手機保險寄送電子保單作業異常";
			}
			logger.info(msg);
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("遠傳手機保險寄送電子保單作業失敗");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("遠傳手機保險寄送電子保單作業失敗");
			logger.error(e.getMessage());
		}				
		logger.info("FetEpolicySchedule end...");
	}
}

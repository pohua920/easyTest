package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.carBrokenPolicyDataServerce.RunBrokenPolicyDataService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能*/
public class CarBrokenPolicySendMailJob implements Job {
	protected Logger logger = Logger.getLogger(CarBrokenPolicySendMailJob.class);	
	private RunBrokenPolicyDataService runBrokenPolicyDataService;
	
	public CarBrokenPolicySendMailJob() {
		super();
		runBrokenPolicyDataService = (RunBrokenPolicyDataService)AppContext.getBean("runBrokenPolicyDataService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("CarBrokenPolicySendMailJob begin...");
		
		try {
			runBrokenPolicyDataService.checkTvbcmAndSendMail();
			logger.info("關貿斷保資料排程信件通知成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("關貿斷保資料排程信件通知失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("關貿斷保資料排程信件通知成功" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("CarBrokenPolicySendMailJob end...");
	}
}

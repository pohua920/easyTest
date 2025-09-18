package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.carFourthPostcardServerce.RunFourthPostcardService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片*/
public class CarFourthPostcardSendMailJob implements Job {
	protected Logger logger = Logger.getLogger(CarFourthPostcardSendMailJob.class);	
	private RunFourthPostcardService runFourthPostcardService;
	
	public CarFourthPostcardSendMailJob() {
		super();
		runFourthPostcardService = (RunFourthPostcardService)AppContext.getBean("runFourthPostcardService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("CarFourthPostcardSendMailJob begin...");
		
		try {
			runFourthPostcardService.checkTvmcqAndSendMail();
			logger.info("關貿第四次明信片排程信件通知成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("關貿第四次明信片排程信件通知失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("關貿第四次明信片排程信件通知成功" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("CarFourthPostcardSendMailJob end...");
	}
}

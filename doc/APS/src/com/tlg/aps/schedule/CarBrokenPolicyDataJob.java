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
public class CarBrokenPolicyDataJob implements Job {
	protected Logger logger = Logger.getLogger(CarBrokenPolicyDataJob.class);	
	private RunBrokenPolicyDataService runBrokenPolicyDataService;
	
	public CarBrokenPolicyDataJob() {
		super();
		runBrokenPolicyDataService = (RunBrokenPolicyDataService)AppContext.getBean("runBrokenPolicyDataService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("CarBrokenPolicyDataJob begin...");
		
		try {
			runBrokenPolicyDataService.readFileAndImportData(new Date());
			logger.info("關貿斷保資料排程作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("關貿斷保資料排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("關貿斷保資料排程作業成功" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("CarBrokenPolicyDataJob end...");
	}
}

package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public class FetPolicyImportSchedule implements Job{

	protected Logger logger = Logger.getLogger(FetPolicyImportSchedule.class);	
	private RunFetPolicyService runFetPolicyService;
	
	public FetPolicyImportSchedule() {
		super();
		runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FetPolicyImportSchedule begin...");
		try {
			Date executeTime = new Date();
			String rptBatchNo = new SimpleDateFormat("yyyyMMddHH").format(executeTime)+"00";
			runFetPolicyService.getDailyInsuranceDataFromFet(rptBatchNo);
			logger.info("遠傳手機保險投保&批改作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("遠傳手機保險投保&批改作業失敗");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("遠傳手機保險投保&批改作業失敗");
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("FetPolicyImportSchedule end...");
	}
}

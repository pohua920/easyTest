package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firAddressImportService.RunFirAddressImportService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.UserInfo;

public class FirAddressImportDailyJob implements Job{
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
	
	protected Logger logger = Logger.getLogger(FirAddressImportDailyJob.class);	
	private RunFirAddressImportService runFirAddressImportService;
	
	public FirAddressImportDailyJob() {
		super();
		runFirAddressImportService = (RunFirAddressImportService)AppContext.getBean("runFirAddressImportService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirAddressImportDailyJob begin...");
		String dateStr = DateUtils.getDateString(new Date());
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("SYSTEM");
		Date excuteTime = new Date();
		String programId = "FIR_ADDR_01";
		
		try {
			runFirAddressImportService.AddressDataImport(userInfo, excuteTime, programId);
			logger.info("火險地址資料匯入作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("火險地址資料匯入作業失敗" + dateStr);
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("火險地址資料匯入作業失敗" + dateStr);
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("FirAddressImportDailyJob end...");
	}
}

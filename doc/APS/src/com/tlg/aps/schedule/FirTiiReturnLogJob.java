package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firTiiDataServerce.FirTiiReturnLogDownloadService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
public class FirTiiReturnLogJob implements Job{
	
	protected Logger logger = Logger.getLogger(FirTiiReturnLogJob.class);	
	private FirTiiReturnLogDownloadService firTiiReturnLogDownloadService;
	
	public FirTiiReturnLogJob() {
		super();
		firTiiReturnLogDownloadService = (FirTiiReturnLogDownloadService)AppContext.getBean("firTiiReturnLogDownloadService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirTiiReturnLogJob begin...");
		try {
			firTiiReturnLogDownloadService.runToReceiveData();
			logger.info("保發中心-住火保批資料回饋檔回存資料庫作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("保發中心-住火保批資料回饋檔回存資料庫作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("保發中心-住火保批資料回饋檔回存資料庫作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirTiiReturnLogJob end...");
	}
}

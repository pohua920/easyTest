package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firTiiDataServerce.FirTiiDataService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public class FirGenTiiFileJob implements Job{
	
	protected Logger logger = Logger.getLogger(FirGenTiiFileJob.class);	
	private FirTiiDataService firTiiDataService;
	
	public FirGenTiiFileJob() {
		super();
		firTiiDataService = (FirTiiDataService)AppContext.getBean("firTiiDataService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirUploadTiiDataJob begin...");
		try {
			firTiiDataService.generateFileAndUpload("SYSTEM", "", "1", "FIR_BATCH_TII_02");
			logger.info("保發中心-住火保批資料-資料傳送作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("保發中心-住火保批資料-資料傳送作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("保發中心-住火保批資料-資料傳送作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirUploadTiiDataJob end...");
	}
}

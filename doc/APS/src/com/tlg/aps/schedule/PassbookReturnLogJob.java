package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.passbookLogDownloadService.PassbookReturnLogDownloadService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
public class PassbookReturnLogJob implements Job{
	
	protected Logger logger = Logger.getLogger(PassbookReturnLogJob.class);
	private PassbookReturnLogDownloadService passbookReturnLogDownloadService;
	
	public PassbookReturnLogJob() {
		super();
		passbookReturnLogDownloadService = (PassbookReturnLogDownloadService)AppContext.getBean("passbookReturnLogDownloadService");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("PassbookReturnLogJob begin...");
		try {
			passbookReturnLogDownloadService.runToReceiveData();
			logger.info("保單存摺回饋檔回存排程作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("保單存摺回饋檔回存排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("保單存摺回饋檔回存排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}
		logger.info("PassbookReturnLogJob end...");
	}
	
}

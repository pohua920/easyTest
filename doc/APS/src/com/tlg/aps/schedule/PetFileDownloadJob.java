package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.cibPolicyDataImportService.RunCibPolicyDataImportService;
import com.tlg.aps.bs.petFileDownloadService.PetFileDownloadService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.UserInfo;

public class PetFileDownloadJob implements Job{

	protected Logger logger = Logger.getLogger(PetFileDownloadJob.class);	
	private PetFileDownloadService petFileDownloadService;
	
	public PetFileDownloadJob() {
		super();
		petFileDownloadService = (PetFileDownloadService)AppContext.getBean("petFileDownloadService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("PetFileDownloadJob begin...");
		String dateStr = DateUtils.getDateString(new Date());
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("SYS");
		
		try {
			petFileDownloadService.petFileDownload(userInfo);
			logger.info("寵物險附加檔下載成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("寵物險附加檔下載失敗" + dateStr);
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("寵物險附加檔下載失敗" + dateStr);
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("PetFileDownloadJob end...");
	}
}

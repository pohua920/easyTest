package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.hasLionBackFileService.HasLawBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

public class HasLawBackFileJob implements Job {
	protected Logger logger = Logger.getLogger(HasLawBackFileJob.class);	
	private HasLawBackFileService hasLawBackFileService;
	
	public HasLawBackFileJob() {
		super();
		hasLawBackFileService = (HasLawBackFileService) AppContext.getBean("hasLawBackFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info(">>> HasLawBackFileJob begin...");
		
		try {
			Date excuteTime = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, -1);
			Date dataDate = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String formatDataDate = sdf.format(dataDate);
			// test data
			//formatDataDate = "20240515";
			dataDate = sdf.parse(formatDataDate);
			hasLawBackFileService.runToGenerateFile(excuteTime, "SYSTEM", dataDate);
			logger.info("錠嵂保經全險種回饋檔產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("錠嵂保經全險種回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("錠嵂保經全險種回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info(">>> HasLawBackFileJob end...");
	}
}

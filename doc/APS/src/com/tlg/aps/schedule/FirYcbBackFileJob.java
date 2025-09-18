package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firYcbBackFileService.FirYcbBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
public class FirYcbBackFileJob implements Job {
	protected Logger logger = Logger.getLogger(FirYcbBackFileJob.class);	
	private FirYcbBackFileService firYcbBackFileService;
	
	public FirYcbBackFileJob() {
		super();
		firYcbBackFileService = (FirYcbBackFileService)AppContext.getBean("firYcbBackFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirYCBBackFileJob begin...");
		
		try {
			Date excuteTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
			firYcbBackFileService.runToGenerateFile(excuteTime, "SYSTEM", "FIR_AGT_YCB_FB");
			logger.info("元大回饋檔產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("元大回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("元大回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirYCBBackFileJob end...");
	}
}

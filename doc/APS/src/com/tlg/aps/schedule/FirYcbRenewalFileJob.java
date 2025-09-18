package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firYcbRenewalFileService.FirYcbRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程  **/
public class FirYcbRenewalFileJob implements Job {
	protected Logger logger = Logger.getLogger(FirYcbRenewalFileJob.class);	
	private FirYcbRenewalFileService firYcbRenewalFileService;
	
	public FirYcbRenewalFileJob() {
		super();
		firYcbRenewalFileService = (FirYcbRenewalFileService)AppContext.getBean("firYcbRenewalFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirYcbRenewalFileJob begin...");
		
		try {
			Date excuteTime = new Date();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
			Calendar c =Calendar.getInstance();
			c.setTime(excuteTime);
			c.add(Calendar.MONTH, 2);
			String rnDate=sdf.format(c.getTime());
			firYcbRenewalFileService.runToReceiveData("SYSTEM", excuteTime, "FIR_AGT_YCBRN", rnDate);
			logger.info("元大續保產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("元大續保產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("元大續保產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirYcbRenewalFileJob end...");
	}
}

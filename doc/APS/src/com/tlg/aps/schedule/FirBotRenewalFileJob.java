package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firBotRenewalFileService.FirBotRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：FIR0620，處理人員：CD094，需求單編號：FIR0620 住火-台銀續保作業  **/
public class FirBotRenewalFileJob implements Job {
	protected Logger logger = Logger.getLogger(FirBotRenewalFileJob.class);	
	private FirBotRenewalFileService firBotRenewalFileService;
	
	public FirBotRenewalFileJob() {
		super();
		firBotRenewalFileService = (FirBotRenewalFileService)AppContext.getBean("firBotRenewalFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirBotRenewalFileJob begin...");
		
		try {
			Date excuteTime = new Date();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
			Calendar c =Calendar.getInstance();
			c.setTime(excuteTime);
			c.add(Calendar.MONTH, 2);
			String rnDate=sdf.format(c.getTime());
			firBotRenewalFileService.runToReceiveData("SYSTEM", excuteTime, "FIR_AGT_BOTRN", rnDate);
			logger.info("台銀續保產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("台銀續保產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("台銀續保產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirBotRenewalFileJob end...");
	}
}

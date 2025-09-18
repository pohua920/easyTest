package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.hasLionBackFileService.HasLionBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasLionCHBackFileJob implements Job {
	protected Logger logger = Logger.getLogger(HasLionCHBackFileJob.class);	
	private HasLionBackFileService hasLionBackFileService;
	
	public HasLionCHBackFileJob() {
		super();
		hasLionBackFileService = (HasLionBackFileService)AppContext.getBean("hasLionBackFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("HasLionBackFileJob begin...");
		
		try {
			String type ="2";
			Date excuteTime = new Date();
			Calendar c=Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, -1);
			Date dataDate =c.getTime();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String formatDataDate =sdf.format(dataDate);
			 dataDate =sdf.parse(formatDataDate);
		
			hasLionBackFileService.runToGenerateFile(type, excuteTime, "SYSTEM", "HAS_AGT_LION",dataDate);
			logger.info("雄獅回饋檔產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("雄獅回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("雄獅回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("HasLionBackFileJob end...");
	}
}

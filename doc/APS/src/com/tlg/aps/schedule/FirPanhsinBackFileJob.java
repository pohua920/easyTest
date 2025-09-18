package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firPanhsinBackFileService.FirPanhsinBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public class FirPanhsinBackFileJob implements Job {
	protected Logger logger = Logger.getLogger(FirPanhsinBackFileJob.class);	
	private FirPanhsinBackFileService firPanhsinBackFileService;
	
	public FirPanhsinBackFileJob() {
		super();
		firPanhsinBackFileService = (FirPanhsinBackFileService)AppContext.getBean("firPanhsinBackFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirPanhsinBackFileJob begin...");
		
		try {
			String type = "2";
			Date excuteTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
			//定時排程程式：受理檔每日21:30執行。保單檔每日21:50執行。
			if("2130".equals(sdf.format(excuteTime)))
				type = "1";
			firPanhsinBackFileService.runToGenerateFile(type, excuteTime, "SYSTEM", "FIR_AGT_BOP_FB");
			logger.info("板信回饋檔產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("板信回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("板信回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirPanhsinBackFileJob end...");
	}
}

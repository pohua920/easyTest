package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class FirGenTiiDataJob implements Job{
	
	protected Logger logger = Logger.getLogger(FirGenTiiDataJob.class);	
	private FirTiiDataService firTiiDataService;
	
	public FirGenTiiDataJob() {
		super();
		firTiiDataService = (FirTiiDataService)AppContext.getBean("firTiiDataService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirGenTiiDataJob begin...");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date sysdate = sdf.parse(sdf.format(new Date()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(sysdate);
			cal.add(Calendar.DATE, -1);
			Date undate = cal.getTime();
			firTiiDataService.callSpToGenData("A", "", undate, "SYSTEM", "FIR_BATCH_TII_01");
			logger.info("保發中心-住火保批資料-資料產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("保發中心-住火保批資料-資料產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("保發中心-住火保批資料-資料產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirGenTiiDataJob end...");
	}
}

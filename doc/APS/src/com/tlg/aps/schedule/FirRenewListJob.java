package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firGenRenewListService.FirGenRenewListService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public class FirRenewListJob implements Job{
	
	protected Logger logger = Logger.getLogger(FirRenewListJob.class);	
	private FirGenRenewListService firGenRenewListService;
	
	public FirRenewListJob() {
		super();
		firGenRenewListService = (FirGenRenewListService)AppContext.getBean("firGenRenewListService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirRenewListJob begin...");
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, +2);
			String rnYymm = new SimpleDateFormat("yyyyMM").format(cal.getTime());
			firGenRenewListService.runToGenerateList(rnYymm, new Date(), "SYSTEM", "FIR_RENEW_LIST");
			logger.info("住火_每月應續件清單產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("住火_每月應續件清單產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("住火_每月應續件清單產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirRenewListJob end...");
	}
}

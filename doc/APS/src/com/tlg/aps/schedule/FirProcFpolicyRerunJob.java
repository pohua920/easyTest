package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firProcFpolicyRerunService.FirProcFpolicyRerunService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
public class FirProcFpolicyRerunJob implements Job {
	protected Logger logger = Logger.getLogger(FirProcFpolicyRerunJob.class);	
	private FirProcFpolicyRerunService firProcFpolicyRerunService;
	
	public FirProcFpolicyRerunJob() {
		super();
		firProcFpolicyRerunService = (FirProcFpolicyRerunService)AppContext.getBean("firProcFpolicyRerunService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirProcFpolicyRerunJob begin...");
		
		Date sysDate = new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(sysDate);
		c.add(Calendar.DATE, -1);
		sysDate =c.getTime();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
		String underwriteenddate=sdf.format(sysDate);
		underwriteenddate+=" 00:00:00";
		String programId="FIR_PROC_FPOLICY_RERUN";
		String userId="SYSTEM";
		try {
			firProcFpolicyRerunService.rerunFpolicy(new Date(), underwriteenddate, userId, programId);
			logger.info("住火_新核心保單轉入中介檔異常處理成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("住火_新核心保單轉入中介檔異常處理失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("住火_新核心保單轉入中介檔異常處理失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FirProcFpolicyRerunJob end...");
	}
}

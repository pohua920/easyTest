package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firUbNewPolicyService.FirUbBackFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 */
public class FirUbBackFileJob implements Job{

	
	protected Logger logger = Logger.getLogger(FirUbBackFileJob.class);	
	private FirUbBackFileService firUbBackFileService;
	
	public FirUbBackFileJob() {
		super();
		firUbBackFileService = (FirUbBackFileService)AppContext.getBean("firUbBackFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirUbBackFileJob begin...");
		try {
			firUbBackFileService.runToReceiveData("SYSTEM", new Date(), "FIR_AGT_UB_02", "1");
			logger.info("聯邦新件-回饋檔產生作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("聯邦新件-回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("聯邦新件-回饋檔產生作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirUbBackFileJob end...");
	}
}

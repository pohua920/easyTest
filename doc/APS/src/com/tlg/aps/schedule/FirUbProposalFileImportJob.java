package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.firUbNewPolicyService.FirUbProposalFileImportService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 */
public class FirUbProposalFileImportJob implements Job{
	
	protected Logger logger = Logger.getLogger(FirUbProposalFileImportJob.class);	
	private FirUbProposalFileImportService firUbProposalFileImportService;
	
	public FirUbProposalFileImportJob() {
		super();
		firUbProposalFileImportService = (FirUbProposalFileImportService)AppContext.getBean("firUbProposalFileImportService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FirUbProposalFileImportJob begin...");
		try {
			firUbProposalFileImportService.runToImportFile("SYSTEM", new Date(), "FIR_AGT_UB_01");
			logger.info("聯邦新件-要保受理檔接收作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("聯邦新件-要保受理檔接收作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("聯邦新件-要保受理檔接收作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("FirUbProposalFileImportJob end...");
	}
}

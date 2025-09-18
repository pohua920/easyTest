package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.generatePolicyPassbookService.GeneratePolicyPassbookService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/*  mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public class PolicyBatchPassbookJob implements Job{

	protected Logger logger = Logger.getLogger(PolicyBatchPassbookJob.class);	
	private GeneratePolicyPassbookService generatePolicyPassbookService;
	
	public PolicyBatchPassbookJob() {
		super();
		generatePolicyPassbookService = (GeneratePolicyPassbookService)AppContext.getBean("generatePolicyPassbookService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("PolicyBatchPassbookJob begin...");
		try {
			generatePolicyPassbookService.runToGeneratePolicyPassbook("SYSTEM", "OTH_PASSBOOK_01");
			logger.info("保單存摺產生排程作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("保單存摺產生排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("保單存摺產生排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}
		logger.info("PolicyBatchPassbookJob end...");
	}
}

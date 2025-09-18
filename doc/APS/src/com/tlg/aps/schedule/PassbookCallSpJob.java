package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.othBatchPassbookServerce.PassbookCalSpService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public class PassbookCallSpJob implements Job {
	protected Logger logger = Logger.getLogger(PassbookCallSpJob.class);	
	private PassbookCalSpService passbookCalSpService;
	
	public PassbookCallSpJob() {
		super();
		passbookCalSpService = (PassbookCalSpService)AppContext.getBean("passbookCalSpService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("PassbookCallSpJob begin...");
		
		try {
			passbookCalSpService.excuteAndCallSp("SYSTEM", "OTH_PASSBOOK_NC_DATA", "", "", "ALL", "", null);
			logger.info("保單存摺各險寫入中介Table作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("保單存摺各險寫入中介Table作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("保單存摺各險寫入中介Table作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("PassbookCallSpJob end...");
	}
}

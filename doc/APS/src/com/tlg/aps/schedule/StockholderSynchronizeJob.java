package com.tlg.aps.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.stockholderSynchronizeServerce.RunStockholderSynchronizeService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public class StockholderSynchronizeJob implements Job {
	protected Logger logger = Logger.getLogger(StockholderSynchronizeJob.class);	
	private RunStockholderSynchronizeService runStockholderSynchronizeService;
	
	public StockholderSynchronizeJob() {
		super();
		runStockholderSynchronizeService = (RunStockholderSynchronizeService)AppContext.getBean("runStockholderSynchronizeService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("StockholderSynchronizeJob begin...");
		
		try {
			runStockholderSynchronizeService.stockholderDataSynchronize(new Date());
			logger.info("金控利關人資料同步作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("金控利關人資料同步作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("金控利關人資料同步作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}				
		
		logger.info("StockholderSynchronizeJob end...");
	}
}

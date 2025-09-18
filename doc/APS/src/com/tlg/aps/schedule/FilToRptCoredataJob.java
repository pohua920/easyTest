package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.As400FilToRptCoredataService.As400FilToRptCoredataService;
import com.tlg.db2.entity.As400Inrcfil;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;

/** mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B **/
public class FilToRptCoredataJob implements Job {
	protected Logger logger = Logger.getLogger(FilToRptCoredataJob.class);	
	private As400FilToRptCoredataService as400FilToRptCoredataService;
	
	public FilToRptCoredataJob() {
		super();
		as400FilToRptCoredataService = (As400FilToRptCoredataService)AppContext.getBean("as400FilToRptCoredataService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("FilToRptCoredataJob begin...");
		
		try {
			Result result = as400FilToRptCoredataService.excute();
			if(result.getMessage() != null){
				//無資料顯示查無資料，有錯誤則顯示錯誤訊息
				logger.info("已出單資料轉檔收件報備系統作業:"+result.getMessage().toString());
			}
			if(result.getResObject() != null){
				List<As400Inrcfil> as400Inrcfils  = (List<As400Inrcfil>) result.getResObject();
				logger.info("已出單資料轉檔收件報備系統作業成功，共執行"+as400Inrcfils.size()+"筆。");
			}
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("已出單資料轉檔收件報備系統作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("已出單資料轉檔收件報備系統作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("FilToRptCoredataJob end...");
	}
}

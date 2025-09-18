package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.db2.entity.As400Inrcfil;
import com.tlg.db2.service.As400InrcfilService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;

/** mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400程  **/
public class ReiInrcfilJob implements Job {
	protected Logger logger = Logger.getLogger(ReiInrcfilJob.class);	
	private As400InrcfilService as400InrcfilService;
	
	public ReiInrcfilJob() {
		super();
		as400InrcfilService = (As400InrcfilService)AppContext.getBean("as400InrcfilService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("ReiInrcfilJob begin...");
		
		try {
//			Date excuteTime = new Date();
//			SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
//			Calendar c =Calendar.getInstance();
//			c.setTime(excuteTime);
//			c.add(Calendar.MONTH, 2);
//			String rnDate=sdf.format(c.getTime());
			Result result = as400InrcfilService.inrcfilRdmToAs400();
			if(result.getMessage() != null){
				//無資料顯示查無資料，有錯誤則顯示錯誤訊息
				logger.info("再保帳單資料拋轉傳回AS400系統作業:"+result.getMessage().toString());
			}
			if(result.getResObject() != null){
				List<As400Inrcfil> as400Inrcfils  = (List<As400Inrcfil>) result.getResObject();
				logger.info("再保帳單資料拋轉傳回AS400系統作業成功，共執行"+as400Inrcfils.size()+"筆。");
			}
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("再保帳單資料拋轉傳回AS400系統作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("再保帳單資料拋轉傳回AS400系統作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("ReiInrcfilJob end...");
	}
}

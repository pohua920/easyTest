package com.tlg.aps.schedule;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.hasFetMatchFileService.HasFetMatchFileService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;

/** mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 **/
/**
 * 每月
 * @author dp0706
 *
 */
public class HasFetMatchFileMonthJob implements Job {
	protected Logger logger = Logger.getLogger(HasFetMatchFileMonthJob.class);	
	private HasFetMatchFileService hasFetMatchFileService;
	
	public HasFetMatchFileMonthJob() {
		super();
		hasFetMatchFileService = (HasFetMatchFileService)AppContext.getBean("hasFetMatchFileService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("HasFetMatchFileMonthJob begin...");
		String backFileType = "2";//每月作業
		String userId = "SYSTEM";

		try {
			Calendar dataDate = Calendar.getInstance();
			dataDate.add(Calendar.MONTH, -1);//上個月
			Result result = hasFetMatchFileService.runToGenerateFile(backFileType, new Date(), userId, dataDate.getTime());
			if(result.getMessage() != null){
				//無資料顯示查無資料，有錯誤則顯示錯誤訊息
				logger.info("遠傳旅平險姓名生日不一致比對作業(每月):"+result.getMessage().toString());
			}
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("遠傳旅平險姓名生日不一致比對作業(每月)失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("遠傳旅平險姓名生日不一致比對作業(每月)失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("HasFetMatchFileMonthJob end...");
	}
}

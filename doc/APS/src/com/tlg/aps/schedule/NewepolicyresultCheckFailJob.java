package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.vo.NewepolicyVo;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.Result;
import com.tlg.xchg.service.NewepolicyresultService;

/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS)  **/
public class NewepolicyresultCheckFailJob implements Job {
	protected Logger logger = Logger.getLogger(NewepolicyresultCheckFailJob.class);	
	private NewepolicyresultService newepolicyresultService;
	
	public NewepolicyresultCheckFailJob() {
		super();
		newepolicyresultService = (NewepolicyresultService)AppContext.getBean("newepolicyresultService");
	}		
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("NewepolicyresultCheckFailJob begin...");
		
		try {
			Result result = newepolicyresultService.findYdayNewepolicyVo();
			if(result.getMessage()!=null){
				logger.info("電子保單異常檢核--"+result.getMessage());												
			}else{
				List<NewepolicyVo> newepolicyresultErrList = (List<NewepolicyVo>) result.getResObject();				
				if(newepolicyresultErrList!=null){
					newepolicyresultService.sendEmail(newepolicyresultErrList);
					logger.info("電子保單異常檢核寄信成功");				
				}
			}
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("電子保單異常檢核寄信失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("電子保單異常檢核寄信失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.toString());
		}				
		
		logger.info("NewepolicyresultCheckFailJob end...");
	}

	public NewepolicyresultService getNewepolicyresultService() {
		return newepolicyresultService;
	}

	public void setNewepolicyresultService(NewepolicyresultService newepolicyresultService) {
		this.newepolicyresultService = newepolicyresultService;
	}
	
}

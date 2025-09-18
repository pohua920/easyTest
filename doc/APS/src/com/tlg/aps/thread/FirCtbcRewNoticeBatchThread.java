package com.tlg.aps.thread;

import java.util.Date;

import org.apache.log4j.Logger;

import com.tlg.aps.bs.firCtbcRewNoticeService.FirCtbcRewNoticeService;
import com.tlg.aps.vo.FirCtbcRewNoticeBatchVo;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.DateUtils;
import com.tlg.util.UserInfo;

public class FirCtbcRewNoticeBatchThread extends Thread{

	protected Logger logger = Logger.getLogger(FirCtbcRewNoticeBatchThread.class);	
	private FirCtbcRewNoticeService firCtbcRewNoticeService;
	private UserInfo userInfo;
	private FirCtbcRewNoticeBatchVo voObject;
	
	public FirCtbcRewNoticeBatchThread(UserInfo userInfo, FirCtbcRewNoticeBatchVo voObject) {
		super();
		this.userInfo = userInfo;
		this.voObject = voObject;
		firCtbcRewNoticeService = (FirCtbcRewNoticeService)AppContext.getBean("firCtbcRewNoticeService");
	}		
	
	public void run(){
		logger.info("FirCtbcRewNoticeBatchThread begin...");
		String dateStr = DateUtils.getDateString(new Date());
		
		try {
			firCtbcRewNoticeService.policyDataImport(userInfo, voObject);

			logger.info("中信代理投保通知處理作業成功");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("中信代理投保通知處理作業失敗" + dateStr);
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("中信代理投保通知處理作業失敗" + dateStr);
			logger.error(e.getMessage());
		}				
		//sendMail(subject, content);  
		logger.info("FirCtbcRewNoticeBatchThread end...");
	}
}

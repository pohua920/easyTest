package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.thread.ChubbEndorseDataCheckThread;
import com.tlg.aps.vo.ApplicantForEndorseCheckVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.service.ApplicantEndorseService;
import com.tlg.util.AppContext;
import com.tlg.util.CommonFunc;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;

public class ChubbEndorseDataCheckSchedule extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(ChubbEndorseDataCheckSchedule.class);

	public ChubbEndorseDataCheckSchedule() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("ChubbEndorseDataCheckSchedule begin...");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ExecutorService executor = null;
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			 if (jobs != null && !jobs.isEmpty()) {
				 for (JobExecutionContext job : jobs) {
					 if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						 logger.info("There's another ChubbEndorseDataCheckSchedule instance running, : " + this);
						 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
						 Mailer mailer = new Mailer();
						 String smtpServer = configUtil.getString("smtp_host");
						 String userName = configUtil.getString("smtp_username");
						 String password = configUtil.getString("smtp_pwd");
						 String contentType = "text/html; charset=utf-8";
						 String auth = "smtp";
						 String subject = "行動裝置險 - 線下批單資料檢核作業暫停執行";
						 String from = configUtil.getString("mail_from_address");
						 String to = configUtil.getString("policyTransferPauseNotifyMail");
						 String cc = "";
						 String mailBody = "時間：" + dateFormat.format(new Date()) + "<br><br>系統偵測前次排程尚在執行中，故此次作業暫停執行。";
						 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
						 
						 return;
					 }
				 }
			 }
			 
			executor = Executors.newCachedThreadPool();

			// Cast the object to its class type
			ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

			// Stats before tasks execution
			// System.out.println("Core threads: " + pool.getCorePoolSize());
			// System.out.println("Largest executions: " + pool.getLargestPoolSize());
			// System.out.println("Maximum allowed threads: " + pool.getMaximumPoolSize());
			// System.out.println("Current threads in pool: " + pool.getPoolSize());
			// System.out.println("Currently executing threads: " + pool.getActiveCount());
			// System.out.println("Total number of threads(ever scheduled): " + pool.getTaskCount());

			ApplicantEndorseService applicantEndorseService = (ApplicantEndorseService) AppContext.getBean("applicantEndorseService");

			Result result = applicantEndorseService.findEndorseForCheck();
			if (result.getResObject() != null) {
				List<ApplicantForEndorseCheckVo> list = (List<ApplicantForEndorseCheckVo>) result.getResObject();
				List<List<ApplicantForEndorseCheckVo>> tempList = CommonFunc.averageAssign(list, 15); //預設一次3000筆分15組 
				for (int i = 1; i <= tempList.size(); i++) {
					List<ApplicantForEndorseCheckVo> inList = tempList.get(i - 1);
					executor.submit(new ChubbEndorseDataCheckThread(String.valueOf(i),	inList));
				}
			}
			// 判斷pool中的執行緒是否都執行完成，執行完成才能shutdown結束排程，目前設定600秒檢查一次
			while (true) {
				int executeCount = pool.getActiveCount();
				logger.info(">>> 行動裝置險 - 線下批單資料檢核作業目前執行中的執行緒 ：" + executeCount);
				if (executeCount == 0) {
					executor.shutdown();
					break;
				}
				Thread.sleep(600000); // 600秒
			}
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn(">>> 行動裝置險 - 線下批單資料檢核作業" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(">>> 行動裝置險 - 線下批單資料檢核作業" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		}finally{
			if(executor != null){
				 executor.shutdown();
			}
		}
		logger.info("ChubbEndorseDataCheckSchedule end...");
	}
}

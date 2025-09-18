package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.thread.MiDataToFetMobilePolicyThread;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.Application;
import com.tlg.msSqlMob.service.ApplicationService;
import com.tlg.util.AppContext;
import com.tlg.util.CommonFunc;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;

/** mantis：MOB0014，處理人員：BJ085，需求單編號：MOB0014 保批單資料寫入匯入核心中介資料表作業 */
public class TransferDataToFetMobilePolicySchedule extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(TransferDataToFetMobilePolicySchedule.class);

	public TransferDataToFetMobilePolicySchedule() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("TransferDataToFetMobilePolicySchedule begin...");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ExecutorService executor = null;
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			 if (jobs != null && !jobs.isEmpty()) {
				 for (JobExecutionContext job : jobs) {
					 if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						 logger.info("There's another TransferDataToFetMobilePolicySchedule instance running, : " + this);
						 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
						 Mailer mailer = new Mailer();
						 String smtpServer = configUtil.getString("smtp_host");
						 String userName = configUtil.getString("smtp_username");
						 String password = configUtil.getString("smtp_pwd");
						 String contentType = "text/html; charset=utf-8";
						 String auth = "smtp";
						 String subject = "行動裝置險 - 保批單資料寫入匯入核心中介資料表作業暫停執行";
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
//			System.out.println("Largest executions: " + pool.getLargestPoolSize());
//			System.out.println("Maximum allowed threads: " + pool.getMaximumPoolSize());
//			System.out.println("Current threads in pool: " + pool.getPoolSize());
//			System.out.println("Currently executing threads: " + pool.getActiveCount());
//			System.out.println("Total number of threads(ever scheduled): " + pool.getTaskCount());

			ApplicationService applicationService = (ApplicationService) AppContext.getBean("applicationService");

			Map<String, String> params = new HashMap<>();
			params.put("chubbReturnStatus", "1");
			params.put("dataStatus", "READY");
			Result result = applicationService.findApplicationByParams(params);
			
			if (result.getResObject() != null) {
				List<Application> applicationList = (List<Application>) result.getResObject();
				List<List<Application>> tempList = CommonFunc.averageAssign(applicationList, 15);
				for (int i = 1; i <= tempList.size(); i++) {
					List<Application> inList = tempList.get(i - 1);
					executor.submit(new MiDataToFetMobilePolicyThread(inList,String.valueOf(i)));
				}
			}
			//判斷pool中的執行緒是否都執行完成，執行完成才能shutdown結束排程，目前設定600秒檢查一次
			while(true){
				int executeCount = pool.getActiveCount();
				logger.info(">>> 保批單資料寫入匯入核心中介資料表作業目前執行中的執行緒 ：" + executeCount);
				if(executeCount == 0){
					executor.shutdown();
					break;
				}
				Thread.sleep(540000); //600秒
			}
			logger.info(">>> 保批單資料寫入匯入核心中介資料表排程作業完成 ：" + dateFormat.format(new Date()));
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn(">>> 保批單資料寫入匯入核心中介資料表排程作業失敗" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(">>> 保批單資料寫入匯入核心中介資料表作業排程失敗" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		}finally{
//			if(executor != null){
//				 executor.shutdown();
//			}
		}
		logger.info("TransferDataToFetMobilePolicySchedule end...");
	}
}

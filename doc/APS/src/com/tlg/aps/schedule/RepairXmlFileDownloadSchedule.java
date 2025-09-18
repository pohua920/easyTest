package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;

/** mantis：，處理人員：BJ016，需求單編號：下載全虹完修檔 */
public class RepairXmlFileDownloadSchedule implements Job{

	protected Logger logger = Logger.getLogger(RepairXmlFileDownloadSchedule.class);	
	private RunFetPolicyService runFetPolicyService;
	
	public RepairXmlFileDownloadSchedule() {
		super();
		runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}		
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("RepairXmlFileDownloadSchedule begin...");
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			 if (jobs != null && !jobs.isEmpty()) {
				 for (JobExecutionContext job : jobs) {
					 if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						 logger.info("There's another instance running, : " + this);
						 
						 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
						 Mailer mailer = new Mailer();
						 String smtpServer = configUtil.getString("smtp_host");
						 String userName = configUtil.getString("smtp_username");
						 String password = configUtil.getString("smtp_pwd");
						 String contentType = "text/html; charset=utf-8";
						 String auth = "smtp";
						 String subject = "行動裝置險-下載全虹完修檔作業暫停執行";
						 String from = configUtil.getString("mail_from_address");
						 String to = "ce035@ctbcins.com";
						 String cc = "";
						 StringBuilder sb = new StringBuilder();
						 sb.append("<p>時間:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")+"</p>");
						 sb.append("<p>系統偵測前次排程尚在執行中，</p>");
						 sb.append("<p>故此次作業暫停執行。</p>");
						 String mailBody = sb.toString();
						 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
						 return;
					 }
				 }
			 }
			 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strNowDate = sdf.format(new Date());
			runFetPolicyService.downloadRepairXmlFile(strNowDate);
			logger.info("下載全虹完修檔----完成");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("下載全虹完修檔作業失敗");
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("下載全虹完修檔作業失敗");
			logger.error(e.getMessage());
		}				
		logger.info("RepairXmlFileDownloadSchedule end...");
	}
}

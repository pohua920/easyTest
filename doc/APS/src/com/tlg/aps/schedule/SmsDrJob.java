package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.smsService.SmsService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;

public class SmsDrJob extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(SendSmsJob.class);
	
	private SmsService smsService;

	public SmsDrJob() {
		super();
		smsService = (SmsService)AppContext.getBean("smsService");
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("SmsDrJob(簡訊DR web service 排程) begin...");
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
						 String subject = "簡訊Dr web service 排程重複執行(前一次未執行完)通知，請儘快處理～";
						 String from = configUtil.getString("mail_from_address");
						 String to = configUtil.getString("sms_delay_notify_email");
						 String cc = "";
						 String mailBody = "";
						 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
						 
						 return;
					 }
				 }
			 }

			 smsService.queryDeliverReport();
			 
			 logger.info("SmsDrJob(簡訊DR web service 排程)作業完成");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("SmsDrJob(簡訊DR web service 發送排程)作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("SmsDrJob(簡訊DR web service 發送排程)作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("SmsDrJob(簡訊DR web service 發送排程) end...");
	}
}

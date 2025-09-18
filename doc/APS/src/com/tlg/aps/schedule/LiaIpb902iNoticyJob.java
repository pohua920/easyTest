package com.tlg.aps.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.liaAnnounceService.AnnounceService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.DateUtils;
import com.tlg.util.Mailer;

public class LiaIpb902iNoticyJob extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(LiaIpb902iNoticyJob.class);

	public LiaIpb902iNoticyJob() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("LiaIpb902iNoticyJob begin...");
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
						 String subject = "IPB902I呼叫CWP公會通報排程重複執行(前一次未執行完)通知，請儘快處理～";
						 String from = configUtil.getString("mail_from_address");
						 String to = configUtil.getString("toAssoc_mail_to_address");
						 String cc = "";
						 String mailBody = "";
						 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
						 
						 return;
					 }
				 }
			 }
			 
			AnnounceService announceService = (AnnounceService) AppContext.getBean("announceService");
			announceService.ipb902iService();

			logger.info("IPB902I傳送至公會通報排程作業完成");
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn("IPB902I傳送至公會通報排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("IPB902I傳送至公會通報排程作業失敗" + DateUtils.getDateString(new Date()));
			logger.error(e.getMessage());
		}
		logger.info("LiaIpb902iNoticyJob end...");
	}
}

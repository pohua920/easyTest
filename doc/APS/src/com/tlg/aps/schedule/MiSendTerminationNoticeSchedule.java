package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.miTerminationNoticeService.MiTerminationNoticeService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;

public class MiSendTerminationNoticeSchedule extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(MiSendTerminationNoticeSchedule.class);

	public MiSendTerminationNoticeSchedule() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("MiSendTerminationNoticeSchedule begin...");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			 if (jobs != null && !jobs.isEmpty()) {
				 for (JobExecutionContext job : jobs) {
					 if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						 logger.info("There's another MiSendTerminationNoticeSchedule instance running, : " + this);
						 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
						 Mailer mailer = new Mailer();
						 String smtpServer = configUtil.getString("smtp_host");
						 String userName = configUtil.getString("smtp_username");
						 String password = configUtil.getString("smtp_pwd");
						 String contentType = "text/html; charset=utf-8";
						 String auth = "smtp";
						 String subject = "行動裝置險 - 終止通知書簡訊發送";
						 String from = configUtil.getString("mail_from_address");
						 String to = configUtil.getString("policyTransferPauseNotifyMail");
						 String cc = "";
						 String mailBody = "時間：" + dateFormat.format(new Date()) + "<br><br>系統偵測前次排程尚在執行中，故此次作業暫停執行。";
						 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
						 
						 return;
					 }
				 }
			 }

			MiTerminationNoticeService miTerminationNoticeService = (MiTerminationNoticeService) AppContext.getBean("miTerminationNoticeService");
			miTerminationNoticeService.send();
			
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn(">>> 行動裝置險 - 終止通知書簡訊" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(">>> 行動裝置險 - 終止通知書簡訊" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("MiSendTerminationNoticeSchedule end...");
	}
}

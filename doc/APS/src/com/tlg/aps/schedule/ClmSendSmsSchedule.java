package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.clmSmsService.ClmSmsService;
import com.tlg.exception.SystemException;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;

public class ClmSendSmsSchedule extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(ClmSendSmsSchedule.class);

	public ClmSendSmsSchedule() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("ClmSendSmsSchedule begin...");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			 if (jobs != null && !jobs.isEmpty()) {
				 for (JobExecutionContext job : jobs) {
					 if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						 logger.info("There's another ClmSendSmsSchedule instance running, : " + this);
						 ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
						 Mailer mailer = new Mailer();
						 String smtpServer = configUtil.getString("smtp_host");
						 String userName = configUtil.getString("smtp_username");
						 String password = configUtil.getString("smtp_pwd");
						 String contentType = "text/html; charset=utf-8";
						 String auth = "smtp";
						 String subject = "理賠簡訊發送";
						 String from = configUtil.getString("mail_from_address");
						 String to = configUtil.getString("mail_to_clmSms_address");
						 String cc = "";
						 String mailBody = "時間：" + dateFormat.format(new Date()) + "<br><br>系統偵測前次排程尚在執行中，故此次作業暫停執行。";
						 mailer.sendmail(smtpServer, contentType, subject, from,	"", to, "", cc, "", "", "", mailBody, auth, userName, password);
						 
						 return;
					 }
				 }
			 }

			 ClmSmsService clmSmsBsService = (ClmSmsService) AppContext.getBean("clmSmsBsService");
			 //產生PDF
			 clmSmsBsService.createPdf();
			 //發送簡訊
			 clmSmsBsService.sendSms();
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn(">>> 理賠簡訊發送" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(">>> 理賠簡訊發送" + dateFormat.format(new Date()));
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("ClmSendSmsSchedule end...");
	}
}

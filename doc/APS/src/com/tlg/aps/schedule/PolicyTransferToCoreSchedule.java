package com.tlg.aps.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.thread.MiPolicyToCoreThread;
import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.msSqlMob.service.FetMobilePolicyService;
import com.tlg.util.AppContext;
import com.tlg.util.CommonFunc;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Result;

public class PolicyTransferToCoreSchedule extends QuartzJobBean {

	protected Logger logger = Logger.getLogger(PolicyTransferToCoreSchedule.class);

	public PolicyTransferToCoreSchedule() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("PolicyTransferToCoreSchedule begin...");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程 START*/
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			if (jobs != null && !jobs.isEmpty()) {
				for (JobExecutionContext job : jobs) {
					if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						logger.info("There's another PolicyTransferToCoreSchedule instance running, : " + this);
						String subject = "行動裝置險 - 保批單資料匯入核心系統作業暫停執行";
						String mailBody = "時間：" + dateFormat.format(new Date()) + "<br><br>系統偵測前次排程尚在執行中，故此次作業暫停執行。";
						/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 匯入核心排程發信告知結果、修正手機險排程時間、修正保批單號資料型態、洗錢條件檢核補上批單號、遠傳資料批次下載使用者改ce035 */
						this.sendMail(subject, mailBody);
						return;
					}
				}
			}

			FetMobilePolicyService fetMobilePolicyService = (FetMobilePolicyService) AppContext.getBean("fetMobilePolicyService");
			
			Result result = fetMobilePolicyService.findPolicyNoByWait();
			RunFetPolicyService runFetPolicyService = (RunFetPolicyService) AppContext.getBean("runFetPolicyService");
			String startDateTime = dateFormat.format(new Date());
			int policyNoListSize = 0;
			if(result != null && result.getResObject() != null) {
				List<FetMobilePolicy> policyNolist =(List<FetMobilePolicy>)result.getResObject();
				if(policyNolist != null && policyNolist.size() > 0){
					policyNoListSize = policyNolist.size();
					ArrayList<String> errorList = new ArrayList<String>();  // 組合時用字串分隔 → TRANSACTION_ID,POLICY_NO,ENDORSE_NO,備註
					Result transferResult;
					/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 START*/
					PrpinsAgentRespVo estorePrpinsAgentRespVo = null;
					String handlerIdentifyNumber = "";
					for(FetMobilePolicy fmp : policyNolist) {
						if(EnumMobileDataSrc.ESTORE.getCode().equals(fmp.getDataSrc())) {
							handlerIdentifyNumber = StringUtils.isNotBlank(fmp.getHandlerIdentifyNumber()) ? fmp.getHandlerIdentifyNumber() : "AA1C000002";
							break;
						}
					}
					if(StringUtils.isNotBlank(handlerIdentifyNumber)) {
						estorePrpinsAgentRespVo = runFetPolicyService.prpinsAgentQuery(handlerIdentifyNumber);
					}
					for(FetMobilePolicy tmp : policyNolist){
						transferResult = runFetPolicyService.transferMiPolicyToCore(tmp, estorePrpinsAgentRespVo);
						/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 END*/
						if(transferResult != null && transferResult.getResObject() != null) {
							boolean check = (Boolean)transferResult.getResObject();
							if(!check) {
								errorList.add(transferResult.getMessage().getMessageString());
							}
						}
					}
					if(errorList.size() > 0){
						runFetPolicyService.sendErrorMail(errorList, startDateTime, "PolicyTransferToCoreSchedule", "行動裝置險 - 資料匯入核心系統失敗件通知");
					}
				}
			}
			String endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String subject = "行動裝置險 - 保批單資料匯入核心系統排程作業完成";
			Map<String, String>  params = new HashMap<String, String>();
			params.put("status", "FINISH");
			params.put("modifiedTimeLatterThan", startDateTime);
			int finishCount = fetMobilePolicyService.countFetMobilePolicy(params);
			String mailBody = "排程開始:" + startDateTime + ", 排程結束:"+endDateTime +"<br>distinct保單總筆數:"+policyNoListSize+", FINISH筆數:"+finishCount;
			this.sendMail(subject, mailBody);
		} catch (SystemException e) {
			e.printStackTrace();
			logger.warn(">>> 保批單資料匯入核心系統排程作業失敗" + dateFormat.format(new Date()));
			logger.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(">>> 保批單資料匯入核心系統排程失敗" + dateFormat.format(new Date()));
			logger.info(e.getMessage());
		} finally {
			
		}
		/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程 END*/
		logger.info("PolicyTransferToCoreSchedule end...");
	}
	/** mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 匯入核心排程發信告知結果、修正手機險排程時間、修正保批單號資料型態、洗錢條件檢核補上批單號、遠傳資料批次下載使用者改ce035 */
	private void sendMail(String subject, String mailBody) {
		ConfigUtil configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		Mailer mailer = new Mailer();
		String smtpServer = configUtil.getString("smtp_host");
		String userName = configUtil.getString("smtp_username");
		String password = configUtil.getString("smtp_pwd");
		String contentType = "text/html; charset=utf-8";
		String auth = "smtp";
		String from = configUtil.getString("mail_from_address");
		String to = configUtil.getString("policyTransferPauseNotifyMail");
		String cc = "";
		try {
			mailer.sendmail(smtpServer, contentType, subject, from, "", to, "", cc, "", "", "", mailBody, auth, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

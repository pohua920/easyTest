package com.tlg.aps.bs.firBatchSendmailService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBatchSendmailService.BatchSendmailService;
import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.entity.FirBatchSendmailDetail;
import com.tlg.prpins.entity.FirBatchSendmailOk;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.prpins.service.FirBatchSendmailDetailService;
import com.tlg.prpins.service.FirBatchSendmailOkService;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.util.UserInfo;
import com.tlg.xchg.entity.Newepolicymain;
import com.tlg.xchg.service.NewepolicymainService;


@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class BatchSendmailServiceImpl implements BatchSendmailService {
	
	private static final Logger logger = Logger.getLogger(BatchSendmailServiceImpl.class);
	private ConfigUtil configUtil;
	
	private FirBatchSendmailDetailService firBatchSendmailDetailService;
	private FirBatchSendmailOkService firBatchSendmailOkService;
	private NewepolicymainService newepolicymainService;
	private FirBatchInfoService firBatchInfoService;
	
	@SuppressWarnings({"unchecked" })
	@Override
	public Result batchSendmail(UserInfo userInfo) throws SystemException, Exception {
		logger.info("batchSendmail start...");
		Result result = new Result();
		
		//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
		Map<String,String> brnoMap = new HashMap<>();
		try {
			logger.info("batchSendmail findFirBatchSendmailDetail start...");
			Map<String,String> params = new HashMap<>();
			
			/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
			params.put("prgId", "FIR_APS_EPDF_BATCH");
			result = firBatchInfoService.findFirBatchInfoByUK(params);
			if(result.getResObject()!=null) {
				FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
				if(firBatchInfo.getMailTo().equals("N")) {
					result.setResObject(null);
					Message message = new Message();
					message.setMessageString("FIR_BATCH_INFO設定檔設定為排程暫停執行。");
					result.setMessage(message);
					return result;
				}
			}
			/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
			
			params.clear();
			//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
			//params.put("riskcode", "F02");
			params.put("emaildateNull", "Y");
			//mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件
			params.put("smsdatedateNull", "Y");
			result = this.firBatchSendmailDetailService.findFirBatchSendmailDetailByParams(params);
			if(result != null && result.getResObject() != null) {
				logger.info("batchSendmail newepolicymainService selectForFirBatchSendmail start...");
				/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start*/
				//mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件
				Map<String, Integer> countMap = selectAndUpdateSendtime(result, brnoMap);
				logger.info("batchSendmail insertFirBatchSendmailOk start...");
				Set keySet = brnoMap.keySet();
		        Iterator it = keySet.iterator();
		        while(it.hasNext()){
		            String riskcode = (String) it.next();
		            String brno = brnoMap.get(riskcode);
		            FirBatchSendmailOk firBatchSendmailOk = new FirBatchSendmailOk();
					firBatchSendmailOk.setBrno(brno);
					firBatchSendmailOk.setEdatacount(new BigDecimal(countMap.get(riskcode)));
					firBatchSendmailOk.setRiskcode(riskcode);
					this.firBatchSendmailOkService.insertFirBatchSendmailOk(firBatchSendmailOk);
		        }
		        /*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end*/
			}
			
			/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
			//重新查詢有email但無寄送時間且有手機號碼但無簡訊時間的資料
			params = new HashMap<>();
			//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
			//params.put("riskcode", "F02");
			params.put("emailAndMobileNotNull", "Y");
			result = this.firBatchSendmailDetailService.findFirBatchSendmailDetailByParams(params);
			if(result != null && result.getResObject() != null) {
				logger.info("batchSendmail newepolicymainService selectForFirBatchSendmail again start...");
				//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
				selectAndUpdateSendtime(result,brnoMap);
			}
			
			/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start*/
			//最後依險種再查詢一次有email但無寄送時間且有手機號碼但無簡訊時間的資料，寄送異常通知Email
			result = firBatchSendmailDetailService.findRiskcodeForNoticeEmail();
			if(result != null && result.getResObject() != null) {
				List<WriteForBatchSendmailVo> riskcodeList = (List<WriteForBatchSendmailVo>)result.getResObject();
				params.clear();
				params.put("emailAndMobileAllNull", "Y");
				params.put("orderBy", "Y");
				String riskcode = "";
				for(WriteForBatchSendmailVo writeForBatchSendmailVo : riskcodeList) {
					riskcode = writeForBatchSendmailVo.getRiskcode();
					params.put("riskcode", riskcode);
					result = this.firBatchSendmailDetailService.findFirBatchSendmailDetailByParams(params);
					if(result != null && result.getResObject() != null) {
						List<FirBatchSendmailDetail> resultList =  (List<FirBatchSendmailDetail>) result.getResObject();
						sendEmail(resultList, riskcode);
					}
				}
			}
			/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end*/
			/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
			
			result = new Result();
			result.setResObject(Boolean.TRUE);
			logger.info("batchSendmail finish...");
		}catch(Exception e) {
			//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
			logger.warn("APS電子保單發送結果確認作業失敗");
			logger.error(e.getMessage());
			result = new Result();
			result.setResObject(Boolean.FALSE);
			Message message = new Message();
			message.setMessageString(e.getMessage());
			result.setMessage(message);
		}
		
		return result;
	}
	
	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start*/
	/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
	@SuppressWarnings("unchecked")
	private Map selectAndUpdateSendtime(Result result, Map<String, String> brnoMap) throws Exception {
		int dataCount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar sysdate = Calendar.getInstance();
		sysdate.setTime(new Date());
		
		Map<String, Integer> countMap = new HashMap<>();
		Map<String,String> params = new HashMap<>();
		List<FirBatchSendmailDetail> resultList = (List<FirBatchSendmailDetail>)result.getResObject();
		List<Newepolicymain> newepolicymainResult;
		Newepolicymain newepolicymain;
		
		String riskcode;
		for(FirBatchSendmailDetail entity : resultList) {
			params.put("policyno", entity.getPolicyno());
			result = this.newepolicymainService.selectForFirBatchSendmail(params);
			riskcode = entity.getRiskcode();
			if(result != null && result.getResObject() != null) {
				if(!brnoMap.containsKey(riskcode)) {
					//因時間只給到秒，多個險種迴圈時會取到相同的brno，改為每次取brno都多加一秒
					sysdate.add(Calendar.SECOND, 1);
					brnoMap.put(riskcode, sdf.format(sysdate.getTime()));
				}
				newepolicymainResult = (List<Newepolicymain>)result.getResObject();
				newepolicymain = newepolicymainResult.get(0);
				entity.setEmaildate(newepolicymain.getEmaildate());
				entity.setSmsdatedate(newepolicymain.getSmsdate());
				entity.setApplicantemail(newepolicymain.getApplicantemail());
				entity.setMobile(newepolicymain.getMobile());
				entity.setBrno(brnoMap.get(riskcode));
				this.firBatchSendmailDetailService.updateFirBatchSendmailDetail(entity);
				if(!countMap.containsKey(riskcode)) {
					dataCount = 0;
					countMap.put(riskcode, dataCount);
				}else {
					dataCount = countMap.get(riskcode);
				}
				countMap.put(riskcode, dataCount+1);
			}
		}
		return countMap;
	}
	/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end*/
	
	/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
	//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
	private void sendEmail(List<FirBatchSendmailDetail> resultList, String riskcode) throws SystemException, Exception {
		Mailer mailer = new Mailer();
		/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start*/
		//依險種分別查寄件標題、收件者(因要分險種查詢寄送信件主旨、寄件者，所以寫死目前有的險種)
		Map<String,String> subjectMap = new HashMap<>();
		subjectMap.put("A01", "CAR_APS_EPDF_REWRITE");
		subjectMap.put("B01", "CAR_APS_EPDF_REWRITE");
		subjectMap.put("F02", "FIR_APS_EPDF_REWRITE");
		subjectMap.put("TA", "TA_APS_EPDF_REWRITE");
		/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end*/
		
		Map<String,Object> params = new HashMap<>();
		//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
		params.put("prgId", subjectMap.get(riskcode));
		Result result = this.firBatchInfoService.findFirBatchInfoByUK(params);
		if(result.getResObject() != null) {
			logger.info("batchSendmail sendEmail start...");
			FirBatchInfo firBatchInfo = (FirBatchInfo) result.getResObject();
			String subject = firBatchInfo.getMailSubject();
			String mailTo = firBatchInfo.getMailTo();
			StringBuilder sb = new StringBuilder();
			//mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)
			sb.append("<p>日期："+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + "&nbsp;&nbsp;&nbsp;&nbsp; " +getRiskname(riskcode)+ "電子保單寄送異常清單</p>");
			sb.append("<p>註: 未有寄送時間的保單，有可能日後會更新。</p>");
			sb.append("<p>註: 異常件定義為有email但是無寄送紀錄，有手機號碼但是無簡訊紀錄。無寄送時間的保單，也有可能是異常。</p>");
			sb.append("<table border=1 style='border-collapse: collapse;'>");
			sb.append("<tr bgcolor='#70bbd9' align='center'>");
			sb.append("<td>保單號碼</td>");
			sb.append("<td>轉要保單日期</td>");
			sb.append("<td>使用者EMAIL</td>");
			sb.append("<td>EMAIL寄送時間</td>");
			sb.append("<td>手機號碼</td>");
			sb.append("<td>簡訊寄送時間</td>");
			sb.append("</tr>");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			String emaildate = "";
			String smsdatedate = "";
			for(FirBatchSendmailDetail firBatchSendmailDetail : resultList) {
				sb.append("<tr align='center'>");
				sb.append("<td>" + firBatchSendmailDetail.getPolicyno() + "</td>");
				sb.append("<td>" + firBatchSendmailDetail.getUnderwriteenddate() + "</td>");
				sb.append("<td>" + StringUtil.nullToSpace(firBatchSendmailDetail.getApplicantemail()) + "</td>");
				if(firBatchSendmailDetail.getEmaildate()!=null) {
					emaildate = sdf.format(firBatchSendmailDetail.getEmaildate());
				}
				sb.append("<td>" + emaildate + "</td>");
				sb.append("<td>" + StringUtil.nullToSpace(firBatchSendmailDetail.getMobile()) + "</td>");
				if(firBatchSendmailDetail.getSmsdatedate()!=null) {
					smsdatedate = sdf.format(firBatchSendmailDetail.getSmsdatedate());
				}
				sb.append("<td>" + smsdatedate + "</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", 
					"", mailTo, "", "", "", "", "", sb.toString(), "smtp","newims", "2012newims");
		}
		
	}
	/* mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
	
	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start*/
	public String getRiskname(String riskcode) {
		String riskname = "";
		if("A01".equals(riskcode)) {
			riskname = "任意車險";
		}
		if("B01".equals(riskcode)) {
			riskname = "強制車險";
		}
		if("F02".equals(riskcode)) {
			riskname = "住火";
		}
		if("TA".equals(riskcode)) {
			riskname = "旅平險";
		}
		return riskname;
	}
	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end*/

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}


	public FirBatchSendmailDetailService getFirBatchSendmailDetailService() {
		return firBatchSendmailDetailService;
	}


	public void setFirBatchSendmailDetailService(FirBatchSendmailDetailService firBatchSendmailDetailService) {
		this.firBatchSendmailDetailService = firBatchSendmailDetailService;
	}


	public FirBatchSendmailOkService getFirBatchSendmailOkService() {
		return firBatchSendmailOkService;
	}


	public void setFirBatchSendmailOkService(FirBatchSendmailOkService firBatchSendmailOkService) {
		this.firBatchSendmailOkService = firBatchSendmailOkService;
	}


	public NewepolicymainService getNewepolicymainService() {
		return newepolicymainService;
	}


	public void setNewepolicymainService(NewepolicymainService newepolicymainService) {
		this.newepolicymainService = newepolicymainService;
	}

	public FirBatchInfoService getFirBatchInfoService() {
		return firBatchInfoService;
	}

	public void setFirBatchInfoService(FirBatchInfoService firBatchInfoService) {
		this.firBatchInfoService = firBatchInfoService;
	}

}

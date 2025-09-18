package com.tlg.aps.bs.mobclaimService.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.mobclaimService.MobReviewService;
import com.tlg.prpins.service.FetclaimmainService;
import com.tlg.prpins.service.MiClaimSpService;
import com.tlg.util.AppContext;
import com.tlg.util.ConfigUtil;
import com.tlg.util.Mailer;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MobReviewServiceImpl implements MobReviewService {

	private static final Logger logger = Logger.getLogger(MobReviewServiceImpl.class);
	private MiClaimSpService miClaimSpService;
	private FetclaimmainService fetclaimmainService;
	private ConfigUtil configUtil;
	
	@Override
	public Result reviewClaimData(String userId, String dataDate) throws Exception {
		Map<String, Object> params = new HashMap<>();
		int outResult = 0;
		
		//執行前先查詢此次要處理的筆數
		params.put("wda00",dataDate);
		params.put("nclaimNull","N");
		params.put("wda61","4");
		int dataCount = fetclaimmainService.countFetclaimmain(params);
		
		params.clear();
		params.put("dataYm", dataDate);
		params.put("wda55Var", userId);
		try {
			Map<String, Object> spResultMap = miClaimSpService.runSpFetmispTonewclaim(params);
			outResult = (Integer)spResultMap.get("outResult");
			if(0 != outResult) {
				sendEmail(outResult,(String) spResultMap.get("outErrmsg"),0);
				return this.getReturnResult("資料年月" + dataDate + ":執行審核作業SP失敗，請聯絡資訊人員!");
			}
		}catch(Exception e) {
			logger.error("行動裝置險理賠資料審核作業:執行審核作業SP發生錯誤:"+e);
			e.printStackTrace();
			return this.getReturnResult("資料年月" + dataDate + ":執行審核作業SP發生錯誤，請聯絡資訊人員!");
		}
		
		sendEmail(outResult,"",dataCount);
		return this.getReturnResult("資料年月" + dataDate + ":資料審核完成!");
	}
	
	private String sendEmail(int outResult, String outErrmsg, int dataCount) {
		Mailer mailer = new Mailer();
		configUtil = (ConfigUtil) AppContext.getBean("configUtil");
		try {
			//mantis：CLM0237，處理人員： DP0713 ，需求單編號：APS-行動裝置險UAT信件主旨新增辨識文字
			String subject =  "【SIT-行動裝置理賠轉檔通知】核心資料轉入-";
			StringBuilder sb = new StringBuilder();
			if(1 == outResult) { 
				subject =  subject + "失敗";
				sb.append("<p>異常:"+outErrmsg+"</p>");
			}else {
				subject =  subject + "成功";
				sb.append("<p>成功:資料轉入筆數"+dataCount+"筆</p>");
			}
			String mailTo = configUtil.getString("claimMobReview_mailTo");
			String mailCc = configUtil.getString("claimMobReview_mailCc");

			
			mailer.sendmail("mail.ctbcins.com", "text/html; charset=utf-8", subject, "newims@ctbcins.com", "", mailTo,
					"", mailCc, "", "", "", sb.toString(), "smtp", "newims", "2012newims");
		} catch (Exception e) {
			logger.error("行動裝置險理賠資料審核作業:寄送通知Email發生錯誤:", e);
			e.printStackTrace();
		}
		return "";
	}

	private Result getReturnResult(String msg) {
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}

	public MiClaimSpService getMiClaimSpService() {
		return miClaimSpService;
	}

	public void setMiClaimSpService(MiClaimSpService miClaimSpService) {
		this.miClaimSpService = miClaimSpService;
	}

	public FetclaimmainService getFetclaimmainService() {
		return fetclaimmainService;
	}

	public void setFetclaimmainService(FetclaimmainService fetclaimmainService) {
		this.fetclaimmainService = fetclaimmainService;
	}

	public ConfigUtil getConfigUtil() {
		return configUtil;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
}

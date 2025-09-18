package com.tlg.aps.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.bs.fetPolicyService.RunFetPolicyService;
import com.tlg.aps.vo.PrpinsAgentRespVo;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMobileDataSrc;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.util.AppContext;
import com.tlg.util.Result;

public class MiPolicyToCoreThread implements Runnable {

	protected Logger logger = Logger.getLogger(MiPolicyToCoreThread.class);
	private RunFetPolicyService runFetPolicyService;
	private List<FetMobilePolicy> policyNolist;
	private String groupNo;
	
	public MiPolicyToCoreThread(String groupNo, List<FetMobilePolicy> policyNolist){
		this.policyNolist = policyNolist;
		this.groupNo = groupNo;
		this.runFetPolicyService = (RunFetPolicyService)AppContext.getBean("runFetPolicyService");
	}

	public void run() {
		try {
			logger.debug("MiPolicyToCoreThread START： groupNo - " + this.groupNo + "," +new Date() + ", this.policyNolist.size() = " + this.policyNolist.size());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String executeDate = dateFormat.format(new Date());
			
			if(policyNolist != null && policyNolist.size() > 0){
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
					estorePrpinsAgentRespVo = this.runFetPolicyService.prpinsAgentQuery(handlerIdentifyNumber);
				}
				for(FetMobilePolicy tmp : policyNolist){
					transferResult = this.runFetPolicyService.transferMiPolicyToCore(tmp, estorePrpinsAgentRespVo);
					/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 END*/
					if(transferResult != null && transferResult.getResObject() != null) {
						boolean check = (Boolean)transferResult.getResObject();
						if(!check) {
							errorList.add(transferResult.getMessage().getMessageString());
						}
					}
				}
				if(errorList.size() > 0){
					this.runFetPolicyService.sendErrorMail(errorList, executeDate, groupNo, "行動裝置險 - 資料匯入核心系統失敗件通知");
				}
			}else{
				logger.debug("this.policyNolist is null ，呼叫核心web service 執行結束");
			}
			
			logger.debug("MiPolicyToCoreThread END： groupNo - " + this.groupNo + "," +new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


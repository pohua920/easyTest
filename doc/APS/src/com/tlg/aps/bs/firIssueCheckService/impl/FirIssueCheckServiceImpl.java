package com.tlg.aps.bs.firIssueCheckService.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firIssueCheckService.FirIssueCheckService;
import com.tlg.aps.bs.firIssueCheckService.IssueCheckService;
import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.aps.webService.firRuleService.client.RuleCheckService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirTmpDatacheck;
import com.tlg.prpins.service.FirTmpDatacheckService;
import com.tlg.util.Message;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;
import com.tlg.util.WebserviceObjConvert;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirIssueCheckServiceImpl implements FirIssueCheckService {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	private FirTmpDatacheckService firTmpDatacheckService;
	private RuleCheckService clientRuleCheckService;
	private IssueCheckService issueCheckService;
	private List<FirTmpDatacheck> devResults = new ArrayList<>();

	@Override
	@SuppressWarnings("unchecked")
	public Result firIssueCheck(UserInfo userInfo) throws SystemException, Exception {
		Result result = new Result();
		BigDecimal oid = insertFirBatchLog(userInfo);//程式開始先新增一筆執行記錄檔資料
		try {
			Map params = new HashMap<String, String>();
			params.put("pStatus", "N");
			int count = this.firTmpDatacheckService.countFirTmpDatacheck(params);
			if(count==0) {//FIR_TMP_DATACHECK 查無結果 STSTUS='N'，寫回記錄檔
				String remark = "FIR_TMP_DATACHECK查無需檢核之資料";
				updateFirBatchLog("N",remark,userInfo,oid);
				return this.getReturnResult("查無需檢核之資料");
			}
			//分次讀取資料庫資料，一次一千筆
			int cycleTimes = count / 1000;
			if(count % 1000 > 0) {
				cycleTimes = cycleTimes + 1;
			}
			
			for(int c=0; c<cycleTimes; c++) {
				result = this.firTmpDatacheckService.findBatchesFirTmpDatacheckByParams(params);//查詢欲檢核資料
				devResults = (List<FirTmpDatacheck>)result.getResObject();
				
				for(int i=0;i<devResults.size();i++) {//每筆資料逐一呼叫webService檢核，逐筆將檢核後資料更新回FIR_TMP_DATACHECK
					FirAddressRuleObj firAddressRuleObj = new FirAddressRuleObj();
					firAddressRuleObj.setInsType("F02");
					firAddressRuleObj.setOperationType("P");
					firAddressRuleObj.setRulePrefix("FIR");
					firAddressRuleObj.setAddrRoof(devResults.get(i).getRoofmaterial());
					firAddressRuleObj.setAddrStructure(devResults.get(i).getStructure());
					firAddressRuleObj.setAddrSumfloors(devResults.get(i).getSumfloors());
					firAddressRuleObj.setAddrWall(devResults.get(i).getWallmaterial());
					firAddressRuleObj.setAddress(devResults.get(i).getAddressdetailinfo());
					firAddressRuleObj.setPostcode(devResults.get(i).getAddresscode());
					RuleReponseVo ruleReponseVo = callWbServiceCheck(firAddressRuleObj);
					String status = ruleReponseVo.getStatus();
					
					FirTmpDatacheck firTmpDatacheck = new FirTmpDatacheck();
					firTmpDatacheck.setIupdate(userInfo.getUserId());
					firTmpDatacheck.setDupdate(new Date());
					firTmpDatacheck.setOid(devResults.get(i).getOid());

					if(!Status.FAIL.equals(status)&&!Status.SUCESS.equals(status)) {//STATUS ='E'
						firTmpDatacheck.setpStatus("E");
						firTmpDatacheck.setErrMsg("WS無回應或例外異常");
					}else {
						//STATUS = 'Y' 
						firTmpDatacheck.setpStatus("Y");
						if(Status.FAIL.equals(status)) {//WS執行結果不通過
							StringBuilder sbWarn = new StringBuilder();
							StringBuilder sbErr = new StringBuilder();
							for(RuleReponseDetailVo vo : ruleReponseVo.getDetailList()) {//webService不通過，檢核資料明細逐筆抓訊息
								if(RuleResult.FAIL.equals(vo.getRuleResult())) {//WS檢核結果不通過
									if(CheckType.PROMPT.equals(vo.getCheckType())) {//CHECK_TYPE=0提示
										sbWarn.append(vo.getRuleMsg());
									}else {//CHECK_TYPE=1 禁止
										sbErr.append(vo.getRuleMsg());				
									}									
								}
							}
 							firTmpDatacheck.setWarnMsg(sbWarn.toString());
							firTmpDatacheck.setErrMsg(sbErr.toString());
						}//不通過 if() end，將ws檢核結果訊息寫回FIR_TMP_DATACHECK
					}
					updateFirTmpDatacheckByOid(firTmpDatacheck);
				}//檢核資料For{} end
			}
			//資料檢核成功，STATUS='S'，寫回紀錄檔
			updateFirBatchLog("S","",userInfo,oid);
			return this.getReturnResult("資料檢核成功");
		}catch(Exception e) {
			e.printStackTrace();
			updateFirBatchLog("F",e.getMessage(),userInfo,oid);
		}
		return result;
	}

	public BigDecimal insertFirBatchLog(UserInfo userInfo) throws SystemException, Exception {
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");		
		String dateTime = sdFormat.format(date).toString();
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setBatchNo("FIRTMP"+dateTime);
		firBatchLog.setPrgId("FIR_TMP_01");
		firBatchLog.setStatus("1");
		firBatchLog.setIcreate(userInfo.getUserId().toUpperCase());
		firBatchLog.setDcreate(date);
		
		Result result = issueCheckService.insertFirBatchLog(firBatchLog);
		firBatchLog = (FirBatchLog) result.getResObject();
		BigDecimal oid = firBatchLog.getOid();
		
		return oid;	
	}

	public void updateFirBatchLog(String status, String remark, UserInfo userInfo,BigDecimal oid) throws Exception{
		FirBatchLog firBatchLog = new FirBatchLog();
		if(remark.length() > 300) {
			remark = remark.substring(0, 300);
		}
		firBatchLog.setStatus(status);
		firBatchLog.setRemark(remark);
		firBatchLog.setIupdate(userInfo.getUserId().toUpperCase());
		firBatchLog.setDupdate(new Date());
		firBatchLog.setOid(oid);
		issueCheckService.updateFirBatchLog(firBatchLog);
	}

	public void updateFirTmpDatacheckByOid(FirTmpDatacheck firTmpDatacheck) throws SystemException, Exception {
		issueCheckService.updateFirTmpDatacheckByOid(firTmpDatacheck);
	}
	
	public RuleReponseVo callWbServiceCheck(FirAddressRuleObj firAddressRuleObj) throws JAXBException, Exception {
		String soapxml = WebserviceObjConvert.convertObjToBase64Str(FirAddressRuleObj.class, firAddressRuleObj);
		String returnValue = clientRuleCheckService.ruleCheck(soapxml);
		RuleReponseVo ruleReponseVo = (RuleReponseVo)WebserviceObjConvert.convertBase64StrToObj(returnValue, RuleReponseVo.class);
		return ruleReponseVo;
	}
	
	private Result getReturnResult(String msg){
		Result result = new Result();
		Message message = new Message();
		message.setMessageString(msg);
		result.setMessage(message);
		return result;
	}
	
	private static class Status{
		private static final String FAIL = "0"; //WS執行結果不通過
		private static final String SUCESS = "2"; //WS執行結果通過
	}
	
	private static class RuleResult{
		private static final String FAIL = "0"; //WS檢核結果不通過
		private static final String SUCESS = "2";//WS檢核結果通過
	}
	
	private static class CheckType{
		private static final String PROMPT = "0";//WS檢核方式提示
		private static final String PROHIBIT = "1";//WS檢核方式禁止
	}


	public FirTmpDatacheckService getFirTmpDatacheckService() {
		return firTmpDatacheckService;
	}

	public void setFirTmpDatacheckService(FirTmpDatacheckService firTmpDatacheckService) {
		this.firTmpDatacheckService = firTmpDatacheckService;
	}

	public List<FirTmpDatacheck> getDevResults() {
		return devResults;
	}

	public void setDevResults(List<FirTmpDatacheck> devResults) {
		this.devResults = devResults;
	}

	public RuleCheckService getClientRuleCheckService() {
		return clientRuleCheckService;
	}

	public void setClientRuleCheckService(RuleCheckService clientRuleCheckService) {
		this.clientRuleCheckService = clientRuleCheckService;
	}

	public IssueCheckService getIssueCheckService() {
		return issueCheckService;
	}

	public void setIssueCheckService(IssueCheckService issueCheckService) {
		this.issueCheckService = issueCheckService;
	}
}

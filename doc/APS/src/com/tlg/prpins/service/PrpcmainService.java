package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcmainService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcmainByParams(Map params) throws SystemException, Exception;
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/	
	public Result findPAAs400PrefilEpolicy() throws SystemException, Exception;
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
	public Result findPAAs400HeccfilEpolicy() throws SystemException, Exception;
	
	/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
	public Result findPAAs400TolfilEpolicy() throws SystemException, Exception;
	
	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 start*/
	public Result findToCompareTiiTvbcm(Map params) throws SystemException, Exception;
	
	public Result findPolicynoToTvbcm(Map params) throws SystemException, Exception;
	/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 end*/
	
	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) start*/
	public Result findForBatchSendmail(Map params) throws SystemException, Exception;
	
	public Result findForBatchSendmailDtl(Map params) throws SystemException, Exception;
	/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險) end*/
	
	//mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程
	public Result findForPanhsinRenewalCoreData(Map params) throws SystemException, Exception;
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  **/	
	public Result findAs400TAPrefilEpolicy() throws SystemException, Exception;
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  **/
	public Result findAs400TAHeccfilEpolicy() throws SystemException, Exception;
	
	/** HAS0217 ，處理人員：BI086，需求單編號：HAS0217提供外部呼叫以產生TA電子保單  **/
	public Result findAs400TATolfilEpolicy() throws SystemException, Exception;
	
	/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
	public Result findForRerunFpolicy(Map params) throws SystemException, Exception;
}

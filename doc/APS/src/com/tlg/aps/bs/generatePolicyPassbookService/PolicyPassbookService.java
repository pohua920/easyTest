package com.tlg.aps.bs.generatePolicyPassbookService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.OthBatchPassbook;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.util.Result;

/*  mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public interface PolicyPassbookService {
	
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;
	
	public Result insertOthBatchPassbook(String batchNo, String status, String userId, Date executeTime) throws SystemException, Exception;
	
	public Result updateOthBatchPassbook(OthBatchPassbook othBatchPassbook) throws  SystemException, Exception;
	
	public Result updateOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList) throws  SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result updateOthBatchPassbookListByTmpBno(Map params) throws SystemException, Exception;
	
}

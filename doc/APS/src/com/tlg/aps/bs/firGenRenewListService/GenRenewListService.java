package com.tlg.aps.bs.firGenRenewListService;

import java.util.Date;

import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.util.Result;

/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface GenRenewListService {
	
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog) throws Exception;
	
	public void updateFirRenewListDtl(FirRenewListDtl dtl, String userId) throws Exception;
	
	//mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格
	public void updateFirRenewList(FirRenewList firRenewList) throws Exception;
	
}

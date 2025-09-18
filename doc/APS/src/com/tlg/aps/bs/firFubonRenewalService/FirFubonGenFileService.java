package com.tlg.aps.bs.firFubonRenewalService;

import com.tlg.util.Result;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 */
public interface FirFubonGenFileService {
	
	public Result genDiffFile(String batchNo, String userId, String filePwd) throws Exception;
	
	public Result genRejectFile(String batchNo, String userId, String filePwd) throws Exception;
	
	public Result genBigPolicy(String batchNo, String userId, String filePwd) throws Exception;
	
	public Result genRenewFile(String batchNo, String userId, String filePwd) throws Exception;
	
	public Result genDebitNotice(String batchNo, String userId) throws Exception;
	
	public Result uploadBigPolicyFile(String bno, String batchNo, String fileType, String userId) throws Exception;
}

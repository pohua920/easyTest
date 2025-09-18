package com.tlg.aps.bs.firUbNewPolicyService;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程  **/
public interface FirUbProposalFileImportService {
	
	public Result runToImportFile(String userId,Date excuteTime,String programId) throws Exception;
}

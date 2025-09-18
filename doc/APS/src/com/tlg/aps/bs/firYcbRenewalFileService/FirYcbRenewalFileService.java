package com.tlg.aps.bs.firYcbRenewalFileService;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程  **/
public interface FirYcbRenewalFileService {
	
	public Result runToReceiveData(String userId,Date excuteTime,String programId,String rnDate) throws Exception;
}

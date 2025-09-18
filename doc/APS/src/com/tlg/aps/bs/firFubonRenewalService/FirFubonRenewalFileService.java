package com.tlg.aps.bs.firFubonRenewalService;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  **/
public interface FirFubonRenewalFileService {
	
	public Result runToReceiveData(String userId,Date excuteTime,String programId) throws Exception;
}

package com.tlg.aps.bs.firBotRenewalFileService;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：FIR0623，處理人員：CD094，需求單編號：FIR0623_住火_臺銀續保作業_臺銀RD簽屬檔接收排程 **/
public interface FirBotReceiveFDFileService {
	
	public Result runToReceiveData(String userId,Date excuteTime,String programId) throws Exception;
}

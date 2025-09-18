package com.tlg.aps.bs.firRnproposalRenewalFileService;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/* mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public interface FirRnproposalRenewalFileService {
	
	public Result runToProcessFile(String userId, Date excuteTime, String programId) throws SystemException, Exception;
}

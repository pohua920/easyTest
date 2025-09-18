package com.tlg.aps.bs.firIssueCheckService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface FirIssueCheckService {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	public Result firIssueCheck(UserInfo userInfo) throws SystemException, Exception;
	
}

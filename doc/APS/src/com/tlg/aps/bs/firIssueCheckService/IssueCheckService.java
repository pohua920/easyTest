package com.tlg.aps.bs.firIssueCheckService;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirTmpDatacheck;
import com.tlg.util.Result;

public interface IssueCheckService {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;

	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;
	
	public Result updateFirTmpDatacheckByOid(FirTmpDatacheck firTmpDatacheck) throws SystemException, Exception;
}

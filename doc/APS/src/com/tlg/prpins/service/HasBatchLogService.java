package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@SuppressWarnings("rawtypes")
public interface HasBatchLogService {
	
	
	public int countHasBatchLog(Map params) throws SystemException, Exception;
	
	public Result findHasBatchLogByParams(Map params) throws SystemException, Exception;
	
	public Result updateHasBatchLog(HasBatchLog hasBatchLog) throws SystemException, Exception;

	public Result insertHasBatchLog(HasBatchLog hasBatchLog) throws SystemException, Exception;

	public Result removeHasBatchLog(BigDecimal oid) throws SystemException, Exception;
		

}

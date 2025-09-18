package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasBatchInfoService {
	
	@SuppressWarnings("rawtypes")
	public Result findHasBatchInfoByParams(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findHasBatchInfoByUK(Map params) throws SystemException, Exception;
	

}

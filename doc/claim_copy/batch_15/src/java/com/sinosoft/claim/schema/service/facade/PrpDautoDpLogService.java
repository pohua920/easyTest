package com.sinosoft.claim.schema.service.facade;


import java.util.List;
import java.util.Map;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpDautoDpLog;

/**
 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
 */
public interface PrpDautoDpLogService {
	
	/**
	 * PrpDautoDpLog]
	 * @param PrpDautoDpLog
	 */
	public void save(PrpDautoDpLog prpDautoDpLog) throws Exception;
	
	/**
	 * PrpDautoDpLog
	 * @param list  
	 * @throws Exceptionuan
	 */
	public void save(List<PrpDautoDpLog> list) throws Exception;
 	/**
	 * @param prpLcompensate
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpDautoDpLog prpDautoDpLog) throws Exception;
	
	public void saveOrUpdate(List<PrpDautoDpLog> prpLcompensateList) throws Exception;
	

	/**
	 */
	public void update(PrpDautoDpLog prpDautoDpLog) throws Exception;
	public void update(List<PrpDautoDpLog> prpDautoDpLogList) throws Exception;
	

	/**
	 *
	 */
	public List<PrpDautoDpLog> findPrpDautoDpLog(String logId) throws Exception;
	
	public List<PrpDautoDpLog> findPrpDautoDpLogStatus(PrpDautoDpLog prpDautoDpLog) throws Exception;
}

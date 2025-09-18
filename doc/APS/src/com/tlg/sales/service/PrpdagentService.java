package com.tlg.sales.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
public interface PrpdagentService {
	public Result findPrpdagentByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrpdagentByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdagentByUk(Map params) throws SystemException, Exception;
	
	public Result updatePrpdagent(Prpdagent prpdagent) throws SystemException, Exception;
	
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 start */
	public Result selectForSalesAgentDateAlert(Map params) throws SystemException, Exception;
	
	public Result findAPS043ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 end */
	
	//mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格
	public Result FindForFirChangeHandler1code(Map params) throws SystemException, Exception;
}

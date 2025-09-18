package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.util.PageInfo;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
public interface PrpdagentDao extends IBatisBaseDao<Prpdagent, BigDecimal> {
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 start */
	public List<SalesAgentDateAlertVo> selectForSalesAgentDateAlert(Map<String,String> params) throws Exception;
	
	public List<SalesAgentDateAlertVo> selectForAps043(PageInfo pageInfo)throws Exception;
	
	public int countForAps043(Map<String,String> params)throws Exception;
	/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 end */
	
	//mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格
	public List<Prpdagent> selectForFirChangeHandler1code(Map<String,String> params) throws Exception;
}
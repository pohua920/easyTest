package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.SalesAgentDateReserveVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.PrpdagentLoginDateReserve;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
public interface PrpdagentLoginDateReserveDao extends IBatisBaseDao<PrpdagentLoginDateReserve, BigDecimal> {

	List<SalesAgentDateReserveVo> selectForAgentLoginDateReserve(Map<String, String> params) throws Exception;
	

}
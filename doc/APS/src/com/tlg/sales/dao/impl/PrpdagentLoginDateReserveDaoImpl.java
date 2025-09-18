package com.tlg.sales.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.SalesAgentDateReserveVo;
import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.sales.dao.PrpdagentDao;
import com.tlg.sales.dao.PrpdagentLoginDateReserveDao;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.entity.PrpdagentLoginDateReserve;
import com.tlg.util.PageInfo;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
public class PrpdagentLoginDateReserveDaoImpl extends IBatisBaseDaoImpl<PrpdagentLoginDateReserve, BigDecimal> implements PrpdagentLoginDateReserveDao {
	@Override
	public String getNameSpace() {
		return "PrpdagentLoginDateReserve";
	}
	@Override
	public List<SalesAgentDateReserveVo> selectForAgentLoginDateReserve(Map<String, String> params) throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAgentLoginDateReserve",params);//selectForSalesAgentDateAlert
	}
	
}
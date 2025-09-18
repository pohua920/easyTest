package com.tlg.sales.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.sales.dao.PrpdagentIdvDao;
import com.tlg.sales.entity.PrpdagentIdv;
import com.tlg.util.PageInfo;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
public class PrpdagentIdvDaoImpl extends IBatisBaseDaoImpl<PrpdagentIdv, BigDecimal> implements PrpdagentIdvDao {
	@Override
	public String getNameSpace() {
		return "PrpdagentIdv";
	}
	
}
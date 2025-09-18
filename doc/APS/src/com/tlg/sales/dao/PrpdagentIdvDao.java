package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.PrpdagentIdv;
import com.tlg.util.PageInfo;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
public interface PrpdagentIdvDao extends IBatisBaseDao<PrpdagentIdv, BigDecimal> {
	
}
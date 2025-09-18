package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps028ResultVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.Prpdagentsub;
import com.tlg.util.PageInfo;

/** mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 */
public interface PrpdagentsubDao extends IBatisBaseDao<Prpdagentsub, BigDecimal> {
	
	public int countMaxAgentsubcode() throws Exception;
	
	public List<Aps028ResultVo> selectForAps028(PageInfo pageInfo) throws Exception;
	
	public int countForAps028(Map<String, String> params) throws Exception;
	
}
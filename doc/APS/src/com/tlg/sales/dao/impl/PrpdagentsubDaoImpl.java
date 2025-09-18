package com.tlg.sales.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps028ResultVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.sales.dao.PrpdagentsubDao;
import com.tlg.sales.entity.Prpdagentsub;
import com.tlg.util.PageInfo;

/** mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 */
public class PrpdagentsubDaoImpl extends IBatisBaseDaoImpl<Prpdagentsub, BigDecimal> implements PrpdagentsubDao {
	@Override
	public String getNameSpace() {
		return "Prpdagentsub";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int countMaxAgentsubcode() throws Exception {
		int count = (int) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectMaxAgentsubcode");
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Aps028ResultVo> selectForAps028(PageInfo pageInfo) throws Exception {
		List<Aps028ResultVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps028",pageInfo.getFilter());
		return queryForList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int countForAps028(Map<String, String> params) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps028", params);
		return count;
	}
}
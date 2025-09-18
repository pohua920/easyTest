package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpdclausereportDao;
import com.tlg.prpins.entity.Prpdclausereport;

public class PrpdclausereportDaoImpl extends IBatisBaseDaoImpl<Prpdclausereport, BigDecimal> implements PrpdclausereportDao {
	
	@Override
	public String getNameSpace() {
		return "Prpdclausereport";
	}

	@Override
	public String selectForEpolicy(Map params) throws Exception {
		String result = (String) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectForEpolicy",params);
		return result;
	}
	
}
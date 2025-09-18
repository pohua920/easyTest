package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirPremcalcTmpdtlDao;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;

public class FirPremcalcTmpdtlDaoImpl extends IBatisBaseDaoImpl<FirPremcalcTmpdtl, BigDecimal> implements FirPremcalcTmpdtlDao {
	
	@Override
	public String getNameSpace() {
		return "FirPremcalcTmpdtl";
	}

	@Override
	public int getDiscountNum(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countF01Discount", params);
		return count;
	}
	
}
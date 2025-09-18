package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirPremiumRate3Dao;
import com.tlg.prpins.entity.FirPremiumRate3;

public class FirPremiumRate3DaoImpl extends IBatisBaseDaoImpl<FirPremiumRate3, BigDecimal> implements FirPremiumRate3Dao {
	
	@Override
	public String getNameSpace() {
		return "FirPremiumRate3";
	}
	
	
	@Override
	public Double getMinDeduction(Map params) {
		Double deduction = (Double) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMinDeduction", params);
		return deduction;
	}

}
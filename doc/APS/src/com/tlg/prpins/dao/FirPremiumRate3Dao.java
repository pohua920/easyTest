package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirPremiumRate3;

public interface FirPremiumRate3Dao extends IBatisBaseDao<FirPremiumRate3, BigDecimal> {
	
	public Double getMinDeduction(Map params);
	
}
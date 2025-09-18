package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;

public interface FirPremcalcTmpdtlDao extends IBatisBaseDao<FirPremcalcTmpdtl, BigDecimal> {
	
	public int getDiscountNum(Map params);
}
package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetMobilePolicySalesDao;
import com.tlg.msSqlMob.entity.FetMobilePolicySales;

public class FetMobilePolicySalesDaoImpl extends IBatisBaseDaoImpl<FetMobilePolicySales, BigDecimal> implements FetMobilePolicySalesDao {

	@Override
	public String getNameSpace() {
		return "FetMobilePolicySales";
	}

}
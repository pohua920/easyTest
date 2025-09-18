package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.BatchRepairMainDao;
import com.tlg.msSqlMob.entity.BatchRepairMain;

public class BatchRepairMainDaoImpl extends IBatisBaseDaoImpl<BatchRepairMain, BigDecimal> implements BatchRepairMainDao {
	
	@Override
	public String getNameSpace() {
		return "BatchRepairMain";
	}

}
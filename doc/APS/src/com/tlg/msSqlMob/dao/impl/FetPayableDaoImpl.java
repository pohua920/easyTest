package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetPayableDao;
import com.tlg.msSqlMob.entity.FetPayable;

import java.math.BigDecimal;

public class FetPayableDaoImpl extends IBatisBaseDaoImpl<FetPayable, BigDecimal> implements FetPayableDao {
	@Override
	public String getNameSpace() {
		return "FetPayable";
	}
}
package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetMobileEpolicyDao;
import com.tlg.msSqlMob.entity.FetMobileEpolicy;

public class FetMobileEpolicyDaoImpl extends IBatisBaseDaoImpl<FetMobileEpolicy, BigDecimal> implements FetMobileEpolicyDao {

	@Override
	public String getNameSpace() {
		return "FetMobileEpolicy";
	}

}
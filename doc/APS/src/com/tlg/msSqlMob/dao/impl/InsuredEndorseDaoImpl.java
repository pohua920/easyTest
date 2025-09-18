package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.InsuredEndorseDao;
import com.tlg.msSqlMob.entity.InsuredEndorse;

public class InsuredEndorseDaoImpl extends IBatisBaseDaoImpl<InsuredEndorse, BigDecimal> implements InsuredEndorseDao {

	@Override
	public String getNameSpace() {
		return "InsuredEndorse";
	}
}
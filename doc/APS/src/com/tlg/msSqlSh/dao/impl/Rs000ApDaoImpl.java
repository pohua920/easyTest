package com.tlg.msSqlSh.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlSh.dao.Rs000ApDao;
import com.tlg.msSqlSh.entity.Rs000Ap;

public class Rs000ApDaoImpl extends IBatisBaseDaoImpl<Rs000Ap, BigDecimal> implements Rs000ApDao {
	
	@Override
	public String getNameSpace() {
		return "Rs000Ap";
	}

}
package com.tlg.msSqlAs400.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlAs400.dao.CtbcClmsmsRmDao;
import com.tlg.msSqlAs400.entity.CtbcClmsmsRm;

public class CtbcClmsmsRmDaoImpl extends IBatisBaseDaoImpl<CtbcClmsmsRm, BigDecimal> implements CtbcClmsmsRmDao {
	
	@Override
	public String getNameSpace() {
		return "CtbcClmsmsRm";
	}


}
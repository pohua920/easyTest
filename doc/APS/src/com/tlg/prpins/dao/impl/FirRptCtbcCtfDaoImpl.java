package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirRptCtbcCtfDao;
import com.tlg.prpins.entity.FirRptCtbcCtf;

public class FirRptCtbcCtfDaoImpl extends IBatisBaseDaoImpl<FirRptCtbcCtf, BigDecimal> implements FirRptCtbcCtfDao {
	
	@Override
	public String getNameSpace() {
		return "FirRptCtbcCtf";
	}
	
}
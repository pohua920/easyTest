package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirPremcalcTmpDao;
import com.tlg.prpins.entity.FirPremcalcTmp;

public class FirPremcalcTmpDaoImpl extends IBatisBaseDaoImpl<FirPremcalcTmp, BigDecimal> implements FirPremcalcTmpDao {
	
	@Override
	public String getNameSpace() {
		return "FirPremcalcTmp";
	}

}
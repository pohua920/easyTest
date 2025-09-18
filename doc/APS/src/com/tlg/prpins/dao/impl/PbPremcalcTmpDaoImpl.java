package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremcalcTmpDao;
import com.tlg.prpins.entity.PbPremcalcTmp;

public class PbPremcalcTmpDaoImpl extends IBatisBaseDaoImpl<PbPremcalcTmp, BigDecimal> implements PbPremcalcTmpDao {
	
	@Override
	public String getNameSpace() {
		return "PbPremcalcTmp";
	}

}
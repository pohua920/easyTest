package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremcalcTmpdtlDao;
import com.tlg.prpins.entity.PbPremcalcTmpdtl;

public class PbPremcalcTmpdtlDaoImpl extends IBatisBaseDaoImpl<PbPremcalcTmpdtl, BigDecimal> implements PbPremcalcTmpdtlDao {
	
	@Override
	public String getNameSpace() {
		return "PbPremcalcTmpdtl";
	}

}
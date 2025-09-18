package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremcalcClauseDao;
import com.tlg.prpins.entity.PbPremcalcClause;

public class PbPremcalcClauseDaoImpl extends IBatisBaseDaoImpl<PbPremcalcClause, BigDecimal> implements PbPremcalcClauseDao {
	
	@Override
	public String getNameSpace() {
		return "PbPremcalcClause";
	}

}
package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PbPremcalcAdditionTermDao;
import com.tlg.prpins.entity.PbPremcalcAdditionTerm;

public class PbPremcalcAdditionTermDaoImpl extends IBatisBaseDaoImpl<PbPremcalcAdditionTerm, BigDecimal> implements PbPremcalcAdditionTermDao {
	
	@Override
	public String getNameSpace() {
		return "PbPremcalcAdditionTerm";
	}

}
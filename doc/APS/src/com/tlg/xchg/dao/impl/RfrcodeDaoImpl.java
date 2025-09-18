package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.RfrcodeDao;
import com.tlg.xchg.entity.Rfrcode;

public class RfrcodeDaoImpl extends IBatisBaseDaoImpl<Rfrcode, BigDecimal> implements RfrcodeDao {
	
	@Override
	public String getNameSpace() {
		return "Rfrcode";
	}
	
}
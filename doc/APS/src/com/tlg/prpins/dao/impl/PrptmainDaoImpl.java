package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrptmainDao;
import com.tlg.prpins.entity.Prptmain;

public class PrptmainDaoImpl extends IBatisBaseDaoImpl<Prptmain, BigDecimal> implements PrptmainDao {
	
	@Override
	public String getNameSpace() {
		return "Prptmain";
	}
	
}
package com.tlg.dms.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.dms.dao.PrpdexchDao;
import com.tlg.dms.entity.Prpdexch;

public class PrpdexchDaoImpl extends IBatisBaseDaoImpl<Prpdexch, BigDecimal> implements PrpdexchDao {
	
	@Override
	public String getNameSpace() {
		return "Prpdexch";
	}
	
}
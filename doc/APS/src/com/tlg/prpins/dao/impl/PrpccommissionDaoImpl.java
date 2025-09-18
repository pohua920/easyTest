package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpccommissionDao;
import com.tlg.prpins.entity.Prpccommission;

public class PrpccommissionDaoImpl extends IBatisBaseDaoImpl<Prpccommission, BigDecimal> implements PrpccommissionDao {
	
	@Override
	public String getNameSpace() {
		return "Prpccommission";
	}

}
package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcopycommissionDao;
import com.tlg.prpins.entity.Prpcopycommission;

public class PrpcopycommissionDaoImpl extends IBatisBaseDaoImpl<Prpcopycommission, BigDecimal> implements PrpcopycommissionDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcopycommission";
	}

}
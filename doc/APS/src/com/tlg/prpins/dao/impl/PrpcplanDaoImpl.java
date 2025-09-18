package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcplanDao;
import com.tlg.prpins.entity.Prpcplan;

public class PrpcplanDaoImpl extends IBatisBaseDaoImpl<Prpcplan, BigDecimal> implements PrpcplanDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcplan";
	}
	
}
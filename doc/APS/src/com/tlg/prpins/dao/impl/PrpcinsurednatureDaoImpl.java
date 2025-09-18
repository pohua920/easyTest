package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcinsurednatureDao;
import com.tlg.prpins.entity.Prpcinsurednature;

public class PrpcinsurednatureDaoImpl extends IBatisBaseDaoImpl<Prpcinsurednature, BigDecimal> implements PrpcinsurednatureDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcinsurednature";
	}
	
}
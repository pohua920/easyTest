package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcmainpropDao;
import com.tlg.prpins.entity.Prpcmainprop;

public class PrpcmainpropDaoImpl extends IBatisBaseDaoImpl<Prpcmainprop, BigDecimal> implements PrpcmainpropDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcmainprop";
	}
	
}
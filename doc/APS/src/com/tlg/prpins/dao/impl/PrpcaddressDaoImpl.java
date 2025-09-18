package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcaddressDao;
import com.tlg.prpins.entity.Prpcaddress;

public class PrpcaddressDaoImpl extends IBatisBaseDaoImpl<Prpcaddress, BigDecimal> implements PrpcaddressDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcaddress";
	}
	
}
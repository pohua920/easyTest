package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrpcitemkindDao;
import com.tlg.prpins.entity.Prpcitemkind;

public class PrpcitemkindDaoImpl extends IBatisBaseDaoImpl<Prpcitemkind, BigDecimal> implements PrpcitemkindDao {
	
	@Override
	public String getNameSpace() {
		return "Prpcitemkind";
	}
	
}
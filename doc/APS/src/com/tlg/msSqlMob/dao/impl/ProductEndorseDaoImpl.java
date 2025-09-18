package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ProductEndorseDao;
import com.tlg.msSqlMob.entity.ProductEndorse;

public class ProductEndorseDaoImpl extends IBatisBaseDaoImpl<ProductEndorse, BigDecimal> implements ProductEndorseDao {

	@Override
	public String getNameSpace() {
		return "ProductEndorse";
	}
}
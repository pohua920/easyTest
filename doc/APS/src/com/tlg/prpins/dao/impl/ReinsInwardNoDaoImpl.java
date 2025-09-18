package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ReinsInwardNoDao;
import com.tlg.prpins.entity.ReinsInwardNo;

public class ReinsInwardNoDaoImpl extends IBatisBaseDaoImpl<ReinsInwardNo, BigDecimal> implements ReinsInwardNoDao {
	
	@Override
	public String getNameSpace() {
		return "ReinsInwardNo";
	}

}
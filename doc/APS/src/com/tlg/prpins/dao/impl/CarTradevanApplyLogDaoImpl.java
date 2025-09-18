package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.CarTradevanApplyLogDao;
import com.tlg.prpins.entity.CarTradevanApplyLog;

public class CarTradevanApplyLogDaoImpl extends IBatisBaseDaoImpl<CarTradevanApplyLog, BigDecimal> implements CarTradevanApplyLogDao {
	
	@Override
	public String getNameSpace() {
		return "CarTradevanApplyLog";
	}

}
package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ReinsInwardInsDataDao;
import com.tlg.prpins.entity.ReinsInwardInsData;

public class ReinsInwardInsDataDaoImpl extends IBatisBaseDaoImpl<ReinsInwardInsData, BigDecimal> implements ReinsInwardInsDataDao {
	
	@Override
	public String getNameSpace() {
		return "ReinsInwardInsData";
	}

}
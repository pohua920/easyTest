package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ReinsInwardClaimInsDataDao;
import com.tlg.prpins.entity.ReinsInwardClaimInsData;

public class ReinsInwardClaimInsDataDaoImpl extends IBatisBaseDaoImpl<ReinsInwardClaimInsData, BigDecimal> implements ReinsInwardClaimInsDataDao {
	
	@Override
	public String getNameSpace() {
		return "ReinsInwardClaimInsData";
	}

}
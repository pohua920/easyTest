package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ReinsInwardClaimDataDao;
import com.tlg.prpins.entity.ReinsInwardClaimData;

public class ReinsInwardClaimDataDaoImpl extends IBatisBaseDaoImpl<ReinsInwardClaimData, BigDecimal> implements ReinsInwardClaimDataDao {
	
	@Override
	public String getNameSpace() {
		return "ReinsInwardClaimData";
	}


}
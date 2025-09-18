package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetMobilePolicyInsurantInfoDao;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;

public class FetMobilePolicyInsurantInfoDaoImpl extends IBatisBaseDaoImpl<FetMobilePolicyInsurantInfo, BigDecimal> implements FetMobilePolicyInsurantInfoDao {

	@Override
	public String getNameSpace() {
		return "FetMobilePolicyInsurantInfo";
	}
}
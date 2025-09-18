package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetMobilePolicyDeviceDao;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;

public class FetMobilePolicyDeviceDaoImpl extends IBatisBaseDaoImpl<FetMobilePolicyDevice, BigDecimal> implements FetMobilePolicyDeviceDao {

	@Override
	public String getNameSpace() {
		return "FetMobilePolicyDevice";
	}
}
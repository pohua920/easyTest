package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetCancelNotificationDao;
import com.tlg.msSqlMob.entity.FetCancelNotification;

import java.math.BigDecimal;

public class FetCancelNotificationDaoImpl extends IBatisBaseDaoImpl<FetCancelNotification, BigDecimal> implements FetCancelNotificationDao {
	@Override
	public String getNameSpace() {
		return "FetCancelNotification";
	}
}
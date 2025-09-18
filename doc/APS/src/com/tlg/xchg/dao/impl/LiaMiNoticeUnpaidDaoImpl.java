package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaMiNoticeUnpaidDao;
import com.tlg.xchg.entity.LiaMiNoticeUnpaid;

public class LiaMiNoticeUnpaidDaoImpl extends IBatisBaseDaoImpl<LiaMiNoticeUnpaid, BigDecimal> implements LiaMiNoticeUnpaidDao {
	
	@Override
	public String getNameSpace() {
		return "LiaMiNoticeUnpaid";
	}
	
}
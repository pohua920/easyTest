package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaMiNoticeCancleDao;
import com.tlg.xchg.entity.LiaMiNoticeCancle;

public class LiaMiNoticeCancleDaoImpl extends IBatisBaseDaoImpl<LiaMiNoticeCancle, BigDecimal> implements LiaMiNoticeCancleDao {
	
	@Override
	public String getNameSpace() {
		return "LiaMiNoticeCancle";
	}
	
}
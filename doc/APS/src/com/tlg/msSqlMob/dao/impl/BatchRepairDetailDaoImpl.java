package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.BatchRepairDetailDao;
import com.tlg.msSqlMob.entity.BatchRepairDetail;

public class BatchRepairDetailDaoImpl extends IBatisBaseDaoImpl<BatchRepairDetail, BigDecimal> implements BatchRepairDetailDao {
	
	@Override
	public String getNameSpace() {
		return "BatchRepairDetail";
	}

}
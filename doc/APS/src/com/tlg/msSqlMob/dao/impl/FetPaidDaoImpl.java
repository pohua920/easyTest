package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetPaidDao;
import com.tlg.msSqlMob.entity.FetPaid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FetPaidDaoImpl extends IBatisBaseDaoImpl<FetPaid, BigDecimal> implements FetPaidDao {
	@Override
	public String getNameSpace() {
		return "FetPaid";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<FetPaid> selectForCheckAccount(Map params) throws Exception {
		
		return getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForCheckAccount", params);
	}
}
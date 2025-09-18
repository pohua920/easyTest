package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.ReinsInwardMainDataDao;
import com.tlg.prpins.entity.ReinsInwardMainData;

public class ReinsInwardMainDataDaoImpl extends IBatisBaseDaoImpl<ReinsInwardMainData, BigDecimal> implements ReinsInwardMainDataDao {
	
	@Override
	public String getNameSpace() {
		return "ReinsInwardMainData";
	}


	@Override
	public int queryCurrentEndorseNo(Map params) {
		Integer no = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".currentEndorseNo", params);
		return no;
	}

}
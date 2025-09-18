package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.ReinsInwardMainData;

public interface ReinsInwardMainDataDao extends IBatisBaseDao<ReinsInwardMainData, BigDecimal> {
	
	public int queryCurrentEndorseNo(Map params);
}
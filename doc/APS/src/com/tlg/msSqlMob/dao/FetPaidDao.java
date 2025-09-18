package com.tlg.msSqlMob.dao;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.FetPaid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface FetPaidDao extends IBatisBaseDao<FetPaid, BigDecimal>{

	@SuppressWarnings("rawtypes")
	public List<FetPaid> selectForCheckAccount(Map params)throws Exception;
	
}
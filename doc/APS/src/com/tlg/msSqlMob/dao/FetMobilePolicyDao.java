package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.FetMobilePolicy;

public interface FetMobilePolicyDao extends IBatisBaseDao<FetMobilePolicy, BigDecimal>{
	
	public List<FetMobilePolicy> findPolicyByStatusWait(Map<String, String> map)throws Exception;
	
	public int countPolicyByStatusWait(Map params)throws Exception;
}
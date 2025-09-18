package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.FetMobilePolicyDao;
import com.tlg.msSqlMob.entity.FetMobilePolicy;

public class FetMobilePolicyDaoImpl extends IBatisBaseDaoImpl<FetMobilePolicy, BigDecimal> implements FetMobilePolicyDao {

	@Override
	public String getNameSpace() {
		return "FetMobilePolicy";
	}
	
	@SuppressWarnings("unchecked")
	public List<FetMobilePolicy> findPolicyByStatusWait(Map<String, String> map)throws Exception {
		List<FetMobilePolicy> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByWaitPolicy");
		return queryForList;
	}
	
	@Override
	public int countPolicyByStatusWait(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countByWaitPolicy", params);
		return count;
	}
}
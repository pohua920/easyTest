package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewSnnDao;
import com.tlg.prpins.entity.FirCtbcRewSnn;

public class FirCtbcRewSnnDaoImpl extends IBatisBaseDaoImpl<FirCtbcRewSnn, BigDecimal> implements FirCtbcRewSnnDao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewSnn";
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	@Override
	public List<FirCtbcRewSnn> findCtbcRewSnnForSameId(Map params) throws Exception {
		List<FirCtbcRewSnn> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectSameRelateId",params);
		return queryForList;
	}
	
	@Override
	public List<FirCtbcRewSnn> findCtbcRewSnnForSameName(Map params) throws Exception {
		List<FirCtbcRewSnn> queryForList =  getSqlMapClientTemplate().queryForList(getNameSpace()+".selectSameNames",params);
		return queryForList;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
}
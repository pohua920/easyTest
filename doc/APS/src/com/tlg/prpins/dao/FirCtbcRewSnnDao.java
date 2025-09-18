package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.FirCtbcRewSnn;
import com.tlg.iBatis.IBatisBaseDao;

public interface FirCtbcRewSnnDao extends IBatisBaseDao<FirCtbcRewSnn, BigDecimal> {
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	public List<FirCtbcRewSnn> findCtbcRewSnnForSameId(Map params) throws Exception;
	
	public List<FirCtbcRewSnn> findCtbcRewSnnForSameName(Map params) throws Exception;
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
}
package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirCtbcRewFix180;

public interface FirCtbcRewFix180Dao extends IBatisBaseDao<FirCtbcRewFix180, BigDecimal> {
	
	//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 
	public FirCtbcRewFix180 findFirCtbcRewFix180ByPK(Map params) throws Exception;
}
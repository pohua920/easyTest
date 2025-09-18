package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirCtbcRewFix180Dao;
import com.tlg.prpins.entity.FirCtbcRewFix180;

public class FirCtbcRewFix180DaoImpl extends IBatisBaseDaoImpl<FirCtbcRewFix180, BigDecimal> implements FirCtbcRewFix180Dao {
	
	@Override
	public String getNameSpace() {
		return "FirCtbcRewFix180";
	}

	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	@Override
	public FirCtbcRewFix180 findFirCtbcRewFix180ByPK(Map params) throws Exception{
		FirCtbcRewFix180 entity = (FirCtbcRewFix180) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectByPrimaryKey", params);
		return entity;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
}
package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.PrplClaimDao;
import com.tlg.prpins.entity.PrplClaim;

public class PrplClaimDaoImpl extends IBatisBaseDaoImpl<PrplClaim, BigDecimal> implements PrplClaimDao {
	
	@Override
	public String getNameSpace() {
		return "PrplClaim";
	}
	
	@Override
	public List<String> findFirClaimByParams(Map params) {
		List<String> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectFirClaimByParams",params);
		return queryForList;
	}

}
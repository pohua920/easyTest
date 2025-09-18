package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.AssocRcvAncmtDao;
import com.tlg.xchg.entity.AssocRcvAncmt;

public class AssocRcvAncmtDaoImpl extends IBatisBaseDaoImpl<AssocRcvAncmt, BigDecimal> implements AssocRcvAncmtDao {
	
	@Override
	public String getNameSpace() {
		return "AssocRcvAncmt";
	}
	
	public List<AssocRcvAncmt> selectByDistinctIdno() throws Exception {
		List<AssocRcvAncmt> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}
	
}
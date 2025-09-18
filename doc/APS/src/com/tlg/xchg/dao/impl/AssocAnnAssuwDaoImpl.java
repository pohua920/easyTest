package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.AssocAnnAssuwDao;
import com.tlg.xchg.entity.AssocAnnAssuw;

public class AssocAnnAssuwDaoImpl extends IBatisBaseDaoImpl<AssocAnnAssuw, BigDecimal> implements AssocAnnAssuwDao {
	
	@Override
	public String getNameSpace() {
		return "AssocAnnAssuw";
	}
	
	public List<AssocAnnAssuw> selectByDistinctIdno() throws Exception {
		List<AssocAnnAssuw> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}
	
}
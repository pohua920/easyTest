package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaUndwrtAnnounceDao;
import com.tlg.xchg.entity.LiaUndwrtAnnounce;

public class LiaUndwrtAnnounceDaoImpl extends IBatisBaseDaoImpl<LiaUndwrtAnnounce, BigDecimal> implements LiaUndwrtAnnounceDao {
	
	@Override
	public String getNameSpace() {
		return "LiaUndwrtAnnounce";
	}
	
	public List<LiaUndwrtAnnounce> selectByDistinctIdno() throws Exception {
		List<LiaUndwrtAnnounce> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}
}
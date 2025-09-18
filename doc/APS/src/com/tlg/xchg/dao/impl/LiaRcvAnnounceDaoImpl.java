package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.LiaRcvAnnounceDao;
import com.tlg.xchg.entity.LiaRcvAnnounce;

public class LiaRcvAnnounceDaoImpl extends IBatisBaseDaoImpl<LiaRcvAnnounce, BigDecimal> implements LiaRcvAnnounceDao {
	
	@Override
	public String getNameSpace() {
		return "LiaRcvAnnounce";
	}
	
	public List<LiaRcvAnnounce> selectByDistinctIdno() throws Exception {
		List<LiaRcvAnnounce> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}
	
}
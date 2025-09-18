package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.xchg.entity.LiaRcvAnnounce;
import com.tlg.xchg.entity.LiaUndwrtAnnounce;

public interface LiaUndwrtAnnounceDao extends IBatisBaseDao<LiaUndwrtAnnounce, BigDecimal> {
	
	public List<LiaUndwrtAnnounce> selectByDistinctIdno() throws Exception;
}
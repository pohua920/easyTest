package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.xchg.entity.LiaRcvAnnounce;

public interface LiaRcvAnnounceDao extends IBatisBaseDao<LiaRcvAnnounce, BigDecimal> {
	
	public List<LiaRcvAnnounce> selectByDistinctIdno() throws Exception;
	
}
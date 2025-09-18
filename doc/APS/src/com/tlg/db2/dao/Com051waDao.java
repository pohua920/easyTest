package com.tlg.db2.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.db2.entity.Com051wa;
import com.tlg.iBatis.IBatisBaseDao;

public interface Com051waDao extends IBatisBaseDao<Com051wa, BigDecimal> {
	
	public boolean updateForWA60(Com051wa com051wa);
	
	public boolean clearForWA60(Map params);
	
	public List<Com051wa> selectByDistinctIdno() throws Exception;
}
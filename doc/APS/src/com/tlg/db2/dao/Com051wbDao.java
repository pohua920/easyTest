package com.tlg.db2.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.db2.entity.Com051wb;
import com.tlg.iBatis.IBatisBaseDao;

public interface Com051wbDao extends IBatisBaseDao<Com051wb, BigDecimal> {
	
	public boolean updateForWB60(Com051wb com051wb);
	
	public boolean clearForWB60(Map params);
	
	public List<Com051wb> selectByDistinctIdno() throws Exception;
	
}
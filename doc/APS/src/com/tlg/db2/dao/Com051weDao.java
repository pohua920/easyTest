package com.tlg.db2.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.db2.entity.Com051wa;
import com.tlg.db2.entity.Com051we;
import com.tlg.iBatis.IBatisBaseDao;

public interface Com051weDao extends IBatisBaseDao<Com051we, BigDecimal> {
	
	public boolean updateForWE20(Com051we com051we);
	
	public boolean clearForWE20(Map params);
	
	public List<Com051we> selectByDistinctIdno() throws Exception;
}
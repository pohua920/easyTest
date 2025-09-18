package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.xchg.entity.AssocAnnAssuw;

public interface AssocAnnAssuwDao extends IBatisBaseDao<AssocAnnAssuw, BigDecimal> {
	
	public List<AssocAnnAssuw> selectByDistinctIdno() throws Exception;
	
}
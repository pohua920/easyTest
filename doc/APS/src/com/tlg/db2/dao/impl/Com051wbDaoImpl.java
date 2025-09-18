package com.tlg.db2.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.db2.dao.Com051wbDao;
import com.tlg.db2.entity.Com051wb;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com051wbDaoImpl extends IBatisBaseDaoImpl<Com051wb, BigDecimal> implements Com051wbDao {
	
	@Override
	public String getNameSpace() {
		return "Com051wb";
	}
	
	public boolean updateForWB60(Com051wb com051wb) {
		String nameSpace = getNameSpace()+".updateForWb60";
		int i = getSqlMapClientTemplate().update(nameSpace, com051wb);
		if(i >= 1) {
			return true;
		}
		return false;
	}
	
	public List<Com051wb> selectByDistinctIdno() throws Exception {
		List<Com051wb> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}
	
	@Override
	public boolean clearForWB60(Map params) {
		String nameSpace = getNameSpace()+".clearForWb60";
		int i = getSqlMapClientTemplate().update(nameSpace, params);
		if(i >= 1) {
			return true;
		}
		return false;
	}

}
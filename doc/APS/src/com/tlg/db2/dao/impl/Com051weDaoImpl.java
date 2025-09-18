package com.tlg.db2.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.db2.dao.Com051weDao;
import com.tlg.db2.entity.Com051we;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com051weDaoImpl extends IBatisBaseDaoImpl<Com051we, BigDecimal> implements Com051weDao {
	
	@Override
	public String getNameSpace() {
		return "Com051we";
	}

	public boolean updateForWE20(Com051we com051we) {
		String nameSpace = getNameSpace()+".updateForWe20";
		int i = getSqlMapClientTemplate().update(nameSpace, com051we);
		if(i >= 1) {
			return true;
		}
		return false;
	}
	
	public List<Com051we> selectByDistinctIdno() throws Exception {
		List<Com051we> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}

	@Override
	public boolean clearForWE20(Map params) {
		String nameSpace = getNameSpace()+".clearForWe20";
		int i = getSqlMapClientTemplate().update(nameSpace, params);
		if(i >= 1) {
			return true;
		}
		return false;
	}

}
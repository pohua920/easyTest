package com.tlg.db2.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.db2.dao.Com051waDao;
import com.tlg.db2.entity.Com051wa;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;

public class Com051waDaoImpl extends IBatisBaseDaoImpl<Com051wa, BigDecimal> implements Com051waDao {
	
	@Override
	public String getNameSpace() {
		return "Com051wa";
	}

	public boolean updateForWA60(Com051wa com051wa) {
		String nameSpace = getNameSpace()+".updateForWa60";
		int i = getSqlMapClientTemplate().update(nameSpace, com051wa);
		if(i >= 1) {
			return true;
		}
		return false;
	}
	
	public List<Com051wa> selectByDistinctIdno() throws Exception {
		List<Com051wa> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}

	@Override
	public boolean clearForWA60(Map params) {
		String nameSpace = getNameSpace()+".clearForWa60";
		int i = getSqlMapClientTemplate().update(nameSpace, params);
		if(i >= 1) {
			return true;
		}
		return false;
	}

}
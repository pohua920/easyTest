package com.tlg.xchg.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.xchg.dao.UndwrtIpb902iDao;
import com.tlg.xchg.entity.UndwrtIpb902i;

public class UndwrtIpb902iDaoImpl extends IBatisBaseDaoImpl<UndwrtIpb902i, BigDecimal> implements UndwrtIpb902iDao {
	
	@Override
	public String getNameSpace() {
		return "UndwrtIpb902i";
	}

	public List<UndwrtIpb902i> selectByDistinctIdno() throws Exception {
		List<UndwrtIpb902i> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByDistinctIdno");
		return queryForList;
	}
	
	public boolean updateSendtime(UndwrtIpb902i entity) {
		boolean result = false;
		String nameSpace = getNameSpace()+".updateSendtime";
		;
		if(getSqlMapClientTemplate().update(nameSpace, entity) == 1) {
			result = true;
		}
		return result;
	}
}
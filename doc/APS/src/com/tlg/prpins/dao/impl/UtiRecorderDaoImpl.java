package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.prpins.dao.UtiRecorderDao;
import com.tlg.prpins.entity.UtiRecorder;
import com.tlg.util.PageInfo;

public class UtiRecorderDaoImpl extends SqlMapClientDaoSupport implements UtiRecorderDao {
	
	@Override
	public String getNameSpace() {
		return "UtiRecorder";
	}
	
	@Override
	public int count(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".count", params);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<UtiRecorder> findByPageInfo(PageInfo pageInfo)throws Exception {
		
		List<UtiRecorder> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".select",pageInfo.getFilter());
		return queryForList;
	}
	
	@SuppressWarnings("unchecked")
	public List<UtiRecorder> findByParams(Map params)throws Exception {	
		
		List<UtiRecorder> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByParams",params);
		
		return queryForList;
	}
	
	public BigDecimal insert(UtiRecorder entity) {
		BigDecimal serialNo = (BigDecimal) getSqlMapClientTemplate().insert(getNameSpace()+".insert", entity);

		return serialNo;
	}
	
	public boolean update(UtiRecorder entity) {
		boolean result = false;
		String nameSpace = getNameSpace()+".update";
		if(update(nameSpace, entity) == 1) {
			result = true;
		}
		return result;
	}
	
	private int update(String nameSpace, UtiRecorder entity) {
		return getSqlMapClientTemplate().update(nameSpace, entity);
	}


}
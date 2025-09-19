package com.tlg.msSqlSms.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlSms.dao.UsageLogDao;
import com.tlg.msSqlSms.entity.UsageLog;

public class UsageLogDaoImpl extends IBatisBaseDaoImpl<UsageLog, BigDecimal> implements UsageLogDao {
	
	@Override
	public String getNameSpace() {
		return "UsageLog";
	}

	
	public boolean updateForResp(UsageLog usageLog) {
		boolean result = false;
		String nameSpace = getNameSpace()+".updateResp";
		if(getSqlMapClientTemplate().update(nameSpace, usageLog) == 1) {
			result = true;
		}
		return result;
	}


	@Override
	public List<UsageLog> selectBySubmitHour(Map params) throws Exception {
		
		List<UsageLog> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectBySubmitHour",params);
		return queryForList;
	}
}
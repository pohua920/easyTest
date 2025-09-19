package com.tlg.msSqlSms.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlSms.entity.UsageLog;

public interface UsageLogDao extends IBatisBaseDao<UsageLog, BigDecimal> {
	
	
	public boolean updateForResp(UsageLog usageLog);
	
	public List<UsageLog> selectBySubmitHour(Map params)throws Exception;
	
}
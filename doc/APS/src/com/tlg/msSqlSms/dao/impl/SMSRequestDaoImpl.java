package com.tlg.msSqlSms.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlSms.dao.SMSRequestDao;
import com.tlg.msSqlSms.entity.SMSRequest;

public class SMSRequestDaoImpl extends IBatisBaseDaoImpl<SMSRequest, BigDecimal> implements SMSRequestDao {
	
	@Override
	public String getNameSpace() {
		return "SMSRequest";
	}
	
	@SuppressWarnings("unchecked")
	public List<SMSRequest> findTopByParams(Map params)throws Exception {	
		
		List<SMSRequest> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectTopForSendMessage",params);
		
		return queryForList;
	}
	
	public void removeNullData() {
		String nameSpace = getNameSpace()+".deleteByNullData";
		getSqlMapClientTemplate().delete(nameSpace);
	}

}
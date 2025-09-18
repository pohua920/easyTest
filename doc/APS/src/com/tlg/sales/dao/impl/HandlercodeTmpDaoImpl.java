package com.tlg.sales.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.sales.dao.HandlercodeTmpDao;
import com.tlg.sales.entity.HandlercodeTmp;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
public class HandlercodeTmpDaoImpl extends IBatisBaseDaoImpl<HandlercodeTmp, BigDecimal> implements HandlercodeTmpDao {
	@Override
	public String getNameSpace() {
		return "HandlercodeTmp";
	}
	
	@Override
	public Map findHandlercodeIsValidByParams(Map params) throws Exception {
		Map searchResult = (Map) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectHandlercodeIsValid",params);
		return searchResult;
	}
}
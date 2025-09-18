package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.OthBatchPassbookListDao;
import com.tlg.prpins.entity.OthBatchPassbookList;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public class OthBatchPassbookListDaoImpl extends IBatisBaseDaoImpl<OthBatchPassbookList, BigDecimal> implements OthBatchPassbookListDao {

	@Override
	public String getNameSpace() {
		return "OthBatchPassbookList";
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public Integer updateBatchNoByTmpBno(Map params) throws Exception {
		return getSqlMapClientTemplate().update(getNameSpace()+".updateBatchNoByTmpBno",params);
	}
	
	
}
package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.OthBatchPassbookList;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public interface OthBatchPassbookListDao extends IBatisBaseDao<OthBatchPassbookList, BigDecimal> {
	@SuppressWarnings("rawtypes")
	public Integer updateBatchNoByTmpBno(Map params) throws Exception;
}
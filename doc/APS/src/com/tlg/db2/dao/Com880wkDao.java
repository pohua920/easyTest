package com.tlg.db2.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.db2.entity.Com880wk;
import com.tlg.iBatis.IBatisBaseDao;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 *
 */
public interface Com880wkDao extends IBatisBaseDao<Com880wk, BigDecimal> {
	
	public boolean updateForBatch(Com880wk com880wk)throws Exception;
	
	public List<Com880wk> selectByUnsend() throws Exception;
	
	
}
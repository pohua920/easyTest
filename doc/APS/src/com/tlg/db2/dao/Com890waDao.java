package com.tlg.db2.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.db2.entity.Com890wa;
import com.tlg.iBatis.IBatisBaseDao;

/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 *
 */
public interface Com890waDao extends IBatisBaseDao<Com890wa, BigDecimal> {
	
	public boolean updateForBatch(Com890wa com890wa)throws Exception;
	
	public List<Com890wa> selectByUnsend() throws Exception;
	
	
}
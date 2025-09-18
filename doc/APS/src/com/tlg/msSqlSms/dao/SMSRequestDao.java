package com.tlg.msSqlSms.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlSms.entity.SMSRequest;

public interface SMSRequestDao extends IBatisBaseDao<SMSRequest, BigDecimal> {
	
	
	/**
	 * 依照params條件搜尋
	 * 注意，需配合SqlMap的selectByParam條件
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SMSRequest> findTopByParams(Map params)throws Exception;
	
	/**
	 * 刪除message為null或是target為空值的
	 */
	public void removeNullData();
}
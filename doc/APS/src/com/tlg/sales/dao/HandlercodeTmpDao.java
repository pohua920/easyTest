package com.tlg.sales.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.sales.entity.HandlercodeTmp;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
public interface HandlercodeTmpDao extends IBatisBaseDao<HandlercodeTmp, BigDecimal> {
	
	public Map findHandlercodeIsValidByParams(Map params)throws Exception;
}
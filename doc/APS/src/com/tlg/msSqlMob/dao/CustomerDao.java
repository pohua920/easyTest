package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps050DetailResultVo;
import com.tlg.aps.vo.Aps050ResultVo;
import com.tlg.aps.vo.ReturnPolicyNoToG10Vo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.Customer;

public interface CustomerDao extends IBatisBaseDao<Customer, BigDecimal> {
	
	@SuppressWarnings("rawtypes")
	public int countForAps050(Map params)throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<Aps050ResultVo> selectForAps050(Map params)throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<Aps050DetailResultVo> selectForAps050Detail(Map params)throws Exception;
	
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 回傳保單號給數開 */
	@SuppressWarnings("rawtypes")
	public List<ReturnPolicyNoToG10Vo> returnPolicyNoToG10(Map<String, String> params)throws Exception;
}
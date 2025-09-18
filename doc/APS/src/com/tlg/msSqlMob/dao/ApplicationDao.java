package com.tlg.msSqlMob.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlMob.entity.Application;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public interface ApplicationDao extends IBatisBaseDao<Application, BigDecimal>{
	/** mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員  */
	List<Application> selectUncheckApplications(Map params) throws Exception;
	
}
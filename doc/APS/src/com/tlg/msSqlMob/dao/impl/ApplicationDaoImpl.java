package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ApplicationDao;
import com.tlg.msSqlMob.entity.Application;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public class ApplicationDaoImpl extends IBatisBaseDaoImpl<Application, BigDecimal> implements ApplicationDao {
	@Override
	public String getNameSpace() {
		return "Application";
	}
	/** mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員  START*/
	@Override
	public List<Application> selectUncheckApplications(Map params) throws Exception {
		List<Application> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectUncheckApplications",params);
		return queryForList;
	}
	/** mantis：MOB0029，處理人員：CE035，需求單編號：MOB0029 行動裝置險未完成審核通過通知險部人員  END*/
}
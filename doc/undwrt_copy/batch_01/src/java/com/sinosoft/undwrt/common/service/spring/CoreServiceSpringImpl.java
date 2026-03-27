package com.sinosoft.undwrt.common.service.spring;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import com.sinosoft.undwrt.common.service.facade.CoreService;

import ins.framework.dao.GenericDaoHibernate;

/**
 *  紀錄使用者登錄相關資訊
 *  mantis： OTH0126，處理人員：DP0706，需求單編號：OTH0126 核心系統增加登入紀錄
 * @author dp0706
 *
 */
public class CoreServiceSpringImpl  extends GenericDaoHibernate<Serializable, Serializable> implements CoreService{
	
	/** 日志 */
	private static Log log = LogFactory.getLog(CoreServiceSpringImpl.class);

	@Override
	public void insertCoreLoginRecord(String system, String account, String loginDate, String status) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO CORE_LOGIN_RECORD (SYSTEM, ACCOUNT, LOGIN_DATE, STATUS) VALUES (?, ?, ?, ?)");
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, system);
		query.setString(1, account);
		query.setString(2, loginDate);
		query.setString(3, status);
		query.executeUpdate();
		this.getSession().flush();
		
	}

}

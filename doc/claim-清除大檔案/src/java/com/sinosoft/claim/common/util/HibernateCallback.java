package com.sinosoft.claim.common.util;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class HibernateCallback implements org.springframework.orm.hibernate3.HibernateCallback<Object> {
	
	private String hql = "";
	private int pageNo = 0;
	private int pageSize = 0;

	public HibernateCallback(String hql, int pageNo, int pageSize) {
		this.hql = hql;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	@Override
	public Object doInHibernate(Session session) throws HibernateException, SQLException {
		Query query = session.createQuery(hql);
		if(pageNo!=0){
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo - 1) * pageSize);
		}
		return query.list();
	}

}

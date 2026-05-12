package com.sinosoft.claim.common.util;

import ins.framework.common.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Hibernate工具类，执行sql查询
 * @author 中科软
 *
 */
public class HibernateUtils {
	/***
	 * Hibernate 下执行SQL
	 * @param session hibernate的session
	 * @param sql 完整的sql语句
	 * @throws SQLException
	 * @throws HibernateException
	 */
	public static void executeSql(Session session, String sql) throws HibernateException, SQLException {
		// 获取connection,执行静态SQL
		session.createSQLQuery(sql).executeUpdate();
	}

	/***
	 * Hibernate 下执行SQL
	 * @param session hibernate的session
	 * @param sql 完整的sql语句
	 * @throws SQLException
	 * @throws HibernateException
	 */
	public static void executeSql(Session session, String sql , Object ... params) throws Exception {
		// 获取connection,执行静态SQL
		Query query = session.createSQLQuery(sql);
		if(!CommonUtils.isEmpty(params)){
			for(int i = 0 , l = params.length ; i < l ; i++){
				query.setParameter(i, params[i]);
			}
		}
		query.executeUpdate();
	}
	
	/***
	 * 根据sql查询结果集
	 * @param session
	 * @param sql 完整查询sql
	 * @param pageNo 开始的页数
	 * @param pageSize 每页条数
	 * @return
	 */
	public static List<?> findbySql(Session session, String sql, int pageNo, int pageSize) {
		List<?> resultList = new ArrayList<Object>();
		Query query = session.createSQLQuery(sql);
		pageNo = (pageNo < 0) ? 0 : pageNo;
		pageSize = (pageSize < 0) ? 0 : pageSize;
		if (pageNo > 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		resultList = query.list();
		return resultList;
	}

	/***
	 * 根据sql查询结果集(所有记录)
	 * @param session
	 * @param sql 完整查询sql
	 * @return
	 */
	public static List<?> findbySql(Session session, String sql) {
		return findbySql(session, sql, 0, 0);
	}

	/**
	 * 分页查询sql语句
	 * @param session
	 * @param sql 完整sql语句
	 * @param pageNo 开始的页数
	 * @param pageSize 每页显示条数
	 * @param clz 对象类型
	 * @return Page
	 */
	public static Page findPagebySql(Session session, String sql, int pageNo, int pageSize, Class<?> clz) {
		List<?> resultList = findbySql(session, sql, pageNo, pageSize, clz);
		return new Page((pageNo - 1) * pageSize, getCountbySql(session, sql), pageSize, resultList);
	}

	/***
	 * 根据sql查询结果集
	 * @param session
	 * @param sql 完整查询sql
	 * @param pageNo 开始的页数
	 * @param pageSize 每页显示条数
	 * @param clz 返回对象的Class
	 * @return
	 */
	public static List<?> findbySql(Session session, String sql, int pageNo, int pageSize, Class<?> clz) {
		List<?> resultList = new ArrayList<Object>();
		Query query = session.createSQLQuery(sql).addEntity(clz);
		pageNo = (pageNo < 0) ? 0 : pageNo;
		pageSize = (pageSize < 0) ? 0 : pageSize;
		if (pageNo > 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		resultList = query.list();
		return resultList;
	}

	/***
	 * 根据sql查询结果集（所有记录）
	 * @param session
	 * @param sql 完整查询sql
	 * @param clz 返回对象的Class
	 * @return
	 */
	public static List<?> findbySql(Session session, String sql, Class<?> clz) {
		return findbySql(session, sql, 0, 0, clz);
	}

	/***
	 * 根据sql查询Page信息
	 * @param session
	 * @param sql 完整sql语句
	 * @param pageNo 开始页数
	 * @param pageSize 每页显示条数
	 * @return
	 */
	public static Page findPagebySql(Session session, String sql, int pageNo, int pageSize) {
		List<?> resultList = findbySql(session, sql, pageNo, pageSize);
		return new Page((pageNo - 1) * pageSize, getCountbySql(session, sql), pageSize, resultList);
	}

	public static void main(String[] args) {
		int pageNo = -1;
		pageNo = (pageNo < 0) ? 0 : pageNo;
		System.err.println("pageNo=" + pageNo);
	}

	/***
	 * 根据sql 查询记录数
	 * @param session
	 * @param sql 完整sql(不包含count关键字的sql)
	 * @return
	 */
	public static long getCountbySql(Session session, String sql) {
		long count = 0;// 记录数目
		try {
			List<?> resultList = new ArrayList<Object>();
			sql = "select count(1) from ( " + sql + " ) num ";
			Query query = session.createSQLQuery(sql);
			resultList = query.list();
			if (resultList.size() > 0) {
				BigDecimal object = (BigDecimal) resultList.get(0);
				count = object.longValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/***
	 * 根据sql 查询记录数
	 * @param session
	 * @param sql 完整sql(包含count关键字的sql)
	 * @return
	 */
	public static long getCountbyCountSql(Session session, String sql) {
		long count = 0;// 记录数目
		try {
			List<?> resultList = new ArrayList<Object>();
			Query query = session.createSQLQuery(sql);
			resultList = query.list();
			if (resultList.size() > 0) {
				BigDecimal object = (BigDecimal) resultList.get(0);
				count = object.longValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/***
	 * HQL取指定页的数据
	 * @param pageNo
	 * @param pageSize
	 * @param hql
	 * @return
	 */
	public static List<?> findByPageHql(HibernateTemplate hibernateTemplate, final int pageNo, final int pageSize, final String hql, final Object[] args) {
		HibernateCallback callback = new HibernateCallback(hql, pageSize, pageSize);
		return hibernateTemplate.executeFind(callback);
	}
	
	/***
	 * Hibernate 下执行SQL
	 * @param <T>
	 * @param session hibernate的session
	 * @param sql 完整的sql语句
	 * @throws SQLException
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> executeQuery(Class<T> c , Session session, String sql , Object ... params) throws HibernateException, SQLException {
		// 获取connection,执行静态SQL
		Query query = session.createSQLQuery(sql).addEntity(c);
		if(!CommonUtils.isEmpty(params)){
			for(int i = 0 , l = params.length ; i < l ; i++){
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}
}

package com.sinosoft.claim.common.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.HibernateUtils;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

public class CommonServiceSpringImpl extends GenericDaoHibernate implements CommonService {

	public Page findPage(String statements, int pageNo, int pageSize) {
		List<?> resultList = findbySql(super.getSession(), statements, pageNo, pageSize);
		long count = HibernateUtils.getCountbySql(super.getSession(), statements);
		return new Page((pageNo - 1) * pageSize, count, pageSize, resultList);
	}

	public List<?> find(String statements, int pageNo, int pageSize) {
		return findbySql(super.getSession(), statements, pageNo, pageSize);
	}

	/***
	 * 查询并返回map封装的结果集
	 * @param session
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private List<?> findbySql(Session session, String sql, int pageNo, int pageSize) {
		List<?> resultList = new ArrayList<Object>();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		pageNo = (pageNo < 0) ? 0 : pageNo;
		pageSize = (pageSize < 0) ? 0 : pageSize;
		if (pageNo > 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		resultList = query.list();
		return resultList;
	}

	@Override
	public List<?> findByStatements(String statements) {
		Query query = super.getSession().createSQLQuery(statements);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByStatements(String statements , Class<T> clz) {
		Query query = super.getSession().createSQLQuery(statements).addEntity(clz);
		return query.list();
	}
	
	@Override
	public List<?> findBySQL(String statements, String[] params, String[] paramtypes, Map<String, Object> flowParamMap , Class<?> cls) {
		SQLQuery query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(statements);
		if(cls != null){
			query.addEntity(cls);
		}
		if (params != null && params.length > 0) {
			for (int i = 0, l = params.length; i < l; i++) {
				if (paramtypes != null && paramtypes.length > 0 ) {
					if("string".equalsIgnoreCase(paramtypes[i])){
						query.setString(params[i], flowParamMap.get(params[i]).toString());
					} else if ("integer".equalsIgnoreCase(paramtypes[i])){
						query.setInteger(params[i], Integer.parseInt(flowParamMap.get(params[i]).toString()));
					} else if ("date".equalsIgnoreCase(paramtypes[i])){
						query.setTime(params[i], (Date)flowParamMap.get(params[i]));
					} else if("double".equalsIgnoreCase(paramtypes[i])){
						query.setDouble(params[i], Double.valueOf(flowParamMap.get(params[i]).toString()));
					} else if("float".equalsIgnoreCase(paramtypes[i])){
						query.setFloat(params[i], Float.valueOf(flowParamMap.get(params[i]).toString()));
					} else if("list".equalsIgnoreCase(paramtypes[i])){
						query.setParameterList(params[i], (Object[]) flowParamMap.get(params[i]));
					} else {
						query.setString(params[i], flowParamMap.get(params[i]).toString());
					}
				} else {
					query.setString(params[i], flowParamMap.get(params[i]).toString());
				}
			}
		}
		return query.list();
	}
}

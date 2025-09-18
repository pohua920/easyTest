package com.tlg.iBatis.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.iBatis.IBatisBaseEntity;
import com.tlg.util.PageInfo;


public abstract class IBatisBaseDaoImpl<T extends IBatisBaseEntity<OID> ,OID extends Serializable> extends SqlMapClientDaoSupport implements IBatisBaseDao<T,OID> {


	
	@Override
	public int count(Map params) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".count", params);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<T> findByPageInfo(PageInfo pageInfo)throws Exception {
		
		List<T> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".select",pageInfo.getFilter());
		return queryForList;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByParams(Map params)throws Exception {	
		
		List<T> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectByParams",params);
		
		return queryForList;
	}
	
	@SuppressWarnings("unchecked")
	public T findByOid(OID oid) {
		
		T entity = (T) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectByParams", getOidMap(oid));
		return entity;
	}
	@SuppressWarnings("unchecked")
	public T findByUK(Map params){
		
		T entity = (T) getSqlMapClientTemplate().queryForObject(getNameSpace()+".selectByUK", params);
		return entity;
	}
	@SuppressWarnings("unchecked")
	public OID insert(T entity) {
		OID oid = (OID) getSqlMapClientTemplate().insert(getNameSpace()+".insert", entity);
		return oid;
	}

	public boolean remove(OID oid) {
		boolean result = false;
		String nameSpace = getNameSpace()+".delete";
		if(delete(nameSpace, oid) == 1) {
			result = true;
		}
		return result;
	}
	
	public boolean remove(T entity) {
		boolean result = false;
		String nameSpace = getNameSpace()+".deleteByEntity";
		if(delete(nameSpace, entity) == 1) {
			result = true;
		}
		return result;
	}


	public boolean update(T entity) {
		boolean result = false;
		String nameSpace = getNameSpace()+".update";
		if(update(nameSpace, entity) == 1) {
			result = true;
		}
		return result;
	}
	@Override
	public boolean isUnique(T entity) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countByUK", entity);
		if(count == 0 ) {
			return true;
		}
		return false;
	}
	public abstract String getNameSpace();
	
	private int update(String nameSpace, T entity) {
		return getSqlMapClientTemplate().update(nameSpace, entity);
	}
	private int delete(String nameSpace, OID oid) {
		return getSqlMapClientTemplate().delete(nameSpace, oid);
	}
	private int delete(String nameSpace, T entity) {
		return getSqlMapClientTemplate().delete(nameSpace, entity);
	}
	private Map<String, String> getOidMap(OID oid){
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", String.valueOf(oid));	
		//params.put("sortBy", "oid");
		//params.put("sortType", "ASC");
		//params.put("startRow", "1");
		//params.put("endRow", "1");
		return params;
	}
	

}



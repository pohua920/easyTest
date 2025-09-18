package com.tlg.prpins.dao.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasSpDao;

public class HasSpDaoImpl extends SqlMapClientDaoSupport implements HasSpDao{
	
	public String getNameSpace() {
		return "HasSp";
	}

	@Override
	public int runSpHasAgtLionOP(Map<String, Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasAgtLionOP",params);
		return 0;
	}

	@Override
	public int runSpHasAgtLionCH(Map<String, Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasAgtLionCH",params);
		return 0;
	}

	@Override
	public int runSpHasAgtLionAC(Map<String, Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasAgtLionAC",params);
		return 0;
	}

	@Override
	public int runSpHasAgtLionCM(Map<String, Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasAgtLionCM",params);
		return 0;
	}

	@Override
	public int runSpHasAgtLionCL(Map<String, Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasAgtLionCL",params);
		return 0;
	}

	// mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔 -- start
	@Override
	public int runSpHasAgtLawPol(Map<String, Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasAgtLawPol",params);
		return 0;
	}
	// mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔 -- end
	
	/**
	 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同
	 */
	@Override
	public int runSpHasBatchMatchFet(Map<String,Object> params) throws SystemException, Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpHasBatchMatchFet",params);
		return 0;
	}
}
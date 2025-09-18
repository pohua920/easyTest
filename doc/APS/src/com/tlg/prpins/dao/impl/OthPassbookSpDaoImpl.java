package com.tlg.prpins.dao.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.prpins.dao.OthPassbookSpDao;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public class OthPassbookSpDaoImpl extends SqlMapClientDaoSupport implements OthPassbookSpDao{
	
	public String getNameSpace() {
		return "OthPassbookSp";
	}

	@Override
	public void runSpOthPassbookMarP(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookMarP",params);
	}

	@Override
	public void runSpOthPassbookMarE(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookMarE",params);
	}

	@Override
	public void runSpOthPassbookFirP(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookFirP",params);
	}

	@Override
	public void runSpOthPassbookFirE(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookFirE",params);
	}

	@Override
	public void runSpOthPassbookCalP(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookCalP",params);
	}

	@Override
	public void runSpOthPassbookCalE(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookCalE",params);
	}

	@Override
	public void runSpOthPassbookCarP(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookCarP",params);
	}

	@Override
	public void runSpOthPassbookCarE(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookCarE",params);
	}

	@Override
	public void runSpOthPassbookLopP(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookLopP",params);
	}

	@Override
	public void runSpOthPassbookLopE(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpOthPassbookLopE",params);
	}
}
package com.tlg.prpins.dao.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.prpins.dao.MiClaimSpDao;

/** mantis：MOB0019，處理人員：BJ085，需求單編號：MOB0019 理賠審核確認作業 */
public class MiClaimSpDaoImpl extends SqlMapClientDaoSupport implements MiClaimSpDao{
	
	public String getNameSpace() {
		return "MiClaimSp";
	}

	@Override
	public void runSpFetmispTonewclaim(Map<String, Object> params) throws Exception {
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpFetmispTonewclaim",params);
	}
}
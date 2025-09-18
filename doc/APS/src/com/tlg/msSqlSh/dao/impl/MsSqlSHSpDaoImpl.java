package com.tlg.msSqlSh.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tlg.msSqlSh.dao.MsSqlSHSpDao;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
public class MsSqlSHSpDaoImpl extends SqlMapClientDaoSupport implements MsSqlSHSpDao{
	
	public String getNameSpace() {
		return "MsSqlSHSp";
	}

	@Override
	public void runSpCtbcStockholder() throws Exception{
		getSqlMapClientTemplate().queryForObject(getNameSpace()+".executeSpCtbcStockholder");
	}

}
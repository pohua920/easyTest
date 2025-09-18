package com.tlg.msSqlRdm.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlRdm.dao.InrcfilDao;
import com.tlg.msSqlRdm.entity.Inrcfil;
//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
public class InrcfilDaoImpl extends IBatisBaseDaoImpl<Inrcfil, BigDecimal> implements InrcfilDao {

	
	@Override
	public String getNameSpace() {
		return "Inrcfil";
	}

	@Override
	public List<Inrcfil> selectExecuteList() throws Exception {
		return  getSqlMapClientTemplate().queryForList(getNameSpace() + ".selectExecuteList" );
		
	}
	
	
    
}
package com.tlg.msSqlRdm.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.msSqlRdm.entity.Inrcfil;
//mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
public interface InrcfilDao extends IBatisBaseDao<Inrcfil, BigDecimal> {
    
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Inrcfil> selectExecuteList() throws Exception;
    
}
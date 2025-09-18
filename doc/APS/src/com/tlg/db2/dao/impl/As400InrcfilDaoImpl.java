package com.tlg.db2.dao.impl;

import com.tlg.db2.dao.As400InrcfilDao;
import com.tlg.db2.entity.As400Inrcfil;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;


/**
 * mantis：REI00021，處理人員：DP0706，需求單編號：REI00021 再保帳單介接400
 *
 */
public class As400InrcfilDaoImpl extends IBatisBaseDaoImpl<As400Inrcfil, String> implements As400InrcfilDao {
	
	@Override
	public String getNameSpace() {
		return "As400Inrcfil";
	}

}
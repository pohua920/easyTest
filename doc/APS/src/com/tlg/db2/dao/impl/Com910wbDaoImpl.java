package com.tlg.db2.dao.impl;

import com.tlg.db2.dao.Com910wbDao;
import com.tlg.db2.entity.Com910wb;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;


/**
 * @author bi086
 *
 */
public class Com910wbDaoImpl extends IBatisBaseDaoImpl<Com910wb, String> implements Com910wbDao {
	
	@Override
	public String getNameSpace() {
		return "Com910wb";
	}

}
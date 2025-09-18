package com.tlg.db2.dao.impl;

import java.math.BigDecimal;

import com.tlg.db2.dao.Com890wbDao;
import com.tlg.db2.entity.Com890wb;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;


/**
 * mantis：OTH0132，處理人員：BI086，需求單編號：OTH0132  保單存摺AS400資料寫入核心中介Table
 * @author bi086
 *
 */
public class Com890wbDaoImpl extends IBatisBaseDaoImpl<Com890wb, BigDecimal> implements Com890wbDao {
	
	@Override
	public String getNameSpace() {
		return "Com890wb";
	}

}
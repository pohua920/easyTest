package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.MetaAmlResultMainDao;
import com.tlg.prpins.entity.MetaAmlResultMain;

/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */
public class MetaAmlResultMainDaoImpl extends IBatisBaseDaoImpl<MetaAmlResultMain, BigDecimal> implements MetaAmlResultMainDao {
	
	@Override
	public String getNameSpace() {
		return "MetaAmlResultMain";
	}

}
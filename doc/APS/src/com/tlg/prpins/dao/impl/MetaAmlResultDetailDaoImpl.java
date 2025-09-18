package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.MetaAmlResultDetailDao;
import com.tlg.prpins.entity.MetaAmlResultDetail;

/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */
public class MetaAmlResultDetailDaoImpl extends IBatisBaseDaoImpl<MetaAmlResultDetail, BigDecimal> implements MetaAmlResultDetailDao {
	
	@Override
	public String getNameSpace() {
		return "MetaAmlResultDetail";
	}

}
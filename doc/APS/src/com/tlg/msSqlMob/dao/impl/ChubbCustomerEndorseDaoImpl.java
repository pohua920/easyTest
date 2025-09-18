package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ChubbCustomerEndorseDao;
import com.tlg.msSqlMob.entity.ChubbCustomerEndorse;

import java.math.BigDecimal;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
public class ChubbCustomerEndorseDaoImpl extends IBatisBaseDaoImpl<ChubbCustomerEndorse, BigDecimal> implements ChubbCustomerEndorseDao {
	@Override
	public String getNameSpace() {
		return "ChubbCustomerEndorse";
	}
}
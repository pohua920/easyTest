package com.tlg.msSqlMob.dao.impl;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.ChubbInsuredEndorseDao;
import com.tlg.msSqlMob.entity.ChubbInsuredEndorse;

import java.math.BigDecimal;

/** mantis：，處理人員：BJ016，需求單編號：  行動裝置險安達線下批單資料檔下載*/
public class ChubbInsuredEndorseDaoImpl extends IBatisBaseDaoImpl<ChubbInsuredEndorse, BigDecimal> implements ChubbInsuredEndorseDao {
	@Override
	public String getNameSpace() {
		return "ChubbInsuredEndorse";
	}
}
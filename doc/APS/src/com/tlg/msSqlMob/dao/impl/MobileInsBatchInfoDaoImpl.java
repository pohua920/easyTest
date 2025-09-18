package com.tlg.msSqlMob.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.msSqlMob.dao.MobileInsBatchInfoDao;
import com.tlg.msSqlMob.entity.MobileInsBatchInfo;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
public class MobileInsBatchInfoDaoImpl extends IBatisBaseDaoImpl<MobileInsBatchInfo, BigDecimal> implements MobileInsBatchInfoDao {
	@Override
	public String getNameSpace() {
		return "MobileInsBatchInfo";
	}
}
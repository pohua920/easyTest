package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasBatchInfoDao;
import com.tlg.prpins.entity.HasBatchInfo;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasBatchInfoDaoImpl extends IBatisBaseDaoImpl<HasBatchInfo, BigDecimal> implements HasBatchInfoDao {
	
	@Override
	public String getNameSpace() {
		return "HasBatchInfo";
	}
	
}
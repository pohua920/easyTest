package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasBatchLogDao;
import com.tlg.prpins.entity.HasBatchLog;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasBatchLogDaoImpl extends IBatisBaseDaoImpl<HasBatchLog, BigDecimal> implements HasBatchLogDao {
	
	@Override
	public String getNameSpace() {
		return "HasBatchLog";
	}
	
}
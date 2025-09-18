package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtBatchMainDao;
import com.tlg.prpins.entity.HasAgtBatchMain;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasAgtBatchMainDaoImpl extends IBatisBaseDaoImpl<HasAgtBatchMain, BigDecimal> implements HasAgtBatchMainDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtBatchMain";
	}
	
}
package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.HasAgtBatchDtlDao;
import com.tlg.prpins.entity.HasAgtBatchDtl;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public class HasAgtBatchDtlDaoImpl extends IBatisBaseDaoImpl<HasAgtBatchDtl, BigDecimal> implements HasAgtBatchDtlDao {
	
	@Override
	public String getNameSpace() {
		return "HasAgtBatchDtl";
	}
	
	@Override
	public Integer updateByParams(Map<String, Object> params) throws Exception {
		return (Integer) getSqlMapClientTemplate().update(getNameSpace() + ".updateByParams", params);
	}
	
}
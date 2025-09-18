package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.HasAgtBatchDtl;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface HasAgtBatchDtlDao extends IBatisBaseDao<HasAgtBatchDtl, BigDecimal> {
	
	public Integer updateByParams(Map<String,Object> params) throws Exception;
	
}
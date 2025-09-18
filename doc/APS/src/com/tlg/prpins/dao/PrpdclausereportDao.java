package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Prpdclausereport;

public interface PrpdclausereportDao extends IBatisBaseDao<Prpdclausereport, BigDecimal> {
	public String selectForEpolicy(Map params)throws Exception;
}
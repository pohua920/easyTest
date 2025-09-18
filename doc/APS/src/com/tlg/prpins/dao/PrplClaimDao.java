package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.PrplClaim;

public interface PrplClaimDao extends IBatisBaseDao<PrplClaim, BigDecimal> {
	
	public List<String> findFirClaimByParams(Map params)throws Exception;
}
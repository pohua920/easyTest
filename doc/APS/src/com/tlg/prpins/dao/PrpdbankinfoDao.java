package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.BankInfoVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.Prpdbankinfo;

public interface PrpdbankinfoDao extends IBatisBaseDao<Prpdbankinfo, BigDecimal> {
	
	public List<BankInfoVo> findByParamsForWs(Map params)throws Exception;
}
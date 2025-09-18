package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtYcb02;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
public interface FirAgtYcb02Dao extends IBatisBaseDao<FirAgtYcb02, BigDecimal> {
	
	public List<FirAgtYcb02> selectForGenFile(Map<String,String> params) throws Exception;
}
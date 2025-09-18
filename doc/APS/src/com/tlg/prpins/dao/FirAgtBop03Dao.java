package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtBop03;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public interface FirAgtBop03Dao extends IBatisBaseDao<FirAgtBop03, BigDecimal> {
	
	public List<FirAgtBop03> selectForGenFile(Map<String,String> params) throws Exception;
}
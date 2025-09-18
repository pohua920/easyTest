package com.tlg.xchg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.xchg.entity.CwpLiaLia07061aqResult;

public interface CwpLiaLia07061aqResultDao extends IBatisBaseDao<CwpLiaLia07061aqResult, BigDecimal> {
	
	
	public void processInBatch(List<CwpLiaLia07061aqResult> list);
}
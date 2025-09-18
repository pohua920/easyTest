package com.tlg.prpins.dao;

import java.math.BigDecimal;

import com.tlg.aps.vo.FirHandler1codeVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchInfo;

public interface FirBatchInfoDao extends IBatisBaseDao<FirBatchInfo, BigDecimal> {
	
	//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 
	public FirHandler1codeVo selectHandler1code() throws Exception;
}
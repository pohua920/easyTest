package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;

import com.tlg.aps.vo.FirHandler1codeVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchInfoDao;
import com.tlg.prpins.entity.FirBatchInfo;

public class FirBatchInfoDaoImpl extends IBatisBaseDaoImpl<FirBatchInfo, BigDecimal> implements FirBatchInfoDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchInfo";
	}
	
	/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程  **/
	@Override
	public FirHandler1codeVo selectHandler1code() throws Exception {
		FirHandler1codeVo handler1codeVo = (FirHandler1codeVo) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".selectHandler1code");
		return handler1codeVo;
	}
	
}
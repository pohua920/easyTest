package com.tlg.prpins.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps031ExcelVo;
import com.tlg.iBatis.impl.IBatisBaseDaoImpl;
import com.tlg.prpins.dao.FirBatchPins01SpMainDao;
import com.tlg.prpins.entity.FirBatchPins01SpMain;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public class FirBatchPins01SpMainDaoImpl extends IBatisBaseDaoImpl<FirBatchPins01SpMain, BigDecimal> implements FirBatchPins01SpMainDao {
	
	@Override
	public String getNameSpace() {
		return "FirBatchPins01SpMain";
	}

	@Override
	public List<Aps031ExcelVo> selectForAps031Excel(Map params) throws Exception {
		List<Aps031ExcelVo> queryForList = getSqlMapClientTemplate().queryForList(getNameSpace()+".selectForAps031Excel",params);
		return queryForList;
	}
	
	@Override
	public void truncate() throws Exception {
		getSqlMapClientTemplate().update(getNameSpace()+".truncate");
	}
	
	@Override
	public int countForAps031Excel(Map params) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".countForAps031Excel", params);
	}
}
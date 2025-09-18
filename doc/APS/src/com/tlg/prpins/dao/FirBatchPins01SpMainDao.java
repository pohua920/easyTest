package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps031ExcelVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchPins01SpMain;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface FirBatchPins01SpMainDao extends IBatisBaseDao<FirBatchPins01SpMain, BigDecimal> {

	public List<Aps031ExcelVo> selectForAps031Excel(Map params) throws Exception;
	
	public void truncate() throws Exception;
	
	public int countForAps031Excel(Map params) throws Exception;
	
}
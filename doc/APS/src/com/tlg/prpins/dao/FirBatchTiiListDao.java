package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.FirTiiDataVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirBatchTiiList;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public interface FirBatchTiiListDao extends IBatisBaseDao<FirBatchTiiList, BigDecimal> {
	
	public List<FirTiiDataVo> selectForCountProcType(Map<String, String> params) throws Exception;
}
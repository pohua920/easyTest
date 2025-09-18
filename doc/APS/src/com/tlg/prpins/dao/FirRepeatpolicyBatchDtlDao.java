package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps042ImportVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
public interface FirRepeatpolicyBatchDtlDao extends IBatisBaseDao<FirRepeatpolicyBatchDtl, BigDecimal>{
	public List<Aps042ImportVo> selectForAps042Import(Map<String,String> params) throws Exception;
}
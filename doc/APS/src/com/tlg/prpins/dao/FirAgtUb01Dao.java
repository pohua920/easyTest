package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps041MainVo;
import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.util.PageInfo;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
public interface FirAgtUb01Dao extends IBatisBaseDao<FirAgtUb01, BigDecimal>{
	public List<Aps041MainVo> selectForAps041(PageInfo pageInfo)throws Exception;
	
	public int countForAps041(Map<String,String> params)throws Exception;
}
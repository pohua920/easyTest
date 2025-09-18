package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.util.PageInfo;
import com.tlg.aps.vo.Aps002DetailVo;
import com.tlg.iBatis.IBatisBaseDao;

public interface FirCtbcBatchDtlDao extends IBatisBaseDao<FirCtbcBatchDtl, BigDecimal> {
	
	/**
	 * 取得回饋檔資料
	 */
	public List<FirCtbcRst> selectForFeedback(Map<String,Object> params)throws Exception;
	
	/**
	 * APS002進入明細頁
	 * */
	public Aps002DetailVo selectForAps002Detail(Map<String,Object> params)throws Exception;
	
	// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯
	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	public List<Aps002DetailVo> selectJoinStl(Map<String,Object> params)throws Exception;
	public int countJoinStl(Map<String,Object> params)throws Exception;
	
}
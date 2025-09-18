package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.util.PageInfo;
import com.tlg.aps.vo.Aps003DetailVo;
import com.tlg.iBatis.IBatisBaseDao;

public interface FirCtbcRstDao extends IBatisBaseDao<FirCtbcRst, BigDecimal> {

	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	public int countForAps003Detail(Map<String,Object> params)throws Exception;
	/**
	 * APS003查詢結果
	 * mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)*/
	public List<Aps003DetailVo> selectForAps003Detail(PageInfo pageInfo)throws Exception;
	
}
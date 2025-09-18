package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.PbPremcalcCklist;

public interface PbPremcalcCklistDao extends IBatisBaseDao<PbPremcalcCklist, BigDecimal> {
	
	/**
	 * 取得各項分數
	 */
	public List<Map> selectByScore(Map params)throws Exception;
	
	/**
	 * 取得結果分數
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public BigDecimal selectByResultScore(Map params) throws Exception;
}
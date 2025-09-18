package com.tlg.prpins.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tlg.iBatis.IBatisBaseDao;
import com.tlg.prpins.entity.PbQueryDetail;

public interface PbQueryDetailDao extends IBatisBaseDao<PbQueryDetail, BigDecimal> {
	
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
	/**
	 * Pb取得各項分數
	 */
	public List<Map> selectByPbScore(Map params)throws Exception;
	
	/**
	 * Pb取得結果分數
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public BigDecimal selectByPbResultScore(Map params) throws Exception;
	
}
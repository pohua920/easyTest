package com.sinosoft.claim.common.service.facade;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.claim.common.vo.EndorseDto;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.sysframework.exceptionlog.UserException;

public interface EndorseService {
	/**
	 * 获得批单
	 * @param endorseNo 批单号
	 * @return 批单对象
	 * @throws Exception
	 */
	public EndorseDto findByPrimaryKey(String endorseNo) throws SQLException,UserException,Exception;
	/**
	 * 获得批单
	 * @param policyNo 保单号码
	 * @return 批单对象
	 * @throws Exception
	 */
	public EndorseDto findByConditions(String policyNo) throws SQLException,UserException,Exception;
	/**
	 * 获得批单
	 * @param conditions 查询条件
	 * @return 批单对象
	 * @throws Exception
	 */
	public List<PrpPhead> findByPrpPheadConditions(String conditions) throws SQLException,UserException,Exception;
	/**
	 * 交验此保单是否处於批改状态
	 * @param policyNo 保单号码
	 * @return
	 * @throws Exception
	 */
	public int  checkStatus(String policyNo) throws Exception;
	
}

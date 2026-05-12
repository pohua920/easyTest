package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpPhead;

public interface PrpPheadService {
	/**
	 * 按条件查询多条数据
	 * @param conditions 查询条件
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @return Collection
	 * @throws Exception
	 */
	public List<PrpPhead> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;

	/**
	 * 按主键查找一条数据
	 * @param endorseNo 批单号码
	 * @return PrpPheadDto
	 * @throws Exception
	 */
	public PrpPhead findByPrimaryKey(String endorseNo) throws Exception;

	/***
	 * 根据保单号查批单号
	 * @param policyNo 保单号
	 * @return
	 * @throws Exception
	 */
	public List<PrpPhead> findByPolicyNo(String policyNo) throws Exception;

	/***
	 * 根据查询条件查询
	 * @param policyNo 保单号
	 * @return
	 * @throws Exception
	 */
	public List<PrpPhead> findByQueryConditions(String conditions) throws Exception;

	/**
	 * 根据sql语句查询有多少条立案信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public long getCount(String conditions) throws Exception;
}

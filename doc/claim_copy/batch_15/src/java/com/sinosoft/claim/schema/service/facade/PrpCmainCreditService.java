package com.sinosoft.claim.schema.service.facade;
/**
 * 信用险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainCredit;

public interface PrpCmainCreditService {
	
	/**
	 * 信用险保单信息信息
	 * @param PrpCmainCredit ：传入的信用险保单信息
	 */
	public void save(PrpCmainCredit PrpCmainCredit) throws Exception;
	
	/**
	 * 保存信用险保单信息
	 * @param list  :传入的信用险保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainCredit> list) throws Exception;
	
	/**
	 * 删除信用险保单信息信息
	 * @param policyNo ：传入的信用险保单信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新信用险保单信息信息
	 * @param PrpCmainCredit :传入需要更新的信用险保单信息
	 */
	public void update(PrpCmainCredit PrpCmainCredit) throws Exception;

	/**
	 * 根据信用险保单信息编号查询出信用险保单信息信息
	 * @param policyNo ：传入的信用险保单信息编号
	 * @return 返回信用险保单信息
	 */
	public PrpCmainCredit findPrpCmainCredit(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的信用险保单信息页面信息
	 */
	public Page findPrpCmainCredit(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 信用险保单信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的信用险保单信息  的列表
	 */
	public List<PrpCmainCredit> findPrpCmainCredit(QueryRule queryRule) throws Exception;
}

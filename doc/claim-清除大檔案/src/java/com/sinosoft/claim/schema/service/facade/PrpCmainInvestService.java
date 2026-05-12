package com.sinosoft.claim.schema.service.facade;
/**
 * 保单全貌接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainInvest;

public interface PrpCmainInvestService {
	
	/**
	 * 立案基本信息信息
	 * @param PrpCmainInvest ：传入的保单全貌
	 */
	public void save(PrpCmainInvest PrpCmainInvest) throws Exception;
	
	/**
	 * 保存立案基本信息
	 * @param list  :传入的立案基本信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainInvest> list) throws Exception;
	
	/**
	 * 删除立案基本信息信息
	 * @param policyNo ：传入的立案基本信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新立案基本信息信息
	 * @param PrpCmainInvest :传入需要更新的立案基本信息
	 */
	public void update(PrpCmainInvest PrpCmainInvest) throws Exception;

	/**
	 * 根据立案基本信息编号查询出立案基本信息信息
	 * @param policyNo ：传入的立案基本信息编号
	 * @return 返回立案基本信息
	 */
	public PrpCmainInvest findPrpCmainInvest(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的立案基本信息页面信息
	 */
	public Page findPrpCmainInvest(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取立案基本信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 立案基本信息 的列表
	 */
	public List<PrpCmainInvest> findPrpCmainInvest(QueryRule queryRule) throws Exception;
}

package com.sinosoft.claim.schema.service.facade;
/**
 * 财产险保单信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainProp;

public interface PrpCmainPropService {
	
	/**
	 * 立案基本信息信息
	 * @param PrpCmainProp ：传入的财产险保单信息
	 */
	public void save(PrpCmainProp PrpCmainProp) throws Exception;
	
	/**
	 * 保存立案基本信息
	 * @param list  :传入的立案基本信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCmainProp> list) throws Exception;
	
	/**
	 * 删除财产险保单信息信息
	 * @param policyNo ：传入的财产险保单信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新财产险保单信息信息
	 * @param PrpCmainProp :传入需要更新的财产险保单信息
	 */
	public void update(PrpCmainProp PrpCmainProp) throws Exception;

	/**
	 * 根据财产险保单信息编号查询出财产险保单信息信息
	 * @param policyNo ：传入的财产险保单信息编号
	 * @return 返回财产险保单信息
	 */
	public PrpCmainProp findPrpCmainProp(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的财产险保单信息页面信息
	 */
	public Page findPrpCmainProp(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取  财产险保单信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的 财产险保单信息 的列表
	 */
	public List<PrpCmainProp> findPrpCmainProp(QueryRule queryRule) throws Exception;
}

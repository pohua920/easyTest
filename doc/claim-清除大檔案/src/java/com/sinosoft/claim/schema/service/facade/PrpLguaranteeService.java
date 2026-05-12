package com.sinosoft.claim.schema.service.facade;
/**
 * 担保信息表接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLguarantee;

public interface PrpLguaranteeService {
	
	/**
	 * 担保信息
	 * @param PrpLguarantee ：传入的担保
	 */
	public void save(PrpLguarantee prpLguarantee) throws Exception;
	
	/**
	 * 保存担保信息
	 * @param list  :传入的担保信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLguarantee> list) throws Exception;
	
	/**
	 * 删除担保信息
	 * @param policyNo ：传入的担保编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新担保信息
	 * @param PrpLguarantee :传入需要更新的担保
	 */
	public void update(PrpLguarantee prpLguarantee) throws Exception;

	/**
	 * 根据担保编号查询出担保信息
	 * @param policyNo ：传入的担保编号
	 * @return 返回担保
	 */
	public PrpLguarantee findPrpLguarantee(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的担保页面信息
	 */
	public Page findPrpLguarantee(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	/**根据查询对象获取担保信息  的集合
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpLguarantee> findPrpLguarantee(QueryRule queryRule) throws Exception;
}

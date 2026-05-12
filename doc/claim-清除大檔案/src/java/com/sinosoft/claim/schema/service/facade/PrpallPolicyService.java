package com.sinosoft.claim.schema.service.facade;
/**
 * 保单全貌接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpallPolicy;

public interface PrpallPolicyService {
	
	/**
	 * 保存保单全貌信息
	 * @param PrpallPolicy ：传入的保单全貌
	 */
	public void save(PrpallPolicy prpallPolicy) throws Exception;
	
	/**
	 * 保存保单全貌信息
	 * @param list  :传入的保单全貌信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpallPolicy> list) throws Exception;
	
	/**
	 * 删除保单全貌信息
	 * @param policyNo ：传入的保单全貌编号
	 */
	public void delete(String policyNo) throws Exception;

	/**
	 * 更新保单全貌信息
	 * @param PrpallPolicy :传入需要更新的保单全貌
	 */
	public void update(PrpallPolicy prpallPolicy) throws Exception;

	/**
	 * 根据保单全貌编号查询出保单全貌信息
	 * @param policyNo ：传入的保单全貌编号
	 * @return 返回保单全貌
	 */
	public PrpallPolicy findPrpallPolicy(String policyNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保单全貌页面信息
	 */
	public Page findPrpallPolicy(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的保单全貌列表信息
	 */
	public List<PrpallPolicy> findPrpallPolicy(QueryRule queryRule) throws Exception;
}

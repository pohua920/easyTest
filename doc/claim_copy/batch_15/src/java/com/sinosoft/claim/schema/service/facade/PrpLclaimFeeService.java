package com.sinosoft.claim.schema.service.facade;
/**
 * 估损金额接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimFee;
import com.sinosoft.claim.schema.model.PrpLclaimFeeId;

public interface PrpLclaimFeeService {
	
	/**
	 * 估损金额信息
	 * @param PrpLclaimFee ：传入的估损金额
	 */
	public void save(PrpLclaimFee prpLclaimFee) throws Exception;
	
	/**
	 * 保存估损金额信息
	 * @param list  :传入的估损金额信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimFee> list) throws Exception;
	
	/**
	 * 删除估损金额信息
	 * @param policyNo ：传入的估损金额编号
	 */
	public void delete(PrpLclaimFeeId prpLclaimFeeId) throws Exception;

	/**
	 * 更新估损金额信息
	 * @param PrpLclaimFee :传入需要更新的估损金额
	 */
	public void update(PrpLclaimFee prpLclaimFee) throws Exception;

	/**
	 * 根据估损金额编号查询出估损金额信息
	 * @param policyNo ：传入的估损金额编号
	 * @return 返回估损金额
	 */
	public PrpLclaimFee findPrpLclaimFee(PrpLclaimFeeId prpLclaimFeeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的估损金额页面信息
	 */
	public Page findPrpLclaimFee(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 估损金额 的列表
	 * @param queryRule 查询对象
	 * @return 包含的  估损金额的列表
	 */
	public List<PrpLclaimFee> findPrpLclaimFee(QueryRule queryRule) throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改方法
	 */
	public void saveOrUpdate(PrpLclaimFee prpLclaimFee) throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改方法
	 */
	public void saveOrUpdate(List<PrpLclaimFee> list) throws Exception;
	/**
	 * @param claimNo
	 * @throws Exception
	 * 根据立案号删除信息
	 */
	public void deleteByClaimNo(String claimNo) throws Exception ;
}

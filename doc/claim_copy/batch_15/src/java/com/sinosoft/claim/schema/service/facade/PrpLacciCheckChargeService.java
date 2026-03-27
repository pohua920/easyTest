package com.sinosoft.claim.schema.service.facade;
/**
 * 意健险调查费用接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLacciCheckCharge;
import com.sinosoft.claim.schema.model.PrpLacciCheckChargeId;

public interface PrpLacciCheckChargeService {
	
	/**
	 * 保存意健险调查费用信息
	 * @param prpLacciCheckCharge ：传入的意健险调查费用
	 */
	public void save(PrpLacciCheckCharge prpLacciCheckCharge) throws Exception;
	
	/**
	 * 意健险调查费用信息
	 * @param list  :传入的意健险调查费用信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLacciCheckCharge> list) throws Exception;
	
	/**
	 * 删除意健险调查费用信息
	 * @param prpLacciCheckChargeId ：传入的意健险调查费用编号
	 */
	public void delete(PrpLacciCheckChargeId prpLacciCheckChargeId) throws Exception;

	/**
	 * 更新意健险调查费用信息
	 * @param prpLacciCheckCharge :传入需要更新的意健险调查费用
	 */
	public void update(PrpLacciCheckCharge prpLacciCheckCharge) throws Exception;

	/**
	 * 根据意健险调查费用编号查询出意健险调查费用信息
	 * @param prpLacciCheckChargeId ：传入的意健险调查费用编号
	 * @return 返回意健险调查费用
	 */
	public PrpLacciCheckCharge findPrpLacciCheckCharge(PrpLacciCheckChargeId prpLacciCheckChargeId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的意健险调查费用页面信息
	 */
	public Page findPrpLacciCheckCharge(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 意健险调查费用 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 意健险调查费用 的列表
	 */
	public List<PrpLacciCheckCharge> findPrpLacciCheckCharge(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据意健险调查费用编号查询出意健险调查费用信息
	 * @param certiNo ：传入的意健险调查费用编号
	 * @return 返回意健险调查费用
	 */
	public PrpLacciCheckCharge findPrpLacciCheckCharge(String certiNo) throws Exception;

	public List<PrpLacciCheckCharge> findByConditions(String condition) throws Exception;
}

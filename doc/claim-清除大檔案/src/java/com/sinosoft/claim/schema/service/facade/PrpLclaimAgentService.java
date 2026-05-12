package com.sinosoft.claim.schema.service.facade;
/**
 * 代理赔保单接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLclaimAgent;

public interface PrpLclaimAgentService {
	
	/**
	 * 代理赔保单信息
	 * @param PrpLclaimAgent ：传入的代理赔保单信息
	 */
	public void save(PrpLclaimAgent prpLclaimAgent) throws Exception;
	
	/**
	 * 保存代理赔保单信息
	 * @param list  :传入的代理赔保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLclaimAgent> list) throws Exception;
	
	/**
	 * 删除代理赔保单信息信息
	 * @param policyNo ：传入的代理赔保单信息编号
	 */
	public void delete(String claimNo) throws Exception;

	/**
	 * 更新代理赔保单信息信息
	 * @param PrpLclaimAgent :传入需要更新的代理赔保单信息
	 */
	public void update(PrpLclaimAgent prpLclaimAgent) throws Exception;

	/**
	 * 根据代理赔保单信息编号查询出代理赔保单信息信息
	 * @param policyNo ：传入的代理赔保单信息编号
	 * @return 返回代理赔保单信息
	 */
	public PrpLclaimAgent findPrpLclaimAgent(String claimNo) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代理赔保单信息页面信息
	 */
	public Page findPrpLclaimAgent(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取代理赔保单信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 代理赔保单信息 的列表
	 */
	public List<PrpLclaimAgent> findPrpLclaimAgent(QueryRule queryRule) throws Exception;
}

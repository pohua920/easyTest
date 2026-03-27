package com.sinosoft.claim.schema.service.facade;
/**
 * 代理接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCagent;
import com.sinosoft.claim.schema.model.PrpCagentId;

public interface PrpCagentService {
	
	/**
	 * 保存代理信息
	 * @param prpCagent ：传入的代理
	 */
	public void save(PrpCagent prpCagent) throws Exception;
	
	/**
	 * 代理信息
	 * @param list  :传入的代理信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCagent> list) throws Exception;
	
	/**
	 * 删除代理信息
	 * @param prpCagentId ：传入的代理编号
	 */
	public void delete(PrpCagentId prpCagentId) throws Exception;

	/**
	 * 更新代理信息
	 * @param prpCagent :传入需要更新的代理
	 */
	public void update(PrpCagent prpCagent) throws Exception;

	/**
	 * 根据代理编号查询出代理信息
	 * @param prpCagentId ：传入的代理编号
	 * @return 返回代理
	 */
	public PrpCagent findPrpCagent(PrpCagentId prpCagentId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代理页面信息
	 */
	public Page findPrpCagent(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取代理对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的代理页面信息
	 */
	public List<PrpCagent> findPrpCagent(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据代理编号查询出代理信息
	 * @param certiNo ：传入的代理编号
	 * @return 返回代理
	 */
	public PrpCagent findPrpCagent(String certiNo) throws Exception;
}

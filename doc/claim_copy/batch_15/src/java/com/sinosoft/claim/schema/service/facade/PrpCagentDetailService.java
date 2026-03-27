package com.sinosoft.claim.schema.service.facade;
/**
 * 代理的详细信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCagentDetail;
import com.sinosoft.claim.schema.model.PrpCagentDetailId;

public interface PrpCagentDetailService {
	
	/**
	 * 保存代理的详细信息信息
	 * @param PrpCagentDetail ：传入的代理的详细信息
	 */
	public void save(PrpCagentDetail PrpCagentDetail) throws Exception;
	
	/**
	 * 代理的详细信息信息
	 * @param list  :传入的代理的详细信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCagentDetail> list) throws Exception;
	
	/**
	 * 删除代理的详细信息信息
	 * @param PrpCagentDetailId ：传入的代理的详细信息编号
	 */
	public void delete(PrpCagentDetailId PrpCagentDetailId) throws Exception;

	/**
	 * 更新代理的详细信息信息
	 * @param PrpCagentDetail :传入需要更新的代理的详细信息
	 */
	public void update(PrpCagentDetail PrpCagentDetail) throws Exception;

	/**
	 * 根据代理的详细信息编号查询出代理的详细信息信息
	 * @param PrpCagentDetailId ：传入的代理的详细信息编号
	 * @return 返回代理的详细信息
	 */
	public PrpCagentDetail findPrpCagentDetail(PrpCagentDetailId PrpCagentDetailId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的代理的详细信息页面信息
	 */
	public Page findPrpCagentDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取对象的列表
	 * @param queryRule 查询对象
	 * @return 包含的代理列表信息
	 */
	public List<PrpCagentDetail> findPrpCagentDetail(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据代理的详细信息编号查询出代理的详细信息信息
	 * @param certiNo ：传入的代理的详细信息编号
	 * @return 返回代理的详细信息
	 */
	public PrpCagentDetail findPrpCagentDetail(String certiNo) throws Exception;
}

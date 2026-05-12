package com.sinosoft.claim.schema.service.facade;
/**
 * 查勘/代查勘扩展接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLcheckExt;
import com.sinosoft.claim.schema.model.PrpLcheckExtId;

public interface PrpLcheckExtService {
	
	/**
	 * 保存查勘/代查勘扩展信息
	 * @param prpLcheckExt ：传入的查勘/代查勘扩展
	 */
	public void save(PrpLcheckExt prpLcheckExt) throws Exception;
	
	/**
	 * 查勘/代查勘扩展信息
	 * @param list  :传入的查勘/代查勘扩展信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcheckExt> list) throws Exception;
	
	/**
	 * 删除查勘/代查勘扩展信息
	 * @param prpLcheckExtId ：传入的查勘/代查勘扩展编号
	 */
	public void delete(PrpLcheckExtId prpLcheckExtId) throws Exception;

	/**
	 * 更新查勘/代查勘扩展信息
	 * @param prpLcheckExt :传入需要更新的查勘/代查勘扩展
	 */
	public void update(PrpLcheckExt prpLcheckExt) throws Exception;

	/**
	 * 根据查勘/代查勘扩展编号查询出查勘/代查勘扩展信息
	 * @param prpLcheckExtId ：传入的查勘/代查勘扩展编号
	 * @return 返回查勘/代查勘扩展
	 */
	public PrpLcheckExt findPrpLcheckExt(PrpLcheckExtId prpLcheckExtId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的查勘/代查勘扩展页面信息
	 */
	public Page findPrpLcheckExt(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 查勘/代查勘扩展页面信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的查勘/代查勘扩展页面信息  的列表
	 */
	public List<PrpLcheckExt> findPrpLcheckExt(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据查勘/代查勘扩展编号查询出查勘/代查勘扩展信息
	 * @param certiNo ：传入的查勘/代查勘扩展编号
	 * @return 返回查勘/代查勘扩展
	 */
	public PrpLcheckExt findPrpLcheckExt(String certiNo) throws Exception;
}

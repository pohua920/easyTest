package com.sinosoft.claim.schema.service.facade;
/**
 * 备注摘要接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLextId;


public interface PrpLextService {
	
	/**
	 * 保存备注摘要信息
	 * @param prpLext ：传入的备注摘要
	 */
	public void save(PrpLext prpLext) throws Exception;
	
	/**
	 * 保存备注摘要信息
	 * @param list:保存备注摘要信息
	 */
	public void save(List<PrpLext> list) throws Exception;
	
	/**
	 * 删除备注摘要信息
	 * @param prpLextId ：传入的备注摘要编号
	 */
	public void delete(PrpLextId prpLextId) throws Exception;

	/**
	 * 更新备注摘要信息
	 * @param prpLext :传入需要更新的备注摘要
	 */
	public void update(PrpLext prpLext) throws Exception;

	/**
	 * 根据备注摘要编号查询出备注摘要信息
	 * @param prpLextId ：传入的备注摘要编号
	 * @return 返回备注摘要
	 */
	public PrpLext findPrpLext(PrpLextId prpLextId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的备注摘要页面信息
	 */
	public Page findPrpLext(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取备注摘要页面信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的 备注摘要页面信息 的集合
	 */
	public List<PrpLext> findPrpLext(QueryRule queryRule) throws Exception;
	/**
	 * 保存备注摘要信息
	 * @param list:保存备注摘要信息
	 */
	public void saveOrUpdate(PrpLext prpLext) throws Exception;
	/**
	 * 保存备注摘要信息
	 * @param list:保存备注摘要信息
	 */
	public void saveOrUpdate(List<PrpLext> list) throws Exception;
}

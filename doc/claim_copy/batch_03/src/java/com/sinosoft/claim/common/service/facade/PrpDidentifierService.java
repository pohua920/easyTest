package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDidentifier;

/**
 * 检验人代码表接口
 * @author 中科软
 */
public interface PrpDidentifierService {

	/**
	 * 保存检验人信息
	 * @param prpDidentifier ：传入的检验人
	 */
	public void save(PrpDidentifier prpDidentifier) throws Exception;

	/**
	 * 保存检验人信息
	 * @param list:保存检验人信息
	 */
	public void save(List<PrpDidentifier> list) throws Exception;

	/**
	 * 更新检验人信息
	 * @param prpDidentifier :传入需要更新的检验人
	 */
	public void update(PrpDidentifier prpDidentifier) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的检验人页面信息
	 */
	public Page findPrpDidentifier(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询条件查询所有结果
	 * @param queryRule 查询条件
	 * @return 集合
	 * @throws Exception
	 */
	public List<PrpDidentifier> findPrpDidentifier(QueryRule queryRule) throws Exception;

	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 */
	public List<PrpDidentifier> findByConditions(String conditions) throws Exception;

	Page findPrpDidentifier(String conditions, int pageNo, int pageSize) throws Exception;

}

package com.sinosoft.claim.schema.service.facade;
/**
 * 特约救助接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLsalvation;
import com.sinosoft.claim.schema.model.PrpLsalvationId;

public interface PrpLsalvationService {
	
	/**
	 * 保存特约救助信息
	 * @param prpLsalvation ：传入的特约救助
	 */
	public void save(PrpLsalvation prpLsalvation) throws Exception;
	
	/**
	 * 特约救助信息
	 * @param list  :传入的特约救助信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLsalvation> list) throws Exception;
	
	/**
	 * 删除特约救助信息
	 * @param prpLsalvationId ：传入的特约救助编号
	 */
	public void delete(PrpLsalvationId prpLsalvationId) throws Exception;

	/**
	 * 更新特约救助信息
	 * @param prpLsalvation :传入需要更新的特约救助
	 */
	public void update(PrpLsalvation prpLsalvation) throws Exception;

	/**
	 * 根据特约救助编号查询出特约救助信息
	 * @param prpLsalvationId ：传入的特约救助编号
	 * @return 返回特约救助
	 */
	public PrpLsalvation findPrpLsalvation(PrpLsalvationId prpLsalvationId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的特约救助页面信息
	 */
	public Page findPrpLsalvation(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取特约救助信息  的集合
	 * @param queryRule 查询对象
	 * @return 包含的  特约救助信息的集合
	 */
	public List<PrpLsalvation> findPrpLsalvation(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据特约救助编号查询出特约救助信息
	 * @param certiNo ：传入的特约救助编号
	 * @return 返回特约救助
	 */
	public PrpLsalvation findPrpLsalvation(String certiNo) throws Exception;
}

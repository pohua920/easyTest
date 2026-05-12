package com.sinosoft.claim.schema.service.facade;
/**
 * 批量保单接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCbatch;
import com.sinosoft.claim.schema.model.PrpCbatchId;

public interface PrpCbatchService {
	
	/**
	 * 保存批量保单信息
	 * @param PrpCbatch ：传入的批量保单
	 */
	public void save(PrpCbatch PrpCbatch) throws Exception;
	
	/**
	 * 批量保单信息
	 * @param list  :传入的批量保单信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCbatch> list) throws Exception;
	
	/**
	 * 删除批量保单信息
	 * @param PrpCbatchId ：传入的批量保单编号
	 */
	public void delete(PrpCbatchId PrpCbatchId) throws Exception;

	/**
	 * 更新批量保单信息
	 * @param PrpCbatch :传入需要更新的批量保单
	 */
	public void update(PrpCbatch PrpCbatch) throws Exception;

	/**
	 * 根据批量保单编号查询出批量保单信息
	 * @param PrpCbatchId ：传入的批量保单编号
	 * @return 返回批量保单
	 */
	public PrpCbatch findPrpCbatch(PrpCbatchId PrpCbatchId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的批量保单页面信息
	 */
	public Page findPrpCbatch(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取批量保单信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的批量保单信息列表
	 */
	public List<PrpCbatch> findPrpCbatch(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据批量保单编号查询出批量保单信息
	 * @param certiNo ：传入的批量保单编号
	 * @return 返回批量保单
	 */
	public PrpCbatch findPrpCbatch(String certiNo) throws Exception;
}

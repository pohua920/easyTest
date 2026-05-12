package com.sinosoft.claim.schema.service.facade;
/**
 * 农业险承保标的信息接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemAgri;
import com.sinosoft.claim.schema.model.PrpCitemAgriId;

public interface PrpCitemAgriService {
	
	/**
	 * 保存农业险承保标的信息信息
	 * @param PrpCitemAgri ：传入的农业险承保标的信息
	 */
	public void save(PrpCitemAgri PrpCitemAgri) throws Exception;
	
	/**
	 * 农业险承保标的信息信息
	 * @param list  :传入的农业险承保标的信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemAgri> list) throws Exception;
	
	/**
	 * 删除农业险承保标的信息信息
	 * @param PrpCitemAgriId ：传入的农业险承保标的信息编号
	 */
	public void delete(PrpCitemAgriId PrpCitemAgriId) throws Exception;

	/**
	 * 更新农业险承保标的信息信息
	 * @param PrpCitemAgri :传入需要更新的农业险承保标的信息
	 */
	public void update(PrpCitemAgri PrpCitemAgri) throws Exception;

	/**
	 * 根据农业险承保标的信息编号查询出农业险承保标的信息信息
	 * @param PrpCitemAgriId ：传入的农业险承保标的信息编号
	 * @return 返回农业险承保标的信息
	 */
	public PrpCitemAgri findPrpCitemAgri(PrpCitemAgriId PrpCitemAgriId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的农业险承保标的信息页面信息
	 */
	public Page findPrpCitemAgri(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取农业险承保标的信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的农业险承保标的信息的列表
	 */
	public List<PrpCitemAgri> findPrpCitemAgri(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据农业险承保标的信息编号查询出农业险承保标的信息信息
	 * @param certiNo ：传入的农业险承保标的信息编号
	 * @return 返回农业险承保标的信息
	 */
	public PrpCitemAgri findPrpCitemAgri(String certiNo) throws Exception;
}

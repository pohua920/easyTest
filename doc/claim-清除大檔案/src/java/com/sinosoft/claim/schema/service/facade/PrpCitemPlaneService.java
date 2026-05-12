package com.sinosoft.claim.schema.service.facade;
/**
 * 标的接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemPlane;
import com.sinosoft.claim.schema.model.PrpCitemPlaneId;

public interface PrpCitemPlaneService {
	
	/**
	 * 保存标的信息
	 * @param PrpCitemPlane ：传入的标的
	 */
	public void save(PrpCitemPlane PrpCitemPlane) throws Exception;
	
	/**
	 * 标的信息
	 * @param list  :传入的标的信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCitemPlane> list) throws Exception;
	
	/**
	 * 删除标的信息
	 * @param PrpCitemPlaneId ：传入的标的编号
	 */
	public void delete(PrpCitemPlaneId PrpCitemPlaneId) throws Exception;

	/**
	 * 更新标的信息
	 * @param PrpCitemPlane :传入需要更新的标的
	 */
	public void update(PrpCitemPlane PrpCitemPlane) throws Exception;

	/**
	 * 根据标的编号查询出标的信息
	 * @param PrpCitemPlaneId ：传入的标的编号
	 * @return 返回标的
	 */
	public PrpCitemPlane findPrpCitemPlane(PrpCitemPlaneId PrpCitemPlaneId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的标的页面信息
	 */
	public Page findPrpCitemPlane(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取标的页面  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 标的页面 的列表
	 */
	public List<PrpCitemPlane> findPrpCitemPlane(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据标的编号查询出标的信息
	 * @param certiNo ：传入的标的编号
	 * @return 返回标的
	 */
	public PrpCitemPlane findPrpCitemPlane(String certiNo) throws Exception;
}

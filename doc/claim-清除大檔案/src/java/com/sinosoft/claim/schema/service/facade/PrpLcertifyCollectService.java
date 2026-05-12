/*
 * @(#)PrpLcertifyCollectService.java	Jan 23, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.schema.service.facade;
/**
 * 单证收集接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcertifyCollectId;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @Date    <Jan 23, 2013>
 * @description 
 */
public interface PrpLcertifyCollectService {
	/**
	 * 保存单证收集信息
	 * @param prpLcertifyCollect ：传入的单证收集信息
	 */
	public void save(PrpLcertifyCollect prpLcertifyCollect) throws Exception;
	/**
	 * 单证收集信息
	 * @param list  :传入的单证收集信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLcertifyCollect> list) throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
	public void saveOrUpdate(List<PrpLcertifyCollect> list)throws Exception;
	/**
	 * @param list
	 * @throws Exception
	 * 保存或者修改，如果对象在数据库中不存在就保存对象，如果存在就更新对象
	 */
	public void saveOrUpdate(PrpLcertifyCollect prpLcertifyCollect)throws Exception;
	/**
	 * 删除单证收集信息
	 * @param prpLcertifyCollectId ：传入的单证收集信息
	 */
	public void delete(PrpLcertifyCollectId prpLcertifyCollectId) throws Exception;
	
	/**
	 * @param registNo
	 * @throws Exception
	 * 根据报案号删除所有的信息
	 */
	public void deleteByRegistNo(String registNo) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的单证收集信息页面信息
	 */
	public  Page findPrpLcertifyCollect(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取单证收集信息  的列表
	 * @param queryRule 查询对象
	 * @return 包含的 单证收集信息 的列表
	 */
	public List<PrpLcertifyCollect> findPrpLcertifyCollect(QueryRule queryRule) throws Exception;
	/**
	 * 根据单证收集信息编号查询出单证收集信息
	 * @param certiNo ：传入的单证收集信息编号
	 * @return 返回单证收集信息
	 */
	public PrpLcertifyCollect findByPrpLcertifyCollectId(PrpLcertifyCollectId prpLcertifyCollectId)throws Exception;
	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 * 根据sql语句查询分页方法
	 */
	public Page findByQueryConditions(String conditions, int pageNo, int rowsPerPage) throws Exception;
	/**
	 * @param conditions
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 * 根据sql语句查询分页方法
	 */
	public List<PrpLcertifyCollect> findByQueryConditions(String conditions) throws Exception;

}

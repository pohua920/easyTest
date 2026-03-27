package com.sinosoft.claim.schema.service.facade;
/**
 * 保险地址接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredId;

public interface PrpCinsuredService {
	
	/**
	 * 保存保险关系人信息
	 * @param prpLcheck ：传入的保险关系人
	 */
	public void save(PrpCinsured prpCinsured) throws Exception;
	
	/**
	 * 保险关系人信息
	 * @param list  :传入的保险关系人信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCinsured> list) throws Exception;
	
	/**
	 * 删除保险关系人信息
	 * @param prpCinsuredId ：传入的保险关系人编号
	 */
	public void delete(PrpCinsuredId prpCinsuredId) throws Exception;

	/**
	 * 更新保险关系人信息
	 * @param prpCinsured :传入需要更新的保险关系人
	 */
	public void update(PrpCinsured prpCinsured) throws Exception;

	/**
	 * 根据保险关系人编号查询出保险关系人信息
	 * @param prpCinsuredId ：传入的保险关系人编号
	 * @return 返回保险关系人
	 */
	public PrpCinsured findPrpCinsured(PrpCinsuredId prpCinsuredId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的保险关系人页面信息
	 * @deprecated 请用findByPage代替
	 */
	public Page findPrpCinsured(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取保险关系人页面信息的列表
	 * @param queryRule 查询对象
	 * @return 包含的保险关系人页面信息的列表
	 */
	public List<PrpCinsured> findPrpCinsured(QueryRule queryRule) throws Exception;
	
	/**
	 * 分页查询PrpCinsured
	 * @author 中科软
	 * @date Mar 26, 2013 11:47:54 AM
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(String conditions,int pageNo, int pageSize) throws Exception;
	/**
	 * 分页查询PrpCinsured
	 * @author 中科软
	 * @date Mar 26, 2013 11:47:54 AM
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsured(String conditions) throws Exception;
}

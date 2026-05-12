package com.sinosoft.claim.schema.service.facade;
/**
 * UtiConfig接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiConfig;

public interface UtiConfigService {
	
	/**
	 * 保存UtiConfig信息
	 * @param utiConfig ：传入的UtiConfig
	 */
	public void save(UtiConfig utiConfig) throws Exception;
	
	/**
	 * 保存UtiConfig信息
	 * @param list:保存UtiConfig信息
	 */
	public void save(List<UtiConfig> list) throws Exception;
	
	/**
	 * 删除UtiConfig信息
	 * @param configCode ：传入的UtiConfig编号
	 */
	public void delete(String configCode) throws Exception;

	/**
	 * 更新UtiConfig信息
	 * @param utiConfig :传入需要更新的UtiConfig
	 */
	public void update(UtiConfig utiConfig) throws Exception;

	/**
	 * 根据UtiConfig编号查询出UtiConfig信息
	 * @param configCode ：传入的UtiConfig编号
	 * @return 返回UtiConfig
	 */
	public UtiConfig findUtiConfig(String configCode) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的UtiConfig页面信息
	 */
	public Page findUtiConfig(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<UtiConfig> findUtiConfig(QueryRule queryRule) throws Exception;
	/**
	 * @param conditions
	 * @return
	 * @throws Exception
	 * 根据sql语句条件查询
	 */
	public List<UtiConfig> findByConditions(String conditions)throws Exception;
	/**
	 * @param configCode
	 * @return
	 */
	public boolean isExist(String configCode)throws Exception;
	/**
	 * @param utiConfig
	 * @throws Exception
	 * 保存或修改，
	 */
	public void saveOrUpdate(UtiConfig utiConfig)throws Exception;
	
	/**
     * 查询满足模糊查询条件的记录数
     * @param conditions conditions
     * @return 满足模糊查询条件的记录数
     * @throws Exception
     */
	public int getCount(String conditions) throws Exception;
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param conditions 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的UtiConfig页面信息
	 */
	public Page findUtiConfig(String conditions, int pageNo, int pageSize) throws Exception;
}

package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiUserGradePower;
import com.sinosoft.claim.schema.model.UtiUserGradePowerId;


/**
 * 用户岗位权限接口
 * @author 中科软
 *
 */
public interface UtiUserGradePowerService {
	/**
	 * 保存用户岗位权限定义表信息
	 * @param utiUserGradePower ：传入的用户岗位权限定义表
	 */
	public void save(UtiUserGradePower utiUserGradePower) throws Exception;
	
	/**
	 * 保存用户岗位权限定义表信息
	 * @param list:保存用户岗位权限定义表信息
	 */
	public void save(List<UtiUserGradePower> list) throws Exception;
	
	/**
	 * 删除用户岗位权限定义表信息
	 * @param utiUserGradePowerId ：传入的用户岗位权限定义表编号
	 */
	public void delete(UtiUserGradePowerId utiUserGradePowerId) throws Exception;

	/**
	 * 更新用户岗位权限定义表信息
	 * @param utiUserGradePower :传入需要更新的用户岗位权限定义表
	 */
	public void update(UtiUserGradePower utiUserGradePower) throws Exception;

	/**
	 * 根据用户岗位权限定义表编号查询出用户岗位权限定义表信息
	 * @param utiUserGradePowerId ：传入的用户岗位权限定义表编号
	 * @return 返回用户岗位权限定义表
	 */
	public UtiUserGradePower findUtiUserGradePower(UtiUserGradePowerId utiUserGradePowerId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的用户岗位权限定义表页面信息
	 */
	public Page findUtiUserGradePower(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	
	public List<UtiUserGradePower> findUtiUserGradePower(QueryRule queryRule) throws Exception;
}

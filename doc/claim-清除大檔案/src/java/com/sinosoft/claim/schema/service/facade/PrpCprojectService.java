package com.sinosoft.claim.schema.service.facade;
/**
 * 预约协议缴费计划接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCproject;
import com.sinosoft.claim.schema.model.PrpCprojectId;

public interface PrpCprojectService {
	
	/**
	 * 保存预约协议缴费计划信息
	 * @param PrpCproject ：传入的预约协议缴费计划
	 */
	public void save(PrpCproject PrpCproject) throws Exception;
	
	/**
	 * 预约协议缴费计划信息
	 * @param list  :传入的预约协议缴费计划信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCproject> list) throws Exception;
	
	/**
	 * 删除预约协议缴费计划信息
	 * @param PrpCprojectId ：传入的预约协议缴费计划编号
	 */
	public void delete(PrpCprojectId PrpCprojectId) throws Exception;

	/**
	 * 更新预约协议缴费计划信息
	 * @param PrpCproject :传入需要更新的预约协议缴费计划
	 */
	public void update(PrpCproject PrpCproject) throws Exception;

	/**
	 * 根据预约协议缴费计划编号查询出预约协议缴费计划信息
	 * @param PrpCprojectId ：传入的预约协议缴费计划编号
	 * @return 返回预约协议缴费计划
	 */
	public PrpCproject findPrpCproject(PrpCprojectId PrpCprojectId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的预约协议缴费计划页面信息
	 */
	public Page findPrpCproject(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取预约协议缴费计划  的列表
	 * @param queryRule 查询对象
	 * @return 包含的预约协议缴费计划  的列表
	 */
	public List<PrpCproject> findPrpCproject(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据预约协议缴费计划编号查询出预约协议缴费计划信息
	 * @param certiNo ：传入的预约协议缴费计划编号
	 * @return 返回预约协议缴费计划
	 */
	public PrpCproject findPrpCproject(String certiNo) throws Exception;
}

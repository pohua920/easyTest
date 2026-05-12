package com.sinosoft.claim.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpDriskConfigId;


public interface PrpDriskConfigService {
	
	/***
	 * 根据主键查询
	 * @param comCode
	 * @param riskCode
	 * @param configCode
	 * @return
	 */
	public PrpDriskConfig findByPrimaryKey(String comCode, String riskCode, String configCode);

	/***
	 * 根据查询条件
	 * @param queryRule
	 * @return
	 * @throws Exception
	 */
	public List<PrpDriskConfig> findPrpDriskConfig(QueryRule queryRule) throws Exception;
	
	/**
	 * 保存险种配置信息
	 * @param prpDriskConfig ：传入的险种配置
	 */
	public void save(PrpDriskConfig prpDriskConfig) throws Exception;

	/**
	 * 保存或修改
	 * @param prpDriskConfig
	 * @throws Exception 
	 */
	public void saveOrUpdate(PrpDriskConfig prpDriskConfig) throws Exception;

	/**
	 * 删除险种配置信息
	 * @param registNo ：传入的险种配置编号
	 */
	public void delete(PrpDriskConfigId prpDriskConfigId) throws Exception;

	/**
	 * 保存险种配置信息
	 * @param list:保存险种配置信息
	 */
	public void save(List<PrpDriskConfig> list) throws Exception;

	/**
	 * @description: 险种配置修改
	 * @param PrpDriskConfig prpDriskConfig
	 * @throws Exception
	 */
	public void update(PrpDriskConfig prpDriskConfig);

	/**
	 * 根据险种配置编号查询出险种配置信息
	 * @param prpDriskConfigId ：传入的险种配置编号
	 * @return 返回险种配置
	 */
	public PrpDriskConfig findPrpDriskConfig(PrpDriskConfigId prpDriskConfigId) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种配置页面信息
	 */
	public Page findPrpDriskConfig(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的险种配置页面信息
	 */
	public Page findPrpDriskConfig(PrpDriskConfigId conditions, int pageNo, int pageSize) throws Exception;

	/**
	 * 根据sql语句条件查询
	 * @param conditions
	 * @return
	 * @throws Exception 
	 */
	public List<PrpDriskConfig> findByConditions(String conditions) throws Exception;
	/**
	 * 通过机构代码、险种和属性名称返回参数值
	 * @param configCode
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public String getConfigValue(String configCode, String riskCode) throws Exception;


}

package com.sinosoft.claim.schema.service.facade;
/**
 * 简讯模板信息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLsmsTemplate;

public interface PrpLsmsTemplateService {
	
	/**
	 * 保存简讯模板信息
	 * @param prpL ：传入的简讯模板信息
	 */
	public void save(PrpLsmsTemplate prpLsmsTemplate) throws Exception;
	
	/**
	 * 简讯模板信息
	 * @param list  :传入的简讯模板信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLsmsTemplate> list) throws Exception;
	
	/**
	 * 删除简讯模板信息
	 * @param prpLsmsTemplateId ：简讯模板信息主键
	 */
	public void delete(String id) throws Exception;

	/**
	 * 更新简讯模板信息
	 * @param prpLsmsTemplate :简讯模板信息
	 */
	public void update(PrpLsmsTemplate prpLsmsTemplate) throws Exception;

	/**
	 * 根据简讯模板信息信息
	 * @param prpLsmsTemplateId ：传入的简讯模板信息主键
	 * @return 简讯模板信息
	 */
	public PrpLsmsTemplate findPrpLsmsTemplate(String id) throws Exception;
	
	/**
	 * 根据查询对象获取简讯模板信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  简讯模板信息的集合
	 */
	public List<PrpLsmsTemplate> findPrpLsmsTemplate(QueryRule queryRule) throws Exception;
	
}

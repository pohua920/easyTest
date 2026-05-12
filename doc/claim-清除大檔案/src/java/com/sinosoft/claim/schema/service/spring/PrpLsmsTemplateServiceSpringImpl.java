package com.sinosoft.claim.schema.service.spring;
/**
 * 简讯模板
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLsmsTemplate;
import com.sinosoft.claim.schema.service.facade.PrpLsmsTemplateService;

public class PrpLsmsTemplateServiceSpringImpl extends GenericDaoHibernate<PrpLsmsTemplate, String> implements PrpLsmsTemplateService{
	/**
	 * 保存简讯模板信息
	 * @param prpL ：传入的简讯模板信息
	 */
	public void save(PrpLsmsTemplate prpLsmsTemplate) throws Exception {
		super.save(prpLsmsTemplate);
		
	}
	/**
	 * 简讯模板信息
	 * @param list  :传入的简讯模板信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLsmsTemplate> list) throws Exception {
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * 删除简讯模板信息
	 * @param prpLsmsTemplateId ：简讯模板信息主键
	 */
	public void delete(String id) throws Exception {
		super.deleteByPK(PrpLsmsTemplate.class, id);
	}
	/**
	 * 根据简讯模板信息信息
	 * @param prpLsmsTemplateId ：传入的简讯模板信息主键
	 * @return 简讯模板信息
	 */
	public PrpLsmsTemplate findPrpLsmsTemplate(String id) throws Exception {
		return super.get(PrpLsmsTemplate.class, id);
	}
	/**
	 * 根据查询对象获取简讯模板信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  简讯模板信息的集合
	 */
	public List<PrpLsmsTemplate> findPrpLsmsTemplate(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}

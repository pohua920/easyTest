package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLemailConfig;

public interface PrpLemailConfigService {
	/**
	 * 保存邮件细节信息
	 * @param prpLemailDetail ：传入的邮件细节
	 */
	public void save(PrpLemailConfig prpLemailConfig) throws Exception;
	
	/**
	 * 邮件细节信息
	 * @param list  :传入的邮件细节信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLemailConfig> list) throws Exception;
	
	/**
	 * 删除邮件细节信息
	 * @param prpLemailDetailId ：传入的邮件细节编号
	 */
	public void delete(String id) throws Exception;

	/**
	 * 更新邮件细节信息
	 * @param prpLemailDetail :传入需要更新的邮件细节
	 */
	public void update(PrpLemailConfig prpLemailConfig) throws Exception;

	/**
	 * 根据邮件细节编号查询出邮件细节信息
	 * @param prpLemailDetailId ：传入的邮件细节编号
	 * @return 返回邮件细节
	 */
	public PrpLemailConfig findPrpLemailConfig(String id) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的邮件细节页面信息
	 */
	public Page findPrpLemailConfig(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取邮件细节  的集合
	 * @param queryRule 查询对象
	 * @return 包含的邮件细节  的集合
	 */
	public List<PrpLemailConfig> findPrpLemailConfig(QueryRule queryRule) throws Exception;
}

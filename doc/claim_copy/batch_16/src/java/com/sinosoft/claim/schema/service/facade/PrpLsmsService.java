package com.sinosoft.claim.schema.service.facade;
/**
 * 简讯讯息接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLsms;
import com.sinosoft.sysframework.reference.DBManager;

public interface PrpLsmsService {
	
	/**
	 * 保存简讯
	 * @param prpLsms ：简讯讯息
	 */
	public void save(PrpLsms prpLsms) throws Exception;
	
	/**
	 * 保存简讯
	 * @param list  :简讯讯息集合
	 * @throws Exception
	 */
	public void save(List<PrpLsms> list) throws Exception;
	
	/**
	 * 删除简讯讯息
	 * @param prpLsmsId ：传入简讯讯息主键
	 */
	public void delete(String id) throws Exception;

	/**
	 * 更新简讯讯息
	 * @param prpLsms :传入简讯讯息
	 */
	public void update(PrpLsms prpLsms) throws Exception;

	/**
	 * 简讯讯息
	 * @param prpLsmsId ：简讯讯息主键
	 * @return 返回简讯讯息
	 */
	public PrpLsms findPrpLsms(String id) throws Exception;
	
	/**
	 * 根据查询对象获取 简讯讯息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  简讯讯息的集合
	 */
	public List<PrpLsms> findPrpLsms(QueryRule queryRule) throws Exception;
	/**
	 * 保存SMSRequest信息
	 * @param prpLsms
	 * @param dbManager
	 * @throws Exception
	 */
	public String[] saveSMSRequest(List<PrpLsms> list) throws Exception;
	/**
	 * 保存简讯信息
	 * @param list
	 * @throws Exception
	 */
	public String[] saveSms(List<PrpLsms> list) throws Exception;
	/**
	 * 保存SMSRequest信息
	 * @param prpLsms
	 * @param dbManager
	 * @throws Exception
	 */
	public String[] saveSMSRequest(PrpLsms prpLsms) throws Exception;

}

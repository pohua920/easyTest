package com.sinosoft.claim.schema.service.facade;
/**
 * 简讯日志
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLsmsLog;

public interface PrpLsmsLogService {
	
	/**
	 * 保存简讯日志信息
	 * @param prpLsmsLog ：简讯日志信息
	 */
	public void save(PrpLsmsLog prpLsmsLog) throws Exception;
	
	/**
	 * 保存简讯日志信息
	 * @param list  :简讯日志信息信息集合
	 * @throws Exception
	 */
	public void save(List<PrpLsmsLog> list) throws Exception;
	
	/**
	 * 删除简讯日志信息
	 * @param prpLsmsLogId ：简讯日志主键
	 */
	public void delete(String id) throws Exception;

	/**
	 * 跟新简讯日志
	 * @param prpLsmsLog :跟新简讯日志信息
	 */
	public void update(PrpLsmsLog prpLsmsLog) throws Exception;

	/**
	 * 查询简讯日志信息
	 * @param prpLsmsLogId ：传入简讯日志信息主键
	 * @return 返回日志信息
	 */
	public PrpLsmsLog findPrpLsmsLog(String id) throws Exception;
	
	/**
	 * 根据查询对象获取 简讯的集合
	 * @param queryRule 查询对象
	 * @return 包含的   简讯 的集合
	 */
	public List<PrpLsmsLog> findPrpLsmsLog(QueryRule queryRule) throws Exception;
	/**
	 *  保存消息日志信息
	 * @param prpLsmsLog
	 */
	public void logForSms(PrpLsmsLog prpLsmsLog);
}

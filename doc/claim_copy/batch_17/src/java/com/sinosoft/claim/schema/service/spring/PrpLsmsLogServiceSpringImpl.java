package com.sinosoft.claim.schema.service.spring;
/**
 * 理赔调派处理记录信息接口实现类
 * @author 中科软
 */
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLsmsLog;
import com.sinosoft.claim.schema.service.facade.PrpLsmsLogService;

public class PrpLsmsLogServiceSpringImpl extends GenericDaoHibernate<PrpLsmsLog, String> implements PrpLsmsLogService{
	/**
	 * 保存简讯日志信息
	 * @param prpLsmsLog ：简讯日志信息
	 */
	public void save(PrpLsmsLog prpLsmsLog) throws Exception {
		super.save(prpLsmsLog);
		
	}
	/**
	 * 保存简讯日志信息
	 * @param list  :简讯日志信息信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLsmsLog> list) throws Exception {
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}
	/**
	 * 删除简讯日志信息
	 * @param prpLsmsLogId ：简讯日志主键
	 */
	public void delete(String id) throws Exception {
		super.deleteByPK(PrpLsmsLog.class, id);
	}
	/**
	 * 查询简讯日志信息
	 * @param prpLsmsLogId ：传入简讯日志信息主键
	 * @return 返回日志信息
	 */
	public PrpLsmsLog findPrpLsmsLog(String id) throws Exception {
		return super.get(PrpLsmsLog.class, id);
	}
	/**
	 * 根据查询对象获取 简讯的集合
	 * @param queryRule 查询对象
	 * @return 包含的   简讯 的集合
	 */
	public List<PrpLsmsLog> findPrpLsmsLog(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 *  保存消息日志信息
	 * @param prpLsmsLog
	 */
	public void logForSms(PrpLsmsLog prpLsmsLog){
		try {
			this.save(prpLsmsLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

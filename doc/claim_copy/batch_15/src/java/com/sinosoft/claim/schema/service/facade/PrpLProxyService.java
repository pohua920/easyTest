package com.sinosoft.claim.schema.service.facade;
/**
 * 理赔调派处理记录接口
 * @author 中科软
 */
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import com.sinosoft.claim.schema.model.PrpLProxy;
import com.sinosoft.claim.schema.model.PrpLProxyId;

public interface PrpLProxyService {
	
	/**
	 * 保存理赔调派处理记录信息
	 * @param prpLProxy ：传入的理赔调派处理记录
	 */
	public void save(PrpLProxy prpLProxy) throws Exception;
	
	/**
	 * 理赔调派处理记录信息
	 * @param list  :传入的理赔调派处理记录信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpLProxy> list) throws Exception;
	
	/**
	 * 删除理赔调派处理记录信息
	 * @param prpLProxyId ：传入的理赔调派处理记录编号
	 */
	public void delete(PrpLProxyId prpLProxyId) throws Exception;

	/**
	 * 更新理赔调派处理记录信息
	 * @param prpLProxy :传入需要更新的理赔调派处理记录
	 */
	public void update(PrpLProxy prpLProxy) throws Exception;

	/**
	 * 根据理赔调派处理记录编号查询出理赔调派处理记录信息
	 * @param prpLProxyId ：传入的理赔调派处理记录编号
	 * @return 返回理赔调派处理记录
	 */
	public PrpLProxy findPrpLProxy(PrpLProxyId prpLProxyId) throws Exception;
	
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的理赔调派处理记录页面信息
	 */
	public Page findPrpLProxy(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 * 根据查询对象获取 理赔调派处理记录信息 的集合
	 * @param queryRule 查询对象
	 * @return 包含的  理赔调派处理记录信息的集合
	 */
	public List<PrpLProxy> findPrpLProxy(QueryRule queryRule) throws Exception;
	
	/**
	 * 根据理赔调派处理记录编号查询出理赔调派处理记录信息
	 * @param certiNo ：传入的理赔调派处理记录编号
	 * @return 返回理赔调派处理记录
	 */
	public PrpLProxy findPrpLProxy(String certiNo) throws Exception;
}

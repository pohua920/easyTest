/**
 * 
 */
package com.sinosoft.sys.platform.power.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

/**
 * @author 中科软
 */
public interface SaaAuthExceptCompanyService {

	/**
	 * @author 中科软 
	 * @param rule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findSaaAuthExceptCompany(QueryRule rule, int pageNo,
			int pageSize);
}

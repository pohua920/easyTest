/**
 * 
 */
package com.sinosoft.sys.platform.power.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

/**
 * @author 中科软
 */
public interface SaaAuthCompanyService {
	
	/**
	 * 未完成双击域的实现
	 * @author 中科软 
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findSaaAuthCompany(QueryRule queryRule, int pageNo, int pageSize);
	
}

package com.sinosoft.claim.common.service.facade;

import com.sinosoft.claim.compensate.vo.PayRefRecDto;


/***
 * 这是AP_HEAD收付财务中间表对应类
 * @author 中科软
 */
public interface ApHeadService {

	/**
     * 按条件从AP_HEAD表查询支付信息
	 * @author 中科软
	 * @date Mar 29, 2013 9:58:19 AM
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	 public PayRefRecDto findByQueryConditions(String conditions) throws Exception;
}

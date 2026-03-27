package com.sinosoft.claim.undwrt.service.spring;

import com.sinosoft.claim.undwrt.service.facade.ClaimCallBackService;
/***
 * 核赔回调处理函数实现类
 * @author 中科软
 * @version 1.0
 *
 */
public class ClaimCallBackEmptyServiceSpringImpl implements ClaimCallBackService{

	/**
	 * 理赔核赔回调处理函数（空处理）
	 * @param businessType 业务类型
	 * @param businessNo 业务号码
	 */
	public void callBack(String businessType, String businessNo) throws Exception {
		System.out.println("核赔回调理赔的处理是空的，请重载实现方法，並在配置文件中重指向");
	}

}

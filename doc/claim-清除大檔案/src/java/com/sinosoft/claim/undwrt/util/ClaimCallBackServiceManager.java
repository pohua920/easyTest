package com.sinosoft.claim.undwrt.util;

import ins.framework.common.ServiceFactory;

import com.sinosoft.claim.undwrt.service.facade.ClaimCallBackService;
import com.sinosoft.claim.undwrt.service.spring.ClaimCallBackEmptyServiceSpringImpl;
import com.sinosoft.sysframework.reference.AppConfig;
/**
 * 回调理赔处理
 * @author 中科軟
 *
 */
public class ClaimCallBackServiceManager {
	/**
	 * 回调理赔处理
	 * @return
	 * @throws Exception
	 */
	public static ClaimCallBackService getService() throws Exception{		
		ClaimCallBackService claimCallBackService = null;
		String claimCallBackImplClassName = AppConfig.get("sysconst.claimCallBackImplClass");
		if(claimCallBackImplClassName==null||claimCallBackImplClassName.trim().length()==0){
			claimCallBackService = (ClaimCallBackService) ServiceFactory.getService("claimCallBackService");
		}else{
			if("default".equalsIgnoreCase(claimCallBackImplClassName)){
				claimCallBackService = new ClaimCallBackEmptyServiceSpringImpl();
			}else if("empty".equalsIgnoreCase(claimCallBackImplClassName)){
				claimCallBackService = new ClaimCallBackEmptyServiceSpringImpl();
			}else if("test".equalsIgnoreCase(claimCallBackImplClassName)){
				claimCallBackService = new ClaimCallBackEmptyServiceSpringImpl();
			}else{
				claimCallBackService = (ClaimCallBackService) ServiceFactory.getService(claimCallBackImplClassName);
			}
		}
		return claimCallBackService;
	}
}

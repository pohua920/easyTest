package com.sinosoft.claim.reins.service;

import ins.framework.common.ServiceFactory;

import com.sinosoft.claim.reins.service.facade.ReinsService;
import com.sinosoft.claim.reins.service.spring.ReinsServiceImpl;
import com.sinosoft.claim.reins.service.spring.ReinsServiceImplEmpty;
import com.sinosoft.claim.reins.service.spring.ReinsServiceImplTest;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 再保接口管理
 * @author 中科软
 *
 */
public class ReinsServiceManager {
	/**再保服务*/
	private ReinsService reinsService;

	public  ReinsService getReinsService() throws Exception{
		String reinsServiceImplName = AppConfig.get("sysconst.ReinsImplClass");
		if(!(reinsServiceImplName==null||reinsServiceImplName.trim().length()==0)){
			if("default".equalsIgnoreCase(reinsServiceImplName)){
				reinsService = new ReinsServiceImpl();
			}else if("empty".equalsIgnoreCase(reinsServiceImplName)){
				reinsService = new ReinsServiceImplEmpty();
			}else if("test".equalsIgnoreCase(reinsServiceImplName)){
				reinsService = new ReinsServiceImplTest();
			}else{
				reinsService = (ReinsService) ServiceFactory.getService(reinsServiceImplName);
			}
		}
		//有可能是自己new创建的实例，需要这个判断
		if(reinsService==null){
			reinsService = (ReinsService) ServiceFactory.getService("reinsService");
		}
		return reinsService;
	}

	public void setReinsService(ReinsService reinsService) {
		this.reinsService = reinsService;
	}


}

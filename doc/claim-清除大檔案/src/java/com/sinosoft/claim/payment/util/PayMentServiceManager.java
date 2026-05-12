package com.sinosoft.claim.payment.util;

import ins.framework.common.ServiceFactory;

import com.sinosoft.claim.payment.service.facade.PayMentService;
import com.sinosoft.claim.payment.service.spring.PayMentServiceImplEmpty;
import com.sinosoft.sysframework.reference.AppConfig;
 
/**
 * 收付信息处理
 * @author 中科软
 *
 */
public class PayMentServiceManager{
	
	/**
	 * 收付处理
	 * @return payMentSerivce
	 * @throws Exception
	 */
   public static PayMentService getService()throws Exception{
     PayMentService payMentService = null;
     String payMentServiceImplName = AppConfig.get("sysconst.PayMentImplClass");
     if ((payMentServiceImplName == null) || (payMentServiceImplName.trim().length() == 0)) {
       payMentService = new PayMentServiceImplEmpty();
     }
     else if ("default".equalsIgnoreCase(payMentServiceImplName)) {
       payMentService = new PayMentServiceImplEmpty();
     } else if ("empty".equalsIgnoreCase(payMentServiceImplName)) {
       payMentService = new PayMentServiceImplEmpty();
     } else if ("test".equalsIgnoreCase(payMentServiceImplName)) {
       payMentService = new PayMentServiceImplEmpty();
     } else {
//       Class loaderClass = Class.forName(payMentServiceImplName);
//       payMentService = (PayMentService)loaderClass.newInstance();
    	 payMentService = (PayMentService)ServiceFactory.getService(payMentServiceImplName);
     }
     return payMentService;
   }
 }


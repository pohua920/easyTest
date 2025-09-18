package com.tlg.aps.webService.stakeHolderService.client;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.tlg.aps.vo.StakeHolderVo;
import com.tlg.util.WebserviceObjConvert;

public class ClientTest {
	
	
    public static void main(String[] args) throws java.lang.Exception {
    	
    	
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://192.168.112.6:8880/APS/webService/stakeHolderService");
//        factory.setAddress("http://192.168.190.32:8080/APS/webService/stakeHolderService");
        factory.setServiceClass(StakeHolderService.class);
        factory.getInInterceptors().add(new LoggingInInterceptor());
        StakeHolderService stakeHolderService = (StakeHolderService) factory.create();
        
        StakeHolderVo stakeHolderVo = new StakeHolderVo();
		stakeHolderVo.setListNo("TEST00000001");
		stakeHolderVo.setStakeId("27938888");
		stakeHolderVo.setUserCode("bh055");
		
		String str = WebserviceObjConvert.convertObjToBase64Str(StakeHolderVo.class, stakeHolderVo);
        
        String resultStr = stakeHolderService.stakeHolderQuery(str);
        
        stakeHolderVo = (StakeHolderVo)WebserviceObjConvert.convertBase64StrToObj(resultStr, StakeHolderVo.class);
        
        System.out.println("resultStr = " + resultStr);
        
		System.exit(0);
    }
 	
}


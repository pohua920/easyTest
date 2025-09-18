package com.tlg.aps.webService.creditCardBlackList.client;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.tlg.aps.vo.BankInfoRequestVo;
import com.tlg.aps.vo.BankInfoResponseVo;
import com.tlg.aps.vo.CreditCardBlackListVo;
import com.tlg.util.WebserviceObjConvert;

public class ClientTest {
	
	
    public static void main(String[] args) throws java.lang.Exception {
    	
    	
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setAddress("http://192.168.112.6:8880/APS/webService/creditCardBlackListService");
        factory.setAddress("http://192.168.190.32:8080/APS/webService/creditCardBlackListService");
//        factory.setAddress("http://192.168.2.112:8880/APS/webService/creditCardBlackListService");
        factory.setServiceClass(CreditCardBlackListService.class);
        factory.getInInterceptors().add(new LoggingInInterceptor());
        CreditCardBlackListService creditCardBlackListService = (CreditCardBlackListService) factory.create();
        
        CreditCardBlackListVo creditCardBlackListVo = new CreditCardBlackListVo();
        creditCardBlackListVo.setCreditCardNo("5241150504729204");
        
		
		String str = WebserviceObjConvert.convertObjToBase64Str(CreditCardBlackListVo.class, creditCardBlackListVo);
        
        String resultStr = creditCardBlackListService.creditCardBlackListQuery(str);
        
        creditCardBlackListVo = (CreditCardBlackListVo)WebserviceObjConvert.convertBase64StrToObj(resultStr, CreditCardBlackListVo.class);
        
        System.out.println("creditCardBlackListVo.getCreditCardNo() = " + creditCardBlackListVo.getCreditCardNo());
        System.out.println("creditCardBlackListVo.getResultCode() = " + creditCardBlackListVo.getResultCode());
        System.out.println("creditCardBlackListVo.getResultMsg() = " + creditCardBlackListVo.getResultMsg());
        
		System.exit(0);
    }
 	
}


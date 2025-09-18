package com.tlg.aps.webService.firRuleService.client;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.tlg.aps.vo.FirAddressRuleObj;
import com.tlg.aps.vo.RuleReponseDetailVo;
import com.tlg.aps.vo.RuleReponseVo;
import com.tlg.util.WebserviceObjConvert;

public class ClientTest {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	
    public static void main(String[] args) throws java.lang.Exception {
    	
    	
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setAddress("http://192.168.190.32:8180/CWP/webService/firRuleService");
        factory.setAddress("http://192.168.112.6:8880/CWP/webService/firRuleService");
        factory.setServiceClass(RuleCheckService.class);
        factory.getInInterceptors().add(new LoggingInInterceptor());
        RuleCheckService ruleCheckService = (RuleCheckService) factory.create();
        
        
        FirAddressRuleObj firAddressRuleObj = new FirAddressRuleObj();
        firAddressRuleObj.setInsType("F02");
        firAddressRuleObj.setOperationType("P");
        firAddressRuleObj.setRulePrefix("FIR");
        firAddressRuleObj.setPostcode("236");
        firAddressRuleObj.setAddrStructure("2");
        firAddressRuleObj.setAddrSumfloors("11");
        firAddressRuleObj.setAddrWall("06");
        firAddressRuleObj.setAddress("台北市中正區許昌街17號之1");
        firAddressRuleObj.setAddrRoof("70");
        
		String str = WebserviceObjConvert.convertObjToBase64Str(FirAddressRuleObj.class, firAddressRuleObj);
        
        String resultStr = ruleCheckService.ruleCheck(str);
        
        RuleReponseVo ruleReponseVo = (RuleReponseVo)WebserviceObjConvert.convertBase64StrToObj(resultStr, RuleReponseVo.class);
        System.out.println("status = " + ruleReponseVo.getStatus());
        for(RuleReponseDetailVo vo : ruleReponseVo.getDetailList()){
        	System.out.println("----------------------------------------");
        	System.out.println("ruleId = " + vo.getRuleId());
        	System.out.println("ruleSeq = " + vo.getRuleSeq());
        	System.out.println("result = " + vo.getRuleResult());
        	System.out.println("Msg = " + vo.getRuleMsg());
        	System.out.println("checkType = " + vo.getCheckType());
        }
//        
//        System.out.println("resultCode = " + getInsCardResponseVo.getResultCode());
//        System.out.println("resultMsg = " + getInsCardResponseVo.getResultMsg());
//        System.out.println("getInsuredCard = " + getInsCardResponseVo.getInsuredCard());
//        
		System.exit(0);
    }
 	
}


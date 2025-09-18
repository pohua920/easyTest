package com.tlg.aps.webService.blasklistQueryService.client;

import java.net.URL;

import com.tlg.aps.vo.BlacklistReqVo;
import com.tlg.aps.vo.BlacklistRespVo;
import com.tlg.util.WebserviceObjConvert;

public class ClientTest {
	
	
    public static void main(String[] args) throws java.lang.Exception {
    	
    	BlacklistReqVo blacklistReqVo = new BlacklistReqVo();
    	blacklistReqVo.setIdentifyNumber("A1234567890");
    	String str = WebserviceObjConvert.convertObjToBase64Str(BlacklistReqVo.class, blacklistReqVo);
    	
    	String url = "http://192.168.112.6:8880/CWP/webService/blasklistQueryService?wsdl";
    	URL wsdlURL = new URL(url);
    	BlacklistQueryService blacklistQueryService = new BlacklistQueryServiceImplService(wsdlURL).getBlacklistQueryServiceImplPort();
		String returnValue = blacklistQueryService.blacklistQuery(str);
		BlacklistRespVo responseVo = (BlacklistRespVo) WebserviceObjConvert.convertBase64StrToObj(returnValue, BlacklistRespVo.class);
		System.out.println("responseVo.getResult() = " + responseVo.getResult());
		
    	System.exit(0);
    }
    
 	
}

package com.tlg.aps.webService.corePolicyService.client;

import java.net.URL;
import java.util.ArrayList;

public class ClientTest {
	
	
    public static void main(String[] args) throws java.lang.Exception {
    	
    	String url = "http://192.168.112.98:7001/prpins/cxfService/generatePolicyService?wsdl";
    	URL wsdlURL = new URL(url);
    	GeneratePolicyService generatePolicyWebServiceService = new GeneratePolicyWebServiceService(wsdlURL).getGeneratePolicyWebServicePort();
    	
    	PolicyInfoResultVo vo = generatePolicyWebServiceService.generateMIPolicyInfos(getData());
    	
    	String returnCode = vo.returnCode;
    	String message = vo.returnMsg;

		System.out.println("returnCode = " + returnCode + " , message = " + message);
		
    	System.exit(0);
    }
    
 	private static PolicyEndorseInfoListRequest getData(){
 		
 		PolicyEndorseInfoRequest e1 = new PolicyEndorseInfoRequest();
 		
 		
 		PolicyEndorseInfoListRequest request = new PolicyEndorseInfoListRequest();
// 		request.getPolicyEndorseInfoRequests().add(e);
 		
 		request.getPolicyEndorseInfoRequests().add(e1);
 		
 		return request;
 	}
    
}

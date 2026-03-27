package com.sinosoft.app.webservice.server.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.sinosoft.app.webservice.server.schema.model.common.RespClaimData;
import com.sinosoft.app.webservice.server.schema.model.common.RespClaimRiskData;
import com.sinosoft.app.webservice.server.schema.model.regist.ReqRegist;
import com.tlg.commons.util.api.soap.regist.ObjectFactory;

/**
 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
 */
@WebService(name = "claimRegistRiskWebService", targetNamespace = "http://sinosoft.com.cn")
@SOAPBinding(parameterStyle = ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface ClaimRegistRiskService {
	
	public RespClaimRiskData registRiskByWs(ReqRegist request);
	
	public RespClaimRiskData test(ReqRegist request);

}
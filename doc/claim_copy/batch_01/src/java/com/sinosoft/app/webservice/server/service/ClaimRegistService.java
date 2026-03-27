package com.sinosoft.app.webservice.server.service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.sinosoft.app.webservice.server.schema.model.common.RespClaimData;
import com.sinosoft.app.webservice.server.schema.model.regist.ReqRegist;
import com.tlg.commons.util.api.soap.regist.ObjectFactory;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@WebService(name = "claimRegistWebService", targetNamespace = "http://sinosoft.com.cn")
@SOAPBinding(parameterStyle = ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface ClaimRegistService {
	/** 登錄前查詢 
	 * @throws Exception */
	public RespClaimData registByWs(ReqRegist request);
	
	public RespClaimData test(ReqRegist request);

}
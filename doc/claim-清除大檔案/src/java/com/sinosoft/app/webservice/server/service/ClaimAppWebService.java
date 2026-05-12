package com.sinosoft.app.webservice.server.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.sinosoft.app.webservice.server.schema.model.claimQuery.ReqClaimQuery;
import com.sinosoft.app.webservice.server.schema.model.claimQuery.RespClaimQueryResult;
import com.sinosoft.app.webservice.server.schema.model.login.ReqLogin;
import com.sinosoft.app.webservice.server.schema.model.login.RespLoginResult;
import com.sinosoft.app.webservice.server.schema.model.workflow.ReqWorkFlow;
import com.sinosoft.app.webservice.server.schema.model.workflow.RespWorkFlowResult;
@WebService(name="claimAppWebService",targetNamespace="http://service.server.webservice.app.sinosoft.com")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface ClaimAppWebService {
	/**
	 * 登入接口
	 * @param ReqLogin
	 * @return
	 */
	@WebResult(name="respLoginResult")
	@WebMethod(operationName="LOGIN",action="urn:LOGIN")
	public RespLoginResult LOGIN(@WebParam(name="reqLogin")ReqLogin reqLogin);//登入
	
	/**
     * 理賠申請(備案)
     * @param ReqRegist
     * @return
     */
//    @WebResult(name="respRegistResult")
//    @WebMethod(operationName="RECORD",action="urn:RECORD")
//    public RespRegistResult RECORD(@WebParam(name="reqRegist")ReqRegist reqRegist);//理賠申請(備案)
    /**
     * 備案文件上傳
     * @param xmlStr
     * @return
     * @throws Exception 
     */
//    @WebResult(name="respPictureResult")
//    @WebMethod(operationName="PICTURE",action="urn:PICTURE")
//    public RespPictureResult PICTURE(@WebParam(name="ReqPicture") ReqPicture reqPicture);//備案文件上傳
    /**
     * 理賠狀態查詢
     * @param xmlStr
     * @return
     * @throws Exception 
     */
    @WebResult(name="respWorkFlowResult")
    @WebMethod(operationName="WORKFLOW",action="urn:WORKFLOW")
    public RespWorkFlowResult WORKFLOW(@WebParam(name="ReqWorkFlow") ReqWorkFlow reqWorkFlow);//理賠狀態查詢
    /**
     * 理賠查詢
     * @param xmlStr
     * @return
     * @throws Exception 
     */
    @WebResult(name="respClaimQueryResult")
    @WebMethod(operationName="CLAIMQUERY",action="urn:CLAIMQUERY")
    public RespClaimQueryResult CLAIMQUERY(@WebParam(name="ReqClaimQuery") ReqClaimQuery reqClaimQuery);//理賠查詢
    
}

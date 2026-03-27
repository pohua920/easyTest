package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;

import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent.GetPrpDagentReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDagent.GetPrpDagentResPacket;

import com.sinosoft.sysframework.exception.BusinessException;
import com.wutka.jox.JOXBeanInputStream;

public class GetPrpDagentServiceImpl implements 
DataTransformer<GetPrpDagentReqPacket, GetPrpDagentResPacket>{

	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		GetPrpDagentReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String agentCode = requestPacket.getBODY().getAGENTCODE();
		PrpDagent prpDagent = dictionaryService.getPrpDagent(systemCode, agentCode);
		GetPrpDagentResPacket responsePacket = new GetPrpDagentResPacket();
		if(prpDagent==null){
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETPRPDAGENT);// 可以是当前代码，
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;
		}else{
			/**设置返回报文头*/
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETPRPDAGENT);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			/**设置返回报文体*/
			responsePacket.getBODY().getPRPDAGENTINFO().setAGENTCODE(prpDagent.getAgentCode());
			responsePacket.getBODY().getPRPDAGENTINFO().setAGENTNAME(prpDagent.getAgentName());
			responsePacket.getBODY().getPRPDAGENTINFO().setADDRESSNAME(prpDagent.getAddressName());
			responsePacket.getBODY().getPRPDAGENTINFO().setPOSTCODE(prpDagent.getPostCode());
			responsePacket.getBODY().getPRPDAGENTINFO().setAGENTTYPE(prpDagent.getAgentType());
			responsePacket.getBODY().getPRPDAGENTINFO().setPERMITNO(prpDagent.getPermitNo());
			responsePacket.getBODY().getPRPDAGENTINFO().setLINKERNAME(prpDagent.getLinkerName());
			if(prpDagent.getBargainDate()!=null){
				responsePacket.getBODY().getPRPDAGENTINFO().setBARGAINDATE(PubFun.DateToStr(prpDagent.getBargainDate()));
			}
			responsePacket.getBODY().getPRPDAGENTINFO().setPHONENUMBER(prpDagent.getPhoneNumber());
			responsePacket.getBODY().getPRPDAGENTINFO().setFAXNUMBER(prpDagent.getFaxNumber());
			responsePacket.getBODY().getPRPDAGENTINFO().setCOMCODE(prpDagent.getComCode());
			responsePacket.getBODY().getPRPDAGENTINFO().setUPPERAGENTCODE(prpDagent.getUpperAgentCode());
			responsePacket.getBODY().getPRPDAGENTINFO().setNEWAGENTCODE(prpDagent.getNewAgentCode());
			responsePacket.getBODY().getPRPDAGENTINFO().setAGENTNATURE(prpDagent.getAgentNature());
			responsePacket.getBODY().getPRPDAGENTINFO().setARTICLECODE(prpDagent.getArticleCode());
		}
		
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDagentResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDagentReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetPrpDagentReqPacket response = (GetPrpDagentReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDagentReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDagentReqPacket response = (GetPrpDagentReqPacket) joxIn
//				.readObject(GetPrpDagentReqPacket.class);
		return response;
	}

}

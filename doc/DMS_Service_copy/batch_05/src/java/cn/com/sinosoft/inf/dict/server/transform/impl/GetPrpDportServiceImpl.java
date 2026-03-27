package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport.GetPrpDportReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport.GetPrpDportResPacket;

public class GetPrpDportServiceImpl implements 
DataTransformer<GetPrpDportReqPacket, GetPrpDportResPacket>{

	public String execute(String requestxml) throws Exception {
		PrpDport prpDport = new PrpDport();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetPrpDportReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		prpDport = dictionaryService.getPrpDport(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getPORTCODE());
		requestType = ServiceInfoConst.GETPRPDPORT;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		GetPrpDportResPacket responsePacket = new GetPrpDportResPacket();
		if(prpDport==null){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
		}else{
			/**设置返回报文头*/
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			/**设置返回报文体*/
			responsePacket.getBODY().getPRPDPORT().setCOUNTRYCNAME(prpDport.getCountryCName());
			responsePacket.getBODY().getPRPDPORT().setCOUNTRYCODE(prpDport.getCountryCode());
			responsePacket.getBODY().getPRPDPORT().setCOUNTRYENAME(prpDport.getCountryEName());
			responsePacket.getBODY().getPRPDPORT().setNEWPORTCODE(prpDport.getNewPortCode());
			responsePacket.getBODY().getPRPDPORT().setPORTCNAME(prpDport.getPortCName());
			responsePacket.getBODY().getPRPDPORT().setPORTCODE(prpDport.getPortCode());
			responsePacket.getBODY().getPRPDPORT().setPORTENAME(prpDport.getPortEName());
		}
		
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDportResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDportReqPacket xmlToSchema(String requestxml) throws Exception {
		GetPrpDportReqPacket response = (GetPrpDportReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDportReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDportReqPacket response = (GetPrpDportReqPacket) joxIn
//				.readObject(GetPrpDportReqPacket.class);
		return response;
	}

}

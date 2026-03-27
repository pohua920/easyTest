package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcode.GetPrpDcodeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcode.GetPrpDcodeResPacket;

public class GetPrpDcodeServiceImpl implements DataTransformer<GetPrpDcodeReqPacket, GetPrpDcodeResPacket>{

	public String execute(String requestxml) throws Exception {
		PrpDnewCode prpDnewCode = new PrpDnewCode();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetPrpDcodeReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		prpDnewCode = dictionaryService.getPrpDcode(requestPacket.getHEAD()
				.getSYSTEMCODE(),requestPacket.getBODY().getCODECODE(), requestPacket.getBODY().getCODETYPE());
		requestType = ServiceInfoConst.GETPRPDCODE;// 设置返回报文的requestcode
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		GetPrpDcodeResPacket responsePacket = new GetPrpDcodeResPacket();
		if (null == prpDnewCode) {
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
		}else {
		/**设置返回报文头*/
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			/**设置返回报文体*/
			responsePacket.getBODY().getPRPDCODERESINFO().setCODETYPE(prpDnewCode.getId().getCodeType());
			responsePacket.getBODY().getPRPDCODERESINFO().setCODECODE(prpDnewCode.getId().getCodeCode());
			responsePacket.getBODY().getPRPDCODERESINFO().setCODECNAME(prpDnewCode.getCodeCName());
			responsePacket.getBODY().getPRPDCODERESINFO().setCODEENAME(prpDnewCode.getCodeEName());
			responsePacket.getBODY().getPRPDCODERESINFO().setOLDCODETYPE(prpDnewCode.getOldCodeType());
			responsePacket.getBODY().getPRPDCODERESINFO().setOLDCODECODE(prpDnewCode.getOldCodeCode());
			responsePacket.getBODY().getPRPDCODERESINFO().setNEWCODECODE(prpDnewCode.getNewCodeCode());
			responsePacket.getBODY().getPRPDCODERESINFO().setVALIDSTATUS(prpDnewCode.getValidStatus());
			responsePacket.getBODY().getPRPDCODERESINFO().setFLAG(prpDnewCode.getFlag());
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDcodeResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDcodeReqPacket xmlToSchema(String requestxml) throws Exception {
		GetPrpDcodeReqPacket response = (GetPrpDcodeReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDcodeReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDcodeReqPacket response = (GetPrpDcodeReqPacket) joxIn
//				.readObject(GetPrpDcodeReqPacket.class);
		return response;
	}

}

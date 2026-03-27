package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode.GetUpperCodeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getUpperCode.GetUpperCodeResPacket;

public class GetUpperCodeServiceImpl 
	implements DataTransformer<GetUpperCodeReqPacket, GetUpperCodeResPacket>{

	public String execute(String requestxml) throws Exception {
		PrpDnewCode prpDcode = new PrpDnewCode();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetUpperCodeReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		prpDcode = dictionaryService.getUpperCode(requestPacket.getHEAD()
				.getSYSTEMCODE(),requestPacket.getBODY().getCODETYPE(), requestPacket.getBODY().getCODECODE());
		requestType = ServiceInfoConst.GETUPPERCODE;// 设置返回报文的requestcode
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		GetUpperCodeResPacket responsePacket = new GetUpperCodeResPacket();
		if(null == prpDcode){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
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
			responsePacket.getBODY().getUPPERCODERESINFO().setCODETYPE(prpDcode.getId().getCodeType());
			responsePacket.getBODY().getUPPERCODERESINFO().setCODECODE(prpDcode.getId().getCodeCode());
			responsePacket.getBODY().getUPPERCODERESINFO().setCODECNAME(prpDcode.getCodeCName());
			responsePacket.getBODY().getUPPERCODERESINFO().setCODEENAME(prpDcode.getCodeEName());
			//modify by duanfa 2011-05-28 修改prpdnewcode数据库结构
//			responsePacket.getBODY().getUPPERCODERESINFO().setNEWCODECODE(prpDcode.getNewCodeCode());
			responsePacket.getBODY().getUPPERCODERESINFO().setNEWCODECODE(prpDcode.getId().getCodeCode());
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetUpperCodeResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetUpperCodeReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetUpperCodeReqPacket response = (GetUpperCodeReqPacket) PubFun
		.generateJox(requestxml).readObject(GetUpperCodeReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetUpperCodeReqPacket response = (GetUpperCodeReqPacket) joxIn
//				.readObject(GetUpperCodeReqPacket.class);
		return response;
	}


}

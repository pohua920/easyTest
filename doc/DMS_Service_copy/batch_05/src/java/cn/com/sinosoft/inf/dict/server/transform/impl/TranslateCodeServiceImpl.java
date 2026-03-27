package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.translateCode.TranslateCodeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.translateCode.TranslateCodeResPacket;
/**
 * 代码翻译
 * */
public class TranslateCodeServiceImpl implements DataTransformer<TranslateCodeReqPacket, TranslateCodeResPacket> {

	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		TranslateCodeReqPacket requestPacket = xmlToSchema(requestxml);
		String requestType = "";
		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String codeType = requestPacket.getBODY().getCODETYPE();
		String codeCode = requestPacket.getBODY().getCODECODE();
		String language = requestPacket.getBODY().getLANGUAGE();
		String codeFlag = requestPacket.getBODY().getCODEFLAG();
		
		String codeName = dictionaryService.translateCode(systemCode, codeType, codeCode,codeFlag, language);
		TranslateCodeResPacket responsePacket = new TranslateCodeResPacket();
		requestType = ServiceInfoConst.TRANSLATECODE;
		if(null == codeName||"".equals(codeName)){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
		}else{
			responsePacket.getBODY().setCODENAME(codeName);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public TranslateCodeReqPacket xmlToSchema(String requestxml)
			throws Exception {
		TranslateCodeReqPacket response = (TranslateCodeReqPacket) PubFun
		.generateJox(requestxml).readObject(TranslateCodeReqPacket.class);
		return response;
	}

	public String schemaToXml(TranslateCodeResPacket responsePacket) throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(responsePacket, responsePacket.getHEAD()
				.getREQUEST_TYPE());
		return responsexml;
	}
}

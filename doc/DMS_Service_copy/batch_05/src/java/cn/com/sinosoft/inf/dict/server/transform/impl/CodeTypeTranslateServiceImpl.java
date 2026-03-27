package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.codeTypeTranslate.CodeTypeTranslateReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.codeTypeTranslate.CodeTypeTranslateResPacket;

public class CodeTypeTranslateServiceImpl 
	implements DataTransformer<CodeTypeTranslateReqPacket, CodeTypeTranslateResPacket>{
	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		CodeTypeTranslateReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String codeType = requestPacket.getBODY().getCODETYPE();
		
		String codeName = dictionaryService.codeTypeTranslate(systemCode, codeType);
		CodeTypeTranslateResPacket responsePacket = new CodeTypeTranslateResPacket();
		String requestType = "";
		requestType = ServiceInfoConst.CODETYPETRANSLATE;
		if("".equals(codeName)||null == codeName){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			responsePacket.getBODY();
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;
		}else{
			responsePacket.getBODY().setCODETYPECNAME(codeName);
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

	public CodeTypeTranslateReqPacket xmlToSchema(String requestxml)
			throws Exception {
		CodeTypeTranslateReqPacket response = (CodeTypeTranslateReqPacket) PubFun
		.generateJox(requestxml).readObject(CodeTypeTranslateReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		CodeTypeTranslateReqPacket response = (CodeTypeTranslateReqPacket) joxIn
//				.readObject(CodeTypeTranslateReqPacket.class);
		return response;
	}

	public String schemaToXml(CodeTypeTranslateResPacket responsePacket) throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(responsePacket, responsePacket.getHEAD()
				.getREQUEST_TYPE());
		return responsexml;
	}
}

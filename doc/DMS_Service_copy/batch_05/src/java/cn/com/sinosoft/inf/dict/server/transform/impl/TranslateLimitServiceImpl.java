package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.CodeTranslateResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.CodeTranslateResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.TranslateVO;
import cn.com.sinosoft.inf.dict.xmlmsg.translateLimit.TranslateLimitReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.translateLimit.TranslateLimitReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.translateLimit.TranslateLimitResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.translateLimit.TranslateLimitResPacket;

import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;
import com.sinosoft.dmsdriver.domain.common.ResponseHeadSchema;
import com.thoughtworks.xstream.XStream;
/**
 * 代码翻译
 * */
public class TranslateLimitServiceImpl implements DataTransformer<TranslateLimitReqPacket,TranslateLimitResPacket> {

	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		TranslateLimitReqPacket requestPacket = xmlToSchema(requestxml);
		String requestType = "";
		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String riskCode = requestPacket.getBODY().getRISKCODE();
		String limitCode = requestPacket.getBODY().getLIMITCODE();
		String codeName = dictionaryService.translateLimit(systemCode, riskCode, limitCode);
		TranslateLimitResPacket responsePacket = new TranslateLimitResPacket();
		requestType = ServiceInfoConst.TRANSLATELIMIT;
		if(null == codeName||"".equals(codeName)){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
		}else{
			responsePacket.getBODY().setCODECNAME(codeName);
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

	public TranslateLimitReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream();
		xs.alias("TranslateLimitReqPacket", TranslateLimitReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("TranslateLimitReqBody", TranslateLimitReqBody.class);
		TranslateLimitReqPacket ep = (TranslateLimitReqPacket) xs.fromXML(requestxml);
		return ep;
	}

	public String schemaToXml(TranslateLimitResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("TranslateLimitResPacket", TranslateLimitResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("TranslateLimitResBody", TranslateLimitResBody.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}
	
}

package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.List;

import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.CodeTranslateReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.CodeTranslateReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.CodeTranslateResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.CodeTranslateResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.TranslateVO;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

import com.thoughtworks.xstream.XStream;

public class CodeTranslateServiceImpl implements
		DataTransformer<CodeTranslateReqPacket, CodeTranslateResPacket> {

	public String execute(String requestxml) throws Exception {
		CodeTranslateResPacket resPacket = new CodeTranslateResPacket();
		CodeTranslateReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		
		List<TranslateVO> voList = requestPacket.getBODY().getTRANSLATELIST();
		String transType = requestPacket.getBODY().getTRANSTYPE();
		
		List list = dictionaryService.codeTranslate(systemCode, voList, transType);
		ResponseHeadSchema head = MessageUtil
				.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.CODETRANSLATE,
						ServiceInfoConst.RESPONSECODE_SUCCESS);
		resPacket.setHEAD(head);
		resPacket.getBODY().setTRANSLATELIST(list);
		String responsexml = schemaToXml(resPacket);
		return responsexml;
	}

	public CodeTranslateReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("CodeTranslateReqPacket",CodeTranslateReqPacket.class);
		xs.alias("RequestHeadPacket",RequestHeadPacket.class);
		xs.alias("CodeTranslateReqBody",CodeTranslateReqBody.class);
		xs.alias("TranslateVO", TranslateVO.class);
		CodeTranslateReqPacket ep = (CodeTranslateReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	public String schemaToXml(CodeTranslateResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("CodeTranslateResPacket", CodeTranslateResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("CodeTranslateResBody", CodeTranslateResBody.class);
		xs.alias("TranslateVO", TranslateVO.class);
		/***********************************************/
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}

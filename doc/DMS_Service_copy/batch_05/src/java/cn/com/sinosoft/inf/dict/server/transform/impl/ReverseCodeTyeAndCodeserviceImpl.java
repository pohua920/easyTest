package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.List;

import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeResPacket;

import com.thoughtworks.xstream.XStream;

public class ReverseCodeTyeAndCodeserviceImpl implements
		DataTransformer<ReverseCodeTyeAndCodeReqPacket, ReverseCodeTyeAndCodeResPacket> {

	public String execute(String requestxml) throws Exception {
		ReverseCodeTyeAndCodeResPacket resPacket = new ReverseCodeTyeAndCodeResPacket();
		ReverseCodeTyeAndCodeReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		
		String reverseType = requestPacket.getBODY().getREVERSETYPE();
		List codeVolist = requestPacket.getBODY().getCODEVOLIST();
		
		List list = dictionaryService.reverseCodeTyeAndCode(systemCode, codeVolist, reverseType);
		ResponseHeadSchema head = MessageUtil
		.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS,
				ServiceInfoConst.CODETRANSLATE,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		resPacket.setHEAD(head);
		resPacket.getBODY().setCODEVOLIST(list);
		String responsexml = schemaToXml(resPacket);
		return responsexml;
	}

	public ReverseCodeTyeAndCodeReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空常");
		}
		XStream xs = new XStream();
		xs.alias("ReverseCodeTyeAndCodeReqPacket",ReverseCodeTyeAndCodeReqPacket.class);
		xs.alias("RequestHeadSchema",RequestHeadSchema.class);
		xs.alias("ReverseCodeTyeAndCodeReqBody",ReverseCodeTyeAndCodeReqBody.class);
		ReverseCodeTyeAndCodeReqPacket ep = (ReverseCodeTyeAndCodeReqPacket) xs.fromXML(requestxml, new ReverseCodeTyeAndCodeReqPacket());
		return ep;
	}
	
	public String schemaToXml(ReverseCodeTyeAndCodeResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("ReverseCodeTyeAndCodeResPacket", ReverseCodeTyeAndCodeResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("ReverseCodeTyeAndCodeResBody", ReverseCodeTyeAndCodeResBody.class);
		/***********************************************/
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}
}

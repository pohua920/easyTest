package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.List;

import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.codetransform.CodeTransformReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.codetransform.CodeTransformReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.codetransform.CodeTransformResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.codetransform.CodeTransformResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

import com.thoughtworks.xstream.XStream;

public class CodeTransformServiceImpl implements
		DataTransformer<CodeTransformReqPacket, CodeTransformResPacket> {

	public String execute(String requestxml) throws Exception {
		CodeTransformResPacket pageResPacket = new CodeTransformResPacket();
		CodeTransformReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String codeType = requestPacket.getBODY().getCODETYPE();
		String codeCode = requestPacket.getBODY().getCODECODE();
		String transType = requestPacket.getBODY().getTRANSTYPE();
		List list = dictionaryService.codeTransform(systemCode, codeType, codeCode, transType);
		ResponseHeadSchema head = MessageUtil
				.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.CODETRANSFORM,
						ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.getBODY().setCODECODE(list);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public CodeTransformReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("CodeTransformReqPacket",CodeTransformReqPacket.class);
		xs.alias("RequestHeadPacket",RequestHeadPacket.class);
		xs.alias("CodeTransformReqBody",CodeTransformReqBody.class);
		CodeTransformReqPacket ep = (CodeTransformReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	public String schemaToXml(CodeTransformResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("CodeTransformResPacket", CodeTransformResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		/***********************************************/
		xs.alias("CodeTransformResBody", CodeTransformResBody.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}

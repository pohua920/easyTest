package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.List;

import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.risktransform.RiskTransformReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.risktransform.RiskTransformReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.risktransform.RiskTransformResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.risktransform.RiskTransformResPacket;

import com.thoughtworks.xstream.XStream;

public class RiskTransformServiceImpl implements
		DataTransformer<RiskTransformReqPacket, RiskTransformResPacket> {

	public String execute(String requestxml) throws Exception {
		RiskTransformResPacket pageResPacket = new RiskTransformResPacket();
		RiskTransformReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String riskCode = requestPacket.getBODY().getRISKCODE();
		String clauseCode = requestPacket.getBODY().getCLAUSECODE();
		String kindCode = requestPacket.getBODY().getKINDCODE();
		String transType = requestPacket.getBODY().getTRANSTYPE();
		
		List list = dictionaryService.riskTransform(systemCode, riskCode, clauseCode, kindCode, transType);
		ResponseHeadSchema head = MessageUtil
				.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.RISKTRANSFORM,
						ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.getBODY().setCODECODE(list);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public RiskTransformReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("RiskTransformReqPacket",RiskTransformReqPacket.class);
		xs.alias("RequestHeadPacket",RequestHeadPacket.class);
		xs.alias("RiskTransformReqBody",RiskTransformReqBody.class);
		RiskTransformReqPacket ep = (RiskTransformReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	public String schemaToXml(RiskTransformResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("RiskTransformResPacket", RiskTransformResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("RiskTransformResBody", RiskTransformResBody.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}

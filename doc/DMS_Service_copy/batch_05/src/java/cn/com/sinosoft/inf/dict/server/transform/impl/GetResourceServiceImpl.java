package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDresource;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getresource.GetResourceReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getresource.GetResourceReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetResourceServiceImpl implements DataTransformer<GetResourceReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetResourceReqPacket getResourceReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getResourceReqPacket.getHEAD().getSYSTEMCODE();
		String resourceCodeOrName = getResourceReqPacket.getBODY().getResourceCodeOrName();
		String agentCode = getResourceReqPacket.getBODY().getAgentCode();
		String projectCode = getResourceReqPacket.getBODY().getProjectCode();
		String comCode = getResourceReqPacket.getBODY().getComCode();
		
		int pageNO = getResourceReqPacket.getHEAD().getPAGENO();
		int pageSize = getResourceReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getResource(systemCode, resourceCodeOrName,projectCode,agentCode,comCode,pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETRESOURCE,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDresource", PrpDresource.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetResourceReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetResourceReqPacket", GetResourceReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetResourceReqBody", GetResourceReqBody.class);
		GetResourceReqPacket ep = (GetResourceReqPacket) xs.fromXML(requestxml, new GetResourceReqPacket());
		return ep;
	}


}



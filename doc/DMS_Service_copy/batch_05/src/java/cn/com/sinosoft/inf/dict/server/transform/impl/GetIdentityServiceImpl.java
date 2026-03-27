package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDidentifier;
import cn.com.sinosoft.dms.model.PrpDidentifierId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.GetIdentity.GetIdentityReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.GetIdentity.GetIdentityReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetIdentityServiceImpl implements
		DataTransformer<GetIdentityReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {

		DictPage dictPage = new DictPage();
		GetIdentityReqPacket getIdentityReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean

		String systemcode = getIdentityReqPacket.getHEAD().getSYSTEMCODE();
		String  countryCName = getIdentityReqPacket.getBODY().getCountryCName();
		String  countryCode  = getIdentityReqPacket.getBODY().getCountryCode();
		String  countryEName = getIdentityReqPacket.getBODY().getCountryEName();
		String  identifierCode = getIdentityReqPacket.getBODY().getIdentifierCode();
		String  identifierName = getIdentityReqPacket.getBODY().getIdentifierName();
		String  identifierType = getIdentityReqPacket.getBODY().getIdentifierType();
		String  portCode = getIdentityReqPacket.getBODY().getPortCode();
		String  portName = getIdentityReqPacket.getBODY().getPortName();
		int pageNO = getIdentityReqPacket.getHEAD().getPAGENO();
		int pageSize = getIdentityReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getIdentity(systemcode, identifierCode, 
				identifierName, portCode, portName, countryCode, countryCName,
				countryEName, identifierType, pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETIDENTITY,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDidentifier",PrpDidentifier.class);
		xstream.alias("PrpDidentifierId",PrpDidentifierId.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetIdentityReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream(new DomDriver());
		xs.alias("GetIdentityReqPacket", GetIdentityReqPacket.class);
		xs.alias("GetIdentityReqBody", GetIdentityReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetIdentityReqPacket ep = (GetIdentityReqPacket) xs.fromXML(requestxml,
				new GetIdentityReqPacket());
		return ep;
	}

}

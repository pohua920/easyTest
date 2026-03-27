package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDidentifierDesc;
import cn.com.sinosoft.dms.model.PrpDidentifierDescId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getIdentityDesc.GetIdentityDescReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getIdentityDesc.GetIdentityDescReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;

import com.thoughtworks.xstream.XStream;

public class GetIdentityDescServiceImpl implements DataTransformer<GetIdentityDescReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetIdentityDescReqPacket getIdentityDescReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getIdentityDescReqPacket.getHEAD().getSYSTEMCODE();
		String identifierCode = getIdentityDescReqPacket.getBODY().getIDENTIFIERCODE();
		
		int pageNO = getIdentityDescReqPacket.getHEAD().getPAGENO();
		int pageSize = getIdentityDescReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getIdentityDesc(systemCode, identifierCode, pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETIDENTITYDESC,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDidentifierDescId", PrpDidentifierDescId.class);
		xstream.alias("PrpDidentifierDesc", PrpDidentifierDesc.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetIdentityDescReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetIdentityDescReqPacket", GetIdentityDescReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetIdentityDescReqBody", GetIdentityDescReqBody.class);
		GetIdentityDescReqPacket ep = (GetIdentityDescReqPacket) xs.fromXML(requestxml, new GetIdentityDescReqPacket());
		return ep;
	}


}
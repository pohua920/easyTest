package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcrossOrg.GetPrpDcrossOrgReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcrossOrg.GetPrpDcrossOrgReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype.GetPrpDtypeReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetPrpDcrossOrgServiceImpl implements
		DataTransformer<GetPrpDcrossOrgReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {

		DictPage dictPage = new DictPage();
		GetPrpDcrossOrgReqPacket getPrpDcrossOrgReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean

		String systemcode = getPrpDcrossOrgReqPacket.getHEAD().getSYSTEMCODE();
		String orgcod = getPrpDcrossOrgReqPacket.getBODY().getOrgcod();
		String comp_cod = getPrpDcrossOrgReqPacket.getBODY().getComp_cod();
		String org_lvl = getPrpDcrossOrgReqPacket.getBODY().getOrg_lvl();
		int pageNO = getPrpDcrossOrgReqPacket.getHEAD().getPAGENO();
		int pageSize = getPrpDcrossOrgReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getPrpDcrossOrg(systemcode, orgcod,
				comp_cod, org_lvl, pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETPRPDCROSSORG,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDcrossOrg",PrpDcrossOrg.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDcrossOrgReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream();
		xs.alias("GetPrpDcrossOrgReqPacket", GetPrpDcrossOrgReqPacket.class);
		xs.alias("GetPrpDcrossOrgReqBody", GetPrpDcrossOrgReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetPrpDcrossOrgReqPacket ep = (GetPrpDcrossOrgReqPacket) xs.fromXML(requestxml,
				new GetPrpDcrossOrgReqPacket());
		return ep;
	}

}

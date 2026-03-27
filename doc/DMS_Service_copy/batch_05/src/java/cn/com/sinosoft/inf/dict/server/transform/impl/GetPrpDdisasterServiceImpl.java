package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.Date;

import cn.com.sinosoft.dms.model.PrpDdisaster;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.PrpDdisasterReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.PrpDdisasterReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;

import com.thoughtworks.xstream.XStream;

public class GetPrpDdisasterServiceImpl implements
		DataTransformer<PrpDdisasterReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		PrpDdisasterReqPacket prpDdisasterReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = prpDdisasterReqPacket.getHEAD().getSYSTEMCODE();
		String disasterCode = prpDdisasterReqPacket.getBODY().getDISASTERCODE();
		Date damageDate = prpDdisasterReqPacket.getBODY().getDAMAGEDATE();
		
		String validStatus = prpDdisasterReqPacket.getHEAD().getVALIDSTATUS();
		int pageNO = prpDdisasterReqPacket.getHEAD().getPAGENO();
		int pageSize = prpDdisasterReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getPrpDdisaster(systemCode, disasterCode,
				validStatus, damageDate,pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.PRPDDISASTER,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDdisaster", PrpDdisaster.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public PrpDdisasterReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("PrpDdisasterReqPacket", PrpDdisasterReqPacket.class);
		xs.alias("PrpDdisasterReqBody", PrpDdisasterReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		PrpDdisasterReqPacket ep = (PrpDdisasterReqPacket) xs.fromXML(requestxml, new PrpDdisasterReqPacket());
		return ep;
	}

}

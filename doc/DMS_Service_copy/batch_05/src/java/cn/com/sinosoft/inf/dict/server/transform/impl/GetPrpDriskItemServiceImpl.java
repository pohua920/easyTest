package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDriskItem.GetPrpDriskItemReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDriskItem.GetPrpDriskItemReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetPrpDriskItemServiceImpl implements
		DataTransformer<GetPrpDriskItemReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetPrpDriskItemReqPacket prpDriskItemReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = prpDriskItemReqPacket.getHEAD().getSYSTEMCODE();
		String riskCode = prpDriskItemReqPacket.getBODY().getRISKCODE();
		String upperItemCode = prpDriskItemReqPacket.getBODY().getUPPERITEMCODE();
		String itemCode = prpDriskItemReqPacket.getBODY().getITEMCODE();
		String clauseCode = prpDriskItemReqPacket.getBODY().getCLAUSECODE();
		String extraItemCode = prpDriskItemReqPacket.getBODY().getEXTRAITEMCODE();
		
		int pageNO = prpDriskItemReqPacket.getHEAD().getPAGENO();
		int pageSize = prpDriskItemReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getPrpDriskItem(systemCode, riskCode,
				itemCode, upperItemCode, clauseCode, extraItemCode,pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETPRPDRISKITEM,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDriskItem", PrpDriskItem.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDriskItemReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetPrpDriskItemReqPacket", GetPrpDriskItemReqPacket.class);
		xs.alias("GetPrpDriskItemReqBody", GetPrpDriskItemReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetPrpDriskItemReqPacket ep = (GetPrpDriskItemReqPacket) xs.fromXML(requestxml, new GetPrpDriskItemReqPacket());
		return ep;
	}

}


package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.Date;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDrationShortrate;
import cn.com.sinosoft.dms.model.PrpdRationRate;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestBodySchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

import com.thoughtworks.xstream.XStream;

public class GetRationRateServiceImpl implements
		DataTransformer<RequestPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		RequestPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//
		DictPage dictPage = new DictPage();
		
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String riskCode = (String) requestPacket.getBODY().getValues().get("riskCode");
		String clauseCode = (String) requestPacket.getBODY().getValues().get("clauseCode");
		String kindCode = (String) requestPacket.getBODY().getValues().get("kindCode");
		String rationCode = (String) requestPacket.getBODY().getValues().get("rationCode");
		String areaCode = (String) requestPacket.getBODY().getValues().get("areaCode");
		Date startDate = (Date) requestPacket.getBODY().getValues().get("startDate");
		Date endDate = (Date) requestPacket.getBODY().getValues().get("endDate");
		int startHour = (Integer) requestPacket.getBODY().getValues().get("startHour");
		int endHour = (Integer) requestPacket.getBODY().getValues().get("endHour");
		int pageNo = (Integer) requestPacket.getBODY().getValues().get("pageNo");
		int pageSize = (Integer)requestPacket.getBODY().getValues().get("pageSize");
		dictPage = dictionaryService.getRationRate(systemCode, riskCode, areaCode, clauseCode, kindCode, rationCode, startDate, startHour, endDate, endHour, pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETRATIONRATE, ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);

		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("PrpdRationRate", PrpdRationRate.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

	public RequestPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("请求参数为空！");
		}
		XStream xs = new XStream();
		xs.alias("RequestPacket",RequestPacket.class);
		xs.alias("RequestHeadSchema",RequestHeadSchema.class);
		xs.alias("RequestBodySchema",RequestBodySchema.class);
		RequestPacket ep = (RequestPacket) xs.fromXML(requestxml, new RequestPacket());
		return ep;
	}
}

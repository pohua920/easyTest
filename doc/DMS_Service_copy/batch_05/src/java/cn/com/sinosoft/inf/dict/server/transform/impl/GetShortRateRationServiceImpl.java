package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.Date;

import cn.com.sinosoft.dms.model.PrpDrationShortrate;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getshortrate.GetShortRateReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getshortrate.GetShortRateReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetShortRateRationServiceImpl implements
		DataTransformer<GetShortRateReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetShortRateReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		DictPage dictPage = new DictPage();
		
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String riskCode = requestPacket.getBODY().getRISKCODE();
		String clauseCode = requestPacket.getBODY().getCLAUSECODE();
		String rateType = requestPacket.getBODY().getRATETYPE();
		String rationCode = requestPacket.getBODY().getRATIONCODE();
		String areaCode = requestPacket.getBODY().getAREACODE();
		Date startDate = requestPacket.getBODY().getSTARTDATE();
		Date endDate = requestPacket.getBODY().getENDDATE();
		int startHour = requestPacket.getBODY().getSTARTHOUR();
		int endHour = requestPacket.getBODY().getENDHOUR();
		int pageNo = requestPacket.getHEAD().getPAGENO();
		int pageSize = requestPacket.getHEAD().getPAGESIZE();
		dictPage = dictionaryService.getRationShortRate(systemCode, riskCode, areaCode, clauseCode, rateType, rationCode, startDate, startHour, endDate, endHour, pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETSHORTRATE, ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);

		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("PrpDrationShortrate", PrpDrationShortrate.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

	public GetShortRateReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetShortRateReqPacket",GetShortRateReqPacket.class);
		xs.alias("RequestHeadSchema",RequestHeadSchema.class);
		xs.alias("GetShortRateReqBody",GetShortRateReqBody.class);
		GetShortRateReqPacket ep = (GetShortRateReqPacket) xs.fromXML(requestxml, new GetShortRateReqPacket());
		return ep;
	}
}

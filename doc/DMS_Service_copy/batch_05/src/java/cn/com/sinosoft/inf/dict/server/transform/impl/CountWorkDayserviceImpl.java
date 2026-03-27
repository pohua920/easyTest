package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.Date;

import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay.CountWorkDayReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay.CountWorkDayReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay.CountWorkDayResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay.CountWorkDayResPacket;

import com.thoughtworks.xstream.XStream;

public class CountWorkDayserviceImpl implements
		DataTransformer<CountWorkDayReqPacket, CountWorkDayResPacket> {

	public String execute(String requestxml) throws Exception {
		CountWorkDayResPacket resPacket = new CountWorkDayResPacket();
		CountWorkDayReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		
		Date date = requestPacket.getBODY().getDate();
		int n  = requestPacket.getBODY().getN();
		String flag = requestPacket.getBODY().getFlag();
		
		Date result = dictionaryService.countWorkDay(systemCode, date, n ,flag);
		ResponseHeadSchema head = MessageUtil
		.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS,
				ServiceInfoConst.CODETRANSLATE,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		resPacket.setHEAD(head);
		resPacket.getBODY().setResult(result);
		String responsexml = schemaToXml(resPacket);
		return responsexml;
	}

	public CountWorkDayReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空常");
		}
		XStream xs = new XStream();
		xs.alias("CountWorkDayReqPacket",CountWorkDayReqPacket.class);
		xs.alias("RequestHeadSchema",RequestHeadSchema.class);
		xs.alias("CountWorkDayReqBody",CountWorkDayReqBody.class);
		CountWorkDayReqPacket ep = (CountWorkDayReqPacket) xs.fromXML(requestxml, new CountWorkDayReqPacket());
		return ep;
	}
	
	public String schemaToXml(CountWorkDayResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("CountWorkDayResPacket", CountWorkDayResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("CountWorkDayResBody", CountWorkDayResBody.class);
		/***********************************************/
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}
}

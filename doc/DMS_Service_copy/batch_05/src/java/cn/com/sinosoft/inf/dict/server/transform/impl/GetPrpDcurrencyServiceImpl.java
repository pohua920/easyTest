package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDcurrency;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdcurrency.GetPrpDcurrencyReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdcurrency.GetPrpDcurrencyReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetPrpDcurrencyServiceImpl implements
		DataTransformer<GetPrpDcurrencyReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket resPacket = new PageResPacket();
		GetPrpDcurrencyReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
		int pageNo = requestPacket.getHEAD().getPAGENO();
		int pageSize = requestPacket.getHEAD().getPAGESIZE();
		String currencyCode = requestPacket.getBODY().getCURRENCYCODE();
		String currencyName = requestPacket.getBODY().getCURRENCYNAME();
		DictPage page = dictionaryService.getPrpDcurrency(systemCode, currencyCode, currencyName, validStatus, pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil
				.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.GETPRPDCURRENCY,
						ServiceInfoConst.RESPONSECODE_SUCCESS);
		resPacket.setHEAD(head);
		resPacket.setBODY(page);
		String responsexml = schemaToXml(resPacket);
		return responsexml;
	}

	public GetPrpDcurrencyReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("GetPrpDcurrencyReqPacket", GetPrpDcurrencyReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetPrpDcurrencyReqBody", GetPrpDcurrencyReqBody.class);
		xs.alias("PrpDcurrency", PrpDcurrency.class);
		GetPrpDcurrencyReqPacket ep = (GetPrpDcurrencyReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);
		xs.alias("PrpDcurrency", PrpDcurrency.class);
		/***********************************************/
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}

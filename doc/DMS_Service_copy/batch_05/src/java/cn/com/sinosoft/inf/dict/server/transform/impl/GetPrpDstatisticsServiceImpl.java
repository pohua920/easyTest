package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDstatistics;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdstatistics.GetPrpDstatisticsReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdstatistics.GetPrpDstatisticsReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetPrpDstatisticsServiceImpl implements
		DataTransformer<GetPrpDstatisticsReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetPrpDstatisticsReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		int pageNo = requestPacket.getHEAD().getPAGENO();
		int pageSize = requestPacket.getHEAD().getPAGESIZE();
		String makeCom = requestPacket.getBODY().getMAKECOM();
		DictPage dictPage = dictionaryService.getPrpDstatistics(systemCode, makeCom, pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil
				.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.GETPRPDSTATISTICS,
						ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public GetPrpDstatisticsReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("GetPrpDstatisticsReqPacket",GetPrpDstatisticsReqPacket.class);
		xs.alias("RequestHeadPacket",RequestHeadPacket.class);
		xs.alias("GetPrpDstatisticsReqBody",GetPrpDstatisticsReqBody.class);
		GetPrpDstatisticsReqPacket ep = (GetPrpDstatisticsReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("PrpDstatistics", PrpDstatistics.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}

package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getTradeCodes.GetTradeCodesReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getTradeCodes.GetTradeCodesReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetTradeCodesServiceImpl implements DataTransformer<GetTradeCodesReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetTradeCodesReqPacket getTradeCodesReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getTradeCodesReqPacket.getHEAD().getSYSTEMCODE();
		String riskCode = getTradeCodesReqPacket.getBODY().getRiskCode();
		String upperCode = getTradeCodesReqPacket.getBODY().getUpperCode();
		
		int pageNo = getTradeCodesReqPacket.getHEAD().getPAGENO();
		int pageSize = getTradeCodesReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getTradeCodes(systemCode, upperCode, riskCode, pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETTRADECODES,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
		
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDnewCode", PrpDnewCode.class);
		xstream.alias("PrpDnewCodeId", PrpDnewCodeId.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetTradeCodesReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetTradeCodesReqPacket", GetTradeCodesReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetTradeCodesReqBody", GetTradeCodesReqBody.class);
		GetTradeCodesReqPacket ep = (GetTradeCodesReqPacket) xs.fromXML(requestxml, new GetTradeCodesReqPacket());
		return ep;
	}


}



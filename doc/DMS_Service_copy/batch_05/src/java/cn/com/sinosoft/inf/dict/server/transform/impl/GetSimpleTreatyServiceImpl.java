package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDSimpleTreaty;
import cn.com.sinosoft.dms.model.PrpDSimpleTreatyId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getSimpleTreaty.GetSimpleTreatyReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getSimpleTreaty.GetSimpleTreatyReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetSimpleTreatyServiceImpl implements DataTransformer<GetSimpleTreatyReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetSimpleTreatyReqPacket getSimpleTreatyReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getSimpleTreatyReqPacket.getHEAD().getSYSTEMCODE();
		String classCode = getSimpleTreatyReqPacket.getBODY().getClassCode();
		String riskCode = getSimpleTreatyReqPacket.getBODY().getRiskCode();
		String sectionNo = getSimpleTreatyReqPacket.getBODY().getSectionNo();
		String startDate = getSimpleTreatyReqPacket.getBODY().getStartDate();
		String endDate = getSimpleTreatyReqPacket.getBODY().getEndDate();;
		
		int pageNo = getSimpleTreatyReqPacket.getHEAD().getPAGENO();
		int pageSize = getSimpleTreatyReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getSimpleTreaty(systemCode,classCode,riskCode,sectionNo,startDate,endDate,pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETSIMPLETREATY,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDSimpleTreaty", PrpDSimpleTreaty.class);
		xstream.alias("PrpDSimpleTreatyId", PrpDSimpleTreatyId.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetSimpleTreatyReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetSimpleTreatyReqPacket", GetSimpleTreatyReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetSimpleTreatyReqBody", GetSimpleTreatyReqBody.class);
		GetSimpleTreatyReqPacket ep = (GetSimpleTreatyReqPacket) xs.fromXML(requestxml, new GetSimpleTreatyReqPacket());
		return ep;
	}


}



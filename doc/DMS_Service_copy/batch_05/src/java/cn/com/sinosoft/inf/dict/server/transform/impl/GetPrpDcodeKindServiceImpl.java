package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDcodeKind;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeKind.GetPrpDcodeKindReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeKind.GetPrpDcodeKindReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;

import com.thoughtworks.xstream.XStream;

public class GetPrpDcodeKindServiceImpl implements DataTransformer<GetPrpDcodeKindReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetPrpDcodeKindReqPacket getPrpDcodeKindReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getPrpDcodeKindReqPacket.getHEAD().getSYSTEMCODE();
		String riskCode = getPrpDcodeKindReqPacket.getBODY().getRISKCODE();
		String codeType = getPrpDcodeKindReqPacket.getBODY().getCODETYPE();
		String kindCode = getPrpDcodeKindReqPacket.getBODY().getKINDCODE();
		
		int pageNO = getPrpDcodeKindReqPacket.getHEAD().getPAGENO();
		int pageSize = getPrpDcodeKindReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getPrpDcodeKind(systemCode,riskCode,codeType,kindCode,pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETRESOURCE,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDcodeKind", PrpDcodeKind.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDcodeKindReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetPrpDcodeKindReqPacket", GetPrpDcodeKindReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetPrpDcodeKindReqBody", GetPrpDcodeKindReqBody.class);
		GetPrpDcodeKindReqPacket ep = (GetPrpDcodeKindReqPacket) xs.fromXML(requestxml, new GetPrpDcodeKindReqPacket());
		return ep;
	}


}



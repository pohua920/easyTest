package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.GetCodeReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.GetCodeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.PrpDcodeInfo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetCodeServiceImpl implements
		DataTransformer<GetCodeReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetCodeReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String codeType = requestPacket.getBODY().getCodeType();
		String codeFlag = requestPacket.getBODY().getCodeFlag();
		int pageNo = requestPacket.getBODY().getDictPage().getPageNo();
		int pageSize = requestPacket.getBODY().getDictPage().getPageSize();
		 DictPage dictPage = dictionaryService.getCode(systemCode,codeType,codeFlag, pageNo, pageSize);
		 ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETCODE, ServiceInfoConst.RESPONSECODE_SUCCESS);
		 pageResPacket.setHEAD(head);
		 pageResPacket.setBODY(dictPage);
		 String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		xstream.alias("PrpDcodeInfo", PrpDcodeInfo.class);
		xstream.alias("PrpDcodeId", PrpDnewCodeId.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetCodeReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("请求报文不能为空！");
		}
		XStream xs = new XStream();
		xs.alias("GetCodeReqPacket",GetCodeReqPacket.class);
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		xs.alias("GetCodeReqBody",GetCodeReqBody.class);
		xs.alias("DictPage", DictPage.class);
		xs.alias("PrpDcodeInfo", PrpDcodeInfo.class);
		xs.alias("PrpDcodeId", PrpDnewCodeId.class);
		GetCodeReqPacket ep = (GetCodeReqPacket) xs.fromXML(requestxml, new GetCodeReqPacket());
		return ep;
	}
}
 
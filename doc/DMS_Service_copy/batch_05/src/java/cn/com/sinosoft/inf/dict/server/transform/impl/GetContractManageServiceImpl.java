package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDcontractManage;
import cn.com.sinosoft.dms.model.PrpDcontractManageId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getContractManage.GetContractManageReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getContractManage.GetContractManageReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetContractManageServiceImpl implements
		DataTransformer<GetContractManageReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {

		DictPage dictPage = new DictPage();
		GetContractManageReqPacket getContractManageReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean

		String systemcode = getContractManageReqPacket.getHEAD().getSYSTEMCODE();
		String contractObjectCode = getContractManageReqPacket.getBODY().getContractObjectCode();
		String validStatus = getContractManageReqPacket.getBODY().getValidStatus();
		int pageNO = getContractManageReqPacket.getHEAD().getPAGENO();
		int pageSize = getContractManageReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getContractManage(systemcode,contractObjectCode,
				validStatus, pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETCONTRACTMANAGE,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDcontractManage",PrpDcontractManage.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetContractManageReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream(new DomDriver());
		xs.alias("GetContractManageReqPacket", GetContractManageReqPacket.class);
		xs.alias("GetContractManageReqBody", GetContractManageReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetContractManageReqPacket ep = (GetContractManageReqPacket) xs.fromXML(requestxml,
				new GetContractManageReqPacket());
		return ep;
	}

}

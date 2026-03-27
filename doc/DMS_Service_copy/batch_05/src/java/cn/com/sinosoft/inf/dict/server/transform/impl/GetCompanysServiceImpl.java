package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getCompany.GetCompanyReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getCompany.GetCompanyReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetCompanysServiceImpl implements DataTransformer<GetCompanyReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		ResponseHeadSchema head = new ResponseHeadSchema();
		PageResPacket pageResPacket = new PageResPacket();
		GetCompanyReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String comCodeOrName = requestPacket.getBODY().getComCodeOrName();
		String upperComCode = requestPacket.getBODY().getUpperComCode();
		String flag = requestPacket.getBODY().getFlag();
		String validStatus = requestPacket.getBODY().getValidStatus();
		int pageNo = requestPacket.getHEAD().getPAGENO();
		int pageSize = requestPacket.getHEAD().getPAGESIZE();
		DictPage dictPage = dictionaryService.getCompanys(systemCode, comCodeOrName, upperComCode, flag, validStatus,
				pageNo, pageSize);
		head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS,
				ServiceInfoConst.GETCOMPANYS, ServiceInfoConst.RESPONSECODE_SUCCESS);
		List<PrpDcompany> list = dictPage.getData();
		List<cn.com.sinosoft.dms.vo.PrpDcompany> voList = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				cn.com.sinosoft.dms.vo.PrpDcompany company = new cn.com.sinosoft.dms.vo.PrpDcompany();
				BeanUtilsEx.copyProperties(company, list.get(i));
				voList.add(company);
			}
		}
		dictPage.setData(voList);
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
		xstream.alias("PrpDcompany", cn.com.sinosoft.dms.vo.PrpDcompany.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetCompanyReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetCompanyReqPacket", GetCompanyReqPacket.class);
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		xs.alias("GetCompanyReqBody", GetCompanyReqBody.class);
		GetCompanyReqPacket ep = (GetCompanyReqPacket) xs.fromXML(requestxml, new GetCompanyReqPacket());
		return ep;
	}
}

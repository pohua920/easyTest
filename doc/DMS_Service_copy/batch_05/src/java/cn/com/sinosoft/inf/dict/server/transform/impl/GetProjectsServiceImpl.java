package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDproject;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getprojects.GetProjectsReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getprojects.GetProjectsReqPacket;

import com.thoughtworks.xstream.XStream;

public class GetProjectsServiceImpl implements DataTransformer<GetProjectsReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetProjectsReqPacket getProjectsReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getProjectsReqPacket.getHEAD().getSYSTEMCODE();
		String projectCode = getProjectsReqPacket.getBODY().getProjectCode();
		String comCode = getProjectsReqPacket.getBODY().getComCode();
		
		int pageNO = getProjectsReqPacket.getHEAD().getPAGENO();
		int pageSize = getProjectsReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getProjects(systemCode, projectCode,comCode,pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETPROJECTS,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDproject", PrpDproject.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetProjectsReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetProjectsReqPacket", GetProjectsReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetProjectsReqBody", GetProjectsReqBody.class);
		GetProjectsReqPacket ep = (GetProjectsReqPacket) xs.fromXML(requestxml, new GetProjectsReqPacket());
		return ep;
	}


}



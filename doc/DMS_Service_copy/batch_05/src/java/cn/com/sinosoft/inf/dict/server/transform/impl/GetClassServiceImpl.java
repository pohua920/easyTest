package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDclass;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getRisk.GetRiskReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getRisk.GetRiskReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetClassServiceImpl implements
		DataTransformer<GetRiskReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetRiskReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		DictPage dictPage = new DictPage();
		
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String classCode = requestPacket.getBODY().getClassCode();
		String request_type = requestPacket.getHEAD().getREQUEST_TYPE();
		if(ServiceInfoConst.PRPDCLASS.equals(request_type)){
			String validStatus = requestPacket.getHEAD().getVALIDSTATUS();
			String reverseType = requestPacket.getBODY().getReverseType();
	        int pageNo = requestPacket.getHEAD().getPAGENO();
	        int pageSize = requestPacket.getHEAD().getPAGESIZE();
			dictPage = dictionaryService.getClass(systemCode,classCode, reverseType,validStatus,pageNo, pageSize);
			ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
					ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.PRPDCLASS,
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			 pageResPacket.setHEAD(head);
		}else{
			int pageNo = requestPacket.getBODY().getDictPage().getPageNo();
			int pageSize = requestPacket.getBODY().getDictPage().getPageSize();
			dictPage = dictionaryService.getClass(systemCode,classCode, pageNo, pageSize);
			ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, 
					ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETCLASS,
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			 pageResPacket.setHEAD(head);
		}
		
		List<PrpDclass> list = dictPage.getData();
		 List<cn.com.sinosoft.dms.vo.PrpDclass> voList = new ArrayList();
		 if(list!=null&&list.size()>0){
			 for (int i = 0;i<list.size();i++){
				 cn.com.sinosoft.dms.vo.PrpDclass prpDclass = new  cn.com.sinosoft.dms.vo.PrpDclass();
				 BeanUtilsEx.copyProperties(prpDclass, list.get(i));
				 voList.add(prpDclass);
			 }
		 }
		 dictPage.setData(voList);
		 pageResPacket.setBODY(dictPage);
		 String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("GetRiskReqPacket",GetRiskReqPacket.class);
		xs.alias("GetRiskReqBody",GetRiskReqBody.class);
		xs.alias("PrpDclass", cn.com.sinosoft.dms.vo.PrpDclass.class);
//		XStream xs = AliasTool.getInstance().alias();
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

	public GetRiskReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetRiskReqPacket",GetRiskReqPacket.class);
		xs.alias("GetRiskReqBody",GetRiskReqBody.class);
		xs.alias("DictPage", DictPage.class);//分页对象
//		XStream xs = AliasTool.getInstance().alias();
		GetRiskReqPacket ep = (GetRiskReqPacket) xs.fromXML(requestxml, new GetRiskReqPacket());
		return ep;
	}
}

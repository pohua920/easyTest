package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDtaxAuthorities;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getTaxAuthorities.GetTaxAuthoritiesReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getTaxAuthorities.GetTaxAuthoritiesReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetTaxAuthoritiesServiceImpl implements
		DataTransformer<GetTaxAuthoritiesReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetTaxAuthoritiesReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String userCode = requestPacket.getBODY().getUserCode();
		String comCode = requestPacket.getBODY().getComCode();
		int pageNo = requestPacket.getBODY().getDictPage().getPageNo();
		int pageSize = requestPacket.getBODY().getDictPage().getPageSize();
		 DictPage dictPage = dictionaryService.getTaxAuthorities(systemCode,userCode,comCode, pageNo, pageSize);
		 ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETTAXAUTHORITIES, ServiceInfoConst.RESPONSECODE_SUCCESS);
		 List<PrpDtaxAuthorities> list = dictPage.getData();
		 List<cn.com.sinosoft.dms.vo.PrpDtaxAuthorities> voList = new ArrayList();
		 if(list!=null&&list.size()>0){
			 for (int i = 0;i<list.size();i++){
				 cn.com.sinosoft.dms.vo.PrpDtaxAuthorities prpDtaxAuthorities = new  cn.com.sinosoft.dms.vo.PrpDtaxAuthorities();
				 BeanUtilsEx.copyProperties(prpDtaxAuthorities, list.get(i));
				 voList.add(prpDtaxAuthorities);
			 }
		 }
		 dictPage.setData(voList);
		 pageResPacket.setHEAD(head);
		 pageResPacket.setBODY(dictPage);
		 String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		/**返回报文公用对象*/
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("PrpDtaxAuthorities",cn.com.sinosoft.dms.vo.PrpDtaxAuthorities.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

	public GetTaxAuthoritiesReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		/**返回报文公用对象*/
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("GetTaxAuthoritiesReqPacket",GetTaxAuthoritiesReqPacket.class);
		xs.alias("GetTaxAuthoritiesReqBody",GetTaxAuthoritiesReqBody.class);

		GetTaxAuthoritiesReqPacket ep = (GetTaxAuthoritiesReqPacket) xs.fromXML(requestxml, new GetTaxAuthoritiesReqPacket());
		return ep;
	}
}

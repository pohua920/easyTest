package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getRiskEngage.GetRiskEngageReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getRiskEngage.GetRiskEngageReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetRiskEngageServiceImpl implements
		DataTransformer<GetRiskEngageReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetRiskEngageReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String riskCode = requestPacket.getBODY().getRiskCode();
		String language = requestPacket.getBODY().getLanguage();
		String clauseCode = requestPacket.getBODY().getClauseCode();
		String engageCode = requestPacket.getBODY().getEngageCode();
		String extraEngageCode = requestPacket.getBODY().getExtraEngageCode();
		String extraCondition = requestPacket.getBODY().getExtraCondition();//add by guyanqing 2012-02-06 reason:增加可扩展查询条件
		String initFlag = requestPacket.getBODY().getInitFlag();
		int pageNo = requestPacket.getBODY().getDictPage().getPageNo();
		int pageSize = requestPacket.getBODY().getDictPage().getPageSize();
		 DictPage dictPage = dictionaryService.getRiskEngage(systemCode,riskCode,language,clauseCode,engageCode,extraEngageCode, pageNo, pageSize,extraCondition,initFlag);//add by guyanqing 2012-02-06 reason:���ӿ���չ��ѯ����
		 ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETRISKENGAGE, ServiceInfoConst.RESPONSECODE_SUCCESS);
		 List<PrpDriskEngage> list = dictPage.getData();
		 List<cn.com.sinosoft.dms.vo.PrpDriskEngage> voList = new ArrayList();
		 if(list!=null&&list.size()>0){
			 for (int i = 0;i<list.size();i++){
				 cn.com.sinosoft.dms.vo.PrpDriskEngage prpDriskEngage = new  cn.com.sinosoft.dms.vo.PrpDriskEngage();
				 BeanUtilsEx.copyProperties(prpDriskEngage, list.get(i));
				 voList.add(prpDriskEngage);
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
		xs.alias("PageResPacket", PageResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("DictPage", DictPage.class);//分页对象
		/**请求报文公用对象*/
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		/***********************************************/
		xs.alias("GetRiskEngageReqPacket",GetRiskEngageReqPacket.class);
		xs.alias("GetRiskEngageReqBody",GetRiskEngageReqBody.class);
		xs.alias("PrpDriskEngage", cn.com.sinosoft.dms.vo.PrpDriskEngage.class);
		xs.alias("PrpDriskEngageId", PrpDriskEngageId.class);
//		XStream xs = AliasTool.getInstance().alias();
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

	public GetRiskEngageReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetRiskEngageReqPacket",GetRiskEngageReqPacket.class);
		xs.alias("GetRiskEngageReqBody",GetRiskEngageReqBody.class);
		xs.alias("DictPage", DictPage.class);//分页对象
//		XStream xs = AliasTool.getInstance().alias();
		GetRiskEngageReqPacket ep = (GetRiskEngageReqPacket) xs.fromXML(requestxml, new GetRiskEngageReqPacket());
		return ep;
	}
}

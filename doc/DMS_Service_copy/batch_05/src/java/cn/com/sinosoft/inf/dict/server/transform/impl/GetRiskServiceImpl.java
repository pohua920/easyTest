package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDrisk;
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

public class GetRiskServiceImpl implements
		DataTransformer<GetRiskReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();	//声明返回报文对象
		GetRiskReqPacket requestPacket = xmlToSchema(requestxml);	//请求报文转换为请求报文对象
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");// 获得Spring管理的bean
		//从请求报文中获得参数
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String riskCode = requestPacket.getBODY().getRiskCode();
		String classCode = requestPacket.getBODY().getClassCode();
		String reverseType = requestPacket.getBODY().getReverseType();
		int pageNo = requestPacket.getBODY().getDictPage().getPageNo();
		int pageSize = requestPacket.getBODY().getDictPage().getPageSize();
		//调用接口实现方法 
		DictPage dictPage = dictionaryService.getRisk(systemCode, classCode, riskCode, reverseType, pageNo, pageSize);
		//生成返回报文头对象
		ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
				"成功", ServiceInfoConst.GETRISK, ServiceInfoConst.RESPONSECODE_SUCCESS);
		List<PrpDrisk> list = dictPage.getData();
		//把prpDrisk对象准换为prpDriskVo
		List<cn.com.sinosoft.dms.vo.PrpDrisk> voList = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				cn.com.sinosoft.dms.vo.PrpDrisk prpDrisk = new cn.com.sinosoft.dms.vo.PrpDrisk();
				BeanUtilsEx.copyProperties(prpDrisk, list.get(i));
				voList.add(prpDrisk);
			}
		}
		dictPage.setData(voList);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);	//返回报文转换为返回报文对象
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
		xs.alias("PrpDrisk", cn.com.sinosoft.dms.vo.PrpDrisk.class);
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

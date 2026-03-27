package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDplan;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanWhetherHasFixed.GetPlanWhetherHasFixedReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanWhetherHasFixed.GetPlanWhetherHasFixedReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanWhetherHasFixed.GetPlanWhetherHasFixedResPacket;

import com.thoughtworks.xstream.XStream;

public class GetPlanWhetherHasFixedServiceImpl implements
DataTransformer<GetPlanWhetherHasFixedReqPacket, GetPlanWhetherHasFixedResPacket>{

	public String execute(String requestxml) throws Exception {
		
		GetPlanWhetherHasFixedReqPacket getPlanWhetherHasFixedReqPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean

		String systemCode = getPlanWhetherHasFixedReqPacket.getHEAD().getSYSTEMCODE();
		String riskCode = getPlanWhetherHasFixedReqPacket.getBODY().getRiskCode();

		String result = dictionaryService.getPlanWhetherHasFixed(systemCode,riskCode);
		GetPlanWhetherHasFixedResPacket responsePacket = new GetPlanWhetherHasFixedResPacket();
		String requestType = "";
		requestType = ServiceInfoConst.GetPlanWhetherHasFixed;
		if("".equals(result)||null == result){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
		}else{
			responsePacket.getBODY().setResult(result);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
		}
		
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPlanWhetherHasFixedResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDplan",PrpDplan.class);
		xstream.alias("GetPlanWhetherHasFixedResPacket", GetPlanWhetherHasFixedResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);;
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPlanWhetherHasFixedReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream();
		xs.alias("GetPlanWhetherHasFixedReqPacket", GetPlanWhetherHasFixedReqPacket.class);
		xs.alias("GetPlanWhetherHasFixedReqBody", GetPlanWhetherHasFixedReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetPlanWhetherHasFixedReqPacket ep = (GetPlanWhetherHasFixedReqPacket) xs.fromXML(requestxml,
				new GetPlanWhetherHasFixedReqPacket());
		return ep;
	}
}

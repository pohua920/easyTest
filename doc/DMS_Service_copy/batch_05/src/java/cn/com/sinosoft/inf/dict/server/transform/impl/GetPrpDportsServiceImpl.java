package cn.com.sinosoft.inf.dict.server.transform.impl;

import java.util.ArrayList;
import java.util.List;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport.GetPrpDportReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDport.GetPrpDportReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetPrpDportsServiceImpl implements DataTransformer<GetPrpDportReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetPrpDportReqPacket getPrpDportReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = getPrpDportReqPacket.getHEAD().getSYSTEMCODE();
		String portCode = getPrpDportReqPacket.getBODY().getPORTCODE();
		
		int pageNO = getPrpDportReqPacket.getHEAD().getPAGENO();
		int pageSize = getPrpDportReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getPrpDports(systemCode, portCode, pageNO, pageSize);
		List<PrpDport> prpDports = (List<PrpDport>) dictPage.getData();
		List<cn.com.sinosoft.dms.vo.PrpDport> prpDportVos = new ArrayList<cn.com.sinosoft.dms.vo.PrpDport>();
		for(PrpDport prpDport : prpDports){
			cn.com.sinosoft.dms.vo.PrpDport prpDportVo = new cn.com.sinosoft.dms.vo.PrpDport();
			prpDportVo.setCountryCName(prpDport.getCountryCName());
			prpDportVo.setCountryCode(prpDport.getCountryCode());
			prpDportVo.setCountryEName(prpDport.getCountryEName());
			prpDportVo.setFlag(prpDport.getFlag());
			prpDportVo.setNewPortCode(prpDport.getNewPortCode());
			prpDportVo.setPortCName(prpDport.getPortCName());
			prpDportVo.setPortCode(prpDport.getPortCode());
			prpDportVo.setPortEName(prpDport.getPortEName());
			prpDportVo.setValidStatus(prpDport.getValidStatus());
			prpDportVos.add(prpDportVo);
		}
		dictPage.setData(prpDportVos);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETPRPDRISKITEM,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDport", cn.com.sinosoft.dms.vo.PrpDport.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDportReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetPrpDportReqPacket", GetPrpDportReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetPrpDportReqBody", GetPrpDportReqBody.class);
		GetPrpDportReqPacket ep = (GetPrpDportReqPacket) xs.fromXML(requestxml, new GetPrpDportReqPacket());
		return ep;
	}


}



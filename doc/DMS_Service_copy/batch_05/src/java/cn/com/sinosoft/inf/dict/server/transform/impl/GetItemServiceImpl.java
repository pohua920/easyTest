package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskItemId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getItem.GetItemReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getItem.GetItemReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;

import com.thoughtworks.xstream.XStream;

public class GetItemServiceImpl implements DataTransformer<GetItemReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = null;
		GetItemReqPacket getItemReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		// 获得Spring管理的bean
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");
		String systemCode = getItemReqPacket.getHEAD().getSYSTEMCODE();
		String riskCode = getItemReqPacket.getBODY().getRISKCODE();
		
		int pageNO = getItemReqPacket.getHEAD().getPAGENO();
		int pageSize = getItemReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getItem(systemCode,riskCode,pageNO, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETITEM,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;

	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDriskItem", PrpDriskItem.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetItemReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetItemReqPacket", GetItemReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetItemReqBody", GetItemReqBody.class);
		GetItemReqPacket ep = (GetItemReqPacket) xs.fromXML(requestxml, new GetItemReqPacket());
		return ep;
	}


}
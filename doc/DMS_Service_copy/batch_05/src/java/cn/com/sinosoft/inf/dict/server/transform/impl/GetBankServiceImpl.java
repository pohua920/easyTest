package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.vo.PrpDbank;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank.GetPrpDbankReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank.GetPrpDbankReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetBankServiceImpl implements
		DataTransformer<GetPrpDbankReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetPrpDbankReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		DictPage dictPage = new DictPage();
		
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String bankCode = requestPacket.getBODY().getBANKCODE();
		String bankName = requestPacket.getBODY().getBANKNAME();
		int pageNo = requestPacket.getHEAD().getPAGENO();
		int pageSize = requestPacket.getHEAD().getPAGESIZE();
		dictPage = dictionaryService.getBank(systemCode,bankCode,bankName, pageNo, pageSize);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETBANK, ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);

		List list = dictPage.getData();
		List voList = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PrpDbank bankVo = new PrpDbank();
				BeanUtilsEx.copyProperties(bankVo, list.get(i));
				voList.add(bankVo);
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
		xs.alias("PrpDbank", PrpDbank.class);
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDbankReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetPrpDbankReqPacket",GetPrpDbankReqPacket.class);
		xs.alias("RequestHeadSchema",RequestHeadSchema.class);
		xs.alias("GetPrpDbankReqBody",GetPrpDbankReqBody.class);
		GetPrpDbankReqPacket ep = (GetPrpDbankReqPacket) xs.fromXML(requestxml, new GetPrpDbankReqPacket());
		return ep;
	}
}

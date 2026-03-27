package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcurrency;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcurrencyAndExchRate.GetPrpDcurrencyAndExchRateReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcurrencyAndExchRate.GetPrpDcurrencyAndExchRateReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcurrencyAndExchRate.PrpDcurrencyAndExchRateVo;

import com.thoughtworks.xstream.XStream;

public class GetPrpDcurrencyAndExchRateServiceImpl implements
		DataTransformer<GetPrpDcurrencyAndExchRateReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {



		DictPage dictPage = new DictPage();
		GetPrpDcurrencyAndExchRateReqPacket getPrpDcurrencyAndExchRateReqPacket = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean

		String systemcode = getPrpDcurrencyAndExchRateReqPacket.getHEAD().getSYSTEMCODE();
		String currencyCode = getPrpDcurrencyAndExchRateReqPacket.getBODY().getCurrencyCode();
		String currencyName = getPrpDcurrencyAndExchRateReqPacket.getBODY().getCurrencyName();
		String validStatus = getPrpDcurrencyAndExchRateReqPacket.getBODY().getValidStatus();
		int pageNO = getPrpDcurrencyAndExchRateReqPacket.getHEAD().getPAGENO();
		int pageSize = getPrpDcurrencyAndExchRateReqPacket.getHEAD().getPAGESIZE();

		dictPage = dictionaryService.getPrpDcurrencyAndExchRate(systemcode, currencyCode,
				currencyName, validStatus, pageNO, pageSize);
		List list = dictPage.getData();
		List<PrpDcurrencyAndExchRateVo> pcaeList = new ArrayList();
		Object [] cuAndEx = null;
		for(int i=0;i<list.size();i++){	
			PrpDcurrencyAndExchRateVo pcae = new PrpDcurrencyAndExchRateVo();
				cuAndEx = (Object [])list.get(i);
				PrpDcurrency pcaeCuAndEx = (PrpDcurrency)cuAndEx[0];
				pcae.setCurrencyCode(pcaeCuAndEx.getCurrencyCode());
				pcae.setCurrencyCName(pcaeCuAndEx.getCurrencyCName());
				pcae.setCurrencyEName(pcaeCuAndEx.getCurrencyEName());
				pcae.setAccBookCode(pcaeCuAndEx.getAccBookCode());
				pcae.setNewCurrencyCode(pcaeCuAndEx.getNewCurrencyCode());
				pcae.setValidStatus(pcaeCuAndEx.getValidStatus());
				pcae.setFlag(pcaeCuAndEx.getFlag());
				pcae.setExchrate((BigDecimal)cuAndEx[1]);
				
				pcaeList.add(pcae);
				
		}	
		dictPage.setData(pcaeList);
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.PrpDcurrencyAndExchRate,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDcurrencyAndExchRateVo",PrpDcurrencyAndExchRateVo.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDcurrencyAndExchRateReqPacket xmlToSchema(String requestxml)
			throws Exception {
		XStream xs = new XStream();
		xs.alias("GetPrpDcurrencyAndExchRateReqPacket", GetPrpDcurrencyAndExchRateReqPacket.class);
		xs.alias("GetPrpDcurrencyAndExchRateReqBody", GetPrpDcurrencyAndExchRateReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetPrpDcurrencyAndExchRateReqPacket ep = (GetPrpDcurrencyAndExchRateReqPacket) xs.fromXML(requestxml,
				new GetPrpDcurrencyAndExchRateReqPacket());
		return ep;
	}

}

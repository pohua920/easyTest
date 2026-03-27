package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.GetCodeReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.PrpDcodeInfo;
import cn.com.sinosoft.inf.dict.xmlmsg.getCodeWithRiskOrCom.GetCodeWithReqPacket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetCodeWithServiceImpl implements
		DataTransformer<GetCodeWithReqPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		PageResPacket pageResPacket = new PageResPacket();
		GetCodeWithReqPacket requestPacket = xmlToSchema(requestxml);
		DictPage dictPage = new DictPage();
		String requestType = requestPacket.getHEAD().getREQUEST_TYPE();
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String codeFlag = requestPacket.getBODY().getCodeFlag();
		String codeType = requestPacket.getBODY().getCodeType();
		String codeCode = requestPacket.getBODY().getCodeCode();
		String codeCName = requestPacket.getBODY().getCodeCName();
		String withCode = requestPacket.getBODY().getWithCode();
		String ignoreCode = requestPacket.getBODY().getIgnoreCode();
		String extraCodeCode = requestPacket.getBODY().getExtraCodeCode();
		String upperCode = requestPacket.getBODY().getUpperCode();
		int pageNo = requestPacket.getBODY().getDictPage().getPageNo();
		int pageSize = requestPacket.getBODY().getDictPage().getPageSize();
		ResponseHeadSchema head = null;
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		if(ServiceInfoConst.GETCODEWITHRISK.equals(requestType) ||
		   ServiceInfoConst.GETSUBCODEWITHRISK.equals(requestType)){
			if(!"2".equals(codeFlag)){
				dictPage = dictionaryService.getCodeWithRisk(systemCode,codeType,codeCode,codeCName,withCode,ignoreCode,extraCodeCode,upperCode,pageNo, pageSize);
			} else {
				dictPage = dictionaryService.getOldCodeWithRisk(systemCode, codeType, codeCode, codeCName, withCode, pageNo, pageSize);
			}
			  head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETCODEWITHRISK, ServiceInfoConst.RESPONSECODE_SUCCESS);
		}else if(ServiceInfoConst.GETCODEWITHCOM.equals(requestType)){
			  dictPage = dictionaryService.getCodeWithCom(systemCode,codeType,codeCode,codeCName,withCode, pageNo, pageSize);
			  head = MessageUtil.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETCODEWITHCOM, ServiceInfoConst.RESPONSECODE_SUCCESS);
		}
		 pageResPacket.setHEAD(head);
		 pageResPacket.setBODY(dictPage);
		 String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}
	/**请求报文转换对象*/
	public GetCodeWithReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("服务器异常");
		}
		XStream xs = new XStream();
		xs.alias("GetCodeWithReqPacket",GetCodeWithReqPacket.class);
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		xs.alias("GetCodeReqBody",GetCodeReqBody.class);
		xs.alias("DictPage", DictPage.class);
		GetCodeWithReqPacket ep = (GetCodeWithReqPacket) xs.fromXML(requestxml, new GetCodeWithReqPacket());
		return ep;
	}
}
 
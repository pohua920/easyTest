package cn.com.sinosoft.inf.dict.server.transform.impl;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype.GetPrpDtypeReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype.GetPrpDtypeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDtype.PrpDtypeVO;

public class GetPrpDtypeServiceImpl implements DataTransformer<GetPrpDtypeReqPacket, PageResPacket>{

	public String execute(String requestxml) throws Exception {
		
		DictPage dictPage = new DictPage();
		GetPrpDtypeReqPacket packet = xmlToSchema(requestxml);
		PageResPacket pageResPacket = new PageResPacket();
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		
		int pageNO = packet.getHEAD().getPAGENO();
		int pageSize = packet.getHEAD().getPAGESIZE();
		String request_type = packet.getHEAD().getREQUEST_TYPE();
		ResponseHeadSchema head = null;
		if(ServiceInfoConst.PRPDTYPE.equals(request_type)){
			String systemCode = packet.getHEAD().getSYSTEMCODE();
    		String codeType = packet.getBODY().getCODETYPE();
            String codeTypeName = packet.getBODY().getCODETYPENAME();
    		String validStatus = packet.getHEAD().getVALIDSTATUS();
    		
    		dictPage = dictionaryService.getPrpDtype(systemCode,codeType,codeTypeName,
    				validStatus,pageNO,pageSize);
    		List<PrpDtype> list = dictPage.getData();
    		List<PrpDtypeVO> voList = new ArrayList();
    		if(list!=null && list.size()>0){
    			 for (int i = 0;i<list.size();i++){
    				 PrpDtypeVO prpDtypeVO = new PrpDtypeVO();
    				 BeanUtilsEx.copyProperties(prpDtypeVO, list.get(i));
    				 voList.add(prpDtypeVO);
    			 }
    		 }
    		 dictPage.setData(voList);
    		 head = MessageUtil.setHeadMessage(
    				 ServiceInfoConst.ERRORCODE_SUCCESS,
    				 ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.PRPDTYPE,
    				 ServiceInfoConst.RESPONSECODE_SUCCESS);
		}else{
			String systemCode = packet.getHEAD().getSYSTEMCODE();
    		String codeType = packet.getBODY().getCODETYPE();
        	PrpDtype prpDtype  = dictionaryService.getPrpDtype(systemCode,codeType);
    		List<PrpDtype> list = new ArrayList();
    		list.add(prpDtype);
    		List<PrpDtypeVO> voList = new ArrayList();
    		if(list!=null && list.size()>0){
    			 for (int i = 0;i<list.size();i++){
    				 PrpDtypeVO prpDtypeVO = new PrpDtypeVO();
    				 BeanUtilsEx.copyProperties(prpDtypeVO, list.get(i));
    				 voList.add(prpDtypeVO);
    			 }
    		}
    		head = MessageUtil.setHeadMessage(
    				ServiceInfoConst.ERRORCODE_SUCCESS,
    				ServiceInfoConst.ERRORMSG_SUCCESS, ServiceInfoConst.GETPRPDTYPE,
    				ServiceInfoConst.RESPONSECODE_SUCCESS);
    		dictPage.setData(voList);
		}
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

	public String schemaToXml(PageResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("PrpDtype", PrpDtypeVO.class);
		xstream.alias("PageResPacket", PageResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xstream.alias("DictPage", DictPage.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public GetPrpDtypeReqPacket xmlToSchema(String requestxml) throws Exception {
		XStream xs = new XStream();
		xs.alias("GetPrpDtypeReqPacket", GetPrpDtypeReqPacket.class);
		xs.alias("GetPrpDtypeReqBody", GetPrpDtypeReqBody.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		GetPrpDtypeReqPacket ep = (GetPrpDtypeReqPacket) xs.fromXML(requestxml, new GetPrpDtypeReqPacket());
		return ep;
	}



}

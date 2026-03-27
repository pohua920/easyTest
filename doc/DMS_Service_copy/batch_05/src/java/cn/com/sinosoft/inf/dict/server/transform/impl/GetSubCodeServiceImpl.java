package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getSubCode.GetSubCodeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getSubCode.GetSubCodeResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getSubCode.SubCodeResInfo;

public class GetSubCodeServiceImpl implements 
	DataTransformer<GetSubCodeReqPacket, GetSubCodeResPacket>{

	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		GetSubCodeReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String codeType = requestPacket.getBODY().getCODETYPE();
		String codeCode = requestPacket.getBODY().getCODECODE();
		List<PrpDnewCode> list = null;
		GetSubCodeResPacket responsePacket = new GetSubCodeResPacket();
		try {
			list = dictionaryService.getSubCode(systemCode, codeType, codeCode);
		} catch (Exception e) {
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETSUBCODE);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_FAIL);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NOSUBCODE);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NOSUBCODE);
		}
		if(list == null||list.size()==0){
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETSUBCODE);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
		}else{
			/**设置返回报文头*/
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETSUBCODE);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			/**设置返回报文体*/
			List<SubCodeResInfo> subCodeList = new ArrayList<SubCodeResInfo>();
			for(int i = 0;i<list.size();i++){
				PrpDnewCode prpDcode = list.get(i);
				SubCodeResInfo subCodeResInfo = new SubCodeResInfo();
				subCodeResInfo.setCODETYPE(prpDcode.getId().getCodeType());
				subCodeResInfo.setCODECODE(prpDcode.getId().getCodeCode());
				subCodeResInfo.setCODECNAME(prpDcode.getCodeCName());
				subCodeResInfo.setCODEENAME(prpDcode.getCodeEName());
				subCodeResInfo.setNEWCODECODE(prpDcode.getNewCodeCode());
				subCodeList.add(subCodeResInfo);
			}
			int i = subCodeList.size();
			responsePacket.getBODY().getSUBCODELIST().setSUBCODE(subCodeList.toArray(new SubCodeResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetSubCodeResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetSubCodeReqPacket xmlToSchema(String requestxml) throws Exception {
		GetSubCodeReqPacket response = (GetSubCodeReqPacket) PubFun
		.generateJox(requestxml).readObject(GetSubCodeReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetSubCodeReqPacket response = (GetSubCodeReqPacket) joxIn
//				.readObject(GetSubCodeReqPacket.class);
		return response;
	}

}

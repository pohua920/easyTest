package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank.GetPrpDbankReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDbank.GetPrpDbankResPacket;

public class GetPrpDbankServiceImpl implements 
	DataTransformer<GetPrpDbankReqPacket, GetPrpDbankResPacket>{

	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		GetPrpDbankReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String bankCode = requestPacket.getBODY().getBANKCODE();
		PrpDbank prpDbank = dictionaryService.getPrpDbank(systemCode, bankCode);
		GetPrpDbankResPacket responsePacket = new GetPrpDbankResPacket();
		if(prpDbank==null){
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETPRPDBANK);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
		}else{
			/**设置返回报文头*/
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETPRPDBANK);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			/**设置返回报文体*/
			responsePacket.getBODY().getPRPDBANKINFO().setBANKCODE(prpDbank.getBankCode());
			responsePacket.getBODY().getPRPDBANKINFO().setBANKNAME(prpDbank.getBankName());
			responsePacket.getBODY().getPRPDBANKINFO().setCUSTOMERCODE(prpDbank.getCustomerCode());
			responsePacket.getBODY().getPRPDBANKINFO().setADDRESSNAME(prpDbank.getAddressName());
			responsePacket.getBODY().getPRPDBANKINFO().setPOSTCODE(prpDbank.getPostCode());
			responsePacket.getBODY().getPRPDBANKINFO().setBANKTYPE(prpDbank.getBankType());
			responsePacket.getBODY().getPRPDBANKINFO().setLINKERNAME(prpDbank.getLinkerName());
			responsePacket.getBODY().getPRPDBANKINFO().setPHONENUMBER(prpDbank.getPhoneNumber());
			responsePacket.getBODY().getPRPDBANKINFO().setFAXNUMBER(prpDbank.getFaxNumber());
			if(prpDbank.getArrearageRate()!=null){
				responsePacket.getBODY().getPRPDBANKINFO().setARREARAGERATE(prpDbank.getArrearageRate().toString());
			}
			if(prpDbank.getArrearageCoff()!=null){
				responsePacket.getBODY().getPRPDBANKINFO().setARREARAGECOFF(prpDbank.getArrearageCoff().toString());
			}

			responsePacket.getBODY().getPRPDBANKINFO().setCOMCODE(prpDbank.getComCode());
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDbankResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDbankReqPacket xmlToSchema(String requestxml) throws Exception {
		GetPrpDbankReqPacket response = (GetPrpDbankReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDbankReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDbankReqPacket response = (GetPrpDbankReqPacket) joxIn
//				.readObject(GetPrpDbankReqPacket.class);
		return response;
	}

}

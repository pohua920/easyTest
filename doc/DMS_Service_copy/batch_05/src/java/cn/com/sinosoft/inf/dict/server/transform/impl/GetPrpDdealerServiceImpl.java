package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;

import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer.GetPrpDdealerReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdealer.GetPrpDdealerResPacket;

import com.wutka.jox.JOXBeanInputStream;

public class GetPrpDdealerServiceImpl implements
		DataTransformer<GetPrpDdealerReqPacket, GetPrpDdealerResPacket> {

	public String execute(String requestxml) throws Exception {
		PrpDdealer prpDdealer = new PrpDdealer();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetPrpDdealerReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		prpDdealer = dictionaryService.getPrpDdealer(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getDEALERCODE());
		requestType = ServiceInfoConst.GETPRPDDEALER;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		GetPrpDdealerResPacket responsePacket = new GetPrpDdealerResPacket();
		if(prpDdealer==null){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
		}else{
			/**设置返回报文头*/
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			/**设置返回报文体*/
			responsePacket.getBODY().getPRPDDEALER().setADDRESSNAME(prpDdealer.getAddressName());
			if(prpDdealer.getArrearageCoff()!=null){
				responsePacket.getBODY().getPRPDDEALER().setARREARAGECOFF(prpDdealer.getArrearageCoff().toString());
			}
			
			if(prpDdealer.getArrearageRate()!=null){
				responsePacket.getBODY().getPRPDDEALER().setARREARAGERATE(prpDdealer.getArrearageRate().toString());
			}
			
			if(prpDdealer.getCapital()!=null){
				responsePacket.getBODY().getPRPDDEALER().setCAPITAL(prpDdealer.getCapital().toString());
			}
			
			responsePacket.getBODY().getPRPDDEALER().setCARTYPE(prpDdealer.getCarType());
			responsePacket.getBODY().getPRPDDEALER().setCOMCODE(prpDdealer.getComCode());
			responsePacket.getBODY().getPRPDDEALER().setCUSTOMERCODE(prpDdealer.getCustomerCode());
			responsePacket.getBODY().getPRPDDEALER().setDEALERCODE(prpDdealer.getDealerCode());
			responsePacket.getBODY().getPRPDDEALER().setDEALERGRADE(prpDdealer.getDealerGrade());
			responsePacket.getBODY().getPRPDDEALER().setDEALERNAME(prpDdealer.getDealerName());
			responsePacket.getBODY().getPRPDDEALER().setDEALERTYPE(prpDdealer.getDealerType());
			responsePacket.getBODY().getPRPDDEALER().setFAXNUMBER(prpDdealer.getFaxNumber());
			responsePacket.getBODY().getPRPDDEALER().setLINKERNAME(prpDdealer.getLinkerName());
			responsePacket.getBODY().getPRPDDEALER().setPHONENUMBER(prpDdealer.getPhoneNumber());
			responsePacket.getBODY().getPRPDDEALER().setPOSTCODE(prpDdealer.getPostCode());
			
		}
		
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDdealerResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDdealerReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetPrpDdealerReqPacket response = (GetPrpDdealerReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDdealerReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDdealerReqPacket response = (GetPrpDdealerReqPacket) joxIn
//				.readObject(GetPrpDdealerReqPacket.class);
		return response;
	}

}

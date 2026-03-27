package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;

import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDship.GetPrpDshipReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDship.GetPrpDshipResPacket;

import com.sinosoft.sysframework.exception.BusinessException; 
import com.wutka.jox.JOXBeanInputStream;

public class GetPrpDshipServiceImpl implements 
DataTransformer<GetPrpDshipReqPacket, GetPrpDshipResPacket>{

	private static final String MAKEFACTORY = null;

	public String execute(String requestxml) throws Exception {
		PrpDship prpDship = new PrpDship();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetPrpDshipReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		prpDship = dictionaryService.getPrpDship(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getSHIPCODE());
//		requestType = ServiceInfoConst.GETPRPDSHIP;// 设置返回报文的requesttype
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		GetPrpDshipResPacket responsePacket = new GetPrpDshipResPacket();
		if(prpDship==null){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
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
			responsePacket.getBODY().getPRPDSHIPINFO().setCONVEYMANAGER(prpDship.getConveyManager());
			responsePacket.getBODY().getPRPDSHIPINFO().setCOUNTRYCODE(prpDship.getCountryCode());
			responsePacket.getBODY().getPRPDSHIPINFO().setCURRENCY(prpDship.getCurrency());
			if(prpDship.getHorsePower()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setHORSEPOWER(prpDship.getHorsePower().toString());
			}
			if(prpDship.getLoadTon()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setLOADTON(prpDship.getLoadTon().toString());
			}
			if(prpDship.getMakeEndDate()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setMAKEENDDATE(PubFun.DateToStr(prpDship.getMakeEndDate()));
			}
			responsePacket.getBODY().getPRPDSHIPINFO().setMAKEFACTORY(prpDship.getMakeFactory());
			if(prpDship.getMakeStartDate()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setMAKESTARTDATE(PubFun.DateToStr(prpDship.getMakeStartDate()));
			}
			if(prpDship.getMakeYearMonth()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setMAKEYEARMONTH(PubFun.DateToStr(prpDship.getMakeYearMonth()));
			}
			responsePacket.getBODY().getPRPDSHIPINFO().setMORTGAGENAME(prpDship.getMortgageName());
			if(prpDship.getNetTonCount()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setNETTONCOUNT(prpDship.getNetTonCount().toString());
			}
			responsePacket.getBODY().getPRPDSHIPINFO().setNEWSHIPCODE(prpDship.getNewShipCode());
			responsePacket.getBODY().getPRPDSHIPINFO().setOLDSHIPNAME(prpDship.getOldShipName());
			responsePacket.getBODY().getPRPDSHIPINFO().setOLDSHIPOWNER(prpDship.getOldShipOwner());
			responsePacket.getBODY().getPRPDSHIPINFO().setOLDSTEPHULL(prpDship.getOldStepHull());
			responsePacket.getBODY().getPRPDSHIPINFO().setPOWERUNIT(prpDship.getPowerUnit());
			responsePacket.getBODY().getPRPDSHIPINFO().setREGISTRYSITE(prpDship.getRegistrySite());
			responsePacket.getBODY().getPRPDSHIPINFO().setREMARK(prpDship.getRemark());
			responsePacket.getBODY().getPRPDSHIPINFO().setSAILMODECODE(prpDship.getSailModeCode());
			if(prpDship.getSeatCount()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setSEATCOUNT(prpDship.getSeatCount().toString());
			}
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPCNAME(prpDship.getShipCName());
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPCODE(prpDship.getShipCode());
			if(prpDship.getShipDepth()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setSHIPDEPTH(prpDship.getShipDepth().toString());
			}
			
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPENAME(prpDship.getShipEName());
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPFLAG(prpDship.getShipFlag());
			if(prpDship.getShipLength()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setSHIPLENGTH(prpDship.getShipLength().toString());
			}
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPOWNER(prpDship.getShipOwner());
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPPORT(prpDship.getShipPort());
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPSTRUCT(prpDship.getShipStruct());
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPTYPECODE(prpDship.getShipTypeCode());
			responsePacket.getBODY().getPRPDSHIPINFO().setSHIPUSAGE(prpDship.getShipUsage());
			if(prpDship.getShipValue()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setSHIPVALUE(prpDship.getShipValue().toString());
			}
			if(prpDship.getShipWidth()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setSHIPWIDTH(prpDship.getShipWidth().toString());
			}
			
			responsePacket.getBODY().getPRPDSHIPINFO().setSHORTHANDCODE(prpDship.getShortHandCode());
			responsePacket.getBODY().getPRPDSHIPINFO().setSTEPHULL(prpDship.getStepHull());
			if(prpDship.getTonCount()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setTONCOUNT(prpDship.getTonCount().toString());
			}
			responsePacket.getBODY().getPRPDSHIPINFO().setUSENATURECODE(prpDship.getUseNatureCode());
			responsePacket.getBODY().getPRPDSHIPINFO().setOPERATORCODE(prpDship.getOperatorCode());
			if(prpDship.getOperateDTime()!=null){
				responsePacket.getBODY().getPRPDSHIPINFO().setOPERATEDTIME(PubFun.DateToStr(prpDship.getOperateDTime()));
			}
			
		}
		
		
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDshipResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDshipReqPacket xmlToSchema(String requestxml) throws Exception {
		GetPrpDshipReqPacket response = (GetPrpDshipReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDshipReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDshipReqPacket response = (GetPrpDshipReqPacket) joxIn
//				.readObject(GetPrpDshipReqPacket.class);
		return response;
	}

}

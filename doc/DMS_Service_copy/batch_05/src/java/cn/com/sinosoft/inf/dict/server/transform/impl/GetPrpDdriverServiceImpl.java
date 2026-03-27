package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;

import cn.com.sinosoft.dms.model.PrpDdriver;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver.GetPrpDdriverReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdriver.GetPrpDdriverResPacket;

import com.sinosoft.sysframework.exception.BusinessException;
import com.wutka.jox.JOXBeanInputStream;

public class GetPrpDdriverServiceImpl implements 
	DataTransformer<GetPrpDdriverReqPacket, GetPrpDdriverResPacket>{

	public String execute(String requestxml) throws Exception {
		PrpDdriver prpDdriver = new PrpDdriver();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetPrpDdriverReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		prpDdriver = dictionaryService.getPrpDdriver(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getDRIVERLICENSENO());
//		requestType = ServiceInfoConst.GETPRPDDRIVER;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		GetPrpDdriverResPacket responsePacket = new GetPrpDdriverResPacket();
		if(prpDdriver==null){
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
			responsePacket.getBODY().getPRPDDRIVER().setAWARDLICENSEORGAN(prpDdriver.getAwardLicenseOrgan());
			if(prpDdriver.getBirthday()!=null){
				responsePacket.getBODY().getPRPDDRIVER().setBIRTHDAY(PubFun.DateToStr(prpDdriver.getBirthday()));
			}
			
			responsePacket.getBODY().getPRPDDRIVER().setDRIVERADDRESS(prpDdriver.getDriverAddress());
			responsePacket.getBODY().getPRPDDRIVER().setDRIVERNAME(prpDdriver.getDriverName());
			responsePacket.getBODY().getPRPDDRIVER().setDRIVERSEX(prpDdriver.getDriverSex());
			responsePacket.getBODY().getPRPDDRIVER().setDRIVINGCARTYPE(prpDdriver.getDrivingCarType());
			responsePacket.getBODY().getPRPDDRIVER().setDRIVINGLICENSENO(prpDdriver.getDrivingLicenseNo());
			responsePacket.getBODY().getPRPDDRIVER().setIDENTIFYNUMBER(prpDdriver.getIdentifyNumber());
			if(prpDdriver.getReceiveLicenseDate() != null){
				responsePacket.getBODY().getPRPDDRIVER().setRECEIVELICENSEDATE(PubFun.DateToStr(prpDdriver.getReceiveLicenseDate()));
			}
			
		}
		
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDdriverResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDdriverReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetPrpDdriverReqPacket response = (GetPrpDdriverReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDdriverReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDdriverReqPacket response = (GetPrpDdriverReqPacket) joxIn
//				.readObject(GetPrpDdriverReqPacket.class);
		return response;
	}

}

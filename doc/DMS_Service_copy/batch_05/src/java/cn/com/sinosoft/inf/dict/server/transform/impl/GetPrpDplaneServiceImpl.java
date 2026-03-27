package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;

import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane.GetPrpDplaneReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDplane.GetPrpDplaneResPacket;

import com.wutka.jox.JOXBeanInputStream;

public class GetPrpDplaneServiceImpl implements 
DataTransformer<GetPrpDplaneReqPacket, GetPrpDplaneResPacket>{

	public String execute(String requestxml) throws Exception {
		PrpDplane prpDplane = new PrpDplane();
		String requestType = "";
		/** �����xml���ķ����Packet���� */
		GetPrpDplaneReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * ���ó־ò����������������
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// ���Spring�����bean
		prpDplane = dictionaryService.getPrpDplane(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getLICENSENO());
//		requestType = ServiceInfoConst.GETPRPDPLANE;// ���÷��ر��ĵ�requesttype

		/*************************************
		 * �־ò����ת����װ����ݰ����
		 * *******************************/
		GetPrpDplaneResPacket responsePacket = new GetPrpDplaneResPacket();
		
		if(prpDplane==null){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// �����ǵ�ǰ���룬
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
		}else{
			/**���÷��ر���ͷ*/
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// �����ǵ�ǰ���룬
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			/**���÷��ر�����*/
//			responsePacket.getBODY().getPRPDPLANERESINFO().setAIRLINECNAME(prpDplane.getAirlineCname());
//			responsePacket.getBODY().getPRPDPLANERESINFO().setAIRLINEENAME(prpDplane.getAirlineEname());
//			if(null!=prpDplane.getFactoryDate()){
//			responsePacket.getBODY().getPRPDPLANERESINFO().setFACTORYDATE(PubFun.DateToStr(prpDplane.getFactoryDate()));
//			}
//			responsePacket.getBODY().getPRPDPLANERESINFO().setFACTORYNO(prpDplane.getFactoryNo());
//			if(prpDplane.getJpyAmount()!=null){
//				responsePacket.getBODY().getPRPDPLANERESINFO().setJPYAMOUNT(prpDplane.getJpyAmount().toString());
//			}
//			
//			responsePacket.getBODY().getPRPDPLANERESINFO().setLICENCENO(prpDplane.getLicenceNo());
//			responsePacket.getBODY().getPRPDPLANERESINFO().setLOANSTAUS(prpDplane.getLoanStaus());
//			if(null!=prpDplane.getMakeYear()){
//				responsePacket.getBODY().getPRPDPLANERESINFO().setMAKEYEAR(PubFun.DateToStr(prpDplane.getMakeYear()));
//			}
//			responsePacket.getBODY().getPRPDPLANERESINFO().setMODEL(prpDplane.getModel());
//			responsePacket.getBODY().getPRPDPLANERESINFO().setPLANETYPE(prpDplane.getPlaneType());
//			responsePacket.getBODY().getPRPDPLANERESINFO().setPLANEUSAGE(prpDplane.getPlaneUsage());
//			responsePacket.getBODY().getPRPDPLANERESINFO().setRANGE(prpDplane.getRange());
//			responsePacket.getBODY().getPRPDPLANERESINFO().setREMARK(prpDplane.getRemark());
//			if(null!=prpDplane.getSeatCount()){
//				responsePacket.getBODY().getPRPDPLANERESINFO().setSEATCOUNT(prpDplane.getSeatCount().toString());
//			}
//			if(null!=prpDplane.getUsdAmount()){
//				responsePacket.getBODY().getPRPDPLANERESINFO().setUSDAMOUNT(prpDplane.getUsdAmount().toString());
//			}
			
		}
		/***************************
		 * ���ر��Ķ���ת����xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDplaneResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDplaneReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetPrpDplaneReqPacket response = (GetPrpDplaneReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDplaneReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDplaneReqPacket response = (GetPrpDplaneReqPacket) joxIn
//				.readObject(GetPrpDplaneReqPacket.class);
		return response;
	}

}

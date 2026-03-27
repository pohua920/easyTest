package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany.GetPrpDcompanyReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany.GetPrpDcompanyResPacket;
/**
 * 2009.7.30 by ain
 * */
public class GetPrpDcompanyServiceImpl implements
		DataTransformer<GetPrpDcompanyReqPacket, GetPrpDcompanyResPacket> {

	public String execute(String requestxml) throws Exception {
		PrpDcompany prpDcompany = new PrpDcompany();
		String requestType ="";
		/**请求的xml报文翻译成Packet对象*/
		GetPrpDcompanyReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		/**判断requesttype，确定具体执行查找当前代码，还是查找当前代码的上级代码*/
		if(ServiceInfoConst.GETPRPDCOMPANY.equals(requestPacket.getHEAD().getREQUEST_TYPE())){//当前代码
			prpDcompany = dictionaryService.getPrpDcompany(requestPacket.getHEAD().getSYSTEMCODE(), requestPacket.getBODY().getCOMCODE());
			requestType = ServiceInfoConst.GETPRPDCOMPANY;//设置返回报文的requesttype
		}else if(ServiceInfoConst.GETUPPERPRPDCOMPANY.equals(requestPacket.getHEAD().getREQUEST_TYPE())){//上级代码
			prpDcompany = dictionaryService.getUpperPrpDcompany(requestPacket.getHEAD().getSYSTEMCODE(), requestPacket.getBODY().getCOMCODE());
			requestType = ServiceInfoConst.GETUPPERPRPDCOMPANY;//设置返回报文的requesttype
		}
		GetPrpDcompanyResPacket responsePacket = new GetPrpDcompanyResPacket();
		if(null == prpDcompany){
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);//可以是当前代码，和上级代码的请求类型
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
		}else{
		
			 /*************************************
			  * 持久层对象转换封装成数据包对象
			  * *******************************/
			 
			 responsePacket.getHEAD().setREQUEST_TYPE(requestType);//可以是当前代码，和上级代码的请求类型
			 responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			 responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			 responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			 responsePacket.getBODY().getPRPDCOMPANY().setACCOUNTANT(prpDcompany.getAccountant());
			 responsePacket.getBODY().getPRPDCOMPANY().setACNTUNIT(prpDcompany.getAcntUnit());
			 responsePacket.getBODY().getPRPDCOMPANY().setADDRESSCNAME(prpDcompany.getAddressCName());
			 responsePacket.getBODY().getPRPDCOMPANY().setADDRESSENAME(prpDcompany.getAddressEName());
			 responsePacket.getBODY().getPRPDCOMPANY().setARTICLECODE(prpDcompany.getArticleCode());
			 responsePacket.getBODY().getPRPDCOMPANY().setCOMCNAME(prpDcompany.getComCName());
			 responsePacket.getBODY().getPRPDCOMPANY().setCOMCODE(prpDcompany.getComCode());
			 responsePacket.getBODY().getPRPDCOMPANY().setCOMENAME(prpDcompany.getComEName());
			 responsePacket.getBODY().getPRPDCOMPANY().setCOMTYPE(prpDcompany.getComType());
			 responsePacket.getBODY().getPRPDCOMPANY().setINSURERNAME(prpDcompany.getInsurerName());
			 responsePacket.getBODY().getPRPDCOMPANY().setMANAGER(prpDcompany.getManager());
			 responsePacket.getBODY().getPRPDCOMPANY().setNEWCOMCODE(prpDcompany.getNewComCode());
			 responsePacket.getBODY().getPRPDCOMPANY().setPHONENUMBER(prpDcompany.getPhoneNumber());
			 responsePacket.getBODY().getPRPDCOMPANY().setPOSTCODE(prpDcompany.getPostCode());
//			 responsePacket.getBODY().getPRPDCOMPANY().setPRINTADDRESSCNAME(prpDcompany.getPrintAddressCName());
//			 responsePacket.getBODY().getPRPDCOMPANY().setPRINTADDRESSENAME(prpDcompany.getPrintAddressEName());
//			 responsePacket.getBODY().getPRPDCOMPANY().setPRINTCOMCNAME(prpDcompany.getPrintComCName());
//			 responsePacket.getBODY().getPRPDCOMPANY().setPRINTCOMENAME(prpDcompany.getPrintComEName());
//			 responsePacket.getBODY().getPRPDCOMPANY().setPRINTPOSTCODE(prpDcompany.getPrintPostCode());
			 responsePacket.getBODY().getPRPDCOMPANY().setCOMKIND(prpDcompany.getComKind()); //新增加的字段　start...
			 responsePacket.getBODY().getPRPDCOMPANY().setUPDATEFLAG(prpDcompany.getUpdateFlag());
			 responsePacket.getBODY().getPRPDCOMPANY().setUPDATEDATE(prpDcompany.getUpdateDate());
			 responsePacket.getBODY().getPRPDCOMPANY().setOPERATORCOMCODE(prpDcompany.getOperatorComCode());//新增加的字段　end...
			 responsePacket.getBODY().getPRPDCOMPANY().setREMARK(prpDcompany.getRemark());
			 responsePacket.getBODY().getPRPDCOMPANY().setUPPERCOMCODE(prpDcompany.getUpperComCode());
			 responsePacket.getBODY().getPRPDCOMPANY().setFAXNUMBER(prpDcompany.getFaxNumber());
			 
			 responsePacket.getBODY().getPRPDCOMPANY().setCOMFLAG(prpDcompany.getComFlag());
			 responsePacket.getBODY().getPRPDCOMPANY().setCENTERFLAG(prpDcompany.getCenterFlag());
			 responsePacket.getBODY().getPRPDCOMPANY().setBRANCHTYPE(prpDcompany.getBranchType());
			 if(!"".equals(prpDcompany.getComLevel())&&prpDcompany.getComLevel()!=null){
				 responsePacket.getBODY().getPRPDCOMPANY().setCOMLEVEL(prpDcompany.getComLevel().toString());
			 }
			 responsePacket.getBODY().getPRPDCOMPANY().setVALIDSTATUS(prpDcompany.getValidStatus());
			 responsePacket.getBODY().getPRPDCOMPANY().setFLAG(prpDcompany.getFlag());
//			 /**添加岗位模板id--2009-10-20 start*/
//			 if(null!=prpDcompany.getGradeTemplId()){
//				 responsePacket.getBODY().getPRPDCOMPANY().setGRADETEMPLID(Long.toString(prpDcompany.getGradeTemplId()));
//			 }
			 responsePacket.getBODY().getPRPDCOMPANY().setGRADE(prpDcompany.getUpperPath());//GRADE代表model中的upperPath
//			 responsePacket.getBODY().getPRPDCOMPANY().setPRINTWSURL(prpDcompany.getPrintWsUrl());
			 /**添加岗位模板id--2009-10-20 end*/
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDcompanyResPacket responsePacket) throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(responsePacket, responsePacket.getHEAD()
				.getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDcompanyReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetPrpDcompanyReqPacket response = (GetPrpDcompanyReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDcompanyReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDcompanyReqPacket response = (GetPrpDcompanyReqPacket) joxIn
//				.readObject(GetPrpDcompanyReqPacket.class);
		return response;
	}

}

package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.findCompanyByCondition.FindCompanyByConditionReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcompanyList.GetPrpDcompanyListResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany.PrpDcompanyResInfo;

public class FindCompanyByConditionServiceImpl implements
DataTransformer<FindCompanyByConditionReqPacket, GetPrpDcompanyListResPacket>{

	public String execute(String requestxml) throws Exception {

		List<PrpDcompany> list = new ArrayList<PrpDcompany>();
		String requestType ="";
		/**请求的xml报文翻译成Packet对象*/
		FindCompanyByConditionReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		/**判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码*/
			/**调用本地接口服务getAllSubCompany*/ 
			list = dictionaryService.findCompanyByCondition(requestPacket.getHEAD().getSYSTEMCODE(), requestPacket.getBODY().getCONDITION());
			requestType = ServiceInfoConst.FINDCOMPANYBYCONDITION;//设置返回报文的requesttype
		 /*************************************
		  * 持久层对象转换封装成数据包对象
		  * *******************************/
		 GetPrpDcompanyListResPacket responsePacket = new GetPrpDcompanyListResPacket();
		 if (list.size() == 0) {
				responsePacket.getHEAD().setREQUEST_TYPE(requestType);
				responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
				responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
				responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			 BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//				throw be;	
			}else {
				 responsePacket.getHEAD().setREQUEST_TYPE(requestType);//可以是当前代码，和上级代码的请求类型
				 responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
				 responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
				 responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
				 List<PrpDcompanyResInfo> temp = new ArrayList<PrpDcompanyResInfo>();
				for(int i=0;i<list.size();i++){
					PrpDcompany prpDcompany = list.get(i);
					PrpDcompanyResInfo prpDcompanyResInfo = new PrpDcompanyResInfo();
					 prpDcompanyResInfo.setACCOUNTANT(prpDcompany.getAccountant());
					 prpDcompanyResInfo.setACNTUNIT(prpDcompany.getAcntUnit());
					 prpDcompanyResInfo.setADDRESSCNAME(prpDcompany.getAddressCName());
					 prpDcompanyResInfo.setADDRESSENAME(prpDcompany.getAddressEName());
					 prpDcompanyResInfo.setARTICLECODE(prpDcompany.getArticleCode());
					 prpDcompanyResInfo.setCOMCNAME(prpDcompany.getComCName());
					 prpDcompanyResInfo.setCOMCODE(prpDcompany.getComCode());
					 prpDcompanyResInfo.setCOMENAME(prpDcompany.getComEName());
					 prpDcompanyResInfo.setCOMTYPE(prpDcompany.getComType());
					 prpDcompanyResInfo.setINSURERNAME(prpDcompany.getInsurerName());
					 prpDcompanyResInfo.setMANAGER(prpDcompany.getManager());
					 prpDcompanyResInfo.setNEWCOMCODE(prpDcompany.getNewComCode());
					 prpDcompanyResInfo.setPHONENUMBER(prpDcompany.getPhoneNumber());
					 prpDcompanyResInfo.setPOSTCODE(prpDcompany.getPostCode());
//					 prpDcompanyResInfo.setPRINTADDRESSCNAME(prpDcompany.getPrintAddressCName());
//					 prpDcompanyResInfo.setPRINTADDRESSENAME(prpDcompany.getPrintAddressEName());
//					 prpDcompanyResInfo.setPRINTCOMCNAME(prpDcompany.getPrintComCName());
//					 prpDcompanyResInfo.setPRINTCOMENAME(prpDcompany.getPrintComEName());
//					 prpDcompanyResInfo.setPRINTPOSTCODE(prpDcompany.getPrintPostCode());
					 prpDcompanyResInfo.setCOMKIND(prpDcompany.getComKind()); //新增加的字段　start...
					 prpDcompanyResInfo.setUPDATEFLAG(prpDcompany.getUpdateFlag());
					 prpDcompanyResInfo.setUPDATEDATE(prpDcompany.getUpdateDate());
					 prpDcompanyResInfo.setOPERATORCOMCODE(prpDcompany.getOperatorComCode());//新增加的字段　end...
					 prpDcompanyResInfo.setREMARK(prpDcompany.getRemark());
					 prpDcompanyResInfo.setUPPERCOMCODE(prpDcompany.getUpperComCode());
					 prpDcompanyResInfo.setFAXNUMBER(prpDcompany.getFaxNumber());
					
					 prpDcompanyResInfo.setCOMFLAG(prpDcompany.getComFlag());
					 prpDcompanyResInfo.setCENTERFLAG(prpDcompany.getCenterFlag());
					 prpDcompanyResInfo.setBRANCHTYPE(prpDcompany.getBranchType());
					 if(!"".equals(prpDcompany.getComLevel())&&prpDcompany.getComLevel()!=null){
						 prpDcompanyResInfo.setCOMLEVEL(prpDcompany.getComLevel().toString());
					 }
					 prpDcompanyResInfo.setVALIDSTATUS(prpDcompany.getValidStatus());
					 prpDcompanyResInfo.setFLAG(prpDcompany.getFlag());
					 
//					 /**添加岗位模板id--2009-10-20 start*/
//					 if(null!=prpDcompany.getGradeTemplId()){
//						 prpDcompanyResInfo.setGRADETEMPLID(Long.toString(prpDcompany.getGradeTemplId()));
//					 }
					 /**添加岗位模板id--2009-10-20 end*/
					 prpDcompanyResInfo.setGRADE(prpDcompany.getUpperPath());//GRADE代表model中的upperPath
//					 prpDcompanyResInfo.setPRINTWSURL(prpDcompany.getPrintWsUrl());
					 temp.add(prpDcompanyResInfo);
				}
				 int i = temp.size();
				 responsePacket.getBODY().getPRPDCOMPANYLIST().setPRPDCOMPANY(temp.toArray(new PrpDcompanyResInfo[i]));
			}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDcompanyListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public FindCompanyByConditionReqPacket xmlToSchema(String requestxml)
			throws Exception {
		FindCompanyByConditionReqPacket response = (FindCompanyByConditionReqPacket) PubFun
		.generateJox(requestxml).readObject(FindCompanyByConditionReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
//		FindCompanyByConditionReqPacket response = (FindCompanyByConditionReqPacket) joxIn
//				.readObject(FindCompanyByConditionReqPacket.class);
		return response;
	}
}

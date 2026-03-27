package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList.GetPrpDcodeListReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList.GetPrpDcodeListResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList.PrpDcodeListResInfo;


public class GetPrpDcodeListServiceImpl implements 
	DataTransformer<GetPrpDcodeListReqPacket, GetPrpDcodeListResPacket>{

	public String execute(String requestxml) throws Exception {
		/**请求的xml报文翻译成Packet对象*/
		GetPrpDcodeListReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory.getService("dictionaryService");//获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String requestType = requestPacket.getHEAD().getREQUEST_TYPE();//add by guyanqing 
		String codeType = requestPacket.getBODY().getCODETYPE();
		
		List<PrpDnewCode> list = null;
		GetPrpDcodeListResPacket responsePacket = new GetPrpDcodeListResPacket();
		//modify begin by guyanqing 2011-06-23 reason:增加条件查询prpdcode表
		//list = dictionaryService.getPrpDcodeBytype(systemCode, codeType);
			if(ServiceInfoConst.GETPRPDBYCONDITON.equals(requestType)){
				list = dictionaryService.findCodeByCondition(systemCode, codeType);
			}else{
			list = dictionaryService.getPrpDcodeBytype(systemCode, codeType);
			}
		//modify end by guyanqing 2011-06-23 reason:增加条件查询prpdcode表
		if(list == null||list.size()==0){
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETPRPDCODELIST);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;	
		}else{
			/**设置返回报文头*/
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETPRPDCODELIST);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			/**设置返回报文体*/
			List<PrpDcodeListResInfo> codeList = new ArrayList<PrpDcodeListResInfo>();
			for(int i = 0;i<list.size();i++){
				PrpDnewCode prpDcode = list.get(i);
				PrpDcodeListResInfo codeResInfo = new PrpDcodeListResInfo();
				codeResInfo.setCODETYPE(prpDcode.getId().getCodeType());
				codeResInfo.setCODECODE(prpDcode.getId().getCodeCode());
				codeResInfo.setCODECNAME(prpDcode.getCodeCName());
				codeResInfo.setCODEENAME(prpDcode.getCodeEName());
				codeResInfo.setOLDCODETYPE(prpDcode.getOldCodeType());
				codeResInfo.setOLDCODECODE(prpDcode.getOldCodeCode());
				codeResInfo.setNEWCODECODE(prpDcode.getNewCodeCode());
				codeResInfo.setVALIDSTATUS(prpDcode.getValidStatus());
				codeResInfo.setFLAG(prpDcode.getFlag());
				codeList.add(codeResInfo);
			}
			int i = codeList.size();
			responsePacket.getBODY().getCODELIST().setCODEINFO(codeList.toArray(new PrpDcodeListResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetPrpDcodeListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDcodeListReqPacket xmlToSchema(String requestxml)
			throws Exception {
		GetPrpDcodeListReqPacket response = (GetPrpDcodeListReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDcodeListReqPacket.class);
		return response;
	}
}

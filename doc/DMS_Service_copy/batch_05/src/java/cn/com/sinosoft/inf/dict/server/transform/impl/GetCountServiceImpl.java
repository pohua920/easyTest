package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getCount.GetCountReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getCount.GetCountResPacket;

public class GetCountServiceImpl implements 
DataTransformer<GetCountReqPacket, GetCountResPacket>{

	public String execute(String requestxml) throws Exception {
//		PrpDship prpDship = new PrpDship();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		GetCountReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String tableName = requestPacket.getBODY().getTABLENAME();
		String condition = requestPacket.getBODY().getCONDITION();
		int count = dictionaryService.getCount(systemCode, tableName, condition);
//		requestType = ServiceInfoConst.GETCOUNT;// 设置返回报文的requesttype
		GetCountResPacket responsePacket = new GetCountResPacket();
		if(count == -1){
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERROR_MESSAGE_NULL);
//			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
//			throw be;
		}else{
			/*************************************
			 * 持久层对象转换封装成数据包对象
			 * *******************************/
			/**设置返回报文头*/
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			/**设置返回报文体*/
			responsePacket.getBODY().setCOUNT(Integer.toString(count));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(GetCountResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetCountReqPacket xmlToSchema(String requestxml) throws Exception {
		GetCountReqPacket response = (GetCountReqPacket) PubFun
		.generateJox(requestxml).readObject(GetCountReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetCountReqPacket response = (GetCountReqPacket) joxIn
//				.readObject(GetCountReqPacket.class);
		return response;
	}

}

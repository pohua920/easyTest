package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.Date;

import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.exchange.ExchangeReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.exchange.ExchangeResPacket;

import com.sinosoft.sysframework.exception.BusinessException;

public class ExchangeServiceImpl implements
		DataTransformer<ExchangeReqPacket, ExchangeResPacket> {

	public String execute(String requestxml) throws Exception {
		/** 请求的xml报文翻译成Packet对象 */
		ExchangeReqPacket requestPacket = xmlToSchema(requestxml);

		/*************************************
		 * 持久层操作，查找数据库并生成返回报文对象
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		/** string 转换成 date类型yyyy-MM-dd形式 */
		Date currDate = PubFun.StrToDate(requestPacket.getBODY().getCURRDATE());
		String baseCurrency = requestPacket.getBODY().getBASECURRENCY();
		String exchCurrency = requestPacket.getBODY().getEXCHCURRENCY();
		Double amount = Double.parseDouble(requestPacket.getBODY().getAMOUNT());
		Double EXCHEDAMOUNT;
		EXCHEDAMOUNT = dictionaryService.exchange(systemCode, currDate,
				baseCurrency, exchCurrency, amount);
		ExchangeResPacket responsePacket = new ExchangeResPacket();
		if(-1==EXCHEDAMOUNT){//兑换发生异常(没有相应的兑换率)
//			/** 设置返回错误报文头 */
//			responsePacket.getHEAD().setERROR_CODE(
//					ServiceInfoConst.ERROR_CODE_NULL);
//			responsePacket.getHEAD().setERROR_MESSAGE(
//					ServiceInfoConst.ERROR_MESSAGE_NULL);
//			responsePacket.getHEAD().setREQUEST_TYPE(
//					ServiceInfoConst.EXCHANGE);
//			responsePacket.getHEAD().setRESPONSE_CODE(
//					ServiceInfoConst.RESPONSE_CODE0);
			BusinessException be = new BusinessException(ServiceInfoConst.ERROR_CODE_NULL, ServiceInfoConst.ERROR_MESSAGE_NULL);
			throw be;
		}else{
			/** 设置返回报文头 */
			responsePacket.getHEAD().setERROR_CODE(ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(ServiceInfoConst.ERRORMSG_SUCCESS);
			responsePacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.EXCHANGE);
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			/** 设置返回报文体 */
			responsePacket.getBODY().setEXCHEDAMOUNT(Double.toString(EXCHEDAMOUNT));
		}
		
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(ExchangeResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public ExchangeReqPacket xmlToSchema(String requestxml) throws Exception {
		ExchangeReqPacket response = (ExchangeReqPacket) PubFun
		.generateJox(requestxml).readObject(ExchangeReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		ExchangeReqPacket response = (ExchangeReqPacket) joxIn
//				.readObject(ExchangeReqPacket.class);
		return response;
	}

}

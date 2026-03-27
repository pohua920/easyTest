package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.Date;

import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch.GetPrpDexchReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPrpDexch.GetPrpDexchResPacket;

public class GetPrpDexchServiceImpl implements
		DataTransformer<GetPrpDexchReqPacket, GetPrpDexchResPacket> {

	public String execute(String requestxml) throws Exception {
		String responsexml = null;

			/** 请求的xml报文翻译成Packet对象 */
			GetPrpDexchReqPacket requestPacket;
			// try {

			requestPacket = xmlToSchema(requestxml);

			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			/*************************************
			 * 持久层操作，查找数据库并生成返回报文对象
			 * **********************************/
			DictionaryService dictionaryService = (DictionaryService) ServiceFactory
					.getService("dictionaryService");// 获得Spring管理的bean
			String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
			Date exchDate;
			exchDate = PubFun.StrToDate(requestPacket.getBODY().getEXCHDATE());
			String baseCurrency = requestPacket.getBODY().getBASECURRENCY();
			String exchCurrency = requestPacket.getBODY().getEXCHCURRENCY();
			PrpDexch prpDexch = dictionaryService.getPrpDexch(systemCode,
					exchDate, baseCurrency, exchCurrency);
			GetPrpDexchResPacket responsePacket = new GetPrpDexchResPacket();
			if (null == prpDexch) {
				// /** 设置返回报文头 */
				 responsePacket.getHEAD().setERROR_CODE(
				 ServiceInfoConst.ERROR_CODE_NULL);
				 responsePacket.getHEAD().setERROR_MESSAGE(
				 ServiceInfoConst.ERROR_MESSAGE_NULL);
				 responsePacket.getHEAD().setREQUEST_TYPE(
				 ServiceInfoConst.GETPRPDEXCH);
				 responsePacket.getHEAD().setRESPONSE_CODE(
				 ServiceInfoConst.RESPONSECODE_SUCCESS);
//				throw new BusinessException(ServiceInfoConst.ERROR_CODE_NULL,
//						ServiceInfoConst.ERROR_MESSAGE_NULL);
			} else {
				/** 设置返回报文头 */
				responsePacket.getHEAD().setERROR_CODE(
						ServiceInfoConst.ERRORCODE_SUCCESS);
				responsePacket.getHEAD().setERROR_MESSAGE(
						ServiceInfoConst.ERRORMSG_SUCCESS);
				responsePacket.getHEAD().setREQUEST_TYPE(
						ServiceInfoConst.GETPRPDEXCH);
				responsePacket.getHEAD().setRESPONSE_CODE(
						ServiceInfoConst.RESPONSECODE_SUCCESS);
				/** 设置返回报文体 */
				responsePacket.getBODY().getPRPDEXCHINFO().setEXCHDATE(
						PubFun.DateToStr(prpDexch.getId().getExchDate()));
				if (null != prpDexch.getBase()) {
					responsePacket.getBODY().getPRPDEXCHINFO().setBASE(
							prpDexch.getBase().toString());
				}
				responsePacket.getBODY().getPRPDEXCHINFO().setBASECURRENCY(
						prpDexch.getId().getBaseCurrency());
				responsePacket.getBODY().getPRPDEXCHINFO().setEXCHCURRENCY(
						prpDexch.getId().getExchCurrency());
				if (null != prpDexch.getExchRate()) {
					responsePacket.getBODY().getPRPDEXCHINFO().setEXCHRATE(
							prpDexch.getExchRate().toString());
				}
				if (null != prpDexch.getBuyPrice()) {
					responsePacket.getBODY().getPRPDEXCHINFO().setBUYPRICE(
							prpDexch.getBuyPrice().toString());
				}

				if (null != prpDexch.getSalePrice()) {
					responsePacket.getBODY().getPRPDEXCHINFO().setSALEPRICE(
							prpDexch.getSalePrice().toString());
				}

				if (null != prpDexch.getCashPrice()) {
					responsePacket.getBODY().getPRPDEXCHINFO().setCASHPRICE(
							prpDexch.getCashPrice().toString());
				}

			}
			/***************************
			 * 返回报文对象转换成xml
			 * ***************************/
			responsexml = schemaToXml(responsePacket);

		return responsexml;
	}

	public String schemaToXml(GetPrpDexchResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public GetPrpDexchReqPacket xmlToSchema(String requestxml) throws Exception {
		GetPrpDexchReqPacket response = (GetPrpDexchReqPacket) PubFun
		.generateJox(requestxml).readObject(GetPrpDexchReqPacket.class);
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(
//				new ByteArrayInputStream(requestxml.getBytes()));
//		GetPrpDexchReqPacket response = (GetPrpDexchReqPacket) joxIn
//				.readObject(GetPrpDexchReqPacket.class);
		return response;
	}

}

package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskEngageIdReqPacket.SendPrpDriskEngageIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskEngageListResPacket.PrpDriskEngageListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskEngageListResPacket.PrpDriskEngageResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDriskEngageById implements
		DataTransformer<SendPrpDriskEngageIdReqPacket, PrpDriskEngageListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDriskEngage> list = new ArrayList<PrpDriskEngage>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDriskEngageIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDriskEngageById */
		
		PrpDriskEngageId prpDriskEngageId = new PrpDriskEngageId();
		prpDriskEngageId.setEngageCode(requestPacket.getBODY().getENGAGECODE());
		prpDriskEngageId.setRiskCode(requestPacket.getBODY().getRISKCODE());
		list = dictionaryService.findPrpDriskEngageById(requestPacket.getHEAD()
				.getSYSTEMCODE(), prpDriskEngageId);
		
		requestType = ServiceInfoConst.FINDPRPDRISKENGAGEBYID;// 设置返回报文的requesttype
		
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDriskEngageListResPacket responsePacket = new PrpDriskEngageListResPacket();
		if (list.size() == 0) {
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(
					ServiceInfoConst.ERROR_CODE_NULL);
			responsePacket.getHEAD().setERROR_MESSAGE(
					ServiceInfoConst.ERROR_MESSAGE_NULL);
			// BusinessException be = new
			// BusinessException(ServiceInfoConst.ERROR_CODE_NULL,
			// ServiceInfoConst.ERROR_MESSAGE_NULL);
			// throw be;
		} else {
			responsePacket.getHEAD().setREQUEST_TYPE(requestType);// 可以是当前代码，
			// 和上级代码的请求类型
			responsePacket.getHEAD().setRESPONSE_CODE(
					ServiceInfoConst.RESPONSECODE_SUCCESS);
			responsePacket.getHEAD().setERROR_CODE(
					ServiceInfoConst.ERRORCODE_SUCCESS);
			responsePacket.getHEAD().setERROR_MESSAGE(
					ServiceInfoConst.ERRORMSG_SUCCESS);
			List<PrpDriskEngageResInfo> temp = new ArrayList<PrpDriskEngageResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDriskEngage prpDriskEngage = list.get(i);
				PrpDriskEngageResInfo prpDriskEngageResInfo = new PrpDriskEngageResInfo();
				if(null!=prpDriskEngage.getId()){
					prpDriskEngageResInfo.setRISKCODE(prpDriskEngage.getId().getRiskCode());
					prpDriskEngageResInfo.setENGAGECODE(prpDriskEngage.getId().getEngageCode());
				}
				prpDriskEngageResInfo.setENGAGECNAME(prpDriskEngage.getEngageCName());
				prpDriskEngageResInfo.setENGAGEENAME(prpDriskEngage.getEngageEName());
				prpDriskEngageResInfo.setLANGUAGE(prpDriskEngage.getLanguage());
				prpDriskEngageResInfo.setENGAGEDESC(prpDriskEngage.getEngageDesc());
				prpDriskEngageResInfo.setVALIDIND(prpDriskEngage.getValidInd());
				prpDriskEngageResInfo.setTCOL1(prpDriskEngage.getTcol1());
				prpDriskEngageResInfo.setTCOL2(prpDriskEngage.getTcol2());
				prpDriskEngageResInfo.setTCOL3(prpDriskEngage.getTcol3());
				prpDriskEngageResInfo.setREMARK(prpDriskEngage.getRemark());
				prpDriskEngageResInfo.setFLAG(prpDriskEngage.getFlag());
				if(null!=prpDriskEngage.getValidDate()){
					prpDriskEngageResInfo.setVALIDDATE(PubFun.DateToStr(prpDriskEngage.getValidDate()));
				}
				if(null!=prpDriskEngage.getInvalidDate()){
					prpDriskEngageResInfo.setINVAIDDATE(PubFun.DateToStr(prpDriskEngage.getInvalidDate()));
				}
				temp.add(prpDriskEngageResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDRISKENGAGELIST().setPRPDRISKENGAGE(
					temp.toArray(new PrpDriskEngageResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDriskEngageListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDriskEngageIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDriskEngageIdReqPacket response = (SendPrpDriskEngageIdReqPacket) joxIn
				.readObject(SendPrpDriskEngageIdReqPacket.class);
		return response;
	}
}

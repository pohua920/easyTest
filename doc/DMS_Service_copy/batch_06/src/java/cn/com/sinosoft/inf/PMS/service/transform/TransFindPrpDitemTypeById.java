package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDitemType;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDitemTypeIdReqPacket.SendPrpDitemTypeIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDitemTypeListResPacket.PrpDitemTypeListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDitemTypeListResPacket.PrpDitemTypeResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDitemTypeById implements
		DataTransformer<SendPrpDitemTypeIdReqPacket, PrpDitemTypeListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDitemType> list = new ArrayList<PrpDitemType>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDitemTypeIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDitemTypeById */
		
		//TODO 在数据字典接口中添加方法（findPrpDitemTypeById）
		list = dictionaryService.findPrpDitemTypeById(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getITEMTYPE());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDITEMTYPEBYID;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDitemTypeListResPacket responsePacket = new PrpDitemTypeListResPacket();
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
			List<PrpDitemTypeResInfo> temp = new ArrayList<PrpDitemTypeResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDitemType prpDitemType = list.get(i);
				PrpDitemTypeResInfo prpDitemTypeResInfo = new PrpDitemTypeResInfo();
				prpDitemTypeResInfo.setITEMTYPE(prpDitemType.getItemType());
				prpDitemTypeResInfo.setITEMTYPECNAME(prpDitemType.getItemTypeCName());
				prpDitemTypeResInfo.setITEMTYPETNAME(prpDitemType.getItemTypeTName());
				prpDitemTypeResInfo.setITEMTYPEENAME(prpDitemType.getItemTypeEName());
				prpDitemTypeResInfo.setCREATORCODE(prpDitemType.getCreatorCode());
				if(null!=prpDitemType.getCreateTime()){
					prpDitemTypeResInfo.setCREATETIME(PubFun.DateToStr(prpDitemType.getCreateTime()));
				}
				prpDitemTypeResInfo.setUPDATERCODE(prpDitemType.getUpdaterCode());
				if(null!=prpDitemType.getUpdateTime()){
					prpDitemTypeResInfo.setUPDATETIME(PubFun.DateToStr(prpDitemType.getUpdateTime()));
				}
				if(null!=prpDitemType.getValidDate()){
					prpDitemTypeResInfo.setVALIDDATE(PubFun.DateToStr(prpDitemType.getValidDate()));
				}
				if(null!=prpDitemType.getInvalidDate()){
					prpDitemTypeResInfo.setINVALIDDATE(PubFun.DateToStr(prpDitemType.getInvalidDate()));
				}
				
				prpDitemTypeResInfo.setVALIDIND(prpDitemType.getValidInd());
				prpDitemTypeResInfo.setTCOL1(prpDitemType.getTcol1());
				prpDitemTypeResInfo.setTCOL2(prpDitemType.getTcol2());
				prpDitemTypeResInfo.setTCOL3(prpDitemType.getTcol3());
				prpDitemTypeResInfo.setREMARK(prpDitemType.getRemark());
				prpDitemTypeResInfo.setFLAG(prpDitemType.getFlag());
				temp.add(prpDitemTypeResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDITEMTYPELIST().setPRPDITEMTYPE(
					temp.toArray(new PrpDitemTypeResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDitemTypeListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDitemTypeIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDitemTypeIdReqPacket response = (SendPrpDitemTypeIdReqPacket) joxIn
				.readObject(SendPrpDitemTypeIdReqPacket.class);
		return response;
	}
}

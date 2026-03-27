package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDframe;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDframeIdReqPacket.SendPrpDframeIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDframeListResPacket.PrpDframeListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDframeListResPacket.PrpDframeResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDframeById implements
		DataTransformer<SendPrpDframeIdReqPacket, PrpDframeListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDframe> list = new ArrayList<PrpDframe>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDframeIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDframeById */
		
		//TODO 在数据字典接口中添加方法（findPrpDframeById）
		list = dictionaryService.findPrpDframeById(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getFRAMECODE());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDFRAMEBYID;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDframeListResPacket responsePacket = new PrpDframeListResPacket();
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
			List<PrpDframeResInfo> temp = new ArrayList<PrpDframeResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDframe prpDframe = list.get(i);
				PrpDframeResInfo prpDframeResInfo = new PrpDframeResInfo();
				prpDframeResInfo.setFRAMECODE(prpDframe.getFrameCode());
				prpDframeResInfo.setFRAMECNAME(prpDframe.getFrameCName());
				prpDframeResInfo.setFRAMETNAME(prpDframe.getFrameTName());
				prpDframeResInfo.setFRAMESNAME(prpDframe.getFrameSName());
				prpDframeResInfo.setFRAMEENAME(prpDframe.getFrameEName());
				prpDframeResInfo.setSALEAREALEVEL(prpDframe.getAreaLevel());
				prpDframeResInfo.setSALEAREACODE(prpDframe.getAreaCode());
				prpDframeResInfo.setCUSTOMERTYPE(prpDframe.getCustomerType());
				prpDframeResInfo.setMATERIALCONTXT(prpDframe.getMaterialContxt());
				prpDframeResInfo.setDOCUMENTNUMBER(prpDframe.getDocumentNumber());
				prpDframeResInfo.setCONTENTNUMBER(prpDframe.getContentNumber());
				prpDframeResInfo.setCREATORCODE(prpDframe.getCreaterCode());
				if(null!=prpDframe.getCreateTime()){
					prpDframeResInfo.setCREATETIME(PubFun.DateToStr(prpDframe.getCreateTime()));
				}
				prpDframeResInfo.setUPDATERCODE(prpDframe.getUpdaterCode());
				if(null!=prpDframe.getUpdateTime()){
					prpDframeResInfo.setUPDATETIME(PubFun.DateToStr(prpDframe.getUpdateTime()));
				}
				if(null!=prpDframe.getValidDate()){
					prpDframeResInfo.setVALIDDATE(PubFun.DateToStr(prpDframe.getValidDate()));
				}
				if(null!=prpDframe.getInvalidDate()){
					prpDframeResInfo.setINVALIDDATE(PubFun.DateToStr(prpDframe.getInvalidDate()));
				}
				
				prpDframeResInfo.setVALIDIND(prpDframe.getValidInd());
				prpDframeResInfo.setTCOL1(prpDframe.getTcol1());
				prpDframeResInfo.setTCOL2(prpDframe.getTcol2());
				prpDframeResInfo.setTCOL3(prpDframe.getTcol3());
				prpDframeResInfo.setREMARK(prpDframe.getRemark());
				prpDframeResInfo.setFLAG(prpDframe.getFlag());
				temp.add(prpDframeResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDFRAMELIST().setPRPDFRAME(
					temp.toArray(new PrpDframeResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDframeListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDframeIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDframeIdReqPacket response = (SendPrpDframeIdReqPacket) joxIn
				.readObject(SendPrpDframeIdReqPacket.class);
		return response;
	}
}

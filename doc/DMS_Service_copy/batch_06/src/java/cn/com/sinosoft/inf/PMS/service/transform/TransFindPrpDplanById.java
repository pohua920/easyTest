package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDplan;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDplanIdReqPacket.SendPrpDplanIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDplanListResPacket.PrpDplanListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDplanListResPacket.PrpDplanResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDplanById implements
		DataTransformer<SendPrpDplanIdReqPacket, PrpDplanListResPacket> {

	public String execute(String requestxml) throws Exception {
		List list = new ArrayList();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDplanIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDplanById */
		
		//TODO 在数据字典接口中添加方法（findPrpDplanById）
		list = dictionaryService.findPrpDplanById(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getPLANCODE());
		
		requestType =  ServiceInfoConst.FINDPRPDPLANBYID;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDplanListResPacket responsePacket = new PrpDplanListResPacket();
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
			List<PrpDplanResInfo> temp = new ArrayList<PrpDplanResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDplan prpDplan = (PrpDplan)list.get(i);
				PrpDplanResInfo prpDplanResInfo = new PrpDplanResInfo();
				prpDplanResInfo.setPLANCODE(prpDplan.getPlanCode());
				prpDplanResInfo.setPLANCODE(prpDplan.getPlanCode());
				prpDplanResInfo.setOLDPLANCODE(prpDplan.getOldPlanCode());
				prpDplanResInfo.setPLANCNAME(prpDplan.getPlanCName());
				prpDplanResInfo.setPLANTNAME(prpDplan.getPlanTName());
				prpDplanResInfo.setPLANSNAME(prpDplan.getPlanSName());
				prpDplanResInfo.setPLANENAME(prpDplan.getPlanEName());
				prpDplanResInfo.setFRAMECODE(prpDplan.getFrameCode());
				prpDplanResInfo.setRISKCODE(prpDplan.getRiskCode());
				prpDplanResInfo.setCREATORCODE(prpDplan.getCreaterCode());
				if(null!=prpDplan.getCreateTime()){
					prpDplanResInfo.setCREATETIME(PubFun.DateToStr(prpDplan.getCreateTime()));
				}
				
				prpDplanResInfo.setUPDATERCODE(prpDplan.getUpdaterCode());
				if(null!=prpDplan.getUpdateTime()){
					prpDplanResInfo.setUPDATETIME(PubFun.DateToStr(prpDplan.getUpdateTime()));
				}
				
				prpDplanResInfo.setCONTENTNUMBER(prpDplan.getContentNumber());
				if(null!=prpDplan.getValidDate()){
					prpDplanResInfo.setVALIDDATE(PubFun.DateToStr(prpDplan.getValidDate()));
				}
				if(null!=prpDplan.getInvalidDate()){
					prpDplanResInfo.setINVAIDDATE(PubFun.DateToStr(prpDplan.getInvalidDate()));
				}
				
				prpDplanResInfo.setVALIDIND(prpDplan.getValidInd());
				prpDplanResInfo.setTCOL1(prpDplan.getTcol1());
				prpDplanResInfo.setTCOL2(prpDplan.getTcol2());
				prpDplanResInfo.setTCOL3(prpDplan.getTcol3());
				prpDplanResInfo.setREMARK(prpDplan.getRemark());
				prpDplanResInfo.setFLAG(prpDplan.getFlag());
				temp.add(prpDplanResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDPLANLIST().setPRPDPLAN(
					temp.toArray(new PrpDplanResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDplanListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDplanIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDplanIdReqPacket response = (SendPrpDplanIdReqPacket) joxIn
				.readObject(SendPrpDplanIdReqPacket.class);
		return response;
	}
}

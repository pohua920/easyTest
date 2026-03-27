package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskClauseKindIdReqPacket.SendPrpDriskClauseKindIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskClauseKindListResPacket.PrpDriskClauseKindListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskClauseKindListResPacket.PrpDriskClauseKindResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDriskClauseKindById implements
		DataTransformer<SendPrpDriskClauseKindIdReqPacket, PrpDriskClauseKindListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDriskClauseKind> list = new ArrayList<PrpDriskClauseKind>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDriskClauseKindIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDriskClauseKindById */
		
		//TODO 在数据字典接口中添加方法（findPrpDriskClauseKindById）
		PrpDriskClauseKindId prpDriskClauseKindId = new PrpDriskClauseKindId();
		prpDriskClauseKindId.setClauseCode(requestPacket.getBODY().getCLAUSEKINDID());
		prpDriskClauseKindId.setRiskCode(requestPacket.getBODY().getRISKCODE());
		list = dictionaryService.findPrpDriskClauseKindById(requestPacket.getHEAD()
				.getSYSTEMCODE(), prpDriskClauseKindId);
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDRISKCLAUSEKINDBYID;// 设置返回报文的requesttype
		
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDriskClauseKindListResPacket responsePacket = new PrpDriskClauseKindListResPacket();
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
			List<PrpDriskClauseKindResInfo> temp = new ArrayList<PrpDriskClauseKindResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDriskClauseKind prpDriskClauseKind = list.get(i);
				PrpDriskClauseKindResInfo prpDriskClauseKindResInfo = new PrpDriskClauseKindResInfo();
				if(null!=prpDriskClauseKind.getId()){
					prpDriskClauseKindResInfo.setRISKCODE(prpDriskClauseKind.getId().getRiskCode());
					prpDriskClauseKindResInfo.setCLAUSEKINDID(prpDriskClauseKind.getId().getClauseCode());
				}
//				prpDriskClauseKindResInfo.setCLAUSECODE(prpDriskClauseKind.getClauseAttribute());
				prpDriskClauseKindResInfo.setKINDCODE(prpDriskClauseKind.getKindCode());
				prpDriskClauseKindResInfo.setCLAUSECLASSCODE(prpDriskClauseKind.getClauseClassCode());
				prpDriskClauseKindResInfo.setKINDCLASSCODE(prpDriskClauseKind.getKindClassCode());
//				prpDriskClauseKindResInfo.setCLAUSEATTRIBUTE(prpDriskClauseKind.getClauseAttribute());
				prpDriskClauseKindResInfo.setKINDATTRIBUTE(prpDriskClauseKind.getKindAttribute());
				prpDriskClauseKindResInfo.setTYPE(prpDriskClauseKind.getType());
				prpDriskClauseKindResInfo.setLOWEROPERATOR(prpDriskClauseKind.getLowerOperator());
				prpDriskClauseKindResInfo.setUPPEROPERATOR(prpDriskClauseKind.getUpperOperator());
				if(null!=prpDriskClauseKind.getUpper()){
					prpDriskClauseKindResInfo.setUPPER(prpDriskClauseKind.getUpper().toString());
				}
				if(null!=prpDriskClauseKind.getLower()){
					prpDriskClauseKindResInfo.setLOWER(prpDriskClauseKind.getLower().toString());
				}
				if(null!=prpDriskClauseKind.getLower()){
					prpDriskClauseKindResInfo.setLOWER(prpDriskClauseKind.getLower().toString());
				}
				if(null!=prpDriskClauseKind.getValue()){
					prpDriskClauseKindResInfo.setVALUE(prpDriskClauseKind.getValue().toString());
				}
				
				prpDriskClauseKindResInfo.setCALCULATEFLAG(prpDriskClauseKind.getCalculateFlag());
//				prpDriskClauseKindResInfo.setOFFSETFLAG(prpDriskClauseKind.getOffSetFlag());
				prpDriskClauseKindResInfo.setVALIDIND(prpDriskClauseKind.getValidInd());
//				prpDriskClauseKindResInfo.setDOCUMENTNUMBER(prpDriskClauseKind.getDocumentNumber());
//				prpDriskClauseKindResInfo.setCLAUSECONTENTNUMBER(prpDriskClauseKind.getClausecontentnumbe());
				prpDriskClauseKindResInfo.setTCOL1(prpDriskClauseKind.getTcol1());
				prpDriskClauseKindResInfo.setTCOL2(prpDriskClauseKind.getTcol2());
				prpDriskClauseKindResInfo.setTCOL3(prpDriskClauseKind.getTcol3());
				prpDriskClauseKindResInfo.setREMARK(prpDriskClauseKind.getRemark());
				prpDriskClauseKindResInfo.setFLAG(prpDriskClauseKind.getFlag());
				if(null!=prpDriskClauseKind.getValidDate()){
					prpDriskClauseKindResInfo.setVALIDDATE(PubFun.DateToStr(prpDriskClauseKind.getValidDate()));
				}
				if(null!=prpDriskClauseKind.getInvalidDate()){
					prpDriskClauseKindResInfo.setINVAIDDATE(PubFun.DateToStr(prpDriskClauseKind.getInvalidDate()));
				}
				temp.add(prpDriskClauseKindResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDRISKCLAUSEKINDLIST().setPRPDRISKCLAUSEKIND(
					temp.toArray(new PrpDriskClauseKindResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDriskClauseKindListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDriskClauseKindIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDriskClauseKindIdReqPacket response = (SendPrpDriskClauseKindIdReqPacket) joxIn
				.readObject(SendPrpDriskClauseKindIdReqPacket.class);
		return response;
	}
}

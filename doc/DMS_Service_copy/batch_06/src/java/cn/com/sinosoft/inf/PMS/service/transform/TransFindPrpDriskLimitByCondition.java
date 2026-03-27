package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskLimitId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.findByConditionReqpacket.FindByConditionReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskLimitListResPacket.PrpDriskLimitListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskLimitListResPacket.PrpDriskLimitResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDriskLimitByCondition implements
		DataTransformer<FindByConditionReqPacket, PrpDriskLimitListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDriskLimit> list = new ArrayList<PrpDriskLimit>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		FindByConditionReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDriskLimitById */
		
		//TODO 在数据字典接口中添加方法（findPrpDriskLimitById）
		list = dictionaryService.findPrpDriskLimitByCondition(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getCONDITION());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDRISKLIMITBYCONDITION;// 设置返回报文的requesttype
		
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDriskLimitListResPacket responsePacket = new PrpDriskLimitListResPacket();
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
			List<PrpDriskLimitResInfo> temp = new ArrayList<PrpDriskLimitResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDriskLimit prpDriskLimit = list.get(i);
				PrpDriskLimitResInfo prpDriskLimitResInfo = new PrpDriskLimitResInfo();
				prpDriskLimitResInfo.setRISKCODE(prpDriskLimit.getId().getRiskCode());
				if(prpDriskLimit.getId().getSerialNo()!=null){
					prpDriskLimitResInfo.setSERIALNO(prpDriskLimit.getId().getSerialNo().toString());
				}
				
				prpDriskLimitResInfo.setLIMITCODE(prpDriskLimit.getId().getLimitCode());
				prpDriskLimitResInfo.setLIMITCNAME(prpDriskLimit.getLimitCName());
				prpDriskLimitResInfo.setLIMITLEVEL(prpDriskLimit.getLimitLevel());
				prpDriskLimitResInfo.setLIMITFLAG(prpDriskLimit.getLimitFlag());
				prpDriskLimitResInfo.setLIMITTYPE(prpDriskLimit.getLimitType());
				prpDriskLimitResInfo.setKINDCODE(prpDriskLimit.getKindCode());
				prpDriskLimitResInfo.setITEMCODE(prpDriskLimit.getItemCode());
				prpDriskLimitResInfo.setCURRENCY(prpDriskLimit.getCurrency());
				if(null!=prpDriskLimit.getLimitFee()){
					prpDriskLimitResInfo.setLIMITFEE(prpDriskLimit.getLimitFee().toString());
				}
				prpDriskLimitResInfo.setPRIORITY(prpDriskLimit.getPriority());
				prpDriskLimitResInfo.setVALIDIND(prpDriskLimit.getValidInd());
				prpDriskLimitResInfo.setTCOL1(prpDriskLimit.getTcol1());
				prpDriskLimitResInfo.setTCOL2(prpDriskLimit.getTcol2());
				prpDriskLimitResInfo.setTCOL3(prpDriskLimit.getTcol3());
				prpDriskLimitResInfo.setREMARK(prpDriskLimit.getRemark());
				prpDriskLimitResInfo.setFLAG(prpDriskLimit.getFlag());
				if(null!=prpDriskLimit.getValidDate()){
					prpDriskLimitResInfo.setVALIDDATE(PubFun.DateToStr(prpDriskLimit.getValidDate()));
				}
				if(null!=prpDriskLimit.getInvalidDate()){
					prpDriskLimitResInfo.setINVALIDDATE(PubFun.DateToStr(prpDriskLimit.getInvalidDate()));
				}
				
				temp.add(prpDriskLimitResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDRISKLIMITLIST().setPRPDRISKLIMIT(
					temp.toArray(new PrpDriskLimitResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDriskLimitListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public FindByConditionReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		FindByConditionReqPacket response = (FindByConditionReqPacket) joxIn
				.readObject(FindByConditionReqPacket.class);
		return response;
	}
}

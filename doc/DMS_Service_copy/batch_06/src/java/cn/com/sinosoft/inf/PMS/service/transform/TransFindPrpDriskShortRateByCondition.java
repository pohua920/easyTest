package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDriskShortRate;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.findByConditionReqpacket.FindByConditionReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskShortRateListResPacket.PrpDriskShortRateListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskShortRateListResPacket.PrpDriskShortRateResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDriskShortRateByCondition implements
		DataTransformer<FindByConditionReqPacket, PrpDriskShortRateListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDriskShortRate> list = new ArrayList<PrpDriskShortRate>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		FindByConditionReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDriskShortRateById */
		
		//TODO 在数据字典接口中添加方法（findPrpDriskShortRateById）
		list = dictionaryService.findPrpDriskShortRateByCondition(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getCONDITION());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDRISKSHORTRATEBYCONDITION;// 设置返回报文的requesttype
		
		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDriskShortRateListResPacket responsePacket = new PrpDriskShortRateListResPacket();
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
			List<PrpDriskShortRateResInfo> temp = new ArrayList<PrpDriskShortRateResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDriskShortRate prpDriskShortRate = list.get(i);
				PrpDriskShortRateResInfo prpDriskShortRateResInfo = new PrpDriskShortRateResInfo();
				prpDriskShortRateResInfo.setRISKCODE(prpDriskShortRate.getId().getRiskCode());
				prpDriskShortRateResInfo.setSHORTRATEID(prpDriskShortRate.getId().getShortRateID());
				if(null!=prpDriskShortRate.getId().getSerialNo()){
					prpDriskShortRateResInfo.setSERIALNO(prpDriskShortRate.getId().getSerialNo().toString());
				}
			
				prpDriskShortRateResInfo.setSHORTRATENAME(prpDriskShortRate.getShortRateName());
				prpDriskShortRateResInfo.setRATETYPE(prpDriskShortRate.getRateType());
				prpDriskShortRateResInfo.setLOWEROPERATOR(prpDriskShortRate.getLowerOperator());
				if(null!=prpDriskShortRate.getLower()){
					prpDriskShortRateResInfo.setLOWER(prpDriskShortRate.getLower().toString());
				}
				prpDriskShortRateResInfo.setUPPEROPERATOR(prpDriskShortRate.getUpperOperator());
				if(null!=prpDriskShortRate.getUpper()){
					prpDriskShortRateResInfo.setUPPER(prpDriskShortRate.getUpper().toString());
				}
				if(null!=prpDriskShortRate.getShortRateNumerator()){
					prpDriskShortRateResInfo.setSHORTRATENUMERATOR(prpDriskShortRate.getShortRateNumerator().toString());
				}
				if(null!=prpDriskShortRate.getShortRateDenominator()){
					prpDriskShortRateResInfo.setSHORTRATEDENOMINATOR(prpDriskShortRate.getShortRateDenominator().toString());
				}
				
				prpDriskShortRateResInfo.setVALIDIND(prpDriskShortRate.getValidInd());
				prpDriskShortRateResInfo.setFLAG(prpDriskShortRate.getFlag());
				prpDriskShortRateResInfo.setREMARK(prpDriskShortRate.getRemark());
				if(null!=prpDriskShortRate.getValidDate()){
					prpDriskShortRateResInfo.setVALIDDATE(PubFun.DateToStr(prpDriskShortRate.getValidDate()));
				}
				if(null!=prpDriskShortRate.getInvalidDate()){
					prpDriskShortRateResInfo.setINVALIDDATE(PubFun.DateToStr(prpDriskShortRate.getInvalidDate()));
				}
				
				temp.add(prpDriskShortRateResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDRISKSHORTRATELIST().setPRPDRISKSHORTRATE(
					temp.toArray(new PrpDriskShortRateResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDriskShortRateListResPacket responsePacket)
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

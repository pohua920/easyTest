package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDclass;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.findByConditionReqpacket.FindByConditionReqPacket;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDclassIdReqPacket.SendPrpDclassIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDclassListResPacket.PrpDclassListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDclassListResPacket.PrpDclassResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDclassByCondition implements
		DataTransformer<FindByConditionReqPacket, PrpDclassListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDclass> list = new ArrayList<PrpDclass>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		FindByConditionReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDclassById */
		
		//TODO 在数据字典接口中添加方法（findPrpDclassById）
		list = dictionaryService.findPrpDclassByCondition(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getCONDITION());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType =ServiceInfoConst.FINDPRPDCLASSBYCONDITION;// 设置返回报文的requesttype
		// TODO 添加常量定义
//		requestType = "";//TODO 记得删除

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDclassListResPacket responsePacket = new PrpDclassListResPacket();
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
			List<PrpDclassResInfo> temp = new ArrayList<PrpDclassResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDclass prpDclass = list.get(i);
				PrpDclassResInfo prpDclassResInfo = new PrpDclassResInfo();
				prpDclassResInfo.setCLASSCODE(prpDclass.getClassCode());
				prpDclassResInfo.setCLASSCNAME(prpDclass.getClassCName());
				prpDclassResInfo.setCLASSSCNAME(prpDclass.getClassSCName());
				prpDclassResInfo.setCLASSTNAME(prpDclass.getClassTName());
				prpDclassResInfo.setCLASSENAME(prpDclass.getClassEName());
				prpDclassResInfo.setCLASSSENAME(prpDclass.getClassSEName());
				prpDclassResInfo.setCREATORCODE(prpDclass.getCreatorCode());
				if(null!=prpDclass.getCreateTime()){
					prpDclassResInfo.setCREATETIME(PubFun.DateToStr(prpDclass.getCreateTime()));
				}
				prpDclassResInfo.setUPDATERCODE(prpDclass.getUpdaterCode());
				if(null!=prpDclass.getUpdateTime()){
					prpDclassResInfo.setUPDATETIME(PubFun.DateToStr(prpDclass.getUpdateTime()));
				}
				if(null!=prpDclass.getValidDate()){
					prpDclassResInfo.setVALIDDATE(PubFun.DateToStr(prpDclass.getValidDate()));
				}
				if(null!=prpDclass.getInvalidDate()){
					prpDclassResInfo.setINVAIDDATE(PubFun.DateToStr(prpDclass.getInvalidDate()));
				}
				prpDclassResInfo.setVALIDIND(prpDclass.getValidInd());
				prpDclassResInfo.setTCOL1(prpDclass.getTcol1());
				prpDclassResInfo.setTCOL2(prpDclass.getTcol2());
				prpDclassResInfo.setTCOL3(prpDclass.getTcol3());
				prpDclassResInfo.setREMARK(prpDclass.getRemark());
				prpDclassResInfo.setFLAG(prpDclass.getFlag());

				//TODO 将prpDclassresInfo set如上的值
				temp.add(prpDclassResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDCLASSLIST().setPRPDCLASS(
					temp.toArray(new PrpDclassResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDclassListResPacket responsePacket)
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

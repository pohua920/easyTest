package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDitem;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.findByConditionReqpacket.FindByConditionReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDitemListResPacket.PrpDitemListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDitemListResPacket.PrpDitemResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDitemByCondition implements
		DataTransformer<FindByConditionReqPacket, PrpDitemListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDitem> list = new ArrayList<PrpDitem>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		FindByConditionReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDitemById */
		
		//TODO 在数据字典接口中添加方法（findPrpDitemById）
		list = dictionaryService.findPrpDitemByCondition(requestPacket.getHEAD()
				.getSYSTEMCODE(), requestPacket.getBODY().getCONDITION());
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDITEMBYCONDITION;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDitemListResPacket responsePacket = new PrpDitemListResPacket();
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
			List<PrpDitemResInfo> temp = new ArrayList<PrpDitemResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDitem prpDitem = list.get(i);
				PrpDitemResInfo prpDitemResInfo = new PrpDitemResInfo();
				prpDitemResInfo.setITEMCODE(prpDitem.getItemCode());
				prpDitemResInfo.setITEMTYPE(prpDitem.getItemType());
				prpDitemResInfo.setITEMCNAME(prpDitem.getItemCName());
				prpDitemResInfo.setITEMTNAME(prpDitem.getItemTName());
				prpDitemResInfo.setITEMENAME(prpDitem.getItemEName());
				prpDitemResInfo.setCREATORCODE(prpDitem.getCreatorCode());
				if(null!=prpDitem.getCreateTime()){
					prpDitemResInfo.setCREATETIME(PubFun.DateToStr(prpDitem.getCreateTime()));
				}
				prpDitemResInfo.setUPDATERCODE(prpDitem.getUpdaterCode());
				if(null!=prpDitem.getUpdateTime()){
					prpDitemResInfo.setUPDATETIME(PubFun.DateToStr(prpDitem.getUpdateTime()));
				}
				if(null!=prpDitem.getValidDate()){
					prpDitemResInfo.setVALIDDATE(PubFun.DateToStr(prpDitem.getValidDate()));
				}
				if(null!=prpDitem.getInvalidDate()){
					prpDitemResInfo.setINVALIDDATE(PubFun.DateToStr(prpDitem.getInvalidDate()));
				}
				prpDitemResInfo.setVALIDIND(prpDitem.getValidInd());
				prpDitemResInfo.setTCOL1(prpDitem.getTcol1());
				prpDitemResInfo.setTCOL2(prpDitem.getTcol2());
				prpDitemResInfo.setTCOL3(prpDitem.getTcol3());
				prpDitemResInfo.setREMARK(prpDitem.getRemark());
				prpDitemResInfo.setFLAG(prpDitem.getFlag());
				temp.add(prpDitemResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDITEMLIST().setPRPDITEM(
					temp.toArray(new PrpDitemResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDitemListResPacket responsePacket)
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

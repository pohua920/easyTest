package cn.com.sinosoft.inf.PMS.service.transform;

import ins.framework.common.ServiceFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskItemId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskItemIdReqPacket.SendPrpDriskItemIdReqPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskItemListResPacket.PrpDriskItemListResPacket;
import cn.com.sinosoft.inf.PMS.resDomains.prpDriskItemListResPacket.PrpDriskItemResInfo;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.JoxSupport;
import cn.com.sinosoft.inf.dict.util.PubFun;

import com.wutka.jox.JOXBeanInputStream;

public class TransFindPrpDriskItemById implements
		DataTransformer<SendPrpDriskItemIdReqPacket, PrpDriskItemListResPacket> {

	public String execute(String requestxml) throws Exception {
		List<PrpDriskItem> list = new ArrayList<PrpDriskItem>();
		String requestType = "";
		/** 请求的xml报文翻译成Packet对象 */
		SendPrpDriskItemIdReqPacket requestPacket = xmlToSchema(requestxml);
		/*************************************
		 * 调用持久层操作，获得所需数据
		 * **********************************/
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		/** 判断requesttype，确定具体执行查找直接下级代码代码，还是查找当前代码的所有下级代码代码 */
		/** 调用本地接口服务findPrpDriskItemById */
		
		//TODO 在数据字典接口中添加方法（findPrpDriskItemById）
		PrpDriskItemId prpDriskItemId = new PrpDriskItemId();
		prpDriskItemId.setItemCode(requestPacket.getBODY().getITEMCODE());
		prpDriskItemId.setRiskCode(requestPacket.getBODY().getRISKCODE());
		list = dictionaryService.findPrpDriskItemById(requestPacket.getHEAD()
				.getSYSTEMCODE(), prpDriskItemId);
		
		//TODO 在ServiceInfoConst中添加对应接口的代码（FINDPRPDCLASSBYID）
		requestType = ServiceInfoConst.FINDPRPDRISKITEMBYID;// 设置返回报文的requesttype

		/*************************************
		 * 持久层对象转换封装成数据包对象
		 * *******************************/
		PrpDriskItemListResPacket responsePacket = new PrpDriskItemListResPacket();
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
			List<PrpDriskItemResInfo> temp = new ArrayList<PrpDriskItemResInfo>();
			for (int i = 0; i < list.size(); i++) {
				PrpDriskItem prpDriskItem = list.get(i);
				PrpDriskItemResInfo prpDriskItemResInfo = new PrpDriskItemResInfo();
				if(null!=prpDriskItem.getId()){
					prpDriskItemResInfo.setRISKCODE(prpDriskItem.getId().getRiskCode());
					prpDriskItemResInfo.setITEMCODE(prpDriskItem.getId().getItemCode());
				}
				prpDriskItemResInfo.setITEMDETAILCNAME(prpDriskItem.getItemCName());
				prpDriskItemResInfo.setVALIDIND(prpDriskItem.getValidInd());
				prpDriskItemResInfo.setTCOL1(prpDriskItem.getTcol1());
				prpDriskItemResInfo.setTCOL2(prpDriskItem.getTcol2());
				prpDriskItemResInfo.setTCOL3(prpDriskItem.getTcol3());
				prpDriskItemResInfo.setREMARK(prpDriskItem.getRemark());
				prpDriskItemResInfo.setFLAG(prpDriskItem.getFlag());
				if(null!=prpDriskItem.getValidDate()){
					prpDriskItemResInfo.setVALIDDATE(PubFun.DateToStr(prpDriskItem.getValidDate()));
				}
				if(null!=prpDriskItem.getInvalidDate()){
					prpDriskItemResInfo.setINVALIDDATE(PubFun.DateToStr(prpDriskItem.getInvalidDate()));
				}
				temp.add(prpDriskItemResInfo);
			}
			int i = temp.size();
			responsePacket.getBODY().getPRPDRISKITEMLIST().setPRPDRISKITEM(
					temp.toArray(new PrpDriskItemResInfo[i]));
		}
		/***************************
		 * 返回报文对象转换成xml
		 * ***************************/
		String responsexml = schemaToXml(responsePacket);
		return responsexml;
	}

	public String schemaToXml(PrpDriskItemListResPacket responsePacket)
			throws Exception {
		String responsexml = JoxSupport.getInstance().requestConvert(
				responsePacket, responsePacket.getHEAD().getREQUEST_TYPE());
		return responsexml;
	}

	public SendPrpDriskItemIdReqPacket xmlToSchema(String requestxml)
			throws Exception {
		JOXBeanInputStream joxIn = new JOXBeanInputStream(
				new ByteArrayInputStream(requestxml.getBytes(IConstants.ENCODING_UTF8)));
		SendPrpDriskItemIdReqPacket response = (SendPrpDriskItemIdReqPacket) joxIn
				.readObject(SendPrpDriskItemIdReqPacket.class);
		return response;
	}
}

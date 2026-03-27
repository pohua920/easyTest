package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.webservice.facade.DictionaryNewService;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.util.BeanUtilsEx;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.PageResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;

/**
 * mantis：CAR0027，處理人員：DP0706，需求單編號：mantis：CAR0027 : 
 * 	因DMS 正式機上的程式與開發版本有所差異故將DNS查詢方法抽出並另外改寫
 * 	1.繼承 TransServiceImpl 實作DataTransformer
 *  2.改寫execute內容呼叫新程式dictionaryServiceNew.savePrpDcustomerUnitNew
 * @author DP0706
 *
 */
public class TransServiceSubImpl extends TransServiceImpl implements
		DataTransformer<RequestPacket, PageResPacket> {

	public String execute(String requestxml) throws Exception {
		DictPage dictPage = new DictPage();
		PageResPacket pageResPacket = new PageResPacket();
		RequestPacket requestPacket = xmlToSchema(requestxml);
		DictionaryNewService dictionaryServiceNew = (DictionaryNewService) ServiceFactory
				.getService("dictionaryServiceNew");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String requestType = requestPacket.getHEAD().getREQUEST_TYPE();
		Map values = requestPacket.getBODY().getValues();
		// 根據requestType指向不同方法
		if (ServiceInfoConst.SAVEPRPDCUSTOMERUNITNEW.equals(requestType)) {
			System.out.println("-----------進入單位保存NEW方法------------");
			dictPage = dictionaryServiceNew.savePrpDcustomerUnitNew(systemCode, values);
		}
		// 增加新接口，就要增加分发，通过if else 分开
		/***************** 继续分发，根据不同的requestType发往不同的持久方法获取数据****end ***/
		ResponseHeadSchema head = MessageUtil.setHeadMessage(
				ServiceInfoConst.ERRORCODE_SUCCESS,
				ServiceInfoConst.ERRORMSG_SUCCESS, requestType,
				ServiceInfoConst.RESPONSECODE_SUCCESS);
		pageResPacket.setHEAD(head);
		pageResPacket.setBODY(dictPage);
		String responsexml = schemaToXml(pageResPacket);
		return responsexml;
	}

}

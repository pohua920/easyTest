package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKind;
import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngage;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngageId;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRateId;
import cn.com.sinosoft.dms.model.PrpDRationEngage;
import cn.com.sinosoft.dms.model.PrpDRationEngageId;
import cn.com.sinosoft.dms.model.PrpDRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDRationPeriodRateId;
import cn.com.sinosoft.dms.model.PrpDplanClause;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDrationLimit;
import cn.com.sinosoft.dms.model.PrpDrationLimitId;
import cn.com.sinosoft.dms.model.PrpDrationShortrate;
import cn.com.sinosoft.dms.model.PrpDrationShortrateId;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpdChannelCoins;
import cn.com.sinosoft.dms.model.PrpdChannelCoinsId;
import cn.com.sinosoft.dms.model.PrpdChannelInfo;
import cn.com.sinosoft.dms.model.PrpdChannelInfoId;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.GetPlanInfoReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.GetPlanInfoReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.GetPlanInfoResBody;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.GetPlanInfoResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.TranslateObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.RationObj;

import com.thoughtworks.xstream.XStream;


public class GetPlanInfoServiceImpl implements
		DataTransformer<GetPlanInfoReqPacket, GetPlanInfoResPacket> {

	public String execute(String requestxml) throws Exception {
		GetPlanInfoResPacket resPacket = new GetPlanInfoResPacket();
		GetPlanInfoReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		PrpDration prpDration = requestPacket.getBODY().getPrpDration();
		RationObj rationObj = dictionaryService.getRationInfo(systemCode, prpDration);
		ResponseHeadSchema head = MessageUtil
				.setHeadMessage(ServiceInfoConst.ERRORCODE_SUCCESS,
						ServiceInfoConst.ERRORMSG_SUCCESS,
						ServiceInfoConst.GETPLANINFO,
						ServiceInfoConst.RESPONSECODE_SUCCESS);	
		resPacket.setHEAD(head);
		resPacket.getBODY().setRationObj(rationObj);
		String responsexml = schemaToXml(resPacket);
		return responsexml;
	}

	public GetPlanInfoReqPacket xmlToSchema(String requestxml) throws Exception {
		if (requestxml == null || "".equals(requestxml)) {
			throw new Exception("报文不可以为空");
		}
		XStream xs = new XStream();
		xs.alias("GetPlanInfoReqPacket", GetPlanInfoReqPacket.class);
		xs.alias("RequestHeadPacket", RequestHeadPacket.class);
		xs.alias("GetPlanInfoReqBody", GetPlanInfoReqBody.class);
		xs.alias("PrpDration", cn.com.sinosoft.dms.model.PrpDration.class);
		xs.alias("PrpDrisk", PrpDrisk.class);
		xs.alias("PrpDrationClauseKind", PrpDrationClauseKind.class);
		xs.alias("PrpDrationClauseKindId", PrpDrationClauseKindId.class);
		xs.alias("PrpDrationLimit", PrpDrationLimit.class);
		xs.alias("PrpDrationLimitId", PrpDrationLimitId.class);
		xs.alias("PrpDRationEngage", PrpDRationEngage.class);
		xs.alias("PrpDRationEngageId", PrpDRationEngageId.class);
		xs.alias("PrpDrationShortrate", PrpDrationShortrate.class);
		xs.alias("PrpDrationShortrateId", PrpDrationShortrateId.class);
		xs.alias("PrpdChannelInfo", PrpdChannelInfo.class);// modify update by wpf
		xs.alias("PrpdChannelInfoId", PrpdChannelInfoId.class);// modify update by wpf
		GetPlanInfoReqPacket ep = (GetPlanInfoReqPacket) xs.fromXML(requestxml);
		return ep;
	}
	
	public String schemaToXml(GetPlanInfoResPacket responsePacket) throws Exception {
		XStream xs = new XStream();
		xs.alias("GetPlanInfoResPacket", GetPlanInfoResPacket.class);
		xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		xs.alias("GetPlanInfoResBody", GetPlanInfoResBody.class);
		xs.alias("RationObj", RationObj.class);
		xs.alias("PrpDration", PrpDration.class);
		xs.alias("PrpDrationClauseKind", PrpDrationClauseKind.class);
		xs.alias("PrpDRationEngage", PrpDRationEngage.class);
		xs.alias("PrpDRationEngageId", PrpDRationEngageId.class);
		xs.alias("PrpDplanClause", PrpDplanClause.class);
		xs.alias("PrpDrationLimit", PrpDrationLimit.class);
		xs.alias("PrpDrationShortrate", PrpDrationShortrate.class);// modify update by renshuo 2011-08-01 reason:增加短期费率
		xs.alias("PrpDrationShortrateId", PrpDrationShortrateId.class);
		xs.alias("PrpdChannelInfo", PrpdChannelInfo.class);// modify update by wpf
		xs.alias("PrpdChannelInfoId", PrpdChannelInfoId.class);// modify update by wpf
		xs.alias("PrpdChannelCoins", PrpdChannelCoins.class);// modify update by wpf
		xs.alias("PrpdChannelCoinsId", PrpdChannelCoinsId.class);// modify update by wpf
		xs.alias("PrpDRationPeriodRate", PrpDRationPeriodRate.class);
		xs.alias("PrpDRationPeriodRateId", PrpDRationPeriodRateId.class);
		xs.alias("PrpDChannelRationEngage", PrpDChannelRationEngage.class);
		xs.alias("PrpDChannelRationEngageId", PrpDChannelRationEngageId.class);
		xs.alias("PrpDChannelRationClauseKind", PrpDChannelRationClauseKind.class);
		xs.alias("PrpDChannelRationClauseKindId", PrpDChannelRationClauseKindId.class);
		xs.alias("PrpDChannelRationPeriodRate", PrpDChannelRationPeriodRate.class);
		xs.alias("PrpDChannelRationPeriodRateId", PrpDChannelRationPeriodRateId.class);
		xs.alias("TranslateObj", TranslateObj.class);
		/***********************************************/
		String responsexml = xs.toXML(responsePacket);
		return responsexml;
	}

}

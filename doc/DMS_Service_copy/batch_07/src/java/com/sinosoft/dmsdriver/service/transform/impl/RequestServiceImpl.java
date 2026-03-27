package com.sinosoft.dmsdriver.service.transform.impl;


import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDstartPlace;
import cn.com.sinosoft.dms.model.PrpDtype;

import com.sinosoft.dmsdriver.domain.agentSYN.AgentSYNObj;
import com.sinosoft.dmsdriver.domain.common.PageResPacket;
import com.sinosoft.dmsdriver.domain.common.RequestBodySchema;
import com.sinosoft.dmsdriver.domain.common.RequestHeadSchema;
import com.sinosoft.dmsdriver.domain.common.RequestPacket;
import com.sinosoft.dmsdriver.domain.common.ResponseHeadSchema;
import com.sinosoft.dmsdriver.domain.getCode.PrpDcodeInfo;
import com.sinosoft.dmsdriver.model.IPServiceConfig;
import com.sinosoft.dmsdriver.model.IPServiceConfigId;
import com.sinosoft.dmsdriver.model.PrintType;
import com.sinosoft.dmsdriver.model.PrpDaccountInfo;
import com.sinosoft.dmsdriver.model.PrpDagent;
import com.sinosoft.dmsdriver.model.PrpDclause;
import com.sinosoft.dmsdriver.model.PrpDclauseKind;
import com.sinosoft.dmsdriver.model.PrpDclauseKindId;
import com.sinosoft.dmsdriver.model.PrpDclauseReport;
import com.sinosoft.dmsdriver.model.PrpDclauseReportId;
import com.sinosoft.dmsdriver.model.PrpDcoins;
import com.sinosoft.dmsdriver.model.PrpDcoinsId;
import com.sinosoft.dmsdriver.model.PrpDcustomer;
import com.sinosoft.dmsdriver.model.PrpDcustomerFXQ;
import com.sinosoft.dmsdriver.model.PrpDcustomerFine;
import com.sinosoft.dmsdriver.model.PrpDcustomerFineId;
import com.sinosoft.dmsdriver.model.PrpDcustomerIdv;
import com.sinosoft.dmsdriver.model.PrpDcustomerRelation;
import com.sinosoft.dmsdriver.model.PrpDcustomerUnit;
import com.sinosoft.dmsdriver.model.PrpDkind;
import com.sinosoft.dmsdriver.model.PrpDkindCar;
import com.sinosoft.dmsdriver.model.PrpDkindCarId;
import com.sinosoft.dmsdriver.model.PrpDkindId;
import com.sinosoft.dmsdriver.model.PrpDkindProduct;
import com.sinosoft.dmsdriver.model.PrpDkindProductId;
import com.sinosoft.dmsdriver.model.PrpDkindReport;
import com.sinosoft.dmsdriver.model.PrpDkindReportId;
import com.sinosoft.dmsdriver.model.PrpDrationCondition;
import com.sinosoft.dmsdriver.model.PrpDrationRelation;
import com.sinosoft.dmsdriver.model.PrpDrationRelationId;
import com.sinosoft.dmsdriver.model.PrpDregulationVo;
import com.sinosoft.dmsdriver.model.PrpDreinsurer;
import com.sinosoft.dmsdriver.model.PrpDrisk;
import com.sinosoft.dmsdriver.model.PrpDriskClause;
import com.sinosoft.dmsdriver.model.PrpDriskClauseId;
import com.sinosoft.dmsdriver.model.PrpDriskClauseKind;
import com.sinosoft.dmsdriver.model.PrpDriskClauseKindId;
import com.sinosoft.dmsdriver.model.PrpDriskClauseKindRelation;
import com.sinosoft.dmsdriver.model.PrpDriskClauseKindRelationId;
import com.sinosoft.dmsdriver.model.PrpDriskEngage;
import com.sinosoft.dmsdriver.model.PrpDriskEngageId;
import com.sinosoft.dmsdriver.model.PrpDriskItem;
import com.sinosoft.dmsdriver.model.PrpDriskItemId;
import com.sinosoft.dmsdriver.model.PrpDriskLimit;
import com.sinosoft.dmsdriver.model.PrpDriskLimitId;
import com.sinosoft.dmsdriver.model.PrpDset;
import com.sinosoft.dmsdriver.model.PrpDsetChannel;
import com.sinosoft.dmsdriver.model.PrpDsetChannelId;
import com.sinosoft.dmsdriver.model.PrpDsetRationrelation;
import com.sinosoft.dmsdriver.model.PrpDsetRationrelationId;
import com.sinosoft.dmsdriver.model.PrpDsetRenewal;
import com.sinosoft.dmsdriver.model.PrpDsetRenewalId;
import com.sinosoft.dmsdriver.model.PrpDsettlementByr;
import com.sinosoft.dmsdriver.model.PrpDsettlementLkr;
import com.sinosoft.dmsdriver.model.PrpDtreatyReten;
import com.sinosoft.dmsdriver.model.PrpDtreatyRetenId;
import com.sinosoft.dmsdriver.model.PrpYDDagent;
import com.sinosoft.dmsdriver.model.PrpdRationRate;
import com.sinosoft.dmsdriver.model.PrpdRationRateId;
import com.sinosoft.dmsdriver.service.common.DataTransformer;
import com.sinosoft.dmsdriver.service.common.DictPage;
import com.sinosoft.dmsdriver.service.transform.RequestService;
import com.sinosoft.dmsdriver.util.BpsSupport;
import com.thoughtworks.xstream.XStream;

public class RequestServiceImpl
  implements RequestService, DataTransformer<RequestPacket, PageResPacket>
{
  public PageResPacket execute(RequestPacket request)
    throws Exception
  {
    String requestXml = requestToXml(request);
    String responseXml = BpsSupport.getInstance().execute(requestXml);
    PageResPacket response = xmlToResponse(responseXml);
    return response;
  }
  public String requestToXml(RequestPacket request) throws Exception {
    XStream xstream = new XStream();
    xstream.alias("RequestPacket", RequestPacket.class);
    xstream.alias("RequestHeadSchema", RequestHeadSchema.class);
    xstream.alias("RequestBodySchema", RequestBodySchema.class);
    String responsexml = xstream.toXML(request);
    return responsexml;
  }

  public PageResPacket xmlToResponse(String xml) throws Exception {
    XStream xs = new XStream();
    xs.alias("PageResPacket", PageResPacket.class);
    xs.alias("ResponseHeadSchema", ResponseHeadSchema.class);
    xs.alias("DictPage", DictPage.class);

    xs.alias("PrpDrisk", PrpDrisk.class);
    xs.alias("PrpDtreatyReten", PrpDtreatyReten.class);
    xs.alias("PrpDtreatyRetenId", PrpDtreatyRetenId.class);
    xs.alias("PrintType", PrintType.class);
    xs.alias("IPServiceConfig", IPServiceConfig.class);
    xs.alias("IPServiceConfigId", IPServiceConfigId.class);
    xs.alias("PrpDagent", PrpDagent.class);
    xs.alias("PrpYDDagent", PrpYDDagent.class);
    xs.alias("PrpDriskClause", PrpDriskClause.class);
    xs.alias("PrpDriskClauseId", PrpDriskClauseId.class);
	//added by yuyiqiang 20132025 begin 对应prpdkind类转换
	xs.alias("PrpDkind", PrpDkind.class);
	xs.alias("PrpDkindId", PrpDkindId.class);
	//added by yuyiqiang 20130225 end
	// added by wanglianzhou 20130409 begin  个人单位关联体信息查询
	xs.alias("PrpDcustomer", PrpDcustomer.class);
	xs.alias("PrpDcustomerFine", PrpDcustomerFine.class);
	xs.alias("PrpDcustomerFineId", PrpDcustomerFineId.class);
	xs.alias("PrpDcustomerFXQ", PrpDcustomerFXQ.class);
	xs.alias("PrpDcustomerIdv", PrpDcustomerIdv.class);
	xs.alias("PrpDcustomerRelation", PrpDcustomerRelation.class);
	xs.alias("PrpDcustomerUnit", PrpDcustomerUnit.class);
	// added by wanglianzhou 20130409 end
	xs.alias("PrpDclauseKindId", PrpDclauseKindId.class);
	xs.alias("PrpDclauseKind", PrpDclauseKind.class);
	//and by xuli 20130623
	xs.alias("PrpDkindReport", PrpDkindReport.class);
	xs.alias("PrpDkindReportId", PrpDkindReportId.class);
    xs.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
    xs.alias("PrpDriskClauseKindId", PrpDriskClauseKindId.class);
    xs.alias("PrpDkindProduct", PrpDkindProduct.class);
    xs.alias("PrpDkindProductId", PrpDkindProductId.class);
    //add by zhongjiang begin
    xs.alias("PrpDkindCar",PrpDkindCar.class);
    xs.alias("PrpDkindCarId", PrpDkindCarId.class);
    //add by zhongjiang end 
    xs.alias("PrpDcodeInfo", PrpDcodeInfo.class);
    xs.alias("PrpDaccountInfo", PrpDaccountInfo.class);
    xs.alias("PrpDreinsurer", PrpDreinsurer.class);
    xs.alias("PrpDcoins", PrpDcoins.class);
    xs.alias("PrpDcoinsId", PrpDcoinsId.class);
    xs.alias("PrpDriskLimit", PrpDriskLimit.class);
    xs.alias("PrpDriskLimitId", PrpDriskLimitId.class);
    xs.alias("AgentSYNObj", AgentSYNObj.class);
    xs.alias("PrpDsettlementLkr", PrpDsettlementLkr.class);
    xs.alias("PrpDsettlementByr", PrpDsettlementByr.class);
    xs.alias("PrpDriskItem", PrpDriskItem.class);
    xs.alias("PrpDriskItemId", PrpDriskItemId.class);
    xs.alias("PrpDriskLimit", PrpDriskLimit.class);
    xs.alias("PrpDriskLimitId", PrpDriskLimitId.class);
    xs.alias("PrpDriskEngage", PrpDriskEngage.class);
    xs.alias("PrpDriskEngageId", PrpDriskEngageId.class);
    
    /*xs.alias("PrpDcustomerFXQ", PrpDcustomerFXQ.class);*/
    
    xs.alias("PrpDregulationVo", PrpDregulationVo.class);

    xs.alias("PrpDriskClauseKindRelationId", PrpDriskClauseKindRelationId.class);
    xs.alias("PrpDriskClauseKindRelation", PrpDriskClauseKindRelation.class);

    xs.alias("PrpdRationRate", PrpdRationRate.class);
    xs.alias("PrpdRationRateId", PrpdRationRateId.class);
    //add by cuishang 20140305 start
    xs.alias("PrpDclauseReport", PrpDclauseReport.class);
    xs.alias("PrpDclauseReportId", PrpDclauseReportId.class);
    
    /*xs.alias("PrpDrationRelation", PrpDrationRelation.class);
    xs.alias("PrpDrationRelationId", PrpDrationRelationId.class);
    xs.alias("PrpDrationClauseKind", PrpDrationClauseKind.class);
    xs.alias("PrpDrationClauseKindId", PrpDrationClauseKindId.class);
    //add by cuishang 20140305 end
    //add by fengyang 20140401
    xs.alias("PrpDrationCondition", PrpDrationCondition.class);
    //add by fengyang 20140402
    xs.alias("PrpDset", PrpDset.class);
    xs.alias("PrpDsetRationrelation", PrpDsetRationrelation.class);
    xs.alias("PrpDsetRationrelationId", PrpDsetRationrelationId.class);
    xs.alias("PrpDsetRenewal", PrpDsetRenewal.class);
    xs.alias("PrpDsetRenewalId", PrpDsetRenewalId.class);
    xs.alias("PrpDsetChannel", PrpDsetChannel.class);
    xs.alias("PrpDsetChannelId", PrpDsetChannelId.class);*/
    //add by yjm 伤害险险种详细信息查询（通报用） 20150729 start
    xs.alias("PrpDclause", PrpDclause.class);
    //add by yjm 伤害险险种详细信息查询（通报用） 20150729 end
    //add by yjm 20150331 特約 start
    xs.alias("PrpDriskEngage", PrpDriskEngage.class);
    xs.alias("PrpDriskEngageId", PrpDriskEngageId.class);
    xs.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
    xs.alias("PrpDriskClauseKindId", PrpDriskClauseKindId.class);
    //add by yjm 20150331 特約 end
    //add by liuyang  港口代碼維護 start
    xs.alias("PrpDstartPlace", PrpDstartPlace.class);
    //add by liuyang  港口代碼維護 end
    PageResPacket pageResPacket = (PageResPacket)xs.fromXML(xml, new PageResPacket());
    return pageResPacket;
  }
}
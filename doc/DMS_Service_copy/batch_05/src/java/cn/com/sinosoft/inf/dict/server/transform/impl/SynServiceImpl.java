package cn.com.sinosoft.inf.dict.server.transform.impl;

import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKind;
import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngage;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngageId;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRateId;
import cn.com.sinosoft.dms.model.PrpDRCKRateLower;
import cn.com.sinosoft.dms.model.PrpDRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDaccountInfo;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDagentExt;
import cn.com.sinosoft.dms.model.PrpDagentExtId;
import cn.com.sinosoft.dms.model.PrpDarea;
import cn.com.sinosoft.dms.model.PrpDareaId;
import cn.com.sinosoft.dms.model.PrpDclass;
import cn.com.sinosoft.dms.model.PrpDcontractManage;
import cn.com.sinosoft.dms.model.PrpDcontractManageId;
import cn.com.sinosoft.dms.model.PrpDframe;
import cn.com.sinosoft.dms.model.PrpDnewCodeRisk;
import cn.com.sinosoft.dms.model.PrpDnewCodeRiskId;
import cn.com.sinosoft.dms.model.PrpDplan;
import cn.com.sinosoft.dms.model.PrpDplanClause;
import cn.com.sinosoft.dms.model.PrpDplanClauseKind;
import cn.com.sinosoft.dms.model.PrpDplanLimit;
import cn.com.sinosoft.dms.model.PrpDrationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDrationCondition;
import cn.com.sinosoft.dms.model.PrpDrationConditionId;
import cn.com.sinosoft.dms.model.PrpDrationShortrateId;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpDriskClause;
import cn.com.sinosoft.dms.model.PrpDriskClauseId;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindMinPremium;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindMinPremiumId;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindRelation;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindRelationId;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskItemId;
import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskLimitId;
import cn.com.sinosoft.dms.model.PrpDriskMinPremium;
import cn.com.sinosoft.dms.model.PrpDriskMinPremiumId;
import cn.com.sinosoft.dms.model.PrpDriskShortRate;
import cn.com.sinosoft.dms.model.PrpDriskShortRateId;
import cn.com.sinosoft.dms.model.PrpDset;
import cn.com.sinosoft.dms.model.PrpDsetChannel;
import cn.com.sinosoft.dms.model.PrpDsetChannelId;
import cn.com.sinosoft.dms.model.PrpDsetRationrelation;
import cn.com.sinosoft.dms.model.PrpDsetRationrelationId;
import cn.com.sinosoft.dms.model.PrpDsetRenewal;
import cn.com.sinosoft.dms.model.PrpDsetRenewalId;
import cn.com.sinosoft.dms.model.PrpYDDagent;
import cn.com.sinosoft.dms.model.PrpdChannelCoins;
import cn.com.sinosoft.dms.model.PrpdChannelCoinsId;
import cn.com.sinosoft.dms.model.PrpdChannelInfo;
import cn.com.sinosoft.dms.model.PrpdChannelInfoId;
import cn.com.sinosoft.dms.model.SaOldAgent;
import cn.com.sinosoft.dms.model.SaOldAgentId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.dms.model.PrpDRationEngage;
import cn.com.sinosoft.dms.model.PrpDclauseReport;
import cn.com.sinosoft.dms.model.PrpDclauseReportId;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationLimit;
import cn.com.sinosoft.dms.model.PrpDrationRelation;
import cn.com.sinosoft.dms.model.PrpDrationRelationId;
import cn.com.sinosoft.dms.model.PrpDrationShortrate;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.ClauseReportObj;


import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.agentSYN.AgentSYNObj;
import cn.com.sinosoft.inf.dict.xmlmsg.agentSYN.SYNReqBody;
import cn.com.sinosoft.inf.dict.xmlmsg.agentSYN.SYNReqPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.agentSYN.SYNResPacket;
import cn.com.sinosoft.inf.dict.xmlmsg.common.MessageUtil;
import cn.com.sinosoft.inf.dict.xmlmsg.common.RequestHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.common.ResponseHeadSchema;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.ClassObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.FrameObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.ProductSetObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.RationObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.RiskObj;


import cn.com.sinosoft.dms.model.PrpDRationEngage;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationLimit;
import cn.com.sinosoft.dms.model.PrpDrationShortrate;


import com.thoughtworks.xstream.XStream;

public class SynServiceImpl extends GenericDaoHibernate implements DataTransformer<SYNReqPacket, SYNResPacket> {

	public String execute(String requestxml) throws Exception {
		SYNResPacket synResPacket = new SYNResPacket();
		SYNReqPacket requestPacket = xmlToSchema(requestxml);
		DictionaryService dictionaryService = (DictionaryService) ServiceFactory
				.getService("dictionaryService");// 获得Spring管理的bean
		String systemCode = requestPacket.getHEAD().getSYSTEMCODE();
		String requestType = requestPacket.getHEAD().getREQUEST_TYPE();
		Object data = requestPacket.getBODY().getData();
		String resMessage = "";
		ResponseHeadSchema head = null;
		/*************** 继续分发，根据不同的requestType发往不同的持久方法获取数据****start **/

		try {
			if (ServiceInfoConst.SYNCHRORISKDATA.equals(requestType)) {
				resMessage = dictionaryService.synShortRiskData(systemCode,
						data);
			} else if (ServiceInfoConst.SYNCHROFRAMEDATA.equals(requestType)) {
				resMessage = dictionaryService.synFrameDataData(systemCode,
						data);
			} else if (ServiceInfoConst.SYNCHROPLANDATA.equals(requestType)) {
				resMessage = dictionaryService.synPlanData(systemCode, data);
			} else if (ServiceInfoConst.SYNCHROCLASSDATA.equals(requestType)) {
				resMessage = dictionaryService.synClassData(systemCode, data);
			} else if(ServiceInfoConst.SYNCHROAGENTDATA.equals(requestType)){
				resMessage = dictionaryService.synPrpDAgentData(systemCode, data);
			}//modify begin add by guyanqing 2011-09-28 reason: 增加产品废止、条款废止、产品修订
			else if(ServiceInfoConst.SYNCHROREVISERISKDATA.equals(requestType)){//产品废止
				System.out.println("dms调用产品废止------------------------------");
				dictionaryService.synReviseRiskData(data);
			}else if(ServiceInfoConst.SYNCHROREVISECLAUSEDATA.equals(requestType)){//条款废止
				System.out.println("dms调用条款废止------------------------------");
				dictionaryService.synReviseClauseData(data);
			}else if(ServiceInfoConst.SYNCHROMODIFYRISKDATA.equals(requestType)){//产品修订
				System.out.println("dms调用产品修订------------------------------");
				dictionaryService.synModiyRiskData(data);
			}else if(ServiceInfoConst.SYNCHROMODIFYCLAUSEREPORTDATA.equals(requestType)){//条款备案号同步
				System.out.println("dms调用条款备案------------------------------");
				dictionaryService.synClauseReportData(data);
			}
			else if(ServiceInfoConst.SYNCHROPRODUCTSETDATA.equals(requestType)){//add by fengyang 20140402:套装商品信息同步
				System.out.println("dms调用套装商品------------------------------");
				dictionaryService.synProductSetData(systemCode,data);
			}
			//modify begin add by guyanqing 2011-09-28 reason: 增加产品废止、条款废止、产品修订
			head = MessageUtil.setHeadMessage(
					ServiceInfoConst.ERRORCODE_SUCCESS, ServiceInfoConst.ERRORMSG_SUCCESS,
					requestType, ServiceInfoConst.RESPONSECODE_SUCCESS);
		} catch (UndeclaredThrowableException e){
			resMessage = "0" + e.getCause().getMessage();
			head = MessageUtil.setHeadMessage(
					ServiceInfoConst.ERRORCODE_FAIL, ServiceInfoConst.ERRORMSG_FAIL,
					requestType, ServiceInfoConst.RESPONSECODE_FAIL);
			e.printStackTrace();
		} catch (Exception e) {
			resMessage = "0" + mergeExceptionMessage(e);
			head = MessageUtil.setHeadMessage(
					ServiceInfoConst.ERRORCODE_FAIL, ServiceInfoConst.ERRORMSG_FAIL,
					requestType, ServiceInfoConst.RESPONSECODE_FAIL);
			e.printStackTrace();
		}
		// 增加新接口，就要增加分发，通过if else 分开
		/***************** 继续分发，根据不同的requestType发往不同的持久方法获取数据****end ***/
		synResPacket.setHEAD(head);
		synResPacket.setBODY(resMessage);
		String responsexml = schemaToXml(synResPacket);
		return responsexml;
	}

	public String schemaToXml(SYNResPacket responsePacket) throws Exception {
		XStream xstream = new XStream();

		xstream.alias("SYNResPacket", SYNResPacket.class);
		xstream.alias("ResponseHeadSchema", ResponseHeadSchema.class);
		String responsexml = xstream.toXML(responsePacket);
		return responsexml;
	}

	public SYNReqPacket xmlToSchema(String requestxml) throws Exception {
		XStream xs = new XStream();

		xs.alias("SYNReqPacket", SYNReqPacket.class);
		xs.alias("RequestHeadSchema", RequestHeadSchema.class);
		xs.alias("SYNReqBody", SYNReqBody.class);
		xs.alias("PrpDrisk", PrpDrisk.class);
		xs.alias("ClassObj", ClassObj.class);
		xs.alias("FrameObj", FrameObj.class);
		//xs.alias("PlanObj", PlanObj.class);
		xs.alias("RiskObj", RiskObj.class);
		xs.alias("RationObj", RationObj.class);
		xs.alias("PrpDration", PrpDration.class);
		xs.alias("PrpDrationClauseKind", PrpDrationClauseKind.class);
		xs.alias("PrpDrationLimit", PrpDrationLimit.class);
		xs.alias("PrpDRationEngage", PrpDRationEngage.class);
		xs.alias("PrpDrationShortrate", PrpDrationShortrate.class);
		/**  add by wpf  2012-04-28 begin reason: 方案个性信息  */
		xs.alias("PrpDChannelRationEngage", PrpDChannelRationEngage.class);
		xs.alias("PrpDChannelRationEngageId", PrpDChannelRationEngageId.class);
		xs.alias("PrpDChannelRationClauseKind", PrpDChannelRationClauseKind.class);
		xs.alias("PrpDChannelRationClauseKindId", PrpDChannelRationClauseKindId.class);
		xs.alias("PrpdChannelCoins", PrpdChannelCoins.class);
		xs.alias("PrpdChannelCoinsId", PrpdChannelCoinsId.class);
		xs.alias("PrpDRationPeriodRate", PrpDRationPeriodRate.class);
		xs.alias("PrpDRationPeriodRateId", PrpDrationShortrateId.class);
		xs.alias("PrpdChannelInfo", PrpdChannelInfo.class);
		xs.alias("PrpdChannelInfoId", PrpdChannelInfoId.class);
		xs.alias("PrpDChannelRationPeriodRate", PrpDChannelRationPeriodRate.class);
		xs.alias("PrpDChannelRationPeriodRateId", PrpDChannelRationPeriodRateId.class);
		/**  add by wpf  2012-04-28 end reason: 方案个性信息  */
		xs.alias("PrpDclass", PrpDclass.class);
		xs.alias("PrpDaccountInfo", PrpDaccountInfo.class);
		xs.alias("PrpDarea", PrpDarea.class);
		xs.alias("PrpDareaId", PrpDareaId.class);
		xs.alias("PrpDframe", PrpDframe.class);
		xs.alias("PrpDplan", PrpDplan.class);
		xs.alias("PrpDplanClauseKind", PrpDplanClauseKind.class);
		xs.alias("PrpDplanClause", PrpDplanClause.class);
		xs.alias("PrpDplanLimit", PrpDplanLimit.class);
		xs.alias("PrpDriskClause", PrpDriskClause.class);
		xs.alias("PrpDriskClauseId", PrpDriskClauseId.class);
		xs.alias("PrpDriskClauseKind", PrpDriskClauseKind.class);
		xs.alias("PrpDriskClauseKindId", PrpDriskClauseKindId.class);
		xs.alias("PrpDriskShortRate", PrpDriskShortRate.class);
		xs.alias("PrpDriskShortRateId", PrpDriskShortRateId.class);
		xs.alias("PrpDriskItem", PrpDriskItem.class);
		xs.alias("PrpDriskItemId", PrpDriskItemId.class);
		xs.alias("PrpDriskLimit", PrpDriskLimit.class);
		xs.alias("PrpDriskLimitId", PrpDriskLimitId.class);
		xs.alias("PrpDriskEngage", PrpDriskEngage.class);
		xs.alias("PrpDriskEngageId", PrpDriskEngageId.class);
		xs.alias("PrpDriskClauseKindRelation",PrpDriskClauseKindRelation.class);
		xs.alias("PrpDriskClauseKindRelationId",PrpDriskClauseKindRelationId.class);
		xs.alias("PrpDriskMinPremium", PrpDriskMinPremium.class);
		xs.alias("PrpDriskMinPremiumId", PrpDriskMinPremiumId.class);
		xs.alias("PrpDriskClauseKindMinPremium",PrpDriskClauseKindMinPremium.class);
		xs.alias("PrpDriskClauseKindMinPremiumId",PrpDriskClauseKindMinPremiumId.class);
		xs.alias("PrpDnewCodeRisk", PrpDnewCodeRisk.class);
		xs.alias("PrpDnewCodeRiskId", PrpDnewCodeRiskId.class);
		xs.alias("AgentSYNObj", AgentSYNObj.class);
		xs.alias("PrpDagent", PrpDagentAll.class);
		xs.alias("PrpYDDagent", PrpYDDagent.class);
		xs.alias("PrpDagentExt", PrpDagentExt.class);
		xs.alias("PrpDagentExtId", PrpDagentExtId.class);
		xs.alias("PrpDcontractManage", PrpDcontractManage.class);
		xs.alias("PrpDcontractManageId", PrpDcontractManageId.class);
		xs.alias("SaOldAgent", SaOldAgent.class);
		xs.alias("SaOldAgentId", SaOldAgentId.class);
		xs.alias("PrpDRCKRateLower", PrpDRCKRateLower.class);
		xs.alias("ClauseReportObj", ClauseReportObj.class);							
		xs.alias("PrpDclauseReport", PrpDclauseReport.class);							
		xs.alias("PrpDclauseReportId", PrpDclauseReportId.class);							
		xs.alias("PrpDrationRelation", PrpDrationRelation.class);
		xs.alias("PrpDrationRelationId", PrpDrationRelationId.class);
		xs.alias("PrpDrationCondition", PrpDrationCondition.class);
		xs.alias("PrpDrationConditionId", PrpDrationConditionId.class);
		xs.alias("ProductSetObj", ProductSetObj.class);
	    xs.alias("PrpDset", PrpDset.class);
        xs.alias("PrpDsetRationrelation", PrpDsetRationrelation.class);
        xs.alias("PrpDsetRationrelationId", PrpDsetRationrelationId.class);
	    xs.alias("PrpDsetRenewal", PrpDsetRenewal.class);
        xs.alias("PrpDsetRenewalId", PrpDsetRenewalId.class);
	    xs.alias("PrpDsetChannel", PrpDsetChannel.class);
	    xs.alias("PrpDsetChannelId", PrpDsetChannelId.class);
	    xs.alias("PrpDrationClauseKindId", PrpDrationClauseKindId.class);

		SYNReqPacket ep = (SYNReqPacket) xs.fromXML(requestxml,new SYNReqPacket());
		return ep;
	}
	
	private String mergeExceptionMessage(Throwable e){
		StringBuffer result = new StringBuffer(256);
		String currException = e.toString();
		result.append(currException);
		if(e.getCause()!=null){
			result.append(" >>>> "+mergeExceptionMessage(e.getCause()));
		} 
		return result.toString();
	}
	
	/**
	 * 针对于险种信息接口 qingFenSynClassData  调用如下方法进行清分操作
	 * @param prpdClass
	 */
	public void qingFenSynClassData(List classList){
	
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			String onlineCom = ReadProperties.getString("onlineCom");
			String code = "";
			String[] strOnlineCom = onlineCom.split(",");
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
			.getService("checkSameKeyService");// 获得Spring管理的bean
			Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			UtiISyncLog utiISyncLog = null;
			//日志记录PrpDarea相关保存信息
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDclassMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode("同步险种数据");
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					for(int i=0;i<classList.size();i++){
						PrpDclass prpDclass = (PrpDclass)classList.get(i);
						code = code +"'"+prpDclass.getClassCode()+"',";
					}
					utiISyncLog.setStrKey("classCode in ('" + code.substring(0,(code.length()-1))+ "')");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			//针对于同步险种进行清分
			if (utiISyncLogList.size() > 0) {			
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				.getService("utiISyncLogService");// 获得Spring管理的bean
				utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDclassMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				inputBean.setPrpDclassList(classList);
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				messageProducer.send(inputBean);
			}		
			}
		}
	/**
	 * 针对于方案信息接口 qingFenSynPlanData  调用如下方法进行清分操作
	 * @param prpdClass
	 */			
	public void qingFenSynPlanData(List planList,List planClauseKindList,List planLimitList){
	
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			String onlineCom = ReadProperties.getString("onlineCom");
			String[] strOnlineCom = onlineCom.split(",");
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
			.getService("checkSameKeyService");// 获得Spring管理的bean
			Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			UtiISyncLog utiISyncLog = null;
			//日志记录PrpDplan相关保存信息
				for (String comCode : strOnlineCom) {	
					String code = "";
					String plankindcode = "";
					String planlimitcode = "";
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDplanMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode("同步方案数据");
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					for(int i=0;i<planList.size();i++){
						PrpDplan prpDplan = (PrpDplan)planList.get(i);
						code = code +"'"+prpDplan.getPlanCode()+"',";
					}
					for(int i=0;i<planClauseKindList.size();i++){
						PrpDplanClauseKind kind = (PrpDplanClauseKind)planClauseKindList.get(i);
						plankindcode = plankindcode +"'"+kind.getId().getPlanCode()+"',";
					}
					for(int i=0;i<planLimitList.size();i++){
						PrpDplanLimit limit = (PrpDplanLimit)planLimitList.get(i);
						planlimitcode = planlimitcode +"'"+limit.getId().getPlanCode()+"',";
					}
					code = code.substring(0,(code.length()-1));
					plankindcode = plankindcode.substring(0,(plankindcode.length()-1));
					planlimitcode = planlimitcode.substring(0,(planlimitcode.length()-1));
					utiISyncLog.setStrKey("planCode in ('" +code+ "'),planCode in ('" +plankindcode+ "'),planCode in ('" +planlimitcode+ "')");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			//针对于同步方案进行清分
			if (utiISyncLogList.size() > 0) {			
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				.getService("utiISyncLogService");// 获得Spring管理的bean
				utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDplanMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				//inputBean　保存PrpDplan
				inputBean.setPrpDplanList(planList);
				//inputBean　保存PrpDplanClauseKind
				inputBean.setPrpDplanClauseKindList(planClauseKindList);
				//inputBean　保存PrpDplanLimit
				inputBean.setPrpDplanLimitList(planLimitList);
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				messageProducer.send(inputBean);
			}
		}
	}
	/**
	 * 针对于产品信息接口 synchroRiskData  调用如下方法进行清分操作
	 */
	public void qingFenSynRiskData(List accountInfoList, List areaList, List riskList,
			List riskClauseList,List riskClauseKindList, List riskClauseKindRelationList,List riskEngageList,
			List riskItemList,List riskLimitList,List riskShortRateList,List newCodeRiskList,
			List prpdrckratelowerList){
	
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			String onlineCom = ReadProperties.getString("onlineCom");
			String[] strOnlineCom = onlineCom.split(",");
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
			.getService("checkSameKeyService");// 获得Spring管理的bean
			Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			UtiISyncLog utiISyncLog = null;
			//日志记录PrpDaccountInfo相关保存信息
			for(Object o:accountInfoList)
			{	PrpDaccountInfo info = (PrpDaccountInfo)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDaccountInfoMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(info.getAccountID());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("accountId = '" + info.getAccountID()+ "'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			
			//日志记录PrpDarea相关保存信息
			for(Object o:areaList)
			{	PrpDarea prpDarea = (PrpDarea)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDareaMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDarea.getId().getAreaCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("areaCode = '" + prpDarea.getId().getAreaCode()+ "'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDrisk相关保存信息
			for(Object o:riskList)
			{	PrpDrisk prpDrisk = (PrpDrisk)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDrisk.getRiskCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("riskCode = '" + prpDrisk.getRiskCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskClause相关保存信息
			for(Object o:riskClauseList)
			{	PrpDriskClause prpDriskClause = (PrpDriskClause)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskClauseMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDriskClause.getId().getClauseCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("clauseCode = '" + prpDriskClause.getId().getClauseCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskClauseKind相关保存信息
			for(Object o:riskClauseKindList)
			{	PrpDriskClauseKind clauseKind = (PrpDriskClauseKind)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskClauseKindMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(clauseKind.getId().getClauseCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("clauseCode = '" + clauseKind.getId().getClauseCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskClauseKindRelation相关保存信息
			for(Object o:riskClauseKindRelationList)
			{	PrpDriskClauseKindRelation relation = (PrpDriskClauseKindRelation)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskClauseKindRelationMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(relation.getId().getRelationCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("relationCode = '" + relation.getId().getRelationCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskEngage相关保存信息
			for(Object o:riskEngageList)
			{	PrpDriskEngage engage = (PrpDriskEngage)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskEngageMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(engage.getId().getEngageCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("engageCode = '" + engage.getId().getEngageCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskItem相关保存信息
			for(Object o:riskItemList)
			{	PrpDriskItem prpDriskItem = (PrpDriskItem)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskItemMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDriskItem.getId().getItemCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("itemCode = '" + prpDriskItem.getId().getItemCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskLimit相关保存信息
			for(Object o:riskLimitList)
			{	PrpDriskLimit prpDriskLimit = (PrpDriskLimit)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskLimitMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDriskLimit.getId().getLimitCode());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("limitCode = '" + prpDriskLimit.getId().getLimitCode()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDriskShortRate相关保存信息
			for(Object o:riskShortRateList)
			{	PrpDriskShortRate prpDriskShortRate = (PrpDriskShortRate)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskShortRateMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDriskShortRate.getId().getShortRateID());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("shortRateID = '" + prpDriskShortRate.getId().getShortRateID()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDnewCodeRisk相关保存信息
			for(Object o:newCodeRiskList)
			{	PrpDnewCodeRisk prpDnewCodeRisk = (PrpDnewCodeRisk)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDnewCodeRiskMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(prpDnewCodeRisk.getId().getCodeType());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("codeType = '" + prpDnewCodeRisk.getId().getCodeType()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//日志记录PrpDRCKRateLower相关保存信息
			for(Object o:prpdrckratelowerList)
			{	PrpDRCKRateLower lower = (PrpDRCKRateLower)o;
				for (String comCode : strOnlineCom) {			
					utiISyncLog = new UtiISyncLog();
					utiISyncLog.setId(id);
					utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDRCKRateLowerMaintain);
					/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
				    utiISyncLog.setDestComCode(comCode);
					utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
					utiISyncLog.setOperUserCode(lower.getRateLowerID().toString());
					utiISyncLog.setReplayTimes(0);
					utiISyncLog.setSendDate(new Date());
					utiISyncLog.setLastSendDate(new Date());
					utiISyncLog.setStrKey("rateLowerID = '" + lower.getRateLowerID().toString()+"'");
					utiISyncLogList.add(utiISyncLog);
					id++;
				}
			}
			//针对于同步产品接口相关表进行清分
			if (utiISyncLogList.size() > 0) {			
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				.getService("utiISyncLogService");// 获得Spring管理的bean
				utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDaccountInfoMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				//inputBean 保存PrpDaccountInfo
				inputBean.setAccountInfoList(accountInfoList); 
				//inputBean　保存PrpDarea
				inputBean.setAreaList(areaList);
				//inputBean　保存PrpDrisk
				inputBean.setRiskList(riskList);
				//inputBean　保存PrpDriskClause
				inputBean.setRiskClauseList(riskClauseList);
				//inputBean　保存PrpDriskClauseKind
				inputBean.setRiskClauseKindList(riskClauseKindList);
				//inputBean　保存PrpDriskClauseKindRelation
				inputBean.setRiskClauseKindRelationList(riskClauseKindRelationList);
				//inputBean 保存PrpDriskEnggae
				inputBean.setRiskEngageList(riskEngageList);
				//inputBean　保存PrpDriskItem
				inputBean.setRiskItemList(riskItemList);
				//inputBean　保存PrpDriskLimit
				inputBean.setRiskLimitList(riskLimitList);
				//inputBean　保存PrpDriskShortRate
				inputBean.setRiskShortRateList(riskShortRateList);
				//inputBean　保存PrpDnewCodeRisk
				inputBean.setNewCodeRiskList(newCodeRiskList);
				//inputBean　保存PrpDRCKRateLower
				inputBean.setPrpdrckratelowerList(prpdrckratelowerList);
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				messageProducer.send(inputBean);
			}		
			}
		}	
	}

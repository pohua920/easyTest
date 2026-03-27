package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.bpsdriver.service.facade.SaaAPIService;
import com.sinosoft.bpsdriver.service.spring.SaaAPIServiceImpl;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDriskEngageService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDriskEngageServiceSpringImpl extends
GenericDaoHibernate<PrpDriskEngage, PrpDriskEngageId>implements PrpDriskEngageService {

	public PrpDriskEngage findByPrimaryKey(PrpDriskEngageId prpDriskEngageId) {
		PrpDriskEngage prpDriskEngage = super.get(prpDriskEngageId);
		return prpDriskEngage;
	}
	
	public PrpDriskEngage findByPrimaryKey1(String riskCode) {
		String hql="from PrpDriskEngage prpDriskEngage where prpDriskEngage.id.riskCode=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, riskCode);
		if(list.size()!=0){
			return (PrpDriskEngage) list.get(0);
		}else{
			return null;
		}
	}
	
	public Page getPrpDriskEngageList(PrpDriskEngage prpDriskEngage,String userCode,int pageNo,
			int pageSize) throws Exception{
		StringBuffer hql = new StringBuffer();
		String con = addPower(userCode);
		hql.append(" from PrpDriskEngage prpDriskEngage where ");
		hql.append(con);
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("id.engageCode", prpDriskEngage.getId().getEngageCode());
		hqlRules.addLike("engageCName", prpDriskEngage.getEngageCName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
		
	public void insertPrpDriskEngage(PrpDriskEngage prpDriskEngage, String userCode) {
		super.save(prpDriskEngage);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	        .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String codeCode = prpDriskEngage.getAreaCode();
			//modify by duanfa 20110726 start 总公司改为31000000
//			 if(!codeCode.equals("00000000")){
			if(!codeCode.equals("31000000")){
				//modify by duanfa 20110726 end
				 String hql=" from PrpDcompany prpDcompany where prpDcompany.comCode=? ";
				 List<PrpDcompany> prpDcompany  = super.findByHql(hql,codeCode);
				 String[] code = prpDcompany.get(0).getUpperPath().split(",");			 
				 onlineCom = code[1];
			 }
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskEngageMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("engageCode = '" + prpDriskEngage.getId().getEngageCode()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDriskEngageMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDriskEngage(prpDriskEngage);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}

	}

	public void updatePrpDriskEngage(PrpDriskEngage prpDriskEngage, String userCode) {
		super.update(prpDriskEngage);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
		       .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String codeCode = prpDriskEngage.getAreaCode();
			//modify by duanfa 20110726 start 总公司改为31000000
//			 if(!codeCode.equals("00000000")){
			if(!codeCode.equals("31000000")){
				//modify by duanfa 20110726 end
				 String hql=" from PrpDcompany prpDcompany where prpDcompany.comCode=? ";
				 List<PrpDcompany> prpDcompany  = super.findByHql(hql,codeCode);
				 String[] code = prpDcompany.get(0).getUpperPath().split(",");			 
				 onlineCom = code[1];
			 }
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDriskEngageMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("engageCode = '" + prpDriskEngage.getId().getEngageCode()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDriskEngageMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDriskEngage(prpDriskEngage);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}

	}
	
	public void prpDriskEngageMessageProcess(PrpDriskEngage prpDriskEngage)
	throws Exception {
		if (prpDriskEngage != null) {
			try {
				super.save(prpDriskEngage);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	}
	
	//同步产品信息的清分功能
	public void synchroRiskDataMessageProcess(List accountInfoList, List areaList, List riskList,
			List riskClauseList,List riskClauseKindList, List riskClauseKindRelationList,List riskEngageList,
			List riskItemList,List riskLimitList,List riskShortRateList,List newCodeRiskList,
			List prpdrckratelowerList)
	throws Exception {
		
			try {
				if (accountInfoList.size()> 0) {
					super.getHibernateTemplate().saveOrUpdateAll(accountInfoList);
					}
				if (areaList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(areaList);	
					}
				if (riskList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskList);	
					}
				if (riskClauseList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskClauseList);	
					}
				if (riskClauseKindList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskClauseKindList);	
					}
				if (riskClauseKindRelationList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskClauseKindRelationList);	
					}
				if (riskEngageList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskEngageList);	
					}
				if (riskItemList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskItemList);	
					}
				if (riskLimitList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskLimitList);	
					}
				if (riskShortRateList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(riskShortRateList);	
					}
				if (newCodeRiskList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(newCodeRiskList);	
					}
				if (prpdrckratelowerList.size()> 0){
					super.getHibernateTemplate().saveOrUpdateAll(prpdrckratelowerList);
					}
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	
	//同步险种信息的清分功能
	public void synchroClassDataMessageProcess(List classList)throws Exception{
		try {
			if (classList.size()> 0) {
				super.getHibernateTemplate().saveOrUpdateAll(classList);
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception(e.getMessage());
		}
	}
	
	//同步方案信息的清分功能
	public void synchroPlanDataMessageProcess(List prpDplanList,List prpDplanClauseKindList,List prpDplanLimitList)throws Exception{
		try {
			if (prpDplanList.size()> 0) {
				super.getHibernateTemplate().saveOrUpdateAll(prpDplanList);
				}
			if (prpDplanClauseKindList.size()> 0) {
				super.getHibernateTemplate().saveOrUpdateAll(prpDplanClauseKindList);
				}
			if (prpDplanLimitList.size()> 0) {
				super.getHibernateTemplate().saveOrUpdateAll(prpDplanLimitList);
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception(e.getMessage());
		}
	}

	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDRISKENGAGE_COMCODE, IConstants.PRPDRISKENGAGE_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
}



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
import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDagentExt;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcontractManage;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDagentService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDagentServiceSpringImpl extends
		GenericDaoHibernate<PrpDagent, String> implements PrpDagentService {


	public void deletePrpDagent(PrpDagent prpDagent) {
		super.delete(prpDagent);
	}

	public void deleteByPK(String PK) {
		super.deleteByPK(PK);
	}

	public PrpDagent findByPrimaryKey(String agentCode) {
		PrpDagent prpDagent = super.get(agentCode);
		return prpDagent;
	}
	public PrpDagentAll findByPrimaryKey2(String agentCode){
		String hql="from PrpDagentAll prpDagentAll where prpDagentAll.id.agentCode=? ";
		List list = new ArrayList();
		 list =  super.findByHql(hql, agentCode);
		
		if(list.size()!=0){
			PrpDagentAll prpDagentAll = (PrpDagentAll) list.get(0);
			return prpDagentAll;
		}else{
			return null;
		}
	}
	public PrpDagent findByPrimaryKey1(String agentCode) {
		String hql="from PrpDagent prpDagent where prpDagent.id.agentCode=? and validStatus=1";
		List list = new ArrayList();
		 list =  super.findByHql(hql, agentCode);
		
		if(list.size()!=0){
			PrpDagent prpDagent = (PrpDagent) list.get(0);
			return prpDagent;
		}else{
			return null;
		}
		
	}

	public void insertPrpDagent(PrpDagent prpDagent,String operUserCode) {
		super.save(prpDagent);
		// JMS
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
			for (String comCode : strOnlineCom) {			
				utiISyncLog = new UtiISyncLog();
				utiISyncLog.setId(id);
				utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDagentMaintain);
				/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
			    utiISyncLog.setDestComCode(comCode);
				utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				utiISyncLog.setOperUserCode(operUserCode);
				utiISyncLog.setReplayTimes(0);
				utiISyncLog.setSendDate(new Date());
				utiISyncLog.setLastSendDate(new Date());
				utiISyncLog.setStrKey("agentCode = '" + prpDagent.getAgentCode() + "'");
				utiISyncLogList.add(utiISyncLog);
				id++;
			}
			if (utiISyncLogList.size() > 0) {			
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				.getService("utiISyncLogService");// 获得Spring管理的bean
				utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDagentMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				inputBean.setPrpDagent(prpDagent);
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDagent(PrpDagent prpDagent,String userCode) {
		super.update(prpDagent);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			 CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	         .getService("checkSameKeyService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String[] strOnlineCom = onlineCom.split(",");
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 for (String comCode : strOnlineCom) {
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDagentMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("agentCode = '" + prpDagent.getAgentCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 InputBean inputBean = new InputBean();
				 inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDagentMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDagent(prpDagent);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
				
			}
		}
	}
	//查询分公司prpdagent表
	public Page getPrpDagentList(PrpDagent prpDagent, String userCode,int pageNo, int pageSize)throws Exception {
		StringBuffer hql = new StringBuffer();
		String con = addPower(userCode);
		hql.append(" from PrpDagent prpDagent where ");
		hql.append(con);
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("agentCode", prpDagent.getAgentCode());
		hqlRules.addLike("agentName", prpDagent.getAgentName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
	//查询总公司prpdagentall表
	public Page getPrpDagentAllList(PrpDagent prpDagent, String userCode,int pageNo, int pageSize) throws Exception{
		StringBuffer hql = new StringBuffer();
		String con = addPowerAll(userCode);
		hql.append(" from PrpDagentAll prpDagentAll where ");
		hql.append(con);
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("agentCode", prpDagent.getAgentCode());
		hqlRules.addLike("agentName", prpDagent.getAgentName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
	public void deleteAll(List list) {
		if (list != null && list.size() > 0) {
			super.deleteAll(list);
		}
	}
	
	public void prpDagentMessageProcess(PrpDagent prpDagent)throws Exception{
		if (prpDagent != null) {
			try {				
				super.save(prpDagent);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
	
	//addPower用来限制查询总公司结果（允许机构之内的数据）
	public  String addPowerAll(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDAGENTALL_COMCODE, IConstants.PRPDAGENTALL_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
	
	//addPower用来限制查询分公司结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDAGENTALL_COMCODE, IConstants.PRPDAGENT_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
	/**
	 * 针对于PrpDAgentAll清分到分公司PrpDAgent by wanghaibo 2010-07-12
	 * @param prpDagentAll
	 * @throws Exception
	 */
	public void prpDagentAllMessageProcess(PrpDagentAll prpDagentAll,List prpdAgentExtList,List prpdContractManageList)throws Exception{
		if (prpDagentAll != null) {
			try {
				PrpDagent prpDagent = new PrpDagent();
				prpDagent.setAgentCode(prpDagentAll.getId().getAgentCode());
				prpDagent.setAddressName(prpDagentAll.getAddressName());
				prpDagent.setAgentName(prpDagentAll.getAgentName());
				prpDagent.setAgentNature(prpDagentAll.getAgentNature());
				prpDagent.setAgentType(prpDagentAll.getAgentType());
				prpDagent.setArticleCode(prpDagentAll.getArticleCode());
				prpDagent.setBargainDate(prpDagentAll.getBargainDate());
				prpDagent.setComCode(prpDagentAll.getComCode());
				prpDagent.setFaxNumber(prpDagentAll.getFaxNumber());
				prpDagent.setFlag(prpDagentAll.getFlag());
				prpDagent.setLinkerName(prpDagentAll.getLinkerName());
				prpDagent.setNewAgentCode(prpDagentAll.getNewAgentCode());
				prpDagent.setPermitNo(prpDagentAll.getPermitNo());
				prpDagent.setPhoneNumber(prpDagentAll.getPhoneNumber());
				prpDagent.setPostCode(prpDagentAll.getPostCode());
				prpDagent.setUpperAgentCode(prpDagentAll.getUpperAgentCode());
				prpDagent.setValidStatus(prpDagentAll.getValidStatus());
				super.getHibernateTemplate().saveOrUpdate(prpDagent);
				//保存PrpDagentExt
				if(prpdAgentExtList.size()>0){	
						super.getHibernateTemplate().saveOrUpdateAll(prpdAgentExtList);	
				}						
				if(prpdContractManageList.size()>0){
						super.getHibernateTemplate().saveOrUpdateAll(prpdContractManageList);
				}
					
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
	
	/**
	 * 针对于渠道信息接口 synPrpDAgentData  调用如下方法进行清分操作
	 * @param prpDagent
	 */
	public void qingFenSynPrpDAgentData(PrpDagentAll prpDagent,List prpdAgentExtList,List prpdContractManageList){
	
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			String onlineCom = ReadProperties.getString("onlineCom");
			String codeCode = prpDagent.getComCode();
			//modify by duanfa start 20110726 总公司改为31000000
			//if(!codeCode.equals("00000000")){
			if(!codeCode.equals("31000000")){
			//modify by duanfa end 20110726 
				 String hql=" from PrpDcompany prpDcompany where prpDcompany.comCode=? ";
				 List<PrpDcompany> prpDcompany  = super.findByHql(hql,codeCode);
				 String[] code = prpDcompany.get(0).getUpperPath().split(",");			 
				 onlineCom = code[1];		  
			 }
			String[] strOnlineCom = onlineCom.split(",");
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
			.getService("checkSameKeyService");// 获得Spring管理的bean
			Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			UtiISyncLog utiISyncLog = null;
			//日志记录PrpDagentAll相关保存信息
			for (String comCode : strOnlineCom) {			
				utiISyncLog = new UtiISyncLog();
				utiISyncLog.setId(id);
				utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDagentAllMaintain);
				/** 需要特殊处理下，应该每个上线的分公司都有一条同步记录		 * */
			    utiISyncLog.setDestComCode(comCode);
				utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				utiISyncLog.setOperUserCode("同步渠道数据");
				utiISyncLog.setReplayTimes(0);
				utiISyncLog.setSendDate(new Date());
				utiISyncLog.setLastSendDate(new Date());
				utiISyncLog.setStrKey("agentCode = '" + prpDagent.getId().getAgentCode()+ "',contractObjectCode = '" + prpDagent.getId().getAgentCode()+ "'");
				utiISyncLogList.add(utiISyncLog);
				id++;
			}
			//针对于同步渠道进行清分
			if (utiISyncLogList.size() > 0) {			
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				.getService("utiISyncLogService");// 获得Spring管理的bean
				utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDagentAllMaintain);
				inputBean.setUtiISyncLogList(utiISyncLogList);
				inputBean.setPrpDagentAll(prpDagent); //inputBean 保存prpDagentAll
				inputBean.setPrpDagentExtList(prpdAgentExtList);//inputBean　保存prpDagentExtList
				inputBean.setPrpDcontractManageList(prpdContractManageList);//inputBean　保存prpDcontractManageList
				inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				messageProducer.send(inputBean);
			}		
			}
		}
}

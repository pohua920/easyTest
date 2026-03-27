package cn.com.sinosoft.dms.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDcodeRisk;
import cn.com.sinosoft.dms.model.PrpDcodeRiskId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeRiskService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDcodeRiskServiceSpringImpl extends
GenericDaoHibernate<PrpDcodeRisk, PrpDcodeRiskId>implements PrpDcodeRiskService{

	public Page PrpDcodeRiskList(PrpDcodeRisk prpDcodeRisk, int pageNo,
			int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDcodeRisk prpDcodeRisk where 1=1 ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("id.riskCode", prpDcodeRisk.getId().getRiskCode());
		hqlRules.addLike("id.codeType", prpDcodeRisk.getId().getCodeType());
		hqlRules.addLike("id.codeCode", prpDcodeRisk.getId().getCodeCode());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	public PrpDcodeRisk findByPrimaryKey(PrpDcodeRiskId prpDcodeRiskId) {
		PrpDcodeRisk prpDcodeRisk = super.get(prpDcodeRiskId);
		return prpDcodeRisk;
	}

	public void insertPrpDcodeRisk(PrpDcodeRisk prpDcodeRisk, String userCode) {
		super.save(prpDcodeRisk);
		// JMS 
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeRiskMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.riskCode = '" + prpDcodeRisk.getId().getRiskCode()
						 +"' and id.codeType = '" + prpDcodeRisk.getId().getCodeType() 
						 +"' and id.codeCode = '"+prpDcodeRisk.getId().getCodeCode()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeRiskMaintain);
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcodeRisk(prpDcodeRisk);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDcodeRisk(PrpDcodeRisk prpDcodeRisk, String userCode) {
		super.update(prpDcodeRisk);
		// JMS 
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeRiskMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.riskCode = '" + prpDcodeRisk.getId().getRiskCode()
						 +"' and id.codeType = '" + prpDcodeRisk.getId().getCodeType() 
						 +"' and id.codeCode = '"+prpDcodeRisk.getId().getCodeCode()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeRiskMaintain);
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcodeRisk(prpDcodeRisk);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void prpdCodeRiskMessageProcess(PrpDcodeRisk prpDcodeRisk)
			throws Exception {
		if (prpDcodeRisk != null) {
			try {
				super.save(prpDcodeRisk);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		
	}

}

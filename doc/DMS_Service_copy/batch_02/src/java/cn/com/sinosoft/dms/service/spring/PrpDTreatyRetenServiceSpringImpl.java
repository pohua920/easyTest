package cn.com.sinosoft.dms.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.model.PrpDtreatyRetenId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDTreatyRetenService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDTreatyRetenServiceSpringImpl extends
	GenericDaoHibernate<PrpDtreatyReten, PrpDtreatyRetenId>implements PrpDTreatyRetenService{

	public Page PrpDTreatyRetenList(PrpDtreatyReten prpDtreatyReten,int pageNo, int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDtreatyReten prpDtreatyReten where 1=1 ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("currency", prpDtreatyReten.getCurrency());
		hqlRules.addLike("id.uwYear", prpDtreatyReten.getId().getUwYear());
		hqlRules.addLike("id.classCode", prpDtreatyReten.getId().getClassCode());
		hqlRules.addLike("id.riskCode", prpDtreatyReten.getId().getRiskCode());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	public void insertPrpDTreatyReten(PrpDtreatyReten prpDtreatyReten,String userCode) {
		super.save(prpDtreatyReten);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDTreatyRetenMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.uwYear = '" + prpDtreatyReten.getId().getUwYear() 
						 +"' and id.classCode = '" + prpDtreatyReten.getId().getClassCode() 
						 +"' and id.riskCode = '"+prpDtreatyReten.getId().getRiskCode()
						 +"' and id.serialNo = '"+prpDtreatyReten.getId().getSerialNo()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDTreatyRetenMaintain);
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDtreatyReten(prpDtreatyReten);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	public PrpDtreatyReten findByPrimaryKey(PrpDtreatyRetenId prpDtreatyRetenId) {
		PrpDtreatyReten prpDtreatyReten = super.get(prpDtreatyRetenId);
		return prpDtreatyReten;
	}
	public void updatePrpDTreatyReten(PrpDtreatyReten prpDtreatyReten,String userCode) {
		super.update(prpDtreatyReten);
		//JMS
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDTreatyRetenMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.uwYear = '" + prpDtreatyReten.getId().getUwYear() 
						 +"' and id.classCode = '" + prpDtreatyReten.getId().getClassCode() 
						 +"' and id.riskCode = '"+prpDtreatyReten.getId().getRiskCode()
						 +"' and id.serialNo = '"+prpDtreatyReten.getId().getSerialNo()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDTreatyRetenMaintain);
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDtreatyReten(prpDtreatyReten);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public List riskCodeList(String riskCode) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDtreatyReten prpDtreatyReten where prpDtreatyReten.id.riskCode = ?");
		List<PrpDtreatyReten> list=super.findByHql(hql.toString(),riskCode);
		return list;
	}
	public void prpDTreatyRetenMessageProcess(PrpDtreatyReten prpDTreatyReten) throws Exception {
		if (prpDTreatyReten != null) {
			try {
				super.save(prpDTreatyReten);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
}

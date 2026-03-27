package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDportService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDportServiceSpringImpl extends
		GenericDaoHibernate<PrpDport, String> implements PrpDportService {


	public void deletePrpDport(PrpDport prpDport) {
		super.delete(prpDport);
	}
	
	public void deleteByPK(String PK){
		super.deleteByPK(PK);
	}

	public PrpDport findByPrimaryKey(String portCode) {
		PrpDport prpDport = super.get(portCode);
		return prpDport;
	}
	
	public PrpDport findByPrimaryKey1(String portCode) {
		String hql="from PrpDport prpDport where prpDport.portCode=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, portCode);
		if(list.size()!=0){
			return (PrpDport) list.get(0);
		}else{
			return null;
		}
	}

	public void insertPrpDport(PrpDport prpDport,String userCode) {
		super.save(prpDport);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDportMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("portCode = '" + prpDport.getPortCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDportMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDport(prpDport);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDport(PrpDport prpDport,String userCode) {
		super.update(prpDport);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDportMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("portCode = '" + prpDport.getPortCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDportMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDport(prpDport);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public Page getPrpDportList(PrpDport prpDport, int pageNo, int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDport prpDport where 1=1");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("portCode", prpDport.getPortCode());
		hqlRules.addLike("portCName", prpDport.getPortCName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
	
	public void deleteAll(List list){
		if(list!=null&&list.size()!=0){
			super.deleteAll(list);
		}
	}
	
	public void prpDportMessageProcess(PrpDport prpPort)throws Exception{
		if (prpPort != null) {
			try {
				super.save(prpPort);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	}
}

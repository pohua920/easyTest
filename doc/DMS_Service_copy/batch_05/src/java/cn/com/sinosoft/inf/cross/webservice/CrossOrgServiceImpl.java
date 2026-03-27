package cn.com.sinosoft.inf.cross.webservice;

import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;

public class CrossOrgServiceImpl extends GenericDaoHibernate {
	public String execute(String requestStr) {
		String result = "TRUE";
		try {
			// 截取报文头
			String requestHead = requestStr.substring(0, requestStr.indexOf(":"));
			// 截取报文体
			String body = requestStr.substring(requestStr.indexOf(":") + 1);
			// 截取操作类型
			String[] heads = requestHead.split("-");
			if ("E".equals(heads[0])) { // "E"表示校验结果下发
				PrpDcompanyCheck company = new PrpDcompanyCheck();
				company.deCode(body);
				this.insertCompanyCheck(company);
			} else {					//其他是正常数据下发
				String optType = heads[1];
				// 生成数据对象
				PrpDcrossOrg org = new PrpDcrossOrg(body);
				if ("I".equals(optType)) {
					this.insertCrossOrg(org);
				} else if ("U".equals(optType)) {
					this.updateCrossOrg(org);
				} else if ("D".equals(optType)) {
					org.setStatusCod("0");
					org.setStatusNam("终止");
					this.updateCrossOrg(org);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "FALSE";
		}
		return result;
	}
	
	public void insertCrossOrg(PrpDcrossOrg org) {
		super.save(org);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	        .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcrossOrgMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setOperUserCode(org.getId().getOrgCod());
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("orgCod = '" + org.getId().getOrgCod()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcrossOrgMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcrossOrg(org);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	
	public void insertCompanyCheck(PrpDcompanyCheck check) {
		super.save(check);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	        .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcompanyCheckMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setOperUserCode(check.getCheckComcode());
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("checkComCode = '" + check.getCheckComcode()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcompanyCheckMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcompanyCheck(check);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	public void updateCrossOrg(PrpDcrossOrg org) {
		super.getHibernateTemplate().saveOrUpdate(org);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
		       .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcrossOrgMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setOperUserCode(org.getId().getOrgCod());
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("orgCod = '" + org.getId().getOrgCod()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcrossOrgMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcrossOrg(org);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	
}

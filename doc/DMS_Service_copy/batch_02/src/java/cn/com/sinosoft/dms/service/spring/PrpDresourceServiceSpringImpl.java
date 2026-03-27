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
import cn.com.sinosoft.dms.model.PrpDresource;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDresourceService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDresourceServiceSpringImpl extends
GenericDaoHibernate<PrpDresource, String>implements PrpDresourceService {

	public PrpDresource findByPrimaryKey(String resourceCode) {
		PrpDresource prpDresource = super.get(resourceCode);
		return prpDresource;
	}
	
	public PrpDresource findByPrimaryKey1(String resourceCode) {
		String hql="from PrpDresource prpDresource where prpDresource.resourceCode=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, resourceCode);
		if(list.size()!=0){
			return (PrpDresource) list.get(0);
		}else{
			return null;
		}
	}
	
	public Page getPrpDresourceList(PrpDresource prpDresource, String userCode,int pageNo,
			int pageSize) throws Exception{
		StringBuffer hql = new StringBuffer();
		String con = addPower(userCode);
		hql.append(" from PrpDresource prpDresource where ");
		hql.append(con);
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("resourceCode", prpDresource.getResourceCode());
		hqlRules.addLike("resourceName", prpDresource.getResourceName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
		
	public void insertPrpDresource(PrpDresource prpDresource, String userCode) {
		super.save(prpDresource);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDresourceMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("resourceCode = '" + prpDresource.getResourceCode()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDresourceMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDresource(prpDresource);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}

	}

	public void updatePrpDresource(PrpDresource prpDresource, String userCode) {
		super.update(prpDresource);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDresourceMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("resourceCode = '" + prpDresource.getResourceCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDresourceMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDresource(prpDresource);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}

	}
	
	public void prpDresourceMessageProcess(PrpDresource prpDresource)
	throws Exception {
		if (prpDresource != null) {
			try {
				super.save(prpDresource);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	}
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDRESOURCE_COMCODE, IConstants.PRPDRESOURCE_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
}



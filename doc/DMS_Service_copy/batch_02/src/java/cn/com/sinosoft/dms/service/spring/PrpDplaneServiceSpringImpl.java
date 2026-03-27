package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDplaneService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDplaneServiceSpringImpl extends GenericDaoHibernate<PrpDplane, String>
		implements PrpDplaneService {


	public void deletePrpDplane(PrpDplane prpDplane) {
		super.delete(prpDplane);
	}
/**
 * 
 * */
	public PrpDplane findByPrimaryKey(String licenceNo) {
		 PrpDplane prpDplane=super.get(licenceNo);
		 return prpDplane;
	}

	public PrpDplane findByPrimaryKey1(String licenceNo) {
		String hql="from PrpDplane prpDplane where prpDplane.licenceNo=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, licenceNo);
		if(list.size()!=0){
			return (PrpDplane) list.get(0);
		}else{
			return null;
		}
	}

	public void insertPrpDplane(PrpDplane prpDplane,String userCode) {
		super.save(prpDplane);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals("1")){
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDplaneMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
//				 utiISyncLog.setStrKey("licenceNo = '" + prpDplane.getLicenceNo() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDplaneMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDplane(prpDplane);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDplane(PrpDplane prpDplane,String userCode) {
		super.update(prpDplane);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals("1")){
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDplaneMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
//				 utiISyncLog.setStrKey("licenceNo = '" + prpDplane.getLicenceNo() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDplaneMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDplane(prpDplane);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	 
	public Page getPrpDplaneList(PrpDplane prpDplane, int pageNo, int pageSize) {
		 StringBuffer hql = new StringBuffer();
	        hql.append(" from PrpDplane prpDplane where 1=1 ");
	        HqlRulesUtil hqlRules = new HqlRulesUtil();
//	        hqlRules.addLike("licenceNo", prpDplane.getLicenceNo());
	        hqlRules.addLike("planeType", prpDplane.getPlaneType());
	        if(hqlRules.getHql().trim()!=null&&!hqlRules.getHql().trim().equals("")){
	            hql.append("and "+hqlRules.getHql());
	        }
	        logger.debug("HQL is :"+hql.toString());
	        Page page = findByHql(hql.toString(), pageNo, pageSize);
	        return page;
	}
	
	public void deleteByPK(String PK){
		super.deleteByPK(PK);
	}
	
	public void delsteAll(List list){
		if(list!=null&&list.size()!=0){
			super.deleteAll(list);
		}
	}
	
	public void prpDplaneMessageProcess(PrpDplane prpDplane) throws Exception {
		if (prpDplane != null) {
			super.save(prpDplane);
		}
	}
}

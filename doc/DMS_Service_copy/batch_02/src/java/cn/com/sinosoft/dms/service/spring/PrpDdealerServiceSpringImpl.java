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
import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDdealerService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDdealerServiceSpringImpl extends GenericDaoHibernate<PrpDdealer, String> implements PrpDdealerService {


    public void deletePrpDdealer(PrpDdealer prpDdealer) {
		super.delete(prpDdealer);
	}
    
    public void deleteByPK(String PK){
    	super.deleteByPK(PK);
    }

	public PrpDdealer findByPrimaryKey(String dealerCode) {
		PrpDdealer prpDdealer = super.get(dealerCode);
		return prpDdealer;
	}
	
	public PrpDdealer findByPrimaryKey1(String dealerCode) {
		String hql="from PrpDdealer prpDdealer where prpDdealer.dealerCode=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, dealerCode);
		if(list.size()!=0){
			return (PrpDdealer) list.get(0);
		}else{
			return null;
		}
	}


	public void insertPrpDdealer(PrpDdealer prpDdealer,String userCode) {
		super.save(prpDdealer);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDdealerMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("dealerCode = '" + prpDdealer.getDealerCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDdealerMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDdealer(prpDdealer);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDdealer(PrpDdealer prpDdealer,String userCode) {
		super.update(prpDdealer);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDdealerMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("dealerCode = '" + prpDdealer.getDealerCode() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDdealerMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDdealer(prpDdealer);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	
	public Page getPrpDdealerList(PrpDdealer prpDdealer, String userCode,int pageNo, int pageSize) throws Exception {
		 StringBuffer hql = new StringBuffer();
		 String con = addPower(userCode);
	        hql.append(" from PrpDdealer prpDdealer where ");
	        hql.append(con);
	        HqlRulesUtil hqlRules = new HqlRulesUtil();
	        hqlRules.addLike("dealerCode", prpDdealer.getDealerCode());
	        hqlRules.addLike("dealerName", prpDdealer.getDealerName());
	        if(hqlRules.getHql().trim()!=null&&!hqlRules.getHql().trim().equals("")){
	            hql.append("and "+hqlRules.getHql());
	        }
	        logger.debug("HQL is :"+hql.toString());
	        Page page = findByHql(hql.toString(), pageNo, pageSize);
	        return page;
	}

	public void deleteAll(List list){
		if(list!=null&&list.size()!=0){
			super.deleteAll(list);
		}
	}
	
	public void prpDdealerMessageProcess(PrpDdealer prpDdealer)throws Exception{
		if (prpDdealer != null) {
			try {				
				super.save(prpDdealer);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
	
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDDEALER_COMCODE, IConstants.PRPDDEALER_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
}

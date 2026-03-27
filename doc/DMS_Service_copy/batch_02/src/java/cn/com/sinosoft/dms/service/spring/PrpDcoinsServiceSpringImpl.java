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
import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.model.PrpDcoinsId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDcoinsService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDcoinsServiceSpringImpl extends 
	GenericDaoHibernate<PrpDcoins,PrpDcoinsId>implements PrpDcoinsService{

	public Page PrpDcoinsList(PrpDcoins prpDcoins, String userCode,int pageNo, int pageSize) throws Exception{
		StringBuffer hql = new StringBuffer();
		String con = addPower(userCode);
		hql.append(" from PrpDcoins prpDcoins where ");
		hql.append(con);
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("id.riskCode", prpDcoins.getId().getRiskCode());
		hqlRules.addLike("id.comCode", prpDcoins.getId().getComCode());
		hqlRules.addLike("id.coinsComCode", prpDcoins.getId().getCoinsComCode());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	public PrpDcoins findByPrimaryKey(PrpDcoinsId prpDcoinsId) {
		PrpDcoins prpDcoins = super.get(prpDcoinsId);
		return prpDcoins;
	}

	public void insertPrpDcoins(PrpDcoins prpDcoins, String userCode) {
		super.save(prpDcoins);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcoinsMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.comCode = '" + prpDcoins.getId().getComCode()
						 +"' and id.riskCode = '" + prpDcoins.getId().getRiskCode() 
						 +"' and id.coinsComCode = '"+prpDcoins.getId().getCoinsComCode()
						 +"' and id.period = '"+prpDcoins.getId().getPeriod()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcoinsMaintain);
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcoins(prpDcoins);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
		
	}

	public void updatePrpDcoins(PrpDcoins prpDcoins, String userCode) {
		super.update(prpDcoins);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcoinsMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("id.comCode = '" + prpDcoins.getId().getComCode()
						 +"' and id.riskCode = '" + prpDcoins.getId().getRiskCode() 
						 +"' and id.coinsComCode = '"+prpDcoins.getId().getCoinsComCode()
						 +"' and id.period = '"+prpDcoins.getId().getPeriod()
						 +"'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcoinsMaintain);
				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDcoins(prpDcoins);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void prpdCoinsMessageProcess(PrpDcoins prpDcoins) throws Exception{
		if (prpDcoins != null) {
			try {
				super.save(prpDcoins);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDCOINS_COMCODE, IConstants.PRPDCOINS_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}

}

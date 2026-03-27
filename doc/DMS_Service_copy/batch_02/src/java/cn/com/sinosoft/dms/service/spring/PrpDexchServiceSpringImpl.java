package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDexchId;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDexchService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.inf.dict.util.PubFun;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDexchServiceSpringImpl extends
		GenericDaoHibernate<PrpDexch, PrpDexchId> implements PrpDexchService {
	private static Log logger = LogFactory
			.getLog(PrpDexchServiceSpringImpl.class);

	public void deleteByPK(PrpDexchId prpDexchId) {
		super.deleteByPK(prpDexchId);
	}

	public void deletePrpDexch(PrpDexch prpDexch) {
		super.delete(prpDexch);
	}

	public PrpDexch findByPrimaryKey(PrpDexchId prpDexchId) {
		return super.get(prpDexchId);
	}

	public PrpDexch getLastPrpDexch(Date currDate, String baseCurrency,
			String exchCurrency) {
		StringBuffer hql = new StringBuffer();
		hql
				.append("from PrpDexch t where t.id.baseCurrency = ? and t.id.exchCurrency = ? ");
		if (currDate != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			hql.append(" and t.id.exchDate <= date('" + sf.format(currDate)
					+ "')");
		}
		hql.append(" " +
				"order by exchdate desc");
		List<PrpDexch> list = findByHql(hql.toString(),baseCurrency,exchCurrency);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	public PrpDexch getLastPrpDexchs(Date currDate, String baseCurrency,
			String exchCurrency) {
		StringBuffer hql = new StringBuffer();
		hql
				.append("from PrpDexch t where t.id.baseCurrency = ? and t.id.exchCurrency in (?) ");
		if (currDate != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			hql.append(" and t.id.exchDate <= to_date('" + sf.format(currDate)
					+ "','yyyy-MM-dd')");
		}
		hql.append(" " +
				"order by exchdate desc");
		List<PrpDexch> list = findByHql(hql.toString(),baseCurrency,exchCurrency);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Page getPrpDexchList(PrpDexch prpDexch, int pageNo, int pageSize) {

		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDexch prpDexch where 1=1 ");
		if (prpDexch != null && prpDexch.getId() != null) {
			HqlRulesUtil hqlRules = new HqlRulesUtil();
			hqlRules.addLike("prpDexch.id.baseCurrency", prpDexch.getId()
					.getBaseCurrency());
			hqlRules.addLike("prpDexch.id.exchCurrency", prpDexch.getId()
					.getExchCurrency());
			if (hqlRules.getHql().trim() != null
					&& !hqlRules.getHql().trim().equals("")) {
				hql.append("and " + hqlRules.getHql());
			}
		}

		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}

	public void insertPrpDexch(PrpDexch prpDexch,String userCode) {
		super.save(prpDexch);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDexchMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 String strDate = PubFun.DateToStr(prpDexch.getId().getExchDate());
				 utiISyncLog.setStrKey("id.exchDate = date('" + strDate + "') and id.baseCurrency = '" + prpDexch.getId().getBaseCurrency() 
						  + "' and id.exchCurrency = '" + prpDexch.getId().getExchCurrency() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDexchMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDexch(prpDexch);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public void updatePrpDexch(PrpDexch prpDexch,String userCode) {
		super.update(prpDexch);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDexchMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 String strDate = PubFun.DateToStr(prpDexch.getId().getExchDate());
				 utiISyncLog.setStrKey("id.exchDate = date('" + strDate + "') and id.baseCurrency = '" + prpDexch.getId().getBaseCurrency() 
						  + "' and id.exchCurrency = '" + prpDexch.getId().getExchCurrency() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDexchMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDexch(prpDexch);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}

	public boolean isSameKey(PrpDexchId id) {
		StringBuffer hql = new StringBuffer();
		hql
				.append(" from PrpDexch  o where o.id.exchDate=? and o.id.baseCurrency=? and o.id.exchCurrency=?");
		List list = super.findByHql(hql.toString(), id.getExchDate(), id
				.getBaseCurrency(), id.getExchCurrency());
		if (list == null || list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void deleteAll(List list) {
		if (list != null && list.size() != 0) {
			super.deleteAll(list);
		}
	}
	
	public void prpDexchMessageProcess(PrpDexch prpDexch)throws Exception{
		if (prpDexch != null) {
			try {
				super.save(prpDexch);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
	}
}

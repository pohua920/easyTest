package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDnewCodeComId;
import cn.com.sinosoft.dms.service.facade.PrpDcodeComService;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDcodeComServiceSpringImpl extends 
    GenericDaoHibernate<PrpDnewCodeCom, PrpDnewCodeComId>implements PrpDcodeComService{

	public Page PrpDcodeComList(PrpDnewCodeCom prpDnewCodeCom, int pageNo,
			int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpDnewCodeCom prpDnewCodeCom where 1=1 ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("id.comCode", prpDnewCodeCom.getId().getComCode());
		hqlRules.addLike("id.codeType", prpDnewCodeCom.getId().getCodeType());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
	public PrpDnewCodeCom findByPrimaryKey(PrpDnewCodeComId prpDnewCodeComId) {
		PrpDnewCodeCom prpDnewCodeCom = super.get(prpDnewCodeComId);
		return prpDnewCodeCom;
	}
	public void insertPrpDcodeCom(PrpDnewCodeCom prpDnewCodeCom, String userCode) {
		super.save(prpDnewCodeCom);
		// JMS 
//		String syncflag = ReadProperties.getString("syncflag");
//		if(syncflag.equals(SyncConstants.SyncFlag)){
//			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
//	        .getService("checkSameKeyService");// 获得Spring管理的bean
//			 String onlineCom = ReadProperties.getString("onlineCom");
//			 String[] strOnlineCom = onlineCom.split(",");
//			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
//			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
//			 UtiISyncLog utiISyncLog = null;
//			 for (String comCode : strOnlineCom) {	
//				 utiISyncLog = new UtiISyncLog();
//				 utiISyncLog.setId(id);
//				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeComMaintain);
//				 utiISyncLog.setDestComCode(comCode);
//				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
//				 utiISyncLog.setOperUserCode(userCode);
//				 utiISyncLog.setReplayTimes(0);
//				 utiISyncLog.setSendDate(new Date());
//				 utiISyncLog.setLastSendDate(new Date());
//				 utiISyncLog.setStrKey("id.comCode = '" + prpDcodeCom.getId().getComCode() 
//						 +"' and id.codeType = '" + prpDcodeCom.getId().getCodeType()
//						 +"' and id.codeCode = '"+prpDcodeCom.getId().getCodeCode()
//						 +"'");
//				 utiISyncLogList.add(utiISyncLog);
//				 id++;
//			}
//			 if (utiISyncLogList.size() > 0) {
//				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
//				.getService("messageProducer");// 获得Spring管理的bean
//				InputBean inputBean = new InputBean();
//				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeComMaintain);
//				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
//				 .getService("utiISyncLogService");// 获得Spring管理的bean
//				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
//				 inputBean.setUtiISyncLogList(utiISyncLogList);
//				 inputBean.setPrpDcodeCom(prpDcodeCom);
//				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
//				 messageProducer.send(inputBean);
//			}
//		}
	}
	public void updatePrpDcodeCom(PrpDnewCodeCom prpDnewCodeCom, String userCode) {
		super.update(prpDnewCodeCom);
		// JMS 
//		String syncflag = ReadProperties.getString("syncflag");
//		if(syncflag.equals(SyncConstants.SyncFlag)){
//			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
//	        .getService("checkSameKeyService");// 获得Spring管理的bean
//			 String onlineCom = ReadProperties.getString("onlineCom");
//			 String[] strOnlineCom = onlineCom.split(",");
//			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
//			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
//			 UtiISyncLog utiISyncLog = null;
//			 for (String comCode : strOnlineCom) {	
//				 utiISyncLog = new UtiISyncLog();
//				 utiISyncLog.setId(id);
//				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDcodeComMaintain);
//				 utiISyncLog.setDestComCode(comCode);
//				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
//				 utiISyncLog.setOperUserCode(userCode);
//				 utiISyncLog.setReplayTimes(0);
//				 utiISyncLog.setSendDate(new Date());
//				 utiISyncLog.setLastSendDate(new Date());
//				 utiISyncLog.setStrKey("id.comCode = '" + prpDcodeCom.getId().getComCode() 
//						 +"' and id.codeType = '" + prpDcodeCom.getId().getCodeType()
//						 +"' and id.codeCode = '"+prpDcodeCom.getId().getCodeCode()
//						 +"'");
//				 utiISyncLogList.add(utiISyncLog);
//				 id++;
//			}
//			 if (utiISyncLogList.size() > 0) {
//				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
//				.getService("messageProducer");// 获得Spring管理的bean
//				InputBean inputBean = new InputBean();
//				inputBean.setRequestFlag(SyncConstants.RequestFlag_EditTypeModify);
//				UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
//				 .getService("utiISyncLogService");// 获得Spring管理的bean
//				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
//				 inputBean.setUtiISyncLogList(utiISyncLogList);
//				 inputBean.setPrpDcodeCom(prpDcodeCom);
//				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
//				 messageProducer.send(inputBean);
//			}
//		}
	}
//	public void prpDcodeComMessageProcess(PrpDnewCodeCom prpDnewCodeCom) throws Exception {
//		if (prpDnewCodeCom != null) {
//			try {
//				super.save(prpDnewCodeCom);				
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new Exception(e.getMessage());
//			}
//		}
//	}
}

package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDtypeService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDtypeServiceSpringImpl extends
GenericDaoHibernate<PrpDtype, String> implements PrpDtypeService {

/**
 * 废弃方法，未用到，不建议使用
 * */
	public void deleteByPK(String PK) {
		super.deleteByPK(PK);
	}

/**
 * 删除prpDtype级联删除prpDcode
 * */
	public void deletePrpDtype(PrpDtype prpDtype) {
		PrpDcodeService prpDcodeService=(PrpDcodeService) applicationContext.getBean("prpDcodeService");
		StringBuffer hql = new StringBuffer();
        hql.append(" from PrpDnewCode prpDcode where prpDcode.id.codeType=?");
		List list = prpDcodeService.findByHql(hql.toString(), prpDtype.getCodeType());
		List<PrpDnewCode> prpDcodes = new ArrayList<PrpDnewCode>();
		for(int i=0;i<list.size();i++){
			PrpDnewCode prpDcode = (PrpDnewCode) list.get(i);
			prpDcodes.add(prpDcode);
		}
		prpDtype.setPrpDnewCodes(prpDcodes);
		super.delete(prpDtype);
	}

	public PrpDtype findByPrimaryKey(String prpDtype) {
		return super.get(prpDtype);
	}
	
	public PrpDtype findByPrimaryKey1(String prpDtype) {
		String hql="from PrpDtype prpDtype where prpDtype.codeType=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, prpDtype);
		if(list.size()!=0){
			return (PrpDtype) list.get(0);
		}else{
			return null;
		}
	}

	public Page getPrpDtypeList(PrpDtype prpDtype, int pageNo, int pageSize) {
		StringBuffer hql = new StringBuffer();
        hql.append(" from PrpDtype prpDtype where 1=1 ");
//        ---------------
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules
				.addLike("prpDtype.codeType", prpDtype.getCodeType());
		hqlRules
				.addLike("prpDtype.codeTypeDesc", prpDtype.getCodeTypeDesc());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append(" and " + hqlRules.getHql());
		}
//        ---------------
        
        Page page = findByHql(hql.toString(), pageNo, pageSize);
        return page;
	}

	public void insertPrpDtype(PrpDtype prpDtype,String userCode) {
		super.save(prpDtype);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDtypeMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("codeType = '" + prpDtype.getCodeType() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDtypeMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDtype(prpDtype);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
/**
 * 更新prpDtype
 * */
	public void updatePrpDtype(PrpDtype prpDtype,String userCode) {
		
//		由于表结构变化，prpDcode表中只有codetype属性，不需要更新prpDcode表
//		PrpDcodeService prpDcodeService=(PrpDcodeService) applicationContext.getBean("prpDcodeService");
//		PrpDcodeServiceSpringImpl PrpDcodeServiceSpringImpl = new PrpDcodeServiceSpringImpl();
//		StringBuffer hql = new StringBuffer();
//        hql.append("from PrpDcode prpDcode where prpDcode.id.codeType=?");
//        List<PrpDcode> list = prpDcodeService.findByHql(hql.toString(), prpDtype.getCodeType());
//        List<PrpDcode> prpDcodes = new ArrayList<PrpDcode>();
//		for(int i=0;i<list.size();i++){
//			PrpDcode prpDcode = (PrpDcode) list.get(i);
//			prpDcodes.add(prpDcode);
//		}
//		prpDtype.setPrpDcodes(prpDcodes);
		
		super.update(prpDtype);
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
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDtypeMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("codeType = '" + prpDtype.getCodeType() + "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDtypeMaintain);
				 UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
				 .getService("utiISyncLogService");// 获得Spring管理的bean
				 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDtype(prpDtype);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}
	}
	public PrpDtype get(String codeType){
		PrpDtype prpDtype = super.get(codeType);
		return prpDtype;
	}
	
	public void deleteAll(List list){
		if(list!=null&&list.size()!=0){
			super.deleteAll(list);
		}
	}
	
	public void prpdTypeMessageProcess(PrpDtype prpDtype)throws Exception{
		if (prpDtype != null) {
			try {
				super.save(prpDtype);				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}	
}

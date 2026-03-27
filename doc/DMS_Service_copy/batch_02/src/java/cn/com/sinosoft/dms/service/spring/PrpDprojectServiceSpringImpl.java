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
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDproject;
import cn.com.sinosoft.dms.service.facade.CheckSameKeyService;
import cn.com.sinosoft.dms.service.facade.PrpDprojectService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDprojectServiceSpringImpl extends
GenericDaoHibernate<PrpDproject, String>implements PrpDprojectService {

	public PrpDproject findByPrimaryKey(String projectCode) {
		PrpDproject prpDproject = super.get(projectCode);
		return prpDproject;
	}
	
	public PrpDproject findByPrimaryKey1(String projectCode) {
		String hql="from PrpDproject prpDproject where prpDproject.projectCode=? and validStatus=1";
		List list = new ArrayList();
		list = super.findByHql(hql, projectCode);
		if(list.size()!=0){
			return (PrpDproject) list.get(0);
		}else{
			return null;
		}
	}
	
	public Page getPrpDprojectList(PrpDproject prpDproject,String userCode,int pageNo,
			int pageSize) throws Exception{
		StringBuffer hql = new StringBuffer();
		String con = addPower(userCode);
		hql.append(" from PrpDproject prpDproject where ");
		hql.append(con);
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("projectCode", prpDproject.getProjectCode());
		hqlRules.addLike("projectCName", prpDproject.getProjectCName());
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and " + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		return page;
	}
		
	public void insertPrpDproject(PrpDproject prpDproject, String userCode) {
		super.save(prpDproject);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
	        .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String codeCode = prpDproject.getComCode();
			//modify by duanfa start 20110726 总公司改为31000000
//			 if(!codeCode.equals("00000000")){
			if(!codeCode.equals("31000000")){
				//modify by duanfa end
				 String hql=" from PrpDcompany prpDcompany where prpDcompany.comCode=? ";
				 List<PrpDcompany> prpDcompany  = super.findByHql(hql,codeCode);
				 String[] code = prpDcompany.get(0).getUpperPath().split(",");			 
				 onlineCom = code[1];
			 }
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDprojectMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeAdd);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("projectCode = '" + prpDproject.getProjectCode()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDprojectMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDproject(prpDproject);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}

	}

	public void updatePrpDproject(PrpDproject prpDproject, String userCode) {
		super.update(prpDproject);
		// JMS
		String syncflag = ReadProperties.getString("syncflag");
		if(syncflag.equals(SyncConstants.SyncFlag)){
			CheckSameKeyService checkSameKeyService = (CheckSameKeyService) ServiceFactory
		       .getService("checkSameKeyService");// 获得Spring管理的bean
			UtiISyncLogService utiISyncLogService = (UtiISyncLogService) ServiceFactory
			.getService("utiISyncLogService");// 获得Spring管理的bean
			 String onlineCom = ReadProperties.getString("onlineCom");
			 String codeCode = prpDproject.getComCode();
			//modify by duanfa start 20110726 总公司改为31000000
//			 if(!codeCode.equals("00000000")){
			if(!codeCode.equals("31000000")){
				//modify by duanfa end
				 String hql=" from PrpDcompany prpDcompany where prpDcompany.comCode=? ";
				 List<PrpDcompany> prpDcompany  = super.findByHql(hql,codeCode);
				 String[] code = prpDcompany.get(0).getUpperPath().split(",");			 
				 onlineCom = code[1];
			 }
			 String[] strOnlineCom = onlineCom.split(",");
			 List<UtiISyncLog> utiISyncLogList = new ArrayList<UtiISyncLog>();
			 UtiISyncLog utiISyncLog = null;
			 Long id = checkSameKeyService.getMaxId("UtiISyncLog", "id");
			 for (String comCode : strOnlineCom) {	
				 utiISyncLog = new UtiISyncLog();
				 utiISyncLog.setId(id);
				 utiISyncLog.setClassName(SyncConstants.RequestFlag_PrpDprojectMaintain);
				 utiISyncLog.setDestComCode(comCode);
				 utiISyncLog.setEditType(SyncConstants.RequestFlag_EditTypeModify);
				 utiISyncLog.setOperUserCode(userCode);
				 utiISyncLog.setReplayTimes(0);
				 utiISyncLog.setSendDate(new Date());
				 utiISyncLog.setLastSendDate(new Date());
				 utiISyncLog.setStrKey("projectCode = '" + prpDproject.getProjectCode()+ "'");
				 utiISyncLogList.add(utiISyncLog);
				 id++;
			}
			 utiISyncLogService.insertAllUtiISyncLog(utiISyncLogList);
			 if (utiISyncLogList.size() > 0) {
				HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
				.getService("messageProducer");// 获得Spring管理的bean
				InputBean inputBean = new InputBean();
				inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDprojectMaintain);
				 inputBean.setUtiISyncLogList(utiISyncLogList);
				 inputBean.setPrpDproject(prpDproject);
				 inputBean.setDestComCode(SyncConstants.DestComCode_Pub);
				 messageProducer.send(inputBean);
			}
		}

	}
	
	public void prpDprojectMessageProcess(PrpDproject prpDproject)
	throws Exception {
		if (prpDproject != null) {
			try {
				super.save(prpDproject);				
			} catch (Exception e) {
				e.printStackTrace();
				throw new  Exception(e.getMessage());
			}
		}
	}
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception{
		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
		String condition =  saaAPIService.addPower(IConstants.SVRCODE, userCode, IConstants.SEARCH_PRPDPROJECT_COMCODE, IConstants.PRPDPROJECT_BM, "", "");
		if(!"".equals(condition))
			return condition;
		else
			return " 1 != 1";
	}
}



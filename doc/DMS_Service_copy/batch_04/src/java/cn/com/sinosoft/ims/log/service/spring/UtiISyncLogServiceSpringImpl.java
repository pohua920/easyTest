package cn.com.sinosoft.ims.log.service.spring;

import ins.framework.common.Page;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDplan;
import cn.com.sinosoft.dms.model.PrpDplanClauseKind;
import cn.com.sinosoft.dms.model.PrpDplanLimit;
import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.model.PrpDproject;
import cn.com.sinosoft.dms.model.PrpDreinsurer;
import cn.com.sinosoft.dms.model.PrpDresource;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDsettlementByr;
import cn.com.sinosoft.dms.model.PrpDsettlementLkr;
import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.log.service.facade.UtiISyncLogService;
import cn.com.sinosoft.ims.sync.HDMessageProducer;
import cn.com.sinosoft.ims.sync.SyncConstants;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class UtiISyncLogServiceSpringImpl extends
		GenericDaoHibernate<Integer, String> implements UtiISyncLogService {
	private static Log logger = LogFactory
			.getLog(UtiISyncLogServiceSpringImpl.class);

	public void deleteMethod() {
	}

	public void insertMethod(UtiISyncLog utiISyncLog) {
		super.save(utiISyncLog);
	}
	
	public void insertAllUtiISyncLog(List<UtiISyncLog> utiISyncLogList){
		super.saveAll(utiISyncLogList);
	}

	public List<UtiISyncLog> getLogList(UtiISyncLog log, String userName) {
		StringBuffer hql = new StringBuffer();
		List<UtiISyncLog> logs = new ArrayList<UtiISyncLog>();
		hql.append("from UtiISyncLog utiISyncLog where 1=1");
		List<String> userCodes = getUserCodeByName(userName);
		if (userCodes == null) {
			hql.append(" and where 1=2 ");
		} else {
			for (int i = 0; i < userCodes.size(); i++) {
				String userCode = userCodes.get(i);
				if (userCode.equals("")) {
				} else {
					hql
							.append(" and utiISyncLog.userCode = '" + userCode
									+ "'");
				}
				List list = new ArrayList();
				list = super.findByHql(hql.toString());
				for (int j = 0; j < list.size(); j++) {
					logs.add((UtiISyncLog) list.get(j));
				}
			}
		}
		return logs;
	}
	public List<String> getUserCodeByName(String userName) {
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		StringBuffer hql = new StringBuffer();
		hql
				.append("select utiIUser.userCode from UtiIUser utiIUser where utiIUser.userName = ?");
		List<String> list = new ArrayList<String>();
		list = super.findByHql(hql.toString(), userName);
		if (list.size() == 0) {
			if (userName.equals("")) {
				list.add("");
				return list;
			} else {
				return null;
			}
		} else {
			return list;
		}
	}

	public Page getSyncLogPage(UtiISyncLog utiISyncLog, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append("from UtiISyncLog u where 1=1 " );
		HqlRulesUtil hqlRulesUtil = new HqlRulesUtil();
		hqlRulesUtil.addLike("u.operUserCode", utiISyncLog.getOperUserCode());
		if (hqlRulesUtil.getHql().trim().length() > 0) {
			sql.append(" and ");
			sql.append(hqlRulesUtil.getHql());
		}
		Page page = null;
		if (utiISyncLog.getSendDate() != null) {			
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
			String datetime = tempDate.format(new java.util.Date());
//			String database = ReadProperties.getString("database");
//			if (IConstants.DB_ORACLE.equals(database)) {
//				sql.append(" and u.sendDate = to_date(?,'yyyy-mm-dd')");
//			} else {
				sql.append(" and u.sendDate = date(?)");
//			}
			page = super.findByHql(sql.toString(), pageNo, pageSize,datetime);
		}else {
			page = super.findByHql(sql.toString(), pageNo, pageSize);
		}
		return page;
	}

	public void onTimeSendMessage(){
		String sql = "from UtiISyncLog u where (u.isSuccess = ? or u.isSuccess is null) and u.replayTimes < 3";
		List<UtiISyncLog> syncLogList = super.findByHql(sql, "0");
		for (UtiISyncLog utiISyncLog : syncLogList) {
			//渠道代码的清分
			if (SyncConstants.RequestFlag_PrpDagentMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDagentMaintain);
				prpdAgentMessageOnTimeSend(utiISyncLog);
			//金融机构代码清分
			}if (SyncConstants.RequestFlag_PrpDbankMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDbankMaintain);
				prpdBackMessageOnTimeSend(utiISyncLog);
			//通用代码清分
			}if (SyncConstants.RequestFlag_PrpDcodeMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDcodeMaintain);
				prpdCodeMessageOnTimeSend(utiISyncLog);
			//机构代码清分	
			}if (SyncConstants.RequestFlag_PrpDcompanyMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDcompanyMaintain);
				prpdCompanyMessageOnTimeSend(utiISyncLog);
			//汽车经销商代码清分
			}if (SyncConstants.RequestFlag_PrpDdealerMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDdealerMaintain);
				prpdDealerMessageOnTimeSend(utiISyncLog);
			//兑换率清分
			}if (SyncConstants.RequestFlag_PrpDexchMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDexchMaintain);
				prpdExchMessageOnTimeSend(utiISyncLog);
			//飞机代码清分
			}if (SyncConstants.RequestFlag_PrpDplaneMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDplaneMaintain);
				prpdPlaneMessageOnTimeSend(utiISyncLog);
			//港口代码清分
			}if (SyncConstants.RequestFlag_PrpDportMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDportMaintain);
				prpdPortMessageOnTimeSend(utiISyncLog);
			//船舶代码清分
			}if (SyncConstants.RequestFlag_PrpDshipMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDshipMaintain);
				prpdShipMessageOnTimeSend(utiISyncLog);
			//代码类型清分
			}if (SyncConstants.RequestFlag_PrpDtypeMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDtypeMaintain);
				prpdTypeMessageOnTimeSend(utiISyncLog);
			}
			//同步渠道数据清分
			if (SyncConstants.RequestFlag_PrpDagentAllMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDagentAllMaintain);
				prpDagentAllMessageOnTimeSend(utiISyncLog);
			}
			//特别约定代码清分
			if (SyncConstants.RequestFlag_PrpDriskEngageMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDriskEngageMaintain);
				prpDriskEngageMessageOnTimeSend(utiISyncLog);
			}
			//项目代码清分
			if (SyncConstants.RequestFlag_PrpDprojectMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDprojectMaintain);
				prpDprojectMessageOnTimeSend(utiISyncLog);
			}
			//专管专营代码清分
			if (SyncConstants.RequestFlag_PrpDresourceMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDresourceMaintain);
				prpDresourceMessageOnTimeSend(utiISyncLog);
			}
			//自留额代码清分
			if (SyncConstants.RequestFlag_PrpDTreatyRetenMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDTreatyRetenMaintain);
				prpDtreatyRetenMessageOnTimeSend(utiISyncLog);
			}
			//共保体代码清分
			if (SyncConstants.RequestFlag_PrpDcoinsMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDcoinsMaintain);
				prpDcoinsMessageOnTimeSend(utiISyncLog);
			}
			//分保接受人代码清分
			if (SyncConstants.RequestFlag_PrpDreinsurerMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDreinsurerMaintain);
				prpDreinsurerMessageOnTimeSend(utiISyncLog);
			}
			//国管局PICC联系人代码清分
			if (SyncConstants.RequestFlag_PrpDsettlementLkrMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDsettlementLkrMaintain);
				prpDsettlementLkrMessageOnTimeSend(utiISyncLog);
			}
			//国管局一级预算单位代码清分
			if (SyncConstants.RequestFlag_PrpDsettlementByrMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDsettlementByrMaintain);
				prpDsettlementByrMessageOnTimeSend(utiISyncLog);
			}
			//交叉销售PrpDcrossOrg清分
			if (SyncConstants.RequestFlag_PrpDcrossOrgMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDcrossOrgMaintain);
				prpDcrossOrgMessageOnTimeSend(utiISyncLog);
			}
			//交叉销售PrpDcompanyCheck清分
			if (SyncConstants.RequestFlag_PrpDcompanyCheckMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDcompanyCheckMaintain);
				prpDcompanyCheckMessageOnTimeSend(utiISyncLog);
			}
			//交叉销售PrpDcompanyCheck清分
			if (SyncConstants.RequestFlag_PrpDplanMaintain.equals(utiISyncLog.getClassName())) {
				System.out.println("再次清分："+SyncConstants.RequestFlag_PrpDplanMaintain);
				prpDplanMessageOnTimeSend(utiISyncLog);
			}
		}
	}
	private void prpDplanMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String[] str = utiISyncLog.getStrKey().split(",");
		String planSql = " from PrpDplan p where p." + str[0];
		String kindSql = " from PrpDplanClauseKind kind where kind." + str[1];
		String limitSql = " from PrpDplanLimit limit where limit." + str[2];
		List<PrpDplan> prpDplanList = super.findByHql(planSql);
		List<PrpDplanClauseKind> prpDplanClauseKindList = super.findByHql(kindSql);
		List<PrpDplanLimit> prpDplanLimitList = super.findByHql(limitSql);
		
		if (prpDplanList.size() >0) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDplanMaintain);
			inputBean.setPrpDplanList(prpDplanList);
			inputBean.setPrpDplanClauseKindList(prpDplanClauseKindList);
			inputBean.setPrpDplanLimitList(prpDplanLimitList);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDcompanyCheckMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDcompanyCheck p where p." + utiISyncLog.getStrKey();
		List<PrpDcompanyCheck> prpDcompanyCheckList = super.findByHql(sql);
		PrpDcompanyCheck prpDcompanyCheck = null;
		if (prpDcompanyCheckList.size() > 0) {
			prpDcompanyCheck = prpDcompanyCheckList.get(0);
		}
		if (prpDcompanyCheck != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcompanyCheckMaintain);
			inputBean.setPrpDcompanyCheck(prpDcompanyCheck);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDcrossOrgMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDcrossOrg p where p." + utiISyncLog.getStrKey();
		List<PrpDcrossOrg> prpDcrossOrgList = super.findByHql(sql);
		PrpDcrossOrg prpDcrossOrg = null;
		if (prpDcrossOrgList.size() > 0) {
			prpDcrossOrg = prpDcrossOrgList.get(0);
		}
		if (prpDcrossOrg != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcrossOrgMaintain);
			inputBean.setPrpDcrossOrg(prpDcrossOrg);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDsettlementByrMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from prpDsettlementByr p where p." + utiISyncLog.getStrKey();
		List<PrpDsettlementByr> prpDsettlementByrList = super.findByHql(sql);
		PrpDsettlementByr prpDsettlementByr = null;
		if (prpDsettlementByrList.size() > 0) {
			prpDsettlementByr = prpDsettlementByrList.get(0);
		}
		if (prpDsettlementByr != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDsettlementByrMaintain);
			inputBean.setPrpDsettlementByr(prpDsettlementByr);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDsettlementLkrMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDsettlementLkr p where p." + utiISyncLog.getStrKey();
		List<PrpDsettlementLkr> prpDsettlementLkrList = super.findByHql(sql);
		PrpDsettlementLkr prpDsettlementLkr = null;
		if (prpDsettlementLkrList.size() > 0) {
			prpDsettlementLkr = prpDsettlementLkrList.get(0);
		}
		if (prpDsettlementLkr != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDsettlementLkrMaintain);
			inputBean.setPrpDsettlementLkr(prpDsettlementLkr);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDreinsurerMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDreinsurer p where p." + utiISyncLog.getStrKey();
		List<PrpDreinsurer> prpDreinsurerList = super.findByHql(sql);
		PrpDreinsurer prpDreinsurer = null;
		if (prpDreinsurerList.size() > 0) {
			prpDreinsurer = prpDreinsurerList.get(0);
		}
		if (prpDreinsurer != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDreinsurerMaintain);
			inputBean.setPrpDreinsurer(prpDreinsurer);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDcoinsMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDcoins p where p." + utiISyncLog.getStrKey();
		List<PrpDcoins> prpDcoinsList = super.findByHql(sql);
		PrpDcoins prpDcoins = null;
		if (prpDcoinsList.size() > 0) {
			prpDcoins = prpDcoinsList.get(0);
		}
		if (prpDcoinsList != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcoinsMaintain);
			inputBean.setPrpDcoins(prpDcoins);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDtreatyRetenMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDtreatyReten p where p." + utiISyncLog.getStrKey();
		List<PrpDtreatyReten> prpDtreatyRetenList = super.findByHql(sql);
		PrpDtreatyReten prpDtreatyReten = null;
		if (prpDtreatyRetenList.size() > 0) {
			prpDtreatyReten = prpDtreatyRetenList.get(0);
		}
		if (prpDtreatyReten != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDTreatyRetenMaintain);
			inputBean.setPrpDtreatyReten(prpDtreatyReten);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDresourceMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDresource p where p." + utiISyncLog.getStrKey();
		List<PrpDresource> prpDresourceList = super.findByHql(sql);
		PrpDresource prpDresource = null;
		if (prpDresourceList.size() > 0) {
			prpDresource = prpDresourceList.get(0);
		}
		if (prpDresource != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDresourceMaintain);
			inputBean.setPrpDresource(prpDresource);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDprojectMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDproject p where p." + utiISyncLog.getStrKey();
		List<PrpDproject> prpDprojectList = super.findByHql(sql);
		PrpDproject prpDproject = null;
		if (prpDprojectList.size() > 0) {
			prpDproject = prpDprojectList.get(0);
		}
		if (prpDproject != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDprojectMaintain);
			inputBean.setPrpDproject(prpDproject);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDriskEngageMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDriskEngage p where p." + utiISyncLog.getStrKey();
		List<PrpDriskEngage> prpDriskEngageList = super.findByHql(sql);
		PrpDriskEngage prpDriskEngage = null;
		if (prpDriskEngageList.size() > 0) {
			prpDriskEngage = prpDriskEngageList.get(0);
		}
		if (prpDriskEngage != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDriskEngageMaintain);
			inputBean.setPrpDriskEngage(prpDriskEngage);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpDagentAllMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String[] str = utiISyncLog.getStrKey().split(",");
		String agentSql = " from PrpDagentAll p where p." + str[0];
		String extSql = " from PrpDagentExt ext where ext." + str[0];
		String manageSql = "from PrpDcontractManage man where man." + str[1];
		List<PrpDagentAll> prpDagentList = super.findByHql(agentSql);
		List prpDagentExtList = super.findByHql(extSql);
		List prpDcontractManageList = super.findByHql(manageSql);
		PrpDagentAll prpDagentAll = null;
		if (prpDagentList.size() > 0) {
			prpDagentAll = prpDagentList.get(0);
		}
		if (prpDagentAll != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDagentAllMaintain);
			inputBean.setPrpDagentAll(prpDagentAll);
			inputBean.setPrpDagentExtList(prpDagentExtList);
			inputBean.setPrpDcontractManageList(prpDcontractManageList);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdAgentMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDagent p where p." + utiISyncLog.getStrKey();
		List<PrpDagent> prpDagentList = super.findByHql(sql);
		PrpDagent prpDagent = null;
		if (prpDagentList.size() > 0) {
			prpDagent = prpDagentList.get(0);
		}
		if (prpDagent != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDagentMaintain);
			inputBean.setPrpDagent(prpDagent);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdBackMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDbank p where p." + utiISyncLog.getStrKey();
		List<PrpDbank> prpDbankList = super.findByHql(sql);
		PrpDbank prpDbank = null;
		if (prpDbankList.size() > 0) {
			prpDbank = prpDbankList.get(0);
		}
		if (prpDbank != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDbankMaintain);
			inputBean.setPrpDbank(prpDbank);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdCodeMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDnewCode where " + utiISyncLog.getStrKey();
		List<PrpDnewCode> prpDcodeList = super.findByHql(sql);
		PrpDnewCode prpDcode = null;
		if (prpDcodeList.size() > 0) {
			prpDcode = prpDcodeList.get(0);
		}
		if (prpDcode != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcodeMaintain);
			inputBean.setPrpDcode(prpDcode);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
		
	}
	
	private void prpdCompanyMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDcompany p where p." + utiISyncLog.getStrKey();
		List<PrpDcompany> prpDcompanyList = super.findByHql(sql);
		PrpDcompany prpDcompany = null;
		if (prpDcompanyList.size() > 0) {
			prpDcompany = prpDcompanyList.get(0);
		}
		if (prpDcompany != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDcompanyMaintain);
			inputBean.setPrpDcompany(prpDcompany);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdDealerMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDdealer p where p." + utiISyncLog.getStrKey();
		List<PrpDdealer> prpDdealerList = super.findByHql(sql);
		PrpDdealer prpDdealer = null;
		if (prpDdealerList.size() > 0) {
			prpDdealer = prpDdealerList.get(0);
		}
		if (prpDdealer != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDdealerMaintain);
			inputBean.setPrpDdealer(prpDdealer);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdExchMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDexch where " + utiISyncLog.getStrKey();
		List<PrpDexch> prpDexchList = super.findByHql(sql);
		PrpDexch prpDexch = null;
		if (prpDexchList.size() > 0) {
			prpDexch = prpDexchList.get(0);
		}
		if (prpDexch != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDexchMaintain);
			inputBean.setPrpDexch(prpDexch);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdPlaneMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDplane p where p." + utiISyncLog.getStrKey();
		List<PrpDplane> prpDplaneList = super.findByHql(sql);
		PrpDplane prpDplane = null;
		if (prpDplaneList.size() > 0) {
			prpDplane = prpDplaneList.get(0);
		}
		if (prpDplane != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDplaneMaintain);
			inputBean.setPrpDplane(prpDplane);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdPortMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDport p where p." + utiISyncLog.getStrKey();
		List<PrpDport> prpDportList = super.findByHql(sql);
		PrpDport prpDport = null;
		if (prpDportList.size() > 0) {
			prpDport = prpDportList.get(0);
		}
		if (prpDport != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDportMaintain);
			inputBean.setPrpDport(prpDport);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdShipMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDship p where p." + utiISyncLog.getStrKey();
		List<PrpDship> prpDshipList = super.findByHql(sql);
		PrpDship prpDship = null;
		if (prpDshipList.size() > 0) {
			prpDship = prpDshipList.get(0);
		}
		if (prpDship != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDshipMaintain);
			inputBean.setPrpDship(prpDship);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
	
	private void prpdTypeMessageOnTimeSend(UtiISyncLog utiISyncLog){
		String sql = "from PrpDtype p where p." + utiISyncLog.getStrKey();
		List<PrpDtype> prpDtypeList = super.findByHql(sql);
		PrpDtype prpDtype = null;
		if (prpDtypeList.size() > 0) {
			prpDtype = prpDtypeList.get(0);
		}
		if (prpDtype != null) {
			InputBean inputBean = new InputBean();
			inputBean.setDestComCode(utiISyncLog.getDestComCode());
			inputBean.setRequestFlag(SyncConstants.RequestFlag_PrpDtypeMaintain);
			inputBean.setPrpDtype(prpDtype);
			inputBean.setUtiISyncLog(utiISyncLog);
			HDMessageProducer messageProducer = (HDMessageProducer) ServiceFactory
			.getService("messageProducer");// 获得Spring管理的bean
			messageProducer.send(inputBean);
		}
	}
}

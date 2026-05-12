package com.sinosoft.claim.schema.service.spring;
/**
 * 工作流日志表接口
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.message.service.facade.MessageService;
import com.sinosoft.claim.payment.util.PayMentServiceManager;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredId;
import com.sinosoft.claim.schema.model.PrpCmainSub;
import com.sinosoft.claim.schema.model.PrpDclass;
import com.sinosoft.claim.schema.model.PrpDcompany;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLintfProcess;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfModelUse;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.model.WfFlowMain;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.model.WfLogId;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCmainSubService;
import com.sinosoft.claim.schema.service.facade.PrpDclassService;
import com.sinosoft.claim.schema.service.facade.PrpLacciPersonService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLintfProcessService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.SwfFlowMainService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfModelUseService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.schema.service.facade.WfFlowMainService;
import com.sinosoft.claim.schema.service.facade.WfGradeService;
import com.sinosoft.claim.schema.service.facade.WfLogExtService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.schema.service.facade.WfPackageService;
import com.sinosoft.claim.undwrt.service.facade.PrpFeedBackService;
import com.sinosoft.claim.undwrt.service.facade.UndwrtSendClaimService;
import com.sinosoft.reins.common.util.DataUtils;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.bl.action.domain.BLWfLogAction;
import com.sinosoft.undwrt.dto.custom.SubmitTaskDto;
import com.sinosoft.undwrt.dto.custom.UndwrtSubmitDto;

public class WfLogServiceSpringImpl extends GenericDaoHibernate<WfLog, WfLogId> implements WfLogService {

	private SwfNodeService swfNodeService;
	private WfGradeService wfGradeService;
	private PrpDcompanyService prpDcompanyService;
	private UwNotionService uwNotionService;
	private WfFlowMainService wfFlowMainService;
	private WfPackageService wfPackageService;
	private WfLogExtService wfLogExtService;
	private PrpDriskService prpDriskService;
	private PrpDclassService prpDclassService;
	private PrpLprepayService prpLprepayService;
	private PrpLcompensateService prpLcompensateService;
	private PrpCmainService prpCmainService;
	private PrpLintfProcessService prpLintfProcessService;
	private SwfLogService swfLogService;
	private PrpCmainSubService prpCmainSubService;
	private PrpDuserService prpDuserService;
	private PrpCinsuredService prpCinsuredService;
	private SwfModelUseService swfModelUseService;
	private SwfPathService swfPathService;
	private PrpLclaimService prpLclaimService;
	private SwfFlowMainService swfFlowMainService;
	private PrpFeedBackService prpFeedBackService;
	private UndwrtSendClaimService undwrtSendClaimService;
	private PrpLacciPersonService prpLacciPersonService;
	private MessageService messageService;

	// private MsgAction msgAction;
	// ---------------老系统的参数，不知何用，先留下--------------------
	private String flag = "";

	private String lFlowID = "";

	private int lLogNo = 0;

	private String LogOperatorCode = "";// 记录关联出单的车险商业险操作员代码

	private boolean IsMainSub = false;// 是否关联出单

	private String nodeType = ""; // 是否审核通过节点

	private String underWriteCode = "";

	private DateTime underWriteDate = new DateTime(new Date());
	private char certiType;

	private String status = "";

	// -----------------------------------

	@Override
	public List<WfLog> findByQueryRuleList(QueryRule queryRule) {
		return super.find(queryRule);
	}

	public void saveOrUpdate(WfLog wflog){
		super.getSession().saveOrUpdate(wflog);
	}

	/**
	 * 收回双核工作流
	 * @throws Exception
	 */
	public void recycleWflog(SwfLog swfLogDto) throws Exception {
		SwfLog swfLogTemp = swfLogService.findSwfLog(swfLogDto.getId().getFlowID(), swfLogDto.getId().getLogNo());
		String businessNo = swfLogTemp.getBusinessNo();
		if (businessNo != null && !"".equals(businessNo)) {
			String conditions = " BusinessNo ='" + businessNo + "'" + " AND NodeStatus<>'0' order by flowid,logNo";
			List<WfLog> wfLogList = this.findByConditions(conditions);
			WfLog wflog = null;
			if (wfLogList.size() > 0) {
				for (int i = 0; i < wfLogList.size(); i++) {
					wflog = wfLogList.get(i);
					if (wflog != null) {
						wflog.setNodeStatus("0");
						wflog.setFlowStatus("0");
						this.saveOrUpdate(wflog);
					}
				}
				// 增加一个审核通过节点
				// operatorname 工作流收回，生成审核节点，关闭工作流 收回工作流，生成一个审核通过节点
				if (wflog != null) {
					WfLog wflogSubmit = new WfLog();
					BeanUtils.copyProperties(wflogSubmit, wflog);
					WfLogId wfLogId = new WfLogId(wflog.getId().getFlowId(), wflog.getId().getLogNo() + 1);
					wflogSubmit.setId(wfLogId);
					wflogSubmit.setNodeName("審核通過");
					wflogSubmit.setResultContent("工作流收回，產生審核節點，關閉工作流");
					wflogSubmit.setOperatorName("工作流收回");
					wflogSubmit.setDeptCode(null);
					wflogSubmit.setDeptName(null);
					wflogSubmit.setOperatorCode(null);
					String dateTime = new DateTime(new Date(), DateTime.YEAR_TO_SECOND).toString();
					wflogSubmit.setFlowInTime(dateTime);
					wflogSubmit.setSubmitTime(dateTime);
					wflogSubmit.setHandleTime(dateTime);
					this.save(wflogSubmit);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WfLog> findByHqlList(String hql) {
		Session session = super.getSession();
		SQLQuery query = session.createSQLQuery(hql).addEntity(WfLog.class);
		List<WfLog> list = query.list();
		return list;
	}

	@Override
	public Page findUserTaskList(QueryRule queryRule, int pageNo, int pageSize) {
		Page page = super.find(queryRule, pageNo, pageSize);
		return page;
	}

	public WfLog findByPrimaryKey(String flowId, Integer logNo) {
		WfLogId wfLogId = new WfLogId(flowId, logNo);
		WfLog wflog = super.get(wfLogId);
		return wflog;
	}

	public WfLog findByWflog(WfLogId wfLogId) throws Exception {
		return super.get(wfLogId);
	}

	@Override
	public List<WfLog> findByFlowId(QueryRule queryRule) {
		return super.find(queryRule);
	}

	public List<WfLog> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		List<WfLog> list = super.find(queryRule);
		return list;
	}

	public List<WfLog> getBackList(String FlowId, int LogNo, int nodeNo) throws Exception {
		List<WfLog> wfLogList = new ArrayList<WfLog>();
		List<WfLog> logList = new ArrayList<WfLog>();
		WfLog wfLogDto = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", FlowId);
		queryRule.addLessThan("id.logNo", LogNo);
		queryRule.addLessThan("nodeNo", nodeNo);
		try {
			// 获得该流水号历史记录集，不含最近一条
			wfLogList = (List<WfLog>) super.find(queryRule);
			int wfLogListSize = wfLogList.size();
			List<String> vecLogList = new ArrayList<String>();
			int i = 0;
			// 过滤重复记录
			for (i = 0; i < wfLogListSize; i++) {
				wfLogDto = (WfLog) wfLogList.get(i);
				if (!vecLogList.contains(String.valueOf(wfLogDto.getNodeNo()) + "_" + String.valueOf(wfLogDto.getModelNo()))) {
					logList.add(wfLogDto);
				}
				// 组合节点号+模板号,用於过滤相同模板号且相同节点号的重复记录
				vecLogList.add(String.valueOf(wfLogDto.getNodeNo()) + "_" + String.valueOf(wfLogDto.getModelNo()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return logList;
	}

	@Override
	public int getMaxLogNo(String flowId) throws Exception {
		int logNo = 1;
		String sql = "SELECT MAX(logno) FROM Wflog WHERE flowid='" + flowId + "'";
		Number num = (Number) super.getSession().createSQLQuery(sql).uniqueResult();
		if (num != null) {
			logNo = num.intValue();
		}
		return logNo;
	}

	public void update(WfLog wfLog){
		logger.debug("更新业务号为" + wfLog.getBusinessNo() + "的要保書工作流状态");
		this.saveOrUpdate(wfLog);
	}

	private String[] getIdentifyTypeNumber(String riskCategory, String businessNo, String businessType) throws Exception {
		String identifyType = null, identifyNumber = null;
		// 意健险
		if (StringUtils.trimToEmpty(riskCategory).equals("E")) {
			// 承保
			if (businessType.equals("C") || businessType.equals("Y")) {
				PrpCinsuredId prpCinsuredId = new PrpCinsuredId();
				prpCinsuredId.setPolicyNo(businessNo);
				prpCinsuredId.setSerialNo(1);
				PrpCinsured prpCinsured = prpCinsuredService.findPrpCinsured(prpCinsuredId);
				// PrpCinsuredDto prpCinsuredDto = new
				// BLPrpCinsuredAction().findByPrimaryKey(dbManager,businessNo,1);
				identifyType = StringUtils.trimToEmpty(prpCinsured.getIdentifytype());
				identifyNumber = StringUtils.trimToEmpty(prpCinsured.getIdentifyNumber());
			}
		}
		return new String[] { identifyType, identifyNumber };
	}

	@Override
	@Deprecated
	public int getCount(QueryRule queryRule) throws Exception {
		return super.find(queryRule).size();
	}

	/**
	 * 根据险种和机构判断是否是否使用规则引擎
	 * @param iRiskCode 险种代码
	 * @param iComCode 归属机构代码
	 * @return true 使用规则引擎 false 不使用规则引擎
	 * @throws UserException
	 * @throws Exception
	 */
	public boolean isILog(String iRiskCode, String iComCode) throws UserException, Exception {
		boolean isILog = false;
		String strWhere = "";
		String strWhereCom = " 1=1 And validstatus = '1'  Start With Comcode = '" + iComCode + "' " + " Connect By Prior Uppercomcode = Comcode And Uppercomcode <> Prior Comcode ";
		// 递归查询所有的上级机构
		// BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		List<PrpDcompany> prpDcompanyList = prpDcompanyService.findByConditions(strWhereCom);
		// blPrpDcompany.query(strWhereCom);
		strWhereCom = "'" + iComCode + "'";
		for (int i = 0; i < prpDcompanyList.size(); i++) {
			strWhereCom += ",'" + prpDcompanyList.get(i).getComCode() + "' ";
		}

		strWhere = "select * from PrpDconfigCode funtype = 'ILog' And recordtype = 'ILog' And riskcode = '" + iRiskCode + "' " + " And comcode in (" + strWhereCom + ")" + " And validStatus = '1'";
		// 是否使用ILog配置在PrpDconfigCode表里面。
		// BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
		long count = HibernateUtils.getCountbySql(super.getSession(), strWhere);
		// blPrpDconfigCode.query(strWhere);
		if (count > 0) {
			isILog = true;
		}
		return isILog;
	}

	public List<WfLog> findByStatement(String sql, int pageNo, int rowsPerPage, boolean blnAll) throws Exception {
		logger.debug(sql);
		List<WfLog> list = new ArrayList<WfLog>();
		List<?> listTemp = null;
		if (rowsPerPage < 1) {
			listTemp = HibernateUtils.findbySql(super.getSession(), sql, WfLog.class);
		} else {
			listTemp = HibernateUtils.findbySql(super.getSession(), sql, pageNo, rowsPerPage, WfLog.class);
		}
		for (Iterator<?> iterator = listTemp.iterator(); iterator.hasNext();) {
			WfLog wfLog = (WfLog) iterator.next();
			list.add(wfLog);
		}
		logger.info("DBWfLogBase.findByConditions() success!");
		return list;
	}

	/**
	 * 关闭工作流时，置nodestatus为"0"
	 * @param dbManager DBManager
	 * @param flowID String
	 * @throws SQLException
	 * @throws Exception
	 */
	// modified by wangjun 20130306
	public void updateNodeStatusByFlowID(String flowId) throws SQLException, Exception {
		// //"关闭工作流，把所有的节点状态置为0，component/com/sinosoft/undwrt/resource/dtofactory/domain/DBWfLog.java"
		// + "-->updateNodeStatusByFlowID()方法");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.flowId", flowId);
		List<WfLog> list = super.find(queryRule);
		for(int i=0;i<list.size();i++){
			WfLog wfLog = list.get(i);
			wfLog.setNodeStatus("0");
			super.update(wfLog);
		}
	}

	private String getRiskCategoryByRiskCode(String riskCode) throws Exception {
		PrpDrisk prpDrisk = prpDriskService.findPrpDrisk(riskCode);
		PrpDclass prpDclass = prpDclassService.findPrpDclass(prpDrisk.getClassCode());
		return prpDclass.getRiskCategory();
	}

	/**
	 * 根据部门和时间生成信息包号
	 * @param int iModelno 模版号
	 * @param String iBusinessno 业务号 throws UserException,Exception
	 */
	public String getSoleFlowID(String comCode) throws UserException, Exception {
		String flowID = "";
		String currentTime = DateTime.current().toString();
		String currentYear = currentTime.substring(2, 4);
		String currentMonth = currentTime.substring(5, 7);
		String currentDay = currentTime.substring(8, 10);
		String currentHour = currentTime.substring(11, 13);
		String currentMinute = currentTime.substring(14, 16);
		String currentSecond = currentTime.substring(17, 19);
		String currentMM = currentTime.substring(20, 23);
		String random = String.valueOf(new Random().nextInt(100));
		random = com.sinosoft.sysframework.common.util.StringUtils.newString("0",2-random.length())+random;
		flowID = comCode + currentYear + currentMonth + currentDay + currentHour + currentMinute + currentSecond + currentMM+random;
		return flowID;

	}

	@Override
	public void save(WfLog wfLog) {
		super.save(wfLog);
	}

	private String getRelateContractNo(String riskCategory, String businessNo) throws Exception {
		String relateContractNo = ""; // lijibin:初始化要等於""，而不是null
		// 货运险 水险
		if (StringUtils.trimToEmpty(riskCategory).equals("Y")) {
			// DBPrpCmainSub dbPrpCmainSub = new DBPrpCmainSub(dbManager);
			// String conditions = "PolicyNo='" + businessNo + "'";
			// List collection = (List)
			// dbPrpCmainSub.findByConditions(conditions);
			QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", businessNo);
			List<PrpCmainSub> collection = prpCmainSubService.findPrpCmainSub(queryRule);
			// lijibin modify 20050823 不是所有的保单都预约协议号的
			if (collection.size() > 0) {
				PrpCmainSub schema = collection.get(0);
				relateContractNo = schema.getId().getMainPolicyNo();
			}
		}
		return StringUtils.trimToEmpty(relateContractNo);
	}

	/**
	 * 检查是否理赔工作流数据
	 * @param dbManager DBManager
	 * @param flowID String
	 * @param modelNo int
	 * @param nodeNo int
	 * @param businessType String 业务类型
	 * @throws Exception
	 * @return flag boolean
	 */
	public int checkSubmitClaim(String flowID, int modelNo, int nodeNo, String businessType) throws Exception {
		int flag = 0;
		if (businessType != null && (businessType.equals("C") || businessType.equals("Y"))) {
			// BLWfLogAction blWfLogAction = new BLWfLogAction();
			String strWherePart = "FlowID='" + flowID.trim() + "'";
			int intCount = this.getCount(strWherePart);
			WfLog wfLogOldDto = this.findByPrimaryKey(flowID, intCount - 1);
			WfLog wfLogNewDto = this.findByPrimaryKey(flowID, intCount);
			// 回退任务调用理赔工作流回退接口
			if (wfLogNewDto.getRelateFlowId() != null && !wfLogNewDto.getRelateFlowId().equals("")) {
				// 是否审核通过节点
				boolean blnResult = swfNodeService.checkEndflag(modelNo, nodeNo);
				String interMethod = "";
				// 如果当前节点不是审核通过节点
				if (!blnResult) {
					// 如果是回退理赔节点
					if (nodeNo == 1) {
						interMethod = "backVericToCompp";
					} else {
						interMethod = "addInformationOnVeric";
					}
				} else{
					 // 如果当前的节点TO是核保通过节点
//					//"审核通过节点");
					// 手工结案
					interMethod = "passVeric";
				}
				flag = this.submitClaim(wfLogOldDto, wfLogNewDto, interMethod);

			}
		}
		return flag;
	}

	/**
	 * 调用理赔工作流发送方法
	 * @param dbManager DBManager
	 * @param wfLogOldDto WfLogDto
	 * @param wfLogNewDto WfLogDto
	 * @param interMethod String
	 * @throws Exception
	 * @return flag boolean
	 */
	public int submitClaim(WfLog wfLogOld, WfLog wfLogNew, String interMethod) throws Exception {
		int flag = 0;
		// BLUwNotionAction blUwNotionAction = new BLUwNotionAction();
		String sql = " FlowID = '" + wfLogOld.getId().getFlowId() + "'" + " AND LogNo=" + wfLogOld.getId().getLogNo();
		List<UwNotion> notionList = uwNotionService.findByConditions(sql);
		// ArrayList notionList = (ArrayList)
		// blUwNotionAction.findByConditions(sql);
		String notion = "";
		UwNotion uwNotion = null;
		String claimContent = "";
		// 下发修改，审核通过，得到数据传送对象返回
		SubmitTaskDto submitTaskDto = new SubmitTaskDto();
		if (notionList.size() > 0) {
			uwNotion = (UwNotion) notionList.get(0);
			notion = uwNotion.getHandleText();
			claimContent = wfLogOld.getOperatorName() + "  " + wfLogOld.getNodeName() + " " + wfLogOld.getHandleTime() + " " + notion;
			submitTaskDto.setClaimContent(claimContent);
		} else {
			submitTaskDto.setClaimContent("");
		}

		submitTaskDto.setFlowID(wfLogNew.getRelateFlowId());
		submitTaskDto.setInterMethod(interMethod);
		submitTaskDto.setBusinessNo(wfLogNew.getBusinessNo());
		submitTaskDto.setLogNo(wfLogNew.getRelateLogNo());
		submitTaskDto.setOperatorCode(wfLogOld.getOperatorCode());
		flag = undwrtSendClaimService.sendClaimData(submitTaskDto);
		// flag = new
		// com.sinosoft.claim.bl.action.custom.BLUndwrtSendClaimAction().sendClaimData(dbManager,
		// submitTaskDto);
		// 核赔工作流用的以前的引擎，用一起的对象传输
		return flag;
	}

	/*
	 * 按自定义SQL查询多条数据 @author 中科软
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize, boolean blnAll) throws Exception {
		// PageRecord pageRecord = new PageRecord(0, pageNo, 1, rowsPerPage,
		// new ArrayList());

		if (conditions.trim().length() == 0) {
			conditions = "1=1";
		}

		// DBManager dbManager = new DBManager();
		// BLWfLogAction blWfLogAction = new BLWfLogAction();
		// try {
		// dbManager.open(AppConfig.get("sysconst.DBJNDI"));
		// pageRecord = blWfLogAction.findByConditions(dbManager, conditions,
		// pageNo, rowsPerPage, blnAll);
		//
		// } catch (Exception exception) {
		// throw exception;
		// } finally {
		// dbManager.close();
		// }
		// List collection = this.findByConditions(conditions);
		// List arrayList = new ArrayList();
		// QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		String sql = "select * from WfLog where " + conditions;
		// Page page = HibernateUtils.findPagebySql(super.getSession(), sql,
		// pageNo, rowsPerPage, WfLog.class);
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, WfLog.class);
	}

	/**
	 * 流过滤器
	 * @param workflowList ArrayList
	 * @param dbManager DBManager
	 * @throws Exception
	 * @return ArrayList
	 * @author 中科软
	 */
	public List<WfLog> wfLogFilter(List<?> workflowList) throws Exception {
		List<WfLog> wflogList = new ArrayList<WfLog>(); // 新的ArrayList
		WfLog wfLog = null;
		int workflowSize = workflowList.size();
		Vector<String> vecLogList = new Vector<String>();
		for (int i = 0; i < workflowSize; i++) {
			wfLog = (WfLog) workflowList.get(i);
			if (!vecLogList.contains(wfLog.getId().getFlowId())) {
				wflogList.add(wfLog);
			}
			vecLogList.add(wfLog.getId().getFlowId());
		}
		return wflogList;
	}

	/**
	 * 按自定义SQL查询多条数据
	 * @param conditions 自定义SQL
	 * @param blnAll 自定义参数
	 * @return Collection 包含wfLogDto的集合
	 * @throws Exception
	 * @author 中科软
	 */
	public List<?> findByConditions(String statements, boolean blnAll) throws Exception {
		// DBWfLog dbWfLog = new DBWfLog(dbManager);
		// ArrayList logList = new ArrayList();
		// List collection = new ArrayList();
		// begin delete by xuning gpic 20060707
		// conditions += " ORDER BY flowintime desc ";
		// end delete by xuning
		List<?> logList = HibernateUtils.findbySql(super.getSession(), statements, WfLog.class);
		// logList = this.WflogFilter(logList, dbManager);
		int size = 0;
		String insureName = "";
		String businessNo = "";
		String businessType = "";
		size = logList.size();
		for (int i = 0; i < size; i++) {
			WfLog wfLog = (WfLog) logList.get(i);
			businessType = wfLog.getBusinessType();
			businessNo = wfLog.getBusinessNo();
			if (businessType.equals("Y")) {
				// PrpLprepay prpLprepay = new
				// BLPrpLprepayAction().findByPrimaryKey(dbManager,businessNo);
				PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(businessNo);
				if (prpLprepay != null) {
					businessNo = prpLprepay.getPolicyNo();
				}
			}
			if (businessType.equals("C")) {
				// PrpLcompensateDto prpLcompensateDto = new
				// BLPrpLcompensateAction().findByPrimaryKey(dbManager,businessNo);
				PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
				if (prpLcompensate != null) {
					businessNo = prpLcompensate.getPolicyNo();
				}

			}
			// insureName = new BLPrpCmainAction().findByPrimaryKey(dbManager,
			// businessNo).getInsuredName();
			insureName = prpCmainService.findByPrimaryKey(businessNo).getInsuredName();
			wfLog.setInsuredName(insureName);
		}
		return logList;
	}

	/**
	 * 获得组号列表
	 * @param conditions 查询条件
	 * @return Collection 包含uwGroupDto的集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<?> getPackageId(String conditions) throws Exception {
		String statement = " SELECT DISTINCT PackageId FROM Wflog where " + conditions;
		logger.debug(statement);
		List packageIdList = new ArrayList();
		WfLog wfLog = null;
		List<Object> list = (List<Object>) HibernateUtils.findbySql(super.getSession(), statement);
		for (int i = 0; i < list.size(); i++) {
			Object strs = list.get(i);
			if (strs != null) {
				wfLog = new WfLog();
				wfLog.setPackageId(strs.toString());
				packageIdList.add(wfLog);
			}
		}
		logger.info("DBWfLogBase.findByConditions() success!");
		return packageIdList;
	}

	/**
	 * 获取提交用户列表
	 * @param modelNo int
	 * @param nodeNo int
	 * @param businessType char
	 * @param businessNo String
	 * @param flag String
	 * @return Collection
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<?> getSubmitUserList(int modelNo, int nodeNo, String businessType, String businessNo, String flag) throws SQLException, Exception {
		String strWherePart = "";
		String strSQL = "";
		strWherePart = " a.BusinessNo='" + businessNo + "'" + " AND a.RiskCode = d.RiskCode" + " AND a.ComCode = d.ComCode" + " AND c.NodeNo=" + nodeNo + " AND c.ModelNo=" + modelNo + " AND c.UserCode=e.UserCode" + " AND d.GroupNo=c.GroupNo";
		if (flag != null && flag.equals("1")) {
			strSQL = "SELECT DISTINCT e.* FROM WfLog a,UwGrade c,UwGroup d,PrpDuser e WHERE " + strWherePart;
		} else {
			strWherePart = " AND c.ModelNo=" + modelNo + " AND c.NodeNo=" + nodeNo + " AND c.UserCode = e.UserCode" + " AND a.RiskCode = d.RiskCode " + " AND a.ComCode= d.ComCode" + " AND d.GroupNo=c.GroupNo";
			switch (businessType.charAt(0)) {
			case 'Y':
				strSQL = "SELECT DISTINCT e.* FROM PrpLprepay a,UwPrpInfo b,UwGrade c,UwGroup d,PrpDuser e WHERE " + " a.PreCompensateNo='" + businessNo + "'" + strWherePart;
				break;
			case 'C':
				strSQL = "SELECT DISTINCT e.* FROM PrpLcompensate a,UwPrpInfo b,UwGrade c,UwGroup d,PrpDuser e WHERE " + " a.CompensateNo='" + businessNo + "'" + strWherePart;
				break;
			default:
				throw new UserException(-98, -1167, "無此單證類型");
			}
		}
		List<?> submitUserList = (List<?>) prpDuserService.findByStatement(strSQL, 0, 0);
		return submitUserList;
	}

	public List<?> getWorkFlowQueryView(String sql) throws Exception {
		List<?> workflowList = new ArrayList<Object>();
		DBManager dbManager = new DBManager();
		try {
			dbManager.open(AppConfig.get("sysconst.DBJNDI"));
			workflowList = new BLWfLogAction().getWorkFlowQueryView(dbManager, sql);
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			dbManager.close();
			e.printStackTrace();
			throw e;
		} finally {
			dbManager.close();
		}
		return workflowList;
	}

	/**
	 * 按自定义SQL查询多条数据
	 * @param conditions 自定义SQL
	 * @param blnAll 自定义参数
	 * @return Collection 包含wfLogDto的集合
	 * @throws Exception
	 * @author 中科软
	 */
	public List<WfLog> findByStatement(String conditions) throws Exception {
		List<WfLog> list = this.findByStatement(conditions, 0, 0, true);
		return list;
	}

	/**
	 * 增加放弃任务功能
	 * @param conditions 自定义SQL
	 * @param
	 * @return
	 * @throws Exception
	 * @author 中科软
	 */
	public void undoTask(String flowId, int logNo) throws Exception {
		WfLogId wfLogId = new WfLogId();
		wfLogId.setFlowId(flowId);
		wfLogId.setLogNo(logNo);
		WfLog wfLog = this.findByWflog(wfLogId);
		wfLog.setDeptCode(null);
		wfLog.setDeptName(null);
		wfLog.setOperatorCode(null);
		wfLog.setOperatorName(null);
		wfLog.setNodeStatus("1");
		this.saveOrUpdate(wfLog);
	}

	/**
	 * 送再保需要查询数据
	 */
	public WfLog findByMaxLognoAndBusinessNo(String businessNo) throws Exception {
		String statement = "Select * From wflog Where logno=(Select Max(logno) From wflog Where businessno= '" + businessNo + "' ) And businessno='" + businessNo + "'";
		List<WfLog> list = (List<WfLog>) this.findByStatement(statement);
		WfLog wfLog = null;
		if (list.size() > 0) {
			wfLog = (WfLog) list.get(0);
		} else {
			wfLog = new WfLog();
		}
		return wfLog;
	}

	/**
	 * 提交节点
	 * @param flowID 流水号
	 * @param modelNo 使用模板号
	 * @param nodeNo 提交节点号
	 * @param businessType 业务类型
	 * @param businessNo 业务号
	 * @param flowStatus 流向
	 * @param flag 标志
	 * @param userCode 用户代码
	 * @param opertorCode 操作员代码
	 * @throws Exception
	 * @author 中科软
	 */
	public int submitTask(String flowID, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode, String opertorCode,Map<String,String> infoMap) throws Exception {
		String syn = AppConfig.get("sysconst.CLAIMVERIFY_PAYMENT");
		int retrunFlag = 0;
		boolean blnResult = false;
		// BLWfLogAction blWfLogAction = new BLWfLogAction();
		// 提交节点
		// BLSWfNodeAction blWfNodeAction = new BLSWfNodeAction();
		this.submitTaskNotReturn(flowID, modelNo, nodeNo, businessType, businessNo, flowStatus, flag, userCode, opertorCode,infoMap);
		retrunFlag = this.checkSubmitClaim(flowID, modelNo, nodeNo, businessType);
		blnResult = swfNodeService.checkEndflag(modelNo, nodeNo);
		// modify by liuwei at 2011-05-16 如果送收付数据是同步的话，将送收付接口和理赔处理代码放到同一个事务中
		// start
		if ("SYN".equals(syn) && blnResult) {// 同步调用收付取数方法
			PrpLintfProcess prpLinrfProcess = new PrpLintfProcess();
			DateTime thisTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND);
			prpLinrfProcess.setCertiType("C");
			prpLinrfProcess.setArriveDate(thisTime);
			prpLinrfProcess.setStatus("0");
			String subStr = (1000 + ((int) (Math.random() * 1000))) + "";
			prpLinrfProcess.setBusinessNo(thisTime.getTime() + "" + subStr);
			prpLinrfProcess.setCertiNo(businessNo);
			prpLintfProcessService.save(prpLinrfProcess);
			char chCertiType = businessType.charAt(0);
			switch (chCertiType) {
			case 'C':
				PayMentServiceManager.getService().send("C", businessNo);
				break;
			case 'Y':
				PayMentServiceManager.getService().send("Y", businessNo);
				break;
			default:
			}
			prpLinrfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
			prpLinrfProcess.setStatus("1");
			prpLintfProcessService.update(prpLinrfProcess);
		}
		// modify by liuwei at 2011-05-16 如果送收付数据是同步的话，将送收付接口和理赔处理代码放到同一个事务中
		// end

		// 异步调用收付取数方法
		if (blnResult && "ASYN".equals(syn)) {
			PrpLintfProcess prpLinrfProcess = new PrpLintfProcess();
			DateTime thisTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND);
			prpLinrfProcess.setCertiType("C");
			prpLinrfProcess.setArriveDate(thisTime);
			prpLinrfProcess.setStatus("0");
			try {
				String subStr = (1000 + ((int) (Math.random() * 1000))) + "";
				prpLinrfProcess.setBusinessNo(thisTime.getTime() + "" + subStr);
				prpLinrfProcess.setCertiNo(businessNo);
				prpLintfProcessService.save(prpLinrfProcess);
				char chCertiType = businessType.charAt(0);
				switch (chCertiType) {
				case 'C':
					PayMentServiceManager.getService().send("C", businessNo);
					break;
				case 'Y':
					PayMentServiceManager.getService().send("Y", businessNo);
					break;
				default:
				}
				prpLinrfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
				prpLinrfProcess.setStatus("1");
				prpLintfProcessService.update(prpLinrfProcess);
			} catch (Exception e) {
				prpLinrfProcess.setLastOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_MILLISECOND));
				String exceptionContext = "";
				if (e instanceof com.sinosoft.sysframework.exceptionlog.UserException) {
					com.sinosoft.sysframework.exceptionlog.UserException sysUserException = (com.sinosoft.sysframework.exceptionlog.UserException) e;
					exceptionContext = sysUserException.getErrorMessage();
				} else if (e instanceof com.sinosoft.utility.error.UserException) {
					com.sinosoft.utility.error.UserException errorUserException = (com.sinosoft.utility.error.UserException) e;
					exceptionContext = errorUserException.getErrorMessage();
				} else {
					exceptionContext = e.getLocalizedMessage();
				}
				prpLinrfProcess.setErrorMessage(exceptionContext);
				prpLintfProcessService.update(prpLinrfProcess);
			}
		}
		return retrunFlag;
	}

	/**
	 * 提交任务处理
	 * @param dbManager DBManager
	 * @param iFlowId String
	 * @param iModelNo int
	 * @param iNodeNo int
	 * @param iBusinessType String
	 * @param iBusinessNo String
	 * @param iFlowStatus String
	 * @param flag String //0表示从业务系统提交到双核，1表示双核系统内部提交
	 * @param iUserCode String
	 * @param iOpertorCode String
	 * @throws SQLException
	 * @throws Exception
	 */
	public void submitTaskNotReturn(String flowId, int modelNo, int nodeNo, String businessType, String businessNo, String flowStatus, String flag, String userCode, String opertorCode,Map<String,String> infoMap) throws SQLException, Exception, UserException {
		UndwrtSubmitDto undwrtSubmitDto = new UndwrtSubmitDto();
		undwrtSubmitDto.setCertiType(businessType);
		undwrtSubmitDto.setBusinessNo(businessNo);
		// 简易赔案提交报错修改 start
		if ("".equals(userCode) || userCode == null) {
			undwrtSubmitDto.setUserCode(opertorCode);
		} else {
			undwrtSubmitDto.setUserCode(userCode);
		}
		// 简易赔案提交报错修改 end
		this.submit(flowId, modelNo, nodeNo, flowStatus, flag, opertorCode, undwrtSubmitDto,infoMap);
	}

	/**
	 * 任务提交
	 * @param flowID 工作流ID
	 * @param modelNo 模版号
	 * @param nodeNo 节点号
	 * @param certiType 单证类型
	 * @param businessNo 业务号
	 * @param flowStatus 流转状态标志 0:正常流转 1:回退
	 * @param flag 操作标志(0:复核/修改後的提交,1:核保核赔中的提交)
	 * @param userCode 用户代码
	 * @param handleCode 提交人员代码
	 * @param operatorCode String Return 无
	 * @throws UserException,Exception
	 */
	private boolean submit(String undwrtFlowID, int modelNo, int nodeNo, String flowStatus, String iflag, String operatorCode, UndwrtSubmitDto undwrtSubmitDto,Map<String,String> infoMap) throws Exception {
		boolean blnReturn = false;
		String strWherePart = "";
		int intCount = 0;
		char chCertiType = undwrtSubmitDto.getCertiType().charAt(0);
		WfLog wfLogOld = new WfLog();
		WfLog wfLogNew = new WfLog();
		String strUnderWriteCode = "";
		DateTime underWriteDate = DateTime.current();
		strWherePart = "FlowID='" + undwrtFlowID.trim() + "'";
		intCount = this.getCount(strWherePart);
		wfLogOld = this.findByPrimaryKey(undwrtFlowID, intCount);
		// --1.生成新日志
		this.generate(undwrtFlowID, modelNo, nodeNo, flowStatus, operatorCode, undwrtSubmitDto.getClaimFlag(), undwrtSubmitDto.getLFlowID(), undwrtSubmitDto.getLLogNo());
		intCount++;
		// wfLogNewDto = dbWfLogNew.findByPrimaryKey(undwrtFlowID,
		// intCount);
		wfLogNew = this.findByPrimaryKey(undwrtFlowID, intCount);
		boolean blnResult = swfNodeService.checkEndflag(modelNo, nodeNo);
		 // --2.1.如果当前节点TO不是核保通过节点
		if (!blnResult){
			//"非审核通过节点" + nodeNo);
			// this.nodeType = "0";
			// --2.1.1.判断当前节点TO是否为打回修改节点
			if (nodeNo == 1) {
				strUnderWriteCode = undwrtSubmitDto.getUserCode();
				prpFeedBackService.echo(chCertiType, undwrtSubmitDto.getBusinessNo(), "2", strUnderWriteCode, underWriteDate,infoMap);
			} else {
				if (!wfLogNew.getNodeStatus().equals("0") && (wfLogNew.getId().getLogNo() == 2 || wfLogNew.getNodeNo() == 1)) {
					prpFeedBackService.echoSubmit(chCertiType, undwrtSubmitDto.getBusinessNo());
				} else {
					//"条件不成立");
				}
			}
		} else {
			if (iflag.equals("0")) {
				switch (chCertiType) {
				case 'C':
					PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(undwrtSubmitDto.getBusinessNo());
					strUnderWriteCode = prpLcompensate.getApproverCode();
					break;
				case 'Y':
					PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(undwrtSubmitDto.getBusinessNo());
					strUnderWriteCode = prpLprepay.getApproverCode();
					break;
				default:
				}
			}
			if (iflag.equals("1")) {
				strUnderWriteCode = undwrtSubmitDto.getUserCode();

			}
			// PrpDuserDto prpDuserDto = new PrpDuserDto();
			if (strUnderWriteCode == null || strUnderWriteCode.equals("")) {
				strUnderWriteCode = undwrtSubmitDto.getUserCode();
			}
			// DBPrpDuser dbPrpDuser = new DBPrpDuser(dbManager);
			PrpDuser prpDuser = prpDuserService.findPrpDuser(strUnderWriteCode);
			// prpDuserDto = new
			// BLPrpDuserAction().findByPrimaryKey(dbManager,
			// strUnderWriteCode);
			wfLogOld.setOperatorCode(strUnderWriteCode);
			wfLogOld.setOperatorName(prpDuser.getUserName());
			// dbWfLogOld.update(wfLogOldDto);
			// new BLWfLogAction().update(dbManager, wfLogOldDto);
			this.update(wfLogOld);
			// 关闭工作流
			this.close(undwrtFlowID);
			if (iflag.equals("0")) {
				prpFeedBackService.echo(chCertiType, undwrtSubmitDto.getBusinessNo(), "3", strUnderWriteCode, underWriteDate,infoMap);
			}
			if (iflag.equals("1")) {
				prpFeedBackService.echo(chCertiType, undwrtSubmitDto.getBusinessNo(), "1", strUnderWriteCode, underWriteDate,infoMap);
			}
		}
		messageService.send(wfLogNew, wfLogOld);
		return blnReturn;
	}

	/**
	 * 生成工作流中一个节点
	 * @param iFlowID,iModelNo,iNodeNo,iFlowStatus
	 * @throws SQLException,Exception
	 */
	private boolean generate(String undwrtFlowID, int iModelNo, int iNodeNo, String iFlowStatus, String iHandleCode, String claimFlag, String claimFlowID, int claimLogNo) throws SQLException, UserException, Exception {
		boolean blnReturn = false;
		int intCount = 0;
		String strNowTime = "";
		// DBWfLog dbWfLogCurr = new DBWfLog(dbManager);
		// DBWfLog dbWfLogNext = new DBWfLog(dbManager);
		// DBSWfNode dbWfNode = new DBSWfNode(dbManager);
		WfLog wfLogCurr = new WfLog();
		WfLog wfLogNext = new WfLog();
		SwfNode wfNode = new SwfNode();
		strNowTime = DateTime.current().toString().substring(0, 19);
		String strWherePart = "FlowID='" + undwrtFlowID.trim() + "'";
		intCount = this.getCount(strWherePart);
		wfLogCurr = this.findByPrimaryKey(undwrtFlowID, intCount);
		wfNode = swfNodeService.findByPrimaryKey(iModelNo, iNodeNo);
		// 为生成新的节点做数据准备
		intCount += 1;
		wfLogNext.getId().setFlowId(undwrtFlowID);
		wfLogNext.getId().setLogNo(intCount);
		wfLogNext.setModelNo(iModelNo);
		wfLogNext.setNodeNo(wfNode.getId().getNodeNo());
		wfLogNext.setNodeName(wfNode.getNodeName());
		wfLogNext.setBusinessType(wfLogCurr.getBusinessType());
		wfLogNext.setBusinessNo(wfLogCurr.getBusinessNo());
		wfLogNext.setFlowInTime(strNowTime);
		wfLogNext.setTimeLimit(wfNode.getTimeLimit());
		wfLogNext.setNodeStatus("1");
		wfLogNext.setFlowStatus(iFlowStatus);
		wfLogNext.setOperatorCode(iHandleCode);
		wfLogNext.setPackageId(wfLogCurr.getPackageId());
		wfLogNext.setContractNo(wfLogCurr.getContractNo());
		wfLogNext.setClassCode(wfLogCurr.getClassCode());
		wfLogNext.setRiskCode(wfLogCurr.getRiskCode());
		wfLogNext.setHandlerCode(wfLogCurr.getHandlerCode());
		wfLogNext.setHandler1Code(wfLogCurr.getHandler1Code());
		wfLogNext.setComCode(wfLogCurr.getComCode());
		wfLogNext.setMakeCom(wfLogCurr.getMakeCom());
		wfLogNext.setInsuredCode(wfLogCurr.getInsuredCode());
		wfLogNext.setInsuredName(wfLogCurr.getInsuredName());
		wfLogNext.setLicenseNo(wfLogCurr.getLicenseNo());
		wfLogNext.setRiskCategory(wfLogCurr.getRiskCategory());
		wfLogNext.setIdentifyType(wfLogCurr.getIdentifyType());
		wfLogNext.setIdentifyNumber(wfLogCurr.getIdentifyNumber());
		wfLogNext.setReinsStatus(wfLogCurr.getReinsStatus());
		wfLogNext.setPolicyNo(wfLogCurr.getPolicyNo());
		wfLogNext.setClaimNo(wfLogCurr.getClaimNo());
		if(iNodeNo == 1){//如果是退回理赔员
			WfLog claim = this.findByPrimaryKey(undwrtFlowID, 1);
			wfLogNext.setDeptCode(claim.getDeptCode());
			wfLogNext.setDeptName(claim.getDeptName());
			wfLogNext.setOperatorCode(claim.getOperatorCode());
			wfLogNext.setOperatorName(claim.getOperatorName());
		}
		if (claimFlowID != null && !claimFlowID.equals("")) {
			wfLogNext.setRelateFlowId(claimFlowID);
			wfLogNext.setRelateLogNo(claimLogNo);
		} else {
			wfLogNext.setRelateFlowId(wfLogCurr.getRelateFlowId());
			wfLogNext.setRelateLogNo(wfLogCurr.getRelateLogNo());
		}
		// dbWfLogNext.insert(wfLogNextDto);
		this.save(wfLogNext);
		wfLogCurr.setHandleTime(strNowTime);
		wfLogCurr.setNodeStatus("4");
		wfLogCurr.setSubmitTime(strNowTime);
		this.update(wfLogCurr);
		// dbWfLogCurr.update(wfLogCurrDto);
		blnReturn = true;
		return blnReturn;
	}

	/**
	 *关闭工作流服务
	 *@param DBManager 数据管理器
	 *@param flowID 工作流号
	 *@throws SQLException,Exception
	 *@return
	 */
	public void close(String flowID) throws SQLException, Exception {
		int intCount = 0;
		String strWherePart = "";
		String strSubmitTime = DateTime.current().toString(DateTime.DAY_TO_SECOND);
		strWherePart = "FlowID='" + flowID + "'";
		this.updateNodeStatusByFlowID(flowID);
		intCount = this.getCount(strWherePart);
		WfLog wfLog = this.findByPrimaryKey(flowID, intCount);
		wfLog.setSubmitTime(strSubmitTime);
		this.update(wfLog);
		WfFlowMain wfFlowMain = wfFlowMainService.findByPrimaryKey(flowID);
		if (wfFlowMain != null) {
			wfFlowMain.setStoreFlag("1");// 设置转储标志，1/需要转储 2/已转储
			wfFlowMain.setFlowStatus("0");
			wfFlowMain.setCloseDate(strSubmitTime);
			wfFlowMainService.update(wfFlowMain);
		} else {
			throw new Exception("數據不一置，請聯繫管理員");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.sinosoft.claim.schema.service.facade.WfLogService#getCount(java.lang
	 * .String) 更具条件查询有多少条
	 */
	public int getCount(String conditions) throws Exception {
		if (conditions.trim().length() == 0) {
			conditions = "1=1";
		}
		String sql = "select count(1) FROM WfLog WHERE " + conditions;
		super.getSession().flush();//刷新hibernate的缓存，查询出当前保存对象的条数。不可删除。
		Long count = HibernateUtils.getCountbyCountSql(super.getSession(), sql);
		return count.intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.sinosoft.claim.schema.service.facade.WfLogService#findByStatementQta
	 * (java.lang.String, int, int, boolean) 查询条数
	 */
	public List<WfLog> findByStatementQta(String statement, int pageNo, int rowsPerPage, boolean blnAll) throws Exception {
		return this.findByStatement(statement, pageNo, rowsPerPage, blnAll);
	}

	/**
	 * 双核接口方法
	 * @param modelType String
	 * @param certiType String
	 * @param businessNo String
	 * @param riskCode String
	 * @param classCode String
	 * @param comCode String
	 * @param makecom String
	 * @param userCode String
	 * @param handlerCode String
	 * @param handler1Code String
	 * @param contractNo String
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 * @return String
	 */
	public String start(UndwrtSubmitDto undwrtSubmitDto,Map<String,String> infoMap) throws UserException, SQLException, Exception {
		// BLSWfPathAction blWfPathAction = new BLSWfPathAction();
		// BLWfLogAction blWfLogAction = new BLWfLogAction();
		List<SwfPath> wfPathList = new ArrayList<SwfPath>();
		SwfPath wfPath = new SwfPath();
		String startType = "";
		int modelNo = 0;
		int intStartNodeNo = 1; // 起始节点默认为1
		int intEndNodeNo = 0;
		int j = 0;
		String defaultFlag = "1";
		// BLWfModelAndUseAction blWfModelAndUseAction = new
		// BLWfModelAndUseAction();
		// add by liping 简易赔案自动核赔模板固定，所以直接设定模板号，不再读取 080625
		if ("23".equals(undwrtSubmitDto.getModelType())) {
			modelNo = 34;
		} else {
			// 1-根据模版类型,险种,部门编码获得模版号
			modelNo = swfModelUseService.getModelNo(undwrtSubmitDto.getModelType(), undwrtSubmitDto.getRiskCode(), undwrtSubmitDto.getComCode());
		}
		String conditions = "";
		List<SwfModelUse> collection = null;
		// UIWorkFlowModelAction uiWorkFlowModelAction = new
		// UIWorkFlowModelAction();
		if (modelNo == 0) {
			conditions = "riskcode ='" + undwrtSubmitDto.getRiskCode() + "' And modeltype='22' And modelstatus='1'";
			collection = swfModelUseService.findByModelUseConditions(conditions);
			if (collection.size() > 0) {
				SwfModelUse swfModelUse = collection.get(0);
				modelNo = swfModelUse.getId().getModelNo();
				SwfModelUse swfModelUseNew = new SwfModelUse(swfModelUse);
				swfModelUseNew.getId().setComCode(undwrtSubmitDto.getComCode());
				swfModelUseNew.getId().setRiskCode(undwrtSubmitDto.getRiskCode());
				swfModelUseService.saveOrUpdate(swfModelUseNew);
			} else {
				String message = "沒有找到險種:" + undwrtSubmitDto.getRiskCode() + "、審核部門:" + undwrtSubmitDto.getComCode() + " 所對應的核賠模版請確認是否為該險種，審核部門配置了核賠模版。";
				throw new Exception(message);
			}
		}
		// 2-判断工作流是启动还是修改
		startType = this.checkStartType(undwrtSubmitDto.getBusinessNo());
		wfPathList = swfPathService.getPathes(modelNo, intStartNodeNo, undwrtSubmitDto.getCertiType(), undwrtSubmitDto.getBusinessNo(), defaultFlag, undwrtSubmitDto.getComCode());
		Iterator<SwfPath> itwfpath = wfPathList.iterator();
		while (itwfpath.hasNext()) {
			j++;
			wfPath = (SwfPath) itwfpath.next();
			if (j == 1) {
				intEndNodeNo = Integer.parseInt(String.valueOf(wfPath.getEndNodeNo()));
				break;
			}
		}
		if (j == 0) {
			throw new UserException(-98, -1004, this.getClass().getName());
		}

		// add by zhulei 20060509 end
		// 允许自动核保时，EndNodeNo取当前模板的终止节点，否则按原配置条件找EndNodeNo

		String flowId = this.dealFirstTrans(modelNo, startType, intEndNodeNo, "0", undwrtSubmitDto,infoMap);
		return flowId;
	}

	/**
	 * @desc 判断工作流为新启动还是待修改
	 * @param iBusinessNo 业务号
	 * @param dbManager DBManager
	 * @return flag(U:修改,N:新启动,0:出错)
	 * @author 中科软
	 */
	public String checkStartType(String iBusinessNo) throws SQLException, UserException, Exception {
		String startType = "";
		String strWherePart = "";
		int intCount = 0;
		int intCount1 = 0;
		// DBWfLog dbWfLog = new DBWfLog(dbManager);
		strWherePart = " BusinessNo ='" + iBusinessNo + "'" + " AND NodeStatus<>'0'" + " AND NodeStatus<>'4'";
		intCount = this.getCount(strWherePart);
		if (intCount == 0) {
			startType = "N";
		} else {
			strWherePart = strWherePart.trim() + " AND LogNo=2";
			intCount1 = this.getCount(strWherePart);
			if (intCount1 == 0) {
				startType = "U";
			} else {
				throw new UserException(-98, -1106, this.getClass().getName());
			}
		}
		return startType;
	}

	/**
	 * @desc 对复核後的任务进行处理
	 * @author 中科软
	 * @param iModelNo 模板号
	 * @param iCertiType 单证类型
	 * @param iBusinessNo 业务号
	 * @param iFlag 是修改还是新启动标志
	 * @param iNodeNo 起始节点
	 * @param iOption 是出单员提交还是双核内部提交
	 * @param iComCode 部门代码
	 * @return WfLogSchema对象,对象中的有效属性是FlowID,NodeNo
	 * @throws UserException,Exception
	 */
	public String dealFirstTrans(int iModelNo, String iFlag, int iNodeNo, String iOption, UndwrtSubmitDto undwrtSubmitDto,Map<String,String> infoMap) throws SQLException, UserException, Exception {
		String strFlowID = "";
//		String strPolicyNo = "";
//		String strWhere = "";
		WfLog wfLog = this.dealFirst(iModelNo, iFlag, undwrtSubmitDto);
		strFlowID = wfLog.getId().getFlowId();
//		strPolicyNo = wfLog.getPolicyNo();

		// 简易赔案时将自动核赔的标志位置为4 add by zhangruifeng
		// DBSwfFlowMain dbSwfFlowMain = new DBSwfFlowMain(dbManager);
		// SwfFlowMain swfFlowMain = new SwfFlowMainDto();
		// swfFlowMainDto =
		// dbSwfFlowMain.findByPrimaryKey(undwrtSubmitDto.getLFlowID());
		SwfFlowMain swfFlowMain = swfFlowMainService.findSwfFlowMain(undwrtSubmitDto.getLFlowID());
		if ("03".equals(swfFlowMain.getClaimTypeFlag())) {
			this.submitTask(strFlowID, iModelNo, iNodeNo, "C", wfLog.getBusinessNo(), "0", "0", wfLog.getUserCode(), wfLog.getOperatorCode(),infoMap);
			this.checkSubmitClaim(strFlowID, iModelNo, iNodeNo, "C");
		} else {
			this.submit(strFlowID, iModelNo, iNodeNo, "0", iOption, "", undwrtSubmitDto,infoMap);
		}

		return strFlowID;
	}

	/**
	 * @author 中科软
	 * @desc 由业务提交核保任务处理
	 * @param modelNo 模板号
	 * @param certiType 单证类型
	 * @param businessNo 业务号
	 * @param flag 是修改还是新启动标志
	 * @param riskCode 险种
	 * @param classCode 险类
	 * @param ComCode 归属部门
	 * @param makeCom 出单机构
	 * @param userCode 业务操作人员
	 * @param handlerCode业务经办人员
	 * @param handlerCode业务归属人员
	 * @param contractNo 合同号
	 * @return WfLogDto对象
	 * @throws UserException,Exception
	 */
	private WfLog dealFirst(int modelNo, String undwrtFlag, UndwrtSubmitDto undwrtSubmitDto) throws SQLException, UserException, Exception {
		// DBPrpDuser dbPrpDuser = new DBPrpDuser(dbManager);
		// DBPrpDcompany dbPrpDcompany = new DBPrpDcompany(dbManager);
		// DBSWfNode dbWfNode = new DBSWfNode(dbManager);
		// DBWfLog dbWfLog = new DBWfLog(dbManager);
		// DBWfFlowMain dbWfFlowMain = new DBWfFlowMain(dbManager);

		// SWfNodeDto wfNodeDto = new SWfNodeDto();

		// PrpDcompanyDto prpDcompanyDto = new PrpDcompanyDto();

		// BLWfPackageAction blWfPackageAction = new BLWfPackageAction();
		WfLog wfLog = new WfLog();
		String strFlowID = "";
		String strWfPackageID = "";
		String userName = "";
		String insuredCode = "";
		String insuredName = "";
		String policyNo = undwrtSubmitDto.getPolicyNo();
		String riskCategory = this.getRiskCategoryByRiskCode(undwrtSubmitDto.getRiskCode());
		String[] identifyTypeNumber = null;
		// WfPackage是否要保存
		strWfPackageID = wfPackageService.create(modelNo, undwrtSubmitDto.getCertiType(), undwrtSubmitDto.getBusinessNo(), undwrtSubmitDto.getComCode());
		PrpDuser prpDuser = null;
		prpDuser = prpDuserService.findPrpDuser(undwrtSubmitDto.getUserCode());
		userName = prpDuser.getUserName();

		wfLog.setRiskCategory(riskCategory);
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(undwrtSubmitDto.getClaimNo());
		// List wfLogList = new ArrayList();
		// PrpLclaimDto prpLclaimDto = new
		// BLPrpLclaimAction().findByPrimaryKey(dbManager,
		// undwrtSubmitDto.getClaimNo());
		// 启动工作流
		if (undwrtFlag.equals("N")) {
			//"＝＝＝＝＝＝＝＝＝ *启动工作流* ＝＝＝＝＝＝＝＝＝");
			strFlowID = this.getSoleFlowID(undwrtSubmitDto.getComCode());
			SwfNode wfNode = swfNodeService.findByPrimaryKey(modelNo, 1);
			wfLog.getId().setLogNo(1);
			wfLog.setModelNo(modelNo);
			wfLog.setNodeNo(1);
			wfLog.setNodeName(wfNode.getNodeName());
			wfLog.setBusinessType(undwrtSubmitDto.getCertiType());
			wfLog.setBusinessNo(undwrtSubmitDto.getBusinessNo());
			wfLog.setDeptCode(undwrtSubmitDto.getComCode());
			PrpDcompany prpDcompany = prpDcompanyService.findByPrimaryKey(undwrtSubmitDto.getComCode());
			// prpDcompanyDto = new
			// BLPrpDcompanyAction().findByPrimaryKey(dbManager,
			// undwrtSubmitDto.getComCode());
			wfLog.setDeptName(prpDcompany.getComCName());
			wfLog.setOperatorCode(undwrtSubmitDto.getUserCode());
			wfLog.setOperatorName(userName);
			wfLog.setFlowInTime(DateTime.current().toString(DateTime.YEAR_TO_SECOND));
			wfLog.setHandleTime(DateTime.current().toString(DateTime.YEAR_TO_SECOND));
			wfLog.setTimeLimit(wfNode.getTimeLimit());
			wfLog.setNodeStatus("3");
			wfLog.setFlowStatus("0");
			wfLog.setPackageId(strWfPackageID);
			wfLog.getId().setFlowId(strFlowID);
			if (undwrtSubmitDto.getContractNo().length() > 0) {
				wfLog.setContractNo(undwrtSubmitDto.getContractNo());
				// 以下信息为业务传送过来的信息
			}
			wfLog.setMakeCom(undwrtSubmitDto.getMakecom());
			wfLog.setComCode(undwrtSubmitDto.getComCode());
			wfLog.setRiskCode(undwrtSubmitDto.getRiskCode());
			wfLog.setClassCode(undwrtSubmitDto.getClassCode());
			wfLog.setHandler1Code(undwrtSubmitDto.getHandler1Code());
			wfLog.setHandlerCode(undwrtSubmitDto.getHandlerCode());

			if (undwrtSubmitDto.getCertiType().equals("C")) {
				/*
				 * modify by zhangyurui 2009-02-21 begin
				 * 团单等情况存在多个被保险人，所以从立案取被保险人
				 */

				insuredCode = prpLclaim.getInsuredCode();
				insuredName = prpLclaim.getInsuredName();
				/*
				 * modify by zhangyurui 2009-02-21 end 团单等情况存在多个被保险人，所以从立案取被保险人
				 */
				wfLog.setInsuredCode(insuredCode);
				wfLog.setInsuredName(insuredName);
				identifyTypeNumber = this.getIdentifyTypeNumber(riskCategory, policyNo, "P");
				wfLog.setIdentifyType(identifyTypeNumber[0]);
				wfLog.setIdentifyNumber(identifyTypeNumber[1]);
				wfLog.setPolicyNo(policyNo);
				wfLog.setClaimNo(undwrtSubmitDto.getClaimNo());
			} else if (undwrtSubmitDto.getCertiType().equals("Y")) {// add
				// by
				// xuning
				// 增加预赔的功能
				// PrpLprepayDto prpLprepayDto = new
				// BLPrpLprepayAction().findByPrimaryKey(dbManager,
				// undwrtSubmitDto.getBusinessNo());
				PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(undwrtSubmitDto.getBusinessNo());
				policyNo = prpLprepay.getPolicyNo();
				/*
				 * modify by zhangyurui 2009-02-21 begin
				 * 团单等情况存在多个被保险人，所以从立案取被保险人
				 */
				insuredCode = prpLclaim.getInsuredCode();
				insuredName = prpLclaim.getInsuredName();
				/*
				 * modify by zhangyurui 2009-02-21 end 团单等情况存在多个被保险人，所以从立案取被保险人
				 */
				wfLog.setInsuredCode(insuredCode);
				wfLog.setInsuredName(insuredName);

				identifyTypeNumber = this.getIdentifyTypeNumber(riskCategory, policyNo, "P");
				wfLog.setIdentifyType(identifyTypeNumber[0]);
				wfLog.setIdentifyNumber(identifyTypeNumber[1]);
				wfLog.setPolicyNo(prpLprepay.getPolicyNo());
				wfLog.setClaimNo(prpLprepay.getClaimNo());
			}
			//
			// 理赔工作流数据
			if (undwrtSubmitDto.getClaimFlag().equals("claim")) {
				//"undwrtSubmitDto.getLFlowID()==" + undwrtSubmitDto.getLFlowID());
				wfLog.setRelateFlowId(undwrtSubmitDto.getLFlowID());
				wfLog.setRelateLogNo(undwrtSubmitDto.getLLogNo());
			}
			// dbWfLog.insert(wfLogDto);
			this.save(wfLog);

			// chengkai;2006-07-20;如果是出单员，则插入出单员意见。begin
			if (wfLog.getNodeNo() == 1) {
				// BLUwNotionAction blUwNotionAction = new
				// BLUwNotionAction();
				uwNotionService.insertUwNotionByMakeUser(wfLog, undwrtSubmitDto.getCertiType());
			}

			WfFlowMain wfFlowMain = new WfFlowMain();
			wfFlowMain.setFlowId(strFlowID);
			if (undwrtSubmitDto.getCertiType().equals("C")) {
				wfFlowMain.setFlowName("核賠工作流");
			}
			if (undwrtSubmitDto.getCertiType().equals("Y")) {
				wfFlowMain.setFlowName("核賠工作流");
			}
			wfFlowMain.setFlowStatus("1");
			wfFlowMain.setCreatDate(DateTime.current().toString(DateTime.YEAR_TO_SECOND));
			wfFlowMainService.insert(wfFlowMain);
			// dbWfFlowMain.insert(wfFlowMainDto);
		}
		if (undwrtFlag.equals("U")) {
			//"＝＝＝＝＝＝＝＝ *修改工作流* ＝＝＝＝＝＝＝＝");
			String strSQL = " BusinessNo='" + undwrtSubmitDto.getBusinessNo() + "'" + " AND NodeStatus<>'0' " + " AND NodeStatus<>'4'";
			List<WfLog> wfLogList = this.findByConditions(strSQL);
			Iterator<WfLog> itwflog = wfLogList.iterator();
			while (itwflog.hasNext()) {
				wfLog = (WfLog) itwflog.next();
				wfLog.setDeptCode(undwrtSubmitDto.getComCode());
				wfLog.setHandlerCode(undwrtSubmitDto.getHandlerCode());
				wfLog.setOperatorName(userName);
				wfLog.setHandleTime(DateTime.current().toString(DateTime.YEAR_TO_SECOND));
				wfLog.setNodeStatus("3");
				wfLog.setFlowStatus("0");
				wfLog.setPackageId(strWfPackageID);
				// modify by xukefeng 20061107 下发修改、业务修改被保险人，重新提交时，要重新获取被保险人
				// begin
				insuredCode = prpLclaim.getInsuredCode();
				insuredName = prpLclaim.getInsuredName();
				// modify by xukefeng 20060515 下发修改、业务修改被保险人，重新提交时，要重新获取被保险人
				// end
				// modify by zhulei 20060515 下发修改、业务修改被保险人，重新提交时，要重新获取被保险人
				// begin
				wfLog.setInsuredCode(insuredCode);
				wfLog.setInsuredName(insuredName);
				// modify by zhulei 20060515 下发修改、业务修改被保险人，重新提交时，要重新获取被保险人
				// 理赔工作流数据
				if (undwrtSubmitDto.getClaimFlag().equals("claim")) {
					wfLog.setRelateFlowId(undwrtSubmitDto.getLFlowID());
					wfLog.setRelateLogNo(undwrtSubmitDto.getLLogNo());
				}
				if (undwrtSubmitDto.getContractNo().length() > 0) {
					wfLog.setContractNo(undwrtSubmitDto.getContractNo());
				}
				this.update(wfLog);

				// chengkai;2006-07-20;如果是出单员，则插入出单员意见。begin
				if (wfLog.getNodeNo() == 1) {
					// BLUwNotionAction blUwNotionAction = new
					// BLUwNotionAction();
					uwNotionService.insertUwNotionByMakeUser(wfLog, undwrtSubmitDto.getCertiType());
				}
				// end
			}
		}
		return wfLog;
	}

	/**
	 * 核赔查询时用到此方法
	 * @param statement
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageRecord findByStatementFromWflog(String statement ,int pageNo ,int rowsPerPage) throws Exception{
		Page page = this.findWfLogObjectAllByStatement(statement, pageNo, rowsPerPage);
		List<WfLog> bigCollection = (List<WfLog>)page.getResult();
        int totalCount = Integer.parseInt(""+page.getTotalCount());
        List collection = new ArrayList();
        PrpDcompany prpDcompany = null;
        WfLog wfLog = null;
        for(int i=0; i<totalCount; i++){
            if(i >= bigCollection.size()){
                break;
            }
            wfLog = (WfLog)bigCollection.get(i);
            prpDcompany = this.getPrpDcompanyService().findByPrimaryKey(wfLog.getComCode());
            wfLog.setComName(prpDcompany.getComCName());
            collection.add(bigCollection.get(i));
        }
        return new PageRecord(totalCount, pageNo, 1, rowsPerPage, collection);
    }
	
	/**
     *从View_wfLogAll视图里查询数据
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	public PageRecord findView_wfLogAll(String statement ,int pageNo ,int rowsPerPage) throws Exception{
    	Page page = this.findWfLogObjectAllByStatement(statement, pageNo , rowsPerPage);
    	List<WfLog> bigCollection = page.getResult();
        int totalCount = Integer.parseInt(""+page.getTotalCount());
        List<WfLog> collection = new ArrayList<WfLog>();
        PrpDcompany prpDcompany = null;
        WfLog wfLog = null;
        String strAcciName = "";
        for(int i=0; i<bigCollection.size(); i++){
            if(i >= bigCollection.size()){
                break;
            }
            wfLog = (WfLog)bigCollection.get(i);
            prpDcompany = this.getPrpDcompanyService().findByPrimaryKey(wfLog.getComCode());
            wfLog.setComName(prpDcompany.getComCName());
            //modify by dengwenchun 2006-4-11 cause: 核赔查询列表中增加事故者姓名(当险类为27并且是核赔业务时，核赔查询列表中被保险人名称的值，根据立案号从prplacciperson表中获得AcciName) begin
            if(wfLog.getClassCode().equals("27")&&(wfLog.getBusinessType().equals("C")||wfLog.getBusinessType().equals("Y"))){
            	strAcciName = this.getPrpLacciPersonService().findPrpLacciPerson(wfLog.getClaimNo()).getAcciName();
     		      if(strAcciName!=null&&!strAcciName.equals("")){
     		    	 wfLog.setInsuredName(strAcciName);
     		      }
     	      }
            //end modify by dengwenchun 2006-4-11 cause: 核赔查询列表中增加事故者姓名(当险类为27并且是核赔业务时，核赔查询列表中被保险人名称的值，根据立案号从prplacciperson表中获得AcciName)
            collection.add(bigCollection.get(i));
        }
        return new PageRecord(totalCount, pageNo, 1, rowsPerPage, collection);
    }
    
    /**
	 * 按自定义SQL查询多条数据
	 * @param statement 自定义SQL（含Select）
	 * @param pageNo 页号
	 * @param rowsPerPage 每页的行数
	 * @param blnAll  BOOLEAN
	 * @return Collection
	 * @throws Exception
	 */
	public Page findWfLogObjectAllByStatement(String statement,int pageNo ,int rowsPerPage) throws Exception{
		List<WfLog> wfLogAll = new ArrayList<WfLog>();
		Page page = HibernateUtils.findPagebySql(super.getSession(),statement, pageNo, rowsPerPage);
		List<?> result = page.getResult();
		if(result != null && !result.isEmpty()){
			Object[] object = null;
			for (Iterator<?> it = result.iterator(); it.hasNext(); wfLogAll.add(this.getWfLogObject(object))) {
				object = (Object[]) it.next();
			}
		}
		return new Page(page.getStart(), page.getTotalCount(), page.getPageSize(), wfLogAll);
    }
	/**
	 * 为了把从view_wfLogAll视图查到的数据封装到wflog对象中，方便页面的使用
	 * @param object
	 * @return
	 */
	public WfLog getWfLogObject(Object[] object){
		WfLog wfLog = new WfLog();
		wfLog.getId().setFlowId((String)object[0]);
		wfLog.getId().setLogNo(DataUtils.nullToZero((BigDecimal) object[1]).intValue());
		wfLog.setModelNo(DataUtils.nullToZero((BigDecimal) object[2]).intValue());
		wfLog.setNodeNo(DataUtils.nullToZero((BigDecimal) object[3]).intValue());
		wfLog.setNodeName((String)object[4]);
		wfLog.setDeptCode((String)object[5]);
		wfLog.setDeptName((String)object[6]);
        wfLog.setOperatorCode((String)object[7]);
        wfLog.setOperatorName((String)object[8]);
        wfLog.setFlowInTime((String)object[9]);
        wfLog.setTimeLimit(DataUtils.nullToZero((BigDecimal) object[10]).intValue());
        wfLog.setHandleTime((String)object[11]);
        wfLog.setSubmitTime((String)object[12]);
        wfLog.setNodeStatus((String)object[13]);
        wfLog.setFlowStatus((String)object[14]);
        wfLog.setPackageId((String)object[15]);
        wfLog.setBusinessType((String)object[16]);
        wfLog.setBusinessNo((String)object[17]);
        wfLog.setContractNo((String)object[18]);
        wfLog.setClassCode((String)object[19]);
        wfLog.setRiskCode((String)object[20]);
        wfLog.setMakeCom((String)object[21]);
        wfLog.setComCode((String)object[22]);
        wfLog.setHandlerCode((String)object[23]);
        wfLog.setHandler1Code((String)object[24]);
        wfLog.setRelateFlowId((String)object[25]);
        wfLog.setRelateLogNo(DataUtils.nullToZero((BigDecimal) object[26]).intValue());
        wfLog.setPosX(DataUtils.nullToZero((BigDecimal) object[27]).intValue());
        wfLog.setPosY(DataUtils.nullToZero((BigDecimal) object[28]).intValue());
        wfLog.setFlag((String)object[29]);
        wfLog.setLicenseNo((String)object[30]);
        wfLog.setRelateContractNo((String)object[31]);
        wfLog.setRiskCategory((String)object[32]);
        wfLog.setInsuredCode((String)object[33]);
        wfLog.setInsuredName((String)object[34]);
        wfLog.setIdentifyType((String)object[35]);
        wfLog.setIdentifyNumber((String)object[36]);
        wfLog.setReinsStatus((String)object[37]);
        wfLog.setPolicyNo((String)object[38]);
        wfLog.setClaimNo((String)object[39]);
		return wfLog;
	}
    
	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public WfGradeService getWfGradeService() {
		return wfGradeService;
	}

	public void setWfGradeService(WfGradeService wfGradeService) {
		this.wfGradeService = wfGradeService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	public WfFlowMainService getWfFlowMainService() {
		return wfFlowMainService;
	}

	public void setWfFlowMainService(WfFlowMainService wfFlowMainService) {
		this.wfFlowMainService = wfFlowMainService;
	}

	public WfPackageService getWfPackageService() {
		return wfPackageService;
	}

	public void setWfPackageService(WfPackageService wfPackageService) {
		this.wfPackageService = wfPackageService;
	}

	public WfLogExtService getWfLogExtService() {
		return wfLogExtService;
	}

	public void setWfLogExtService(WfLogExtService wfLogExtService) {
		this.wfLogExtService = wfLogExtService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpDclassService getPrpDclassService() {
		return prpDclassService;
	}

	public void setPrpDclassService(PrpDclassService prpDclassService) {
		this.prpDclassService = prpDclassService;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getlFlowID() {
		return lFlowID;
	}

	public void setlFlowID(String lFlowID) {
		this.lFlowID = lFlowID;
	}

	public int getlLogNo() {
		return lLogNo;
	}

	public void setlLogNo(int lLogNo) {
		this.lLogNo = lLogNo;
	}

	public String getLogOperatorCode() {
		return LogOperatorCode;
	}

	public void setLogOperatorCode(String logOperatorCode) {
		LogOperatorCode = logOperatorCode;
	}

	public boolean isIsMainSub() {
		return IsMainSub;
	}

	public void setIsMainSub(boolean isMainSub) {
		IsMainSub = isMainSub;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getUnderWriteCode() {
		return underWriteCode;
	}

	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	public DateTime getUnderWriteDate() {
		return underWriteDate;
	}

	public void setUnderWriteDate(DateTime underWriteDate) {
		this.underWriteDate = underWriteDate;
	}

	public char getCertiType() {
		return certiType;
	}

	public void setCertiType(char certiType) {
		this.certiType = certiType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PrpCmainSubService getPrpCmainSubService() {
		return prpCmainSubService;
	}

	public void setPrpCmainSubService(PrpCmainSubService prpCmainSubService) {
		this.prpCmainSubService = prpCmainSubService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpLintfProcessService getPrpLintfProcessService() {
		return prpLintfProcessService;
	}

	public void setPrpLintfProcessService(PrpLintfProcessService prpLintfProcessService) {
		this.prpLintfProcessService = prpLintfProcessService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public SwfModelUseService getSwfModelUseService() {
		return swfModelUseService;
	}

	public void setSwfModelUseService(SwfModelUseService swfModelUseService) {
		this.swfModelUseService = swfModelUseService;
	}

	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public SwfFlowMainService getSwfFlowMainService() {
		return swfFlowMainService;
	}

	public void setSwfFlowMainService(SwfFlowMainService swfFlowMainService) {
		this.swfFlowMainService = swfFlowMainService;
	}

	public PrpFeedBackService getPrpFeedBackService() {
		return prpFeedBackService;
	}

	public void setPrpFeedBackService(PrpFeedBackService prpFeedBackService) {
		this.prpFeedBackService = prpFeedBackService;
	}

	public UndwrtSendClaimService getUndwrtSendClaimService() {
		return undwrtSendClaimService;
	}

	public void setUndwrtSendClaimService(UndwrtSendClaimService undwrtSendClaimService) {
		this.undwrtSendClaimService = undwrtSendClaimService;
	}

	public PrpLacciPersonService getPrpLacciPersonService() {
		return prpLacciPersonService;
	}

	public void setPrpLacciPersonService(PrpLacciPersonService prpLacciPersonService) {
		this.prpLacciPersonService = prpLacciPersonService;
	}
	
	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}

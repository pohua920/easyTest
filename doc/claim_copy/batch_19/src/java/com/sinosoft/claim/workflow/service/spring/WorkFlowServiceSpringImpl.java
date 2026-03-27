package com.sinosoft.claim.workflow.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

import com.sinosoft.claim.bl.facade.BLWorkFlowFacade;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.model.SwfModelUse;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfNodeId;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogId;
import com.sinosoft.claim.schema.model.SwfPathLogStore;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.SwfConditionService;
import com.sinosoft.claim.schema.service.facade.SwfFlowMainService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfLogStoreService;
import com.sinosoft.claim.schema.service.facade.SwfModelUseService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.schema.service.facade.SwfPathLogService;
import com.sinosoft.claim.schema.service.facade.SwfPathLogStoreService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.claim.util.StringConvert;
import com.sinosoft.claim.workflow.service.facade.JbpmBusinessService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.StatStatusDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.util.JbpmAPIUtil;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.dto.custom.SubmitTaskDto;

/**
 * 工作流处理接口
 * @author 中科软
 */
public class WorkFlowServiceSpringImpl extends GenericDaoHibernate<WorkFlowDto, String> implements WorkFlowService {
	/** 工作流日志service */
	private SwfLogService swfLogService;
	/** 工作流日志转储service */
	private SwfLogStoreService swfLogStoreService;
	/** 流程主表service */
	private SwfFlowMainService swfFlowMainService;
	/** 工作流路径日志service */
	private SwfPathLogService swfPathLogService;
	/** 工作流路径定义转储service */
	private SwfPathLogStoreService swfPathLogStoreService;
	/** 用户模板service */
	private SwfModelUseService swfModelUseService;
	/** 工作流意见处理service */
	private SwfNotionService swfNotionService;
	/** 工作流节点定义service */
	private SwfNodeService swfNodeService;
	/** 工作流路径定义service */
	private SwfPathService swfPathService;
	/** 工作流条件描述service */
	private SwfConditionService swfConditionService;
	/** 险种service */
	private PrpDriskService prpDriskService;
	/** 理赔节点状态service */
	private PrpLclaimStatusService prpLclaimStatusService;
	private WorkFlowEngine workFlowEngine;
	private WorkFlowEngineService workFlowEngineService;
	private JbpmBusinessService jbpmBusinessService;
	private static CacheService cacheManager = CacheManager.getInstance("WorkFlow");
	/**
	 * 获取当前系统工作流模板号，从模板分配表中，利用险种和所属机构
	 * @param riskCode String
	 * @param comCode String
	 * @throws Exception
	 * @return int -1表示无
	 */
	public int getModelNo(String riskCode, String comCode) throws Exception {
		String key = cacheManager.generateCacheKey("WorkFlowSwfModelUseModelNo", riskCode, comCode);
		Object result = cacheManager.getCache(key);
//		if ( result != null) {
//			return Integer.parseInt(result.toString());
//		}
		
		int modelNo = -1;
		String condition = (new StringBuilder(" riskCode ='")).append(riskCode).append("' and comCode ='").append(comCode).append("' and modelType='01'").toString();
		List<SwfModelUse> list = this.getSwfModelUseService().findSwfModelUse(QueryRule.getInstance().addSql(condition));
		if (list != null && !list.isEmpty()) {
			modelNo = list.get(0).getId().getModelNo();
		} else if(!ConstantCodes.MAINCOMPANYCOMCODE.equals(comCode)){//查不到值的话读取总公司模板
			condition = (new StringBuilder(" riskCode ='")).append(riskCode).append("' and comCode ='").append(ConstantCodes.MAINCOMPANYCOMCODE).append("' and modelType='01'").toString();
			list = this.getSwfModelUseService().findSwfModelUse(QueryRule.getInstance().addSql(condition));
			if(!CommonUtils.isEmpty(list)) {
				modelNo = list.get(0).getId().getModelNo();
			}
		}
		cacheManager.putCache(key, modelNo);
		
		return modelNo;
	}

	/**
	 * 获取swfLog表当前flowID相同的最大的LogNo 的maxNo
	 * @param flowID String
	 * @throws Exception
	 * @return int
	 */
	public int getSwfLogMaxLogNo(String flowID) throws Exception {
		return this.getSwfLogService().getMaxLogNo(flowID);
	}

	/**
	 * 获取swfLog表当前flowID相同的最大的LogNo 的maxNo
	 * @param flowID String
	 * @throws Exception
	 * @return int
	 */
	public int getSwfLogStoreMaxLogNo(String flowID) throws Exception {
		return this.getSwfLogStoreService().getMaxLogNo(flowID);
	}

	/**
	 * 获取swfPathLog表当前最大的PathNo 的maxNo
	 * @param flowID String
	 * @throws Exception
	 * @return String
	 */
	public int getSwfPathLogMaxPathNo(String flowID) throws Exception {
		return this.getSwfPathLogService().getMaxPathNo(flowID);
	}

	/**
	 * 获取swfPathLogStore表当前最大的PathNo 的maxNo
	 * @param flowID String
	 * @throws Exception
	 * @return String
	 */
	public int getSwfPathLogStoreMaxPathNo(String flowID) throws Exception {
		return this.getSwfPathLogStoreService().getMaxPathNo(flowID);
	}

	/**
	 * 创建工作流程
	 * @param formalPars：变量定义和内容
	 * @throws Exception
	 * @return String
	 */
	public String createFlow(WorkFlowDto workFlowDto) throws Exception {
		// 创建工作流成的步骤:
		// 1.读取模板的号码 不成功直接报错
		// 2.查询出模板的第1个开始节点
		// 3.从wfflowMain表中取得flowId的值，
		// 4。在wflog表建立和模板的nodeNo相同值的节点，
		// 5。设置wflog表该数据的该节点的状态为正在处理。
		String flowID = "";
		// 查询出该模板任务的第一条记录
		if (workFlowDto.getCreateSwfFlowMain() != null) {
			this.swfFlowMainService.save(workFlowDto.getCreateSwfFlowMain());
			if (workFlowDto.getCreateSwfLog() != null) {
				this.swfLogService.save(workFlowDto.getCreateSwfLog());
			}
			flowID = workFlowDto.getCreateSwfFlowMain().getFlowID();
		}
		return flowID;
	}

	/**
	 * 处理整个工作流程(这个是整个工作流处理的基础)
	 * @param formalPars：变量定义和内容
	 * @throws Exception
	 * @return String
	 */
	public void deal(WorkFlowDto workFlowDto) throws Exception {
		try {
//			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
//				this.workFlowEngineService.dealJbpm(workFlowDto);
//			}
		    WorkFlowEngine workFlowEngine = null;
            if (workFlowDto.isNewWorkFlow()) {// JBPM处理工作流引擎renw
                workFlowEngine = this.getWorkFlowEngineService();
            } else {
                workFlowEngine = this.getWorkFlowEngine();
            }
            workFlowEngine.deal(workFlowDto);
			if (workFlowDto.getCreate()) {// 删除部分节点和关系");
				this.deleteNode(workFlowDto);
			}
			// 创建工作流
			if (workFlowDto.getCreate()) { // 创建工作流");
				this.createFlow(workFlowDto);
			}
			// 重开工作流程
			if (workFlowDto.getReOpen()) {// 提交-工作流
				this.reOpenFlow(workFlowDto);
			}
			// 删除-工作流多节点同时删除信息
			if (workFlowDto.getDelete()) {
				this.deleteNodeList(workFlowDto);
			}
			// 提交-工作流 (先提交，後修改？？)
			if (workFlowDto.getSubmit()) { // 提交-工作流
				this.submitNode(workFlowDto);
			}
			// 判断是不是释放所有占号的操作
			if (workFlowDto.getFreeHoldNode()) { // 判断是不是释放所有占号的操作");
				this.freeAllHoldNode(workFlowDto);
			}
			// 修改工作流
			if (workFlowDto.getUpdate()) { // 修改工作流");
				this.updateNode(workFlowDto);
			}
			// 关闭-工作流
			if (workFlowDto.getClose()) { // 关闭-工作流");
				closeFlow(workFlowDto);
			}
			// 收回-工作流
			if (workFlowDto.getRecycle()) {
				this.recycleFlow(workFlowDto);
			}
			// 修改-工作流主表信息
			if (workFlowDto.getUpdateMainFlow()) {
				this.updateMainFlow(workFlowDto);
			}
		} catch (Exception e) {
			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
				JbpmDto jbpmDto = workFlowDto.getJbpmDto();
				if (jbpmDto != null && jbpmDto.getBpmSuccess()) {
					// jbpm事务回滚
//					JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(), jbpmDto.getTaskId());
					jbpmDto.setBpmSuccess(false);
				}
			}
			throw e;
		}
	}
	
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 处理整个工作流程(这个是整个工作流处理的基础)
	 * @param formalPars：变量定义和内容
	 * @throws Exception
	 * @return String
	 */
	public void deal4Ws(WorkFlowDto workFlowDto,HttpSession session) throws Exception {
		try {
//			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
//				this.workFlowEngineService.dealJbpm(workFlowDto);
//			}
		    WorkFlowEngine workFlowEngine = null;
            if (workFlowDto.isNewWorkFlow()) {// JBPM处理工作流引擎renw
                workFlowEngine = this.getWorkFlowEngineService();
            } else {
                workFlowEngine = this.getWorkFlowEngine();
            }
            workFlowEngine.deal4Ws(workFlowDto,session);
			if (workFlowDto.getCreate()) {// 删除部分节点和关系");
				this.deleteNode(workFlowDto);
			}
			// 创建工作流
			if (workFlowDto.getCreate()) { // 创建工作流");
				this.createFlow(workFlowDto);
			}
			// 重开工作流程
			if (workFlowDto.getReOpen()) {// 提交-工作流
				this.reOpenFlow(workFlowDto);
			}
			// 删除-工作流多节点同时删除信息
			if (workFlowDto.getDelete()) {
				this.deleteNodeList(workFlowDto);
			}
			// 提交-工作流 (先提交，後修改？？)
			if (workFlowDto.getSubmit()) { // 提交-工作流
				this.submitNode(workFlowDto);
			}
			// 判断是不是释放所有占号的操作
			if (workFlowDto.getFreeHoldNode()) { // 判断是不是释放所有占号的操作");
				this.freeAllHoldNode(workFlowDto);
			}
			// 修改工作流
			if (workFlowDto.getUpdate()) { // 修改工作流");
				this.updateNode(workFlowDto);
			}
			// 关闭-工作流
			if (workFlowDto.getClose()) { // 关闭-工作流");
				closeFlow(workFlowDto);
			}
			// 收回-工作流
			if (workFlowDto.getRecycle()) {
				this.recycleFlow(workFlowDto);
			}
			// 修改-工作流主表信息
			if (workFlowDto.getUpdateMainFlow()) {
				this.updateMainFlow(workFlowDto);
			}
		} catch (Exception e) {
			if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
				JbpmDto jbpmDto = workFlowDto.getJbpmDto();
				if (jbpmDto != null && jbpmDto.getBpmSuccess()) {
					// jbpm事务回滚
//					JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(), jbpmDto.getTaskId());
					jbpmDto.setBpmSuccess(false);
				}
			}
			throw e;
		}
	}

	/**
	 * 工作流更新主表信息
	 * @author 中科软
	 * @param workFlowDto
	 */
	private void updateMainFlow(WorkFlowDto workFlowDto) throws Exception {
		SwfFlowMain swfFlowMain = workFlowDto.getSwfFlowMain();
		if (swfFlowMain != null) {
			this.swfFlowMainService.delete(swfFlowMain.getFlowID());
			this.swfFlowMainService.save(swfFlowMain);
		}
	}

	/**
	 * 收回工作流<br>
	 * 实现逻辑为（注意下列操作必须在一个事务处理中,並swfPathLog中的startNodeNo/
	 * endNodeNo实际存储的不是nodeNo而是logNo）<br>
	 * <li>获得校验选中记录当前节点，根据当前节点获取所有後续节点,如果没有後续节点则抛出异常“没有下级节点，不能收回” <li>
	 * 根据当前节点获取所有後续节点是否为待处理状态
	 * （swfLog.nodeStatus=’0’未处理），如果有已处理的，则抛出异常“下一节点正在处理，不能收回” <li>
	 * 删除swfLog表当前节点的所有後续节点数据， <li>删除swfPathLog所有後续节点数据 <li>
	 * 将SwfLog表当前节点的节点状态nodeStatus设置为“2”正在处理 <li>更新表PrpLclaimStatus
	 * @Description:
	 * @author 中科软
	 * @param workFlowDto
	 */
	private void recycleFlow(WorkFlowDto workFlowDto) throws Exception {
		SwfLog updateSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = updateSwfLog.getId().getFlowID();
		int logNo = updateSwfLog.getId().getLogNo();
		SwfLog swfLog = this.swfLogService.findSwfLog(flowID, logNo);
		String conditions = "flowID = '" + flowID + "' AND (logNo in " + "(Select endNodeNo from swfPathLog Where flowID = '" + flowID + "' AND startNodeNo = " + logNo + "))";
		List<?> result = this.swfLogService.findByConditions(conditions);
		if (result == null || result.size() == 0) {
			throw new UserException(0, 1, "工作流", "沒有低階節點，不能收回！");
		}
		result = this.swfLogService.findByConditions(conditions + " AND nodestatus!='0'");
		if (result != null && result.size() > 0) {
			throw new UserException(0, 1, "工作流", "下一節點正在處理，不能收回！");
		}
		// 增加判断单证的过程
		if ("certi".equals(swfLog.getNodeType())) {
			// 如果是单证模式，需要检查是否已出计算书，如果出了，则不能进行回收操作
			result = this.swfLogService.findByConditions("flowID = '" + flowID + "' AND nodetype='compp' ");
			if (result != null && result.size() > 0) {
				throw new UserException(0, 1, "工作流", "已出賠款計算書，不能收回！");
			}
		}
		// 当有且只有一条工作流路径到该节点时删除该节点
		String conditions2 = " flowID='" + flowID + "' and startNodeNo='" + logNo + "'";
		List<SwfPathLog> list = this.swfPathLogService.findSwfPathLog(QueryRule.getInstance().addSql(conditions2));
		if (list != null && !list.isEmpty()) {
			for (SwfPathLog swfPathLogDto : list) {
				int count = this.swfPathLogService.getCount(" flowID='" + flowID + "' and endNodeNo='" + swfPathLogDto.getEndNodeNo() + "'");
				if (count == 1) {
					this.swfLogService.deleteByConditions(conditions);
				}
			}
		}
		conditions = "flowID = '" + flowID + "' AND startNodeNo = " + logNo;
		this.swfPathLogService.deleteByConditions(conditions);
		String nodeStatus = "2";
		if (DataUtils.emptyToNull(swfLog.getBusinessType()) != null) {
			nodeStatus = "3";// 退回任务节点,任务继续留在回退任务里
		}
		String statement = " update SWfLog set  nodeStatus='" + nodeStatus + "',submittime = null " + " Where flowID='" + flowID + "' and logNo = " + logNo;
		HibernateUtils.executeSql(super.getSession(), statement);
		// 更新表PrpLclaimStatus 核损环节收回需要判断是标的车、三者车 start
		if ("verif".equals(swfLog.getNodeType()) || "veriw".equals(swfLog.getNodeType())) {
			statement = " update PrpLclaimStatus set Status='2' " + " Where NodeType = '" + swfLog.getNodeType() + "' AND BusinessNo='" + swfLog.getBusinessNo() + "' AND SERIALNO = '" + swfLog.getLossItemCode() + "'";
		} else {
			statement = " update PrpLclaimStatus set Status='2' " + " Where NodeType = '" + swfLog.getNodeType() + "' AND BusinessNo='" + swfLog.getBusinessNo() + "'";
		}
		HibernateUtils.executeSql(super.getSession(), statement);
	}

	/**
	 * 关闭工作流
	 * @author 中科软
	 * @param workFlowDto
	 */
	private void closeFlow(WorkFlowDto workFlowDto) throws Exception {
		// 1.变更工作流主表的状态位置
		SwfFlowMain closeSwfFlowMain = workFlowDto.getCloseSwfFlowMain();
		if (closeSwfFlowMain != null) {
			String flowID = closeSwfFlowMain.getFlowID();
			// 增加转储的标志 工作流数据调整为适时转储,所以标志位为已转储状态“2”
			closeSwfFlowMain.setStoreFlag("2");
			this.swfFlowMainService.delete(flowID);
			this.swfFlowMainService.save(closeSwfFlowMain);
			String statement = " update SWfLog set  nodeStatus='4' " + " Where flowID='" + closeSwfFlowMain.getFlowID() + "' and (taskType='M')";
			HibernateUtils.executeSql(super.getSession(), statement);
			statement = " update SWflog set FlowStatus='0' Where flowID='" + flowID + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
			// 工作流数据调整为适时转储
			statement = " INSERT INTO SWFLOGSTORE ( SELECT * FROM SWFLOG WHERE FLOWID = '" + flowID + "' )";
			HibernateUtils.executeSql(super.getSession(), statement);
			statement = " INSERT INTO SWFPATHLOGSTORE ( SELECT * FROM SWFPATHLOG WHERE FLOWID = '" + flowID + "' )";
			HibernateUtils.executeSql(super.getSession(), statement);
			String conditions = " FLOWID = '" + flowID + "'";
			HibernateUtils.executeSql(super.getSession(), "delete from SwfLog where " + conditions);
			HibernateUtils.executeSql(super.getSession(), "delete from SwfPathLog where " + conditions);
		}
	}

	/**
	 * 工作流更新节点信息
	 * @author 中科软
	 * @param workFlowDto
	 */
	private void updateNode(WorkFlowDto workFlowDto) throws Exception {
		SwfLog updateSwfLog = workFlowDto.getUpdateSwfLog();
		if (updateSwfLog != null) {
			// this.swfLogService.delete(updateSwfLog.getId().getFlowID(),
			// updateSwfLog.getId().getLogNo());
			this.swfLogService.saveOrUpdate(updateSwfLog);
		}
		SwfLog updateSwfLog2 = workFlowDto.getUpdateSwfLog2();
		if (updateSwfLog2 != null) {
			// this.swfLogService.delete(updateSwfLog2.getId().getFlowID(),
			// updateSwfLog2.getId().getLogNo());
			this.swfLogService.saveOrUpdate(updateSwfLog2);
		}
		// 保存办理意见
		List<SwfNotion> swfNotionList = workFlowDto.getSwfNotionList();
		if (swfNotionList != null && swfNotionList.size() > 0) {
			this.swfNotionService.save(swfNotionList);
		}
	}

	/**
	 * 释放所有该用户的占号信息
	 * @author 中科软
	 * @param workFlowDto
	 * @throws Exception
	 */
	private void freeAllHoldNode(WorkFlowDto workFlowDto) throws Exception {
		SwfLog swfLog = workFlowDto.getUpdateSwfLog();
		if (swfLog != null) {
			String statement = " update SWfLog set handlerCode='0', handlerName=null, flowStatus='1'  Where nodeStatus='0' and handlerCode='" + swfLog.getHandlerCode() + "' and flowstatus='2'";
			HibernateUtils.executeSql(super.getSession(), statement);
		}
	}

	/**
	 * 按内容删除工作流节点数据和关系数据
	 * @author 中科软
	 * @param workFlowDto
	 * @throws Exception
	 */
	private void deleteNodeList(WorkFlowDto workFlowDto) throws Exception {
		List<SwfLog> deleteSwfLogList = workFlowDto.getDeleteSwfLogList();
		if (deleteSwfLogList != null) {
			for (SwfLog swfLog : deleteSwfLogList) {
				this.getSwfLogService().delete(swfLog.getId().getFlowID(), swfLog.getId().getLogNo());
			}
		}
		List<SwfPathLog> tempPathLogList = workFlowDto.getDeleteSwfPathLogList();
		if (tempPathLogList != null) {
			for (SwfPathLog swfPathLog : tempPathLogList) {
				this.swfPathLogService.delete(swfPathLog.getId());
			}
		}
	}

	/**
	 * 重开工作流
	 * @author 中科软
	 * @param workFlowDto
	 * @throws SQLException
	 */
	private void reOpenFlow(WorkFlowDto workFlowDto) throws Exception {
		// 1.变更工作流主表的状态位置
		SwfFlowMain reOpenSwfFlowMain = workFlowDto.getReOpenSwfFlowMain();
		if (reOpenSwfFlowMain != null) {
			// 首先检查是否已经把结束的案件放到存储表里面去了，如果已经存在於存储表里，那么需要把数据给转移到流转表中。
			if ("2".equals(reOpenSwfFlowMain.getStoreFlag())) {
				String statement1 = " insert into SWflog (select * from swflogstore Where flowID='" + reOpenSwfFlowMain.getFlowID() + "')";
				HibernateUtils.executeSql(super.getSession(), statement1);
				statement1 = " insert into SWfpathlog (select * from swfpathlogstore Where flowID='" + reOpenSwfFlowMain.getFlowID() + "')";
				HibernateUtils.executeSql(super.getSession(), statement1);
				statement1 = " delete from swflogstore Where flowID='" + reOpenSwfFlowMain.getFlowID() + "'";
				HibernateUtils.executeSql(super.getSession(), statement1);
				statement1 = " delete from swfpathlogstore Where flowID='" + reOpenSwfFlowMain.getFlowID() + "'";
				HibernateUtils.executeSql(super.getSession(), statement1);
			}
			reOpenSwfFlowMain.setStoreFlag("0");
			this.swfFlowMainService.delete(reOpenSwfFlowMain.getFlowID());
			this.swfFlowMainService.save(reOpenSwfFlowMain);
			String statement = " update SWflog set FlowStatus='1' Where flowID='" + reOpenSwfFlowMain.getFlowID() + "'";
			HibernateUtils.executeSql(super.getSession(), statement);
		}
	}

	/**
	 * 删除部分节点和关系
	 * @param workFlowDto
	 * @throws Exception
	 */
	private void deleteNode(WorkFlowDto workFlowDto) throws Exception {
		List<SwfLog> tempLogList = workFlowDto.getDeleteSwfLogList();
		if (tempLogList != null) {
			for (SwfLog swfLog : tempLogList) {
				this.getSwfLogService().delete(swfLog.getId().getFlowID(), swfLog.getId().getLogNo());
			}
		}
		List<SwfPathLog> tempPathLogList = workFlowDto.getDeleteSwfPathLogList();
		if (tempPathLogList != null) {
			for (SwfPathLog swfPathLog : tempPathLogList) {
				this.swfPathLogService.delete(swfPathLog.getId());
			}
		}
	}

	/**
	 * 修改工作流本身的状态信息
	 * @param formalPars：变量定义和内容
	 * @throws Exception
	 * @return String
	 */
	public void updateFlow(SwfLog swfLog) throws Exception {
		this.swfLogService.update(swfLog);
	}

	/**
	 * 完成工作流节点，並向下一个节点流转
	 * @param workFlowDto
	 * @throws Exception
	 * @return String
	 */
	public String submitNode(WorkFlowDto workFlowDto) throws Exception {
		String flowID = "";
		List<SwfLog> submitSwfLogList = workFlowDto.getSubmitSwfLogList();
		if (submitSwfLogList != null) {
			this.swfLogService.save(submitSwfLogList);
		}
		List<SwfPathLog> submitSwfPathLogList = workFlowDto.getSubmitSwfPathLogList();
		if (submitSwfPathLogList != null) {
			this.swfPathLogService.save(submitSwfPathLogList);
		}
		return flowID;
	}

	/**
	 * 检查工作流是否关闭
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public boolean checkFlowClose(String flowID) throws Exception {
		String condtions = "flowid ='" + flowID + "' and FLOWSTATUS='0'";
		int intRet = this.swfFlowMainService.getCount(condtions);

		boolean retBln = false;
		if (intRet > 0)
			retBln = true;
		return retBln;
	}

	/**
	 * 根据流程节点的流程号和logno查询具体信息
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public SwfLog findNodeByPrimaryKey(String flowID, int logNo) throws Exception {
		return this.swfLogService.findSwfLog(flowID, logNo);

	}

	/**
	 * 根据流程号码查询wfflowMain的具体信息
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public SwfFlowMain findFlowMainByPrimaryKey(String flowID) throws Exception {
		return this.swfFlowMainService.findSwfFlowMain(flowID);
	}

	/**
	 * 查找符合条件的流程节点信息
	 * @param condition 条件
	 * @throws Exception
	 * @return List<SwfLog>
	 */
	public List<SwfLog> findNodesByConditions(String condition) throws Exception {
		String sql = "select * from swflog where " + condition;
		List<SwfLog> list = new ArrayList<SwfLog>();
		List<?> listTemp = HibernateUtils.findbySql(super.getSession(), sql, SwfLog.class);
		for (Iterator<?> iterator = listTemp.iterator(); iterator.hasNext();) {
			SwfLog swfLog = (SwfLog) iterator.next();
			list.add(swfLog);
		}
		return list;
	}

	/**
	 * 查找符合条件的流程节点信息
	 * @param condition 条件
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLog> findStoreNodesByConditions(String condition) throws Exception {
		return this.swfLogService.findSwfLog(QueryRule.getInstance().addSql(condition));
	}

	/**
	 * 查找符合条件的流程节点信息(翻页)
	 * @param condition 条件
	 * @throws Exception
	 * @return String
	 */
	public Page findNodesByConditions(String condition, int pageNo, int recordPerPage) throws Exception {
		Page page = this.swfLogService.findByPage(condition, pageNo, recordPerPage);
		for (Iterator<?> it = page.getResult().iterator(); it.hasNext();) {
			convert((SwfLog) it.next());
		}
		return page;
	}

	/**
	 * 转换工作流节点，设置险别名称
	 * @author 中科软
	 * @param swfLog
	 * @throws Exception
	 */
	private void convert(SwfLog swfLog) throws Exception {
		if (swfLog != null) {
			PrpDrisk prpDrisk = this.prpDriskService.findPrpDrisk(swfLog.getRiskCode());
			if (prpDrisk != null) {
				swfLog.setRiskCodeName(prpDrisk.getRiskCName());
			} else {
				swfLog.setRiskCodeName(swfLog.getRiskCode());
			}
		}
	}

	/**
	 * 查找符合条件的流程节点信息(翻页)
	 * @param condition 条件
	 * @throws Exception
	 * @return String
	 */
	public Page findStoreNodesByConditions(String condition, int pageNo, int recordPerPage) throws Exception {
		return this.swfLogStoreService.findByPage(condition, pageNo, recordPerPage);
	}

	/**
	 * 查找当前处理的节点的节点信息
	 * @param BussinessNo
	 * @param nodeType
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLog> findCurrentNode(String BussinessNo, String nodeType) throws Exception {
		String condition = " BUSINESSNO='" + BussinessNo + "' And " + " NodeType ='" + nodeType + "' And (NodeStatus<'4' )";
		return this.swfLogService.findViewSwfLogAll(condition);

	}

	/**
	 * 查找当前处理的节点的节点信息
	 * @param flowID
	 * @param logNo
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLog> findCurrentNode(String flowID, int logNo) throws Exception {
		String condition = " flowid='" + flowID + "' And " + " LogNo=" + logNo;
		return this.swfLogService.findViewSwfLogAll(condition);
	}

	/**
	 * 查找当前流程的节点中是否存在NodeNo相同，並且状态为0未处理的Log节点
	 * @param flowID
	 * @param nodeNo
	 * @param nodeType
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLog> findNoDealNodeByModelNodeNo(String flowID, int nodeNo, String nodeType) throws Exception {
		// 核赔不用合並的。。
		List<SwfLog> resultList = new ArrayList<SwfLog>();
		if ("veric".equals(nodeType)) {
			return resultList;
		}
		String condition = "flowID='" + flowID + "' And " + " NodeNo=" + nodeNo + " and NodeStatus <4";
		return this.swfLogService.findViewSwfLogAll(condition);
	}

	/**
	 * 查找当前流程的节点中是否存在NodeNo相同，並且状态为0未处理的Log节点(人到人的方式下的)
	 * @param flowID
	 * @param nodeNo
	 * @param nodeType
	 * @param policyNo
	 * @throws Exception
	 * @return List<SwfLog>
	 */
	public List<SwfLog> findNoDealNodeByModelNodeNoByPerson(String flowID, int nodeNo, String nodeType, String policyNo) throws Exception {
		String condition = "";
		// 没有被处理的，不仅仅是待处理的，还应该包括正在处理的，因为比如单证节点，应该就是只能一个节点被操作，並且是聚合性质的
		// 並且一个保单上的数据必须是保证一个聚合的。
		condition = "flowID='" + flowID + "' And " + "  NodeStatus<4 and NodeNo=" + nodeNo;
		// 由於强三的问题。。所以用这个方式 先聚合吧。。
		if (policyNo != null && policyNo.length() > 1) {
			condition = condition + " and policyNo='" + policyNo + "'";
		}
		return this.swfLogService.findViewSwfLogAll(condition);
	}

	/**
	 * 查找符合一个工作流上的所有节点信息
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLog> findNodesByFlowID(String flowID) throws Exception {
		String conditon = "flowid ='" + flowID + "' ORDER BY nodeno";
		return this.findNodesByConditions(conditon);
	}

	/**
	 * 查找符合一个工作流上的所有节点信息
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLogStore> findStoreNodesByFlowID(String flowID) throws Exception {
		String conditon = "flowid ='" + flowID + "' ORDER BY nodeno";
		return this.swfLogStoreService.findSwfLogStore(QueryRule.getInstance().addSql(conditon));
	}

	/**
	 * 查找模板的一个节点的详细信息
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public SwfNode findModelNodeByPrimaryKey(int modelNo, int nodeNo) throws Exception {
		return this.swfNodeService.findSwfNode(new SwfNodeId(modelNo, nodeNo));
	}

	/**
	 * 查找模板的第一个符合条件的节点的详细信息
	 * @param condition
	 * @throws Exception
	 * @return String
	 */
	public SwfNode findModelFirstNodeByCondition(String condition) throws Exception {
		List<SwfNode> swfNodeList = this.swfNodeService.findByConditions(condition);
		SwfNode swfNode = new SwfNode();
		if (swfNodeList != null && !swfNodeList.isEmpty()) {
			return swfNodeList.get(0);
		}
		return swfNode;
	}

	/**
	 * 查找模板的符合节点类型的第一个节点的详细信息
	 * @param modelNo
	 * @param nodeType
	 * @throws Exception
	 * @return String
	 */
	public SwfNode findModelNodeByNodeType(int modelNo, String nodeType) throws Exception {
		String strSql = "modelNo=" + modelNo + " and nodeType='" + nodeType + "'";
		return this.findModelFirstNodeByCondition(strSql);
	}

	/**
	 * 查找模板的下多个节点的详细信息
	 * @param modelNo int 模板号码
	 * @param nodeNo int 模板上的节点号码
	 * @param iBusinessNo String 当前业务号码
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfNode> findModelNextNodes(int modelNo, int nodeNo, String iBusinessNo) throws Exception {
		String strSql = "";
		String nodeConditions = "  (modelno =" + modelNo + " and startnodeno =" + nodeNo + ")";
		String strWhere = "";
		boolean blnResult = true;
		List<SwfNode> wfNodeDtoList = new ArrayList<SwfNode>();
		// 思路是先取得所有符合条件的路径，然後判断路径上的条件是不是满足，只要有一个条件不满足，就不能用这个路径了。
		// 最後把符合条件的路径上的最後一个点都可以用的。。
		// 获得所有的path数据
		List<SwfPath> wfPathList = this.swfPathService.findByConditions(nodeConditions);
		for (SwfPath swfPath : wfPathList) {
			// 由於条件的引入，需要过滤掉不符合wfCondition条件的数据记录，通过pathNo来进行。
			// 首先过滤掉是用来选择用的节点
			if ("3".equals(swfPath.getDefaultFlag())) {
				continue;
			}
			// 没有条件限制的情况下
			if (!"1".equals(swfPath.getConditionStatus())) {
				strSql = strSql + "," + swfPath.getEndNodeNo();
				continue;
			}
			// 有条件约束的情况下，首先要取得所以的条件，一一甄别
			blnResult = this.checkPathCondition(swfPath, iBusinessNo);
			if (blnResult) {
				strSql = strSql + "," + swfPath.getEndNodeNo();
			}
		}
		// 判断有没有符合条件的nodeNo
		if (strSql.length() > 1) {
			strWhere = strSql;
			strSql = " modelno =" + modelNo + " and nodeNo in (" + strWhere.substring(1, strWhere.length()) + ")";
			wfNodeDtoList = this.swfNodeService.findByConditions(strSql);
		}
		return wfNodeDtoList;
	}

	/**
	 * 查找模板的下多个节点的详细信息
	 * @param nodeConditions
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfPath> findModelPathNodes(String nodeConditions) throws Exception {
		// 获得所有的path数据
		return this.swfPathService.findByConditions(nodeConditions);
	}

	/**
	 * 寻找nodeNo为当前NodeNo的T类型的节点
	 * @param modelNo int 模板号码
	 * @param nodeNo int 当前的NodeNo的值
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfNode> findModelNextTNodes(int modelNo, int nodeNo) throws Exception {
		String nodeConditions = "  (modelno =" + modelNo + " and taskType ='T' and taskNo=" + nodeNo + ")";
		return this.swfNodeService.findByConditions(nodeConditions);
	}

	/**
	 * 查找模板的上多个节点的详细信息
	 * @param null
	 * @throws Exception
	 * @return String
	 */
	public List<SwfNode> findModelPerviousNodes(int modelNo, int nodeNo) throws Exception {
		String conditions = " modelno =" + modelNo + " and nodeNo in ( select startnodeno from wfpath where modelno =" + modelNo + " and endnodeno =" + nodeNo + ")";
		return this.swfNodeService.findByConditions(conditions);

	}

	/**
	 * 查找工作流的某点之前上多个节点的详细信息
	 * @param flowID
	 * @param logNo
	 * @throws Exception
	 * @return String
	 */
	public List<SwfLog> findPerviousNodes(String flowID, int logNo) throws Exception {
		String conditions = " flowID ='" + flowID + "' and logNo in ( select startnodeno from swfpathLog where flowID ='" + flowID + "' and endnodeno =" + logNo + ")";
		return this.swfLogService.findByConditions(conditions);
	}

	/**
	 * 根据业务号查询工作流流程日志信息
	 * @param businessNo String
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfLog> findFlowLogByBuessionNo(String businessNo) throws Exception {
		String conditions = "  flowid in (select flowid from swfLog where businessNo='" + businessNo + "' AND nodetype='regis') ORDER BY nodeno";
		return this.swfLogService.findByConditions(conditions);
	}

	/**
	 * 根据流程号查询工作流流程路径信息
	 * @param flowID String
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfPathLog> findFlowPathLogByFlowID(String flowID) throws Exception {
		String conditions = " flowID='" + flowID + "'";
		return this.swfPathLogService.findSwfPathLog(QueryRule.getInstance().addSql(conditions));

	}

	/**
	 * 根据流程号查询工作流流程路径信息
	 * @param flowID String
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfPathLogStore> findStoreFlowPathLogByFlowID(String flowID) throws Exception {
		String conditions = " flowID='" + flowID + "' ";
		return this.swfPathLogStoreService.findSwfPathLogStore(QueryRule.getInstance().addSql(conditions));
	}

	/**
	 * @param swfPathDto
	 * @param iBusinessNo
	 * @return
	 * @throws Exception
	 */
	/**
	 * 检验是不是满足路径上的条件
	 * @param wfPathDto WfPathDto
	 * @param iBusinessNo String
	 * @throws Exception
	 * @return boolean
	 */
	public boolean checkPathCondition(SwfPath swfPath, String iBusinessNo) throws Exception {
		String conditions = " ( modelno =" + swfPath.getId().getModelNo() + " and PathNo =" + swfPath.getId().getPathNo() + ")";
		boolean blnResult = true;
		List<SwfCondition> wfConditionList = this.swfConditionService.findSwfCondition(QueryRule.getInstance().addSql(conditions));
		if (wfConditionList != null) {
			// 必须设置开始的条件检验为
			String strWhere = "";
			String strTemp = "";
			for (SwfCondition swfCondition : wfConditionList) {
				// 根据业务号码，判断业务数据库中是否满足路径上的条件，如果都满足，则说明可以通过
				// .0/1简单条件
				if (swfCondition.getConfigType().equals("0") || swfCondition.getConfigType().equals("1")) {
					strWhere = swfCondition.getBusinessKey().trim() + "='" + iBusinessNo.trim() + "' AND " + swfCondition.getConfigText().trim();
					strTemp = "SELECT COUNT(*) FROM " + swfCondition.getTableName().trim() + " WHERE " + strWhere.trim();
					blnResult = this.swfConditionService.executeResult(strTemp);
				}
				// 2高级条件或者是function
				if (swfCondition.getConfigType().equals("2")) {
					// 目前没有设置function (保留)
				}
				// 如果不符合条件,立即跳出循环
				if (!blnResult) {
					break;
				}
			}
		}
		return blnResult;
	}

	/**
	 * 根据流程号和节点进行独占操作
	 * @param flowID String
	 * @param LogNo int
	 * @throws Exception
	 * @return boolean
	 */
	public SwfLog holdNode(String flowID, int logNo, String userCode, String userName) throws Exception {
		// 占号操作 (默认是没有占号，没有获得分配权)
		boolean retHold = false;
		// 从数据库中查询符合条件的工作流节点
		SwfLog swfLog = this.swfLogService.findSwfLog(flowID, logNo);
		if ("2".equals(swfLog.getFlowStatus())) {
			// 如果工作流已经被占用了，检查是不是同一个人
			if (swfLog.getHandlerCode().equals(userCode)) {
				retHold = true;
			} else if (swfLog.getHandlerCode().equals("0")) {
				retHold = true;
			}
		} else {
			// 没有分配的情况下，独占该工作节点
			if (!swfLog.getHandlerCode().equals(userCode)) {
				// 如果不是指定人的，就占号，如果是指定人的，不改变flowStatus,保持1的状态
				swfLog.setFlowStatus("2");
			}
			swfLog.setHandlerCode(userCode);
			swfLog.setHandlerName(userName);
			swfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());

			WorkFlowDto workFlowDto = new WorkFlowDto();
			workFlowDto.setFreeHoldNode(true);
			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLog(swfLog);
			this.deal(workFlowDto);
			// 再次判断是不是本人占用的???
			swfLog = this.findHoldNode(flowID, logNo, userCode);
			retHold = swfLog.getHoldNode();
		}
		swfLog.setHoldNode(retHold);
		return swfLog;
	}

	/**
	 * 根据流程号和节点进行释放操作
	 * @param flowID String
	 * @param LogNo int
	 * @throws Exception
	 * @return boolean
	 */
	public void freeNode(String flowID, int logNo) throws Exception {
		// 无条件释放
		SwfLog swfLog = this.swfLogService.findSwfLog(flowID, logNo);
		swfLog.setFlowStatus("1");
		swfLog.setHandlerCode("");
		swfLog.setHandlerName("");
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLog(swfLog);
		this.deal(workFlowDto);
	}

	/**
	 * 根据流程号和节点判断用户是否具有独占操作
	 * @param flowID String
	 * @param LogNo int
	 * @throws Exception
	 * @return boolean
	 */
	public SwfLog findHoldNode(String flowID, int logNo, String userCode) throws Exception {
		boolean retBln = false;
		SwfLog swfLog = this.swfLogService.findSwfLog(flowID, logNo);
		// 如果占用等於本人，返回true; 不用区分是flowStatus的状况
		if (swfLog.getHandlerCode().equals(userCode)) {
			retBln = true;
		}
		swfLog.setHoldNode(retBln);
		return swfLog;
	}

	/**
	 * 统计工作流节点状态数量的功能
	 * @param conditions String
	 * @return List<StatStatusDto>
	 */
	public List<StatStatusDto> getNodeStatusStat(String condition) {
		return this.swfLogService.getNodeStatusStat(condition);

	}

	/**
	 * 统计工作流节点用户状态数量的功能
	 * @param conditions String
	 * @throws Exception
	 * @return StatStatusDto
	 */
	public List<StatStatusDto> getNodeUserStatusStat(String condition) throws Exception {
		return swfLogService.getNodeUserStatusStat(condition);
	}

	/**
	 * 根据报案号码查询工作流flowID
	 * @param registNo String
	 * @throws Exception
	 */
	public String findFlowIDByRegistNo(String registNo) throws Exception {
		String condition = "  businessno ='" + registNo + "' and nodeType='regis'";
		List<SwfLog> swfLogList = this.swfLogService.findSwfLog(QueryRule.getInstance().addSql(condition));
		if (swfLogList != null && !swfLogList.isEmpty()) {
			return swfLogList.get(0).getId().getFlowID();
		}
		return "";
	}

	/**
	 * 根据业务号码查询工作流flowID
	 */
	public String findFlowIDBybusinessNo(String businessNo) throws Exception {
		String flowId = "";
		String condition = "  businessno ='" + businessNo + "'";
		List<SwfLog> swfLogList = this.swfLogService.findByConditions(condition);
		if (swfLogList != null && !swfLogList.isEmpty()) {
			return swfLogList.get(0).getId().getFlowID();
		}
		return flowId;
	}

	/**
	 * 获得理赔节点统计信息
	 * @param conditions：查询条件
	 * @return List<StatStatusDto>
	 * @throws Exception
	 */

	public List<StatStatusDto> getStatStatus(String conditions) throws Exception {
		return this.swfLogService.getStatStatus(conditions);
	}

	/**
	 * 获取swfNotion表当前flowID相同,LogNo相同,
	 * @param flowID String
	 * @param logNo int
	 * @throws Exception
	 * @return int lineNo 的最大的的maxNo
	 */
	public int getSwfNotionMaxLineNo(String flowID, int logNo) throws Exception {
		return this.swfNotionService.getMaxLineNo(flowID, logNo);
	}

	public void updateHandlerCode(String flowID, int logNo, String userCode, String userName) throws Exception {
		SwfLog swfLog = this.swfLogService.findSwfLog(flowID, logNo);
		swfLog.setHandlerCode(userCode);
		swfLog.setHandlerName(userName);
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLog(swfLog);
		this.deal(workFlowDto);
	}

	/**
	 * 查找符合条件的节点的个数
	 * @param conditon
	 * @return int
	 * @throws Exception
	 */
	public int findFlowNodeCountByConditon(String condition) throws Exception {
		return this.swfLogService.getCount(condition);
	}

	/**
	 * 根据报案号和保单号,车牌号，操作时间，案件状态查询报案信息
	 * @param httpServletRequest 返回给页面的request
	 * @param registNo 报案号
	 * @param policyNo 保单号
	 * @param licenseNo 车牌号码
	 * @param riskCode 险别
	 * @param insuredName 被保险人名称
	 * @throws Exception
	 */
	public Page getWorkFlowList(HttpServletRequest httpServletRequest, String registNo, String policyNo, String licenseNo, String riskCode, String insuredName, int intPageNo, int intRecordPerPage) throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		registNo = StringUtils.rightTrim(registNo);
		policyNo = StringUtils.rightTrim(policyNo);
		licenseNo = StringUtils.rightTrim(licenseNo);
		insuredName = StringUtils.rightTrim(insuredName);
		riskCode = StringUtils.rightTrim(riskCode);
		riskCode = StringUtils.rightTrim(riskCode);
		insuredName = StringUtils.rightTrim(insuredName);
		String conditions = " nodeType='regis' ";
		if (registNo.length() > 0) {
			conditions = conditions + StringConvert.convertString(" registNo", registNo, httpServletRequest.getParameter("RegistNoSign"));
		}
		if (policyNo.length() > 0) {
			conditions = conditions + StringConvert.convertString(" policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign"));
		}
		if (riskCode.length() > 0) {
			conditions = conditions + StringConvert.convertString(" riskCode", riskCode, httpServletRequest.getParameter("RiskCodeSign"));
		}
		if (insuredName.length() > 0) {
			conditions = conditions + StringConvert.convertString(" insuredName", insuredName, httpServletRequest.getParameter("InsuredNameSign"));
		}
		if (licenseNo.length() > 0) {
			conditions = conditions + StringConvert.convertString(" lossitemName", licenseNo, httpServletRequest.getParameter("LicenseNoSign"));
		}
		// 拼权限
		com.sinosoft.claim.ui.control.action.UIPowerInterface uiPowerInterface = new com.sinosoft.claim.ui.control.action.UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, httpServletRequest.getParameter("taskCodeC"), "swfLog", null);
		// 查询流程信息
		// 得到多行报案主表信息
		Page page = null;
		String caseType = httpServletRequest.getParameter("caseType");
		if ("1".equals(caseType)) {
			page = this.findStoreNodesByConditions(conditions, intPageNo, intRecordPerPage);
		} else {
			page = this.findNodesByConditions(conditions, intPageNo, intRecordPerPage);
		}
		SwfLog swfLog = new SwfLog();
		List<SwfLog> list = new ArrayList<SwfLog>();
		Iterator<?> it = page.getResult().iterator();
		while (it.hasNext()) {
			SwfLog swflogTemp = (SwfLog) it.next();
			list.add(swflogTemp);
		}
		swfLog.setSwfLogList(list);
		httpServletRequest.setAttribute("page", page);
		httpServletRequest.setAttribute("swfLog", swfLog);
		swfLog.setEditType(httpServletRequest.getParameter("editType"));
		return page;

	}

	/**
	 * 获取最大LogNo (SwfLog)
	 */
	public int getSwfLogMaxNodeLogNo(String flowID, String nodeType, String businessNo) throws Exception {
		return this.swfLogService.getMaxNodeLogNo(flowID, nodeType, businessNo);
	}

	/**
	 * 获取最大LogNo (SwfLogStore)
	 */
	public int getSwfLogStoreMaxNodeLogNo(String flowID, String nodeType, String businessNo) throws Exception {
		return this.swfLogStoreService.getMaxNodeLogNo(flowID, nodeType, businessNo);
	}

	public void updateFlowStatus(String flowID) throws Exception {
		this.swfLogService.updateFlowStatus(flowID);
	}

	public void updateSwfflowMain(SwfFlowMain swfFlowMain) throws Exception {
		this.swfFlowMainService.update(swfFlowMain);// ??
	}

	// ********************************对工作流节点进行删除操作,请慎重调用**************************************
	/**
	 * 在不予立案节点处理完毕後，要将在报案环节生成的工作流信息删除
	 */
	public void deletWorkFlowForNotGrand(String conditions) throws Exception {
		// 对删除条件进行严格控制
		if (DataUtils.emptyToNull(conditions) != null) {
			this.swfLogService.deleteByConditions(conditions);
		}
	}

	/**
	 * 删除注销/拒赔任务（即为退回注销/拒赔任务）
	 */
	public void cancelBack(String flowID, int logNo, JbpmDto jbpmDto) throws Exception {
		String strSwfPathLog = " FlowID='" + flowID + "' and EndNodeNo=" + logNo;
		SwfPathLog swfPathLog = new SwfPathLog();
		SwfLog swfLog = new SwfLog();
		String notion = "";
		PrpLclaimStatus prpLclaimStatus = null;
		// 找到注销/拒赔的这条边
		List<SwfPathLog> swfPathLogList = this.swfPathLogService.findSwfPathLog(QueryRule.getInstance().addSql(strSwfPathLog));
		if (swfPathLogList != null && swfPathLogList.size() > 0) {
			swfPathLog = swfPathLogList.get(0);
		} else {
			throw new UserException(0, 1, "工作流", "數據問題不能回退，請檢查數據！");
		}
		// 找到申请注销/拒赔的这个节点
		swfLog = this.swfLogService.findSwfLog(flowID, swfPathLog.getStartNodeNo());
		String strPrpLclaimStatus = " BusinessNo='" + swfLog.getKeyOut() + "' and NodeType='claim'";
		List<PrpLclaimStatus> prpLclaimStatusList = this.prpLclaimStatusService.findPrpLclaimStatus(QueryRule.getInstance().addSql(strPrpLclaimStatus));
		if (prpLclaimStatusList != null && prpLclaimStatusList.size() > 0) {
			prpLclaimStatus = prpLclaimStatusList.get(0);
		}
		if ("claim".equals(swfLog.getNodeType())) {
			swfLog.setNodeStatus("2");
			swfLog.setBusinessNo(swfLog.getKeyIn());
			if (prpLclaimStatus != null) {
				prpLclaimStatus.setStatus("2");
			}
		} else {
			swfLog.setNodeStatus("0");
		}
		// 保存日志信息
		int maxLineNo = this.getSwfNotionMaxLineNo(flowID, logNo);
		SwfNotion swfNotion = new SwfNotion();
		swfNotion.getId().setFlowID(flowID);
		swfNotion.getId().setLogNo(logNo);
		swfNotion.getId().setLineNo(maxLineNo);
		notion = swfLog.getHandlerName() + "在" + swfLog.getNodeName() + "节点的案件" + swfLog.getKeyIn() + "被回退";
		swfNotion.setHandleText(notion);
		this.swfNotionService.save(swfNotion);
		this.swfLogService.delete(flowID, logNo);
		this.swfPathLogService.delete(new SwfPathLogId(flowID, swfPathLog.getId().getPathNo()));
		this.swfLogService.saveOrUpdate(swfLog);
		if ("compp".equals(swfLog.getNodeType())) {
			int comppLogNo = swfLog.getId().getLogNo();
			SwfPathLog swfPathLogC = new SwfPathLog();
			List<SwfPathLog> swfPathLogComppList = this.swfPathLogService.findSwfPathLog(QueryRule.getInstance().addSql(" FlowID='" + flowID + "' and EndNodeNo=" + comppLogNo));
			if (swfPathLogComppList != null && swfPathLogComppList.size() > 0) {
				swfPathLogC = swfPathLogComppList.get(0);
			} else {
				throw new UserException(0, 1, "工作流", "由於數據問題不能回退，請檢查數據！");
			}
			swfLog = this.swfLogService.findSwfLog(flowID, swfPathLogC.getStartNodeNo());
			swfLog.setNodeStatus("0");
			this.swfLogService.delete(flowID, comppLogNo);
			this.swfPathLogService.delete(new SwfPathLogId(flowID, swfPathLogC.getId().getPathNo()));
			this.swfLogService.saveOrUpdate(swfLog);
		}
		if (prpLclaimStatus != null) {
			this.prpLclaimStatusService.saveOrUpdate(prpLclaimStatus);
		}
		if (jbpmDto != null) {
			this.getJbpmBusinessService().processTask(jbpmDto);
		}
	}

	// ********************************对工作流节点进行删除操作,请慎重调用
	// end****************************************

	/**
	 * 核赔节点的通过（手工结案） //??
	 */
	public int passVeric(SubmitTaskDto submitTaskDto) throws Exception {
		return new BLWorkFlowFacade().passVeric(submitTaskDto);
	}

	public int findViewSwfLogAllCountByConditon(String condition) throws Exception {
		return this.swfLogService.getCountViewSwfLogAll(condition);
	}

	public SwfLogStore findSwfLogStoreDtoByPrimaryKey(String flowID, int logNo) throws Exception {
		return this.swfLogStoreService.findSwfLogStore(flowID, logNo);
	}

	/**
	 * 查找符合条件的流程节点信息(翻页)
	 * @param condition 条件
	 * @throws Exception
	 * @return String
	 */
	public Page findViewSwfLogAll(String condition, int pageNo, int recordPerPage) throws Exception {
		return this.swfLogService.findByPageFromView(condition, pageNo, recordPerPage);
	}

	/**
	 * 从视图查询理赔工作流数据
	 */
	public String findViewFlowIDBybusinessNo(String businessNo) throws Exception {
		String condition = "  businessno ='" + businessNo + "'";
		List<SwfLog> swfLogList = this.swfLogService.findViewSwfLogAll(condition);
		if (swfLogList != null && !swfLogList.isEmpty()) {
			return swfLogList.get(0).getId().getFlowID();
		}
		return "";
	}

	/**
	 * 查询SwfLog获取满足条件的工作流数据 List<SwfLog>
	 */
	public List<SwfLog> findByConditions(String condition) throws Exception {
		return this.swfLogService.findByConditions(condition);
	}

	/**
	 * 根据工作流号，序号查询工作流节点数据
	 */
	public SwfLog findByPrimaryKey(String flowID, int logNo) throws Exception {
		return this.swfLogService.findSwfLog(flowID, logNo);
	}

	/**
	 * 查SwfLog获取满足条件的节点数
	 */
	public int getCount(String condition) throws Exception {
		return this.swfLogService.getCount(condition);
	}

	/**
	 * 理算紧急案件清单查询(翻页)
	 * @param condition 条件
	 * @throws Exception
	 * @return String
	 */
	public Page getUrgentCaseList(String condition, int pageNo, int recordPerPage) throws Exception {
		return this.swfLogService.getUrgentCaseList(condition, pageNo, recordPerPage);
	}

	/**
	 * 核赔紧急案件清单查询(翻页)
	 * @param condition 条件
	 * @throws Exception
	 * @return String
	 */
	public Page getUndwrtUrgentCaseList(String condition, int pageNo, int recordPerPage) throws Exception {
		return this.swfLogService.getUndwrtUrgentCaseList(condition, pageNo, recordPerPage);
	}

	/***
	 * 获取产生当前节点任务的提交节点
	 * @param flowID
	 * @param logNo
	 * @return
	 * @throws Exception
	 */
	public SwfLog findBackSwfLog(String flowID, int logNo) throws Exception {
		String sql = " flowid = '" + flowID + "' and endnodeno =" + logNo;
		List<SwfPathLog> tempList = this.getSwfPathLogService().findSwfPathLog(QueryRule.getInstance().addSql(sql));
		if (tempList != null && !tempList.isEmpty()) {
			return this.findByPrimaryKey(flowID, tempList.get(0).getStartNodeNo());
		}
		return null;
	}
	
	public void dealAudit(WorkFlowDto workFlowDto) throws Exception {
		try {
			this.workFlowEngineService.dealAuditJbpm(workFlowDto);
			// 创建工作流
			if (workFlowDto.getCreate()) { // 创建工作流
				this.createFlow(workFlowDto);
			}
			if (workFlowDto.getSubmit() || workFlowDto.getBack()) { //提交-工作流
				this.submitNode(workFlowDto);
			}
			if (workFlowDto.getFreeHoldNode()) { //判断是不是释放所有占号的操作
				this.freeAllHoldNode(workFlowDto);
			}
			if (workFlowDto.getUpdate()) { // 修改工作流
				this.updateNode(workFlowDto);
			}
			if (workFlowDto.getClose()) { // 关闭-工作流
				this.closeFlow(workFlowDto);
			}
		} catch (Exception e) {
			JbpmDto jbpmDto = workFlowDto.getJbpmDto();
			if (jbpmDto != null && jbpmDto.getBpmSuccess()) {
				// jbpm事务回滚
				JbpmAPIUtil.rollbackTask(jbpmDto.getProcessId(), jbpmDto.getBusinessId(), jbpmDto.getActorId(), jbpmDto.getTaskId());
				jbpmDto.setBpmSuccess(false);
			}
			throw e;
		}
	}

	@Override
	public List<SwfLog> findSwfLogWithNotion(String flowID,boolean flag) throws Exception {
		String statements = "";
		if(flag){
			statements = "from SwfLogStore s,SwfNotion u where s.id.flowID = u.id.flowID and s.id.logNo = u.id.logNo and s.id.flowID = '"+flowID+"' order by s.id.logNo asc";
		}else{
			statements = "from SwfLog s,SwfNotion u where s.id.flowID = u.id.flowID and s.id.logNo = u.id.logNo and s.id.flowID = '"+flowID+"' order by s.id.logNo asc";
		}
		List<?> resultList = HibernateUtils.findByPageHql(super.getHibernateTemplate(), 0, 0, statements, null);
		List<SwfLog> list = new ArrayList<SwfLog>();
		if (resultList!=null && !resultList.isEmpty()) {
			SwfLog swfLog = null;
			for (Iterator<?> it = resultList.iterator(); it.hasNext();) {
				Object[] obj = (Object[]) it.next();
				if(obj[0] instanceof SwfLogStore){
					swfLog = ((SwfLogStore)obj[0]).toSwfLog();
				}else{
					swfLog = (SwfLog)obj[0];
				}
				swfLog.getSwfNotionList().add((SwfNotion)obj[1]);
				list.add(swfLog);
			}
		}
		return list;
	}
	
	@Override
	public SwfNode getCancelSwfNode(int modelNo) throws Exception{
		String key = cacheManager.generateCacheKey("WorkFlowCancelSwfNode", modelNo);
		Object result = cacheManager.getCache(key);
		if(result != null){
			return (SwfNode)result;
		}
		String condition = " modelNo =" + modelNo + " and nodeType = 'cance' ";
		List<SwfNode> swfNodeList = this.swfNodeService.findByConditions(condition);
		if (CommonUtils.isEmpty(swfNodeList)) {
			throw new UserException(1, 3, "工作流引擎", "工作流模板【"+modelNo+"】未配置【註銷拒賠】節點！");
		}
		SwfNode swfNode = swfNodeList.get(0);
		cacheManager.putCache(key, swfNode);
		return swfNode;
	}

	@Override
	public SwfNode getFirstSwfNode(int modelNo) throws Exception{
		String key = cacheManager.generateCacheKey("WorkFlowFirstSwfNode", modelNo);
		Object result = cacheManager.getCache(key);
		if(result != null){
			return (SwfNode)result;
		}
		List<SwfNode> swfNodeList = this.swfNodeService.findByConditions(" modelno = " + modelNo + " and nodeno = 1 ");
		if (CommonUtils.isEmpty(swfNodeList)) {
			throw new UserException(1, 3, "工作流引擎", "工作流模板【"+modelNo+"】未配置初始節點！");
		}
		SwfNode swfNode = swfNodeList.get(0);
		cacheManager.putCache(key, swfNode);
		return swfNode;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SwfCondition> getSwfConditionForAutoTask(int modelNo, int startNodeNo, int endNodeNo) throws Exception {
		String key = cacheManager.generateCacheKey("WorkFlowSwfConditionForAutoTask", modelNo, startNodeNo, endNodeNo);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SwfCondition>) result;
		}
		// 自動節點自動流轉的路線及條件查詢
		// 1.當前節點到自動節點的路線（比如計算書節點是自動節點，提算提交到計算書，需要計算書自動流轉的條件就配置在 理算 到 計算書這個swfpath路線上）
		// 2.查找配置在 該路線上的條件，逐一判斷是否有成立，OR 關係
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" modelNo =").append(modelNo).append(" and configType = '5' ");
		conditions.append(" and PathNo IN ( ");
		conditions.append(" select PathNo from SwfPath where modelNo =").append(modelNo);
		conditions.append(" and startNodeNo = ").append(startNodeNo);
		conditions.append(" and endNodeNo = ").append(endNodeNo);
		conditions.append(" ) order by conditionNo , serialNo ");
		List<SwfCondition> wfConditionList = this.swfConditionService.findByConditions(conditions.toString());
		if (CommonUtils.isEmpty(wfConditionList)) {
			wfConditionList = new ArrayList<SwfCondition>();
		}
		cacheManager.putCache(key, wfConditionList);
		return wfConditionList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SwfCondition> getSwfConditionForPath(int modelNo, int pathNo) throws Exception {
		String key = cacheManager.generateCacheKey("WorkFlowSwfConditionForPath", modelNo, pathNo);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SwfCondition>) result;
		}
		String conditions = " modelNo =" + modelNo + " and PathNo =" + pathNo + " and configType in ('0','1','2') order by conditionNo , serialNo ";
		List<SwfCondition> wfConditionList = this.swfConditionService.findByConditions(conditions);
		if (CommonUtils.isEmpty(wfConditionList)) {
			wfConditionList = new ArrayList<SwfCondition>();
		}
		cacheManager.putCache(key, wfConditionList);
		return wfConditionList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SwfPath> getSwfPath(int modelNo , int startNodeNo) throws Exception{
		String key = cacheManager.generateCacheKey("WorkFlowSwfPath", modelNo, startNodeNo);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (List<SwfPath>) result;
		}
		String conditions = " modelNo =" + modelNo + " and startNodeNo = " + startNodeNo + " order by pathNo asc ";
		// 查找節點路線
		List<SwfPath> wfPathList = this.swfPathService.findByConditions(conditions);
		if (CommonUtils.isEmpty(wfPathList)) {
			wfPathList = new ArrayList<SwfPath>();
		}
		cacheManager.putCache(key, wfPathList);
		return wfPathList;
	}
	
	@Override
	public SwfNode getSwfNode(int modelNo, int nodeNo) throws Exception {
		String key = cacheManager.generateCacheKey("WorkFlowSwfNode", modelNo, nodeNo);
		Object result = cacheManager.getCache(key);
		if (result != null) {
			return (SwfNode) result;
		}
		SwfNode swfNode = this.swfNodeService.findByPrimaryKey(modelNo, nodeNo);
		if (swfNode == null) {
			throw new UserException(1, 3, "工作流引擎", "工作流模板【" + modelNo + "】未配置【" + nodeNo + "】節點！");
		}
		cacheManager.putCache(key, swfNode);
		return swfNode;
	}
	
	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public SwfLogStoreService getSwfLogStoreService() {
		return swfLogStoreService;
	}

	public void setSwfLogStoreService(SwfLogStoreService swfLogStoreService) {
		this.swfLogStoreService = swfLogStoreService;
	}

	public SwfFlowMainService getSwfFlowMainService() {
		return swfFlowMainService;
	}

	public void setSwfFlowMainService(SwfFlowMainService swfFlowMainService) {
		this.swfFlowMainService = swfFlowMainService;
	}

	public SwfPathLogStoreService getSwfPathLogStoreService() {
		return swfPathLogStoreService;
	}

	public void setSwfPathLogStoreService(SwfPathLogStoreService swfPathLogStoreService) {
		this.swfPathLogStoreService = swfPathLogStoreService;
	}

	public SwfPathLogService getSwfPathLogService() {
		return swfPathLogService;
	}

	public void setSwfPathLogService(SwfPathLogService swfPathLogService) {
		this.swfPathLogService = swfPathLogService;
	}

	public SwfModelUseService getSwfModelUseService() {
		return swfModelUseService;
	}

	public void setSwfModelUseService(SwfModelUseService swfModelUseService) {
		this.swfModelUseService = swfModelUseService;
	}

	public SwfNotionService getSwfNotionService() {
		return swfNotionService;
	}

	public void setSwfNotionService(SwfNotionService swfNotionService) {
		this.swfNotionService = swfNotionService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	public SwfConditionService getSwfConditionService() {
		return swfConditionService;
	}

	public void setSwfConditionService(SwfConditionService swfConditionService) {
		this.swfConditionService = swfConditionService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public WorkFlowEngineService getWorkFlowEngineService() {
		return workFlowEngineService;
	}

	public void setWorkFlowEngineService(WorkFlowEngineService workFlowEngineService) {
		this.workFlowEngineService = workFlowEngineService;
	}

	public JbpmBusinessService getJbpmBusinessService() {
		return jbpmBusinessService;
	}

	public void setJbpmBusinessService(JbpmBusinessService jbpmBusinessService) {
		this.jbpmBusinessService = jbpmBusinessService;
	}

    public WorkFlowEngine getWorkFlowEngine() {
        return workFlowEngine;
    }

    public void setWorkFlowEngine(WorkFlowEngine workFlowEngine) {
        this.workFlowEngine = workFlowEngine;
    }
	
    public static void main(String[] args) {
    	System.out.println("test...");
    }
}

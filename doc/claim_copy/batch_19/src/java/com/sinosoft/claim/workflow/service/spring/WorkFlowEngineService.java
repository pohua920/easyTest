package com.sinosoft.claim.workflow.service.spring;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.jbpm.task.Task;
import org.jbpm.task.TaskData;
import org.jbpm.task.User;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLscheduleItem;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfConfig;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogId;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogId;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfConfigService;
import com.sinosoft.claim.workflow.service.facade.JbpmBusinessService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 新工作流引擎处理类
 * @author 中科软
 */
public class WorkFlowEngineService extends WorkFlowEngine {

	private static final String RECASEPROCESSID = "claim_reCase_D";// 重开赔案流程图
	private static final String RECASEACTORID = "request_recase";// 申请重开赔案节点
	private SwfConfigService swfConfigService;
	private PrplregistrpolicyService prpLregistrpolicyService;
	private PrpLscheduleMainWFService prpLscheduleMainWFService;
	private PrpLscheduleItemService prpLscheduleItemService;
	private PrpLclaimService prpLclaimService;
	private PrpLregistService prpLregistService;
	private JbpmBusinessService jbpmBusinessService;
	private PrpLcompensateService prpLcompensateService;
	private PrpCmainService prpCmainService;

	/**
	 * 处理jbpm工作流任务
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void dealJbpm(WorkFlowDto workFlowDto) throws Exception {
		this.getUpdateSwfLog(workFlowDto);
		this.initWorkFlowParameters(workFlowDto);
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		if (jbpmDto != null) {
			ActionContext act = ActionContext.getContext();
			Map<String, Object> session = act.getSession();
			UserDto user = (UserDto) session.get("user");
			if (workFlowDto.getCreate()) {
				this.setBeforeCreate(user, workFlowDto);
				this.jbpmBusinessService.startProcess(jbpmDto);// JBPM创建工作流
				jbpmDto.setBpmSuccess(true);
				this.createFlowInfo(user, workFlowDto);
				if (workFlowDto.getSubmit()) {
					this.getSubmit(user, workFlowDto.getCreateSwfLog(), workFlowDto, jbpmDto.getActorId(), 2, 1);
				}
			} else if (workFlowDto.getReOpen()) {
				this.setBeforeReOpen(workFlowDto);
				this.jbpmBusinessService.startProcess(jbpmDto);
				jbpmDto.setBpmSuccess(true);
				this.reOpenWorkFlow(user, workFlowDto);
			} else if (workFlowDto.getSubmit() || workFlowDto.getClose() || workFlowDto.getBack()) {
				this.jbpmBusinessService.processTask(jbpmDto);
				jbpmDto.setBpmSuccess(true);
				workFlowDto.setClose(workFlowDto.getAutoClose() ? true : jbpmDto.getClose());// 工作流处理后是否关闭
				if (workFlowDto.getBack()) {// 逆向流转 回退处理
					this.getBackProcess(user, workFlowDto);
				} else if (workFlowDto.getSubmit()) {// 正向流转
					this.getSubmitSwfLog(user, workFlowDto);
				}
				if (workFlowDto.getClose()) {// 关闭工作流
					this.getClose(user, workFlowDto);
				}
				this.specialProcess(workFlowDto);
			}
		}
	}

	/***
	 * 重开赔案处理
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void reOpenWorkFlow(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog updateSwfLog = workFlowDto.getUpdateSwfLog();// 提取的应该是当前赔案的最后一次结案
		String flowID = updateSwfLog.getId().getFlowID();
		SwfFlowMain swfFlowMain = this.getWorkFlowService().findFlowMainByPrimaryKey(flowID);
		int maxLogNo = 0;
		int maxPathNo = 0;
		if ("2".equals(swfFlowMain.getStoreFlag())) {// 已转储
			maxLogNo = this.getWorkFlowService().getSwfLogStoreMaxLogNo(flowID); // LogNo+1
			maxPathNo = this.getWorkFlowService().getSwfPathLogStoreMaxPathNo(flowID);
		} else {
			maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID); // 最大LogNo+1
			maxPathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
		}
		if (swfFlowMain.getFlowStatus().equals("0")) {
			// 3.1开启工作流主表数据，打开工作流的节点所有数据
			swfFlowMain.setFlowStatus("1");
			workFlowDto.setReOpenSwfFlowMain(swfFlowMain);
		}
		this.getSubmit(user, updateSwfLog, workFlowDto, RECASEACTORID, maxLogNo, maxPathNo);
		workFlowDto.setSubmit(true);
		for (SwfLog swfLog : workFlowDto.getSubmitSwfLogList()) {
			if (!"D".equals(ConstantCodes.carClassMap.get(updateSwfLog.getRiskCode()))) {
				swfLog.setHandleDept(user.getComCode());
				swfLog.setComCode(user.getComCode());
			}
			swfLog.setTitleStr("重開賠案");
		}
	}

	/***
	 * 重开赔案参数配置
	 * @param workFlowDto
	 */
	public void setBeforeReOpen(WorkFlowDto workFlowDto) {
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		jbpmDto.setActorId(RECASEACTORID);
		jbpmDto.setProcessId(RECASEPROCESSID);
	}

	/**
	 * 工作流流转参数设置
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void initWorkFlowParameters(WorkFlowDto workFlowDto) throws Exception {
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		SwfLog updateSwfLog = workFlowDto.getUpdateSwfLog();
		if (updateSwfLog != null && workFlowDto.getSubmit()) {//
			String registNo = updateSwfLog.getRegistNo();// 取备案号码
			if (DataUtils.emptyToNull(registNo) == null) {// 创建工作流的时候，从WorkFlowDto的BessinessNo取，之前Action有预设
				registNo = workFlowDto.getBessinessNo();
			}
			PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
			jbpmDto.putParamsMap("registType", prpLregist.getRegistType());// 备案类型
			String flowID = updateSwfLog.getId().getFlowID();
			String strRiskType = codeService.translateRiskCodetoRiskType(updateSwfLog.getRiskCode());
			//理算退回，需要在生成单证任务，核损提交有单证任务，就不在生成。
			String sql = " nodeStatus < 4 and nodeType = 'certi' and flowId = '" + flowID + "'";
			if("D".equals(strRiskType)){
				sql = " ((nodeStatus < 4 and nodeType = 'certi') or (nodeStatus < 4 and nodeType = 'compe' and riskCode='"+updateSwfLog.getRiskCode()+"')) and flowId = '" + flowID + "'";
			}
			List<SwfLog> list = this.getWorkFlowService().findByConditions(sql);
			if (list == null || list.isEmpty()) {// 生成单证任务的条件限制。只能存在一个可处理单证任务
				jbpmDto.putParamsMap("certiFlag", true);
			}
			
			if ("D".equals(strRiskType) && workFlowDto.getSubmit()) {
				String strSql = " registNo='" + registNo + "' and simpleFlag <>'1' and canceldate is null";
				List<PrpLclaim> claimList = this.prpLclaimService.findPrpLclaim(QueryRule.getInstance().addSql(strSql));
				if (claimList != null && !claimList.isEmpty()) {
					if (claimList.size() >= 2) {
						jbpmDto.putParamsMap("compeFlag", "2");
					} else {
						jbpmDto.putParamsMap("compeFlag", ConstantCodes.RISKCODE_DAA.equals(claimList.get(0).getRiskCode()) ? "0" : "1");
					}
				}
			}
			String nodeType = updateSwfLog.getNodeType();
			// 结案提交，和重开赔案的核赔通过都需要判断是否可以结案
			if ("4".equals(updateSwfLog.getNodeStatus()) && ("endca".equals(nodeType) || workFlowDto.getAutoClose())) {// 结案判断是否可以关闭流程
				String conditonss = "flowId='" + flowID + "' and nodeType='compe' and nodestatus<4";
				List<SwfLog> compeList = this.getWorkFlowService().findNodesByConditions(conditonss);
				if (compeList != null) {
					if (compeList.size() > 1) {
						// 一个流程存在多个结案的情况，当判断还存在没有关闭的结案时，则流程不结束
						for (int i = 0; i < compeList.size(); i++) {
							SwfLog compe = compeList.get(i);
							if (compe.getKeyIn().equals(updateSwfLog.getKeyIn())) {
								compe.setNodeStatus("4");// 关闭理算
								compe.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
								workFlowDto.setUpdateSwfLog2(compe);
								break;
							}
						}
					} else {
						jbpmDto.putParamsMap("endFlag", true);
					}
				}
			}
			PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(prpLregist.getPolicyNo());
			workFlowDto.getJbpmDto().putParamsMap("simpleFlowFlag", "4".equals(prpCmain.getCoinsFlag()));
		}
	}

	/**
	 * 回退任务处理
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void getBackProcess(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		SwfLog backSwfLog = this.getWorkFlowService().findBackSwfLog(flowID, currSwfLog.getId().getLogNo());
		if (backSwfLog == null) {// 該節點任務未找到可退回的節點任務，且當前未指定要退回的節點
			throw new UserException(1, 3, "工作流", "該節點任務未找到可退回的節點任務");
		}
		int maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
		int maxPathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
		List<SwfLog> tempList = this.canBack(backSwfLog);// 没有业务异常则表示 可以退回
		List<SwfLog> backList = workFlowDto.getBackSwfLogList();// 指定回退节点的情况
		if (backList == null || backList.isEmpty()) {// 若没有指定退回的节点，则根据swfPathLog查找退回节点
			backList.add(backSwfLog);
		}
		for (SwfLog swfLog : tempList) {
			if (swfLog.getId().getLogNo() - currSwfLog.getId().getLogNo() != 0) {// 当前操作退回的节点的同级节点亦要退回
				this.setBackSwfLog(swfLog, user);
			}
		}
		this.setBackSwfLog(currSwfLog, user);
		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLog(currSwfLog);
		workFlowDto.setSubmit(true);
		workFlowDto.setUpdateSwfLogList(tempList);
		String currTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString();
		for (SwfLog swfLog : backList) {
			SwfLog tempSwfLog = new SwfLog();
			PropertyUtils.copyProperties(tempSwfLog, swfLog);
			tempSwfLog.setId(new SwfLogId(flowID, maxLogNo));
			tempSwfLog.setBusinessType(currSwfLog.getNodeType());
			tempSwfLog.setHandleTime("");
			tempSwfLog.setSubmitTime("");
			tempSwfLog.setNodeStatus("3");// 表示退回的
			tempSwfLog.setFlowInTime(currTime);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String titleAttr = tempSwfLog.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(tempSwfLog.getFlowInTime()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) + " 上一節點操作人:" + user.getUserName();
			tempSwfLog.setTitleStr(titleAttr);
			tempSwfLog.setBeforeHandlerCode(user.getUserCode());
			tempSwfLog.setBeforeHandlerName(user.getUserName());
			maxLogNo++;
			workFlowDto.getSubmitSwfLogList().add(tempSwfLog);
			workFlowDto.getSubmitSwfPathLogList().add(this.getSwfPathLog(currSwfLog, tempSwfLog, maxPathNo));
			maxPathNo++;
		}
	}

	/***
	 * 回退节点讯息设置
	 * @param backSwfLog 被回退的节点
	 * @param user 当前操作人员
	 */
	protected void setBackSwfLog(SwfLog backSwfLog, UserDto user) {
		String currentTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString();
		backSwfLog.setNodeStatus("5");
		backSwfLog.setHandlerCode(user.getUserCode());
		backSwfLog.setHandlerName(user.getUserName() + "-回退");
		backSwfLog.setHandleTime(currentTime);
		backSwfLog.setSubmitTime(currentTime);
	}

	/****
	 * 检查工作流是否可以退回
	 * @param backSwfLog 可退回的节点
	 * @return
	 * @throws Exception
	 */
	protected List<SwfLog> canBack(SwfLog backSwfLog) throws Exception {
		String flowID = backSwfLog.getId().getFlowID();
		String condition = " flowID = '" + flowID + "' and logNo in (select endNodeNo from swfPathLog where flowID='" + flowID + "' and startNodeNo=" + backSwfLog.getId().getLogNo() + ")";
		List<SwfLog> tempList = this.getWorkFlowService().findByConditions(condition);
		for (SwfLog swfLog : tempList) {
			if ("5".equals(swfLog.getNodeStatus())) {
				throw new UserException(1, 3, "工作流", "該節點任務已退回！");
			} else if ("4".equals(swfLog.getNodeStatus())) {
				throw new UserException(1, 3, "工作流", "該節點任務已由" + swfLog.getHandlerName() + "(" + swfLog.getHandlerCode() + ")處理提交！");
			} else if ("2".equals(swfLog.getNodeStatus())) {
				//核赔任务，暂存后可以退回
				if("veric".equals(swfLog.getNodeType())){
					continue;
				}
				throw new UserException(1, 3, "工作流", "該節點任務已由" + swfLog.getHandlerName() + "(" + swfLog.getHandlerCode() + ")暫存處理中！");
			} else {
				if ("M".equals(swfLog.getTaskType())) {// 多任务处理节点，因为节点状态不会变化，查找其下级节点判断
					this.canBack(swfLog);
				}
			}
		}
		return tempList;
	}

	/***
	 * 流程结束，结案处理
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void getClose(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		// 如果是第一个节点，现在还没有工作流主表内容呢，所以不需要查询的。
		if (workFlowDto.getAutoClose()) {// 自动结案的情况
			this.autoClose(user, workFlowDto);// 构建自动自动结案节点
		}
		// 车险商强关联全结案，非车（自动结案，手动结案），endFlag = true判断可以结案，才可以关闭主流程
		Boolean endFlag = new Boolean(String.valueOf(workFlowDto.getJbpmDto().getParamsMap().get("endFlag")));
		if (endFlag) {
			SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(flowID);
			if (swfFlowMainDto != null) {
				swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
				swfFlowMainDto.setFlowStatus("0");
			}
			workFlowDto.setCloseSwfFlowMain(swfFlowMainDto);
		}
	}

	/***
	 * 构建自动结案节点工作流已处理任务
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	private void autoClose(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		// 因为JBPM在遇到自动结案时，直接关闭结束流程，不会依据流程图产生结案节点
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		SwfLog endcaSwfLog = null;
		List<SwfLog> submitSwfLogList = workFlowDto.getSubmitSwfLogList();
		if (!submitSwfLogList.isEmpty()) {
			endcaSwfLog = submitSwfLogList.get(0);
			this.setAutoEndCaseParam(endcaSwfLog);
		} else {
			String endcaSql = "flowId='" + currSwfLog.getId().getFlowID() + "' and policyNo = '" + currSwfLog.getPolicyNo() + "' and nodeType='endca' and nodestatus <4 ";
			List<SwfLog> tempList = this.getWorkFlowService().findByConditions(endcaSql);
			if (tempList != null && !tempList.isEmpty()) {
				for (SwfLog s : tempList) {
					this.setAutoEndCaseParam(s);
					workFlowDto.getUpdateSwfLogList().add(s);
				}
			}
		}
	}
	/**
	 * 自动结案，设置各结案节点的的参数。
	 * @param endcaSwfLog
	 * @throws Exception 
	 */
	protected void setAutoEndCaseParam(SwfLog endcaSwfLog) throws Exception{
		endcaSwfLog.setHandlerName("自動結案");
		endcaSwfLog.setKeyOut(prpLclaimService.findPrpLclaim(endcaSwfLog.getBusinessNo()).getCaseNo());
		endcaSwfLog.setNodeStatus("4");
		String currTime = DateTime.current().toString(DateTime.YEAR_TO_SECOND);
		endcaSwfLog.setHandleTime(currTime);
		endcaSwfLog.setSubmitTime(currTime);
	}

	/***
	 * 针对新工作流构造业务数据的特殊处理
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void specialProcess(WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		List<SwfLog> submitSwfLogList = workFlowDto.getSubmitSwfLogList();
		if (submitSwfLogList != null && !submitSwfLogList.isEmpty()) {
			String nodeType = currSwfLog.getNodeType();
			JbpmDto jbpmDto = workFlowDto.getJbpmDto();
			if ("sched".equals(nodeType)) {// 调度环节的特殊处理
				String sql = null;
				int certaIndex = -1;
				int propcIndex = -1;
				int woundIndex = -1;
				for (SwfLog swfLog : submitSwfLogList) {
					if ("check".equals(swfLog.getNodeType())) {
						PrpLscheduleMainWF scheduleMainWF = prpLscheduleMainWFService.findPrpLscheduleMainWF(1, currSwfLog.getRegistNo());
						swfLog.setHandleDept(scheduleMainWF.getScheduleObjectID());
						swfLog.setDeptName(scheduleMainWF.getScheduleObjectName());
						swfLog.setHandlerCode(scheduleMainWF.getNextHandlerCode());
						swfLog.setHandlerName(scheduleMainWF.getNextHandlerName());
					} else {
						sql = " registNo='" + currSwfLog.getRegistNo() + "' and nextNodeNo='" + swfLog.getNodeType() + "' ";
						if ("certa".equals(swfLog.getNodeType())) {
							certaIndex++;
							sql += " and itemNo = " + String.valueOf(jbpmDto.getCertainLossNodeMap(swfLog.getNodeType(), certaIndex));
						} else if ("wound".equals(swfLog.getNodeType())) {
							woundIndex++;
							sql += " and itemNo = " + String.valueOf(jbpmDto.getCertainLossNodeMap(swfLog.getNodeType(), woundIndex));
						} else if ("propc".equals(swfLog.getNodeType())) {
							propcIndex++;
							sql += " and itemNo = " + String.valueOf(jbpmDto.getCertainLossNodeMap(swfLog.getNodeType(), propcIndex));
						}
						List<PrpLscheduleItem> items = prpLscheduleItemService.findPrpLscheduleItem(QueryRule.getInstance().addSql(sql));
						PrpLscheduleItem item = items.get(0);
						swfLog.setLossItemCode(String.valueOf(item.getId().getItemNo()));
						swfLog.setLossItemName(item.getLicenseNo());
						swfLog.setInsureCarFlag(item.getInsureCarFlag());
						swfLog.setHandleDept(item.getScheduleObjectID());
						swfLog.setDeptName(item.getScheduleObjectName());
						swfLog.setHandlerCode(item.getNextHandlerCode());
						swfLog.setHandlerName(item.getNextHandlerName());
						swfLog.setExigenceGree(item.getExigenceGree());
					}
				}
			}
			// 注销拒赔
			if (workFlowDto.getClaimCancel()) {
				for (SwfLog swfLog : submitSwfLogList) {
					if ("cance".equals(swfLog.getNodeType())) {
						PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim(swfLog.getKeyIn());
						swfLog.setPolicyNo(prpLclaim.getPolicyNo());
						swfLog.setRiskCode(prpLclaim.getRiskCode());
					}
				}
			}
		}
	}

	/***
	 * 获取当前节点后续节点的工作流流转业务数据
	 * @param user 当前用户
	 * @param currSwfLog 当前工作流节点业务数据
	 * @param logNo 新任务节点序号
	 * @param task JBPM工作流的工作任务
	 * @param businessId 工作流对应的实例ID
	 * @return
	 * @throws Exception
	 */
	private SwfLog getSwfLog(UserDto user, SwfLog currSwfLog, int logNo, Task task) throws Exception {
		SwfLog tempSwfLog = new SwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		tempSwfLog.setId(new SwfLogId(flowID, logNo));
		tempSwfLog.setModelNo(currSwfLog.getModelNo());
		tempSwfLog.setBusinessNo(currSwfLog.getBusinessNo());
		if (DataUtils.emptyToNull(currSwfLog.getNextBusinessNo()) != null) {
			tempSwfLog.setBusinessNo(currSwfLog.getNextBusinessNo());
		}
		if (DataUtils.emptyToNull(currSwfLog.getNextKeyIn()) == null) {
			currSwfLog.setNextKeyIn(currSwfLog.getKeyOut());
		}
		tempSwfLog.setKeyIn(currSwfLog.getNextKeyIn());
		tempSwfLog.setNextKeyIn(currSwfLog.getNextKeyIn());
		tempSwfLog.setRegistNo(currSwfLog.getRegistNo());
		tempSwfLog.setPolicyNo(currSwfLog.getPolicyNo());
		tempSwfLog.setInsuredName(currSwfLog.getInsuredName());
		tempSwfLog.setLossItemCode(currSwfLog.getLossItemCode());
		tempSwfLog.setLossItemName(currSwfLog.getLossItemName());
		tempSwfLog.setRiskCode(currSwfLog.getRiskCode());
		tempSwfLog.setComCode(currSwfLog.getComCode());
		tempSwfLog.setBeforeHandlerCode(user.getUserCode());
		tempSwfLog.setBeforeHandlerName(user.getUserName());
		tempSwfLog.setInsureCarFlag(currSwfLog.getInsureCarFlag());
		tempSwfLog.setTypeFlag(currSwfLog.getTypeFlag());
		if (user.getComCode().equals(currSwfLog.getComCode())) {
			tempSwfLog.setHandleDept(user.getComCode());
			tempSwfLog.setDeptName(user.getComName());
		} else {// 本环节数据指定其他机构处理的任务，则任务处理完毕后回归归属机构
			tempSwfLog.setHandleDept(currSwfLog.getComCode());
			tempSwfLog.setDeptName(this.codeService.translateComCode(currSwfLog.getComCode(), true));
		}
		tempSwfLog.setFlowStatus("1");
		tempSwfLog.setPackageID("0");
		tempSwfLog.setMainFlowID("0");
		tempSwfLog.setSubFlowID("0");
		tempSwfLog.setNodeStatus("0");
		tempSwfLog.setPosX(0);
		tempSwfLog.setPosY(0);
		String currTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString();
		tempSwfLog.setFlowInTime(currTime);
		if (task != null) {
			String actorId = task.getTaskData().getActualOwner().getId();
			String processId = task.getTaskData().getProcessId();
			tempSwfLog.setProcessId(processId);
			tempSwfLog.setActorId(actorId);
			tempSwfLog.setTaskId(task.getId());
			SwfConfig swfConfig = this.getSwfConfigService().getSwfConfig(processId, actorId);
			tempSwfLog.setNodeType(swfConfig.getNodeType());
			tempSwfLog.setNodeName(swfConfig.getNodeName());
			tempSwfLog.setNodeNo(swfConfig.getNodeNo());
			tempSwfLog.setTaskNo(swfConfig.getTaskNo());
			tempSwfLog.setTaskType(swfConfig.getTaskType());
			String riskCode = swfConfig.getRiskCode();
			if (DataUtils.emptyToNull(riskCode) != null) {
				String sql = " registNo = '" + currSwfLog.getRegistNo() + "' and riskCode in ('" + riskCode.replaceAll(",", "','") + "')";
				List<Prplregistrpolicy> tempList = this.getPrpLregistrpolicyService().findPrplregistrpolicy(QueryRule.getInstance().addSql(sql));
				if (tempList != null && !tempList.isEmpty()) {
					Prplregistrpolicy p = tempList.get(0);
					tempSwfLog.setPolicyNo(p.getId().getPolicyNo());
					tempSwfLog.setRiskCode(p.getRiskCode());
					if ("compe".equals(tempSwfLog.getNodeType())) {// 单证生产理算，设置keyIn、businessNo
						tempSwfLog.setBusinessNo(p.getClaimNo());
						tempSwfLog.setKeyIn(p.getClaimNo());
					}
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String titleAttr = tempSwfLog.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(tempSwfLog.getFlowInTime()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) + " 上一節點操作人:" + user.getUserName();
			tempSwfLog.setTitleStr(titleAttr);
		}
		// 理算节点特殊处理
		return tempSwfLog;
	}

	/***
	 * 生成流转记录
	 * @param currSwfLog 开始节点
	 * @param tempSwfLog 结束节点
	 * @return
	 */
	protected SwfPathLog getSwfPathLog(SwfLog currSwfLog, SwfLog tempSwfLog, int pathNo) {
		SwfPathLog tempSwfPathLog = new SwfPathLog();
		String flowID = currSwfLog.getId().getFlowID();
		tempSwfPathLog.setId(new SwfPathLogId(flowID, pathNo));
		tempSwfPathLog.setModelNo(currSwfLog.getModelNo());
		String pathName = "從 " + currSwfLog.getNodeName() + " 到 " + tempSwfLog.getNodeName();
		tempSwfPathLog.setPathName(pathName);
		tempSwfPathLog.setStartNodeNo(currSwfLog.getId().getLogNo()); // 只记录序号
		tempSwfPathLog.setStartNodeName(currSwfLog.getNodeName());
		tempSwfPathLog.setEndNodeNo(tempSwfLog.getId().getLogNo()); // 这里已经不是模板定义的号码
		tempSwfPathLog.setEndNodeName(tempSwfLog.getNodeName());
		tempSwfPathLog.setFlag("");
		return tempSwfPathLog;
	}

	/***
	 * 创建工作流前数据预备
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void setBeforeCreate(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog createSwfLog = new SwfLog();
		String registNo = workFlowDto.getBessinessNo();// 备案号作为工作流的业务号
		PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
		String riskCode = prpLregist.getRiskCode();
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		jbpmDto.setBusinessId(registNo);// 设置JBPM工作流关联的理赔工作流ID
		jbpmDto.setActorId("regis");// 设置JBPM工作流关联的理赔工作流备案节点
		if (DataUtils.emptyToNull(riskCode) != null) {
			String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
			jbpmDto.setProcessId(ConstantCodes.riskCodeProcessId.get(strRiskType));
		}
		String flowID = this.getFlowID(riskCode, user.getComCode());// 获取工作流ID
		int logNo = 1;
		createSwfLog.setId(new SwfLogId(flowID, logNo));
		CommonUtils.setProperty(createSwfLog, workFlowDto.getParamMap());//
		createSwfLog.setRiskCode(riskCode);
		//2014-08-25 工作流任務機構從用戶登錄機構（ComCode从user.ComCode）調整為備案出單機構（prpLregist.getMakeCom）
		createSwfLog.setComCode(prpLregist.getMakeCom());
		createSwfLog.setPolicyNo(prpLregist.getPolicyNo());
		createSwfLog.setRegistNo(registNo);
		createSwfLog.setBusinessNo(registNo);
		createSwfLog.setInsuredName(prpLregist.getInsuredName());
		createSwfLog.setLossItemName(prpLregist.getLicenseNo());
		createSwfLog.setKeyIn(registNo);
		createSwfLog.setKeyOut(registNo);
		int modelNo = this.getWorkFlowService().getModelNo(createSwfLog.getRiskCode(), createSwfLog.getComCode());// 兼容老数据，暂时留着
		createSwfLog.setModelNo(modelNo);
		createSwfLog.setHandleDept(user.getComCode());
		createSwfLog.setHandlerCode(user.getUserCode());
		createSwfLog.setHandlerName(user.getUserName());
		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		createSwfLog.setTimeLimit(0);
		createSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		createSwfLog.setFlowStatus("1");
		createSwfLog.setPackageID("0");
		createSwfLog.setFlag("");
		createSwfLog.setTitleStr("創建工作流程");
		createSwfLog.setDeptName(user.getComName());
		createSwfLog.setSubFlowID("0");
		createSwfLog.setMainFlowID("0");
		createSwfLog.setPosX(0);
		createSwfLog.setPosY(0);
		workFlowDto.setCreateSwfLog(createSwfLog);
		workFlowDto.setCreate(true);
		// 初始主流程信息
		SwfFlowMain swfFlowMainDto = new SwfFlowMain();
		swfFlowMainDto.setFlowID(flowID);
		swfFlowMainDto.setFlowName(createSwfLog.getBusinessNo());
		swfFlowMainDto.setFlowStatus("1");
		swfFlowMainDto.setPolicyNo(createSwfLog.getPolicyNo());
		swfFlowMainDto.setCreatDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		swfFlowMainDto.setModelNo(modelNo);
		swfFlowMainDto.setFlag("");
		workFlowDto.setCreateSwfFlowMain(swfFlowMainDto);

	}

	/***
	 * 创建工作流
	 * @param user 当前用户
	 * @param workFlowDto
	 * @return
	 * @throws Exception
	 */
	public void createFlowInfo(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog createSwfLog = workFlowDto.getCreateSwfLog();
		createSwfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		createSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		createSwfLog.setProcessId(jbpmDto.getProcessId());
		createSwfLog.setActorId(jbpmDto.getActorId());
		createSwfLog.setTaskId(jbpmDto.getCurrentTask().getId());
		createSwfLog.setBusinessId(jbpmDto.getBusinessId());
		SwfConfig swfConfig = this.getSwfConfigService().getSwfConfig(jbpmDto.getProcessId(), jbpmDto.getActorId());
		createSwfLog.setNodeType(swfConfig.getNodeType());
		createSwfLog.setNodeName(swfConfig.getNodeName());
		createSwfLog.setNodeNo(swfConfig.getNodeNo());
		createSwfLog.setTaskNo(swfConfig.getTaskNo());
		createSwfLog.setTaskType(swfConfig.getTaskType());
		workFlowDto.setCurrSwfLog(createSwfLog);
	}

	/***
	 * 提交工作流处理任务入口
	 * @param user 当前登录用户
	 * @param workFlowDto 工作流处理对象
	 * @throws Exception
	 */
	public void getSubmitSwfLog(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		int maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
		int maxPathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
		this.getSubmit(user, currSwfLog, workFlowDto, currSwfLog.getActorId(), maxLogNo, maxPathNo);
	}

	/***
	 * 对当前正处理工作流任务的处理
	 * @param workFlowDto 工作流处理大对象
	 * @throws Exception
	 */
	public void getUpdateSwfLog(WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getCurrSwfLog();
        System.out.println("-----getUpdateSwfLog---------------");
		if (currSwfLog != null) {
			String flowID = currSwfLog.getId().getFlowID();
			SwfLog updateSwfLog = new SwfLog();
			currSwfLog = this.getWorkFlowService().findByPrimaryKey(flowID, currSwfLog.getId().getLogNo());
	        System.out.println("-----currSwfLog-flowID-----------------="+flowID);
	        System.out.println("-----currSwfLog-LogNo------------------="+currSwfLog.getId().getLogNo());
	        System.out.println("-----currSwfLog-BusinessId-------------="+currSwfLog.getBusinessId());
	        System.out.println("-----currSwfLog-ProcessId--------------="+currSwfLog.getProcessId());
	        System.out.println("-----currSwfLog-ActorId----------------="+currSwfLog.getActorId());
            System.out.println("-----jbpmDto-getActorId----------------="+workFlowDto.getJbpmDto().getActorId());
            System.out.println("-----jbpmDto-getTaskId-----------------="+workFlowDto.getJbpmDto().getTaskId());
            System.out.println("-----jbpmDto-getProcessId--------------="+workFlowDto.getJbpmDto().getProcessId());
            System.out.println("-----jbpmDto-getProcessInstanceId------="+workFlowDto.getJbpmDto().getProcessInstanceId());
			JbpmDto jbpmDto = workFlowDto.getJbpmDto();
			if (jbpmDto != null) {
			    System.out.println("-----jbpmDto-not-null------");
				jbpmDto.setBusinessId(currSwfLog.getBusinessId());
				jbpmDto.setProcessId(currSwfLog.getProcessId());
				if (DataUtils.emptyToNull(jbpmDto.getActorId()) == null) {
				    System.out.println("-----jbpmDto-getActorId-not-null------");
					jbpmDto.setActorId(currSwfLog.getActorId());
				}
			}
			PropertyUtils.copyProperties(updateSwfLog, currSwfLog);
			updateSwfLog.setId(new SwfLogId(flowID, currSwfLog.getId().getLogNo()));
			if (DataUtils.emptyToNull(updateSwfLog.getHandleTime()) == null) {
				// 回退任务不占位没有处理时间的问题，
				updateSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			}
			if ("3".equals(updateSwfLog.getNodeStatus()) && DataUtils.emptyToNull(updateSwfLog.getHandleTime()) == null) {
				// 回退任务不占位没有处理时间的问题，
				updateSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			}
			CommonUtils.setProperty(updateSwfLog, workFlowDto.getParamMap());
			workFlowDto.setUpdateSwfLog(updateSwfLog);
		}
        System.out.println("-----getUpdateSwfLog---end------------");
	}

	/***
	 * 获取提交工作流生产的节点任务讯息
	 * @param user 当前用户
	 * @param currSwfLog 当前工作流
	 * @param workFlowDto
	 * @param logNo
	 * @param pathNo
	 * @throws Exception
	 */
	private void getSubmit(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto, String actorId, int logNo, int pathNo) throws Exception {
		String status = currSwfLog.getNodeStatus();
		if ("4".equals(status) || "5".equals(status)) {
			currSwfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		}
		this.filterTask(workFlowDto, actorId);
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		List<Task> nextTasks = jbpmDto.getNextTaskList();
		// 多任务节点暂存，提交时指定的第一后续节点（理算 - 计算书）
		if ("M".equals(currSwfLog.getTaskType()) && ("4".equals(status) || "2".equals(status))) {// 多任务节点
			Task mTask = this.getMTask(jbpmDto.getProcessId(), jbpmDto.getActorId(), nextTasks);
			String mActorId = mTask.getTaskData().getActualOwner().getId();
			SwfLog tempSwfLog = this.setSubmit(user, currSwfLog, workFlowDto, logNo, pathNo, mTask);
			CommonUtils.setProperty(tempSwfLog, workFlowDto.getParamMap());
			tempSwfLog.setNodeStatus(status);
			tempSwfLog.setKeyOut(currSwfLog.getKeyOut());
			tempSwfLog.setHandlerCode(user.getUserCode());
			tempSwfLog.setHandlerName(user.getUserName());
			tempSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			this.getSubmit(user, tempSwfLog, workFlowDto, mActorId, logNo + 1, pathNo + 1);
		} else {
			if (nextTasks != null && !nextTasks.isEmpty()) {
				for (Task task : nextTasks) {
					this.setSubmit(user, currSwfLog, workFlowDto, logNo, pathNo, task);
					logNo++;
					pathNo++;
				}
			}
		}
	}

	/**
	 * 设置工作流数据
	 * @param user 当前用户
	 * @param currSwfLog 当前处理工作流任务
	 * @param workFlowDto 当前处理工作流讯息
	 * @param logNo 生产的后续节点任务序号
	 * @param pathNo 生成的当前节点任务到后续节点任务的路径序号
	 * @param task JBPM生成的后续节点任务讯息
	 * @return
	 * @throws Exception
	 */
	private SwfLog setSubmit(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto, int logNo, int pathNo, Task task) throws Exception {
		SwfLog tempSwfLog = this.getSwfLog(user, currSwfLog, logNo, task);
		tempSwfLog.setBusinessId(workFlowDto.getJbpmDto().getBusinessId());
		SwfPathLog tempSwfPathLog = this.getSwfPathLog(currSwfLog, tempSwfLog, pathNo);
		workFlowDto.getSubmitSwfLogList().add(tempSwfLog);
		workFlowDto.getSubmitSwfPathLogList().add(tempSwfPathLog);
		return tempSwfLog;
	}

	/***
	 * 过滤JBPM生成的不做业务处理的节点
	 * @param workFlowDto
	 * @param actorId
	 */
	private void filterTask(WorkFlowDto workFlowDto, String actorId) {
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		List<Task> nextTasks = new ArrayList<Task>();
		for (Task task : jbpmDto.getNextTaskList()) {
			String nextActorId = task.getTaskData().getActualOwner().getId();
			if (nextActorId.equals(actorId) || nextActorId.endsWith("_cancel") || nextActorId.startsWith("request_")) {
				continue;
			}
			nextTasks.add(task);
		}
		jbpmDto.setNextTaskList(nextTasks);
	}

	/**
	 * 找出多任务节点指定的后续单任务节点
	 * @param processId JBPM工作流流程图ID
	 * @param actorId JBPM工作流当前节点ID
	 * @param nextTasks 当前多任务节点任务生产的后续节点任务
	 * @return
	 */
	private Task getMTask(String processId, String actorId, List<Task> nextTasks) {
		SwfConfig swfConfig = this.getSwfConfigService().getSwfConfig(processId, actorId);
		for (Task task : nextTasks) {
			if (task.getTaskData().getActualOwner().getId().equals(swfConfig.getTaskId())) {
				return task;
			}
		}
		return null;
	}

	/***
	 * 获取工作流流水号
	 * @param riskCode 险种
	 * @param comCode 归属机构
	 * @return
	 * @throws Exception
	 */
	protected String getFlowID(String riskCode, String comCode) throws Exception {
		int year = DateTime.current().getYear();
		String tableName = "swfflowmain";
		return this.getBillService().getNo(tableName, riskCode, comCode, year);
	}

	/***
	 * 審核流程
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void dealAuditJbpm(WorkFlowDto workFlowDto) throws Exception {
		this.getUpdateSwfLog(workFlowDto);
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		if (jbpmDto != null) {
			ActionContext act = ActionContext.getContext();
			Map<String, Object> session = act.getSession();
			UserDto user = (UserDto) session.get("user");
			if (workFlowDto.getCreate()) {
				this.setAuditBeforeCreate(user, workFlowDto);
				this.jbpmBusinessService.startProcess(jbpmDto);// JBPM创建工作流
				jbpmDto.setBpmSuccess(true);
				this.createFlowInfo(user, workFlowDto);
				this.getAuditSubmit(user, workFlowDto.getCreateSwfLog(), workFlowDto, jbpmDto.getActorId(), 2, 1);
			} else if (workFlowDto.getSubmit() || workFlowDto.getBack() || workFlowDto.getClose()) {
				this.jbpmBusinessService.processTask(jbpmDto);
				jbpmDto.setBpmSuccess(true);
				workFlowDto.setClose(jbpmDto.getClose());// 工作流处理后是否关闭
				SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
				currSwfLog.setKeyOut(currSwfLog.getBusinessNo());
				String flowID = currSwfLog.getId().getFlowID();
				int maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
				int maxPathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
				this.getAuditSubmit(user, currSwfLog, workFlowDto, currSwfLog.getActorId(), maxLogNo, maxPathNo);
				workFlowDto.setUpdate(true);
				if (workFlowDto.getBack()) {// 逆向流转 回退处理
					this.setBackSwfLog(currSwfLog, user);
					SwfLog backSwfLog = workFlowDto.getSubmitSwfLogList().get(0);
					String condition = " flowId = '" + flowID + "' and nodeType = '" + backSwfLog.getNodeType() + "' order by logNo desc";
					SwfLog tempSwfLog = this.getWorkFlowService().findByConditions(condition).get(0);
					backSwfLog.setHandleDept(tempSwfLog.getHandleDept());
					backSwfLog.setHandlerCode(tempSwfLog.getHandlerCode());
					backSwfLog.setHandlerName(tempSwfLog.getHandlerName());
					backSwfLog.setBusinessType(currSwfLog.getNodeType());
					backSwfLog.setNodeStatus("3");// 表示退回的
					backSwfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				}
				if (workFlowDto.getClose()) {// 关闭工作流
					this.endAudit(user, workFlowDto);
				}
			}
		}
	}

	/***
	 * 結束審核流程
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	private void endAudit(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		List<SwfConfig> list = this.getSwfConfigService().findByCondition(" processId = '" + currSwfLog.getProcessId() + "' and endFlag = 1 ");
		if (list != null && !list.isEmpty()) {
			int maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID);
			int maxPathNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
			TaskData taskData = new TaskData();
			taskData.setProcessId(currSwfLog.getProcessId());
			taskData.setActualOwner(new User(list.get(0).getId().getActorId()));
			Task task = new Task();
			task.setTaskData(taskData);
			SwfLog endcaSwfLog = this.setSubmit(user, currSwfLog, workFlowDto, maxLogNo, maxPathNo, task);
			endcaSwfLog.setKeyIn(endcaSwfLog.getBusinessNo());
			endcaSwfLog.setHandlerName("");
			endcaSwfLog.setNodeStatus("4");
			String currTime = DateTime.current().toString(DateTime.YEAR_TO_SECOND);
			endcaSwfLog.setHandleTime(currTime);
		}
		SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(flowID);
		if (swfFlowMainDto != null) {
			swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
			swfFlowMainDto.setFlowStatus("0");
		}
		workFlowDto.setCloseSwfFlowMain(swfFlowMainDto);
	}

	private void getAuditSubmit(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto, String actorId, int logNo, int pathNo) throws Exception {
		currSwfLog.setNodeStatus("4");
		currSwfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		List<Task> nextTasks = jbpmDto.getNextTaskList();
		if (nextTasks != null && !nextTasks.isEmpty()) {
			if (workFlowDto.getBack()) {
				for (Task task : nextTasks) {
					if (task.getTaskData().getActualOwner().getId().equals(workFlowDto.getNextActorId())) {
						this.setSubmit(user, currSwfLog, workFlowDto, logNo, pathNo, task);
					}
				}
			} else if (workFlowDto.getSubmit()) {
				this.setSubmit(user, currSwfLog, workFlowDto, logNo, pathNo, nextTasks.get(0));
			}
		}
	}

	private void setAuditBeforeCreate(UserDto user, WorkFlowDto workFlowDto) throws Exception {
	    System.out.println("-----setAuditBeforeCreate----start---");
	    System.out.println("-----getProcessId----------------="+workFlowDto.getJbpmDto().getProcessId());
	    System.out.println("-----getActorId------------------="+workFlowDto.getJbpmDto().getActorId());
	    System.out.println("-----getTaskId-------------------="+workFlowDto.getJbpmDto().getTaskId());
	    System.out.println("-----getProcessInstanceId--------="+workFlowDto.getJbpmDto().getProcessInstanceId());
		SwfLog createSwfLog = new SwfLog();
		String compensateNo = workFlowDto.getBessinessNo();// 备案号作为工作流的业务号
		PrpLcompensate prpLcompensate = this.getPrpLcompensateService().findPrpLcompensate(compensateNo);
		String riskCode = prpLcompensate.getRiskCode();
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		jbpmDto.setBusinessId(compensateNo);// 设置JBPM工作流关联的理赔工作流ID
		jbpmDto.setActorId("Broker");// 审核流程第一个节点，出单员
		jbpmDto.setProcessId(ConstantCodes.riskCodeProcessId.get("AUDIT"));
		//mantis：CLM0139，處理人員：DP0713，需求單編號：追償案件僅有費用審核流程確認 START
//		if ("7".equals(prpLcompensate.getPaySituation())) {//追償給付類型為費用，設置狀態，流程直接提交至部門主管級別
//			jbpmDto.getParamsMap().put("feeFlag", true);
//		}
		//mantis：CLM0139，處理人員：DP0713，需求單編號：追償案件僅有費用審核流程確認 END
		String flowID = this.getFlowID(riskCode, user.getComCode());// 获取工作流ID
		PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
		PrpLregist prpLregist = this.prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
		int logNo = 1;
		createSwfLog.setId(new SwfLogId(flowID, logNo));
		CommonUtils.setProperty(createSwfLog, workFlowDto.getParamMap());//
		createSwfLog.setRiskCode(riskCode);
		createSwfLog.setComCode(user.getComCode());
		createSwfLog.setPolicyNo(prpLregist.getPolicyNo());
		createSwfLog.setRegistNo(prpLregist.getRegistNo());
		createSwfLog.setBusinessNo(compensateNo);
		createSwfLog.setInsuredName(prpLregist.getInsuredName());
		createSwfLog.setLossItemName(prpLregist.getLicenseNo());
		createSwfLog.setKeyIn(compensateNo);
		createSwfLog.setKeyOut(compensateNo);
		int modelNo = -1;
		createSwfLog.setModelNo(modelNo);
		createSwfLog.setHandleDept(user.getComCode());
		createSwfLog.setHandlerCode(user.getUserCode());
		createSwfLog.setHandlerName(user.getUserName());
		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		createSwfLog.setTimeLimit(0);
		createSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		createSwfLog.setFlowStatus("1");
		createSwfLog.setPackageID("0");
		createSwfLog.setFlag("");
		createSwfLog.setTitleStr("創建工作流程");
		createSwfLog.setDeptName(user.getComName());
		createSwfLog.setSubFlowID("0");
		createSwfLog.setMainFlowID("0");
		createSwfLog.setPosX(0);
		createSwfLog.setPosY(0);
		workFlowDto.setCreateSwfLog(createSwfLog);
		workFlowDto.setCreate(true);
		// 初始主流程信息
		SwfFlowMain swfFlowMainDto = new SwfFlowMain();
		swfFlowMainDto.setFlowID(flowID);
		swfFlowMainDto.setFlowName(createSwfLog.getBusinessNo());
		swfFlowMainDto.setFlowStatus("1");
		swfFlowMainDto.setPolicyNo(createSwfLog.getPolicyNo());
		swfFlowMainDto.setCreatDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
		swfFlowMainDto.setModelNo(modelNo);
		swfFlowMainDto.setFlag("");
		workFlowDto.setCreateSwfFlowMain(swfFlowMainDto);
		System.out.println("-----setAuditBeforeCreate----set--------");
		System.out.println("-----jbpmDtogetProcessId---------------="+jbpmDto.getProcessId());
	    System.out.println("-----jbpmDtogetActorId-----------------="+jbpmDto.getActorId());
	    System.out.println("-----jbpmDtogetTaskId------------------="+jbpmDto.getTaskId());
	    System.out.println("-----jbpmDtogetProcessInstanceId-------="+jbpmDto.getProcessInstanceId());
	    System.out.println("-----setAuditBeforeCreate----end------=");
	}

	private CodeService codeService;
	private BillService billService;
	private WorkFlowService workFlowService;

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public SwfConfigService getSwfConfigService() {
		return swfConfigService;
	}

	public void setSwfConfigService(SwfConfigService swfConfigService) {
		this.swfConfigService = swfConfigService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpLscheduleMainWFService getPrpLscheduleMainWFService() {
		return prpLscheduleMainWFService;
	}

	public void setPrpLscheduleMainWFService(PrpLscheduleMainWFService prpLscheduleMainWFService) {
		this.prpLscheduleMainWFService = prpLscheduleMainWFService;
	}

	public PrpLscheduleItemService getPrpLscheduleItemService() {
		return prpLscheduleItemService;
	}

	public void setPrpLscheduleItemService(PrpLscheduleItemService prpLscheduleItemService) {
		this.prpLscheduleItemService = prpLscheduleItemService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public JbpmBusinessService getJbpmBusinessService() {
		return jbpmBusinessService;
	}

	public void setJbpmBusinessService(JbpmBusinessService jbpmBusinessService) {
		this.jbpmBusinessService = jbpmBusinessService;
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

}

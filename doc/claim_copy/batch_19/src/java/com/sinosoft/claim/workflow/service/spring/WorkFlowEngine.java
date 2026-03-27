package com.sinosoft.claim.workflow.service.spring;

import ins.framework.common.DateTime;
import ins.framework.utils.BeanUtils;
import ins.framework.utils.DataUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpSession;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLpersonTraceId;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfCondition;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogId;
import com.sinosoft.claim.schema.model.SwfNode;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.model.SwfPathLogId;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonTraceService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleItemService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfConditionService;
import com.sinosoft.claim.schema.service.facade.SwfConfigService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.SwfPathService;
import com.sinosoft.claim.workflow.service.facade.JbpmBusinessService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 工作流引擎处理类
 * @author 中科软
 */
public class WorkFlowEngine {

	protected SwfConfigService swfConfigService;
	protected PrplregistrpolicyService prpLregistrpolicyService;
	protected PrpLscheduleMainWFService prpLscheduleMainWFService;
	protected PrpLscheduleItemService prpLscheduleItemService;
	protected PrpLclaimService prpLclaimService;
	protected PrpLregistService prpLregistService;
	protected JbpmBusinessService jbpmBusinessService;
	protected PrpLcompensateService prpLcompensateService;
	protected PrpCmainService prpCmainService;
	protected SwfPathService swfPathService;
	protected SwfNodeService swfNodeService;
	protected SwfConditionService swfConditionService;
	protected CommonService commonService;
	protected SwfLogService swfLogService;
	protected PrpLpersonTraceService prpLpersonTraceService;
	/**
	 * 处理jbpm工作流任务
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void deal(WorkFlowDto workFlowDto) throws Exception {
		this.getUpdateSwfLog(workFlowDto);
		this.initWorkFlowParameters(workFlowDto);
		ActionContext act = ActionContext.getContext();
		Map<String, Object> session = act.getSession();
		UserDto user = (UserDto) session.get("user");
		if (workFlowDto.getCreate()) {//備案开启工作流
			this.setBeforeCreate(user, workFlowDto);
			if (workFlowDto.getSubmit() && "4".equals(workFlowDto.getStatus())) {
				workFlowDto.setMaxLogNo(2);
				workFlowDto.setMaxPathLogNo(1);
				this.getSubmit(user, workFlowDto.getCreateSwfLog(), workFlowDto);
			}
		} else if (workFlowDto.getReOpen()) {//重開賠案
			this.reOpenWorkFlow(user, workFlowDto);
		} else if (workFlowDto.getSubmit() || workFlowDto.getClose() || workFlowDto.getBack()) {
			SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
			String flowID = currSwfLog.getId().getFlowID();
			workFlowDto.setMaxLogNo(this.getWorkFlowService().getSwfLogMaxLogNo(flowID));
			workFlowDto.setMaxPathLogNo(this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID));
			if (workFlowDto.getBack()) {// 逆向流转 回退处理
				this.getBackProcess(user, workFlowDto);
			} else if (workFlowDto.getSubmit()) {// 正向流转
				if(workFlowDto.getClaimCancel()){//註銷拒賠
					this.setClaimCancel(user, currSwfLog, workFlowDto);
				} else {
					this.getSubmit(user, currSwfLog, workFlowDto);
				}
				
			}
			if (workFlowDto.getClose()) {// 关闭工作流
				this.getClose(user, workFlowDto);
			}
		}
		//
		this.specialProcess(workFlowDto);
	}
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 处理jbpm工作流任务
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void deal4Ws(WorkFlowDto workFlowDto,HttpSession session) throws Exception {
		this.getUpdateSwfLog(workFlowDto);
		this.initWorkFlowParameters(workFlowDto);
//		ActionContext act = ActionContext.getContext();
//		Map<String, Object> session = act.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		if (workFlowDto.getCreate()) {//備案开启工作流
			this.setBeforeCreate(user, workFlowDto);
			if (workFlowDto.getSubmit() && "4".equals(workFlowDto.getStatus())) {
				workFlowDto.setMaxLogNo(2);
				workFlowDto.setMaxPathLogNo(1);
				this.getSubmit(user, workFlowDto.getCreateSwfLog(), workFlowDto);
			}
		} else if (workFlowDto.getReOpen()) {//重開賠案
			this.reOpenWorkFlow(user, workFlowDto);
		} else if (workFlowDto.getSubmit() || workFlowDto.getClose() || workFlowDto.getBack()) {
			SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
			String flowID = currSwfLog.getId().getFlowID();
			workFlowDto.setMaxLogNo(this.getWorkFlowService().getSwfLogMaxLogNo(flowID));
			workFlowDto.setMaxPathLogNo(this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID));
			if (workFlowDto.getBack()) {// 逆向流转 回退处理
				this.getBackProcess(user, workFlowDto);
			} else if (workFlowDto.getSubmit()) {// 正向流转
				if(workFlowDto.getClaimCancel()){//註銷拒賠
					this.setClaimCancel(user, currSwfLog, workFlowDto);
				} else {
					this.getSubmit(user, currSwfLog, workFlowDto);
				}
				
			}
			if (workFlowDto.getClose()) {// 关闭工作流
				this.getClose(user, workFlowDto);
			}
		}
		//
		this.specialProcess(workFlowDto);
	}
	
	/***
	 * 流程申請註銷拒賠的情況
	 * @param user
	 * @param currSwfLog 當前申請註銷拒賠的節點 
	 * @param workFlowDto
	 * @throws Exception 
	 */
	protected void setClaimCancel(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto) throws Exception{
		int modelNo = currSwfLog.getModelNo();
		SwfNode swfNode = this.workFlowService.getCancelSwfNode(modelNo);
		//找到註銷/拒賠節點
		if(swfNode != null){
			SwfLog canceSwfLog = this.getSwfLog(user, currSwfLog, workFlowDto.getMaxLogNo(), swfNode);
			workFlowDto.getSubmitSwfLogList().add(canceSwfLog);
			workFlowDto.getSubmitSwfPathLogList().add(this.getSwfPathLog(currSwfLog, canceSwfLog , workFlowDto.getMaxPathLogNo()));
		}
	}
	
	/***
	 * 重开赔案处理
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	protected void reOpenWorkFlow(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog endcaSwfLog = workFlowDto.getUpdateSwfLog();// 提取當前賠案的最後一次結案
		String flowID = endcaSwfLog.getId().getFlowID();
		SwfFlowMain swfFlowMain = this.getWorkFlowService().findFlowMainByPrimaryKey(flowID);
		int maxLogNo = 0;
		int pathLogNo = 0;
		if ("2".equals(swfFlowMain.getStoreFlag())) {// 已转储
			maxLogNo = this.getWorkFlowService().getSwfLogStoreMaxLogNo(flowID); // LogNo+1
			pathLogNo = this.getWorkFlowService().getSwfPathLogStoreMaxPathNo(flowID);
		} else {
			maxLogNo = this.getWorkFlowService().getSwfLogMaxLogNo(flowID); // 最大LogNo+1
			pathLogNo = this.getWorkFlowService().getSwfPathLogMaxPathNo(flowID);
		}
		workFlowDto.setMaxLogNo(maxLogNo);
		workFlowDto.setMaxPathLogNo(pathLogNo);
		if (swfFlowMain.getFlowStatus().equals("0")) {
			// 3.1开启工作流主表数据，打开工作流的节点所有数据
			swfFlowMain.setFlowStatus("1");
			workFlowDto.setReOpenSwfFlowMain(swfFlowMain);
		}
		this.getSubmit(user, endcaSwfLog, workFlowDto);
		workFlowDto.setSubmit(true);
		for (SwfLog swfLog : workFlowDto.getSubmitSwfLogList()) {
			swfLog.setTitleStr("重開賠案");
		}
	}
	
	/**
	 * 工作流流转参数设置
	 * @param workFlowDto
	 * @throws Exception
	 */
	protected void initWorkFlowParameters(WorkFlowDto workFlowDto) throws Exception {
		// JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		SwfLog updateSwfLog = workFlowDto.getUpdateSwfLog();
		if (updateSwfLog != null && workFlowDto.getSubmit() && !workFlowDto.getReOpen()) {//
			Map<String, Object> flowParamMap = workFlowDto.getFlowParamMap();
			String registNo = updateSwfLog.getRegistNo();// 取备案号码
			if (DataUtils.emptyToNull(registNo) == null) {// 创建工作流的时候，从WorkFlowDto的BessinessNo取，之前Action有预设
				registNo = workFlowDto.getBessinessNo();
			}
			PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
			// jbpmDto.putParamsMap("registType", prpLregist.getRegistType());//
			// 备案类型
			flowParamMap.put("policyNo", prpLregist.getPolicyNo());
			flowParamMap.put("registPolicyNo", prpLregist.getPolicyNo());
			flowParamMap.put("registType", prpLregist.getRegistType());// 備案類型
			flowParamMap.put("registNo", registNo);
			String flowID = updateSwfLog.getId().getFlowID();
			flowParamMap.put("flowID", flowID);
			flowParamMap.put("logNo", updateSwfLog.getId().getLogNo().intValue());
			flowParamMap.put("riskCode", updateSwfLog.getRiskCode());
//			String strRiskType = codeService.translateRiskCodetoRiskType(updateSwfLog.getRiskCode());
			// 理算退回，需要在生成单证任务，核损提交有单证任务，就不在生成。
//			車險生成單證：
//			1>無未處理完畢單證節點（未有核損提交 或 理算退回定損的情況）;
//			2>有單證節點且已處理完畢情況（則需判斷未走簡易流程的案件是否存在未處理完畢的理算）
//
//			String sql = " nodeStatus < 4 and nodeType = 'certi' and flowId = '" + flowID + "'";
//			if ("D".equals(strRiskType)) {
//				sql = " ((nodeStatus < 4 and nodeType = 'certi') or (nodeStatus < 4 and nodeType = 'compe' and riskCode='" + updateSwfLog.getRiskCode() + "')) and flowId = '" + flowID + "'";
//			}
//			List<SwfLog> list = this.getWorkFlowService().findByConditions(sql);
//			if (list == null || list.isEmpty()) {// 生成单证任务的条件限制。只能存在一个可处理单证任务
//				// jbpmDto.putParamsMap("certiFlag", true);
//				flowParamMap.put("certiFlag", true);
//			}
		}
	}

	/**
	 * 回退任務處理
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	protected void getBackProcess(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		//查找可退回的節點
		SwfLog backSwfLog = this.getWorkFlowService().findBackSwfLog(flowID, currSwfLog.getId().getLogNo());
		if (backSwfLog == null) {// 該節點任務未找到可退回的節點任務，且當前未指定要退回的節點
			throw new UserException(1, 3, "工作流", "該節點任務未找到可退回的節點任務");
		}
		//判斷本節點同級所有節點是否處於可退回處理狀態
		List<SwfLog> tempList = this.canBack(backSwfLog);
		//指定退回某個節點的情況，比如車險理算退回定損
		List<SwfLog> backList = workFlowDto.getBackSwfLogList();
		if (backList == null || backList.isEmpty()) {
			// 若沒有指定退回的節點，則根據swfPathLog流轉路線查找退回節點
			backList.add(backSwfLog);
		}
		// 設置當前節點已退回
		this.setBackSwfLog(currSwfLog, user);
		for (SwfLog swfLog : tempList) {
			if (swfLog.getId().getLogNo() - currSwfLog.getId().getLogNo() != 0) {
				// 同級其他節點已退回狀態設置（比如操作強制險退回，那麼任意險也需要設置已退回）
				this.setBackSwfLog(swfLog, user);
			}
		}
		workFlowDto.setUpdate(true);
		workFlowDto.setUpdateSwfLog(currSwfLog);
		workFlowDto.setSubmit(true);
		workFlowDto.setUpdateSwfLogList(tempList);
		String currTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString();
		for (SwfLog swfLog : backList) {
			SwfLog tempSwfLog = new SwfLog();
			PropertyUtils.copyProperties(tempSwfLog, swfLog);
			tempSwfLog.setId(new SwfLogId(flowID, workFlowDto.getMaxLogNo()));
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
			workFlowDto.getSubmitSwfLogList().add(tempSwfLog);
			workFlowDto.getSubmitSwfPathLogList().add(this.getSwfPathLog(currSwfLog, tempSwfLog, workFlowDto.getMaxPathLogNo()));
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
//		backSwfLog.setHandleTime(currentTime);
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
		//節點要退回，則需要判斷該節點同級的任務是否都未處理才可以退回，且退回操作完成時，同級節點的任務狀態也必須設置已退回
		//所謂同級任務是指：產生該任務的前一個任務在提交產生本任務時產生的其他任務。
		//比如：理算退回單證時，因單證提交可能產生強制險理算和任意險理算，所以只有當任意險和強制險理算均未處理時，才校驗可以退回處理。
		String condition = " flowID = '" + flowID + "' and logNo in (select endNodeNo from swfPathLog where flowID='" + flowID + "' and startNodeNo=" + backSwfLog.getId().getLogNo() + ")";
		List<SwfLog> tempList = this.getWorkFlowService().findByConditions(condition);
		for (SwfLog swfLog : tempList) {
			if ("5".equals(swfLog.getNodeStatus())) {
				throw new UserException(1, 3, "工作流", "該節點任務已退回！");
			} else if ("4".equals(swfLog.getNodeStatus())) {
				throw new UserException(1, 3, "工作流", "該節點任務已由" + swfLog.getHandlerName() + "(" + swfLog.getHandlerCode() + ")處理提交！");
			} else if ("2".equals(swfLog.getNodeStatus()) && !("verif".equals(swfLog.getNodeType()) || "veriw".equals(swfLog.getNodeType()) || "propv".equals(swfLog.getNodeType())||"veric".equals(swfLog.getNodeType()))) {
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
	 * 流程結束，結案處理
	 * @param user
	 * @param workFlowDto
	 * @throws Exception
	 */
	protected void getClose(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getUpdateSwfLog();
		String flowID = currSwfLog.getId().getFlowID();
		if (workFlowDto.getAutoClose()) {// 自动结案的情况
			List<SwfLog> submitSwfLogList = workFlowDto.getSubmitSwfLogList();
			if (!submitSwfLogList.isEmpty()) {
				this.setAutoEndCaseParam(submitSwfLogList.get(0));
			} else {
				String endcaSql = "flowId='" + currSwfLog.getId().getFlowID() + "' and policyNo = '" + currSwfLog.getPolicyNo() + "' and nodeType='endca' and nodestatus < 4 ";
				List<SwfLog> tempList = this.getWorkFlowService().findByConditions(endcaSql);
				if (tempList != null && !tempList.isEmpty()) {
					for (SwfLog s : tempList) {
						this.setAutoEndCaseParam(s);
						workFlowDto.getUpdateSwfLogList().add(s);
					}
				}
			}
		}
		// 結案判斷是否可以關閉工作流
		String conditonss = "flowId = '" + flowID + "' and nodeType='compe' and nodestatus < 4";
		List<SwfLog> compeList = this.getWorkFlowService().findNodesByConditions(conditonss);
		if (!CommonUtils.isEmpty(compeList)) {
			// 流程存在多個結案的情況，若其他結案任務未結束，則主流程不能關閉！
			for (int i = 0; i < compeList.size(); i++) {
				SwfLog compe = compeList.get(i);
				if (compe.getKeyIn().equals(currSwfLog.getKeyIn())) {
					compe.setNodeStatus("4");// 关闭理算
					compe.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
					workFlowDto.setUpdateSwfLog2(compe);
					break;
				}
			}
			workFlowDto.getFlowParamMap().put("endFlag", compeList.size() == 1);
		}
		// 车险商强关联全结案，非车（自动结案，手动结案），endFlag = true判断可以结案，才可以关闭主流程
		Boolean endFlag = new Boolean(String.valueOf(workFlowDto.getFlowParamMap().get("endFlag")));
		if (endFlag) {
			SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(flowID);
			if (swfFlowMainDto != null) {
				swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
				swfFlowMainDto.setFlowStatus("0");
			}
			workFlowDto.setCloseSwfFlowMain(swfFlowMainDto);
		}
	}

	/**
	 * 自动结案，设置各结案节点的的参数。
	 * @param endcaSwfLog
	 * @throws Exception
	 */
	protected void setAutoEndCaseParam(SwfLog endcaSwfLog) throws Exception {
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
	protected void specialProcess(WorkFlowDto workFlowDto) throws Exception {
		List<SwfLog> submitSwfLogList = workFlowDto.getSubmitSwfLogList();
		if (submitSwfLogList != null && !submitSwfLogList.isEmpty()) {
			for (SwfLog swfLog : submitSwfLogList) {
				// 注销拒赔
				if ("cance".equals(swfLog.getNodeType()) && workFlowDto.getClaimCancel()) {
					PrpLclaim prpLclaim = this.getPrpLclaimService().findPrpLclaim(swfLog.getKeyIn());
					swfLog.setPolicyNo(prpLclaim.getPolicyNo());
					swfLog.setRiskCode(prpLclaim.getRiskCode());
				}
				if("sched".equals(swfLog.getNodeType())){
					swfLog.setHandlerCode("0");
					swfLog.setHandlerName("");
					swfLog.setSubmitTime(null);
				}
				// 理算暫存的處理
				if("compp".equals(swfLog.getNodeType()) && "2".equals(workFlowDto.getStatus()) && workFlowDto.getSubmit()){
					ActionContext act = ActionContext.getContext();
					Map<String, Object> session = act.getSession();
					UserDto user = (UserDto) session.get("user");
					swfLog.setNodeStatus("2");
					swfLog.setHandlerCode(user.getUserCode());
					swfLog.setHandlerName(user.getUserName());
					String currentTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString();
					swfLog.setHandleTime(currentTime);;
				}
			}
		}
	}

	/***
	 * 获取当前节点后续节点的工作流流转业务数据
	 * @param user 当前用户
	 * @param currSwfLog 当前工作流节点业务数据
	 * @param logNo 新任务节点序号
	 * @param swfNode
	 * @return
	 * @throws Exception
	 */
	protected SwfLog getSwfLog(UserDto user, SwfLog currSwfLog, int logNo, SwfNode swfNode) throws Exception {
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
		tempSwfLog.setNodeType(swfNode.getNodeType());
		tempSwfLog.setNodeName(swfNode.getNodeName());
		tempSwfLog.setNodeNo(swfNode.getId().getNodeNo());
		tempSwfLog.setTaskNo(swfNode.getTaskNo());
		tempSwfLog.setTaskType(swfNode.getTaskType());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String titleAttr = tempSwfLog.getNodeName() + "節點流入時間：" + CommonUtils.getMGDateStr(format.parse(tempSwfLog.getFlowInTime()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) + " 上一節點操作人:" + user.getUserName();
		tempSwfLog.setTitleStr(titleAttr);
		//一經此流轉則後續節點不再走JBPM
		tempSwfLog.setProcessId(null);
		tempSwfLog.setActorId(null);
		tempSwfLog.setBusinessId(null);
		tempSwfLog.setTaskId(null);
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
	protected void setBeforeCreate(UserDto user, WorkFlowDto workFlowDto) throws Exception {
		SwfLog createSwfLog = new SwfLog();
		String registNo = workFlowDto.getBessinessNo();// 备案号作为工作流的业务号
		PrpLregist prpLregist = this.getPrpLregistService().findPrpLregist(registNo);
		workFlowDto.getFlowParamMap().put("registPolicyNo", prpLregist.getPolicyNo());//作为流程引擎流转参数
		workFlowDto.getFlowParamMap().put("registNo", registNo);
		
		String riskCode = prpLregist.getRiskCode();
		String flowID = this.getFlowID(riskCode, user.getComCode());// 获取工作流ID
		workFlowDto.getFlowParamMap().put("flowID", flowID);
		int logNo = 1;
		createSwfLog.setId(new SwfLogId(flowID, logNo));
		CommonUtils.setProperty(createSwfLog, workFlowDto.getParamMap());//
		createSwfLog.setRiskCode(riskCode);
		// 工作流任務機構從用戶登錄機構（ComCode从user.ComCode）調整為備案出單機構（prpLregist.getMakeCom）
		createSwfLog.setComCode(prpLregist.getMakeCom());
		createSwfLog.setPolicyNo(prpLregist.getPolicyNo());
		createSwfLog.setRegistNo(registNo);
		createSwfLog.setBusinessNo(registNo);
		createSwfLog.setInsuredName(prpLregist.getInsuredName());
		createSwfLog.setLossItemName(prpLregist.getLicenseNo());
		createSwfLog.setKeyIn(registNo);
		createSwfLog.setKeyOut(registNo);
		int modelNo = this.getWorkFlowService().getModelNo(createSwfLog.getRiskCode(), createSwfLog.getComCode());
		if(modelNo == -1){
			throw new UserException(0, -1, "工作流", "機構【" + createSwfLog.getComCode() + "】未配置險種【" + createSwfLog.getRiskCode() + "】理賠工作流模板！");
		}
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
		createSwfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		createSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// 讀取模板第一個節點的訊息
		SwfNode swfNode = this.getWorkFlowService().getFirstSwfNode(modelNo);
		createSwfLog.setNodeType(swfNode.getNodeType());
		createSwfLog.setNodeName(swfNode.getNodeName());
		createSwfLog.setNodeNo(swfNode.getId().getNodeNo());
		createSwfLog.setTaskNo(swfNode.getTaskNo());
		createSwfLog.setTaskType(swfNode.getTaskType());

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
	 * 对当前正处理工作流任务的处理
	 * @param workFlowDto 工作流处理大对象
	 * @throws Exception
	 */
	protected void getUpdateSwfLog(WorkFlowDto workFlowDto) throws Exception {
		SwfLog currSwfLog = workFlowDto.getCurrSwfLog();
		if (currSwfLog != null) {
			String flowID = currSwfLog.getId().getFlowID();
			SwfLog updateSwfLog = new SwfLog();
			currSwfLog = this.getWorkFlowService().findByPrimaryKey(flowID, currSwfLog.getId().getLogNo());
			PropertyUtils.copyProperties(updateSwfLog, currSwfLog);
			updateSwfLog.setId(new SwfLogId(flowID, currSwfLog.getId().getLogNo()));
			if (DataUtils.emptyToNull(updateSwfLog.getHandleTime()) == null) {
				// 回退任务不占位没有处理时间的问题，
				updateSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			}
			CommonUtils.setProperty(updateSwfLog, workFlowDto.getParamMap());
			workFlowDto.setUpdateSwfLog(updateSwfLog);
		}
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
	protected void getSubmit(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto) throws Exception {
		String status = currSwfLog.getNodeStatus();
		if ("4".equals(status) || "5".equals(status)) {
			currSwfLog.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		}
		List<SwfLog> nextTasks = this.getNextSwfLogs(user, currSwfLog, workFlowDto);
		for (SwfLog tempSwfLog : nextTasks) {
			if ("sched".equals(currSwfLog.getNodeType())) {// 调度环节的特殊处理
				//根據旺旺部分新增
				if ("wound".equals(tempSwfLog.getNodeType())) {
					PrpLpersonTraceId prpLpersonTraceId=new PrpLpersonTraceId();
					if(tempSwfLog.getLossItemCode()!=null){
						prpLpersonTraceId.setPersonNo(Integer.parseInt(tempSwfLog.getLossItemCode()));
						prpLpersonTraceId.setRegistNo(tempSwfLog.getRegistNo());
						PrpLpersonTrace prpLpersonTrace = prpLpersonTraceService.findPrpLpersonTrace(prpLpersonTraceId);
						if(prpLpersonTrace!=null){
							if(!(CommonUtils.isEmpty(prpLpersonTrace.getReferKind()))&&("21".equals(prpLpersonTrace.getReferKind()))){
								tempSwfLog.setRiskCode(ConstantCodes.RISKCODE_DAZ);
							}
						}
					}
				}
			}
			workFlowDto.getSubmitSwfLogList().add(tempSwfLog);
			workFlowDto.getSubmitSwfPathLogList().add(this.getSwfPathLog(currSwfLog, tempSwfLog , workFlowDto.getMaxPathLogNo()));
		}
		// 多任务节点暂存，提交时指定的第一后续节点（理算 ）
		if ("M".equals(currSwfLog.getTaskType())) {// 連續任務節點提交，則需要繼續處理
			// 如果當前是多任務節點、即只負責產生後續多任務流轉分支，本身不代表某一任務實際含義 ）理算節點
		}
		int modelNo = currSwfLog.getModelNo();
		for (SwfLog tempSwfLog : nextTasks) {
			// M節點的task指向節點，繼續做提交處理
			if ("A".equals(tempSwfLog.getTaskType())) {//自動流轉節點
				//自動節點自動流轉的路線及條件查詢
				//1.當前節點到自動節點的路線（比如計算書節點是自動節點，提算提交到計算書，需要計算書自動流轉的條件就配置在 理算 到 計算書 這個swfpath路線上）
				//2.查找配置在 該路線上的條件，逐一判斷是否有成立，OR 關係
				List<SwfCondition> wfConditionList = this.workFlowService.getSwfConditionForAutoTask(modelNo, currSwfLog.getNodeNo(), tempSwfLog.getNodeNo());
				if (!CommonUtils.isEmpty(wfConditionList)) {// 如果有條件，則判斷條件是否成立
					boolean flag = true;
					for (SwfCondition s : wfConditionList) {
						if (CommonUtils.isEmpty(s.getConfigText()) || !eval(s.getConfigText(), workFlowDto.getFlowParamMap())) {
							flag = false;
							break;
						}
					}
					if (!flag) {
						continue;
					}
				}
				CommonUtils.setProperty(tempSwfLog, workFlowDto.getParamMap());
				tempSwfLog.setNodeStatus(status);
				tempSwfLog.setKeyOut(currSwfLog.getKeyOut());
				tempSwfLog.setHandlerCode(user.getUserCode());
				tempSwfLog.setHandlerName(user.getUserName());
				tempSwfLog.setHandleTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				this.getSubmit(user, tempSwfLog, workFlowDto );
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
	 * @param SwfNode swfNode
	 * @return
	 * @throws Exception
	 */
	protected SwfLog setSubmit(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto, int logNo, int pathNo, SwfNode swfNode) throws Exception {
		SwfLog tempSwfLog = this.getSwfLog(user, currSwfLog, logNo, swfNode);
		SwfPathLog tempSwfPathLog = this.getSwfPathLog(currSwfLog, tempSwfLog, pathNo);
		workFlowDto.getSubmitSwfLogList().add(tempSwfLog);
		workFlowDto.getSubmitSwfPathLogList().add(tempSwfPathLog);
		return tempSwfLog;
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
	 * 根據路徑查找後續節點
	 * @param currSwfLog
	 * @param workFlowDto
	 * @return
	 * @throws Exception
	 */
	protected List<SwfLog> getNextSwfLogs(UserDto user, SwfLog currSwfLog, WorkFlowDto workFlowDto) throws Exception {
		List<SwfLog> nextSwfLogs = new ArrayList<SwfLog>();
		int modelNo = currSwfLog.getModelNo();
		int startNodeNo = currSwfLog.getNodeNo();
		// 查找節點路線
		List<SwfPath> wfPathList = this.workFlowService.getSwfPath(modelNo, startNodeNo);
		for (SwfPath swfPath : wfPathList) {
			// 有条件约束的情况下，首先要取得所以的条件，一一甄别
			List<SwfLog> tempSwfLogs = this.produce(user, currSwfLog, swfPath, workFlowDto);
			nextSwfLogs.addAll(tempSwfLogs);
		}
		return nextSwfLogs;
	}

	/**
	 * 檢查并返回條件可產生的節點任務數
	 * @param user
	 * @param currSwfLog
	 * @param swfPath
	 * @param workFlowDto
	 * @throws Exception
	 * @return
	 */
	protected List<SwfLog> produce(UserDto user, SwfLog currSwfLog, SwfPath swfPath, WorkFlowDto workFlowDto) throws Exception {
		Map<String, Object> flowParamMap = workFlowDto.getFlowParamMap();
		List<SwfLog> swfLogs = new ArrayList<SwfLog>();
		int modelNo = swfPath.getId().getModelNo();
		int nextNodeNo = swfPath.getEndNodeNo();// 後續節點號
		// configType in ('0','1','2')
		List<SwfCondition> wfConditionList = this.workFlowService.getSwfConditionForPath(modelNo, swfPath.getId().getPathNo());
		SwfNode swfNode = this.workFlowService.getSwfNode(swfPath.getId().getModelNo(), nextNodeNo);
		if (CommonUtils.isEmpty(wfConditionList)) {// 沒有條件限制則默認產生一個任務節點
			swfLogs.add(this.getSwfLog(user, currSwfLog, workFlowDto.getMaxLogNo(), swfNode));
		} else {
			// configType  0 ： HQL（有很友好的事務支持） ; 1：sql ; 2 :JavaScript 腳本（能被scriptengine正確解析的）
			String configType = null;
			String statements = null;
			String[] params = null;
			String[] paramtypes = null;
			String[] destProperty = null;//需要回寫swflog的屬性
			String[] origProperty = null;//若目標SQL為HQL，則從指定屬性取
			for (SwfCondition swfCondition : wfConditionList) {
				configType = swfCondition.getConfigType();
				if("2".equals(configType)){//條件為腳本
					if(!CommonUtils.isEmpty(swfCondition.getConfigText()) && eval(swfCondition.getConfigText(), flowParamMap)){
						swfLogs.add(this.getSwfLog(user, currSwfLog, workFlowDto.getMaxLogNo(), swfNode));
					}
				} else {
					Class<?> origClass = null;
					statements = swfCondition.getConfigText();
					// 參數名
					params = CommonUtils.isEmpty(swfCondition.getBusinessKey()) ? null : swfCondition.getBusinessKey().split(",");
					// p 普通參數，pl in查詢的參數
					paramtypes = CommonUtils.isEmpty(swfCondition.getDataType()) ? null : swfCondition.getDataType().split(",");
					// 查詢結果中回寫的到swflog的字段
					destProperty = CommonUtils.isEmpty(swfCondition.getDestProperty()) ? null : swfCondition.getDestProperty().split(",");
					origProperty = CommonUtils.isEmpty(swfCondition.getOrigProperty()) ? null : swfCondition.getOrigProperty().split(",");
					
					if ("0".equals(configType)) {//HQL設置查詢實體
						origClass = Class.forName(swfCondition.getOrigClass());
					}
					List<?> list = this.commonService.findBySQL(statements, params, paramtypes, flowParamMap, origClass);
					// swfCondition.Flag ，在swfCondition.ConfigType in (0 , 1 ) 時有效
					// 0 ： 根據查詢結果判斷，若無資料，則會產生該path後續節點任務
					// 1 ： 根據查詢結果判斷，若有資料，則會產生該path後續節點任務
					// 2 ： 根據查詢結果判斷，若有資料，則根據資料的筆數，產生指定path後續節點任務數
					if("0".equals(swfCondition.getFlag())){// 表示有資料則不產生後續節點
						if(CommonUtils.isEmpty(list)){
							SwfLog tempSwfLog = this.getSwfLog(user, currSwfLog, workFlowDto.getMaxLogNo(), swfNode);
							swfLogs.add(tempSwfLog);
						}
					} else {
						if(!CommonUtils.isEmpty(list)){// 表示 condition能查到資料，則產生後續節點
							SwfLog tempSwfLog = null;
							for (Object bean : list) {
								tempSwfLog = this.getSwfLog(user, currSwfLog, workFlowDto.getMaxLogNo(), swfNode);
								if ( destProperty != null && destProperty.length > 0) {
									if ("0".equals(configType)) {
										for (int i = 0, l = origProperty.length; i < l; i++) {
											Object origValue = PropertyUtils.getProperty(bean, origProperty[i].trim());
											try {
												PropertyUtils.setProperty(tempSwfLog, destProperty[i].trim(), origValue);
											} catch (IllegalArgumentException e) {
												Class<?> destType = PropertyUtils.getPropertyType(tempSwfLog, destProperty[i].trim());
												Class<?> origType = PropertyUtils.getPropertyType(bean, origProperty[i].trim());
												if ((origType == Integer.class || origType == int.class) && destType == String.class) {
													PropertyUtils.setProperty(tempSwfLog, destProperty[i].trim(), String.valueOf(origValue));
												} else if (origType == String.class && (destType == Integer.class || destType == int.class)) {
													PropertyUtils.setProperty(tempSwfLog, destProperty[i].trim(), Integer.parseInt(origValue.toString()));
												}
											}
										}
									} else if ("1".equals(configType)) {//普通SQL，則根據配置查詢列，以及目標屬性順序設置回寫
										if (destProperty.length == 1) {// 如果回寫字段只有一列，則是個
											BeanUtils.setProperty(tempSwfLog, destProperty[0].trim(), bean);
										} else {
											Object[] o = (Object[]) bean;
											for (int i = 0, l = destProperty.length; i < l; i++) {
												PropertyUtils.setProperty(tempSwfLog, destProperty[i].trim(), o[i]);
											}
										}
									}
								}
								swfLogs.add(tempSwfLog);
								if("2".equals(swfCondition.getFlag())){// 多任務情況，則根據list繼續生成
									continue;
								} else {// 其他情況只有一筆
									break;
								}
							}
						}
					}
				} 
			}
		}
		return swfLogs;
	}
	
	protected void dealAudit(WorkFlowDto workFlowDto){
		
	}
	
	private Boolean eval(String expr, Map<String, Object> params) {
		try {
			String source = "function evalExpression() {" + expr + "}";
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");
			if (params !=null && !params.isEmpty()) {
				scriptEngine.put("paramsMap", params);
				for (Entry<String, Object> entry : params.entrySet()) {
					scriptEngine.put(entry.getKey(), entry.getValue());
				}
			}
			scriptEngine.eval(source);
			Invocable invoke = (Invocable) scriptEngine;  
			Object result = invoke.invokeFunction("evalExpression");
			if(result!=null){
				return (Boolean)result;
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		} 
	}

	protected CodeService codeService;
	protected BillService billService;
	protected WorkFlowService workFlowService;

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

	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public SwfConditionService getSwfConditionService() {
		return swfConditionService;
	}

	public void setSwfConditionService(SwfConditionService swfConditionService) {
		this.swfConditionService = swfConditionService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpLpersonTraceService getPrpLpersonTraceService() {
		return prpLpersonTraceService;
	}

	public void setPrpLpersonTraceService(
			PrpLpersonTraceService prpLpersonTraceService) {
		this.prpLpersonTraceService = prpLpersonTraceService;
	}
	
}

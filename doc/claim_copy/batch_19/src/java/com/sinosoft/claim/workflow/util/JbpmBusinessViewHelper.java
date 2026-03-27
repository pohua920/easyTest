package com.sinosoft.claim.workflow.util;

import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.service.spring.WorkFlowEngineService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

public class JbpmBusinessViewHelper {
	private CodeService codeService;
	private WorkFlowService workFlowService;
	private SwfLogService swfLogService;
	private WorkFlowEngineService workFlowEngineService;
	private CommonService commonService;

	/***
	 * 保存带工作流的工作流处理(解决业务数据保存于JBPM工作流事务冲突问题)先暂存业务数据
	 * @param service 执行保存业务方法的service
	 * @param methodName 需要service执行的方法
	 * @param workFlowDto 工作流处理对象
	 * @param params 调用业务servce的methodName方法参数
	 * @throws Exception
	 */
	public void saveBusiness(Object service, String methodName, Object... params) throws Exception {
		WorkFlowDto workFlowDto = (WorkFlowDto) params[0];
		if (workFlowDto.isNewWorkFlow()) {// 开启新工作流
			this.invoke(service, methodName, new WorkFlowDto(), params);
			this.workFlowEngineService.dealJbpm(workFlowDto);
			if (workFlowDto != null) {
				this.workFlowService.deal(workFlowDto);
			}
		} else {
			this.invoke(service, methodName, workFlowDto, params);
		}
	}

	/***
	 * 处理工作流 先处理JBPM工作流，再保存业务数据和工作流数据（理算提交开启核赔的时候）
	 * @param service
	 * @param workFlowDto
	 * @param params
	 */
	public void saveWorkFlow(Object service, String methodName, Object... params) throws Exception {
		WorkFlowDto workFlowDto = (WorkFlowDto) params[0];
		if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
			this.workFlowEngineService.dealJbpm(workFlowDto);
		}
		this.invoke(service, methodName, workFlowDto, params);
	}

	/***
	 * 保存工作流(理算退回、核赔)
	 * @param workFlowDto
	 * @throws Exception
	 */
	public void saveWorkFlow(WorkFlowDto workFlowDto) throws Exception {
		if (workFlowDto.isNewWorkFlow()) {// 处理工作流引擎renw
			this.workFlowEngineService.dealJbpm(workFlowDto);
		}
		if (workFlowDto != null) {
			this.workFlowService.deal(workFlowDto);
		}
	}

	/***
	 * 查找并执行执行业务service中
	 * @param service
	 * @param methodName
	 * @param workFlowDto
	 * @param params
	 * @throws Exception
	 */
	private void invoke(Object service, String methodName, WorkFlowDto workFlowDto, Object... params) throws Exception {
		Class<?>[] parameterTypes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			if (i + 1 == params.length) {
				params[i] = workFlowDto;
			} else {
				params[i] = params[i + 1];
			}
			parameterTypes[i] = (params[i]).getClass();
		}

		// 执行service的save方法。请注意参数顺序 params 的顺序 + 最后workFlowDto
		Method method = service.getClass().getMethod(methodName, parameterTypes);
		method.invoke(service, params);//
	}

	/**
	 * 重开赔案任务
	 * @return
	 */
	public WorkFlowDto getJbpmWorkFlowReCase(UserDto user, SwfLog endcaSwfLog, String nextBusinessNo, String nextKeyIn) {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(true);// 启用新工作流引擎处理
		workFlowDto.setReOpen(true);
		JbpmDto jbpmDto = new JbpmDto();// 只有需要工作流流转时，才能
		endcaSwfLog.setNextKeyIn(nextKeyIn);
		endcaSwfLog.setNextBusinessNo(nextBusinessNo);
		workFlowDto.setUpdateSwfLog(endcaSwfLog);
		workFlowDto.setJbpmDto(jbpmDto);
		return workFlowDto;
	}

	/***
	 * 回退任务数据组织入口
	 * @param request
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	public WorkFlowDto getJbpmWorkFlowBack(HttpServletRequest request, SwfLog swfLog) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(true);// 启用新工作流引擎处理
		workFlowDto.setBack(true);
		workFlowDto.setUpdate(true);
		if (swfLog == null) {
			String flowID = request.getParameter("swfLogFlowID");
			String logNo = request.getParameter("swfLogLogNo");
			swfLog = this.swfLogService.findSwfLog(flowID, Integer.parseInt(logNo));
		}
		workFlowDto.setCurrSwfLog(swfLog);
		JbpmDto jbpmDto = new JbpmDto();// 只有需要工作流流转时，才能
		jbpmDto.putParamsMap("status", "5");
		workFlowDto.setJbpmDto(jbpmDto);
		if (request != null) {
			this.setBackCertaParam(request, workFlowDto, swfLog);
		}
		this.check(workFlowDto);
		return workFlowDto;
	}

	/***
	 * 工作流运行数据整理(Action请求)
	 * @param update
	 * @param submit
	 * @param nodeStatus
	 * @param businessNo
	 * @param nextBusinessNo
	 * @param keyOut
	 * @param nextKeyIn
	 * @param flowID
	 * @param logNo
	 * @return
	 * @throws Exception
	 */
	public WorkFlowDto getJbpmWorkFlowDto(HttpServletRequest request, boolean update, boolean submit, String nodeStatus, String businessNo, String nextBusinessNo, String keyOut, String nextKeyIn, SwfLog currSwfLog) throws Exception {
		if (DataUtils.emptyToNull(nodeStatus) == null) {
			nodeStatus = request.getParameter("buttonSaveType");
		}
		if ("5".equals(nodeStatus)) {
			return this.getJbpmWorkFlowBack(request, currSwfLog);
		}
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(true);// 启用新工作流引擎处理
		workFlowDto.setSubmit(submit);
		workFlowDto.setUpdate(update);
		Map<String, Object> paramMap = workFlowDto.getParamMap();
		paramMap.put("nodeStatus", nodeStatus);
		if ("4".equals(nodeStatus)) {
			workFlowDto.setSubmit(true);
		}
		if (DataUtils.emptyToNull(businessNo) != null) {
			paramMap.put("businessNo", businessNo);
		}
		if (DataUtils.emptyToNull(keyOut) != null) {
			paramMap.put("keyOut", keyOut);
		}
		if (workFlowDto.getSubmit()) {
			if (DataUtils.emptyToNull(nextBusinessNo) != null) {
				paramMap.put("nextBusinessNo", nextBusinessNo);
			}
			paramMap.put("nextKeyIn", nextKeyIn);
		}
		if (currSwfLog == null) {// 当前currSwfLog为空，则从request取，两者不能同时为null
			String flowID = request.getParameter("swfLogFlowID");
			String logNo = request.getParameter("swfLogLogNo");
			if (DataUtils.emptyToNull(flowID) != null && DataUtils.emptyToNull(logNo) != null) {
				currSwfLog = new SwfLog();
				currSwfLog.getId().setFlowID(flowID);
				currSwfLog.getId().setLogNo(Integer.parseInt(logNo));
			}
		}
		workFlowDto.setCurrSwfLog(currSwfLog);
		this.check(workFlowDto);
		if (workFlowDto.getSubmit()) {
			JbpmDto jbpmDto = new JbpmDto();// 只有需要工作流流转时，才能
			jbpmDto.putParamsMap("status", nodeStatus);
			this.setScheduleParam(request, jbpmDto);// 调度节点运行参数预设
			this.setCertainLossParam(request, jbpmDto);// 设置定损，人伤，财产参数
			if (currSwfLog != null) {
				this.setVericParam(currSwfLog, jbpmDto);// 核赔节点参数预设
			}
			workFlowDto.setJbpmDto(jbpmDto);
		}
		return workFlowDto;
	}
	
	/***
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 工作流运行数据整理(Action请求)
	 * @param update
	 * @param submit
	 * @param nodeStatus
	 * @param businessNo
	 * @param nextBusinessNo
	 * @param keyOut
	 * @param nextKeyIn
	 * @param flowID
	 * @param logNo
	 * @return
	 * @throws Exception
	 */
	public WorkFlowDto getJbpmWorkFlowDto4Ws(HttpServletRequest request, boolean update, boolean submit, String nodeStatus, String businessNo, String nextBusinessNo, String keyOut, String nextKeyIn, SwfLog currSwfLog) throws Exception {
		if (DataUtils.emptyToNull(nodeStatus) == null) {
			nodeStatus = (String)request.getAttribute("buttonSaveType");
		}
		if ("5".equals(nodeStatus)) {
			return this.getJbpmWorkFlowBack(request, currSwfLog);
		}
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(true);// 启用新工作流引擎处理
		workFlowDto.setSubmit(submit);
		workFlowDto.setUpdate(update);
		Map<String, Object> paramMap = workFlowDto.getParamMap();
		paramMap.put("nodeStatus", nodeStatus);
		if ("4".equals(nodeStatus)) {
			workFlowDto.setSubmit(true);
		}
		if (DataUtils.emptyToNull(businessNo) != null) {
			paramMap.put("businessNo", businessNo);
		}
		if (DataUtils.emptyToNull(keyOut) != null) {
			paramMap.put("keyOut", keyOut);
		}
		if (workFlowDto.getSubmit()) {
			if (DataUtils.emptyToNull(nextBusinessNo) != null) {
				paramMap.put("nextBusinessNo", nextBusinessNo);
			}
			paramMap.put("nextKeyIn", nextKeyIn);
		}
		if (currSwfLog == null) {// 当前currSwfLog为空，则从request取，两者不能同时为null
			String flowID = (String)request.getAttribute("swfLogFlowID");
			String logNo = (String)request.getAttribute("swfLogLogNo");
			if (DataUtils.emptyToNull(flowID) != null && DataUtils.emptyToNull(logNo) != null) {
				currSwfLog = new SwfLog();
				currSwfLog.getId().setFlowID(flowID);
				currSwfLog.getId().setLogNo(Integer.parseInt(logNo));
			}
		}
		workFlowDto.setCurrSwfLog(currSwfLog);
		this.check(workFlowDto);
		if (workFlowDto.getSubmit()) {
			JbpmDto jbpmDto = new JbpmDto();// 只有需要工作流流转时，才能
			jbpmDto.putParamsMap("status", nodeStatus);
			this.setScheduleParam(request, jbpmDto);// 调度节点运行参数预设
			this.setCertainLossParam(request, jbpmDto);// 设置定损，人伤，财产参数
			if (currSwfLog != null) {
				this.setVericParam(currSwfLog, jbpmDto);// 核赔节点参数预设
			}
			workFlowDto.setJbpmDto(jbpmDto);
		}
		return workFlowDto;
	}

	/***
	 * 结案参数判断
	 * @param swfLog
	 * @param jbpmDto
	 * @throws Exception
	 */
	private void setVericParam(SwfLog swfLog, JbpmDto jbpmDto) throws Exception {
		if (DataUtils.emptyToNull(swfLog.getPolicyNo()) != null) {
			String endcaSql = "flowId='" + swfLog.getId().getFlowID() + "' and policyNo = '" + swfLog.getPolicyNo() + "' and nodeType='endca' and nodestatus <4 ";
			List<SwfLog> endcaList = this.getSwfLogService().findSwfLog(QueryRule.getInstance().addSql(endcaSql));
			if (endcaList == null || endcaList.isEmpty()) {
				jbpmDto.putParamsMap("endcaFlag", true);// 该立案无结案节点，则生产结案任务标志
			}
		}
	}

	private void check(WorkFlowDto workFlowDto) throws Exception {
		if (!workFlowDto.getCreate() && ( workFlowDto.getSubmit() || workFlowDto.getBack() )) {
			SwfLog currSwfLog = workFlowDto.getCurrSwfLog();
			if (currSwfLog != null) {
				String flowID = currSwfLog.getId().getFlowID();
				Integer logNo = currSwfLog.getId().getLogNo();
				SwfLog swfLog = this.workFlowService.findByPrimaryKey(flowID, logNo);
				if (swfLog != null) {
					String nodeStatus = swfLog.getNodeStatus();
					if ("4".equals(nodeStatus)) {
						throw new ProcessTokenException("該任務已處理流转！");
					} else if ("5".equals(nodeStatus)) {
						throw new ProcessTokenException("該任務已退回！");
					} else if ("6".equals(nodeStatus)) {
						throw new ProcessTokenException("該任務已註銷！");
					} else {
						if ("compe".equals(swfLog.getNodeType())) {// 理算节点是否已处理，特殊判断
							// 有无正在处理的计算书
							String statements = "select 0 from prplcompensate where claimNo = '" + swfLog.getBusinessNo() + "' and (compensateno like 'C%' or compensateno like 'D%') " + "  and underWriteFlag not in ('1','3') ";
							List<?> tepmList = this.commonService.findByStatements(statements);
							if (tepmList != null && !tepmList.isEmpty()) {
								throw new ProcessTokenException("該任務已處理流转！");
							}
							statements = "select 0 from SwfLog s where BusinessNo = '" + swfLog.getBusinessNo() + "' and nodeType='cance' and nodestatus = '0' ";
							tepmList = this.commonService.findByStatements(statements);
							if (tepmList != null && !tepmList.isEmpty()) {
								throw new ProcessTokenException("該任務已申請註銷/拒賠！");
							}
						}
					}
				}
			}
		}
	}

	/***
	 * 调度参数设置
	 * @param request
	 * @param jbpmDto
	 */
	private void setScheduleParam(HttpServletRequest request, JbpmDto jbpmDto) {
		if (request != null) {
			String checkSelectSend = request.getParameter("checkSelectSend");
			String oldcheckFlag = request.getParameter("prpLscheduleMainWFScheduleFlag");
			String[] strNextNode = request.getParameterValues("nextNodeNo"); // 指定下一个节点名
			String[] selectSend = request.getParameterValues("prpLscheduleItemSelectSend");// 定损调度选择
			String[] surveyTimes = request.getParameterValues("prpLscheduleItemSurveyTimes");// 是否为已经调度过的
			String[] prpLscheduleItemItemNo = request.getParameterValues("prpLscheduleItemItemNo");// 调度标底号
			if ("0".equals(oldcheckFlag) && "1".equals(checkSelectSend)) {
				jbpmDto.putParamsMap("checkFlag", true);
			}
			String maxrow = request.getParameter("maxrow");
			if (DataUtils.emptyToNull(maxrow) != null) {
				int maxRow = Integer.parseInt(maxrow);
				for (int index = 0; index < maxRow; index++) {
					if ("1".equals(selectSend[index]) && "0".equals(surveyTimes[index])) {
						jbpmDto.addCertainLossNodeMap(strNextNode[index], prpLscheduleItemItemNo[index]);
					}
				}
			}
		}
	}

	/***
	 * 退回定损参数预设
	 * @param request
	 * @param workFlowDto
	 * @param swfLog 当前处理的任务节点
	 * @throws Exception
	 */
	private void setBackCertaParam(HttpServletRequest request, WorkFlowDto workFlowDto, SwfLog swfLog) throws Exception {
		JbpmDto jbpmDto = workFlowDto.getJbpmDto();
		String lossItemCode[] = request.getParameterValues("lossitemCode"); // lossitemCode
		String nodeType[] = request.getParameterValues("nodeType"); // nodeType
		String checked[] = request.getParameterValues("selectCerta"); // lossitemCode
		String conditions = "";
		List<SwfLog> tempList = null;
		SwfLog tempSwfLog = null;
		if (checked != null) {
			for (int i = 1; i < checked.length; i++) {
				if ("1".equals(checked[i])) {
					conditions = " flowId='" + swfLog.getId().getFlowID() + "' and nodeType='" + DataUtils.nullToEmpty(nodeType[i]) + "' and lossItemCode='" + DataUtils.nullToEmpty(lossItemCode[i]) + "' order by logNo desc";
					tempList = this.getWorkFlowService().findNodesByConditions(conditions);
					if (tempList != null && !tempList.isEmpty()) {
						tempSwfLog = tempList.get(0);
						workFlowDto.getBackSwfLogList().add(tempSwfLog);// 要退回的节点任务
						jbpmDto.addCertainLossNodeMap(tempSwfLog.getNodeType(), lossItemCode[i]);
					}
				}
			}
		}
	}

	/**
	 * 设置定损，人伤，财产的参数
	 * @param request
	 * @param jbpmDto
	 */
	private void setCertainLossParam(HttpServletRequest request, JbpmDto jbpmDto) {
		if (request != null) {
			String nodeType = request.getParameter("nodeType");
			String lossItemCode = request.getParameter("prpLverifyLossLossItemCode");
			if (nodeType != null && lossItemCode != null) {
				jbpmDto.addCertainLossNodeMap(nodeType, lossItemCode);
			}
		}
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowEngineService getWorkFlowEngineService() {
		return workFlowEngineService;
	}

	public void setWorkFlowEngineService(WorkFlowEngineService workFlowEngineService) {
		this.workFlowEngineService = workFlowEngineService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

}
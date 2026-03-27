package com.sinosoft.claim.workflow.util;

import ins.framework.utils.DataUtils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

public class BusinessViewHelper {
	private WorkFlowService workFlowService;
	private SwfLogService swfLogService;
	private CommonService commonService;

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
	public WorkFlowDto getWorkFlowDto(HttpServletRequest request, boolean update, boolean submit, String nodeStatus, String businessNo, String nextBusinessNo, String keyOut, String nextKeyIn, SwfLog currSwfLog) throws Exception {
		if (DataUtils.emptyToNull(nodeStatus) == null) {
			nodeStatus = request.getParameter("buttonSaveType");
		}
		if ("5".equals(nodeStatus)) {
			return this.getWorkFlowBack(request, currSwfLog);
		}
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(false);// 启用新工作流引擎处理
		workFlowDto.setSubmit(submit);
		workFlowDto.setUpdate(update);
		workFlowDto.getFlowParamMap().put("status", nodeStatus);
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
		workFlowDto.setStatus(nodeStatus);
		this.check(workFlowDto);
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
	public WorkFlowDto getWorkFlowDto4Ws(HttpServletRequest request, boolean update, boolean submit, String nodeStatus, String businessNo, String nextBusinessNo, String keyOut, String nextKeyIn, SwfLog currSwfLog) throws Exception {
		if (DataUtils.emptyToNull(nodeStatus) == null) {
			nodeStatus = (String)request.getAttribute("buttonSaveType");
		}
		if ("5".equals(nodeStatus)) {
			return this.getWorkFlowBack4Ws(request, currSwfLog);
		}
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(false);// 启用新工作流引擎处理
		workFlowDto.setSubmit(submit);
		workFlowDto.setUpdate(update);
		workFlowDto.getFlowParamMap().put("status", nodeStatus);
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
		workFlowDto.setStatus(nodeStatus);
		this.check(workFlowDto);
		return workFlowDto;
	}

	/***
	 * 回退任务数据组织入口
	 * @param request
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	public WorkFlowDto getWorkFlowBack(HttpServletRequest request, SwfLog swfLog) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(false);// 启用新工作流引擎处理
		workFlowDto.setBack(true);
		workFlowDto.setUpdate(true);
		workFlowDto.setStatus("5");
		workFlowDto.getFlowParamMap().put("status", "5");
		if (swfLog == null) {
			String flowID = request.getParameter("swfLogFlowID");
			String logNo = request.getParameter("swfLogLogNo");
			swfLog = this.swfLogService.findSwfLog(flowID, Integer.parseInt(logNo));
		}
		workFlowDto.setCurrSwfLog(swfLog);
		if (request != null) {
			this.setBackCertaParam(request, workFlowDto, swfLog);
		}
		this.check(workFlowDto);
		return workFlowDto;
	}
	
	/***
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 回退任务数据组织入口
	 * @param request
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	public WorkFlowDto getWorkFlowBack4Ws(HttpServletRequest request, SwfLog swfLog) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(false);// 启用新工作流引擎处理
		workFlowDto.setBack(true);
		workFlowDto.setUpdate(true);
		workFlowDto.setStatus("5");
		workFlowDto.getFlowParamMap().put("status", "5");
		if (swfLog == null) {
			String flowID = (String)request.getAttribute("swfLogFlowID");
			String logNo = (String)request.getAttribute("swfLogLogNo");
			swfLog = this.swfLogService.findSwfLog(flowID, Integer.parseInt(logNo));
		}
		workFlowDto.setCurrSwfLog(swfLog);
		if (request != null) {
			this.setBackCertaParam4Ws(request, workFlowDto, swfLog);
		}
		this.check(workFlowDto);
		return workFlowDto;
	}
	
	/**
	 * 處理重開賠案任務
	 * @param user 當前處理人員
	 * @param endcaSwfLog 重開賠案的最近一次結案任務節點
	 * @param nextBusinessNo 新生理算節點的業務號碼
	 * @param nextKeyIn
	 * @return
	 */
	public WorkFlowDto getWorkFlowReCase(UserDto user, SwfLog endcaSwfLog, String nextBusinessNo, String nextKeyIn) {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(false);// 启用新工作流引擎处理
		workFlowDto.setReOpen(true);
		endcaSwfLog.setNextKeyIn(nextKeyIn);
		endcaSwfLog.setNextBusinessNo(nextBusinessNo);
		workFlowDto.setUpdateSwfLog(endcaSwfLog);
		workFlowDto.getFlowParamMap().put("reOpen", true);
		return workFlowDto;
	}

	private void check(WorkFlowDto workFlowDto) throws Exception {
		if (!workFlowDto.getCreate() && (workFlowDto.getSubmit() || workFlowDto.getBack())) {
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
	 * 退回定损参数预设
	 * @param request
	 * @param workFlowDto
	 * @param swfLog 当前处理的任务节点
	 * @throws Exception
	 */
	private void setBackCertaParam(HttpServletRequest request, WorkFlowDto workFlowDto, SwfLog swfLog) throws Exception {
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
					}
				}
			}
		}
	}
	
	/***
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * 退回定损参数预设
	 * @param request
	 * @param workFlowDto
	 * @param swfLog 当前处理的任务节点
	 * @throws Exception
	 */
	private void setBackCertaParam4Ws(HttpServletRequest request, WorkFlowDto workFlowDto, SwfLog swfLog) throws Exception {
		String lossItemCode[] = (String[])request.getAttribute("lossitemCode"); // lossitemCode
		String nodeType[] = (String[])request.getAttribute("nodeType"); // nodeType
		String checked[] = (String[])request.getAttribute("selectCerta"); // lossitemCode
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
					}
				}
			}
		}
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

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	} 
}

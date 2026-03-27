package com.sinosoft.claim.audit.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.audit.service.facade.AuditProcessService;
import com.sinosoft.claim.schema.model.SwfConfig;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

public class AuditProcessAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private AuditProcessService auditProcessService;
	private SwfLogService swfLogService;
	private WorkFlowService workFlowService;
	private String auditType;
	private String editType;
	private String businessNo;
	/***
	 * 提交上級，獲取後續上級節點
	 * @return
	 * @throws Exception
	 */
	public String submitSuperior() throws Exception{
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		String flowID = request.getParameter("swfLogFlowID");
		int logNo = Integer.parseInt(request.getParameter("swfLogLogNo"));
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("submit", true);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("paramsMap", paramsMap);
		List<SwfConfig> nextNodeList = this.getAuditProcessService().getNextNode(flowID, logNo,condition);
		request.setAttribute("nextNodeList", nextNodeList);
		return SUCCESS;
	}

	/***
	 * 下發修改，獲取可以回退的節點
	 * @return
	 * @throws Exception 
	 */
	public String submitJunior() throws Exception{
		this.clearErrorsAndMessages();
		HttpServletRequest request = super.getRequest();
		String flowID = request.getParameter("swfLogFlowID");
		int logNo = Integer.parseInt(request.getParameter("swfLogLogNo"));
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("back", true);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("paramsMap", paramsMap);
		List<SwfConfig> nextNodeList = this.getAuditProcessService().getNextNode(flowID, logNo,condition);
		request.setAttribute("nextNodeList", nextNodeList);
		return SUCCESS;
	}
	
	
	/***
	 * 審核訊息處理
	 * @return
	 * @throws Exception 
	 */
	public String submitTask() throws Exception{
		this.clearErrorsAndMessages();
		try {
			if ("Replevy".equals(auditType)) {//追償審批
				this.processReplevy();
			}
		} catch (Exception e) {
			this.clearMessages();
			super.addActionMessage("業務號碼：" + businessNo);
			super.addActionMessage("審批處理異常："+e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}


	/***
	 * 追償審批處理
	 * @throws Exception
	 */
	private void processReplevy() throws Exception {
		WorkFlowDto workFlowDto = this.getWorkFlowDto();
		Map<String, Object> paramsMap = workFlowDto.getJbpmDto().getParamsMap();
		if("superior".equals(editType)){//提交上級審批
			workFlowDto.setSubmit(true);
			paramsMap.put("submit", true);
			super.addActionMessage("追償處理提交上級成功！");
			super.addActionMessage("業務號碼：" + businessNo);
		}else if("junior".equals(editType)){//駁回下級修改
			workFlowDto.setBack(true);
			paramsMap.put("back", true);
			super.addActionMessage("追償處理審批下發成功！");
			super.addActionMessage("業務號碼：" + businessNo);
		}
		this.getAuditProcessService().processWorkFlow(auditType,businessNo,workFlowDto);
	}
	/***
	 * 組織當前節點審批工作流訊息
	 * @return
	 */
	private WorkFlowDto getWorkFlowDto(){
		HttpServletRequest request = super.getRequest();
		String flowID = request.getParameter("swfLogFlowID");
		int logNo = Integer.parseInt(request.getParameter("swfLogLogNo"));
		WorkFlowDto workFlowDto = new WorkFlowDto();
		if(DataUtils.emptyToNull(flowID)!=null){
			workFlowDto.setJbpmDto(new JbpmDto());
			SwfLog currSwfLog = new SwfLog(flowID,logNo);
			SwfNotion swfNotion = new SwfNotion();
			swfNotion.getId().setFlowID(flowID);
			swfNotion.getId().setLogNo(logNo);
			swfNotion.getId().setLineNo(1);
			swfNotion.setFlag(request.getParameter("swfNotionFlag"));
			swfNotion.setHandleText(request.getParameter("swfNotionHandleText"));
			currSwfLog.getSwfNotionList().add(swfNotion);
			workFlowDto.setCurrSwfLog(currSwfLog);
			workFlowDto.setNextActorId(request.getParameter("nextActorId"));
		}
		return workFlowDto;
	}

	public AuditProcessService getAuditProcessService() {
		return auditProcessService;
	}

	public void setAuditProcessService(AuditProcessService auditProcessService) {
		this.auditProcessService = auditProcessService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	
	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
}

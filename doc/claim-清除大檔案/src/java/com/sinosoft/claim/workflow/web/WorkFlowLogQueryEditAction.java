package com.sinosoft.claim.workflow.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 分发HTTP GET 工作流状态查询
 * <p>
 * Title: 车险理赔工作流查询信息
 * </p>
 * <p>
 * Description: 车险理赔工作流信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 */
public class WorkFlowLogQueryEditAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	
	/**工作流viewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;
	
	private String nodeType = "";

	/**
	 * 查询工作流状态信息,整理输入，用於初始界面显示
	 * @return
	 * @throws Exception
	 */
	public String workFlowLogQueryEdit() throws Exception {
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String nodeType = request.getParameter("nodeType");
		String caseFlag = request.getParameter("caseFlag");
		String status = request.getParameter("status"); // 立案号
		String editType = request.getParameter("editType");
		String method = request.getParameter("method");
		request.setAttribute("method", method);
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String userCode = user.getUserCode();
		String comLevel = "";
		// 看看是否是总公司-----------------------------
		if (nodeType.equals("verip")) {
			comLevel = user.getComLevel();
			request.setAttribute("comLevel", comLevel);
		}
		// 查询工作流状态信息,整理输入，用於初始界面显示
		// 需要进行翻页处理
		// 每页显示的行数
		String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null || pageNo.trim().equals(""))
			pageNo = "1";
		try {
			if (editType != null && editType.trim().equals("specialQuery")) {
				// 特殊赔案查询
				this.getWorkFlowViewHelper().getSwfLogList(request, caseFlag, userCode, nodeType);
				return "specialQuery";
			} else if (editType != null && editType.trim().equals("welcome")) {// 首页的列表点击的时候
				this.getWorkFlowViewHelper().getWorkFlowLogList(request, nodeType, status, "", pageNo, "10000");
				return "success";
			} else if (editType != null && editType.trim().equals("urgentCase")) {// 理算环节紧急案件清单
				this.getWorkFlowViewHelper().getUrgentCaseList(request, pageNo, recordPerPage);
				return "urgentCase";
			} else if (editType != null && editType.trim().equals("undwrtUrgentCase")) {// 核赔环节紧急案件清单
				this.getWorkFlowViewHelper().getUndwrtUrgentCaseList(request, pageNo, recordPerPage);
				return "urgentCase";
			} else if (editType != null && editType.trim().equals("exportToExcel")) {// 导出理算环节紧急案件清单至Excel
				this.getWorkFlowViewHelper().exportToExcel(request, response);
				return NONE;
			} else if (editType != null && editType.trim().equals("undwrtExportToExcel")) {// 导出理算环节紧急案件清单至Excel
				this.getWorkFlowViewHelper().undwrtExportToExcel(request, response);
				return NONE;
			} else {
				//this.getWorkFlowViewHelper().getWorkFlowLogList(request, nodeType, status, "", pageNo, recordPerPage);
				this.getWorkFlowViewHelper().getWorkFlowLogList(request,Integer.parseInt(pageNo),Integer.parseInt(recordPerPage));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		if(!"".equals(DataUtils.dbNullToEmpty(nodeType))){
			StringBuffer sb = new StringBuffer(nodeType);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			this.nodeType = sb.toString(); 
		}
	}
}

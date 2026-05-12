package com.sinosoft.claim.common.web;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.TaskQueryViewHelper;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

/***
 * 查询
 * 立案作业查询、已核赔资料查询、已核赔赔付查询
 * @author 理赔组
 *
 */
public class TaskQueryAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	private TaskQueryViewHelper taskQueryViewHelper;
	private UtiCodeTransferService utiCodeTransferService;
	/***
	 * 查询入口
	 * @return
	 */
	public String taskQuery(){
		HttpServletRequest request = super.getRequest();
		String searchType = request.getParameter("searchType");
		String pageNo = request.getParameter("pageNo");
		if (DataUtils.emptyToNull(pageNo) == null) {
			pageNo = "1";
		}
		int pageSize = 20;
		try {
			if ("ClaimTask".equals(searchType)) {// 立案作业查询
				this.taskQueryViewHelper.queryClaimTask(request, searchType, Integer.parseInt(pageNo), pageSize);
			} else if ("UndwrtTaskInfo".equals(searchType)) {// 已核赔资料查询
				this.taskQueryViewHelper.queryUndwrtTaskInfo(request, searchType, Integer.parseInt(pageNo), pageSize);
			} else if ("UndwrtTaskPayInfo".equals(searchType)) {// 已核赔赔付查询
				this.taskQueryViewHelper.queryUndwrtTaskPayInfo(request, searchType, Integer.parseInt(pageNo), pageSize);
			}
			request.setAttribute("searchType", searchType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchType;
	}
	/***
	 * 查询入口
	 * @return
	 */
	public String beforeTaskQuery(){
		HttpServletRequest request = super.getRequest();
		String searchType = request.getParameter("searchType");
		try {
			Map<String,String> riskTypes = utiCodeTransferService.findRiskType();
			request.setAttribute("riskTypes", riskTypes);
			request.setAttribute("searchType", searchType);
			if("ClaimTask".equals(searchType)){
				Calendar date = Calendar.getInstance();
				date.add(Calendar.MONTH,-3);//事故日期控制在3个月内的
				request.setAttribute("StartDate", date.getTime());
				request.setAttribute("EndDate", Calendar.getInstance().getTime());
			}else{
				Calendar start = Calendar.getInstance();
				start.add(Calendar.MONTH, -1);
				request.setAttribute("UnderWriteDateStart", start.getTime());
				request.setAttribute("UnderWriteDateEnd", Calendar.getInstance().getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchType;
	}
	/***
	 * 导出入口
	 * @return
	 */
	public String queryExport(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String searchType = request.getParameter("searchType");
		String exportType = request.getParameter("exportType");
		try {
			if ("ClaimTask".equals(searchType)) {//立案作业查询结果导出
				this.taskQueryViewHelper.queryClaimTaskExport(request, response, searchType);
			} else if ("UndwrtTaskInfo".equals(searchType)) {//已核赔资料查询结果导出
				this.taskQueryViewHelper.queryUndwrtTaskInfoExport(request, response, searchType);
			} else if ("UndwrtTaskPayInfo".equals(searchType)) {//已核赔赔付查询导出
				this.taskQueryViewHelper.queryUndwrtTaskPayInfoExport(request, response, searchType);
			} else if ("SendToTiiQuery".equals(searchType)){//add by songxin 需求169報送保發部分 20170510
				if("detail".equals(exportType)){
					this.taskQueryViewHelper.queryExportTiiDetail(request, response, searchType);
				}else if("collect".equals(exportType)){
					this.taskQueryViewHelper.queryExportTiiCollect(request, response, searchType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public TaskQueryViewHelper getTaskQueryViewHelper() {
		return taskQueryViewHelper;
	}
	public void setTaskQueryViewHelper(TaskQueryViewHelper taskQueryViewHelper) {
		this.taskQueryViewHelper = taskQueryViewHelper;
	}
	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}
	public void setUtiCodeTransferService(
			UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}
	
}

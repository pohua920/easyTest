package com.sinosoft.claim.undwrt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sinosoft.claim.undwrt.util.UndwrtTaskDealViewHelper;
import com.sinosoft.sysframework.exceptionlog.UserException;

import ins.framework.common.DateTime;
import ins.framework.web.Struts2Action;

/**
 * 處理核賠查詢
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
@SuppressWarnings("serial")
public class UndwrtTaskDealAction extends Struts2Action {

	/**核赔任务viewHelper*/
	private UndwrtTaskDealViewHelper undwrtTaskDealViewHelper;
	/**action類型*/
	private String actionType;
	/** 查詢類型*/
	private String EditType;
	/** 流入時間1*/
	private String flowInTime1;
	/** 流入時間2*/
	private String flowInTime2;
	/** 當前時間*/
	private int nowYear = 0;

	/**
	 * 查询核赔任务
	 * @return
	 * @throws Exception
	 */
	public String undwrtTaskDeal() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		String forward = null;
		try {
			if (actionType.equals("prepareQuery")) {
				undwrtTaskDealViewHelper.prepareQuery(request, response);
				forward = actionType;
			} else if (actionType.equals("query")) {
				undwrtTaskDealViewHelper.query(request, response);
				forward = actionType;
			} else if (actionType.equals("queryContinue")) {
				undwrtTaskDealViewHelper.queryContinue(request, response);
				forward = "hepeiQueryContinue";
			} else if (actionType.equals("prepareBatchSubmitSuperior")) {
				if (undwrtTaskDealViewHelper.prepareBatchSubmitSuperior(request, response).equals("failure")) { // 已提交或已关闭
					forward = "failure";
					request.setAttribute("content", "該工作流已處理完畢！");
				} else {
					forward = actionType;
				}
			} else if (actionType.equals("batchSubmitSuperior")) {
				undwrtTaskDealViewHelper.batchSubmitSuperior(request, response);
				forward = actionType;
			} else {
				forward = "failure";
				request.setAttribute("content", "不明確的任務類型！");
			}
			
			DateTime dateTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY);
			nowYear = dateTime.getYear();
			if (flowInTime2 == null || "".equals(flowInTime2.trim())) {
				flowInTime2 = dateTime.toString();
			}
			if (flowInTime1 == null || "".equals(flowInTime1.trim())) {
				flowInTime1 = dateTime.addMonth(-1).toString();
			}
		} catch (UserException e) {
			request.setAttribute("content", e.getErrorMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			request.setAttribute("content", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return forward;
	}
	
	/**
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * 查询整批核赔任务
	 * @return
	 * @throws Exception
	 */
	public String undwrtHeapTaskDeal() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		String forward = null;
		try {
			if (actionType.equals("prepareQuery")) {
				undwrtTaskDealViewHelper.prepareHeapQuery(request, response);
				forward = actionType;
			} else {
				forward = "failure";
				request.setAttribute("content", "不明確的任務類型！");
			}
			
			DateTime dateTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY);
			nowYear = dateTime.getYear();
			if (flowInTime2 == null || "".equals(flowInTime2.trim())) {
				flowInTime2 = dateTime.toString();
			}
			if (flowInTime1 == null || "".equals(flowInTime1.trim())) {
				flowInTime1 = dateTime.addMonth(-1).toString();
			}
		} catch (UserException e) {
			request.setAttribute("content", e.getErrorMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			request.setAttribute("content", e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return forward;
	}

	public UndwrtTaskDealViewHelper getUndwrtTaskDealViewHelper() {
		return undwrtTaskDealViewHelper;
	}

	public void setUndwrtTaskDealViewHelper(UndwrtTaskDealViewHelper undwrtTaskDealViewHelper) {
		this.undwrtTaskDealViewHelper = undwrtTaskDealViewHelper;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getEditType() {
		return EditType;
	}

	public void setEditType(String editType) {
		this.EditType = editType;
	}

	public String getFlowInTime1() {
		return flowInTime1;
	}

	public void setFlowInTime1(String flowInTime1) {
		this.flowInTime1 = flowInTime1;
	}

	public String getFlowInTime2() {
		return flowInTime2;
	}

	public void setFlowInTime2(String flowInTime2) {
		this.flowInTime2 = flowInTime2;
	}

	public int getNowYear() {
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
	}
}

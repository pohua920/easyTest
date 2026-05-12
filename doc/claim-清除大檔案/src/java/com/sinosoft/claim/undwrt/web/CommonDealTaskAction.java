/*
 * @(#)CommonDealTaskAction.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.web;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.undwrt.util.CommonDealTaskViewHelper;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CommonDealTaskAction extends Struts2Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**核赔节点帮助类*/
	private CommonDealTaskViewHelper commonDealTaskViewHelper;

	/**
	 * 核赔任务处理
	 * @return
	 * @throws Exception
	 */
	public String commonDealTask() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		// 处理业务类型
		// 任务类型 save－保存审批任务 submit－提交审批任务
		HttpServletRequest req = this.getRequest();
		String dealType = req.getParameter("DealType");
		if (dealType.equals("save")) { // 暂存
			commonDealTaskViewHelper.saveTask(req);
			forward = "save";
			req.setAttribute("content", "任務保存成功！");
		} else if (dealType.equals("submit")) { // 提交任务
			commonDealTaskViewHelper.submitTaskBefore(req);
			String submitDirection = req.getParameter("SubmitDirection");
			// add by caozhigang 20090401 start
			// reason:保存下发修改和提交上级时的意见
			String HandleText = req.getParameter("HandleText");
			req.setAttribute("HandleText", HandleText);
			// add by caozhigang 20090401 end
			req.setAttribute("ClaimNo", req.getParameter("ClaimNo"));
			req.setAttribute("notion", req.getParameter("notion"));
			forward = submitDirection;
		}
		// add by xukefeng 2006-12-01 增加放弃任务功能
		if ("undo".equals(dealType)) {
			String flowId = req.getParameter("FlowId"); // 工作流号
			int logNo = Integer.parseInt(req.getParameter("LogNo")); // 序号
			commonDealTaskViewHelper.undoTask(flowId, logNo);
			forward = "undoTask";
			req.setAttribute("content", "放棄任務成功！");
		}
		return forward;
	}

	public CommonDealTaskViewHelper getCommonDealTaskViewHelper() {
		return commonDealTaskViewHelper;
	}

	public void setCommonDealTaskViewHelper(CommonDealTaskViewHelper commonDealTaskViewHelper) {
		this.commonDealTaskViewHelper = commonDealTaskViewHelper;
	}

}

package com.sinosoft.claim.schedule.web;

import ins.framework.web.Struts2Action;
import javax.servlet.http.*;
import com.sinosoft.claim.schedule.util.DAAScheduleViewHelper;

/**
 * 分发HTTP GET 理赔调度节点的改派任务
 * <p>
 * Title: 理赔调度节点的新的改派任务
 * </p>
 * <p>
 * Description: 理赔调度节点的改派任务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */

public class ScheduleGetBackBeforeAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DAAScheduleViewHelper daaScheduleViewHelper;

	public String scheduleGetBackBefore() throws Exception {

		/**
		 * 4。查询调度取回任务 5。查询调度撤消任务 6。查询调度的所有信息
		 **/
		HttpServletRequest httpServletRequest = getRequest();

		String forward = "";
		String registNo = "";
		// 向前
		String editType = httpServletRequest.getParameter("editType");

		// 1。调度录入的界面展现
		// 调用位置:调度任务登记 ->新案件提示任务列表中选中一个报案->显示调度任务信息
		// 8。调度信息取回详细显示
		// 调用位置:调度取回任务 ->任务列表中选中一个报案->显示保存过的调度任务信息
		httpServletRequest.setAttribute("editType", editType);
		if (editType.equals("GETBACKEDIT")) {
			registNo = (String) httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
			this.daaScheduleViewHelper.scheduleDtoToView(httpServletRequest, registNo, editType, "1");
			forward = "EDITDAA";
		}

		return forward;
	}

	public DAAScheduleViewHelper getDaaScheduleViewHelper() {
		return daaScheduleViewHelper;
	}

	public void setDaaScheduleViewHelper(DAAScheduleViewHelper daaScheduleViewHelper) {
		this.daaScheduleViewHelper = daaScheduleViewHelper;
	}

}

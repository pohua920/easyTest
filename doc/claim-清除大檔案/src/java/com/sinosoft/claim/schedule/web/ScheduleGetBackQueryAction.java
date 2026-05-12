package com.sinosoft.claim.schedule.web;

import ins.framework.common.Page;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.schedule.util.DAAScheduleViewHelper;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 定损任务注销 分发HTTP GET 理赔调度节点的新的需要调度的任务
 * <p>
 * Title: 理赔调度节点的新的需要调度的任务
 * </p>
 * <p>
 * Description: 理赔调度节点的新的需要调度的任务
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

@SuppressWarnings("serial")
public class ScheduleGetBackQueryAction extends Struts2Action {
	/** 查询调度ViewHelper */
	private DAAScheduleViewHelper daaScheduleViewHelper;

	/**
	 * 查询调度取回任务
	 **/
	public String scheduleGetBackQuery() throws Exception {

		HttpServletRequest httpServletRequest = getRequest();
		String forward = ""; // 向前
		String editType = httpServletRequest.getParameter("editType");
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 4。查询调度取回任务
		// 调用位置:调度任务处理->调度改派
		if (editType.equals("GETBACKQUERY")) {
			logger.debug("查询满足条件的 公告信息");
			Page page = null;
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			try {
				page = this.daaScheduleViewHelper.getNextBackTaskList(getRequest(), pageNo, pageSize);
				this.writeJSONData(page, "keyIn", "nodeType", "lossItemCode", "id", "policyNo", "handleDept", "riskCode", "flowInTime", "beforeHandlerName", "handlerName", "lossItemName");

			} catch (Exception ex) {
				ex.printStackTrace();
				this.writeJSONMsg(ex.getMessage());
			}
			return NONE;
		}
		// 调用位置:调度任务处理->定损任务注销
		if (editType.equals("CANCELBEFOREQUERY")) {
			// 说明:能够取回的任务必须是还没有被查勘处理过的调度任务
			logger.debug("查询满足条件的 公告信息");
			Page page = null;
			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			try {
				page = this.daaScheduleViewHelper.getCancelBeforeList(getRequest(), pageNo, pageSize);
				this.writeJSONData(page, "nodeType", "lossItemName", "beforeHandlerName", "handlerName", "flowInTime", "keyIn", "id", "policyNo","actorId");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.writeJSONMsg(ex.getMessage());
			}
			return NONE;

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

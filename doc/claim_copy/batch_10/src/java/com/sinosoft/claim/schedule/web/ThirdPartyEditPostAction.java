package com.sinosoft.claim.schedule.web;

/**
 * 分发HTTP GET 理赔调度节点的新的需要调度的任务
 * <p>
 * Title: 理赔调度节点的新的需要调度的任务
 * </p>
 * <p>
 * Description:三者车处理任务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0u
 *//**
 * 
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sinosoft.claim.check.service.facade.CheckService;
import com.sinosoft.claim.check.vo.CheckDto;
import com.sinosoft.claim.schedule.util.ThirdPartyViewHelper;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;

import ins.framework.web.Struts2Action;

public class ThirdPartyEditPostAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**定损调度ViewHelper*/
	private ThirdPartyViewHelper thirdPartyViewHelper;
	/**工作流处理service*/
	private WorkFlowService workFlowService;

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private CheckService checkService;

	public CheckService getCheckService() {
		return checkService;
	}

	public void setCheckService(CheckService checkService) {
		this.checkService = checkService;
	}

	public ThirdPartyViewHelper getThirdPartyViewHelper() {
		return thirdPartyViewHelper;
	}

	public void setThirdPartyViewHelper(ThirdPartyViewHelper thirdPartyViewHelper) {
		this.thirdPartyViewHelper = thirdPartyViewHelper;
	}

	/**
	 * 提交三者车信息
	 * @return 页面类型
	 * @throws Exception
	 */
	public String thirdPartyEditPost() throws Exception {
		String forward = ""; // 向前流转
		forward = "success";
		HttpServletRequest httpServletRequest = getRequest();
		HttpServletResponse httpServletResponse = getResponse();
		String checkNo = ""; // 查勘号
		checkNo = httpServletRequest.getParameter("businessNo");
		httpServletRequest.setAttribute("registNo", checkNo);
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		CheckDto checkDto = thirdPartyViewHelper.viewToDto(httpServletRequest);
		 // 有新增加的标的信息
		if (checkDto.getNewScheduleItem()){
			// 需要更新工作流的状态等等信息
			int logNo = 0;
			logNo = Integer.parseInt(DataUtils.nullToZero(swfLogLogNo));
			WorkFlowDto workFlowDto = new WorkFlowDto();
			SwfLog swfLogDto = this.workFlowService.findNodeByPrimaryKey(swfLogFlowID, logNo);
			if (swfLogDto != null) {
				// 更新调度
				// modify by lixiang start at 2007-7-25
				// reasion:因为考虑到调度任务可能被别人所占用或者已经变成了而且还要考虑占号的原因，所以这里把案件变成未占号的状态，並且没有被处理过。
				swfLogDto.setNodeStatus("0");
				swfLogDto.setFlowStatus("1");
				swfLogDto.setHandlerCode("0");
				swfLogDto.setHandlerName("");
				// modify by lixiang end at 2007-7-25

				workFlowDto.setUpdate(true);
				workFlowDto.setUpdateSwfLog2(swfLogDto);

				if (workFlowDto.getSubmit()){
					workFlowDto.setSubmit(false);
				}
				this.checkService.saveScheduleAddCertainLoss(checkDto, workFlowDto);

				String commiFlag = httpServletRequest.getParameter("commiFlag"); // add
																					// by
																					// liyanjie
																					// 2005-12-17
																					// start双代标志
				String scheduleRef = httpServletRequest.getContextPath()+"/schedule/scheduleDealRegist.do?prpLscheduleMainWFRegistNo=" + checkNo + "&prpLscheduleMainWFScheduleID=1" + "&status=2" + "&scheduleType=sched" + "&editType=ADD" + "&swfLogFlowID=" + swfLogFlowID
						+ "&swfLogLogNo=" + swfLogLogNo + "&nodeType=sched" + "&riskCode=DAA" + "&handleDept" + swfLogDto.getHandleDept() + "&endflag=" + swfLogDto.getEndFlag() + "&commiFlag=" + commiFlag; // add
				httpServletResponse.sendRedirect(scheduleRef);
			}
		} else {
			this.checkService.saveScheduleAddCertainLoss(checkDto, null);
			this.clearMessages();
			this.addActionMessage(super.getText("prompt.schedule.save"));
			forward = "success";

		}

		return forward;
	}
}

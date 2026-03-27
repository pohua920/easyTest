package com.sinosoft.claim.schedule.web;

import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.schedule.util.ThirdPartyViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP GET 车险理赔新增定损调度前查询保单请求
 * <p>
 * Title: 车险理赔新增定损调度前查询保单信息
 * </p>
 * <p>
 * Description: 车险理赔新增定损调度前查询保单信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */

public class ThirdPartyBeforeEditAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/**定损调度ViewHelper*/
	private ThirdPartyViewHelper thirdPartyViewHelper;
	/**工作流处理service*/
	private WorkFlowService workFlowService;

	public ThirdPartyViewHelper getThirdPartyViewHelper() {
		return thirdPartyViewHelper;
	}

	public void setThirdPartyViewHelper(ThirdPartyViewHelper thirdPartyViewHelper) {
		this.thirdPartyViewHelper = thirdPartyViewHelper;
	}

	private CodeService codeService;

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	/**
	 * 查询三者车任务
	 * @return
	 * @throws Exception
	 */
	public String thirdPartyBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示

		String editType = httpServletRequest.getParameter("editType");
		String businessNo = "";
		String forward = ""; // 向前
		if (editType.equals("ADDSHOW")) {
			businessNo = httpServletRequest.getParameter("businessNo");
			String strRiskCode = this.codeService.translateProductCode("RISKCODE_DAA");
			// 检查是不是可以进行增加定损调度的？？？ 目前只检查调度状态为4的，是不是单正也做完了？？
			String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
			String nodeStatus = httpServletRequest.getParameter("nodeStatus"); // 工作流状态
			if (nodeStatus.equals("4")) { // 需要检查单compe??是否存在？
				String condition = "flowid='" + swfLogFlowID + "' AND nodeType='compe' and nodeStatus<4";
				int retCount = this.getWorkFlowService().findFlowNodeCountByConditon(condition);
				if (retCount > 0) {
					String msg = "案件'" + businessNo + "'已經處理到理算環節，不能進行定損調度標的的增加,請選擇其它案件進行處理!";
					throw new UserException(1, 3, "工作流", msg);
				}
			}
			thirdPartyViewHelper.registDtoToView(httpServletRequest, businessNo, strRiskCode);
			forward = "ADDSHOW";
		}
		return forward;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}

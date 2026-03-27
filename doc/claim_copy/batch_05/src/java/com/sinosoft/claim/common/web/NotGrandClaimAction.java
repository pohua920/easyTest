package com.sinosoft.claim.common.web;

import javax.servlet.http.HttpServletRequest;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;

import ins.framework.web.Struts2Action;

/**
 * @ClassName NotGrandClaimAction
 * @Description 意健险不予立案处理界面信息
 * @author 中科软
 */
@SuppressWarnings("serial")
public class NotGrandClaimAction extends Struts2Action {
	/** 报案处理接口 */
	private RegistService registService;
	/** 车险报案数据收集工具类 */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 工作流数据收集工具类 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 工作流处理接口 */
	private WorkFlowService workFlowService;

	public String execute() throws Exception {
		/*
		 * 程序思路： ========================================================
		 * [1]根据报案号registno取得报案信息 [2]回写报案信息 [3]保存报案表信息
		 * [4]如是提交，执行Complate操作,並且关闭工作流endflag=1。
		 * ========================================================
		 */
		HttpServletRequest request = super.getRequest();
		// 取报案号
		String registNo = request.getParameter("prpLclaimRegistNo");
		String riskCode = request.getParameter("riskCode");
		String policyNo = request.getParameter("prpLclaimPolicyNo");
		String swfLogFlowID = request.getParameter("swfLogFlowID");
		String swfLogLogNo = request.getParameter("swfLogLogNo");
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String comCode = user.getComCode();
		// 用viewHelper整理界面输入
		RegistDto registDto = this.daaRegistViewHelper.getNotGrandClaim(request);
		// 工作流的viewHelper整理界面输入
		SwfLog swfLogDtoDealNode = new SwfLog();
		// 设置报案传入工作流的各个状态
		swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
		swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		swfLogDtoDealNode.setNodeType("regis");
		swfLogDtoDealNode.setNodeStatus("1");
		swfLogDtoDealNode.setBusinessNo(registNo);
		swfLogDtoDealNode.setNextBusinessNo(registNo);
		swfLogDtoDealNode.setKeyIn(registNo);
		swfLogDtoDealNode.setKeyOut(registNo);
		swfLogDtoDealNode.setRiskCode(riskCode);
		swfLogDtoDealNode.setComCode(comCode);
		swfLogDtoDealNode.setPolicyNo(policyNo);
		swfLogDtoDealNode.setEndFlag("1"); // 关闭工作流
		// modify by wangli add start 200504017
		// modify by wangli add end 200504017

		// 整理工作流的创建/修改/提交的数据
		WorkFlowDto workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
		if (workFlowViewHelper.checkDealDto(workFlowDto)) {
			this.registService.save(registDto, workFlowDto);
			user.setUserMessage(registNo);
		} else {
			if (workFlowDto.getOperateResult() < 0) {
				user.setUserMessage("注意:創建工作流流程時，未找到相關工作流模板的設定，請聯系系統管理員進行相應配置！！");
			} else {
				this.registService.save(registDto);
				user.setUserMessage(registNo + ";注意:沒有發現與工作流流程相關任何數據！！");
			}
		}
		/**
		 * [reason:目前不予立案的案件在已处理立案任务中可以找到,按照和刘总沟通的结果，将报案以後各个环节，没有处理完毕的工作流节点全部删除]
		 * [********************************************************************
		 * ************************************]
		 * [*******************************
		 * *对工作流节点进行删除操作,请慎重修改*******************************************]
		 * [*****
		 * ****************************************************************
		 * ***********************************]
		 * [*执行删除操作的条件，工作流号长度判断,必须进行慎重处理，节点状态严格限制;
		 */
		// 对工作流号进行限制
		String conditions = "";
		if (swfLogFlowID.length() == 21) {
			conditions = conditions + " nodestatus <> 4 and flowid ='" + swfLogFlowID + "'  ";
			this.getWorkFlowService().deletWorkFlowForNotGrand(conditions);
		}
		this.clearErrorsAndMessages();
		this.addActionMessage(this.getText("prompt.regist.notGrandClaim"));
		this.addActionMessage(this.getText("db.prpLregist.registNo"));
		return SUCCESS;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}

/*
 * @(#)SpeicalCaseEditPostAction.java	Mar 4, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.specailCase.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLSpecialCaseReason;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLSpecialCaseReasonService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.specailCase.util.SpecialCaseViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class SpeicalCaseEditPostAction extends Struts2Action {
	/**
	 * 序列号ID号
	 */
	private static final long serialVersionUID = 1L;
	private SpecialCaseViewHelper specialCaseViewHelper;
	private ClaimService claimService;
	private PrpLSpecialCaseReasonService prpLSpecialCaseReasonService;
	private PrpLrecaseService prpLrecaseService;
	private WorkFlowService workFlowService;
	private WorkFlowViewHelper workFlowViewHelper;
	
	/**
	 * 特殊赔案处理暂存、提交
	 * @return
	 * @throws Exception
	 */
	public String speicalCaseEditPost() throws Exception {
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpServletRequest httpServletRequest = this.getRequest();
		String claimNo = httpServletRequest.getParameter("prpLclaimNo");
		String nodeStatus = httpServletRequest.getParameter("nodeStatus"); // 原来节点的状态
		String forward = "";// 向前
		// 取用户信息
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		SwfLog swfLogDtoTemp = specialCaseViewHelper.viewToDto(httpServletRequest);
		/**
		 * for (int i = 1; i < claimNoArray.length; i++) { claimNo =
		 * claimNoArray[i]; }
		 */
		ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
		// 以下保存信息不明确
		SwfLog swfLogDtoDealNode = new SwfLog();
		swfLogDtoDealNode.getId().setFlowID(swfLogDtoTemp.getId().getFlowID());
		swfLogDtoDealNode.getId().setLogNo(swfLogDtoTemp.getId().getLogNo());
		swfLogDtoDealNode.setNodeStatus("4");
		swfLogDtoDealNode.setKeyIn(claimNo);
		swfLogDtoDealNode.setKeyOut(claimNo);
		swfLogDtoDealNode.setNextBusinessNo(claimNo);
		swfLogDtoDealNode.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());
		// 指定下个节点就是特殊赔案的申请
		List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
		SwfLog swfLogNextNode = new SwfLog();
		swfLogNextNode.setNodeNo(0);
		swfLogNextNode.setNodeType("speci");
		swfLogNextNode.setTypeFlag(swfLogDtoTemp.getTypeFlag()); // 区分赔案类型的
		swfLogNextNode.setPolicyNo(claimDto.getPrpLclaim().getPolicyNo());

		nextNodeList.add(swfLogNextNode);

		swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定下一个节点的序列，如果不是，就是从模板上寻找下面的节点
		swfLogDtoDealNode.setSwfLogList(nextNodeList);
		WorkFlowDto workFlowDto = this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
		workFlowDto.setSwfNotionList(swfLogDtoTemp.getSwfNotionList());
		if (workFlowDto.getUpdateSwfLog() != null) {
			workFlowDto.getUpdateSwfLog().setNodeStatus(nodeStatus);
		}
		List<SwfLog> collection = workFlowDto.getSubmitSwfLogList();
		if (collection != null && !collection.isEmpty()) {
			for (Iterator<SwfLog> i = collection.iterator(); i.hasNext();) {
				SwfLog swfLogDto = (SwfLog) i.next();
				swfLogDto.setRiskCode(claimDto.getPrpLclaim().getRiskCode());
			}
			// 因为要保持现在的节点为原来的状态，所以。。
		}
		// 保存开始
		if (this.getWorkFlowViewHelper().checkDealDto(workFlowDto)) {
			this.getWorkFlowService().deal(workFlowDto);
		}
		// add by luochang at 2010-06-07 begin 特殊赔案申请原因
		String reason = httpServletRequest.getParameter("Context");

		PrpLSpecialCaseReason prpLSpecialCaseReason = new PrpLSpecialCaseReason();
		prpLSpecialCaseReason.getId().setClaimNo(claimNo);
		SwfLog swfLogDto = new SwfLog();
		if (workFlowDto.getSubmitSwfLogList() != null && workFlowDto.getSubmitSwfLogList().size() > 0) {
			swfLogDto = workFlowDto.getSubmitSwfLogList().get(0);
		}
		prpLSpecialCaseReason.getId().setLogNo(new Long(swfLogDto.getId().getLogNo()));
		String caseType = httpServletRequest.getParameter("specialCaseCaseType");
		if ("5".equals(caseType)) {
			prpLSpecialCaseReason.setSpecialName("預賠");
		} else if ("7".equals(caseType)) {
			prpLSpecialCaseReason.setSpecialName("預-支付搶救費");
		} else if ("8".equals(caseType)) {
			prpLSpecialCaseReason.setSpecialName("墊付搶救費");
		}
		prpLSpecialCaseReason.setReason(reason);
		String businessNo = prpLrecaseService.findJbpmBusinessNo(claimNo, false);
		prpLSpecialCaseReasonService.saveBpm(businessNo, prpLSpecialCaseReason);
		// 特殊赔案申请原因
		this.clearMessages();
		this.addActionMessage(this.getText("prompt.specialcase.save"));
		this.addActionMessage(this.getText("db.prpLclaim.claimNo"));
		this.addActionMessage(claimNo);
		forward = "success";

		return forward;

	}

	public SpecialCaseViewHelper getSpecialCaseViewHelper() {
		return specialCaseViewHelper;
	}

	public void setSpecialCaseViewHelper(SpecialCaseViewHelper specialCaseViewHelper) {
		this.specialCaseViewHelper = specialCaseViewHelper;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLSpecialCaseReasonService getPrpLSpecialCaseReasonService() {
		return prpLSpecialCaseReasonService;
	}

	public void setPrpLSpecialCaseReasonService(PrpLSpecialCaseReasonService prpLSpecialCaseReasonService) {
		this.prpLSpecialCaseReasonService = prpLSpecialCaseReasonService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

}

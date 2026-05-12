package com.sinosoft.claim.check.web;


import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfPathLog;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckService;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckTextService;
import com.sinosoft.claim.specailCase.util.SpecialCaseViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP GET 车险理赔结案前查询保单请求
 * <p>
 * Title: 车险理赔结案前查询保单信息
 * </p>
 * <p>
 * Description: 车险理赔结案前查询保单信息系统样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 理赔组
 * @version 1.0
 */
public class ApplyScheduleEditPostAction extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrpLacciCheckService prpLacciCheckService;
	private SpecialCaseViewHelper specialCaseViewHelper;
	private WorkFlowViewHelper workFlowViewHelper;
	private WorkFlowService workFlowService;
	private PrpLacciCheckTextService prpLacciCheckTextService;

	public String checkAcciEdit() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = getRequest();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		// String editType = httpServletRequest.getParameter("editType");
		// String registNo = httpServletRequest.getParameter("registNo"); //赔案号
		// String riskCode = httpServletRequest.getParameter("riskCode"); //险种
		// String nodeStatus = httpServletRequest.getParameter("nodeStatus");
		// //原来节点的状态
		String nodeType = httpServletRequest.getParameter("nodeType"); // 节点类型
		String nodeName = httpServletRequest.getParameter("nodeName"); // 节点名称
		String forward = ""; // 向前
		try {
			// 取用户信息
			UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
			SwfLog swfLogTemp = specialCaseViewHelper.viewToDto(httpServletRequest);
			PrpLacciCheck prpLacciCheck = specialCaseViewHelper.viewToCheckDto(httpServletRequest);
			prpLacciCheckService.save(prpLacciCheck);
			// 以下保存信息不明确
			SwfLog swfLogDealNode = new SwfLog();
			swfLogDealNode.getId().setFlowID(swfLogTemp.getId().getFlowID());
			// 如果是理算节点，会出现出一个计算书的错误，如何绕开这个问题呢？
			// 暂时认为是如果是compe为理算上提出的，则从1号加点，然后在后续过程中给替换掉,理算要特别对待
			int intLogNo = swfLogTemp.getId().getLogNo();

			if ("compe".equals(nodeType)) {
				swfLogDealNode.getId().setLogNo(1);
			} else {
				swfLogDealNode.getId().setLogNo(intLogNo);
			}
			swfLogDealNode.setNodeStatus("4");
			// swfLogDtoDealNode.setKeyIn(prpLacciCheckDto.getCertiNo());
			// keyIn由certino改为调查号 2005-08-16
			swfLogDealNode.setKeyIn(prpLacciCheck.getCheckNo());
			swfLogDealNode.setKeyOut(prpLacciCheck.getCertiNo());
			swfLogDealNode.setNextBusinessNo(prpLacciCheck.getCertiNo());
			// 指定下个节点就是特殊赔案的申请
			List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
			SwfLog swfLogNextNode = new SwfLog();
			swfLogNextNode.setNodeNo(0);
			swfLogNextNode.setNodeType("check");
			swfLogNextNode.setTypeFlag(swfLogTemp.getTypeFlag());
			// //区分赔案类型的
			nextNodeList.add(swfLogNextNode);

			swfLogDealNode.setNextNodeListType("1");// 如果得1，就是需要指定下一个节点的序列，如果不是，就是从模板上寻找下面的节点
			swfLogDealNode.setSwfLogList(nextNodeList);
			WorkFlowDto workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDealNode);
			workFlowDto.setSwfNotionList(swfLogTemp.getSwfNotionList());
			// workFlowDto.setUpdateSwfLogDto(null);
			if (workFlowDto.getUpdateSwfLog() != null) {
				workFlowDto.setUpdateSwfLog(null);
			}

			// 因为要保持现在的节点为原来的状态，所以。。
			if ("compe".equals(nodeType)) {
				// 设置lognNo=1到调查的边为理算到调查的边
				if (workFlowDto.getSubmitSwfPathLogList() != null) {
					// 查找调查的边
					for (int i = 0; i < workFlowDto.getSubmitSwfPathLogList().size(); i++) {
						if (((SwfPathLog) workFlowDto.getSubmitSwfPathLogList().get(i)).getStartNodeNo() == 1) {
							((SwfPathLog) workFlowDto.getSubmitSwfPathLogList().get(i)).setStartNodeNo(intLogNo);
							((SwfPathLog) workFlowDto.getSubmitSwfPathLogList().get(i)).setStartNodeName(nodeName);
							break;
						}
					}
				}
			}
			// 工作流处理结束

			// 保存开始
			if (workFlowViewHelper.checkDealDto(workFlowDto)) {
				workFlowService.deal(workFlowDto);
				user.setUserMessage(prpLacciCheck.getCheckNo());
			}
			this.addActionMessage(this.getText("db.prpLacciCheck.saveSuccess"));
			this.addActionMessage(this.getText("db.prpLacciCheck.checkNo"));
			forward = "success";
			return forward;
		} catch (UserException usee) {
			usee.printStackTrace();
			this.addActionError("title.endcaseBeforeEdit.titleName");
			httpServletRequest.setAttribute("errorMessage", usee.getMessage());
			throw usee;
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("title.endcaseBeforeEdit.titleName");
			httpServletRequest.setAttribute("errorMessage", e.getMessage());
			throw e;
		}
	}
	public PrpLacciCheckService getPrpLacciCheckService() {
		return prpLacciCheckService;
	}
	public void setPrpLacciCheckService(PrpLacciCheckService prpLacciCheckService) {
		this.prpLacciCheckService = prpLacciCheckService;
	}
	public SpecialCaseViewHelper getSpecialCaseViewHelper() {
		return specialCaseViewHelper;
	}
	public void setSpecialCaseViewHelper(SpecialCaseViewHelper specialCaseViewHelper) {
		this.specialCaseViewHelper = specialCaseViewHelper;
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
	public PrpLacciCheckTextService getPrpLacciCheckTextService() {
		return prpLacciCheckTextService;
	}
	public void setPrpLacciCheckTextService(PrpLacciCheckTextService prpLacciCheckTextService) {
		this.prpLacciCheckTextService = prpLacciCheckTextService;
	}
	
}
